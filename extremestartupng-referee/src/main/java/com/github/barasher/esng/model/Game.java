package com.github.barasher.esng.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

import com.github.barasher.esng.MetricManager;
import com.github.barasher.esng.question.QuestionContext;
import com.github.barasher.esng.question.QuestionFactory;
import com.google.common.base.Preconditions;

public class Game {

	private static final Logger LOG = LoggerFactory.getLogger(Game.class);

	@Autowired
	private QuestionFactory _questionFactory;
	@Autowired
	private MetricManager _metrics;
	@Autowired
	private ApplicationContext _context;

	private boolean _isPaused;
	private int _currentLevel;
	private final Set<Player> _players = new HashSet<>();

	public Game() {
		_currentLevel = 1;
		_isPaused = true;
	}

	@PostConstruct
	public void init() {
		_metrics.specifyLevel(getCurrentLevel());
	}

	public int getCurrentLevel() {
		return _currentLevel;
	}

	public Set<Player> getPlayers() {
		return _players;
	}

	MetricManager getMetricManager() {
		return _metrics;
	}

	QuestionFactory getQuestionFactory() {
		return _questionFactory;
	}

	public boolean isPaused() {
		return _isPaused;
	}

	public Player addPlayer(String aNickname, String aHost, int aPort) {
		final Player p = buildNewEmptyPlayer();
		p.setNickname(aNickname);
		p.setUri(aHost, aPort);
		LOG.info("Adding new player : {}", p);
		_players.add(p);
		return p;
	}

	protected Player buildNewEmptyPlayer() {
		return _context.getBean(Player.class);
	}

	QuestionContext getQuestion() {
		QuestionContext qc = null;
		if (_isPaused) {
			getMetricManager().notifyPauseQuestion();
			qc = getQuestionFactory().buildPauseQuestion();
		} else {
			getMetricManager().notifyNewQuestion();
			qc = getQuestionFactory().build(getCurrentLevel());
		}
		return qc;
	}

	@Scheduled(fixedDelay = 5000)
	public void askQuestions() {
		final QuestionContext qc = getQuestion();
		LOG.info("New question : {}", qc.getQuestion());

		// Thread exécution
		final List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (final Player curPlayer : _players) {
			futures.add(CompletableFuture.runAsync(() -> curPlayer.ask(qc)));
		}

		// Waiting for response
		for (final CompletableFuture<Void> curFuture : futures) {
			try {
				curFuture.get(1000, TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				if (!curFuture.isDone()) {
					curFuture.cancel(true);
				}
			}
		}
	}

	public void setLevel(int aLevel) {
		Preconditions.checkArgument(aLevel >= 1, "Level can't be lower than 1 (" + aLevel + " provided)");
		LOG.info("Changing level to level {}", aLevel);
		_currentLevel = aLevel;
		getMetricManager().specifyLevel(_currentLevel);
	}

	public void pause() {
		_isPaused = true;
		LOG.info("Game paused");
	}

	public void run() {
		_isPaused = false;
		LOG.info("Game is running");
	}

	public boolean removePlayer(String aNickName) {
		LOG.info("Removing player {}", aNickName);
		return _players.removeIf(cur -> cur.getNickname().equalsIgnoreCase(aNickName));
	}

}

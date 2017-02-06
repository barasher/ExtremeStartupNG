package com.barasher.esng.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

import com.barasher.esng.MetricManager;
import com.barasher.esng.question.QuestionContext;
import com.barasher.esng.question.QuestionFactory;
import com.google.common.base.Preconditions;

public class Game {

	private static final Logger LOG = LoggerFactory.getLogger(Game.class);

	@Autowired
	private QuestionFactory _questionFactory;
	@Autowired
	private MetricManager _metrics;
	@Autowired
	private ApplicationContext _context;

	private int _currentLevel;
	private final Set<Player> _players = new HashSet<>();

	public Game() {
		_currentLevel = 1;
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

	public Player addPlayer(String aNickname, String aHost, int aPort) {
		final Player p = _context.getBean(Player.class);
		p.setNickname(aNickname);
		p.setUri(aHost, aPort);
		_players.add(p);
		return p;
	}

	@Scheduled(fixedDelay = 5000)
	public void askQuestions() {
		getMetricManager().notifyNewQuestion();

		// Generating question
		final QuestionContext qc = getQuestionFactory().build(getCurrentLevel());
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

	public void setLevel(Optional<Integer> aLevel) {
		Preconditions.checkNotNull(aLevel, "Level optional can't be null");
		final int lvl = aLevel.orElse(getCurrentLevel() + 1);
		Preconditions.checkArgument(lvl >= 1, "Level can't be lower than 1 (" + aLevel + " provided)");
		LOG.info("Changing level to level {}", lvl);
		_currentLevel = lvl;
		getMetricManager().specifyLevel(_currentLevel);
	}

}

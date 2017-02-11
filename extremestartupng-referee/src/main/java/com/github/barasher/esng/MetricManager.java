package com.github.barasher.esng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.context.annotation.Lazy;

import com.github.barasher.esng.model.Player;

public class MetricManager {

	private static final String PREFIX = "extremeStartupNG.";
	private static final String NEW_QUESTION_METRIC = PREFIX + "questions.count";
	private static final String CORRECT_ANSWER_SUFFIX = ".correctAnswer.count";
	private static final String INCORRECT_ANSWER_SUFFIX = ".incorrectAnswer.count";
	private static final String CURRENT_LEVEL = PREFIX + "currentLevel";

	@Autowired
	@Lazy
	private CounterService _counterService;

	@Autowired
	@Lazy
	private GaugeService _gaugeService;

	public void notifyNewQuestion() {
		getCounterService().increment(NEW_QUESTION_METRIC);
	}

	public void notifyAnswer(Player aPlayer, boolean aIsCorrect) {
		final String metric = PREFIX + aPlayer.getNickname()
				+ (aIsCorrect ? CORRECT_ANSWER_SUFFIX : INCORRECT_ANSWER_SUFFIX);
		getCounterService().increment(metric);
	}

	public void specifyLevel(int aLevel) {
		getGaugeService().submit(CURRENT_LEVEL, aLevel);
	}

	CounterService getCounterService() {
		return _counterService;
	}

	GaugeService getGaugeService() {
		return _gaugeService;
	}

}

package com.github.barasher.esng;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;

import com.github.barasher.esng.MetricManager;
import com.github.barasher.esng.model.Player;

public class MetricManagerTest {

	private MetricManager build(CounterService aCounterS, GaugeService aGaugeS) {
		return new MetricManager() {
			@Override
			CounterService getCounterService() {
				return aCounterS;
			}

			@Override
			GaugeService getGaugeService() {
				return aGaugeS;
			}
		};
	}

	@Test
	public void notifyNewQuestionTest() {
		// set up
		final CounterService counterM = Mockito.mock(CounterService.class);
		final MetricManager m = build(counterM, null);
		// call
		m.notifyNewQuestion();
		// check
		Mockito.verify(counterM, Mockito.times(1)).increment("extremeStartupNG.questions.count");
	}

	@Test
	public void notifyCorrectAnswerTest() {
		// set up
		final CounterService counterM = Mockito.mock(CounterService.class);
		final MetricManager m = build(counterM, null);
		// call
		final Player p = new Player("n", "h", 42);
		m.notifyAnswer(p, true);
		// check
		Mockito.verify(counterM, Mockito.times(1)).increment("extremeStartupNG.n.correctAnswer.count");
	}

	@Test
	public void notifyIncorrectAnswerTest() {
		// set up
		final CounterService counterM = Mockito.mock(CounterService.class);
		final MetricManager m = build(counterM, null);
		// call
		final Player p = new Player("n", "h", 42);
		m.notifyAnswer(p, false);
		// check
		Mockito.verify(counterM, Mockito.times(1)).increment("extremeStartupNG.n.incorrectAnswer.count");
	}

	@Test
	public void specifyLevelTest() {
		// set up
		final GaugeService gaugeM = Mockito.mock(GaugeService.class);
		final MetricManager m = build(null, gaugeM);
		// call
		m.specifyLevel(42);
		// check
		Mockito.verify(gaugeM, Mockito.times(1)).submit("extremeStartupNG.currentLevel", 42);
	}

}

package com.github.barasher.esng.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import com.github.barasher.esng.Context;
import com.github.barasher.esng.configuration.EsngConfiguration;
import com.github.barasher.esng.model.Game;
import com.github.barasher.esng.model.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {
		com.github.barasher.esng.model.GameTest.TestContext.class })
public class GameTest {

	@Configuration
	static class TestContext extends Context {

		@Bean
		public CounterService getCounterService() {
			return Mockito.mock(CounterService.class);
		}

		@Bean
		public GaugeService getGaugeService() {
			return Mockito.mock(GaugeService.class);
		}

		@Override
		@Bean
		public RestTemplate getRestTemplate() {
			return Mockito.mock(RestTemplate.class);
		}

		@Bean
		public EsngConfiguration getConfiguration() {
			return new EsngConfiguration();
		}

	}

	@Autowired
	private Game _game;

	@Test
	public void testAddPlayer() {
		assertNotNull(_game.getPlayers());
		assertTrue(_game.getPlayers().isEmpty());
		final Player p = _game.addPlayer("n", "h", 42);
		assertTrue(_game.getPlayers().contains(p));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidLevelChange() {
		_game.setLevel(Optional.of(-20));
	}

	@Test
	public void testLevelChangeWithSpecifiedLevel() {
		_game.setLevel(Optional.of(2));
		assertEquals(2, _game.getCurrentLevel());
	}

	@Test
	public void testLevelChangeWithUnspecifiedLevel() {
		_game.setLevel(Optional.of(2));
		_game.setLevel(Optional.empty());
		assertEquals(3, _game.getCurrentLevel());
	}

}

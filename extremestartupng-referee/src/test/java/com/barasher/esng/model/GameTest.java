package com.barasher.esng.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

import com.barasher.esng.Context;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {
	com.barasher.esng.model.GameTest.TestContext.class })
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
	_game.setLevel(-20);
    }

    @Test
    public void testLevelChange() {
	_game.setLevel(2);
	assertEquals(2, _game.getCurrentLevel());
    }

}

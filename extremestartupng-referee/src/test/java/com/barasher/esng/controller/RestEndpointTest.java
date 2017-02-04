package com.barasher.esng.controller;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.barasher.esng.controller.data.ChangeLevelResponse;
import com.barasher.esng.model.Game;
import com.barasher.esng.model.Player;

public class RestEndpointTest {

	@Test
	public void getPlayersTest() {
		// set up
		final Game mockedGame = Mockito.mock(Game.class);
		final Set<Player> players = new HashSet<>();
		players.add(new Player("n", "h", 42));
		Mockito.when(mockedGame.getPlayers()).thenReturn(players);
		final RestEndpoint ep = new RestEndpoint(mockedGame);
		// call
		final Set<Player> res = ep.getPlayers();
		// check
		assertEquals(players, res);
		Mockito.verify(mockedGame, Mockito.times(1)).getPlayers();
	}

	@Test
	public void addPlayerTest() {
		// set up
		final Game mockedGame = Mockito.mock(Game.class);
		final Player returned = new Player("n", "h", 42);
		Mockito.when(mockedGame.addPlayer(Matchers.anyString(), Matchers.anyString(), Matchers.anyInt()))
				.thenReturn(returned);
		final RestEndpoint ep = new RestEndpoint(mockedGame);
		// call
		final Player res = ep.addPlayer("m", "g", 41);
		// check
		assertEquals(returned, res);
		Mockito.verify(mockedGame, Mockito.times(1)).addPlayer("m", "g", 41);
	}

	@Test
	public void changeLevelWithoutLevelTest() {
		// set up
		final Game mockedGame = Mockito.mock(Game.class);
		Mockito.when(mockedGame.getCurrentLevel()).thenReturn(42);
		final RestEndpoint ep = new RestEndpoint(mockedGame);
		// call
		ep.changeLevel(null);
		// check
		Mockito.verify(mockedGame, Mockito.times(1)).setLevel(43);
	}

	@Test
	public void changeLevelWithLevelTest() {
		// set up
		final Game mockedGame = Mockito.mock(Game.class);
		final RestEndpoint ep = new RestEndpoint(mockedGame);
		// call
		ep.changeLevel(72);
		// check
		Mockito.verify(mockedGame, Mockito.times(1)).setLevel(72);
	}

	@Test
	public void changeLevelOutputTest() {
		// set up
		final Game mockedGame = Mockito.mock(Game.class);
		final Set<Player> players = new HashSet<>();
		players.add(new Player("n", "h", 42));
		Mockito.when(mockedGame.getPlayers()).thenReturn(players);
		Mockito.when(mockedGame.getCurrentLevel()).thenReturn(42);
		final RestEndpoint ep = new RestEndpoint(mockedGame);
		final ChangeLevelResponse expRes = new ChangeLevelResponse(mockedGame);
		// call
		final ChangeLevelResponse res = ep.changeLevel(72); // does nothing
		// check
		assertEquals(expRes, res);
	}

}

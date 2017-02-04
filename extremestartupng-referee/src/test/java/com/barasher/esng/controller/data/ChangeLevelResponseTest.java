package com.barasher.esng.controller.data;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

import com.barasher.esng.controller.data.ChangeLevelResponse;
import com.barasher.esng.model.Game;
import com.barasher.esng.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChangeLevelResponseTest {

	static ChangeLevelResponse build(int aCurrentLevel, Set<Player> aPlayerSet) {
		final Game g = Mockito.mock(Game.class);
		Mockito.when(g.getCurrentLevel()).thenReturn(aCurrentLevel);
		Mockito.when(g.getPlayers()).thenReturn(aPlayerSet);
		return new ChangeLevelResponse(g);
	}

	@Test
	public void testMashalling() throws IOException {
		final ObjectMapper om = new ObjectMapper();
		final Set<Player> refPlayers = new HashSet<>();
		refPlayers.add(new Player("n", "h", 80));
		final ChangeLevelResponse ref = build(42, refPlayers);
		final String strVal = om.writeValueAsString(ref);
		final ChangeLevelResponse unmarshalled = om.readValue(strVal, ChangeLevelResponse.class);
		assertEquals(ref, unmarshalled);
	}

}

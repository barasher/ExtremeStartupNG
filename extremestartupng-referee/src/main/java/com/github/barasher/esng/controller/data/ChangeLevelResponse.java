package com.github.barasher.esng.controller.data;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.barasher.esng.model.Game;
import com.github.barasher.esng.model.Player;

public class ChangeLevelResponse {

	@JsonProperty("_currentLevel")
	private int _currentLevel;
	@JsonProperty("_players")
	private Set<Player> _players;

	public ChangeLevelResponse() {
		// empty
	}

	public ChangeLevelResponse(Game aGame) {
		_players = aGame.getPlayers();
		_currentLevel = aGame.getCurrentLevel();
	}

	@JsonIgnore
	public int getCurrentLevel() {
		return _currentLevel;
	}

	@JsonIgnore
	public Set<Player> getPlayers() {
		return _players;
	}

	@Override
	public boolean equals(Object anotherObject) {
		if (anotherObject == null || !anotherObject.getClass().equals(ChangeLevelResponse.class)) {
			return false;
		}
		final ChangeLevelResponse other = (ChangeLevelResponse) anotherObject;
		boolean equals = Objects.equals(_currentLevel, other._currentLevel);
		equals = equals && Objects.equals(_players, other._players);
		return equals;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_currentLevel, _players);
	}

	@Override
	public String toString() {
		return new StringBuilder("Level ").append(_currentLevel).append(" : ").append(_players).toString();
	}

}

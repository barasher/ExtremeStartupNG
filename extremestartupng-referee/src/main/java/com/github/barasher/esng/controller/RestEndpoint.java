package com.github.barasher.esng.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.barasher.esng.controller.data.ChangeLevelResponse;
import com.github.barasher.esng.model.Game;
import com.github.barasher.esng.model.Player;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class RestEndpoint {

	@Autowired
	private Game _game;

	public RestEndpoint() {
		// empty
	}

	public RestEndpoint(Game aGame) {
		_game = aGame;
	}

	@ApiOperation(value = "List current players")
	@RequestMapping(method = RequestMethod.GET, path = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Set<Player> getPlayers() {
		return getGame().getPlayers();
	}

	@ApiOperation(value = "Add a new player")
	@RequestMapping(method = RequestMethod.GET, path = "/addPlayer", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Player addPlayer(
			@ApiParam(name = "nick", value = "Candidate's nickname", required = true) @RequestParam(value = "nick", required = true) String aNickName,
			@ApiParam(name = "host", value = "Candidate's host", required = true) @RequestParam(required = true, value = "host") String aHost,
			@ApiParam(name = "port", value = "Candidate's port", required = true) @RequestParam(required = true, value = "port") int aPort) {
		return getGame().addPlayer(aNickName, aHost, aPort);
	}

	@ApiOperation(value = "Change current level")
	@RequestMapping(method = RequestMethod.GET, path = "/changeLevel", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ChangeLevelResponse changeLevel(
			@ApiParam(name = "level", value = "New level (default : next one)", required = false) @RequestParam(value = "level", required = false) Integer aLevel) {
		getGame().setLevel(Optional.ofNullable(aLevel));
		return new ChangeLevelResponse(getGame());
	}

	Game getGame() {
		return _game;
	}

}

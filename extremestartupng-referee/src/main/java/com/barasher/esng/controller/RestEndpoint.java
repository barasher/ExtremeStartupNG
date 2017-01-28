package com.barasher.esng.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.barasher.esng.controller.data.ChangeLevelResponse;
import com.barasher.esng.model.Game;
import com.barasher.esng.model.Player;

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

    @RequestMapping(method = RequestMethod.GET, path = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Set<Player> getPlayers() {
	return getGame().getPlayers();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/addPlayer", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Player addPlayer(@RequestParam(value = "nick", required = true) String aNickName,
	    @RequestParam(required = true, value = "host") String aHost,
	    @RequestParam(required = true, value = "port") int aPort) {
	return getGame().addPlayer(aNickName, aHost, aPort);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/changeLevel", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ChangeLevelResponse changeLevel(
	    @RequestParam(value = "level", required = true) Integer aLevel) {
	final int lvl = aLevel != null ? aLevel : getGame().getCurrentLevel() + 1;
	getGame().setLevel(lvl);
	return new ChangeLevelResponse(getGame());
    }

    Game getGame() {
	return _game;
    }

}

package com.barasher.esng.model;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.barasher.esng.MetricManager;
import com.barasher.esng.data.Question;
import com.barasher.esng.data.Result;
import com.barasher.esng.question.QuestionContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {

    private static final Logger LOG = LoggerFactory.getLogger(Player.class);

    @JsonIgnore
    @Autowired
    private MetricManager _metrics;

    @JsonIgnore
    @Autowired
    RestTemplate _restTemplate;

    @JsonProperty("_nick")
    private String _nick;
    @JsonProperty("_uri")
    private String _uriBase;
    @JsonProperty("_score")
    private long _score;

    public Player() {
	this(null, null, 0);
    }

    public Player(String aNickname, String aHost, int aPort) {
	setNickname(aNickname);
	setUri(aHost, aPort);
	_score = 0;
    }

    public void setNickname(String aNickname) {
	_nick = aNickname;
    }

    public void setUri(String aHost, int aPort) {
	_uriBase = "http://" + aHost + ":" + aPort;
    }

    @JsonIgnore
    public String getNickname() {
	return _nick;
    }

    @JsonIgnore
    public long getScore() {
	return _score;
    }

    public long addScore(int aDelta) {
	_score += aDelta;
	return _score;
    }

    @Override
    public String toString() {
	return Objects.toString(_nick) + "(" + Objects.toString(_uriBase) + "):" + _score;
    }

    @Override
    public boolean equals(Object anotherObject) {
	if (anotherObject == null || !anotherObject.getClass().equals(Player.class)) {
	    return false;
	}
	final Player other = (Player) anotherObject;
	boolean equals = Objects.equals(_nick, other._nick);
	equals = equals && Objects.equals(_uriBase, other._uriBase);
	equals = equals && Objects.equals(_score, other._score);
	return equals;
    }

    @Override
    public int hashCode() {
	return Objects.hash(_nick, _uriBase, _score);
    }

    public void ask(QuestionContext aQuestionContext) {
	final Question q = aQuestionContext.getQuestion();
	LOG.info("Sending question {} to {}", q.getUnid(), getNickname());

	// attente de la réponse ==> timeout
	String answer = "";
	CompletableFuture<String> sendQuestionFuture = null;
	try {
	    sendQuestionFuture = CompletableFuture.supplyAsync(() -> {
		return sendQuestion(q);
	    });
	    answer = sendQuestionFuture.get(1, TimeUnit.SECONDS);
	} catch (InterruptedException | ExecutionException | TimeoutException e) {
	    if (!sendQuestionFuture.isDone()) {
		sendQuestionFuture.cancel(true);
	    }
	    LOG.info("Error while asking question {} to {}", q.getUnid(), getNickname());
	    LOG.debug("Error while asking question {} to {}", q.getUnid(), getNickname(), e);
	}
	LOG.info("Response from {} to question {} : {}", new Object[] { getNickname(), q.getUnid(), answer });

	final boolean isCorrect = aQuestionContext.isAnswerCorrect(answer);
	addScore(isCorrect ? aQuestionContext.getPointsOnSuccess() : aQuestionContext.getPointsOnFailure());
	_metrics.notifyAnswer(this, isCorrect);

	// émission du résultat
	final Result result = new Result(q.getUnid(), isCorrect, _score);
	CompletableFuture<Void> sendResultFuture = null;
	try {
	    sendResultFuture = CompletableFuture.runAsync(() -> sendResult(result));
	    sendResultFuture.get(1, TimeUnit.SECONDS);
	} catch (InterruptedException | ExecutionException | TimeoutException e) {
	    if (!sendResultFuture.isDone()) {
		sendResultFuture.cancel(true);
	    }
	    LOG.warn("Error while notifying {} about his result for question {}", getNickname(), q.getUnid());
	    LOG.debug("Error while notifying {} about his result for question {}", getNickname(), q.getUnid(), e);
	}
    }

    private String sendQuestion(Question aQuestion) {
	return getRestTemplate().postForObject(_uriBase + "/esng/a", aQuestion, String.class);
    }

    private void sendResult(Result aResult) {
	getRestTemplate().postForLocation(_uriBase + "/esng/r", aResult);
    }

    protected RestTemplate getRestTemplate() {
	return _restTemplate;
    }

}

package com.barasher.esng.data;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

    @JsonProperty("_unid")
    private String _unid;
    @JsonProperty("_isAnswerCorrect")
    private boolean _isAnswerCorrect;
    @JsonProperty("_score")
    private long _score;

    public Result() {
	//
    }

    public Result(String anUnid, boolean aIsAnswerCorrect, long aScore) {
	_unid = anUnid;
	_isAnswerCorrect = aIsAnswerCorrect;
	_score = aScore;
    }

    @JsonIgnore
    public String getUnid() {
	return _unid;
    }

    @JsonIgnore
    public boolean isAnwserCorrect() {
	return _isAnswerCorrect;
    }

    @JsonIgnore
    public long getScore() {
	return _score;
    }

    @Override
    public boolean equals(Object anotherObject) {
	if (anotherObject == null || !anotherObject.getClass().equals(Result.class)) {
	    return false;
	}
	final Result other = (Result) anotherObject;
	boolean equals = Objects.equals(_unid, other._unid);
	equals = equals && Objects.equals(_isAnswerCorrect, other._isAnswerCorrect);
	equals = equals && Objects.equals(_score, other._score);
	return equals;
    }

    @Override
    public int hashCode() {
	return Objects.hash(_unid, _isAnswerCorrect, _score);
    }

    @Override
    public String toString() {
	final StringBuilder sb = new StringBuilder("[").append(_unid).append("]");
	sb.append(" Correct ? ").append(_isAnswerCorrect);
	sb.append(", score : ").append(_score);
	return sb.toString();
    }

}

package com.barasher.esng.data;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {

	@JsonProperty("_unid")
	private String _unid;
	@JsonProperty("_question")
	private String _question;

	public Question() {
		//
	}

	public Question(String anUnid, String aQuestion) {
		_unid = anUnid;
		_question = aQuestion;
	}

	@Override
	public boolean equals(Object anotherObject) {
		if (anotherObject == null || !anotherObject.getClass().equals(Question.class)) {
			return false;
		}
		final Question other = (Question) anotherObject;
		boolean equals = Objects.equals(_unid, other._unid);
		equals = equals && Objects.equals(_question, other._question);
		return equals;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_unid, _question);
	}

	@Override
	public String toString() {
		return new StringBuilder("[").append(_unid).append("] ").append(_question).append(" ").toString();
	}

	@JsonIgnore
	public String getUnid() {
		return _unid;
	}

	@JsonIgnore
	public String getQuestion() {
		return _question;
	}

}

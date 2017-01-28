package com.barasher.esng.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

@Component
@ConfigurationProperties(prefix = "esng")
public class EsngConfiguration {

    private long questionInterval = 1000;
    private List<QuestionConfiguration> questions;

    @Override
    public String toString() {
	final ToStringHelper a = MoreObjects.toStringHelper(this);
	a.add("questionInterval", getQuestionInterval());
	a.add("questions", getQuestions());
	return a.toString();
    }

    public long getQuestionInterval() {
	return questionInterval;
    }

    public void setQuestionInterval(long questionInterval) {
	this.questionInterval = questionInterval;
    }

    public List<QuestionConfiguration> getQuestions() {
	return questions;
    }

    public void setQuestions(List<QuestionConfiguration> questions) {
	this.questions = questions;
    }

    public static class QuestionConfiguration {

	private String family;
	private int level = 99;

	@Override
	public String toString() {
	    final ToStringHelper a = MoreObjects.toStringHelper(this);
	    a.add("family", getFamily());
	    a.add("level", getLevel());
	    return a.toString();
	}

	public String getFamily() {
	    return family;
	}

	public void setFamily(String family) {
	    this.family = family;
	}

	public int getLevel() {
	    return level;
	}

	public void setLevel(int level) {
	    this.level = level;
	}

    }

}

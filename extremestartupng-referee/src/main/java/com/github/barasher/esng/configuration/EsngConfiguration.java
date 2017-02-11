package com.github.barasher.esng.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

@Component
@ConfigurationProperties(prefix = "esng")
public class EsngConfiguration {

	private long questionInterval = 1000;
	private List<QuestionConfiguration> questions = new ArrayList<>();

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
		private Optional<Integer> level = Optional.empty();
		private Optional<Boolean> enabled = Optional.empty();

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

		public QuestionConfiguration setFamily(String family) {
			this.family = family;
			return this;
		}

		public Optional<Integer> getLevel() {
			return level;
		}

		public QuestionConfiguration setLevel(int level) {
			this.level = Optional.ofNullable(level);
			return this;
		}

		public Optional<Boolean> isEnabled() {
			return enabled;
		}

		public QuestionConfiguration setEnabled(boolean aIsEnabled) {
			enabled = Optional.of(aIsEnabled);
			return this;
		}

	}

}

package com.github.barasher.esng.question.impl;

import java.util.UUID;

import com.github.barasher.esng.data.Question;
import com.github.barasher.esng.question.IQuestionProvider;
import com.github.barasher.esng.question.QuestionContext;

public class PauseQuestionProvider implements IQuestionProvider {

	static final String PAUSE = "pause";

	@Override
	public QuestionContext provide() {
		final String unid = UUID.randomUUID().toString().substring(0, 8);
		final Question q = new Question(unid,
				"Pause question : what is the upper-case value of the word '" + PAUSE.toLowerCase() + "' ?");
		return new QuestionContext(q, (r) -> {
			return PAUSE.toUpperCase().equals(r);
		}, 0, 0);
	}

}

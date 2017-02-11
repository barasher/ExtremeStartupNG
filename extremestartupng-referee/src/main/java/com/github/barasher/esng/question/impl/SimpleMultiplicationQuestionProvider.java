package com.github.barasher.esng.question.impl;

import java.util.UUID;

import com.github.barasher.esng.data.Question;
import com.github.barasher.esng.question.IQuestionProvider;
import com.github.barasher.esng.question.QuestionContext;
import com.github.barasher.esng.question.QuestionProvider;

@QuestionProvider(family = "simpleAddtition", level = 2)
public class SimpleMultiplicationQuestionProvider implements IQuestionProvider {

	static final String MULTIPLIED_BY = " multiplied by ";

	@Override
	public QuestionContext provide() {
		final String unid = UUID.randomUUID().toString().substring(0, 8);
		final int op1 = (int) (Math.random() * 100);
		final int op2 = (int) (Math.random() * 100);
		final Question q = new Question(unid, "What is " + op1 + MULTIPLIED_BY + op2 + " ?");
		return new QuestionContext(q, (r) -> {
			try {
				if (r != null) {
					return op1 * op2 == Double.parseDouble(r);
				}
			} catch (final NumberFormatException e) {
				// nothing
			}
			return false;
		}, 5, -10);
	}

}

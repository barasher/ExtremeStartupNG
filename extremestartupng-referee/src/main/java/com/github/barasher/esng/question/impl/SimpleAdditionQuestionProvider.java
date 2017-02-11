package com.github.barasher.esng.question.impl;

import java.util.UUID;

import com.github.barasher.esng.data.Question;
import com.github.barasher.esng.question.IQuestionProvider;
import com.github.barasher.esng.question.QuestionContext;
import com.github.barasher.esng.question.QuestionProvider;

@QuestionProvider(family = "simpleAddtition", level = 1)
public class SimpleAdditionQuestionProvider implements IQuestionProvider {

	static String PLUS = " plus ";

	@Override
	public QuestionContext provide() {
		final String unid = UUID.randomUUID().toString().substring(0, 8);
		final int op1 = (int) (Math.random() * 100);
		final int op2 = (int) (Math.random() * 100);
		final Question q = new Question(unid, "What is " + op1 + PLUS + op2 + " ?");
		return new QuestionContext(q, (r) -> {
			try {
				if (r != null) {
					return op1 + op2 == Double.parseDouble(r);
				}
			} catch (final NumberFormatException e) {
				// nothing
			}
			return false;
		}, 10, -20);
	}

}

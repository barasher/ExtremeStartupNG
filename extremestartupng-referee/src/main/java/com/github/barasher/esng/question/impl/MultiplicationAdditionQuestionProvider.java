package com.github.barasher.esng.question.impl;

import java.util.UUID;

import com.github.barasher.esng.data.Question;
import com.github.barasher.esng.question.IQuestionProvider;
import com.github.barasher.esng.question.QuestionContext;
import com.github.barasher.esng.question.QuestionProvider;

@QuestionProvider(family = "MultiplicationAddition", level = 3)
public class MultiplicationAdditionQuestionProvider implements IQuestionProvider {

	static String PLUS = " plus ";
	static String MULT = " multiplied by ";

	@Override
	public QuestionContext provide() {
		final String unid = UUID.randomUUID().toString().substring(0, 8);
		final int op1 = (int) (Math.random() * 100);
		final int op2 = (int) (Math.random() * 100);
		final int op3 = (int) (Math.random() * 100);
		final Question q = new Question(unid, "What is " + op1 + MULT + op2 + PLUS + op3 + " ?");
		return new QuestionContext(q, (r) -> {
			try {
				if (r != null) {
					return op1 * op2 + op3 == Double.parseDouble(r);
				}
			} catch (final NumberFormatException e) {
				// nothing
			}
			return false;
		}, 10, -20);
	}

}

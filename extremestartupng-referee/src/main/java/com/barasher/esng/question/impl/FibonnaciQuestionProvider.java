package com.barasher.esng.question.impl;

import java.util.Random;
import java.util.UUID;

import com.barasher.esng.data.Question;
import com.barasher.esng.question.IQuestionProvider;
import com.barasher.esng.question.QuestionContext;
import com.barasher.esng.question.QuestionProvider;

@QuestionProvider(family = "Fibonnaci", level = 3)
public class FibonnaciQuestionProvider implements IQuestionProvider {

	private static final Random RANDOM = new Random();
	private static final int MAX_PARAM = 10;

	static String SUFFIX_FIRST = "st";
	static String SUFFIX_SECOND = "nd";
	static String SUFFIX_THIRD = "rd";
	static String SUFFIX_DEFAULT = "th";

	int fibo(int aX) {
		if (aX > 1) {
			return fibo(aX - 1) + fibo(aX - 2);
		} else if (aX == 0 || aX == 1) {
			return aX;
		} else {
			throw new IllegalArgumentException("Parameter has to be greater than 0");
		}
	}

	String suffix(int aValue) {
		String suffix = SUFFIX_DEFAULT;
		if (aValue <= 3 || aValue >= 21) {
			switch (aValue % 10) {
			case 1:
				suffix = SUFFIX_FIRST;
				break;
			case 2:
				suffix = SUFFIX_SECOND;
				break;
			case 3:
				suffix = SUFFIX_THIRD;
				break;
			}
		}
		return suffix;
	}

	@Override
	public QuestionContext provide() {
		final String unid = UUID.randomUUID().toString().substring(0, 8);
		final int x = RANDOM.nextInt(MAX_PARAM) + 1;
		final int expRes = fibo(x);
		final Question q = new Question(unid, "What is the " + x + suffix(x) + " Fibonnaci number ?");
		return new QuestionContext(q, (r) -> {
			try {
				if (r != null) {
					return expRes == Double.parseDouble(r);
				}
			} catch (final NumberFormatException e) {
				// nothing
			}
			return false;
		}, 10, -20);
	}

}

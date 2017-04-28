package com.github.barasher.esng.question.impl;

import java.util.Random;
import java.util.UUID;

import com.github.barasher.esng.data.Question;
import com.github.barasher.esng.question.IQuestionProvider;
import com.github.barasher.esng.question.QuestionContext;

public abstract class AbstractFibonnaciQuestionProvider implements IQuestionProvider {

	private static final Random RANDOM = new Random();
	private static final String PREFIX = "What is the ";
	private static final String SUFFIX = " Fibonnaci number ? fibo(0)=0, fibo(1)=1, fibo(n)=fibo(n-1)+fibo(n-2)";

	static String SUFFIX_FIRST = "st";
	static String SUFFIX_SECOND = "nd";
	static String SUFFIX_THIRD = "rd";
	static String SUFFIX_DEFAULT = "th";

	private final int _maxInputParam;

	protected AbstractFibonnaciQuestionProvider(int aMaxInputParam) {
		_maxInputParam = aMaxInputParam;
	}

	int fibo(int aX) {
		if (aX == 0 || aX == 1) {
			return aX;
		}
		int xm2 = 0;
		int xm1 = 1;
		int tmp = -1;
		for (int i = 2; i <= aX; i++) {
			tmp = xm1 + xm2;
			xm2 = xm1;
			xm1 = tmp;
		}
		return xm1;
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
		final int x = RANDOM.nextInt(_maxInputParam) + 1;
		final int expRes = fibo(x);
		final Question q = new Question(unid, PREFIX + x + suffix(x) + SUFFIX);
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

package com.barasher.esng.question.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import com.barasher.esng.data.Question;
import com.barasher.esng.question.IQuestionProvider;
import com.barasher.esng.question.QuestionContext;
import com.barasher.esng.question.QuestionProvider;

@QuestionProvider(family = "maximum", level = 1)
public class MaximumQuestionProvider implements IQuestionProvider {

    private static final int VALUE_COUNT = 6;
    private static final int MAX_VALUE = 60;
    private static final Comparator<Integer> INT_COMPARATOR = (x, y) -> x - y;

    @Override
    public QuestionContext provide() {
	final String unid = UUID.randomUUID().toString().substring(0, 8);
	final Random random = new Random();
	final List<Integer> intValues = new ArrayList<>();
	for (int i = 0; i < VALUE_COUNT; i++) {
	    intValues.add(random.nextInt(MAX_VALUE + 1));
	}
	final int max = intValues.stream().max(INT_COMPARATOR).get().intValue();

	final String strValues = intValues.stream().map(x -> x.toString()).collect(Collectors.joining(", "));
	final Question q = new Question(unid, "What is the maximum value of : " + strValues + " ?");
	return new QuestionContext(q, (r) -> {
	    try {
		if (r != null) {
		    return max == Double.parseDouble(r);
		}
	    } catch (final NumberFormatException e) {
		// nothing
	    }
	    return false;
	}, 10, -20);
    }

}

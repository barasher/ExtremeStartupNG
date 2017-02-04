package com.barasher.esng.question.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import com.barasher.esng.data.Question;
import com.barasher.esng.question.IQuestionProvider;
import com.barasher.esng.question.QuestionContext;
import com.barasher.esng.question.QuestionProvider;

@QuestionProvider(family = "Sort", level = 2)
public class SortQuestionProvider implements IQuestionProvider {

	private static final int VALUE_COUNT = 6;
	private static final int MAX_VALUE = 60;

	@Override
	public QuestionContext provide() {
		final String unid = UUID.randomUUID().toString().substring(0, 8);
		final Random random = new Random();
		final List<Integer> intValues = new ArrayList<>();
		for (int i = 0; i < VALUE_COUNT; i++) {
			intValues.add(random.nextInt(MAX_VALUE + 1));
		}
		final List<Double> sorted = intValues.stream().map(i -> new Double(i.intValue())).sorted()
				.collect(Collectors.toList());

		final String strValues = intValues.stream().map(x -> x.toString()).collect(Collectors.joining(", "));
		final Question q = new Question(unid,
				"Sort these numbers ascending (comma separated, example 1,3,15) : " + strValues);
		return new QuestionContext(q, (r) -> {
			try {
				if (r != null) {
					final List<Double> answValues = Arrays.stream(r.split(",")).map(s -> s.trim())
							.map(s -> Double.parseDouble(s)).collect(Collectors.toList());
					return answValues.equals(sorted);
				}
			} catch (final NumberFormatException e) {
				// nothing
			}
			return false;
		}, 10, -20);
	}

}

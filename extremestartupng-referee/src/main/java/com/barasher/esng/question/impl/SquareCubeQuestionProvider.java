package com.barasher.esng.question.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barasher.esng.data.Question;
import com.barasher.esng.question.IQuestionProvider;
import com.barasher.esng.question.QuestionContext;
import com.barasher.esng.question.QuestionProvider;

@QuestionProvider(family = "SquareCube", level = 3)
public class SquareCubeQuestionProvider implements IQuestionProvider {

    private static Logger LOG = LoggerFactory.getLogger(MaximumQuestionProvider.class);

    private static final int VALUE_COUNT = 6;
    private static final int MAX_SIXTH_ROOT = 10;
    private static final int MAX_VALUE = (int) Math.pow(MAX_SIXTH_ROOT, 6);

    static boolean isSquare(int aValue) {
	return Math.pow((int) Math.sqrt(aValue), 2) == aValue;
    }

    static boolean isCube(int aValue) {
	return Math.pow((int) Math.cbrt(aValue), 3) == aValue;
    }

    @Override
    public QuestionContext provide() {
	final String unid = UUID.randomUUID().toString().substring(0, 8);
	final Random random = new Random();

	// random
	final List<Integer> intValues = new ArrayList<>();
	for (int i = 0; i < VALUE_COUNT; i++) {
	    intValues.add(random.nextInt(MAX_VALUE + 1));
	}

	// correct response insertion
	final int nbToInject = random.nextInt(3) + 1;
	int curRes = -1;
	for (int i = 0; i < nbToInject; i++) {
	    curRes = (int) Math.pow(random.nextInt(MAX_SIXTH_ROOT) + 1, 6);
	    intValues.set(i, curRes);
	}

	// choosing responses
	final Set<Integer> expResponses = new HashSet<>();
	for (final Integer curChoice : intValues) {
	    if (SquareCubeQuestionProvider.isCube(curChoice) && SquareCubeQuestionProvider.isSquare(curChoice)) {
		expResponses.add(curChoice);
	    }
	}

	// defining question
	final String strValues = intValues.stream().map(x -> x.toString()).collect(Collectors.joining(", "));
	final Question q = new Question(unid,
		"What numbers are both square and cube in " + strValues + " ? (comma separated)");
	return new QuestionContext(q, (r) -> {
	    try {
		if (r != null) {
		    final Set<Integer> answValues = Arrays.stream(r.split(",")).map(s -> s.trim())
			    .map(s -> Double.parseDouble(s)).map(d -> d.intValue()).collect(Collectors.toSet());
		    return answValues.equals(expResponses);
		}
	    } catch (final NumberFormatException e) {
		// nothing
	    }
	    return false;
	}, 10, -20);
    }

}

package com.github.barasher.esng.question.impl;

import com.github.barasher.esng.question.QuestionProvider;

@QuestionProvider(family = "Fibonnaci", level = 3)
public class FibonnaciQuestionProvider extends AbstractFibonnaciQuestionProvider {

	public FibonnaciQuestionProvider() {
		super(10);
	}

}

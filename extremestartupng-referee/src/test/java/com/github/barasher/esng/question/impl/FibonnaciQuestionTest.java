package com.github.barasher.esng.question.impl;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class FibonnaciQuestionTest extends AbstractFibonnaciQuestionTest {

	public FibonnaciQuestionTest() {
		super(new FibonnaciQuestionProvider());
	}

}

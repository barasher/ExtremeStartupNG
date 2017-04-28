package com.github.barasher.esng.question.impl;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AdvancedFibonnaciQuestionTest extends AbstractFibonnaciQuestionTest {

	public AdvancedFibonnaciQuestionTest() {
		super(new AdvancedFibonnaciQuestionProvider());
	}

}

package com.github.barasher.esng.question.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.barasher.esng.question.IQuestionProvider;
import com.github.barasher.esng.question.QuestionContext;

@RunWith(SpringRunner.class)
public class PauseQuestionProviderTest {

	private IQuestionProvider _provider = null;

	public PauseQuestionProviderTest() {
		_provider = new PauseQuestionProvider();
	}

	private String getResponse(QuestionContext aQuestionContext) {
		return PauseQuestionProvider.PAUSE.toUpperCase();
	}

	@Test
	public void test() throws InterruptedException {
		final QuestionContext qc = _provider.provide();
		final String response = getResponse(qc);
		final String strError = "Error in response, question : '" + qc.getQuestion().toString()
				+ "' provided response : '" + response + "'";
		assertTrue(strError, qc.isAnswerCorrect(response));
	}

	@Test
	public void testNullResponse() {
		final QuestionContext qc = _provider.provide();
		assertFalse(qc.isAnswerCorrect(null));
	}

	@Test
	public void testWrongResponse() {
		final QuestionContext qc = _provider.provide();
		final String response = "abcdefg";
		assertFalse(qc.isAnswerCorrect(response));
	}

}

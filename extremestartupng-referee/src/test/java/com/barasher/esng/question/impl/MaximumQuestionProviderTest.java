package com.barasher.esng.question.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import com.barasher.esng.question.IQuestionProvider;
import com.barasher.esng.question.QuestionContext;

@RunWith(SpringRunner.class)
public class MaximumQuestionProviderTest {

	private IQuestionProvider _provider = null;
	private final Comparator<Integer> _comparator = (x, y) -> x - y;

	public MaximumQuestionProviderTest() {
		_provider = new MaximumQuestionProvider();
	}

	private int getResponse(QuestionContext aQuestionContext) {
		final String q = aQuestionContext.getQuestion().getQuestion();
		final String strNumbers = q.substring(q.indexOf(": ") + 2, q.indexOf(" ?"));
		return Arrays.stream(strNumbers.split(", ")).map(s -> s.trim()).map(s -> Integer.parseInt(s)).max(_comparator)
				.get();
	}

	@Test
	@Repeat(50)
	public void test() throws InterruptedException {
		final QuestionContext qc = _provider.provide();
		final int res = getResponse(qc);
		final String response = Integer.toString(res);
		final String strError = "Error in response, question : '" + qc.getQuestion().toString()
				+ "' provided response : '" + response + "'";
		assertTrue(strError, qc.isAnswerCorrect(response));
	}

	@Test
	public void testDoubleResponse() {
		final QuestionContext qc = _provider.provide();
		final int sum = getResponse(qc);
		final double dSum = sum;
		final String response = Double.toString(dSum);
		final String strError = "Error in response, question : '" + qc.getQuestion().toString()
				+ "' provided response : '" + response + "'";
		assertTrue(strError, qc.isAnswerCorrect(response));
	}

	@Test
	public void testPaddedResponse() {
		final QuestionContext qc = _provider.provide();
		final int sum = getResponse(qc);
		final String response = "000" + Integer.toString(sum);
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
	public void testUnparsableResponse() {
		final QuestionContext qc = _provider.provide();
		assertFalse(qc.isAnswerCorrect("john"));
	}

	@Test
	public void testWrongResponse() {
		final QuestionContext qc = _provider.provide();
		System.out.println(qc.getQuestion().getQuestion());
		final int sum = getResponse(qc);
		final String response = Integer.toString(sum + 1);
		assertFalse(qc.isAnswerCorrect(response));
	}

}

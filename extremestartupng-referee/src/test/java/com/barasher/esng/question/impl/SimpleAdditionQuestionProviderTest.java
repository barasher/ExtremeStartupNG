package com.barasher.esng.question.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import com.barasher.esng.question.IQuestionProvider;
import com.barasher.esng.question.QuestionContext;

@RunWith(SpringRunner.class)
public class SimpleAdditionQuestionProviderTest {

	private IQuestionProvider _provider = null;
	private final Pattern _pattern;

	public SimpleAdditionQuestionProviderTest() {
		_provider = new SimpleAdditionQuestionProvider();
		_pattern = Pattern.compile(".* (\\d+)" + SimpleAdditionQuestionProvider.PLUS + "(\\d+) .*");
	}

	private int getResponse(QuestionContext aQuestionContext) {
		final Matcher m = _pattern.matcher(aQuestionContext.getQuestion().getQuestion());
		final String strError = "Error while matching regexp, question : " + aQuestionContext.getQuestion().toString();
		Assert.assertTrue(strError, m.matches());
		final int sum = Integer.parseInt(m.group(1)) + Integer.parseInt(m.group(2));
		return sum;
	}

	@Test
	@Repeat(50)
	public void test() throws InterruptedException {
		final QuestionContext qc = _provider.provide();
		final int sum = getResponse(qc);
		final String response = Integer.toString(sum);
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
		final int sum = getResponse(qc);
		final String response = Integer.toString(sum + 1);
		assertFalse(qc.isAnswerCorrect(response));
	}

}

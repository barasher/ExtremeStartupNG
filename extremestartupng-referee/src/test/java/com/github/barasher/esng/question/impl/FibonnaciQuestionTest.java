package com.github.barasher.esng.question.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.barasher.esng.question.QuestionContext;
import com.github.barasher.esng.question.impl.FibonnaciQuestionProvider;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@RunWith(SpringRunner.class)
public class FibonnaciQuestionTest {

	private FibonnaciQuestionProvider _provider = null;
	private final Pattern _pattern;

	public FibonnaciQuestionTest() {
		_provider = new FibonnaciQuestionProvider();
		_pattern = Pattern.compile(".*the (\\d*).. Fibonnaci.*");
	}

	private int getResponse(QuestionContext aQuestionContext) {
		final Matcher m = _pattern.matcher(aQuestionContext.getQuestion().getQuestion());
		final String strError = "Error while matching regexp, question : " + aQuestionContext.getQuestion().toString();
		Assert.assertTrue(strError, m.matches());
		final int x = Integer.parseInt(m.group(1));
		return _provider.fibo(x);
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

	@Test
	public void fiboTest() {
		assertEquals(0, _provider.fibo(0));
		assertEquals(1, _provider.fibo(1));
		assertEquals(1, _provider.fibo(2));
		assertEquals(2, _provider.fibo(3));
		assertEquals(3, _provider.fibo(4));
		assertEquals(5, _provider.fibo(5));
		assertEquals(8, _provider.fibo(6));
		assertEquals(13, _provider.fibo(7));
		assertEquals(21, _provider.fibo(8));
		assertEquals(34, _provider.fibo(9));
		assertEquals(55, _provider.fibo(10));
	}

	@Test
	public void prefixTest() {
		final Multimap<String, Integer> exp = ArrayListMultimap.create();
		exp.putAll("st", Arrays.asList(1, 21, 31));
		exp.putAll("nd", Arrays.asList(2, 22, 32));
		exp.putAll("rd", Arrays.asList(3, 23, 33));
		exp.putAll("th", Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11, 12, 13));
		for (final Entry<String, Integer> cur : exp.entries()) {
			assertEquals("prefix(" + cur.getValue() + ") wrong", cur.getKey(), _provider.suffix(cur.getValue()));
		}
	}

}

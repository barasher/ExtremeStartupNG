package com.github.barasher.esng.question.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.barasher.esng.question.IQuestionProvider;
import com.github.barasher.esng.question.QuestionContext;
import com.github.barasher.esng.question.impl.SquareCubeQuestionProvider;

@RunWith(SpringRunner.class)
public class SquareCubeQuestionProviderTest {

	private IQuestionProvider _provider = null;

	public SquareCubeQuestionProviderTest() {
		_provider = new SquareCubeQuestionProvider();
	}

	private boolean isSquareAndCube(int aValue) {
		return SquareCubeQuestionProvider.isSquare(aValue) && SquareCubeQuestionProvider.isCube(aValue);
	}

	private String getResponse(QuestionContext aQuestionContext) {
		final String q = aQuestionContext.getQuestion().getQuestion();
		final String strNumbers = q.substring(q.indexOf("in ") + 3, q.indexOf(" ?"));
		return Arrays.stream(strNumbers.split(", ")).map(s -> s.trim()).map(s -> Integer.parseInt(s)).filter(i -> {
			final boolean b = isSquareAndCube(i);
			return b;
		}).map(i -> i.toString()).collect(Collectors.joining(","));
	}

	@Test
	@Repeat(50)
	public void test() throws InterruptedException {
		final QuestionContext qc = _provider.provide();
		final String response = getResponse(qc);
		final String strError = "Error in response, question : '" + qc.getQuestion().toString()
				+ "' provided response : '" + response + "'";
		assertTrue(strError, qc.isAnswerCorrect(response));
	}

	@Test
	public void testPaddedResponse() {
		final QuestionContext qc = _provider.provide();
		final String response = "0" + getResponse(qc);
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
		final String response = getResponse(qc) + ",42";
		assertFalse(qc.isAnswerCorrect(response));
	}

	@Test
	public void isSquareTest() {
		assertTrue(SquareCubeQuestionProvider.isSquare(1));
		assertTrue(SquareCubeQuestionProvider.isSquare(4));
		assertTrue(SquareCubeQuestionProvider.isSquare(16));
		assertFalse(SquareCubeQuestionProvider.isSquare(2));
		assertFalse(SquareCubeQuestionProvider.isSquare(5));
	}

	@Test
	public void isCubeTest() {
		assertTrue(SquareCubeQuestionProvider.isCube(1));
		assertTrue(SquareCubeQuestionProvider.isCube(8));
		assertTrue(SquareCubeQuestionProvider.isCube(27));
		assertFalse(SquareCubeQuestionProvider.isCube(2));
		assertFalse(SquareCubeQuestionProvider.isCube(5));
	}

}

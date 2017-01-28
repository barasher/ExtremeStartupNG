package com.barasher.esng.question.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import com.barasher.esng.question.IQuestionProvider;
import com.barasher.esng.question.QuestionContext;

@RunWith(SpringRunner.class)
public class SortQuestionProviderTest {

    private IQuestionProvider _provider = null;
    private final Comparator<Integer> _comparator = (x, y) -> x - y;

    public SortQuestionProviderTest() {
	_provider = new SortQuestionProvider();
    }

    private String getResponse(QuestionContext aQuestionContext) {
	final String q = aQuestionContext.getQuestion().getQuestion();
	final String strNumbers = q.substring(q.indexOf(": ") + 2);
	return Arrays.stream(strNumbers.split(", ")).map(s -> s.trim()).map(s -> Integer.parseInt(s)).sorted()
		.map(i -> i.toString()).collect(Collectors.joining(","));
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

}

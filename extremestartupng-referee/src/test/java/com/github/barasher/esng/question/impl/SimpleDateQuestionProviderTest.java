package com.github.barasher.esng.question.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.barasher.esng.question.IQuestionProvider;
import com.github.barasher.esng.question.QuestionContext;
import com.github.barasher.esng.question.impl.SimpleDateQuestionProvider;

@RunWith(SpringRunner.class)
public class SimpleDateQuestionProviderTest {

	private IQuestionProvider _provider = null;
	private final Pattern _pattern;

	public SimpleDateQuestionProviderTest() {
		_provider = new SimpleDateQuestionProvider();
		_pattern = Pattern.compile(".*is (.*),.*was (\\d*) day\\(s\\), (\\d*) hours\\(s\\), (\\d*) minute\\(s\\).*");
	}

	private String getResponse(QuestionContext aQuestionContext) {
		final Matcher m = _pattern.matcher(aQuestionContext.getQuestion().getQuestion());
		final String strError = "Error while matching regexp, question : " + aQuestionContext.getQuestion().toString();
		Assert.assertTrue(strError, m.matches());

		final String strRefDate = m.group(1);
		final DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		final LocalDateTime refDate = LocalDateTime.parse(strRefDate, form);

		final int deltaDays = Integer.parseInt(m.group(2));
		final int deltaHours = Integer.parseInt(m.group(3));
		final int deltaMinutes = Integer.parseInt(m.group(4));
		final LocalDateTime resDate = refDate.minusDays(deltaDays).minusHours(deltaHours).minusMinutes(deltaMinutes);

		return form.format(resDate);
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
	public void testNullResponse() {
		final QuestionContext qc = _provider.provide();
		assertFalse(qc.isAnswerCorrect(null));
	}

	@Test
	public void testUnparsableResponse() {
		final QuestionContext qc = _provider.provide();
		assertFalse(qc.isAnswerCorrect("john"));
	}

}

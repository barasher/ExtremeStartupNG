package com.github.barasher.esng.question.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import com.github.barasher.esng.data.Question;
import com.github.barasher.esng.question.IQuestionProvider;
import com.github.barasher.esng.question.QuestionContext;
import com.github.barasher.esng.question.QuestionProvider;

@QuestionProvider(family = "simpleDate", level = 4)
public class SimpleDateQuestionProvider implements IQuestionProvider {

	@Override
	public QuestionContext provide() {
		final String unid = UUID.randomUUID().toString().substring(0, 8);

		final Random rand = new Random();
		final LocalDateTime refDate = LocalDateTime.of(rand.nextInt(90) + 1900, rand.nextInt(12) + 1,
				rand.nextInt(25) + 1, rand.nextInt(23) + 1, rand.nextInt(59) + 1);
		final DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

		final int deltaDays = rand.nextInt(150);
		final int deltaHours = rand.nextInt(23);
		final int deltaMinutes = rand.nextInt(59);
		final LocalDateTime calcDate = refDate.minusDays(deltaDays).minusHours(deltaHours).minusMinutes(deltaMinutes);

		final String strQ = "Let's consider that the current date is " + form.format(refDate) + ", what date was "
				+ deltaDays + " day(s), " + deltaHours + " hours(s), " + deltaMinutes
				+ " minute(s) ago ? (format : [year]/[month]/[day] [hour]:[min])";
		final String expRes = form.format(calcDate);

		final Question q = new Question(unid, strQ);
		return new QuestionContext(q, (r) -> {
			if (r != null) {
				return expRes.equals(r.trim());
			}
			return false;
		}, 10, -20);
	}

}

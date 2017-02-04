package com.barasher.esng.question;

import com.barasher.esng.data.Question;

public class QuestionContext {

	private final Question _question;
	private final IAnswerChecker _answerChecker;
	private final int _pointsOnSuccess;
	private final int _pointsOnFailure;

	public QuestionContext(Question aQuestion, IAnswerChecker anAnswerChecker, int aPointsOnSuccess,
			int aPointsOnFailure) {
		_question = aQuestion;
		_answerChecker = anAnswerChecker;
		_pointsOnFailure = aPointsOnFailure;
		_pointsOnSuccess = aPointsOnSuccess;
	}

	public Question getQuestion() {
		return _question;
	}

	public boolean isAnswerCorrect(String anAnswer) {
		return _answerChecker.checkAnswer(anAnswer);
	}

	public int getPointsOnFailure() {
		return _pointsOnFailure;
	}

	public int getPointsOnSuccess() {
		return _pointsOnSuccess;
	}

}

package com.github.barasher.esng;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.barasher.esng.data.Question;
import com.github.barasher.esng.data.Result;

@RestController
public class CandidateService {

	static int ANSWERING_TIMEOUT_SEC = 3;
	static String TIMEOUT_ANSWER = "TimeoutAnswer";

	@RequestMapping(value = "esng/a", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String ask(@RequestBody Question aQuestion) {
		return ask(aQuestion, new AnswerManager());
	}

	private String prefixWithQuestionUnid(Question aQuestion, String aMsg) {
		return "[" + aQuestion.getUnid() + "] " + aMsg;
	}

	String ask(Question aQuestion, AnswerManager anAnswerManager) {
		final String strQuestion = prefixWithQuestionUnid(aQuestion, aQuestion.getQuestion());

		// initializing completableFuture (for timeout handling)
		final CompletableFuture<String> answerFuture = CompletableFuture
				.supplyAsync(() -> anAnswerManager.doAnswer(strQuestion));

		// Waiting for response
		try {
			return answerFuture.get(ANSWERING_TIMEOUT_SEC, TimeUnit.SECONDS);
		} catch (InterruptedException | TimeoutException e) {
			if (!answerFuture.isDone()) {
				answerFuture.cancel(true);
			}
			System.err.println(prefixWithQuestionUnid(aQuestion, ANSWERING_TIMEOUT_SEC + " seconds timeout reached !"));
			return TIMEOUT_ANSWER;
		} catch (final ExecutionException e) {
			e.printStackTrace(System.err);
			return TIMEOUT_ANSWER;
		}

	}

	@RequestMapping(value = "esng/r", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void getResult(@RequestBody Result aResult) {
		(aResult.isAnwserCorrect() ? System.out : System.err).println(aResult);
	}

}
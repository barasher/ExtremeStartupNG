package com.github.barasher.esng;

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

	static final String DEFAULT_RESPONSE = "defaultResponse";

	@RequestMapping(value = "esng/a", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String ask(@RequestBody Question aQuestion) {
		final String strQuestion = "[" + aQuestion.getUnid() + "] " + aQuestion.getQuestion();
		return doAnswer(strQuestion);
	}

	@RequestMapping(value = "esng/r", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void getResult(@RequestBody Result aResult) {
		(aResult.isAnwserCorrect() ? System.out : System.err).println(aResult);
	}

	public String doAnswer(String aQuestion) {
		System.out.println(aQuestion);
		// code here
		return DEFAULT_RESPONSE;
	}

}
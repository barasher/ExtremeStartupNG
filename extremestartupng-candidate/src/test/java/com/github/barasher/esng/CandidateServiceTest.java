package com.github.barasher.esng;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.barasher.esng.data.Question;
import com.github.barasher.esng.data.Result;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CandidateServiceTest extends TestCase {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void nominalServerAskTest() {
		final Question q = new Question("u", "q");
		final String res = restTemplate.postForObject("/esng/a", q, String.class);
		assertEquals(AnswerManager.DEFAULT_RESPONSE, res);
	}

	@Test
	public void nominalServerResultTest() {
		final Result r = new Result("u", true, 42);
		restTemplate.postForLocation("/esng/r", r);
	}

	@Test
	public void offlineTimeoutTest() {
		final AnswerManager am = new AnswerManager() {
			@Override
			public String doAnswer(String aQuestion) {
				try {
					Thread.sleep(6000);
				} catch (final InterruptedException e) {
				}
				return "blabla";
			}
		};
		final Question q = new Question("unid", "question");
		assertEquals(CandidateService.TIMEOUT_ANSWER, new CandidateService().ask(q, am));
	}

	@Test
	public void offlineExceptionTest() {
		final AnswerManager am = new AnswerManager() {
			@Override
			public String doAnswer(String aQuestion) {
				throw new NullPointerException();
			}
		};
		final Question q = new Question("unid", "question");
		assertEquals(CandidateService.TIMEOUT_ANSWER, new CandidateService().ask(q, am));
	}

}

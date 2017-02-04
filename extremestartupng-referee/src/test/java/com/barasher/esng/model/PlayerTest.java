package com.barasher.esng.model;

import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.barasher.esng.MetricManager;
import com.barasher.esng.data.Question;
import com.barasher.esng.model.PlayerTest.PlayerTestContext;
import com.barasher.esng.question.QuestionContext;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PlayerTestContext.class)
public class PlayerTest {

	@Configuration
	static class PlayerTestContext {
		@Bean
		public MetricManager getMetricManager() {
			return Mockito.mock(MetricManager.class);
		}

		@Bean
		public RestTemplate getRestTemplate() {
			return Mockito.mock(RestTemplate.class);
		}

		@Bean
		@Scope("prototype")
		public Player getPlayer() {
			final Player p = new Player();
			p.setNickname("a");
			p.setUri("h", 42);
			return p;
		}
	}

	@Autowired
	private Player _p;

	@Test
	public void testCorrectAnswer() throws URISyntaxException {
		final Question q = new Question("u", "q");
		final QuestionContext qctx = new QuestionContext(q, (a) -> true, 10, -20);
		_p.ask(qctx);
		Assert.assertEquals(_p.getScore(), 10);
	}

	@Test
	public void testWrongAnswer() throws URISyntaxException {
		final Question q = new Question("u", "q");
		final QuestionContext qctx = new QuestionContext(q, (a) -> false, 10, -20);
		_p.ask(qctx);
		Assert.assertEquals(_p.getScore(), -20);
	}

}

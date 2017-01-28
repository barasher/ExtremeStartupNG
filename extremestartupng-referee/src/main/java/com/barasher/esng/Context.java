package com.barasher.esng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.barasher.esng.model.Game;
import com.barasher.esng.model.Player;
import com.barasher.esng.question.QuestionFactory;

@Configuration
@EnableAsync
@EnableScheduling
public class Context {

    private static Logger LOG = LoggerFactory.getLogger(Context.class);

    @Bean
    public Game getReferee() {
	return new Game();
    }

    @Bean
    public QuestionFactory getQuestionFactory() {
	return new QuestionFactory();
    }

    @Bean
    @Scope("prototype")
    public Player getPlayer() {
	return new Player();
    }

    @Bean
    public MetricManager getMetricManager() {
	return new MetricManager();
    }

    @Bean
    public RestTemplate getRestTemplate() {
	return new RestTemplate();
    }

}

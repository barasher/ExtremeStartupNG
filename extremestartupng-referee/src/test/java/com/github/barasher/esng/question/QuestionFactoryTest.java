package com.github.barasher.esng.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.barasher.esng.configuration.EsngConfiguration;
import com.github.barasher.esng.configuration.EsngConfiguration.QuestionConfiguration;
import com.github.barasher.esng.data.Question;
import com.github.barasher.esng.question.IQuestionProvider;
import com.github.barasher.esng.question.QuestionContext;
import com.github.barasher.esng.question.QuestionFactory;
import com.github.barasher.esng.question.QuestionProvider;

public class QuestionFactoryTest {

	private static final int BASE_LEVEL = 84;
	private static final int NEW_LEVEL = 42;

	@QuestionProvider(family = "testFamily", level = BASE_LEVEL)
	public static class TestQuestionProvider implements IQuestionProvider {

		@Override
		public QuestionContext provide() {
			final Question q = new Question("unid", "question");
			return new QuestionContext(q, (r) -> true, -10, 10);
		}

	}

	private static QuestionFactory _factory;

	@BeforeClass
	public static void init() {
		_factory = new QuestionFactory();
	}

	@Test
	public void testRandom() {
		int curVal = -1;
		for (int i = 0; i < 10000; i++) {
			curVal = QuestionFactory.random(12, 42);
			assertTrue("Incorrect value : " + curVal, curVal >= 12 && curVal < 42);
		}

	}

	private void testLevel(int aLevel) {
		final Set<Class<?>> classes = new HashSet<>();

		// retrieving provided classes
		IQuestionProvider curProvided = null;
		for (int i = 0; i < 50; i++) {
			curProvided = _factory.getProvided(aLevel);
			assertNotNull(curProvided);
			classes.add(curProvided.getClass());
		}

		// check
		for (final Class<?> curClass : classes) {
			// checking interface
			Assert.assertTrue(curClass.getCanonicalName() + " is not assignable, asked level : " + aLevel,
					IQuestionProvider.class.isAssignableFrom(curClass));

			// checking annotation
			final QuestionProvider[] annotations = curClass.getAnnotationsByType(QuestionProvider.class);
			assertNotNull(annotations);
			assertEquals(1, annotations.length);
			assertTrue("Annotation level " + annotations[0].level() + " provided for level " + aLevel,
					annotations[0].level() <= aLevel);
		}
	}

	@Test
	public void testLevels() {
		for (int i = 1; i <= _factory.getMaxLevel(); i++) {
			testLevel(i);
		}
	}

	@Test
	public void testNonOverriding() {
		// check nominal behaviour without family configuration

		final QuestionFactory qf = new QuestionFactory(new EsngConfiguration());
		qf.init();
		assertNotNull(qf.getProvided(BASE_LEVEL));
	}

	@Test
	public void testLevelOverridingWhenSpecified() {
		// check that an existing family configuration with specified level
		// change the family level

		// setting up configuration
		final EsngConfiguration conf = new EsngConfiguration();
		final List<QuestionConfiguration> qConfList = new ArrayList<>();
		qConfList.add(new QuestionConfiguration().setFamily("testFamily").setLevel(NEW_LEVEL));
		conf.setQuestions(qConfList);

		// test
		final QuestionFactory qf = new QuestionFactory(conf);
		qf.init();
		try {
			qf.getProvided(BASE_LEVEL);
			fail("Should have failed");
		} catch (final IllegalArgumentException e) {
		}
		assertNotNull(qf.getProvided(NEW_LEVEL));
	}

	@Test
	public void testLevelOverridingWhenUnspecified() {
		// check that an existing family configuration without specified level
		// does not change the family level

		// setting up configuration
		final EsngConfiguration conf = new EsngConfiguration();
		final List<QuestionConfiguration> qConfList = new ArrayList<>();
		qConfList.add(new QuestionConfiguration().setFamily("testFamily"));
		conf.setQuestions(qConfList);

		// test
		final QuestionFactory qf = new QuestionFactory(conf);
		qf.init();
		assertNotNull(qf.getProvided(BASE_LEVEL));
	}

	@Test
	public void testDisabling() {
		// check that desabling a question works properly

		// setting up configuration
		final EsngConfiguration conf = new EsngConfiguration();
		final List<QuestionConfiguration> qConfList = new ArrayList<>();
		qConfList.add(new QuestionConfiguration().setFamily("testFamily").setEnabled(false));
		conf.setQuestions(qConfList);

		// test
		final QuestionFactory qf = new QuestionFactory(conf);
		qf.init();
		try {
			qf.getProvided(BASE_LEVEL);
			fail("Should have failed");
		} catch (final IllegalArgumentException e) {
		}
	}

}

package com.barasher.esng.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class QuestionFactoryTest {

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

}

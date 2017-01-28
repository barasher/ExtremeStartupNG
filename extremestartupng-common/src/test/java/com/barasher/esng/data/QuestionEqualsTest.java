package com.barasher.esng.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class QuestionEqualsTest {

    private final Question _leftOperand;
    private final Object _rightOperand;
    private final boolean _shouldEquals;
    private final String _testCase;

    public QuestionEqualsTest(final Question aLeftOperand, final Object aRightOperand, final boolean aShouldBeEquals,
	    final String aTestCase) {
	_leftOperand = aLeftOperand;
	_rightOperand = aRightOperand;
	_shouldEquals = aShouldBeEquals;
	_testCase = aTestCase;
    }

    @Parameters(name = "{index} : {3}")
    public static Iterable<Object[]> parameters() {
	final List<Object[]> tests = new ArrayList<>();
	final Question ref = new Question("u", "q");

	tests.add(new Object[] { ref, ref, true, "same" });
	tests.add(new Object[] { ref, null, false, "null" });
	tests.add(new Object[] { ref, new Question("u", "q"), true, "clone" });
	tests.add(new Object[] { ref, "truc", false, "other type" });

	tests.add(new Object[] { ref, new Question("u2", "q"), false, "unid1" });
	tests.add(new Object[] { ref, new Question(null, "q"), false, "unid2" });
	tests.add(new Object[] { ref, new Question("u", "q2"), false, "question1" });
	tests.add(new Object[] { ref, new Question("u", null), false, "question2" });

	return tests;
    }

    @Test
    public void testEqualsHashCode() {
	if (_leftOperand.equals(_rightOperand)) {
	    assertTrue(_shouldEquals);
	    assertEquals(_leftOperand.hashCode(), _rightOperand.hashCode());
	} else {
	    assertFalse(_shouldEquals);
	}
    }

}

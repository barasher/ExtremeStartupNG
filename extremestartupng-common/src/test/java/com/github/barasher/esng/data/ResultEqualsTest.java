package com.github.barasher.esng.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.github.barasher.esng.data.Result;

@RunWith(Parameterized.class)
public class ResultEqualsTest {

	private final Result _leftOperand;
	private final Object _rightOperand;
	private final boolean _shouldEquals;

	public ResultEqualsTest(Result aLeftOperand, Object aRightOperand, boolean aShouldBeEquals, String aTestCase) {
		_leftOperand = aLeftOperand;
		_rightOperand = aRightOperand;
		_shouldEquals = aShouldBeEquals;
	}

	@Parameters(name = "{index} : {3}")
	public static Iterable<Object[]> parameters() {
		final List<Object[]> tests = new ArrayList<>();
		final Result ref = new Result("u", true, 42);

		tests.add(new Object[] { ref, ref, true, "same" });
		tests.add(new Object[] { ref, null, false, "null" });
		tests.add(new Object[] { ref, new Result("u", true, 42), true, "clone" });
		tests.add(new Object[] { ref, "truc", false, "other type" });

		tests.add(new Object[] { ref, new Result("u2", true, 42), false, "unid1" });
		tests.add(new Object[] { ref, new Result(null, true, 42), false, "unid2" });
		tests.add(new Object[] { ref, new Result("u", false, 42), false, "isCorrect" });
		tests.add(new Object[] { ref, new Result("u", true, 84), false, "score" });

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

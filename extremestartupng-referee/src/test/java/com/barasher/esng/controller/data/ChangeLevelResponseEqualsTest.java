package com.barasher.esng.controller.data;

import static com.barasher.esng.controller.data.ChangeLevelResponseTest.build;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.barasher.esng.controller.data.ChangeLevelResponse;
import com.barasher.esng.model.Player;

@RunWith(Parameterized.class)
public class ChangeLevelResponseEqualsTest {

    private final ChangeLevelResponse _leftOperand;
    private final Object _rightOperand;
    private final boolean _shouldEquals;
    private final String _testCase;

    public ChangeLevelResponseEqualsTest(final ChangeLevelResponse aLeftOperand, final Object aRightOperand,
	    final boolean aShouldBeEquals, final String aTestCase) {
	_leftOperand = aLeftOperand;
	_rightOperand = aRightOperand;
	_shouldEquals = aShouldBeEquals;
	_testCase = aTestCase;
    }

    @Parameters(name = "{index} : {3}")
    public static Iterable<Object[]> parameters() {
	final List<Object[]> tests = new ArrayList<>();
	final Set<Player> refPlayers = new HashSet<>();
	refPlayers.add(new Player("n", "h", 80));

	final ChangeLevelResponse ref = build(42, refPlayers);

	tests.add(new Object[] { ref, ref, true, "same" });
	tests.add(new Object[] { ref, null, false, "null" });
	tests.add(new Object[] { ref, build(42, refPlayers), true, "clone" });
	tests.add(new Object[] { ref, "truc", false, "other type" });

	tests.add(new Object[] { ref, build(43, refPlayers), false, "level" });
	final Set<Player> otherPlayers = new HashSet<>();
	otherPlayers.add(new Player("n2", "h2", 12));
	tests.add(new Object[] { ref, build(42, otherPlayers), false, "players" });
	tests.add(new Object[] { ref, build(42, null), false, "players null" });

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

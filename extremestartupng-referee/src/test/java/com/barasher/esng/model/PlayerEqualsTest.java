package com.barasher.esng.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PlayerEqualsTest {

	private final Player _leftOperand;
	private final Object _rightOperand;
	private final boolean _shouldEquals;

	public PlayerEqualsTest(Player aLeftOperand, Object aRightOperand, boolean aShouldBeEquals, String aTestCase) {
		_leftOperand = aLeftOperand;
		_rightOperand = aRightOperand;
		_shouldEquals = aShouldBeEquals;
	}

	@Parameters
	public static Iterable<Object[]> parameters() throws URISyntaxException {
		final List<Object[]> tests = new ArrayList<>();

		final Player ref = build("n1", "h", 42, 20);

		tests.add(new Object[] { ref, ref, true, "same" });
		tests.add(new Object[] { ref, null, false, "null" });
		tests.add(new Object[] { ref, build("n1", "h", 42, 20), true, "clone" });
		tests.add(new Object[] { ref, "truc", false, "pas même type" });

		// nickname
		tests.add(new Object[] { ref, build("n2", "h", 42, 20), false, "nick1" });
		tests.add(new Object[] { ref, build(null, "h", 42, 20), false, "nick2" });
		tests.add(new Object[] { build(null, "h", 42, 20), build("n1", "h", 42, 20), false, "nick3" });

		// host
		tests.add(new Object[] { ref, build("n1", "h2", 42, 20), false, "host1" });
		tests.add(new Object[] { ref, build("n1", null, 42, 20), false, "host2" });
		tests.add(new Object[] { build("n1", null, 42, 20), build("n1", "h", 42, 20), false, "host3" });

		// port
		tests.add(new Object[] { ref, build("n1", "h", 84, 20), false, "port1" });

		// score
		tests.add(new Object[] { ref, build("n1", "h", 42, 40), false, "uri1" });

		return tests;
	}

	private static Player build(String aNickname, String aHost, int aPort, Integer aScore) {
		final Player player = new Player(aNickname, aHost, aPort);
		if (aScore != null) {
			player.addScore(aScore);
		}
		return player;
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

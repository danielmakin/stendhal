package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import games.stendhal.client.MockStendhalClient;

import marauroa.common.game.RPAction;

import org.junit.BeforeClass;
import org.junit.Test;

public class AdminLevelActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Tests for executeOneParam.
	 */
	@Test
	public void testExecuteOneParam() {
		new MockStendhalClient("") {
			@Override
			public void send(final RPAction action) {
				client = null;
				assertEquals("adminlevel", action.get("type"));
				assertEquals("schnick", action.get("target"));
			}
		};
		final AdminLevelAction action = new AdminLevelAction();
		assertFalse(action.execute(null, null));
		assertTrue(action.execute(new String[] { "schnick" }, null));
	}

	/**
	 * Tests for executeSecondParamNull.
	 */
	@Test
	public void testExecuteSecondParamNull() {

		new MockStendhalClient("") {
			@Override
			public void send(final RPAction action) {
				client = null;
				assertEquals("adminlevel", action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertFalse(action.has("newlevel"));
			}
		};
		final AdminLevelAction action = new AdminLevelAction();
		assertFalse(action.execute(null, null));
		assertTrue(action.execute(new String[] { "schnick", null }, null));
	}

	/**
	 * Tests for executeSecondParamValid.
	 */
	@Test
	public void testExecuteSecondParamValid() {

		new MockStendhalClient("") {
			@Override
			public void send(final RPAction action) {
				client = null;
				assertEquals("adminlevel", action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertEquals("100", action.get("newlevel"));
			}
		};
		final AdminLevelAction action = new AdminLevelAction();
		assertFalse(action.execute(null, null));
		assertTrue(action.execute(new String[] { "schnick", "100" }, null));
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final AdminLevelAction action = new AdminLevelAction();
		assertThat(action.getMaximumParameters(), is(2));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final AdminLevelAction action = new AdminLevelAction();
		assertThat(action.getMinimumParameters(), is(1));
	}

}

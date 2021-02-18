package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Editor;
import main.Node;

/**
 * This class tests the functionality of the Editor linked list.
 * @author COSI21a 
 */
class ExampleEditorTests {

	/** Editor used for testing */
	Editor e;

	/**
	 * Before each test, the Editor is re-initialized
	 */
	@BeforeEach
	void reset() {
		e = new Editor();
	}

	/**
	 * Building on example movement no. 1 on page 4 on the assignment PDF, tests if
	 * the cursor can be properly moved from before 'b' to before 'l' to before 'u'.
	 */
	@Test
	void testMoveRightBasic() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// point cur at head
		e.cur = e.head;
		e.curPos = 0;

		// move cur right 2x
		e.moveRight();
		assertEquals(1, e.getCursorPosition());
		assertEquals('l', e.cur.data);
		e.moveRight();
		assertEquals(2, e.getCursorPosition());
		assertEquals('u', e.cur.data);
	}

	/**
	 * Building on example movement no. 2 on page 4 on the assignment PDF, tests if
	 * the cursor can be properly moved from before 'e' to before 'u' to before 'l'.
	 */
	@Test
	void testMoveLeftBasic() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// point cur at tail
		e.cur = e.tail;
		e.curPos = 3;

		// move cur left 2x
		e.moveLeft();
		assertEquals(2, e.getCursorPosition());
		assertEquals('u', e.cur.data);
		e.moveLeft();
		assertEquals(1, e.getCursorPosition());
		assertEquals('l', e.cur.data);
	}

	/**
	 * Using example movement no. 3 on page 4 on the assignment PDF, tests if the
	 * cursor can be properly moved from before 'u' to before 'b'.
	 */
	@Test
	void testMoveToHead() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// put cur at 'u'
		e.cur = e.head.next.next;
		e.curPos = 2;

		// move to head
		e.moveToHead();

		// cur should now be at 'b'
		assertEquals(0, e.getCursorPosition());
		assertEquals('b', e.cur.data);
	}

	/**
	 * Using example movement no. 4 on page 4 on the assignment PDF, tests if the
	 * cursor can be properly moved from before 'u' to after 'e'.
	 */
	@Test
	void testMoveToTail() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// put cur at 'u'
		e.cur = e.head.next.next;
		e.curPos = 2;

		// move to tail
		e.moveToTail();

		// cur should now be after 'e'
		assertEquals(4, e.getCursorPosition());
		assertNull(e.cur);
	}

	/**
	 * Builds on example insertions discussed on p. 5 and 6 of the assignment PDF,
	 * tests if the Editor properly inserts '$', '@', '%' after 'b'
	 */
	@Test
	void testMultipleInsertMiddle() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// point cur at 'l'
		e.cur = e.head.next;
		e.curPos = 1;

		// insert $, @, % to get "b$@lue"
		e.insert('$');
		e.insert('@');
		e.insert('%');
		assertEquals(4, e.getCursorPosition());
		assertEquals('l', e.cur.data);
		assertEquals("b$@%lue", e.toString());
	}

	/**
	 * Builds on example deletions discussed on p. 6 of the assignment PDF, tests if
	 * the Editor properly deletes 'l', then 'u', then 'e'.
	 */
	@Test
	void testDeleteMultipleMiddle() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// point cur at 'l'
		e.cur = e.head.next;
		e.curPos = 1;

		// delete 3x, cur moves to 'u', then 'e', then null
		e.delete();

		assertEquals('b', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(1, e.getCursorPosition());
		assertEquals(3, e.size());
		assertEquals('u', e.cur.data);

		e.delete();

		assertEquals('b', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(1, e.getCursorPosition());
		assertEquals(2, e.size());
		assertEquals('e', e.cur.data);

		e.delete();

		assertEquals('b', e.head.data);
		assertEquals('b', e.tail.data);
		assertEquals(1, e.getCursorPosition());
		assertEquals(1, e.size());
		assertNull(e.cur);
	}

	/**
	 * Builds on example backspaces discussed on p. 6 - 7 of the assignment PDF,
	 * tests if the Editor properly backspaces 'u', then 'l', then 'b'.
	 */
	@Test
	void testBackspaceMultipleMiddle() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// point cur after 'u'
		e.cur = e.tail;
		e.curPos = 3;

		// backspace 3x, curPos moves from 2, 1, 0
		e.backspace();

		assertEquals('b', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(2, e.getCursorPosition());
		assertEquals(3, e.size());
		assertEquals('e', e.cur.data);

		e.backspace();

		assertEquals('b', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(1, e.getCursorPosition());
		assertEquals(2, e.size());
		assertEquals('e', e.cur.data);

		e.backspace();

		assertEquals('e', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(0, e.getCursorPosition());
		assertEquals(1, e.size());
		assertEquals('e', e.cur.data);
	}

	/**
	 * Tests the toString() method described on page 7.
	 */
	@Test
	void testToString() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// check toString
		assertEquals("blue", e.toString());
	}

	/**
	 * Helper method that loads the characters 'b', 'l', 'u', and 'e' into an Editor
	 * object without using the insert() method. The cursor and cursor position are
	 * left at their default values (null, 0). numChars is updated to be 4.
	 * 
	 * @postcondition It is necessary that curPos, cur are set following the call of
	 *                this method. Otherwise, the Editor will be in an invalid
	 *                state.
	 * 
	 * @param editor an Editor to load with "blue".
	 */
	public static void loadEditorBLUE(Editor editor) {
		Node b = new Node('b');
		Node l = new Node('l');
		Node u = new Node('u');
		Node e = new Node('e');

		b.next = l;
		l.prev = b;
		l.next = u;
		u.prev = l;
		u.next = e;
		e.prev = u;

		editor.head = b;
		editor.tail = e;

		editor.numChars = 4;
	}
}

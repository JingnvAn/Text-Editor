package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.Stack;

/**
 * This class tests the functionality of the Stack.
 * @author COSI21a 
 */
class ExampleStackTests {

	/** Stack used for testing */
	Stack<Integer> s;

	/**
	 * Before each test, the Stack is re-initialized
	 */
	@BeforeEach
	void reset() {
		s = new Stack<Integer>();
	}

	/**
	 * Tests the state of the stack after a sequence of push and pop operations
	 */
	@Test
	void pushPopTest() {
		s.push(3);
		s.push(5);
		s.pop();
		s.push(7);
		assertFalse(s.isEmpty());
		assertEquals(s.top(),7);
		assertEquals(s.size(),2);
		assertEquals(s.pop(),7);
		assertEquals(s.size(),1);
		assertEquals(s.pop(),3);
		assertEquals(s.size(),0);
		assertTrue(s.isEmpty());
	}
	
	/**
	 * Tests the format of the toString of the stack
	 */
	@Test
	void toStringTest() {
		assertEquals(s.toString(),"");
		s.push(10);
		assertEquals(s.toString(),"10\n");
		s.push(11);
		assertEquals(s.toString(),"11\n10\n");
		s.pop();
		assertEquals(s.toString(),"10\n");
		s.pop();
		assertEquals(s.toString(),"");
	}

}

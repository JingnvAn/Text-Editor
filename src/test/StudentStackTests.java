/**
* Junit test for Stack class. Tests every methods in Stack.java
* Known Bugs: None 
*
* @author Jingnu An
* jingnuan@brandeis.edu
* Oct. 6, 2020
* COSI 21A PA1
*/

package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.Stack;

class StudentStackTests {
	Stack<String> s = new Stack();
	String[] testString = {"a","b","c","d","e","f", "g","h","i","j","k","l"}; //12 strings
	String[] testString2 = {"hello","how","you","doing","?"};
	String[] testString3 = {"lily","cheese","kitten","cub"};
	
	@Test
	void testConstructor() {
		Stack<String> stack = new Stack();
		
		assertTrue(stack.size() == 0);
		assertTrue(stack.isEmpty());
		assertTrue(stack.topIndex == -1);
		assertTrue(stack.cap == 5);
	}
	
	@Test
	void testPush() {
		//populate stack with 5 strings, within the initial capacity of 5
		for(int i=0; i<5; i++) { 
			s.push(testString[i]); 
		}
		assertTrue(s.topIndex == 4); //topIndex should be 5
		assertTrue(s.size() == 5);//after push, size should be updated
		assertTrue(s.cap == 5); //max capacity of the stack is updated to 10 instead of 5
		assertEquals("e",s.top()); //last one being added should be f
		assertEquals("e"+"\n" +"d"+"\n"+"c"+"\n"+"b"+"\n"+"a"+"\n", s.toString());
		
		//populate stack with 6 strings, exceeding the preset capacity of 5
		s = new Stack(); //reset stack
		for(int i=0; i<6; i++) {
			s.push(testString[i]); 
		}
		assertTrue(s.topIndex == 5); //topIndex should be 5
		assertTrue(s.size() == 6);//after push, size should be updated
		assertTrue(s.cap == 10); //max capacity of the stack is updated to 10 instead of 5
		assertEquals("f",s.top()); //last one being added should be f
		assertEquals("f"+"\n" +"e"+"\n"+"d"+"\n"+"c"+"\n"+"b"+"\n"+"a"+"\n", s.toString()); //toString should display 6 strings
		
		//populate stack with 12 strings, exceeding the preset capacity of 5
		s = new Stack(); //reset stack
		for(int i=0; i<12; i++) {
			s.push(testString[i]); 
		}
		assertTrue(s.topIndex == 11); //topIndex should be 11
		assertTrue(s.size() == 12);//after push, size should be updated to 12
		assertTrue(s.cap == 5*3); //max capacity of the stack is updated to 5*3 instead of 5
		assertEquals("l",s.top()); //last one being added should be l
		assertEquals("l\nk\nj\ni\nh\ng\n"+"f"+"\n" +"e"+"\n"+"d"+"\n"+"c"+"\n"+"b"+"\n"+"a"+"\n", s.toString());
	}
	
	@Test
	void testPop() {
		//pop an empty stack => throws exception
		assertThrows(IllegalStateException.class, ()-> {
			s.pop();
		});
		
		//populate stack with 5 strings
		for(int i=0; i<5; i++) {
			s.push(testString2[i]); 
		}
		//assert the first call of pop() is "?"
		String temp = s.pop(); 			//1st pop  ?
		assertEquals("?", temp);
		assertTrue(s.topIndex == 3); //topIndex should be stack size - 1
		assertTrue(s.size() == 4); //stack size decrease by 1
		assertTrue(s.cap == 5); //cap should not change
		
		s.pop(); 						//2nd pop => doing
		temp = s.pop(); 				//3rd pop => you
		assertEquals("you",temp);
		assertTrue(s.topIndex == 1); 
		assertTrue(s.size() == 2); 
		assertTrue(s.cap == 5); 
		
		s.pop(); 						//4th pop => how
		temp = s.pop(); 				//5th pop => hello
		assertEquals("hello", temp);
		assertTrue(s.topIndex == -1); //-1 indicates no elements in stack
		assertTrue(s.size() == 0); 
		assertTrue(s.cap == 5);
		
		//6th pop() => throws IllegalStateException
		assertThrows(IllegalStateException.class, ()-> {
			s.pop();
		});
		
	}
	
	@Test
	void testTop() {
		//top() an empty stack should return null
		assertEquals(null, s.top());
		
 		for(int i=0; i<4; i++) {
			s.push(testString3[i]); 
		}
		
		assertEquals("cub", s.top());
		s.pop();
		assertEquals("kitten", s.top());
		s.pop();
		assertEquals("cheese", s.top());
		s.pop();
		assertEquals("lily", s.top());
		s.pop();
		assertEquals(null, s.top());
		
		//test exceeding cap of 5
		for(int i=0; i<4; i++) {
			s.push(testString3[i]); 
		}
		for(int i=0; i<3; i++) {
			s.push(testString3[i]); 
		}	
		assertEquals("kitten", s.top());
	}
	
	@Test
	void testSize() {
		assertTrue(s.size() == 0); //empty stack
		assertTrue(s.cap == 5);    //initial cap (maximum capacity of the current stack) was set to 5
		
		for(int i=0; i<4; i++) {   //populate the stack with 4 elements
			s.push(testString3[i]);  
		}
		assertTrue(s.size() == 4); 
		assertTrue(s.cap == 5);	   //cap remains unchanged unless reaches capacity
		
		s.pop();
		assertTrue(s.size() == 3); //pop 1 out so there should be 3 elements left
		assertTrue(s.cap == 5);    //cap remains unchanged unless reaches capacity
		
		for(int i=0; i<4; i++) {	//add 4 more, exceeding initial cap of 5
			s.push(testString3[i]); 
		}
		assertTrue(s.size() == 7);	//stack should be resized, total elements should be 7
		assertTrue(s.cap == 10);	//resize to cap = 5*2 = 10
	}
	
	@Test
	void testIsEmpty() {
		assertTrue(s.isEmpty()); //test empty stack
		
		s.push("test test test"); //push one element
		assertFalse(s.isEmpty());
		
		for(int i=0; i<5; i++) { //push another 5 elements => exceeding cap
			s.push(testString2[i]); 
		}
		assertFalse(s.isEmpty());
		
		for(int i=0; i<6; i++) { //pop all 6 elements out
			s.pop(); 
		}
		assertTrue(s.isEmpty());
	}
	
	@Test
	void testToString() {
		//test empty stack
		assertEquals("", s.toString());
		
		//test within first cap ( first cap was set to be 5)
		for(int i=0; i<4; i++) {	//4 less than 5
			s.push(testString3[i]); 
		}
		assertEquals("cub\nkitten\ncheese\nlily\n", s.toString());
		
		//test cap
		s.push("theFifthWord");
		assertEquals("theFifthWord\ncub\nkitten\ncheese\nlily\n", s.toString());
		
		//test more than cap
		s.push("6th");
		s.push("7th");
		s.push("8th");
		assertEquals("8th\n7th\n6th\ntheFifthWord\ncub\nkitten\ncheese\nlily\n", s.toString());
		
		//test special characters, space and \n
		s = new Stack();
		s.push(" ");
		s.push("\n");
		assertEquals("\n\n \n", s.toString());
		
	}
}

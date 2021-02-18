/**
* Junit test for the Editor class. Tests every methods in Editor.java
* Known Bugs: None 
*
* @author Jingnu An
* jingnuan@brandeis.edu
* Oct. 6, 2020
* COSI 21A PA1
*/

package test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;
import main.Node;
import main.Stack;
import main.Editor;

class StudentEditorTests {
	Editor e;
	String letter = "abcdefghijklmnopqrstuvwxyz";
	String num = "0123456789";
	char space = ' ';
	char newLine = '\n';
	
	@Test
	void testConstructor1() {
		e = new Editor(); 
		assertEquals(0, e.numChars);
		assertEquals(0, e.curPos);
		assertEquals(null, e.head);
		assertEquals(null, e.cur);
		assertEquals(null, e.tail);
		assertTrue(e.savedVersions.size() == 0);
		assertTrue(e.savedVersions.isEmpty());
		assertEquals("",e.toString());
	}
	
	@Test	
	void testConstructor2() throws FileNotFoundException {
		//test constructor 2 with single line file
		e = new Editor("single_line_file.txt");
				   // 123456789012345678901234 -> helps me count total chars
		assertEquals("I am a single line file.",e.toString());
		assertEquals(24, e.numChars); //24 chars
		assertEquals(24, e.curPos); //after import, cursor is after the last char
		assertEquals('I', e.head.data);
		assertEquals(null, e.head.prev);
		assertEquals(null, e.cur);
		assertEquals('.', e.tail.data);
		assertEquals(null, e.tail.next);
		assertTrue(e.savedVersions.size() == 0); //savedVersion should be initialized, not null
		assertTrue(e.savedVersions.isEmpty());
		
		//test constructor 2 with multiple lines file
		e = new Editor("multi_line_file.txt");
		assertEquals(22, e.numChars); //24 chars
		assertEquals(22, e.curPos); //after import, cursor is after the last char
		assertEquals('I', e.head.data);
		assertEquals(null, e.head.prev);
		assertEquals(null, e.cur);
		assertEquals('.', e.tail.data);
		assertEquals(null, e.tail.next);
		assertTrue(e.savedVersions.size() == 0); //savedVersion should be initialized, not null
		assertTrue(e.savedVersions.isEmpty());
		 			//1234567 8901234567 89012  > helps me count total chars  
		assertEquals("I am a\nmultiline\nfile.", e.toString());
	}
	
	@Test
	void testSize() {
		//test empty editor
		e = new Editor();
		assertEquals(0, e.size());
		
		//test only \n
		for(int i=0; i<5; i++) { // insert a ~ z 
			e.insert('\n');
		}
		assertEquals(5, e.size());
		
		//test single lines
		e = new Editor(); // reset
		for(int i=0; i<26; i++) { // insert a ~ z 
			e.insert(letter.charAt(i));
		}
		assertEquals(26, e.size());
		
		//test multi lines
		e.insert('\n');
		for(int i=0; i<26; i++) { // insert a ~ z 
			e.insert(letter.charAt(i));
		}
		assertEquals(53, e.size()); // 26 + 26 + \n = 52 + 1 = 53
	}
	
	@Test
	void testGetCursorPosition() throws FileNotFoundException {
		e = new Editor();
		
		//test cursor on an empty editor, and compatibility with move methods
		assertEquals(0, e.getCursorPosition());
		e.moveLeft();
		assertEquals(0, e.getCursorPosition());
		e.moveRight();
		assertEquals(0, e.getCursorPosition());
		e.moveToHead();
		assertEquals(0, e.getCursorPosition());
		e.moveToTail();
		assertEquals(0, e.getCursorPosition());
		
		//test single line insert
		for(int i=0; i<10; i++) { // insert 0 ~ 9
			e.insert(num.charAt(i));
		}
		assertEquals(10, e.getCursorPosition());
		
		//test multi line insert
		e = new Editor();
		e.insert(newLine);
		for(int i=0; i<5; i++) { // insert 0 ~ 4
			e.insert(num.charAt(i));
		}
		e.insert(newLine);
		for(int i=0; i<5; i++) { // insert 0 ~ 4
			e.insert(num.charAt(i));
		}
		assertEquals(12, e.getCursorPosition()); // 1 + 5 + 1 + 5 = 12
		
		//test single line import
		e = new Editor("single_line_file.txt");
		assertEquals(24, e.getCursorPosition());
		
		//test multi line import
		e = new Editor("multi_line_file.txt");
		assertEquals(22, e.getCursorPosition());
	}
	
	@Test
	void testMove() {
		e = new Editor();
		
		//test moveX() on empty editor
		e.moveLeft();
		assertEquals(0, e.getCursorPosition());
		assertEquals(null, e.cur);
		e.moveRight();
		assertEquals(0, e.getCursorPosition());
		assertEquals(null, e.cur);
		e.moveToHead();
		assertEquals(0, e.getCursorPosition());
		assertEquals(null, e.cur);
		e.moveToTail();
		assertEquals(0, e.getCursorPosition());
		assertEquals(null, e.cur);
		
		//test moveToHead()
		for(int i=0; i<10; i++) { // insert 0 ~ 9
			e.insert(num.charAt(i));
		}
		e.moveToHead();
		assertEquals(0, e.getCursorPosition());
		assertEquals('0', e.cur.data);
		
		//test moveToTail()
		e.moveToTail();
		assertEquals(10, e.getCursorPosition());
		assertEquals(null, e.cur);
		
		//test moveLeft()
		e.moveLeft();
		assertEquals(9, e.getCursorPosition());
		assertEquals('9', e.cur.data);
		
		//test moveLeft() multiple times
		e.moveLeft(); 
		e.moveLeft();
		e.moveLeft();
		assertEquals(6, e.getCursorPosition());
		assertEquals('6', e.cur.data);
		
		for(int i=0; i<6; i++) {
			e.moveLeft();
		}
		assertEquals(0, e.getCursorPosition());
		assertEquals('0', e.cur.data);
		
		e.moveLeft(); //user tries to move left when curPos = 0
		assertEquals(0, e.getCursorPosition());
		assertEquals('0', e.cur.data);
		
		//test moveRight() multiple times
		e.moveRight();
		assertEquals(1, e.getCursorPosition());
		assertEquals('1', e.cur.data);
		
		for(int i=0; i<5; i++) {
			e.moveRight();
		}
		assertEquals(6, e.getCursorPosition());
		assertEquals('6', e.cur.data);
		
		for(int i=0; i<4; i++) {
			e.moveRight();
		}
		assertEquals(10, e.getCursorPosition());
		assertEquals(null, e.cur);
		
		e.moveRight();
		e.moveRight(); //user tries to move right when curPos = numChars
		assertEquals(10, e.getCursorPosition());
		assertEquals(null, e.cur);
	}
	
	@Test
	void testInsert() {
		e = new Editor();
		
		//insert in an empty editor
		e.insert('a');
		e.insert('b');
		e.insert('c');
		assertEquals('a', e.head.data);
		assertEquals('c', e.tail.data);
		assertEquals(null, e.cur);
		assertEquals(3, e.numChars);
		assertEquals(3, e.curPos);
		assertEquals("abc", e.toString());
		
		//insert in the beginning 
		e.moveToHead();
		e.insert('1');   //1abc
		assertEquals('1', e.head.data);
		assertEquals('c', e.tail.data);
		assertEquals('a', e.cur.data);
		assertEquals(1, e.curPos);
		assertEquals("1abc", e.toString());
		
		//insert in the middle multiple times
		e.moveRight();
		e.insert('2');
		e.insert('3');
		e.insert('4'); //1a234bc
		assertEquals('1', e.head.data);
		assertEquals('c', e.tail.data);
		assertEquals('b', e.cur.data);
		assertEquals(5, e.curPos);
		assertEquals("1a234bc", e.toString());
		
		//insert at the end
		e.moveToTail();
		e.insert('d');  //1a234bcd
		assertEquals('1', e.head.data);
		assertEquals('d', e.tail.data);
		assertEquals(null, e.cur);
		assertEquals(8, e.curPos);
		assertEquals("1a234bcd", e.toString());
		
		
		//insert until internal array is at capacity: 5
		e = new Editor();
		for(int i=0; i<5; i++) { // insert a ~ e
			e.insert(letter.charAt(i));
		}
		assertEquals('a', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(null, e.cur);
		assertEquals(5, e.curPos);
		assertEquals("abcde", e.toString());
		
		//insert when the internal array is already at capacity (i=5, =6, =7 are out of bound)
		e.clear();
		for(int i=0; i<5; i++) { // insert a ~ e and save every char
			e.insert(letter.charAt(i));
			e.save();
		}
		for(int i=0; i<3; i++) { // insert a ~ c and save every char
			e.insert(letter.charAt(i));
			e.save();
		}
		assertEquals('a', e.head.data); 
		assertEquals('c', e.tail.data);
		assertEquals(null, e.cur);
		assertEquals(8, e.curPos);
		assertEquals("abcdeabc", e.toString());
		assertEquals(10, e.savedVersions.cap); //internal array resied to 5*2
		assertEquals(8, e.savedVersions.size()); //elements in the internal array is 8
	}
	
	@Test
	void testDelete() {
		e = new Editor();
		
		//test delete on an empty editor
		e.delete();
		assertEquals(0, e.getCursorPosition());
		assertEquals(0, e.size());
		assertEquals(null, e.head);
		assertEquals(null, e.cur);
		assertEquals(null, e.tail);
		assertEquals(0, e.savedVersions.size());
		assertEquals("", e.toString());		

		//delete when cursor is right after the last char
		for(int i=0; i<5; i++) { // insert a ~ e
			e.insert(letter.charAt(i));
		}
		e.delete();
		assertEquals(5, e.getCursorPosition());
		assertEquals(null,  e.cur);
		assertEquals('a', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(5, e.size());
		assertEquals("abcde", e.toString());
		
		//delete the last node in the linkedlist => last char in the editor
		e = new Editor();
		for(int i=0; i<5; i++) { // insert a ~ e
			e.insert(letter.charAt(i));
		}
		e.moveLeft(); //cur now points to 'e'
		e.delete(); //abcd
		assertEquals(4, e.getCursorPosition());
		assertEquals(null,  e.cur);
		assertEquals('a', e.head.data);
		assertEquals('d', e.tail.data);
		assertEquals(4, e.size());
		assertEquals("abcd", e.toString());
		
		
		//delete the first node in the linkedlist
		e.moveToHead();
		e.delete(); //bcd
		assertEquals(0, e.getCursorPosition());
		assertEquals('b',  e.cur.data);
		assertEquals('b', e.head.data);
		assertEquals('d', e.tail.data);
		assertEquals(3, e.size());
		assertEquals("bcd", e.toString());
		
		e.delete(); //cd
		e.delete(); //d	
		
		//delete the one and only node in the editor
		e.delete();
		assertEquals(0, e.getCursorPosition());
		assertEquals(null,  e.cur);
		assertEquals(null, e.head);
		assertEquals(null, e.tail);
		assertEquals(0, e.size());
		assertEquals("", e.toString());

		
		//delete a middle node  x2
		e = new Editor();
		for(int i=0; i<5; i++) { // insert a ~ e
			e.insert(letter.charAt(i));
		}
		e.moveToHead();
		e.moveRight(); //a_bcde _ indicates where the cursor is
		e.moveRight(); //ab_cde
		e.delete();
		assertEquals('d', e.cur.data);
		assertEquals(2, e.getCursorPosition());
		assertEquals('a', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(4, e.size());
		assertEquals("abde", e.toString());
		e.delete();
		assertEquals('e', e.cur.data);
		assertEquals(2, e.getCursorPosition());
		assertEquals('a', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(3, e.size());
		assertEquals("abe", e.toString());
		
	}
	
	@Test
	void testBackSpace() {
		e = new Editor();
		
		//test backspace in an empty editor
		e.backspace();
		assertEquals(0, e.getCursorPosition());
		assertEquals(0, e.size());
		assertEquals(null, e.head);
		assertEquals(null, e.cur);
		assertEquals(null, e.tail);
		assertEquals(0, e.savedVersions.size());
		assertEquals("", e.toString());		
		
		//test backspace the last node in the linkedlist => last char in the editor
		for(int i=0; i<5; i++) { // insert a ~ e
			e.insert(letter.charAt(i));
		}
		e.backspace();
		assertEquals(4, e.getCursorPosition());
		assertEquals(null,  e.cur);
		assertEquals('a', e.head.data);
		assertEquals('d', e.tail.data);
		assertEquals(4, e.size());
		assertEquals("abcd", e.toString());
		
		//test backSpace when cursor is at the front, curPos = 0
		e.moveToHead();
		e.backspace();
		assertEquals(0, e.getCursorPosition());
		assertEquals('a',  e.cur.data);
		assertEquals('a', e.head.data);
		assertEquals('d', e.tail.data);
		assertEquals(4, e.size());
		assertEquals("abcd", e.toString());
		
		//test backspace the first node in the linkedlist => first char in the editor
		e.moveRight();
		e.backspace();
		assertEquals(0, e.getCursorPosition());
		assertEquals('b',  e.cur.data);
		assertEquals('b', e.head.data);
		assertEquals('d', e.tail.data);
		assertEquals(3, e.size());
		assertEquals("bcd", e.toString());
		
		//test backspace middle node  x3
		e = new Editor();
		for(int i=0; i<7; i++) { // insert a ~ g
			e.insert(letter.charAt(i));
		}
		e.moveToHead();
		e.moveRight(); 
		e.moveRight();
		e.moveRight();
		e.moveRight(); //abcd_efg
		e.backspace();
		assertEquals(3, e.getCursorPosition());
		assertEquals('e',  e.cur.data);
		assertEquals('a', e.head.data);
		assertEquals('g', e.tail.data);
		assertEquals(6, e.size());
		assertEquals("abcefg", e.toString());
		
		e.backspace();
		assertEquals(2, e.getCursorPosition());
		assertEquals('e',  e.cur.data);
		assertEquals('a', e.head.data);
		assertEquals('g', e.tail.data);
		assertEquals(5, e.size());
		assertEquals("abefg", e.toString());
		
		e.backspace();
		assertEquals(1, e.getCursorPosition());
		assertEquals('e',  e.cur.data);
		assertEquals('a', e.head.data);
		assertEquals('g', e.tail.data);
		assertEquals(4, e.size());
		assertEquals("aefg", e.toString());
		
	}
	
	@Test
	void testSave(){
		e = new Editor();
		
		//call save() on an empty editor
		e.save(); 
		assertFalse(e.savedVersions.isEmpty());//should be saved although nothing has been typed in
		assertTrue(e.savedVersions.size() == 1);
		assertEquals("", e.savedVersions.top());
		
		//test duplicate empty save
		e.save();
		assertFalse(e.savedVersions.isEmpty());
		assertTrue(e.savedVersions.size() == 1);
		assertEquals("", e.savedVersions.top());
		
		//test single word, single line
		e = new Editor(); //resest
		for(int i=0; i<5; i++) { // insert a ~ e with space in between
			e.insert(letter.charAt(i));
			if(i != 4)
				e.insert(space);
		}
		assertTrue(e.savedVersions.size() == 0); //ensure not saved before calling the save method
		e.save();	
		assertTrue(e.savedVersions.size() == 1); //ensure its saved once save() is called
		assertEquals("a b c d e", e.savedVersions.top());//ensure it saved a correct version
		
		//test duplicate save
		e.save();
		e.save(); 
		assertTrue(e.savedVersions.size() == 1); 
		assertEquals("a b c d e", e.savedVersions.top());
		
		//insert the exact same chars in, see if this will be correctly saved 
		for(int i=0; i<5; i++) { // insert a ~ e with space in between
			e.insert(letter.charAt(i));
			if(i != 4)
				e.insert(space);
		}
		
		//even though the second "a b c d e" is the same from the first "a b c d e"
		//the user indeed added more chars, so should be saved
		e.save();
		assertTrue(e.savedVersions.size() == 2);
		assertEquals("a b c d ea b c d e", e.savedVersions.top());
		assertEquals("a b c d ea b c d e\na b c d e\n", e.savedVersions.toString());
		
		//insert more chars to the editor
		//test multi words, multi lines on save() function
		e.insert('\n');
		for(int i=0; i<5; i++) { // insert 0 ~ 4 with space in between
			e.insert(num.charAt(i));
			if(i != 4)
				e.insert(space);
		}
		
		e.save();
		assertTrue(e.savedVersions.size() == 3);
		assertEquals("a b c d ea b c d e\n0 1 2 3 4\na b c d ea b c d e\na b c d e\n",e.savedVersions.toString());
		assertEquals("a b c d ea b c d e\n0 1 2 3 4", e.savedVersions.top()); 
		
		
	}
	
	
	@Test
	void testToString() throws FileNotFoundException {
		e = new Editor();
		//test empty
		assertEquals("", e.toString());
		
		//test compatibility with import files
		//test multi lines
		e = new Editor("multi_line_file.txt");
		assertEquals("I am a\nmultiline\nfile.", e.toString());
		
		//test compatibility with typing chars
		//single line
		e = new Editor();
		e.insert('x');
		e.insert('y');
		e.insert('z');
		assertEquals("xyz", e.toString());
		
		e.clear();
		e.insert(' ');
		e.insert('x');
		e.insert('y');
		e.insert('z');
		assertEquals(" xyz", e.toString());
		
		//multi lines
		e.clear();
		e.insert(' ');
		e.insert('x');
		e.insert('y');
		e.insert('z');
		e.insert('\n');
		e.insert('a');
		e.insert('b');
		e.insert('c');
		assertEquals(" xyz\nabc", e.toString());
		
		//test Delete and BackSpcae with toString
		e.backspace();
		assertEquals(" xyz\nab", e.toString());
		e.moveToHead();
		e.delete();
		assertEquals("xyz\nab", e.toString());
	}
	
	@Test
	void testClear() {
		e = new Editor();
		
		//test empty
		e.clear();
		assertTrue(0 == e.curPos);
		assertEquals(null, e.cur);
		assertEquals(null, e.head);
		assertEquals(null, e.tail);
		assertEquals(0, e.numChars);
		assertEquals("", e.toString());
		
		//test single line
		for(int i=0; i<7; i++) { // insert a ~ g
			e.insert(letter.charAt(i));
		}
		e.clear();
		assertTrue(0 == e.curPos);
		assertEquals(null, e.cur);
		assertEquals(null, e.head);
		assertEquals(null, e.tail);
		assertEquals(0, e.numChars);
		assertEquals("", e.toString());
		
	}
	
	@Test
	void testUndo() {
		e = new Editor();
		
		//test undo empty
		e.undo();
		assertTrue(0 == e.curPos);
		assertEquals(null, e.cur);
		assertEquals(null, e.head);
		assertEquals(null, e.tail);
		assertEquals(0, e.numChars);
		assertEquals("", e.toString());
		assertEquals(0, e.savedVersions.size());
		
		//undo multiple times until all saved versions are used up
		for(int i=0; i<7; i++) { // insert a ~ g, save each char
			e.insert(letter.charAt(i));
			e.save();
		}
		e.insert('h'); //abcdefgh
		e.undo();      //abcdefg -> editor view
		assertEquals("abcdefg", e.toString());
		assertEquals(7, e.curPos); //cursor should be after the last character in the editor, after the contents have been updated with the most recently saved version
		assertEquals("abcdef", e.savedVersions.top()); //g has been undo out, so the most recent saved version is "abcdef"
		e.undo(); //abcdef -> editor view
		e.undo(); //abcde
		e.undo(); //abcd
		assertEquals("abcd", e.toString());
		assertEquals(4, e.curPos);
		assertEquals("abc", e.savedVersions.top());
		e.undo(); //abc
		e.undo(); //ab
		e.undo(); //a
		assertEquals("a", e.toString());
		assertEquals(1, e.curPos);
		assertEquals(null, e.savedVersions.top()); //all records of saved versions are popped out
		
		//test undo on an empty savedVersions stack
		e.undo();
		assertEquals("a", e.toString()); //"a" is the first saved version. There are no saved version in the stack, so the call of the undo method is ignored
		assertEquals(1, e.curPos);
		
		//test more undo on an empty savedVersion stack
		e.undo(); 
		e.undo(); 
		assertEquals("a", e.toString()); //further call of undo is ignored as well
		assertEquals(1, e.curPos);
	}
	
	@Test
	void testExport() throws FileNotFoundException {
		e = new Editor();
		
		e = new Editor();		
		//export empty 
		e.export("result.txt");
		e = new Editor("result.txt");
		assertEquals("", e.toString());
		
		//test multi chars, single line
		for(int i=0; i<7; i++) { // insert a ~ g
			e.insert(letter.charAt(i));
		}
		e.export("result.txt");
		e = new Editor("result.txt");
		assertEquals("abcdefg", e.toString());
		
		//test multi chars, multi line
		e.clear();
		for(int i=0; i<7; i++) { // insert a ~ g
			e.insert(letter.charAt(i));
			e.insert('\n');
		}
		assertEquals("a\nb\nc\nd\ne\nf\ng\n", e.toString());
		e.export("result.txt");
		e = new Editor("result.txt");	
		assertEquals('g', e.tail.data); // I've asked Katie how to deal with the loss of the last '\n' and she acknowledged that we can ignore this issue
		assertEquals(13, e.numChars);   //To fix this issue I'd be using scanenr.useDelimiter() method. I've included the code from line 51 to line 56 in Editor.java file
		assertEquals("a\nb\nc\nd\ne\nf\ng", e.toString());
		
	}
	
}

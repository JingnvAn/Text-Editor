/**
* This class defines and creates an Editor object
* Known Bugs: None 
*
* @author Jingnu An
* jingnuan@brandeis.edu
* Oct. 6, 2020
* COSI 21A PA1
*/

package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;
public class Editor {
	
	public int numChars; /** KEEP THIS PUBLIC : use this to store the number of characters in your Editor */
	public int curPos; /** KEEP THIS PUBLIC : use this to store the current cursor index in [0, numChars] */
	
	public Node cur; /** KEEP THIS PUBLIC : use this to reference the node that is after the visual cursor or null if curPos = numChars */
	public Node head; /** KEEP THIS PUBLIC : use this to reference the first node in the Editor's doubly linked list */
	public Node tail; /** KEEP THIS PUBLIC : use this to reference the last node in the Editor's doubly linked list */
	
	public Stack<String> savedVersions; /** KEEP THIS PUBLIC : use this to store the contents of the editor that are saved by the user */
	
	/**
	 * Constructor 1, creates an empty Editor
	 * Runtime: O(1)
	 */
	public Editor() {
		numChars = 0;
		curPos = 0;
		savedVersions = new Stack<String>();
		head = null;
		cur = null;
		tail = null;
	}
	
	/**
	 * Constructor 2, creates an Editor and loads in external file designated by the user
	 * @param filepath A string representation of the name of the file to load in to our Editor
	 * @throws FileNotFoundException If file is not found, the method throws an exception
	 * Runtime: O(n)
	 */
	public Editor(String filepath) throws FileNotFoundException {
		savedVersions = new Stack<String>();
		File f = new File(filepath);
		Scanner s = new Scanner(f);
		String temp = "";
		
		while(s.hasNextLine()) {   		//m, (m line, p chars, input size n=m*p)
			temp = s.nextLine();		//1
			for(int i=0; i<temp.length(); i++) {  //p*m
				insert(temp.charAt(i));   //p*m
			}
			if(s.hasNextLine())  //m-1
				insert('\n');	//m-1
		}
		
		//alternative solution
		//if the user inserts a new line at the end, scanner would not read the '\n' char, so set the token process delimiter as the entire document
//		temp = s.useDelimiter("\\Z").next();//take the entire doc, including space, and \n
//		for(int i=0; i<temp.length();i++) {
//			insert(temp.charAt(i));
//		}					
// I found the syntax rules for .useDelimiter from java api from oracle: https://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html#lt			
	}
	
	/**
	 * This method returns current position of the cursor in the Editor
	 * @return an integer representing current position of the cursor
	 * runtime: O(1)
	 */
	public int getCursorPosition() {
		return curPos;
	}
	
	/**
	 * This method returns total number of characters currently in the Editor
	 * @return an integer indicating the total number of characters in the Editor
	 * runtime: O(1)
	 */
	public int size() {
		return numChars;
	}
	
	/**
	 * The method moves cursor one step to the right. 
	 * If the cursor is already at the right most position, 
	 * calling this method would result in an unchanged cursor 
	 * runtime: O(1)
	 */
	public void moveRight() {
		if(curPos < numChars) {
			curPos++;
			cur = cur.next;
		}
	}
	
	/**
	 * The method moves cursor one step to the left. 
	 * If the cursor is already at the left most position, 
	 * calling this method would result in an unchanged cursor 
	 * runtime: O(1)
	 */
	public void moveLeft() {
		if(curPos > 0) {
			curPos--;
			if(cur != null) { //cursor at the end
				cur = cur.prev;
			}else {
				cur = tail;
			}
		}
	}
	
	/**
	 * The method moves cursor to the beginning of the text. 
	 * If the cursor is already at the beginning of the text, 
	 * calling this method would result in an unchanged cursor 
	 * runtime: O(1)
	 */
	public void moveToHead() {
		curPos = 0;
		cur = head;
	}
	
	/**
	 * The method moves cursor to the very end of the text. 
	 * If the cursor is already at the very end of the text,
	 * calling this method would result in an unchanged cursor 
	 * runtime: O(1)
	 */
	public void moveToTail() {
		curPos = numChars;
		cur = null;
	}
	
	/**
	 * This method inserts a character in the Editor
	 * @param c a character to be inserted in th Editor
	 * runtime: O(1)
	 */
	public void insert(char c) { 
		Node toInsert = new Node(c);
	
		if(numChars == 0) { //null linkedlist
			head = toInsert;
			tail = toInsert;
		}else {
			toInsert.next = cur;
			
			if(cur == null) { //insert at the end
				tail.next = toInsert;
				toInsert.prev = tail;
				tail = toInsert;
				
			}else if(cur.prev != null) {  //insert into the middle
				toInsert.prev = cur.prev;
				cur.prev.next = toInsert;
				cur.prev = toInsert;
				
			}else {  //insert at the beginning
				toInsert.prev = null;
				cur.prev = toInsert;
				head = toInsert;
			}
		}
		numChars++;
		curPos++;
	}
	
	/**
	 * This method deletes the character that is to the right of the cursor
	 * If there is no character to the right of the cursor
	 * calling this method does not delete anything
	 * runtime: O(1)
	 */
	public void delete() {
		if(numChars != 0 && cur != null) { //there are at least 3 nodes and cur is not null
			if(cur.next != null && cur.prev != null) {//prev, cur, next, all exist
				cur.next.prev = cur.prev;
				cur.prev.next = cur.next;
				cur = cur.next;
			}else if(cur.next == null && cur.prev != null){ //prev and cur exist, next is null
				tail = cur.prev; //update tail since deleted the previous last char		
				cur.prev.next = null;
				cur = null;
			}else if(cur.next != null && cur.prev == null) {//cur and next exists, prev is null
				cur = cur.next;
				cur.prev = null;
				head = cur; //update head since deleted the previous first char
			}else if(cur.next == null && cur.prev == null) {//only cur is not null, one node in the ll
				head = null; //update head
				tail = null; //update tail
				cur = null;
			}
			numChars--;
		}
	}
	
	/**
	 * This method deletes the character that is to the left of the cursor
	 * If there is no character to the left of the cursor
	 * calling this method does not delete anything
	 * runtime: O(1)
	 */
	public void backspace() {
		if(numChars != 0 && cur != null) {
			if(cur.prev != null && cur.prev.prev != null) {//at least 2 nodes exist before cur
				cur.prev.prev.next = cur;
				cur.prev = cur.prev.prev;
				curPos--;
				numChars--;
			}else if(cur.prev != null && cur.prev.prev == null) { //only one node before cur
				cur.prev = null;
				head = cur;
				curPos--;
				numChars--;
			}
		}else if(curPos == numChars && numChars > 1) {//delete the last node of a size > 1 ll, cur is null
			tail = tail.prev;
			tail.next = null;
			curPos--;
			numChars--;
		}else if(numChars == 1 && head == tail) { //delete the one and only node in the ll
			head = null;
			tail = null;
			curPos = 0;
			numChars = 0;
		}
	}
	
	/**
	 * This method returns a string representation of what is currently in the Editor
	 * @return res a string representation of what is currently in the Editor
	 * runtime: O(n)
	 */
	public String toString() {
		Node temp = head;
		String res = "";
		while(temp != null) {
			res += temp.data;
			temp = temp.next;
		}	
		return res;
	}
	
	/**
	 * This method clears the Editor. So after calling this method, the Editor will be empty
	 * runtime: O(1)
	 */
	public void clear() {
		numChars = 0;
		curPos = 0;
		head = null;
		cur = null;
		tail = null;
	}
	
	/**
	 * This method exports the contents of the text editor to a file at the provided save path
	 * @param savepath a string representation of the save path
	 * @throws FileNotFoundException if the file cannot be accessed, this method throws an exception
	 * runtime: O(n)
	 */
	public void export(String savepath) throws FileNotFoundException {
		PrintStream output = new PrintStream(new File(savepath));
		output.print(toString());
	}
	
	/**
	 * This method saved the string of characters currently in the text editor
	 * runtime: O(n)
	 */
	public void save() {
		if(savedVersions.isEmpty()) { //empty stack, push no problem
			savedVersions.push(toString());
		}else if(!toString().equals(savedVersions.top())) {
			savedVersions.push(toString());
		}
	}
	
	/**
	 * This method reverts the contents of the text editor to the most recently saved version
	 * If there is no saved versions, this method would throw an exception
	 * runtime: O(n)
	 */
	public void undo() {
		if(!savedVersions.isEmpty()) {
			clear();
			String showUp = savedVersions.pop();
			for(int i=0; i<showUp.length(); i++) {
				insert(showUp.charAt(i));
			}
			moveToTail();
		}
	}
	
}

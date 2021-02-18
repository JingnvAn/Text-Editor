package main;

/**
 * This class represents a Node storing a character. 
 * @author COSI21a
 */
public class Node {
	
	/**
	 * pointer to next Node
	 */
	public Node next;
	/**
	 * pointer to previous Node
	 */
	public Node prev;
	/**
	 * data stored in this Node
	 */
	public char data;
	
	/**
	 * constructor, builds new Node containing given character
	 * @param c - char to be stored in this Node
	 */
	public Node(char c) {
		this.next = null;
		this.prev = null;
		this.data = c;
	}
}

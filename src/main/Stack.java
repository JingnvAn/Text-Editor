/**
* This class defines and creates a Stack
* Known Bugs: None 
*
* @author Jingnu An
* jingnuan@brandeis.edu
* Oct. 6, 2020
* COSI 21A PA1
*/

package main;
public class Stack<T> {

	public T[] stack;
	public int topIndex; //this keeps track of where the top element is in the array
	public int cap;
	@SuppressWarnings("unchecked")
	
	/**
	 * Constructor, creates a Stack object and initializes fields in the Stack class
	 * runtime: O(1)
	 */
	public Stack() {
		// uncomment this line and replace <your initial size goes here> with the initial capacity of your internal array
		cap = 5;
		this.stack = (T[]) new Object[cap];
		topIndex = -1;
	}
	
	/**
	 * Pushes an element on top of the stack
	 * @param x the element to be pushed on to the stack
	 * runtime: O(n)
	 */
	public void push(T x) {
		topIndex++;
		if(topIndex == cap) { //full
			T[] stackTemp = (T[]) new Object[cap+5];	//resize, create a new stack
			for(int i=0; i<cap; i++) {
				stackTemp[i] = stack[i];
			}
			stack = stackTemp;
			stack[topIndex] = x;
			cap += 5;
		}else {//within bound, just put it in
			stack[topIndex] = x;
		}
	}
	
	/**
	 * Removes and returns the top elements from the stack.
	 * @return the element to be removed and returned
	 * runtime: O(1)
	 */
	public T pop() {
		T res;
		if(topIndex+1 == 0) { //nothing in stack
			throw new IllegalStateException("Stack is empty");
		}else {
			res = stack[topIndex]; //store the top to res
			stack[topIndex] = null; //empty the top
			topIndex--; //update topIndex
		}
		return res;
	}
	
	/**
	 * Returns the element that is at the top of the Stack
	 * @return the element that is at the top of the Stack
	 * runtime: O(1)
	 */
	public T top() {
		if(topIndex+1 == 0) { //nothing in stack
			return null;
		}else {
			return stack[topIndex];
		}
	}
	
	/**
	 * Returns the number of elements in the Stack
	 * @return an integer indicating the number of elements in the Stack.
	 * runtime: O(1)
	 */
	public int size() {	
		return topIndex + 1;
	}
	
	/**
	 * Returns a boolean corresponding to whether or not this Stack is empty.
	 * @return a boolean corresponding to whether or not this Stack is empty.
	 * runtime: O(1)
	 */
	public boolean isEmpty() {
		if(topIndex + 1 == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Returns a string representation of the contents of this Stack.
	 * @return a string representation of the contents of this Stack.
	 * runtime: O(n)
	 */
	public String toString() {
		String res = "";
		for(int j=topIndex; j>=0; j--) {
			res += stack[j] + "\n";
		}
		return res;
	}
	
}

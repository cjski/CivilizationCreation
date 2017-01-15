import java.util.ArrayList;
import java.util.List;
/**
 * @Program Implementation of stack data structure - limited access list
 * @author Chris Janowski
 * @course ICS4U
 * @date May 09 2016
 */
public class Stack {
	
	private NodeList stack;
	private boolean isEmpty;
	
	/**
	 * Default Constructor, creates list and sets variables
	 */
	public Stack(){
		
		this.isEmpty = true;
		this.stack = new NodeList();
	}
	
	/**
	 * Adds an item to the top of the stack
	 * @param item
	 */
	public void push(Object item){
		
		//The first item in the list is set to the new item added
		this.stack.add(item,1);
		this.isEmpty = false;
		
	}
	
	/**
	 * Removing an item from the top of the stack
	 */
	public void pop(){
		
		//If the stack is not empty, will continue with removal
		if(!this.isEmpty){
					
			//Removes the first item in the list, shifting each remaining index in the stack down by one and moving them closer to the top
			this.stack.remove(1);
					
			//If the stack has no more elements, it is set to empty
			if(this.stack.getSize() < 1){
				this.isEmpty = true;
			}
		}
		//If stack is empty prints a warning
		else{
			System.out.println(this+" is empty, cannot remove an item");
		}
	}
	
	/**
	 * Allows the user to see which item is next in the stack
	 * @return Object at the top of the stack
	 */
	public Object peek(){
		return this.stack.get(1);
	}
	
	/**
	 * Accessor method for if the stack is empty
	 * @return if the stack is empty or not
	 */
	public boolean isEmpty(){
		return this.isEmpty;
	}
	
	/**
	 * Accessor method for size of the stack
	 * @return size
	 */
	public int getSize(){
		
		//Returns the size of the NodeList holding the stack data
		return this.stack.getSize();
	}
	
	/**
	 * Converts the stack to a readable string to print out
	 * @return variables in queue as stack
	 */
	public String toString(){
		
		//Returns the string version of the NodeList
		return this.stack.toString();
	}
}

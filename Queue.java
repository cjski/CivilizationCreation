import java.util.ArrayList;
import java.util.List;
/**
 * @Program Implementation of queue data structure - limited access list
 * @author Chris Janowski
 * @course ICS4U
 * @date May 09 2016
 */
public class Queue {
	
	private NodeList queue;
	private boolean isEmpty;
	
	/**
	 * Default Constructor, creates list and sets variables
	 */
	public Queue(){
		
		this.isEmpty = true;
		this.queue = new NodeList();
	}
	
	/**
	 * Adds an item to the end of the queue
	 * @param item
	 */
	public void enqueue(Object item){
		
		//Adds the item to the end of the list
		this.queue.add(item);
		this.isEmpty = false;
		
	}
	/**
	 * Removing an item from the front of the queue
	 */
	public void dequeue(){
		
		//If the queue is not empty, will continue with removal
		if(!this.isEmpty){
			
			//Removes the first item in the list, shifting each remaining index in the queue down by one and moving them closer to the front
			this.queue.remove(1);
			
			//If the queue has no more elements, it is set to empty
			if(this.queue.getSize() < 1){
				this.isEmpty = true;
			}
		}
		//If queue is empty prints a warning
		else{
			System.out.println(this+" is empty, cannot remove an item");
		}
	}
	
	/**
	 * Allows the user to see which item is next in the queue
	 * @return Object at the front of the queue
	 */
	public Object peek(){
		return this.queue.get(1);
	}
	
	/**
	 * Accessor method for if the queue is empty
	 * @return if the queue is empty or not
	 */
	public boolean isEmpty(){
		return this.isEmpty;
	}
	
	/**
	 * Accessor method for size of the queue
	 * @return size
	 */
	public int getSize(){
		
		//Returns the size of the NodeList holding the stack data
		return this.queue.getSize();
	}
	
	/**
	 * Converts the queue to a readable string to print out
	 * @return variables in queue as string
	 */
	public String toString(){
		
		//Returns the string version of the NodeList
		return this.queue.toString();
	}
}

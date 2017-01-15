/**
 * @Program A functioning linked-list data structure
 * @author Chris Janowski
 * @Course ICS4U
 * @date May 09 2016
 *
 */
public class NodeList {
	
	private Node firstNode;
	private int size;
	
	/**
	 * Default constructor, sets the NodeList to be empty
	 */
	NodeList(){
		this.firstNode = null;
		this.size = 0;
	}
	
	/**
	 * Default add method with no position parameter - adds an object to the end of the NodeList
	 * @param item to add
	 */
	public void add(Object item){
		
		this.size += 1;
		
		//If the NodeList is empty, the item becomes the first item
		if(this.firstNode == null){
			
			//Creates a new node with position 1
			this.firstNode = new Node(item,1);
		}
		else{
			
			//Sends the item to be added down the line into the list of nodes, at the end position 
			//which would know to be added when the position is the size
			this.firstNode.add(item, this.size);
		}
	}
	
	/**
	 * Add method that adds an item to the NodeList in a specific position
	 * @param item
	 * @param position
	 */
	public void add(Object item, int position){
		this.size += 1;
		
		//If the item is to be added to the beginning adds it there
		if(position == 1){
			
			//Sets up a temporary pointer to reference the first node
			Node tempNode = this.firstNode;
			
			//Creates a new node as the first
			this.firstNode = new Node(item, 1);
			
			//The next node in the list is set to be the original first one
			this.firstNode.setNextNode(tempNode);	
			
			//If the next node exists, alters its position by one upwards to correspond with it's new value
			if(this.firstNode.getNextNode() != null){
				this.firstNode.getNextNode().alterPosition(1);
			}
		}
		//Otherwise sends the item down the list of nodes to its position
		else{
			this.firstNode.add(item, position);
		}
	}
	
	/**
	 * Removal method to take an item out of the NodeList
	 * @param position to remove from
	 */
	public void remove(int position){
		
		this.size -= 1;
		
		//If the position to remove is first takes the first node out
		if(position == 1){
			//Sets the first node to the one after it and cuts the first node out of the list
			this.firstNode = this.firstNode.getNextNode();
			
			//If the NodeList is not empty alters the position numbers of the elements down 1 to correspond with their new values
			if(this.firstNode != null){
				this.firstNode.alterPosition(-1);
			}
		}
		//Otherwise continues down the line of nodes to find the position to remove
		else{
			this.firstNode.remove(position);
		}
	}
	
	/**
	 * Changes the return value of a string of the NodeList
	 */
	public String toString(){
		 String list = "{ ";
		 
		 //If the list is not empty, adds the elements to the list string
		 if(this.firstNode != null){
			 
			 //Runs through each node to add them to the string
			 list = this.firstNode.print(list);
		 }
		 
		 return list+ "}";
	}
	
	/**
	 * Gets the value of an element of the list at a certain position
	 * @param position
	 * @return Element at that position
	 */
	public Object get(int position){
		
		Object returnValue = null;
		
		//If the position is the first element, returns the value of it
		if(1 == position){
			returnValue = this.firstNode.getValue();
		}
		//Otherwise continues down the nodes until reaching the right position
		else{
			returnValue = this.firstNode.get(position);
		}
		
		return returnValue;
	}
	
	/**
	 * Accessor for the size of the NodeList
	 * @return Size
	 */
	public int getSize(){
		return this.size;
	}
}

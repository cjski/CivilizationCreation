/**
 * @Program Individual memory blocks that hold the values of a NodeList in order
 * @author Chris Janowski
 * @course ICS4U
 * @date May 09 2016
 *
 */
public class Node {
	
	private Object value;
	private int position;
	private Node nextNode;
	
	/**
	 * Default Constructor, sets the value of the node and it's position
	 * @param value
	 * @param position
	 */
	Node(Object value, int position){
		this.value = value;
		this.position = position;
		this.nextNode = null;
	}
	
	/**
	 * Add method, allows for the passing of a value down through the line of nodes until reaching position it should be added
	 * @param item to be added
	 * @param position
	 */
	public void add(Object item, int position){
		
		//If the item should be added in front of this node, does so
		if(this.position == position-1){
			
			//Stores the reference to the next node temporarily
			Node tempNode = this.nextNode;
			
			//Creates a new node with the value passed as the next node in the line
			this.nextNode = new Node(item, position);
			
			//Sets the next node of the new node to the temporary reference and thus connects the list back together
			this.nextNode.setNextNode(tempNode);
			
			//If the list continues after the added node, alters their positions to the correct values
			if(this.nextNode.getNextNode() != null){
				this.nextNode.getNextNode().alterPosition(1);
			}
		}
		//Otherwise passes the data further down to the next node
		else{
			this.nextNode.add(item, position);
		}
	}
	
	/**
	 * Allows for an index to be passed down through each node until reaching one that should be removed
	 * @param position
	 */
	public void remove(int position){
		
		//Checks if the node after this one is set to be removed
		if(this.position == position-1){
			
			//Sets the next node to the one after it and cuts the old node out of the list
			this.nextNode = this.nextNode.getNextNode();
			
			//If there are still more nodes in the list, alters their position to the correct one
			if(this.nextNode != null){
				this.nextNode.alterPosition(-1);
			}
		}
		//Otherwise continues down the list of nodes
		else{
			this.nextNode.remove(position);
		}
	}
	
	/**
	 * Alters the position of a node up or down and continues down 
	 * @param altValue +/-1 depending on whether an item was inserted or removed before it
	 */
	public void alterPosition(int altValue){
		
		this.position += altValue;
		
		//If there are still nodes after the specific one continues to alter
		if(this.nextNode != null){
			this.nextNode.alterPosition(altValue);
		}
	}
	
	/**
	 * Passes data down the nodes until it reaches the node being checked for and gets the data stored there
	 * @param position
	 * @return Data stored at that position
	 */
	public Object get(int position){
		
		Object returnValue = null;
		
		//If the index is searching for this node, returns the value at it
		if(this.position == position){
			returnValue = this.value;
		}
		//Otherwise continues down through to the next nodes
		else{
			returnValue = this.nextNode.get(position);
		}
		
		return returnValue;
	}
	
	/**
	 * Accessor for a reference to the next node in the sequence
	 * @return the next node
	 */
	public Node getNextNode(){
		return this.nextNode;
	}
	
	/**
	 * Setter method for the next node in the sequence
	 * @param newNode
	 */
	public void setNextNode(Node newNode){
		this.nextNode = newNode;
	}
	
	/**
	 * Accessor for the value stored in the node
	 * @return
	 */
	public Object getValue(){
		return this.value;
	}
	
	/**
	 * Adds the string of the value of the node to the overall string when outputting the nodelist to screen
	 * @param list in string form
	 * @return List in string form updated
	 */
	public String print(String list){
		
		//Adds the value along with position to the nodelist
		list += (this.value + "["+this.position+"] ");
		
		//If there is a node after this one, continues adding them to the string
		if(this.nextNode != null){
			list = this.nextNode.print(list);
		}
		
		return list;
	}
}
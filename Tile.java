import java.awt.Graphics;

/**
 * 
 */

/**@Program Tile Class - Make up the map of the world and operate independantly
 * @author Christopher Janowski
 * @Course ISC4U CPT's
 * @date June 15 2016
 */
public class Tile {
	
	//Protected items that can be accessed by the child tile classes
	protected int x;
	protected int y;
	protected boolean occupied;
	protected long timeStamp;
	protected long collectionTime;
	protected Building building;
	protected int resourceAmount;
	
	/**
	 * Constructor
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Tile(int x, int y){
		
		//Initally no building exists
		this.building = null;
		//Tile is unoccupied
		this.occupied = false;
		this.timeStamp = System.currentTimeMillis();
		//Amount of resources collected 
		this.resourceAmount = 15;
		
		//Time needed to pass before collecting, takes longer the further away the tile is
		this.collectionTime = 5000 + 10*(Math.abs(350-x) + Math.abs(300-y));
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Checks if resources can be added to the players supply - handled by the child classes
	 * Based on if enough time has passed and if the tile is occupied or not
	 * Skill increases the resource gain
	 * @param timeStamp
	 * @param resources
	 * @param skill
	 */
	public void collectResources(long timeStamp, int[] resources, int skill){
		//Pass
	}
	
	/**
	 * Sets the tile to be occupied by a worker or not
	 * @param occupied
	 */
	public void setOccupation(boolean occupied){
		
		//If the tile is becoming occupied, sets the time stamp to check how much time has passed since collection began
		if(occupied){
			this.timeStamp = System.currentTimeMillis();
		}
		this.occupied = occupied;
	}
	
	/**
	 * Draws the tile to the screen
	 * Ran by the child classes
	 * @param g
	 */
	public void draw(Graphics g){
		//Pass
	}
	
	/**
	 * Runs a natural disaster
	 * Unique to each child class
	 * @param resources
	 * @param game
	 */
	public void naturalDisaster(int[] resources, MainMenu game){
		
		//Takes away from each resource
		for(int i=0;i<5;i++){
			resources[i] -= 10;
		}
	}
	
	/**
	 * Sets the building to be built on the tile
	 * @param building
	 */
	public void setBuilding(Building building){
		
		this.building = building;
		
		//The tile is unoccupied by any workers
		this.occupied = false;
	}
	
	/**
	 * Gets the x coordinate of the tile
	 * @return x coordinate
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * Gets the y coordinate of the tile
	 * @return y coordinate
	 */
	public int getY(){
		return this.y;
	}
	
	/**
	 * Checks whether the tile is occupied by a worker
	 * @return true or false if occupied
	 */
	public boolean getOccupation(){
		return this.occupied;
	}
	/**
	 * Gets the building that is currently on the tile
	 * @return Building object
	 */
	public Building getBuilding(){
		return this.building;
	}
	
}
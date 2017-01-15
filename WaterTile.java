import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 */

/**
 * @Program Tile that holds the water resource and in the event of a natural disaster a flood occurs
 * @author Christopher Janowski and Jacob Gonçalves
 * @course ICS4U
 * @date June 15 2016
 *
 */
public class WaterTile extends Tile{
	
	/**
	 * Constructor for the WaterTile object
	 * @param x coordinate
	 * @param y coordinate
	 */
	public WaterTile(int x, int y) {
		//Initializes the tile class
		super(x,y);
	}
	
	/**
	 * Checks if resources can be added to the players supply
	 * Based on if enough time has passed and if the tile is occupied or not
	 * @param timeStamp
	 * @param resources
	 * @param skill
	 */
	public void collectResources(long timeStamp, int[] resources, int skill){
		
		//Checks if the tile is occupied by a worker
		if(this.occupied){
			//checks if enough time has passes
			if(timeStamp - this.timeStamp > this.collectionTime){
				//Adds to the water supply
				resources[0] += this.resourceAmount+skill;
				//The program now checks the time against a different initial
				this.timeStamp = timeStamp;
			}
		}
	}
	
	/**
	 * Draws the tile to the screen
	 * @param Graphics g
	 */
	public void draw(Graphics g){
		
		//Sets the graphics colour to blue for water
		g.setColor(Color.blue);
		g.fillRect(this.x, this.y, 100, 100);
		//Draws a border around the tile
		g.setColor(Color.BLACK);
		g.drawRect(this.x, this.y, 100, 100);
		
		//If the tile is occupied, draws a red box within
		if(this.occupied){
			g.setColor(Color.RED);
			g.fillRect(this.x+25, this.y+25, 50, 50);
		}
		
		//If the tile has a building on it draws the building
		if(this.building != null){
			g.setColor(Color.GRAY);
			g.fillRect(this.x+25, this.y+25, 50, 50);
			g.drawString(this.building.getName(), this.x+25, this.y+25);
		}
	}
	
	/**
	 * Runs a flood disaster on the tile
	 * @param resources
	 * @param game
	 */
	public void naturalDisaster(int[] resources, MainMenu game){

		//Water resources go up while the rest fall
		resources[0] += 20;
		resources[1] -= 10;
		resources[2] -= 10;
		resources[3] -= 10;
		resources[4] -= 10;
		
		//Sets an initial time to draw the warning statement
		long initTime = System.currentTimeMillis();
		
		//Draws the warning statement for 5 seconds pausing the game
		while(System.currentTimeMillis() - initTime < 5000){
			
			//Draws a white box on top of the screen with a black border and the warning sign inside
			try {
				game.getGraphics().drawImage(ImageIO.read(new File(System.getProperty("user.dir")+"/src/Flood.png")), 325, 300, null);
			} catch (IOException except) {
				//Traces the error
				except.printStackTrace();
			}
		}
	}

}
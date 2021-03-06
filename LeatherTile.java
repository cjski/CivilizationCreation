import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 */

/**
 * @Program Tile that holds the leather resource and in the event of a natural disaster a spoiling goods disaster occurs
 * @author Christopher Janowski and Jacob Gon�alves
 * @course ICS4U
 * @date June 15 2016
 *
 */
public class LeatherTile extends Tile{

	/**
	 * Constructor for the LeatherTile object
	 * @param x coordinate
	 * @param y coordinate
	 */
	public LeatherTile(int x, int y) {
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
				//Adds to the leather supply
				resources[3] += this.resourceAmount+ skill;
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
		
		//Sets the colour to draw to orange
		g.setColor(Color.ORANGE);
		g.fillRect(this.x, this.y, 100, 100);
		//Draws a border around the tile
		g.setColor(Color.BLACK);
		g.drawRect(this.x, this.y, 100, 100);
		
		//If the tile is occupied, draws a red box within
		if(this.occupied){
			g.setColor(Color.RED);
			g.fillRect(this.x+25, this.y+25, 50, 50);
		}
		
		//If the tile has a building on it then draws the building
		if(this.building != null){
			g.setColor(Color.GRAY);
			g.fillRect(this.x+25, this.y+25, 50, 50);
			g.drawString(this.building.getName(), this.x+25, this.y+25);
		}
	}
	
	/**
	 * Runs a spoiled goods natural disaster
	 * @param resources
	 * @param game
	 */
	public void naturalDisaster(int[] resources, MainMenu game){
		
		//Changes the resources based upon what good were spoiled
		resources[3] -= 30;
		resources[1] -= 15;
		
		//Sets an initial time to check against
		long initTime = System.currentTimeMillis();
		
		//For 5 seconds draws the warning statement for spoiled goods
		while(System.currentTimeMillis() - initTime < 5000){

			//Draws a white box on top of the screen with a black border and the warning sign inside
			try {
				game.getGraphics().drawImage(ImageIO.read(new File(System.getProperty("user.dir")+"/src/SpoiledGoods.png")), 325, 300, null);
			} catch (IOException except) {
				//Traces the error
				except.printStackTrace();
			}
		}
	}

}
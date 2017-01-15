import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * The Main Game Program that runs the game and controls the screen to draw to
 * @author Christopher Janowski and Jacob Gonçalves
 * @course ICS4U
 * @date June 15 2016
 */
public class MainMenu extends JComponent implements MouseListener{
	
	private static final long serialVersionUID = -688856155579961348L;
	
	private int gameState;
	private JFrame frame;
	
	private int[] resources;
	private int[] stats;
	
	private int workers;
	private int population;
	
	private Tile[] tiles;
	private Queue buildings;
	private Stack buildableTiles;
	
	private long timeFoodandWater = System.currentTimeMillis();
	private long timeNaturalDisaster = System.currentTimeMillis();
	
	/**
	 * Constructor for the game object
	 * @throws FileNotFoundException
	 */
	public MainMenu() throws FileNotFoundException {
		
		//0 - Menu / 1 - Game / 2 - Lose / 3 - Win / 4 - Menu
		this.gameState = 0;
		
		//Creates a new Frame to draw graphics to
		this.frame = new JFrame(); 
		//Sets the default shutdown and close operation
	    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //Sets the size of the frame to 850x850 pixels
	    this.frame.setSize(850,850); 
	    //Adds the game object onto the frame to draw to it
	    this.frame.add(this);
	    //Makes the frame visible to the user
	    this.frame.setVisible(true);
	    //Tells the frame to respond to mouse actions
	    this.addMouseListener(this);
	    
	    //Makes new arrays to hold all the tiles, resources, and stats
	    //Made outside of the initGame function to not create new memory spaces
	    this.tiles = new Tile[36];
	    this.resources = new int[5];
		this.stats = new int[5];
		
		//Initalizes the variables of the game
		this.initGame();
		
		//Runs the game until the close operation is chosen
		while(true){
			//Runs the main game functions if the game state is set to 1
			if(this.gameState == 1){
				this.game();
			}
			//Otherwise repaints the screen with menu graphics
			else{
				this.repaint();
			}
		}
		
	}
	
	/**
	 * Initializes stats and items used in the game to restart
	 * @throws FileNotFoundException 
	 */
	public void initGame() throws FileNotFoundException{
		
		//Creates a new Stack and Queue to ensure that duplicates of the buildings and tiles are not re-added
		this.buildings = new Queue();
		this.buildableTiles = new Stack();
		
		//Workers are used to check for occupying tiles
		this.workers = 2;
		//Population used to check against workers for natural disasters and how many tiles are occupied
		this.population = 2;
		
		//Sets the initial resource counts
		//Water
		this.resources[0] = 20;
		//Food
		this.resources[1] = 20;
		//Wood
		this.resources[2] = 0;
		//Leather
		this.resources[3] = 0;
		//Stone
		this.resources[4] = 0;
		
		//Sets the Starting Stats
		
		//Intelligence
		this.stats[0] = 0;
		//Skill
		this.stats[1] = 0;
		//Agility
		this.stats[2] = 0;
		//Endurance
		this.stats[3] = 0;
		//Luck
		this.stats[4] = 0;
		
		//Creates a new file to read the tile information from
		Scanner tileSc = new Scanner(new FileReader(System.getProperty("user.dir")+ "/src/TileCoordinates.txt"));
		
		//Creates a new random object to randomize tile creation
		Random rand = new Random();
		
		//Makes 36 tiles on the map
		for(int i =0 ; i<36;i++){
			
			//Makes sure that at least one of each tile is present on the field each game
			if(i == 0){
				this.tiles[i] = new WaterTile(tileSc.nextInt(),tileSc.nextInt());
				this.buildableTiles.push(this.tiles[i]);
			}
			else if(i==3){
				this.tiles[i] = new FoodTile(tileSc.nextInt(),tileSc.nextInt());
				this.buildableTiles.push(this.tiles[i]);
			}
			else if(i==6){
				this.tiles[i] = new WoodTile(tileSc.nextInt(),tileSc.nextInt());
				this.buildableTiles.push(this.tiles[i]);
			}
			else if(i==9){
				this.tiles[i] = new LeatherTile(tileSc.nextInt(),tileSc.nextInt());
				this.buildableTiles.push(this.tiles[i]);
			}
			else if(i==12){
				this.tiles[i] = new StoneTile(tileSc.nextInt(),tileSc.nextInt());
				this.buildableTiles.push(this.tiles[i]);
			}
			//Sets the rest of the tiles randomly
			else{
				//Takes a random number to set their type
				int type = rand.nextInt(5);
				
				//Sets the type based on the random number, inputs the coordinates based on the location in the file, and pushes them to the top of the stack
				if(type == 0){
					this.tiles[i] = new WaterTile(tileSc.nextInt(),tileSc.nextInt());
					this.buildableTiles.push(this.tiles[i]);
				}
				else if(type == 1){
					this.tiles[i] = new WoodTile(tileSc.nextInt(),tileSc.nextInt());
					this.buildableTiles.push(this.tiles[i]);
				}
				else if(type == 2){
					this.tiles[i] = new StoneTile(tileSc.nextInt(),tileSc.nextInt());
					this.buildableTiles.push(this.tiles[i]);
				}
				else if(type == 3){
					this.tiles[i] = new LeatherTile(tileSc.nextInt(),tileSc.nextInt());
					this.buildableTiles.push(this.tiles[i]);
				}
				else{
					this.tiles[i] = new FoodTile(tileSc.nextInt(),tileSc.nextInt());
					this.buildableTiles.push(this.tiles[i]);
				}
			}
		}
		//Closes the input stream from the tile data file
		tileSc.close();
		
		//Sets the initial times to check against in the program
		this.timeFoodandWater = System.currentTimeMillis();
		this.timeNaturalDisaster = System.currentTimeMillis();
		
		//Opens a new file to read the building names from
		Scanner buildingSc = new Scanner(new FileReader(System.getProperty("user.dir")+"/src/BuildingData.txt"));
		//Opens a new file to read the costs and benefits from the building from
		Scanner numberSc = new Scanner(new FileReader(System.getProperty("user.dir")+"/src/BuildingNumData.txt"));
		
		//Creates new buildings based on the data in the file
		for(int i = 0;i < 12;i++){
			
			String name = buildingSc.nextLine();
			//Creates a new building to add to the queue
			this.buildings.enqueue(new Building(name,new int[]{numberSc.nextInt(),numberSc.nextInt(),numberSc.nextInt(),numberSc.nextInt(),numberSc.nextInt()},new int[]{numberSc.nextInt(),numberSc.nextInt(),numberSc.nextInt(),numberSc.nextInt(),numberSc.nextInt()}));
		}
		//Closes the files being read from
		buildingSc.close();
		numberSc.close();
	}
	
	
	/**
	 * All functions that run during the main portion of the game
	 * @throws FileNotFoundException
	 */
	public void game() throws FileNotFoundException{
		
		//Slowly deteriorates the amount of food and water based on the population
		if(System.currentTimeMillis() - this.timeFoodandWater >= 5000){
			//Endurance gives less loss to the food and water
			this.resources[0] -= (this.population*3)-this.stats[3];
			this.resources[1] -= (this.population*3)-this.stats[3];
			//Sets the next time to check against
			this.timeFoodandWater = System.currentTimeMillis();
		}
		
		//Collects resources if applicable from all available tiles
		for(int i=0;i<36;i++){
			//Resources collect faster with increased agility
			//Agility is added to make the time to check against a larger number
			this.tiles[i].collectResources(System.currentTimeMillis() + (250*this.stats[2]), this.resources, this.stats[1]);
		}
		
		
		//Checks if a natural disaster would run at this time
		//Time to check increases with luck stat
		if(System.currentTimeMillis() - this.timeNaturalDisaster >= 10000 + (250*this.stats[4])){
			//Takes a random number up to 100
			int randomNum = (int)(Math.random() * 100);
			//If the number is higher than the threshold then the disaster occurs
			if(randomNum >= 80 + this.stats[4]){
				
				//Picks a random number out of the amount of occupied tiles
				//Population - workers available gives the number of workers working (-1 to ensure array can check)
				//This number is how many tiles to go through before reaching the one to have the disaster at
				int randTile = (int)(Math.random()*(this.population - this.workers-1));
				
				//Goes through the tiles to find the one to form a natural disaster at
				for(int i=0;i<36;i++){
					
					//Only checks for each occupied tile
					if(this.tiles[i].getOccupation()){
						
						//If the amount of tiles to skip has passed, then runs the natural disaster on that tile
						if(randTile == 0){
							this.tiles[i].naturalDisaster(resources, this);
							//Ensures randTile will not get back to 0
							randTile -=1;
						}
						//Otherwise continues the countdown
						else{
							randTile -=1;
						}
					}
				}
			}
			
			//Sets a new time to check against
			this.timeNaturalDisaster = System.currentTimeMillis();
		}
		//TEST: prints out resource count
		//System.out.println(this.resources[0]+" "+this.resources[1]+" "+this.resources[2]+" "+this.resources[3]+" "+this.resources[4]);
		
		//Repaints the game screen
		this.repaint();
		
		//If the player has run out of food or water then they lose the game
		if(this.resources[0] < 0 || this.resources[1] < 0){
			//Game loop stops running, lose screen will be printed
			this.gameState = 2;
			//Game stats are reset
			this.initGame();
		}
		
		//Ensures that the building materials do not fall below 0
		if(this.resources[2] < 0){
			this.resources[2] = 0;
		}
		if(this.resources[3] < 0){
			this.resources[3] = 0;
		}
		if(this.resources[4] < 0){
			this.resources[4] = 0;
		}
		
		//Checks if the user has met the victory conditions
		boolean victory = true;
		
		//If all the stats are above the threshold, then the victory condition has been met
		for(int i=0;i<5;i++){
			//if an stat is below the threshold, victory is set to false
			if(this.stats[i] < 7){
				victory = false;
			}
		}
		
		//If the user won sets the game to the victory screen and reinitializes the game
		if(victory){
			this.gameState = 3;
			this.initGame();
		}
		
	}
	
	/**
	 * Paints the graphics to be drawn to the screen
	 * @param g
	 */
	public void paint(Graphics g){
		
		//Paints the graphics to be updated for the game
		if(this.gameState == 1){
			
			//Draws each tile
			for(int i=0;i<36;i++){
				this.tiles[i].draw(g);
			}
			
			//Draws menu bar
			g.setColor(Color.GRAY);
			g.fillRect(0, 710, 850, 200);
			
			//Draws the next building button
			g.setColor(Color.RED);
			g.fillRect(750, 710, 100, 70);
			
			//Draws the Craft building button
			g.setColor(Color.GREEN);
			g.fillRect(750, 765, 100, 70);
			g.setColor(Color.BLACK);
			
			g.drawString("Next Building", 757, 740);
			g.drawString("Craft Building", 757, 790);
			
			//Prints Resources
			g.drawString("Resources:", 10, 723);
			//Prints how much water and food will be lost with that population
			g.drawString("Water: "+this.resources[0]+" - "+((this.population*3)-this.stats[3]), 10, 740);
			g.drawString("Food: "+this.resources[1]+" - "+((this.population*3)-this.stats[3]), 10, 757);
			g.drawString("Wood: "+this.resources[2], 10, 774);
			g.drawString("Leather: "+this.resources[3], 10, 791);
			g.drawString("Stone: "+this.resources[4], 10, 808);
			
			//Prints Stats
			g.drawString("Stats:", 170, 723);
			g.drawString("Intelligence: "+this.stats[0], 170, 740);
			g.drawString("Skill: "+this.stats[1], 170, 757);
			g.drawString("Agility: "+this.stats[2], 170, 774);
			g.drawString("Endurance: "+this.stats[3], 170, 791);
			g.drawString("Luck: "+this.stats[4], 170, 808);
			
			//Prints number of workers
			g.drawString("Workers available:"+this.workers+"/"+this.population,615, 742);
			
			
			//Prints Current Building stats and costs
			if(!this.buildings.isEmpty()){
				
				//Prints which building is on top of the queue
				g.drawString("Viewing:"+((Building) (this.buildings.peek())).getName(), 615, 723);
				
				//Prints the costs of the buildings
				//Gets the strings of the costs of the building, in the form "Resource: cost"
				String[] costs = ((Building)this.buildings.peek()).getCostString(this.stats[0]);
				g.drawString("Cost to build:", 330, 723);
				g.drawString(costs[0], 330, 742);
				g.drawString(costs[1], 330, 759);
				g.drawString(costs[2], 330, 776);
				g.drawString(costs[3], 330, 793);
				g.drawString(costs[4], 330, 810);
				
				//Gets the strings of the benefits of the building, in the form "Stat: benefit"
				String[] benefits = ((Building)this.buildings.peek()).getBenefitsString();
				
				//prints benefits of buildings
				g.drawString("Benefit of building:", 490, 723);
				g.drawString(benefits[0], 490, 742);
				g.drawString(benefits[1], 490, 759);
				g.drawString(benefits[2], 490, 776);
				g.drawString(benefits[3], 490, 793);
				g.drawString(benefits[4], 490, 810);
				
			}
			
			//Draw the home base in the middle of the map
			//Opens the file to draw with a try-catch to catch any error in opening the file
	    	try {
				g.drawImage(ImageIO.read(new File(System.getProperty("user.dir")+"/src/Castle.png")), 375, 300, null);
			} catch (IOException except) {
				//Traces the error back to its origin
				except.printStackTrace();
			}
		}
		//Functions to draw if the user won the game
		else if(this.gameState == 3){
	    	 
	    	 //Opens the victory image with a try-catch to catch any errors with the file or name being in the wrong location
	         try {
				g.drawImage(ImageIO.read(new File(System.getProperty("user.dir")+"/src/Aliens.png")), 0, 0, null);
			} catch (IOException except) {
				//Traces the error
				except.printStackTrace();
			}
	         
	         //Draws the victory logo
	         Font f = new Font("serif", Font.BOLD, 50);
	    	 g.setColor(Color.GREEN);
	    	 g.setFont(f);
	         g.drawString("VICTORY", 300, 50);
	         
	         //Draws the restart button
	         g.setColor(Color.GREEN);
	         g.fillRect(150, 500, 550, 100);
	         
	         //Draws the exit button
	         g.setColor(Color.RED);
	         g.fillRect(150, 650, 550, 100);
	         
	         //Draws borders around both buttons
	         g.setColor(Color.BLACK);
	         g.drawRect(150, 500, 550, 100);
	         g.drawRect(150, 650, 550, 100);
	         
	         //Draw the proper info on the buttons
	    	 g.setColor(Color.BLACK);
	         g.drawString("Restart", 355, 550);
	         g.drawString("Exit", 390, 700);
	      
		}
		//Draws the loss screen
		else if(this.gameState == 2){
			
			 //Opens the image to draw with a try-catch catching any errors with finding the file
	         try {
				g.drawImage(ImageIO.read(new File(System.getProperty("user.dir")+"/src/Earth-exploding.png")), 0, 0, null);
			 } catch (IOException except) {
				//Traces the error back to where it originated
				except.printStackTrace();
			 }
	         
	         //Draws the game over text
	         Font f = new Font("serif", Font.BOLD, 50);
	    	 g.setColor(Color.RED);
	    	 g.setFont(f);
	         g.drawString("GAME OVER", 250, 50);
	         
	         //Draws the restart button
	         g.setColor(Color.GREEN);
	         g.fillRect(150, 500, 550, 100);
	         
	         //Draws the exit button
	         g.setColor(Color.RED);
	         g.fillRect(150, 650, 550, 100);
	         
	         //Draws borders around the buttons
	         g.setColor(Color.BLACK);
	         g.drawRect(150, 500, 550, 100);
	         g.drawRect(150, 650, 550, 100);
	         
	    	 //Draws the proper info onto the buttons
	         g.drawString("Restart", 355, 550);
	         g.drawString("Exit", 390, 700);
		}
		
		//Draws the instructions page
		else if(this.gameState == 4){
			
			//Opens an image file with the instructions
			try {
				g.drawImage(ImageIO.read(new File(System.getProperty("user.dir")+"/src/Instructions.png")), 0, 0, null);
			} catch (IOException e) {
				//Traces the error back to its origin
				e.printStackTrace();
			}
		}
		
		//Runs the drawing for the main menu screen
		else{
			
			 //Opens the file to draw with a try-catch to catch any error in opening the file
	    	 try {
					g.drawImage(ImageIO.read(new File(System.getProperty("user.dir")+"/src/MainMenu.png")), 0, 0, null);
			 } catch (IOException except) {
					//Traces the error back to its origin
					except.printStackTrace();
			 }
	    	 
	    	 //Draws the title to the game
	    	 Font f = new Font("serif", Font.BOLD, 50);
	    	 g.setColor(Color.WHITE);
	    	 g.setFont(f);
	         g.drawString("Civilization Creation", 150, 50);
	         
	         //Draws the start button
	         g.setColor(Color.GREEN);
	         g.fillRect(150, 500, 550, 100);
	         
	         //Draws the exit button
	         g.setColor(Color.RED);
	         g.fillRect(150, 650, 550, 100);
	         
	         //Draws borders around the buttons
	         g.setColor(Color.BLACK);
	         g.drawRect(150, 500, 550, 100);
	         g.drawRect(150, 650, 550, 100);
	         
	    	 //Draws the proper info on the buttons
	         g.drawString("Start", 380, 550);
	         g.drawString("Exit", 390, 700);
		}
		
		
	}
	
	/**
	 * Main program, creates a new object and begins running game
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String args[]) throws FileNotFoundException{
		new MainMenu();
	}
	
	/**
	 * Actions to occur upon mouse being clicked on the screen
	 * @param e
	 */
	public void mouseClicked(MouseEvent e) {
		
		//gets mouse coordinates of click
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		//Events that run for the game
		if(this.gameState ==1){
			
			//Checks if any tile was clicked on
			for(int i=0;i< 36;i++){
				
				//Checks if the mouse click was within the range of the tile
				if(mouseX > this.tiles[i].getX() && mouseX < this.tiles[i].getX()+100 && mouseY > this.tiles[i].getY() && mouseY < this.tiles[i].getY()+100){
					
					//Can only occupy if the tile has no building on it and there are available workers
					if(this.tiles[i].getBuilding() == null){
						//If the tile is already occupied by a worker, the worker will return and available workers increases
						if(this.tiles[i].getOccupation()){
							this.workers += 1;
							this.tiles[i].setOccupation(false);
						}
						//Checks if there is an available worker to add to the tile
						else if(this.workers > 0){
							//Adds the worker to the tile and reduces amount of available workers
							this.workers -= 1;
							this.tiles[i].setOccupation(true);
						}
					}
					
				}
				
			}
			
			//Checks if the building button was pressed
			if(mouseX >= 750 && mouseX <= 850 && mouseY >= 765 && mouseY <= 850){
				//Runs only if there are buildings left
				if(!this.buildings.isEmpty()){
					//Checks if enough resources are available to build the building
					if(((Building) this.buildings.peek()).canBuild(this.resources, this.stats[0])){
						
						//If a worker was on the tile that now hosts a building, returns him to be available
						if(((Tile) this.buildableTiles.peek()).getOccupation()){
							this.workers += 1;
						}
						
						//Builds the building by altering stats and resources as applicable
						((Building)this.buildings.peek()).build(this.resources, this.stats);
						((Tile) this.buildableTiles.peek()).setBuilding((Building) this.buildings.peek());
						
						//Removes the added building from the queue
						this.buildings.dequeue();
						//Takes the tile out of the stack so the next one will be built to
						this.buildableTiles.pop();
						
						//Each building increases the population
						this.population += 1;
						this.workers += 1;
					}
				}
			}
			
			//Checks if the switching buildings button was pressed
			if(mouseX >= 750 && mouseX <= 850 && mouseY >= 710 && mouseY <= 765){
				
				//Runs only if the building queue has something within
				if(!this.buildings.isEmpty()){
					//Temporary holder to keep a pointer on the memory location of the building
					Building tempBuild = (Building) this.buildings.peek();
					
					//Removes the building from the front of the queue and adds it to the back
					this.buildings.dequeue();
					this.buildings.enqueue(tempBuild);
				}
			}
		}
		//Button checks for the instructions screen
		else if(this.gameState == 4){
			//A click anywhere will run the game
			this.gameState = 1;
		}
		//Button checks for if the game is not running
		else{
			//Checks if the exit button was clicked
			if(mouseX >= 150 && mouseX <= 700 && mouseY >= 650 && mouseY <= 750){
				
				//Exits the program
				System.exit(0);
			}
			//Checked if the start or restart button was pressed
			if(mouseX >= 150 && mouseX <= 700 && mouseY >= 500 && mouseY <= 600){
				
				//Sets the instructions to be shown
				this.gameState = 4;
			}
			
		}
	}
	 
	/**
	 * Required functions to override mouse activity on the frame, from the MouseListener
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
}
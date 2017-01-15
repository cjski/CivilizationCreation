/**
 * Building that provides stats and costs resources 
 * @author Christopher Janowski
 * @course ICS4U
 * @date June 15 2016
 */
public class Building {
	
	private String name;
	private int[] costs;
	private int[] benefits;
	
	/**
	 * Constuctor for the building
	 * @param name
	 * @param costs
	 * @param benefits
	 */
	public Building(String name, int[] costs, int[] benefits){
		this.name = name;
		this.costs = costs;
		this.benefits = benefits;
	}
	
	/**
	 * Gets the name of the building
	 * @return String of the name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Checks if the user has the resources available to build the building
	 * @param resources
	 * @param intelligence
	 * @return True or false if the user can build
	 */
	public boolean canBuild(int[] resources, int intelligence){
		boolean buildable = true;
		
		//Checks each resource against each cost for the building
		for(int i=0;i<5;i++){
			
			//If the user does not have enough resources then the building is not buildable
			if(resources[i] < this.costs[i] - intelligence){
				buildable = false;
			}
		}
		
		return buildable;
	}
	
	/**
	 * Builds the building by subtracting from resources and improving the statistics
	 * @param resources
	 * @param stats
	 */
	public void build(int[] resources, int[] stats){
		
		//Keeps the intelligence stored as the value changes in building
		int intelligence = stats[0];
		//Runs for each stat and cost as both arrays are of length 5
		for(int i=1;i<5;i++){
			
			//Checks if the intelligence stat brings the cost below 0
			//If not then does not take away anything from the resource to ensure no resources are given for free
			if(this.costs[i] - intelligence >0){
				//Takes away the cost of the building minus the intelligence
				resources[i] -= this.costs[i] - intelligence;
			}

			//Adds the benefits from the building
			stats[i] += this.benefits[i];
		}
		
		//Adds the intelligence afterwards to not affect costs
		stats[0] += this.benefits[0];
	}
	
	/**
	 * Gets the costs of the building
	 * @param intelligence
	 * @return An array of strings length 5 for UI use
	 */
	public String[] getCostString(int intelligence){
		
		//Sets the costs of the resource to less the intelligence factor
		int waterCost = this.costs[0] - intelligence;
		//If the cost went below zero sets it to zero
		if(waterCost < 0){
			waterCost = 0;
		}
		//Sets the costs of the resource to less the intelligence factor
		int foodCost = this.costs[1] - intelligence;
		//If the cost went below zero sets it to zero
		if(foodCost < 0){
			foodCost = 0;
		}
		//Sets the costs of the resource to less the intelligence factor
		int woodCost = this.costs[2] - intelligence;
		//If the cost went below zero sets it to zero
		if(woodCost < 0){
			woodCost = 0;
		}
		//Sets the costs of the resource to less the intelligence factor
		int leatherCost = this.costs[3] - intelligence;
		//If the cost went below zero sets it to zero
		if(leatherCost < 0){
			leatherCost = 0;
		}
		//Sets the costs of the resource to less the intelligence factor
		int stoneCost = this.costs[4] - intelligence;
		//If the cost went below zero sets it to zero
		if(stoneCost < 0){
			stoneCost = 0;
		}
		return new String[]{"Water: -"+waterCost,"Food: -"+foodCost,"Wood: -"+woodCost,"Leather: -"+leatherCost,"Stone: -"+stoneCost};
	}
	
	/**
	 * Gets the benefits to stats from building the building
	 * @return An array of Strings length 5 for UI use
	 */
	public String[] getBenefitsString(){
		return new String[]{"Intelligence: "+this.benefits[0],"Skill: "+this.benefits[1],"Agility: "+this.benefits[2],"Endurance: "+this.benefits[3],"Luck: "+this.benefits[4]};
	}
}
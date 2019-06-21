package runnable;

/*
 * This class is left incomplete on purpose.
 */

import java.util.ArrayList;

import visualization.Vehicle;

public class Main {

	public static void main(String[] args) {
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

		// The main loop of the game
		while(true){
			moveVehicles(vehicles);
			
			createVehicle();
			
			//TODO check for collision and call other methods like updateScore etc.
		}
	}

	/**
	 * Move each vehicle on the arraylist one step in their corresponding direction
	 * 
	 * @param veh	visible vehicles	
	 */
	private static void moveVehicles(ArrayList<Vehicle> veh) {

	}

	/**
	 * Create new vehicle checking the already created vehicles.
	 * Creation, type and lane of the vehicle are decided randomly.
	 * 
	 * Once the vehicle created, if the lane is not available (occupied with some other vehicle),
	 * the newly created one is discarded.
	 * 
	 * @return 		vehicle to be added to the board
	 */
	private static Vehicle createVehicle() {
		//TODO implement
		return null;
	}

}
package runnable;

/*
 * This class is left incomplete on purpose.
 */

import java.util.ArrayList;
import java.lang.System;
import java.util.Random;

import visualization.Vehicle;
import visualization.Car;
import visualization.Bus;
import visualization.Board;

public class Main {

	private static Board board;
	

	public static void main(String[] args) {
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		int screenWidth = 1280;
		int screenHeight = 720;
		int margin = 75;
		board = new Board("SprinterTurtle",screenWidth,screenHeight, margin);
		long now = System.currentTimeMillis();
		long prev;
		double elapsedTime;

		// TODO: Delete these lines
		Car c1 = new Car(1,10);
		c1.move(100, 100);
		board.addObject(c1);

		Bus b1 = new Bus(1,10);
		b1.move(500, 100);
		board.addObject(b1);

		// The main loop of the game
		while(true){
			prev = now;
			now = System.currentTimeMillis();
			elapsedTime = (now-prev);
			moveVehicles(vehicles);

			createVehicle();
			board.moveTurtle(elapsedTime, 0.1);
			
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
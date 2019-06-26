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
	private static Random randGen = new Random(System.currentTimeMillis());

	public static void main(String[] args) {
		int screenWidth = 1280;
		int screenHeight = 720;
		int margin = 75;
		board = new Board("SprinterTurtle",screenWidth,screenHeight, margin);
		long current = System.currentTimeMillis();
		long prev;
		double elapsedTime;
		
		// The main loop of the game
		while(true){
			prev = current;
			current = System.currentTimeMillis();
			elapsedTime = (current-prev);
			board.moveTurtle(elapsedTime, 0.1);
			board.moveVehicles(elapsedTime, 0.2);
			if(board.checkCollision()){	break;	}
			createVehicle(elapsedTime);
			
			//TODO check for collision and call other methods like updateScore etc.
		}
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
	private static boolean createVehicle(double elapsedTime) {
		if(randGen.nextDouble()<0.00025*elapsedTime){
			int d = randGen.nextInt(2)==0 ? -1 : 1;
			int l = randGen.nextInt(10);
			Vehicle v;
			if(randGen.nextDouble()>0.5){
				v = new Bus(d, l);
			}
			else{
				v= new Car(d, l);
			}
			board.addObject(v);
			return true;
		}
		return false;
	}

}
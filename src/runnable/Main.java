package runnable;

/*
 * This class is left incomplete on purpose.
 */

import java.lang.System;
import java.util.Random;
import java.lang.Math;

import acm.graphics.GRect;
import acm.graphics.GRectangle;
import visualization.Vehicle;
import visualization.Car;
import visualization.Bus;
import visualization.Board;

public class Main {

	private static Board board;
	private static Random randGen = new Random(System.currentTimeMillis());
	private static int round = 1;
	private static int score = 0;

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
			board.moveVehicles(elapsedTime, 0.15*Math.sqrt(round));
			if(board.checkCollision()){	
				gameOver();	
			}
			if(board.checkPassed()){
				score+=100*Math.pow(2, Math.sqrt(round-1));
				round++;
				board.updateLabels(round, score);
			}
			createVehicle(elapsedTime);
			
			
			//TODO check for collision and call other methods like updateScore etc.
		}
	}

	private static void gameOver(){
		board.gameOver();
		System.exit(0);
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
		if(randGen.nextDouble()<0.001*elapsedTime){
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
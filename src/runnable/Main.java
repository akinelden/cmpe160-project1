package runnable;

import java.lang.System;
import java.util.Random;
import java.util.ArrayList;
import java.io.*;
import java.lang.Math;

import visualization.Vehicle;
import visualization.Car;
import visualization.Bus;
import visualization.Board;

public class Main {

	private static Board board;
	private static Random randGen = new Random(System.currentTimeMillis());
	private static SQLiteManager dbManager = new SQLiteManager("Highscores.db");
	private static int round = 1;
	private static int score = 0;
	private static String player = "";

	public static void main(String[] args) {
		int screenWidth = 1280;
		int screenHeight = 720;
		int margin = 75;
		board = new Board("SprinterTurtle",screenWidth,screenHeight, margin);
		startGame();
		// The main loop of the game
		long current = System.currentTimeMillis();
		long prev = 0;
		while(true){
			double elapsedTime;
			prev = current;
			current = System.currentTimeMillis();
			elapsedTime = (current-prev);
			board.moveTurtle(elapsedTime, 0.1);
			board.moveVehicles(elapsedTime, 0.15*Math.sqrt(round));
			if(board.checkPassed()){
				score+=100*Math.pow(2, Math.sqrt(round-1));
				round++;
			}
			board.updateLabels(round, score);
			createVehicle(elapsedTime);
			if(board.checkCollision()){	
				gameOver();	
			}
		}
	}

	private static void startGame(){
		ArrayList<String[]> scoresList = dbManager.readScores();
		board.updateHighScores(scoresList);
		round = 1;
		score = 0;
		player = board.startNewGame();
	}

	private static void gameOver(){
		int last_score = board.updateLabels(round, score);
		dbManager.writeNewScore(player, last_score);
		if(board.gameOver()){
			startGame();
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


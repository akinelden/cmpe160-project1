package runnable;

import java.lang.System;
import java.util.Random;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.*;
import java.lang.Math;

import visualization.Vehicle;
import visualization.Car;
import visualization.Bus;
import visualization.Board;

public class Main {

	private static Board board;
	private static Random randGen = new Random(System.currentTimeMillis());
	private static int round = 1;
	private static int score = 0;
	private static String player = "";
	private static ArrayList<Pair> highscores = new ArrayList<Pair>();

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
		readScores();
		sortScores();
		board.updateHighScores(pairListToString());
		round = 1;
		score = 0;
		player = board.startNewGame();
	}

	private static void gameOver(){
		int last_score = board.updateLabels(round, score);
		Pair p = new Pair(player, last_score);
		highscores.add(p);
		sortScores();
		writeScores();
		if(board.gameOver()){
			startGame();
		}

	}
	private static Vector<Vector<String>> pairListToString(){
		Vector<Vector<String>> scores = new Vector<Vector<String>>();
		scores.add(new Vector<String>());
		scores.add(new Vector<String>());
		for(int i=0; i<highscores.size();i++){
			scores.get(0).add(highscores.get(i).getName());
			scores.get(1).add(highscores.get(i).getScore().toString());
		}
		return scores;
	}

	private static void sortScores(){
		highscores.sort(new Comparator<Pair>() {
			@Override
			public int compare(Pair p1, Pair p2){
				if(p1.getScore()>p2.getScore()){return -1;}
				else if(p1.getScore()==p2.getScore()){return 0;}
				return 1;
			}
		});
	}

	private static void readScores(){
		BufferedReader reader = null;
		if(highscores!=null){
			highscores.clear();
		}
		try{
			reader = new BufferedReader(new FileReader("highscores.txt"));
			String line = reader.readLine();
			for(int i=0; line!=null & i<10; i++){
				Pair p;
				String[] splitted = line.split(",");
				if(splitted.length==2){
					p = new Pair(splitted[0], Integer.parseInt(splitted[1]));
					highscores.add(p);
				}
				line = reader.readLine();
			}
		} catch(FileNotFoundException e1){
			System.out.println("'highscores.txt' not found.\nA new one will be created.\n");
		} catch(IOException e2){
			System.out.println("Unable to read 'highscores.txt' file.\n");
		}
		finally{
			try{reader.close();}
			catch(IOException e){
				System.out.println("Cannot close the reader file.\n");
			} catch(NullPointerException e2){}
		}
	}

	private static void writeScores(){
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter("highscores.txt"));
			for(int i=0; i<highscores.size(); i++){
				Pair p = highscores.get(i);
				writer.write(p.getName()+","+p.getScore()+"\n");
			}
		} catch(IOException e2){
			System.out.println("Unable to write highscores.\n");
		}
		finally{
			try{writer.close();}
			catch(IOException e){
				System.out.println("Cannot close the writer file.\n");
			}
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

class Pair{
	private String name;
	private Integer score;

	Pair(String n, Integer s){
		name = n;
		score = s;
	}
	public String getName(){return name;}
	public Integer getScore(){return score;}
}
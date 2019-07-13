package visualization;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;
import java.util.Vector;
import java.util.ArrayList;
import java.lang.System;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GLabel;

public class Board implements BoardIntf {
	private JFrame frame;
	private GCanvas canvas;
	private GImage asphalt,turtle,explode;
	private GLabel round, score;
	private ArrayList<GLabel> highScore1, highScore2;
	private GLabel congrLabel, endLabel, pauseLabel;
	private KeyListener keyListen;
	private JButton startButton, exitButton;
	private String name = "";
	enum Keys {
		K_UP,K_DOWN,K_RIGHT,K_LEFT,K_W,K_S,K_A,K_D,K_P,
		MAX // be sure that MAX always the last one
	}
	private int[] pressedKeys = new int [Keys.MAX.ordinal()];

	public Board(String boardName, int width, int height, int margin) {
		asphalt = new GImage("asfalt.jpg");
		turtle = new GImage("turtle.png");
		explode = new GImage("explode.png");
		congrLabel = new GLabel("CONGRATULATIONS! : 3");
		endLabel = new GLabel("GAME OVER");
		startButton = new JButton("New Game");
		exitButton = new JButton("Exit");
		highScore1 = new ArrayList<>();
		highScore2 = new ArrayList<>();
		setCanvas(boardName, width, height, margin);
	}

	public void setCanvas(String boardName, int width, int height, int margin) {
		frame = new JFrame(boardName);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new GCanvas();
		frame.getContentPane().add(canvas);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		canvas.setVisible(false);
		setBackground(margin);
	}

	public void setBackground(int margin) {
		canvas.setBackground(Color.LIGHT_GRAY);
		asphalt.setSize(canvas.getWidth(),canvas.getHeight()-2*margin);
		asphalt.setLocation(0,margin);
		canvas.add(asphalt);
		turtle.scale(0.5);
		canvas.add(turtle);
		setLabels();
		setButtons();
		addGameInfoLabels();
		addKeyBoardListener();
		resetTurtle();
		canvas.setVisible(true);
	}

	private void setLabels(){
		congrLabel.setColor(Color.YELLOW);
		congrLabel.setFont("*-bold-*");
		congrLabel.setFont("*-40-*");
		canvas.add(congrLabel, (canvas.getWidth()-congrLabel.getWidth())/2, canvas.getHeight()/2);
		congrLabel.setVisible(false);
		endLabel.setFont("*-bold-*");
		endLabel.setFont("*-50-*");
		canvas.add(endLabel, (canvas.getWidth()-endLabel.getWidth())/2, canvas.getHeight()/2-endLabel.getHeight());
		endLabel.setVisible(false);
	}
	private void setButtons(){
		canvas.add(startButton, canvas.getWidth()/2-10,endLabel.getY()+30);
		startButton.setLocation(canvas.getWidth()/2-startButton.getWidth()-10, startButton.getY());
		exitButton.setSize(startButton.getWidth(), startButton.getHeight());
		canvas.add(exitButton, startButton.getX()+startButton.getWidth()+20, startButton.getY());
		startButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				name = "";
			}
		});
		exitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		startButton.setVisible(false);
		exitButton.setVisible(false);
	}

	private void resetTurtle(){
		double x = canvas.getWidth()/2-turtle.getWidth()/2;
		double y = asphalt.getY()+asphalt.getHeight();
		turtle.setLocation(x,y);
	}

	public String startNewGame(){
		for(Vehicle v : objects){
			canvas.remove(v);
		}
		objects.clear();
		resetTurtle();
		name = "";
		GLabel enterName = new GLabel("Enter your name:");
		JTextField nameField = new JTextField();
		enterName.setFont("*-bold-*");
		enterName.setFont("*-40-*");
		enterName.setLocation(canvas.getWidth()/2-enterName.getWidth()/2, canvas.getHeight()/2-enterName.getHeight());
		nameField.setFont(new Font("Arial", Font.BOLD, 35));
		nameField.setSize((int)enterName.getWidth(), nameField.getWidth());
		nameField.setLocation((int)enterName.getX(),(int)enterName.getY()+20);
		JButton submit = new JButton("OK");
		submit.setFont(new Font("Arial", Font.BOLD, 20));
		submit.setLocation(nameField.getX()+nameField.getWidth()+20,nameField.getY()+5);
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				name = nameField.getText();
			}
		});
		canvas.add(enterName);
		canvas.add(nameField);
		canvas.add(submit);
		
		while(name == ""){
			try{
				Thread.sleep(200);
			} catch(InterruptedException e){}
		}
		canvas.remove(enterName);
		canvas.remove(nameField);
		canvas.remove(submit);
		return name;
	}

	private void startNewRound(){
		for(int i=3; i>=0; i--){
			congrLabel.setLabel("CONGRATULATIONS! : "+Integer.toString(i));
			congrLabel.sendToFront();
			congrLabel.setVisible(true);
			waitFor(750);
		}
		congrLabel.setVisible(false);
		resetTurtle();
	}

	public int updateLabels(int r, int s){
		s = s + (int) ((asphalt.getY() + asphalt.getHeight() - turtle.getY())/asphalt.getHeight()*100);
		round.setLabel("ROUND: "+Integer.toString(r));
		score.setLabel("SCORE: "+Integer.toString(s));
		return s;
	}

	public void addKeyBoardListener() {
		keyListen = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP)
					pressedKeys[Keys.K_UP.ordinal()]=1;
				if(e.getKeyCode() == KeyEvent.VK_DOWN)
					pressedKeys[Keys.K_DOWN.ordinal()]=1;
				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					pressedKeys[Keys.K_RIGHT.ordinal()]=1;
				if(e.getKeyCode() == KeyEvent.VK_LEFT)
					pressedKeys[Keys.K_LEFT.ordinal()]=1;
				if(e.getKeyCode() == KeyEvent.VK_W)
					pressedKeys[Keys.K_W.ordinal()]=1;
				if(e.getKeyCode() == KeyEvent.VK_S)
					pressedKeys[Keys.K_S.ordinal()]=1;
				if(e.getKeyCode() == KeyEvent.VK_A)
					pressedKeys[Keys.K_A.ordinal()]=1;
				if(e.getKeyCode() == KeyEvent.VK_D)
					pressedKeys[Keys.K_D.ordinal()]=1;
				if(e.getKeyCode() == KeyEvent.VK_P)
					pressedKeys[Keys.K_P.ordinal()]=1;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP)
					pressedKeys[Keys.K_UP.ordinal()]=0;
				if(e.getKeyCode() == KeyEvent.VK_DOWN)
					pressedKeys[Keys.K_DOWN.ordinal()]=0;
				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					pressedKeys[Keys.K_RIGHT.ordinal()]=0;
				if(e.getKeyCode() == KeyEvent.VK_LEFT)
					pressedKeys[Keys.K_LEFT.ordinal()]=0;
				if(e.getKeyCode() == KeyEvent.VK_W)
					pressedKeys[Keys.K_W.ordinal()]=0;
				if(e.getKeyCode() == KeyEvent.VK_S)
					pressedKeys[Keys.K_S.ordinal()]=0;
				if(e.getKeyCode() == KeyEvent.VK_A)
					pressedKeys[Keys.K_A.ordinal()]=0;
				if(e.getKeyCode() == KeyEvent.VK_D)
					pressedKeys[Keys.K_D.ordinal()]=0;
				if(e.getKeyCode() == KeyEvent.VK_P)
					pressedKeys[Keys.K_P.ordinal()]=0;
			}
		};
		canvas.addKeyListener(keyListen);
	}

	public void addGameInfoLabels() {
		round = new GLabel("ROUND: 1");
		score = new GLabel("SCORE: 0");
		round.setFont("*-18-*");
		score.setFont("*-18-*");
		round.setFont("*-bold-*");
		score.setFont("*-bold-*");
		double x = 20;
		double y = asphalt.getY()+asphalt.getHeight()+30;
		canvas.add(round, x, y);
		canvas.add(score, x, y+25);
		GLabel p = new GLabel("Player");
		GLabel s = new GLabel("Score");
		Font f = new Font("Arial", Font.BOLD, 15);
		p.setFont(f);
		s.setFont(f);
		canvas.add(s, canvas.getWidth()-s.getWidth()-20, 30);
		canvas.add(p, s.getX()-p.getWidth()-20, 30);
		highScore1.add(p);
		highScore2.add(s);
		for(int i=1; i<11; i++){
			GLabel gl1 = new GLabel(" ");
			GLabel gl2 = new GLabel(" ");
			gl1.setFont(f);
			gl2.setFont(f);
			gl1.setColor(Color.WHITE);
			gl2.setColor(Color.WHITE);
			highScore1.add(gl1);
			highScore2.add(gl2);
			canvas.add(gl1, highScore1.get(i-1).getX(),highScore1.get(i-1).getY()+gl1.getHeight());
			canvas.add(gl2, highScore2.get(i-1).getX(),highScore2.get(i-1).getY()+gl2.getHeight());
		}
	}

	public void moveTurtle(double elapsedTime, double turtleSpeed){
		double x_move = (pressedKeys[Keys.K_RIGHT.ordinal()]-pressedKeys[Keys.K_LEFT.ordinal()])*elapsedTime*turtleSpeed;
		double y_move = (pressedKeys[Keys.K_DOWN.ordinal()]-pressedKeys[Keys.K_UP.ordinal()])*elapsedTime*turtleSpeed;
		turtle.move(x_move, y_move);
		turtle.setLocation(Math.max(0, turtle.getX()),Math.max(0, turtle.getY()));
		turtle.setLocation(Math.min(canvas.getWidth()-turtle.getWidth(), turtle.getX()),Math.min(canvas.getHeight()-turtle.getHeight(), turtle.getY()));
	}

	public boolean checkCollision(){
		for(Vehicle v : objects){
			if(v.checkCollisionByParts(turtle.getBounds())){
				return true;
			}
		}
		return false;
	}

	public boolean checkPassed(){
		if(turtle.getY()+turtle.getHeight()<=asphalt.getY()){
			startNewRound();
			return true;	
		}
		return false;
	}

	public void addObject(Vehicle g) {
		int asphaltParts = 10;
		int d = g.getDirection();
		int l = g.getLane() % asphaltParts;
		double x = d > 0 ? -g.getWidth() : asphalt.getWidth();
		double y = (l+1)*(asphalt.getHeight()-g.getHeight())/(asphaltParts+1) + asphalt.getY();
		g.setLocation(x, y);
		for(Vehicle v : objects){
			if(v.getBounds().intersects(g.getBounds())){	return;		}
		}
		canvas.add(g);
		objects.add(g);
	}

	/**
	 * Move each vehicle on the arraylist one step in their corresponding direction
	 * 
	 * @param veh	visible vehicles	
	 */
	public void moveVehicles(double elapsedTime, double vehicleSpeed){
		if(elapsedTime > 1000){
			elapsedTime = 100;
		}
		Vector<Vehicle> outVehicles = new Vector<Vehicle>();
		for(Vehicle g : objects){
			double x_move = elapsedTime*vehicleSpeed*g.getDirection();
			g.move(x_move, 0);
			if(!g.getBounds().intersects(asphalt.getBounds())){
				outVehicles.add(g);
			}
		}
		deleteObjects(outVehicles);
	}

	public void deleteObjects(Vector<Vehicle> outVehicles){
		for(Vehicle v : outVehicles){
			canvas.remove(v);
			objects.remove(v);
		}
	}

	public boolean gameOver(){
		endLabel.sendToFront();
		endLabel.setVisible(true);
		startButton.setVisible(true);
		exitButton.setVisible(true);
		while(name != ""){
			try{
				Thread.sleep(200);
			} catch(InterruptedException e){}
		}
		endLabel.setVisible(false);
		startButton.setVisible(false);
		exitButton.setVisible(false);
		return true;
	}

	public void updateHighScores(ArrayList<String[]> hS){
		int i=0;
		for(String[] s : hS){
			highScore1.get(i+1).setLabel(s[0]);
			highScore2.get(i+1).setLabel(s[1]);
			i++;
		}
	}

	public void waitFor(long millisecs) {
		try{
			Thread.sleep(millisecs);
		} catch(InterruptedException e){
		}
	}
}

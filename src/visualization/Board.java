package visualization;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GPoint;

public class Board implements BoardIntf {
	private JFrame frame;
	private GCanvas canvas;
	private GImage asphalt;
	private GImage turtle;
	private GImage explode;
	private GLabel round, score;
	private GLabel highScore;
	private KeyListener keyListen;
	enum Keys {
		K_UP,K_DOWN,K_RIGHT,K_LEFT,K_W,K_S,K_A,K_D,K_P,
		MAX // be sure that MAX always the last one
	}
	private int[] pressedKeys = new int [Keys.MAX.ordinal()];

	public Board(String boardName, int width, int height) {
		asphalt = new GImage("asfalt.jpg");
		turtle = new GImage("turtle.png");
		explode = new GImage("explode.png");
		setCanvas(boardName, width, height);
	}

	public void setCanvas(String boardName, int width, int height) {
		frame = new JFrame(boardName);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new GCanvas();
		frame.getContentPane().add(canvas);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		canvas.setVisible(false);
		setBackground();
	}

	public void setBackground() {
		canvas.setBackground(Color.LIGHT_GRAY);
		double r = 150;
		asphalt.setSize(canvas.getWidth(),canvas.getHeight()-r);
		asphalt.setLocation(0,r/2);
		canvas.add(asphalt);
		turtle.scale(0.5);
		canvas.add(turtle);
		addGameInfoLabels();
		addKeyBoardListener();
		startNewRound();
		canvas.setVisible(true);
	}

	public void startNewRound(){
		objects.clear();
		double x = canvas.getWidth()/2-turtle.getWidth()/2;
		double y = asphalt.getY()+asphalt.getHeight();
		turtle.setLocation(x,y);
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
		round = new GLabel("ROUND:");
		score = new GLabel("SCORE:");
		round.setFont("*-18-*");
		score.setFont("*-18-*");
		double x = 20;
		double y = asphalt.getY()+asphalt.getHeight()+30;
		canvas.add(round, x, y);
		canvas.add(score, x, y+25);
	}

	public void moveTurtle(double elapsedTime, double turtleSpeed){
		double x_move = (pressedKeys[Keys.K_RIGHT.ordinal()]-pressedKeys[Keys.K_LEFT.ordinal()])*elapsedTime*turtleSpeed;
		double y_move = (pressedKeys[Keys.K_DOWN.ordinal()]-pressedKeys[Keys.K_UP.ordinal()])*elapsedTime*turtleSpeed;
		turtle.move(x_move, y_move);
	}

	public void addObject(GObject g) {
	}

	public void waitFor(long millisecs) {
	}
}

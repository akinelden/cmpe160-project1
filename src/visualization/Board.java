package visualization;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	}

	public void startNewRound(){
		objects.clear();
		double x = canvas.getWidth()/2-turtle.getWidth()/2;
		double y = asphalt.getY()+asphalt.getHeight();
		turtle.setLocation(x,y);
	}

	public void addKeyBoardListener() {
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

	public void addObject(GObject g) {
	}

	public void waitFor(long millisecs) {
	}
}

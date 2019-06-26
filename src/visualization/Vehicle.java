package visualization;

import java.awt.Color;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;

public abstract class Vehicle extends GCompound {

	protected GRect body;
	protected GRect[] windows;
	protected GOval[] wheels;
	protected GLabel label;
	
	protected int width, height, windowLength, wheelCircle;
	protected int direction, lane;

	public Vehicle(int d, int l, int h){
		direction = d; // -1 : left,	1 : right
		lane = l;
		height = h;
		width = 2*height;
		windowLength = height/3;
		wheelCircle = height/4;
	}
	// TODO: Review the parameters
	public void addBody(Color color, int objY){
		body = new GRect(width,height);
		body.setFillColor(color);
		body.setFilled(true);
		this.add(body);
	}
	
	public void addWindows(int winNo, int objY){
		windows = new GRect[winNo];
		double space = (width-winNo*windowLength)/(winNo+3);
		for(int i=0; i<windows.length; i++){
			GRect win = new GRect(2*space+i*(windowLength+space), height/5, windowLength, windowLength);
			win.setFillColor(Color.WHITE);
			win.setFilled(true);
			windows[i]=win;
			this.add(win);
		}
	}

	public void addWheels(int wheelNo, int objY){
		wheels = new GOval[wheelNo];
		for(int i=0; i<wheels.length; i++){
			GOval wh = new GOval(width/(2*wheelNo)-wheelCircle/2, height-wheelCircle/2, wheelCircle, wheelCircle);
			wh.setFillColor(Color.BLACK);
			wh.setFilled(true);
			wh.move(i*width/wheelNo, 0);
			wheels[i]=wh;
			this.add(wh);
		}
	}

	public void addLabel(String l, String font){
		label = new GLabel(l);
		double x = direction < 0 ? width/10 : 9*width/10-label.getWidth();
		double y = windows[0].getY()+windowLength+height/5;
		label.setFont(font);
		label.setFont("*-bold-*");
		label.setColor(Color.WHITE);
		this.add(label,x,y);
	}

	public int getDirection(){
		return direction;
	}
	
	public int getLane() {
		return lane;
	}
	// TODO: Check finalize
	@Override
	public void finalize(){
		System.out.println(label.getLabel()+" DESTROYED");
	}
	
}

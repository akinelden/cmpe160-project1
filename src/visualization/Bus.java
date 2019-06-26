package visualization;

import java.awt.Color;
import acm.graphics.GRect;

public class Bus extends Vehicle{

	public Bus(int d, int l){
		super(d, l, 150);
		super.addBody(Color.RED,1);
		super.addWindows(3,1);
		super.addWheels(4,1);
		super.addLabel("BUS","*-16-*");
	}	
}

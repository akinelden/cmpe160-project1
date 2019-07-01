package visualization;

import java.awt.Color;

public class Bus extends Vehicle{

	public Bus(int d, int l){
		super(d, l, 150);
		super.addBody(Color.RED);
		super.addWindows(3);
		super.addWheels(4);
		super.addLabel("BUS","*-16-*");
	}	
}

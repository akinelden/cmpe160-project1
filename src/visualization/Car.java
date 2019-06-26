package visualization;

import java.awt.Color;

public class Car extends Vehicle{

	public Car(int d, int l){
		super(d, l, 100);
		super.addBody(Color.BLUE,1);
		super.addWindows(2,1);
		super.addWheels(2, 1);
		super.addLabel("CAR", "*-14-*");
	}	
}

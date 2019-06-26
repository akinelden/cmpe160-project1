package visualization;

import java.util.ArrayList;

public interface BoardIntf {
	ArrayList<Vehicle> objects = new ArrayList<Vehicle>();
	
	public void setCanvas(String boardName, int width, int height, int margin);
	public void setBackground(int margin);
	public void addKeyBoardListener();
	public void addGameInfoLabels();
	public void addObject(Vehicle g);
	public void waitFor(long millisecs);
}

package game;

public class Spot {
	boolean _state;
	int _x;
	int _y;
	
	
	public Spot(int x, int y) {
		_state = false;
		_x = x;
		_y = y;
	}
	
	public boolean getState() {
		return _state;
	}
	
	public void setState(boolean b) {
		_state = b;
	}
	
	public void toggleSpot() {
		_state = !_state;
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
}

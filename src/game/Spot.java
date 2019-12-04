package game;

import java.awt.Point;

public class Spot {
	boolean _state;
	Point _p;
	
	public Spot(int x, int y) {
		_state = false;
		_p = new Point(x,y);
	}
	
	public Spot(Point p) {
		this((int) p.getX(), (int) p.getY());
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
		return (int) _p.getX();
	}
	
	public int getY() {
		return (int) _p.getY();
	}
	
	public Point getPoint() {
		return _p;
	}
}

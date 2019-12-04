package a8;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameModel implements Iterable<Spot>{

	private Spot[][] _spots;
	private List<GameObserver> _observers;
	private int _lowBirth;
	private int _highBirth;
	private int _lowDeath;
	private int _highDeath;
	private boolean _torus;
	
	public GameModel(int X, int Y) {
		_spots = new Spot[X][Y];
		_observers = new ArrayList<GameObserver>();
		_lowBirth = 3;
		_highBirth = 3;
		_lowDeath = 2;
		_highDeath = 3;
		_torus = false;
		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {
				_spots[x][y] = new Spot(x,y);
			}
		}
	}
	
	public void clearField() {
		for (Spot s : this) {
			s.setState(false);
		}
	}
	
	public void setThresholds(int lowB, int highB, int lowD, int highD) {
		setLowBirth(lowB);
		setHighBirth(highB);
		setLowDeath(lowD);
		setHighDeath(highD);
	}
	
	public void setLowBirth(int i) {
		_lowBirth = i;
	}
	
	public void setHighBirth(int i) {
		_highBirth = i;
	}
	
	public void setLowDeath(int i) {
		_lowDeath = i;
	}
	
	public void setHighDeath(int i) {
		_highDeath = i;
	}
	
	public void toggleTorus() {
		_torus = !_torus;
	}
	
	public void setTorus(boolean b) {
		_torus = b;
	}
	
	public int getWidth() {
		return _spots.length;
	}
	
	public int getHeight() {
		return _spots[0].length;
	}
	
	public Spot getSpot(int x, int y) {
		validate(x,y);
		return _spots[x][y];
	}
	
	public void advance() {
		List<Spot> spotsToChange = new ArrayList<Spot>();
	
		for (Spot s : this) {
			if (spotNeedsToChange(s)) {
				spotsToChange.add(s);
			}
		}
		
		for (Spot s : spotsToChange) {
			s.toggleSpot();
		}
		
		updateObservers();
	}
	
	private boolean spotNeedsToChange(Spot s) {
		boolean spot = s.getState();
		int counter = spot ? -1 : 0;
		
		if (!_torus) {
			for (int x = s.getX() - 1; x <= s.getX()+1; x++) {
				if (x >= getWidth() || x < 0) continue;
				for (int y = s.getY() - 1; y <= s.getY() + 1; y++) {
					if (y >= getHeight() || y < 0) continue;
					if (getSpot(x,y).getState()) counter++;
				}
			}
		}
		
		else {
			int spotX; int spotY;
			for (int x = s.getX() - 1; x <= s.getX()+1; x++) {
				spotX = x;
				if (spotX >= getWidth()) spotX = 0;
				if (spotX < 0) spotX = getWidth() -1;
				for (int y = s.getY() - 1; y <= s.getY() + 1; y++) {
					spotY = y;
					if (spotY >= getHeight()) spotY = 0;
					if (spotY < 0) spotY = getHeight() -1;
	
					if (getSpot(spotX,spotY).getState()) counter++;
				}
			}
		}

		if (counter < _lowDeath || counter > _highDeath) return spot;
		if (spot) return false;
		return (counter >= _lowBirth && counter <= _highBirth); 
	}
	
	public void toggleSpot(int x, int y) {
		validate(x,y);
		_spots[x][y].toggleSpot();
		updateObservers();
	}
	
	
	private void validate (int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())
			throw new RuntimeException();
	}
	
	public void addObserver(GameObserver o) {
		_observers.add(o);
	}
	
	public void removeObserver(GameObserver o) {
		_observers.remove(o);
	}
	
	private void updateObservers() {
		for (GameObserver o : _observers) {
			o.update(this);
		}
	}

	@Override
	public Iterator<Spot> iterator() {
		// TODO Auto-generated method stub
		return new GameModelIterator(this);
	}
}

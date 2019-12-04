package a8;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class GameModelIterator implements Iterator<Spot> {

	private GameModel _model;
	int _x;
	int _y;
	
	public GameModelIterator(GameModel model) {
		_model = model;
		_x = 0;
		_y = 0;
	}

	@Override
	public boolean hasNext() {
		return (_y < _model.getHeight());
	}

	@Override
	public Spot next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Spot s = _model.getSpot(_x, _y);
		if (_x < _model.getWidth()-1) {
			_x++;
		} else {
			_x = 0;
			_y++;
		}
		return s;
	}

}

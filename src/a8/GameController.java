package a8;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameController implements GameViewListener, GameObserver {
	
	GameModel _model;
	GameView _view;
	JFrame _main;
	private boolean _toggle;
	
	public GameController(GameModel model, GameView view, JFrame mainFrame) {
		_model = model;
		_view = view;
		_main = mainFrame;
		_toggle = false;
		
		model.addObserver(this);
		view.addGameViewListener(this);
	}
	
	@Override
	public void update(GameModel m) {
		// TODO Auto-generated method stub
		_model = m;
	}

	@Override
	public void handleGameViewEvent(String command) {
		switch(command.toLowerCase()) {
		case "toggle cells":
			_toggle = !_toggle;
			break;
		case "clear field":
			clearField();
			break;
		case "randomly fill field":
			randomFill();
			break;
		case "advance":
			advance();
			break;
		case "enable torus":
			_model.toggleTorus();
		}
	}
	
	public void handleGameViewMouseEvent(int x, int y) {
		if (!_toggle) return;
		_model.getSpot(x, y).toggleSpot();
		if (_model.getSpot(x, y).getState()) {
			_view.fillSpot(x,y);
		}
		else {
			_view.emptySpot(x, y);
		}
		
	}
	
	private void clearField() {
		_model.clearField();
		_view.clearField();
	}
	
	private void randomFill() {
		clearField();
		for (Spot s : _model) {
			if (Math.random() < .5) {
				s.toggleSpot();
				_view.fillSpot(s.getX(), s.getY());
			}
		}
		
	}
	
	private void advance() {
		_model.advance();
		for (Spot s : _model) {
			if (s.getState()) {
				_view.fillSpot(s.getX(), s.getY());
			}
			else {
				_view.emptySpot(s.getX(), s.getY());
			}
		}
	}

	@Override
	public void handleGameViewChangesEvent(
			String X, String Y,
			String LowB, String HighB,
			String LowD, String HighD) {
		int newX; int newY; int lowB; int highB; int lowD; int highD;
		try {
			 newX = Integer.parseInt(X);
			 newY = Integer.parseInt(Y);
			 lowB = Integer.parseInt(LowB);
			 highB = Integer.parseInt(HighB);
			 lowD = Integer.parseInt(LowD);
			 highD = Integer.parseInt(HighD);
			 if (newX < 0 || newY < 0 || lowB < 0 ||
				 highB < 0 || lowD < 0 || highD < 0) {
				 throw new Exception("No negative values");
			 }
			 
			 if (newX < 1 || newY < 1) {
				 throw new Exception("X or Y values too small");
			 }
			 
			 if (lowB > highB || lowD > highD) {
				 throw new Exception("Birth or death rates are not valid");
			 }
			 
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
			return;
		}
		_main.setVisible(false);
		_main = new JFrame();
		_main.setTitle("Conway Game of Life");
		_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		_model = new GameModel(newX,newY);
		_view = new GameView(newX,newY);
		_model.setThresholds(lowB, highB, lowD, highD);
		_view.setThresholds(lowB, highB, lowD, highD);
		_model.setTorus(false);
		_toggle = false;
		
		_model.addObserver(this);
		_view.addGameViewListener(this);
		
		_main.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		_main.setContentPane(_view);
		_main.pack();
		_main.setVisible(true);
	}


}

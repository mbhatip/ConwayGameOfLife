package a8;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;



public class GameView extends JPanel implements ActionListener, PanelListener {
	
	private JPanel[][] _panels;
	private List<GameViewListener> _listeners;
	
	
	private JTextField _newX;
	private JTextField _newY;
	private int _width;
	private int _height;
	private JTextField _lowBirth;
	private JTextField _highBirth;
	private JTextField _lowDeath;
	private JTextField _highDeath;
	private gridPanel _gridPanel;
	
	public GameView(Integer width, Integer height) {
		
		_width = width;
		_height = height;
		_panels = new JPanel[width][height];
		_listeners = new ArrayList<GameViewListener>();
		
		setLayout(new BorderLayout());
		
		_gridPanel = new gridPanel(_width, _height);
		add(_gridPanel, BorderLayout.CENTER);
				
		
		JPanel subpanel = new JPanel();
		subpanel.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,2));
		buttonPanel.add(new JToggleButton("Toggle cells"));
		buttonPanel.add(new JButton("Clear field"));
		buttonPanel.add(new JToggleButton("Enable torus"));
		buttonPanel.add(new JButton("Randomly fill field"));
		
		subpanel.add(buttonPanel, BorderLayout.WEST);
		
		JPanel customizePanel = new JPanel();
		_newX = new JTextField(3);
		_newX.setText(width.toString());
		_newY = new JTextField(3);
		_newY.setText(height.toString());
		
		_lowBirth = new JTextField();
		//_lowBirth.setText("3");
		
		_highBirth = new JTextField();
		//_highBirth.setText("3");
		
		_lowDeath = new JTextField();
		//_lowDeath.setText("2");
		
		_highDeath = new JTextField();
		//_highDeath.setText("3");
		
		setTextFields(3,3,2,3);
		
		customizePanel.setLayout(new GridLayout(2,6));
		customizePanel.add(new JLabel("New X: "));
		customizePanel.add(_newX);
		customizePanel.add(new JLabel("Low Birth: "));
		customizePanel.add(_lowBirth);
		customizePanel.add(new JLabel("Low Death: "));
		customizePanel.add(_lowDeath);
		customizePanel.add(new JLabel("New Y: "));
		customizePanel.add(_newY);
		customizePanel.add(new JLabel("High Birth: "));
		customizePanel.add(_highBirth);
		customizePanel.add(new JLabel("High Death: "));
		customizePanel.add(_highDeath);
		
		subpanel.add(customizePanel, BorderLayout.CENTER);

		JPanel buttonPanel2 = new JPanel();
		buttonPanel2.setLayout(new BorderLayout());
		buttonPanel2.add(new JButton("Advance"), BorderLayout.NORTH);
		buttonPanel2.add(new JButton("Apply Changes"), BorderLayout.SOUTH);
		
		subpanel.add(buttonPanel2, BorderLayout.EAST);
		add(subpanel, BorderLayout.SOUTH);
		
		for(Component c: buttonPanel.getComponents()) {
			AbstractButton b;
			b = (AbstractButton) c;
			
			b.addActionListener(this);
		}
		for(Component c: buttonPanel2.getComponents()) {
			AbstractButton b;
			b = (AbstractButton) c;
			b.addActionListener(this);
		}
		
		_gridPanel.addListener(this);
		
		this.setFocusable(true);
		this.grabFocus();
		
	}
	
	public void fillSpot(int x, int y) {
		_gridPanel.fillRect(x,y);
	}
	
	public void emptySpot(int x, int y) {
		_gridPanel.emptyRect(x,y);
	}
	
	public void clearField() {
		_gridPanel.clearField();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (GameViewListener l : _listeners) {
			if (e.getActionCommand().toLowerCase().equals("apply changes")) {
				l.handleGameViewChangesEvent(
						_newX.getText(),
						_newY.getText(),
						_lowBirth.getText(),
						_highBirth.getText(),
						_lowDeath.getText(),
						_highDeath.getText());
			}
			else {
				l.handleGameViewEvent(e.getActionCommand());
			}
			
		}
	}
	
	public void addGameViewListener(GameViewListener l) {
		_listeners.add(l);
	}

	public void setTextFields( Integer lb, Integer hb,
			Integer ld, Integer hd) {
		_lowBirth.setText(lb.toString());
		_highBirth.setText(hb.toString());
		_lowDeath.setText(ld.toString());
		_highDeath.setText(hd.toString());
	}

	public void updatePanel(int x, int y) {
		for (GameViewListener l : _listeners) {
			l.handleGameViewMouseEvent(x, y);
		}
	}
}


class gridPanel extends JPanel implements MouseListener {
	private int _width;
	private int _height;
	private int _rectW;
	private int _rectH;
	private Rectangle[][] _rect;
	List<Rectangle> _rectToFill;
	List<PanelListener> _listeners;
	
	public gridPanel(int w, int h) {
		setSize(700,600);
		_width = w;
		_height = h;
		_rect = new Rectangle[_width][_height];
		_rectToFill = new ArrayList<Rectangle>();
		
		_rectW = getWidth() / _width;
		_rectH = getHeight() / _height;
		
		for (int x = 0; x < _width; x++) {
			for (int y = 0; y < _height; y++) {
				_rect[x][y] = new Rectangle(x * _rectW, y * _rectH, _rectW, _rectH);
			}
		}
		
		clearField();
		_listeners = new ArrayList<PanelListener>();
		addMouseListener(this);
		trigger_update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		g2d.setColor(Color.gray);
		for (int x = 0; x < _width; x++) {
			for (int y = 0; y < _height; y++) {
				g2d.setColor(Color.gray);
				if(_rectToFill.contains(_rect[x][y])) {
					g2d.setColor(Color.yellow);
				}
				g2d.fill(_rect[x][y]);
				g2d.setColor(Color.black);
				g2d.draw(_rect[x][y]);
			}
		}
	}
	
	public void clearField() {
		_rectToFill.clear();
		trigger_update();
	}
	
	public void fillRect(int x, int y) {
		Rectangle r = _rect[x][y];
		if (!_rectToFill.contains(r)) {
			_rectToFill.add(r);
		}
		trigger_update();
	}
	
	public void emptyRect(int x, int y) {
		_rectToFill.remove(_rect[x][y]);
		trigger_update();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());
		if (!contains(p)) {return;}
		int X = -1; int Y = -1;
		for (int x = 0; x < _width; x++) {
			for (int y = 0; y < _height; y++) {
				if(_rect[x][y].contains(p)) {
					X=x; Y=y;
				}
			}
		}
		if (X == -1 || Y == -1) {return;}
		
		for (PanelListener l : _listeners) {
			l.updatePanel(X,Y);
		}
	}

	public void addListener(PanelListener p) {
		_listeners.add(p);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void trigger_update() {		
		repaint();

		// Not sure why, but need to schedule a call
		// to repaint for a little bit into the future
		// as well as the one we just did above
		// in order to make sure that we don't end up
		// with visual artifacts due to race conditions.
		
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
				repaint();
			}
		}).start();

	}
	
}

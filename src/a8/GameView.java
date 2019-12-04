package a8;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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


public class GameView extends JPanel implements ActionListener, MouseListener{
	
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
	
	private JPanel _gridPanel;
	
	public GameView(Integer width, Integer height) {
		
		_width = width;
		_height = height;
		
		_panels = new JPanel[width][height];
		_listeners = new ArrayList<GameViewListener>();
		
		setLayout(new BorderLayout());
		
		_gridPanel = new JPanel();
		_gridPanel.setLayout(new GridLayout(height, width));
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				_panels[x][y] = new JPanel();
				_gridPanel.add(_panels[x][y]);
				
				emptySpot(x,y);
				_panels[x][y].setBorder(BorderFactory.createLineBorder(Color.black));
				_panels[x][y].setPreferredSize(new Dimension(500/width,500/height));;
			}
		}
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
		
		_lowBirth = new JTextField(3);
		//_lowBirth.setText("3");
		
		_highBirth = new JTextField(3);
		//_highBirth.setText("3");
		
		_lowDeath = new JTextField(3);
		//_lowDeath.setText("2");
		
		_highDeath = new JTextField(3);
		//_highDeath.setText("3");
		
		setThresholds(3,3,2,3);
		
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
		buttonPanel2.add(new JButton("Apply Changes"), BorderLayout.NORTH);
		buttonPanel2.add(new JButton("Advance"), BorderLayout.SOUTH);
		
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
		
		this.setFocusable(true);
		this.grabFocus();
		
		addMouseListener(this);
		
	}
	
	public void fillSpot(int x, int y) {
		_panels[x][y].setBackground(Color.YELLOW);
	}
	
	public void emptySpot(int x, int y) {
		_panels[x][y].setBackground(Color.GRAY);
	}
	
	public void clearField() {
		for (int x = 0; x < _panels.length; x++) {
			for (int y = 0; y < _panels[0].length; y++) {
				emptySpot(x,y);
			}
		}
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

	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		double maxWidth = _gridPanel.getWidth();
		double maxHeight = _gridPanel.getHeight();
		
		int x = (int) ((double) e.getX() * _width / maxWidth);
		int y = (int) ((double) e.getY() * _height / maxHeight);
		if (x >= _width || y >= _height) { return;}
		
		for (GameViewListener l : _listeners) {
				l.handleGameViewMouseEvent(y, x);
		}
	}
	
	public void setThresholds( Integer lb, Integer hb,
			Integer ld, Integer hd) {
		_lowBirth.setText(lb.toString());
		_highBirth.setText(hb.toString());
		_lowDeath.setText(ld.toString());
		_highDeath.setText(hd.toString());
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
	
}

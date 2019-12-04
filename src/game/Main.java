package game;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("Conway Game of Life");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GameModel model = new GameModel(10,10);
		GameView view = new GameView(10,10);
		GameController controller = new GameController(model, view, mainFrame);
		
		mainFrame.setContentPane(view);
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setSize(700,700);
		//mainFrame.setResizable(false);
	}

}

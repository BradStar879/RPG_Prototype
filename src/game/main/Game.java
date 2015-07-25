package game.main;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class Game {
	
	public static JFrame frame;
	public static void main(String[] args){
		frame = new JFrame("RPG Prototype");
		//frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(new GamePanel(), BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
}

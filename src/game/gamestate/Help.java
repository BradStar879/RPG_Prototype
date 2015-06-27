package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.sun.glass.events.KeyEvent;

public class Help extends GameState{

	public Help(GameStateManager gsm) {
		super(gsm);
	}

	
	public void init() {
		
		
	}

	
	public void tick() {
		
		
	}

	
	public void draw(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		ImageIcon help = new ImageIcon("src/pics/help.jpg");
		Image image = help.getImage();
		//Image imageSzed = image.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		g.drawImage(image, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 10, null);
		
	}

	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ESCAPE) gsm.states.push(new MenuState(gsm));
		
	}

	
	public void keyReleased(int k) {
		
		
	}
	
	

}

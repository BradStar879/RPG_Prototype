package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.sun.glass.events.KeyEvent;

import display.BufferedImageLoader;

public class Help extends GameState{
	
	BufferedImageLoader loader = new BufferedImageLoader();
	
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
		Image help = new ImageIcon("Sprites/help.jpg").getImage();
		g.drawImage(help, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 10, GamePanel.WIDTH / 2, GamePanel.HEIGHT * 4 / 5, null);
		
	}

	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ESCAPE) gsm.states.push(new MenuState(gsm));
		
	}

	
	public void keyReleased(int k) {
		
		
	}
	
	

}

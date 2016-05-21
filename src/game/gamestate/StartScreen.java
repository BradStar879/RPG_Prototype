package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class StartScreen extends GameState{
	
	int count;

	public StartScreen(GameStateManager gsm) {
		super(gsm);
	}

	
	public void init() {
		count = 0;
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("pixelmix.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
	}

	
	public void tick() {
		count++;
		if(count == 60) gsm.states.push(new MenuState(gsm));
		
	}

	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
	}

	
	public void keyPressed(int k) {
		
		
	}

	
	public void keyReleased(int k) {
		
		
	}

}

package display;

import game.gamestate.MenuState;
import game.gamestate.BaseLevel;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class PauseDisplay {
	
	int ht = GamePanel.HEIGHT;
	int wd = GamePanel.WIDTH;
	int border = ht / 62;
	int pauseWd = wd / 4;
	int pauseHt = ht / 6;
	
	boolean paused = false;
	String[] pauseOptions = new String[]{"Resume", "Options", "Quit"};
	int pauseSelect = 0;
	
	
	public PauseDisplay(){}
	
	public void draw(Graphics g) {
		
		if(paused) {
			g.setColor(Color.WHITE);
			g.fillRect(wd / 2 - pauseWd / 2, ht / 2 - pauseHt / 2, pauseWd, pauseHt);
			g.setColor(Color.BLUE);
			g.fillRect(wd / 2 - pauseWd / 2 + border, ht / 2 - pauseHt / 2 + border, pauseWd - 2 * border, pauseHt - 2 * border);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, ht / 40));
			for(int i = 0; i < 3; i++) {
				g.drawString(pauseOptions[i], wd / 2 - pauseWd / 2 + 3 * border + (pauseWd * i / 3), ht / 2 + border / 2);
			}
			
			g.setColor(Color.RED);
			g.fillRect(wd / 2 - pauseWd / 2 + border * 3 / 2 + (pauseWd * pauseSelect / 3), ht / 2 + border / 2 - border, border, border);
		}
	}
	
	public void keyPressed(int k) {
		if(paused) {
			if(k == KeyEvent.VK_LEFT) {
				if(pauseSelect == 0) pauseSelect = 2;
				else pauseSelect--;
			}
			if(k == KeyEvent.VK_RIGHT) {
				if(pauseSelect == 2) pauseSelect = 0;
				else pauseSelect++;
			}
			if(k == KeyEvent.VK_ENTER) {
				if(pauseSelect == 0) {
					unpause();
					BaseLevel.unpause();
				}
				else if(pauseSelect == 1) ;
				else if(pauseSelect == 2) BaseLevel.exit();
			}
		}
	}
	
	public void pause() {
		pauseSelect = 0;
		paused = true;
	}
	
	public void unpause() {
		paused = false;
	}
	

}

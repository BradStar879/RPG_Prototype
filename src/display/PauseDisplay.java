package display;


import game.gamestate.BaseLevel;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class PauseDisplay {
	
	int ht = GamePanel.HEIGHT;
	int wd = GamePanel.WIDTH;
	int border = ht / 62;
	int pauseWd = wd / 4;
	int pauseHt = ht / 6;
	BufferedImageLoader loader = new BufferedImageLoader();
	Image sideArrow = loader.loadImage("/SideArrow.png").getScaledInstance(border * 2, border, Image.SCALE_SMOOTH);
	Image pauseBox = new ImageIcon("Sprites/PauseBox.png").getImage();
	
	boolean paused = false;
	String[] pauseOptions = new String[]{"Resume", "Options", "Quit"};
	int pauseSelect = 0;
	
	
	public PauseDisplay() {}
	
	public void draw(Graphics g) {
		
		if(paused) {
			g.drawImage(pauseBox, wd / 2 - pauseWd / 2, ht / 2 - pauseHt / 2, pauseWd, pauseHt, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
			for(int i = 0; i < 3; i++) {
				g.drawString(pauseOptions[i], wd / 2 - pauseWd / 2 + border * 7 / 2 + (pauseWd * i / 3), ht / 2 + border / 2);
			}
			
			g.drawImage(sideArrow, wd / 2 - pauseWd / 2 + border + (pauseWd * pauseSelect / 3), ht / 2 + border / 2 - border, null);
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

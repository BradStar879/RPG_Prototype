package display;

import game.main.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class LossScreen {
	
	int wd = GamePanel.WIDTH;
	int ht = GamePanel.HEIGHT;
	int screenTick;
	float alpha;
		
	public LossScreen() {
		screenTick = 0;
		alpha = 0.0f;
	}
	
	public void tick() {
		if(screenTick < 600) screenTick++;
		if(screenTick < 30 && screenTick % 3 == 0) alpha += 0.1;
		else if(screenTick == 30) alpha = 0.0f;
		else if(screenTick < 90 && screenTick > 60 && screenTick % 3 == 0) alpha += 0.1;
		else if(screenTick < 570 && screenTick > 540 && screenTick % 3 == 0) alpha -= 0.1;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;	
		g2d.setColor(Color.BLACK);
		if(screenTick < 30) {;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		}
		g2d.fillRect(0, 0, wd, ht);
		if(screenTick > 60) {
			g2d.setFont(new Font("pixelmix", Font.BOLD, ht / 7));
			g2d.setColor(Color.WHITE);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g2d.drawString("YOU LOSE", wd / 4, ht / 2);
		}
	}

}

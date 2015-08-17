package display;

import game.gamestate.BaseLevel;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import characters.BaseCharacter;
import characters.Spells;

public class QueueDisplay {
	
	BaseCharacter[] disQueue = new BaseCharacter[3];
	int ht = GamePanel.HEIGHT;
	int wd = GamePanel.WIDTH;
	int queueWd = wd / 8;
	int queueHt = ht / 4;
	int border = ht / 62;
	Image queueBox = new ImageIcon("Sprites/QueueBox.png").getImage();
	Image shortQueueBox = new ImageIcon("Sprites/ShortQueueDisplay.png").getImage();
	Spells spell;
	
	public QueueDisplay() {}
	
	public void tick() {
		
		for(int j = 0; j < 3; j++) {
			if(disQueue[j] != null) {
			}
		}
	}
	
	public void draw(Graphics g) {
		
		for(int j = 0; j < 3; j++) {
			if(disQueue[j] != null) {
				if(j == 0 && disQueue[j] != null) {
					g.drawImage(queueBox, wd - queueWd, ht - queueHt * 2 - (j * (ht / 10)), queueWd, queueHt, null);
					spell = new Spells(BaseLevel.getMenuOption());
					g.drawString(spell.name,  wd - queueWd + border * 2,  ht - (queueHt * 2) + border * 6);
					if(spell.mpCost != -1) {
						g.drawString(disQueue[j].mpName + ": " + spell.mpCost,  wd - queueWd + border * 2,  ht - queueHt * 2 + border * 8);
						g.drawString("Cd: " + spell.cooldown / 60 + " sec",  wd - queueWd + border * 2,  ht - queueHt * 2 + border * 10);
					}
					if(disQueue[j].getCurrentCooldown() != 0) {
						g.drawString("Cd left: " + (disQueue[j].getCurrentCooldown() + 1), wd - queueWd + border * 2,  ht - queueHt * 2 + border * 12);
					}
				}
				if(j != 0) g.drawImage(shortQueueBox, wd - queueWd, ht - queueHt * 2 - (j * (ht / 10)), queueWd, ht / 10, null);
				g.setColor(Color.WHITE);
				g.setFont(new Font("pixelmix", Font.PLAIN, ht / 50));
				g.drawString(disQueue[j].getName(), wd - queueWd + border * 2,  ht - 2 * queueHt - (j * (ht / 10)) + border * 7 / 2);
			}
		}
	}
	
	public void enqueue(BaseCharacter q) {
		if(disQueue[0] == null) {
			disQueue[0] = q;
		}
		else if(disQueue[1] == null) {
			disQueue[1] = q;
		}
		else {
			disQueue[2] = q;
		}
	}
	
	public void dequeue() {
		disQueue[0] = null;
		if(disQueue[1] != null) {
			disQueue[0] = disQueue[1];
			disQueue[1] = null;
		}
		if(disQueue[2] != null) {
			disQueue[1] = disQueue[2];
			disQueue[2] = null;
		}
	}

}

package display;

import game.gamestate.BaseLevel;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import characters.BaseCharacter;

public class QueueDisplay {
	
	BaseCharacter[] disQueue = new BaseCharacter[3];
	int ht = GamePanel.HEIGHT;
	int wd = GamePanel.WIDTH;
	int queueWd = wd / 8;
	int queueHt = ht / 4;
	int border = ht / 62;
	String[][] movesOnCooldown = new String[3][4];
	String[][] spellsOnCooldown = new String[3][4];
	int[] amountMovesOnCooldown = new int[3];
	int[] amountSpellsOnCooldown = new int[3];
	int[][] moveCooldowns = new int[3][4];
	int[][] spellCooldown = new int[3][4];
	
	public QueueDisplay() {}
	
	public void tick() {
		
		for(int j = 0; j < 3; j++) {
			if(disQueue[j] != null) {
				movesOnCooldown[j] = disQueue[j].getMovesOnCooldown();
				amountMovesOnCooldown[j] = disQueue[j].getMovesOnCooldown().length;
				moveCooldowns[j] = disQueue[j].getMoveSetCooldowns();
				spellsOnCooldown[j] = disQueue[j].getSpellsOnCooldown();
				amountSpellsOnCooldown[j] = disQueue[j].getSpellsOnCooldown().length;
				spellCooldown[j] = disQueue[j].getSpellSetCooldowns();
			}
		}
	}
	
	public void draw(Graphics g) {
		
		for(int j = 0; j < 3; j++) {
			if(disQueue[j] != null) {
				g.setColor(Color.WHITE);
				g.fillRect(wd - queueWd, ht - (queueHt * (2 + j)), queueWd, queueHt);
				g.setColor(Color.BLUE);
				g.fillRect(wd - queueWd + border, ht - (queueHt * (2 + j)) + border, queueWd - 2 * border, queueHt - 2 * border);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, ht / 40));
				g.drawString(disQueue[j].getName(), wd - queueWd + border * 2,  ht - (queueHt * (2 + j)) + border * 3);
				for(int i = 0; i < amountMovesOnCooldown[j]; i++)
					g.drawString(movesOnCooldown[j][i] + ": " + moveCooldowns[j][i],  wd - queueWd + border * 2, ht - (queueHt * (2 + j)) + border * 3 + (2 * border * (i + 1)));
				for(int i = 0; i < amountSpellsOnCooldown[j]; i++)
					g.drawString(spellsOnCooldown[j][i] + ": " + spellCooldown[j][i],  wd - queueWd + border * 2, ht - (queueHt * (2 + j)) + border * 3 + (2 * border * (i + 1 + amountMovesOnCooldown[j])));
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

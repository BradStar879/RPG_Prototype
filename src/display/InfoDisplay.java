package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import characters.BaseCharacter;

public class InfoDisplay {
	
	int infoWd;
	int infoHt;
	int ht;
	int border;
	BaseCharacter c;
	int factor;
	
	public InfoDisplay(int infoWd, int infoHt, int ht, int border, BaseCharacter c, int factor) {
		this.infoWd = infoWd;
		this.infoHt = infoHt;
		this.ht = ht;
		this.border = border;
		this.c = c;
		this.factor = factor;
	}
	
	public void draw(Graphics g) {
		
		//Top right text of info box
		g.setColor(c.getColor());
		g.fillRect(2 * border, 2 * border + (factor * (infoHt + 2 * border)), infoWd * 45 / 100, infoHt / 2);
		g.setFont(new Font("Arial", Font.PLAIN, ht / 40));
		g.setColor(Color.WHITE);
		g.drawString(c.getName(), infoWd * 45 / 100 + 3 * border, 4 * border + (factor * (infoHt + 2 * border)));
		g.drawString("Level: " + c.getLevel(), infoWd * 45 / 100 + 3 * border, 7 * border + (factor * (infoHt + 2 * border)));
		g.setColor(Color.RED);
		if(!c.getAlive()) g.drawString("DEAD", infoWd * 45 / 100 + 3 * border, 9 * border + (factor * (infoHt + 2 * border)));
				
		//Hp text and bar of info box
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, ht / 60));
		g.drawString("HP: " + c.getHp() + " / " + c.getMaxHp(), 2 * border, infoHt / 2 + 3 * border + (border / 2) + (factor * (infoHt + 2 * border)));
		if((double)c.getHp() / (double)c.getMaxHp() >= .75) g.setColor(Color.GREEN);
		else if((double)c.getHp() / (double)c.getMaxHp() >= .5) g.setColor(Color.ORANGE);
		else if((double)c.getHp() / (double)c.getMaxHp() >= .25) g.setColor(Color.YELLOW);
		else g.setColor(Color.RED);
		g.fillRect(3 * border, infoHt / 2 + 3 * border + (border * 2 / 3) + (factor * (infoHt + 2 * border)), (infoWd * 3 / 4) * c.getHp() / c.getMaxHp(), border);
		g.setColor(Color.BLACK);
		g.drawRect(3 * border, infoHt / 2 + 3 * border + (border * 2 / 3) + (factor * (infoHt + 2 * border)), infoWd * 3 / 4, border);
				
		//Mp text and bar of info box
		g.setColor(Color.WHITE);
		g.drawString("MP: " + c.getMp() + " / " + c.getMaxMp(), 2 * border, infoHt / 2 + 6 * border + (factor * (infoHt + 2 * border)));
		g.setColor(Color.MAGENTA);
		g.fillRect(3 * border, infoHt / 2 + 6 * border + (border / 3) + (factor * (infoHt + 2 * border)), (infoWd * 3 / 4) * c.getMp() / c.getMaxMp(), border);
		g.setColor(Color.BLACK);
		g.drawRect(3 * border, infoHt / 2 + 6 * border + (border / 3) + (factor * (infoHt + 2 * border)), infoWd * 3 / 4, border);
				
		//Timer text and bar of info box
		g.setColor(Color.WHITE);
		g.drawString("Time", 2 * border, infoHt / 2 + 8 * border + (border / 2) + (factor * (infoHt + 2 * border)));
		g.setColor(Color.CYAN);
		g.fillRect(3 * border, infoHt / 2 + 9 * border + (factor * (infoHt + 2 * border)), (infoWd * 3 / 4) * c.getTime() / c.getTimeMax(), border);
		g.setColor(Color.BLACK);
		g.drawRect(3 * border, infoHt / 2 + 9 * border + (factor * (infoHt + 2 * border)), infoWd * 3 / 4, border);
		
		
	}
	
}

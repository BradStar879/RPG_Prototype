 package display;

import game.gamestate.BaseLevel;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import characters.BaseCharacter;

public class InfoDisplay {
	
	int infoWd;
	int infoHt;
	int ht = GamePanel.HEIGHT;
	int wd = GamePanel.WIDTH;
	int border = ht / 62;
	BaseCharacter c;
	int factor;
	Image stats = new ImageIcon("Sprites/CharacterStatsBox.png").getImage();
	Image statsSelected = new ImageIcon("Sprites/CharacterStatsBoxYellow.png").getImage();
	Image green = new ImageIcon("Sprites/GreenInfoBar.png").getImage();
	Image yellow = new ImageIcon("Sprites/YellowInfoBar.png").getImage();
	Image orange = new ImageIcon("Sprites/OrangeInfoBar.png").getImage();
	Image red = new ImageIcon("Sprites/RedInfoBar.png").getImage();
	Image purple = new ImageIcon("Sprites/PurpleInfoBar.png").getImage();
	Image cyan = new ImageIcon("Sprites/CyanInfoBar.png").getImage();
	Image health;
	BaseLevel battle;
	
	public InfoDisplay(BaseCharacter c, int factor, BaseLevel battle) {
		this.c = c;
		this.factor = factor;
		this.battle = battle;
	}
	
	public void draw(Graphics g) {
		
		if(battle.getCharSelected() == factor) g.drawImage(statsSelected, 0, factor * (ht / 3), wd / 6, ht / 3, null);
		else g.drawImage(stats, 0, factor * (ht / 3), wd / 6, ht / 3, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
		g.drawString("" + c.getName(), border * 2, factor * (ht / 3) + 15 * border / 4);
		g.drawString("" + c.className, border * 2, factor * (ht / 3) + 13 * border / 2);
		g.drawString("Level: " + c.getLevel(), border * 2, factor * (ht / 3) + 9 * border);
		if(c.getHp() > 0) g.drawString("HP: " + c.getHp() + "/" + c.getMaxHp(), border * 2, factor * (ht / 3) + 47 * border / 4);
		else {
			g.setColor(Color.RED);
			g.drawString("DEAD", border * 2, factor * (ht / 3) + 47 * border / 4);
			g.setColor(Color.WHITE);
		}
		if(c.className.equals("Black Mage") || c.className.equals("White Mage")) {
			g.drawString("MP: " + 	c.getMp() + "/" + c.getMaxMp(), border * 2, factor * (ht / 3) + 61 * border / 4);
			g.drawImage(purple, border * 2, factor * (ht / 3) + border * 63 / 4, (wd / 8 + border) * c.getMp() / c.getMaxMp(), border, null);
		}
		else if(c.className.equals("Warrior")) {
			g.drawString("Rage: " + 	c.getMp() + "/" + c.getMaxMp(), border * 2, factor * (ht / 3) + 61 * border / 4);
			g.drawImage(red, border * 2, factor * (ht / 3) + border * 63 / 4, (wd / 8 + border) * c.getMp() / c.getMaxMp(), border, null);
		}
		else if(c.className.equals("Spearman")) {
			g.drawString("Energy: " + 	c.getMp() + "/" + c.getMaxMp(), border * 2, factor * (ht / 3) + 61 * border / 4);
			g.drawImage(yellow, border * 2, factor * (ht / 3) + border * 63 / 4, (wd / 8 + border) * c.getMp() / c.getMaxMp(), border, null);
		}
		else if(c.className.equals("Monk")) {
			g.drawString("Charms: " + 	c.getMp() + "/" + c.getMaxMp(), border * 2, factor * (ht / 3) + 61 * border / 4);
			g.drawImage(green, border * 2, factor * (ht / 3) + border * 63 / 4, (wd / 8 + border) * c.getMp() / c.getMaxMp(), border, null);
		}
		g.drawString("Time: ", border * 2, factor * (ht / 3) + 75 * border / 4);
		
		if((double)c.getHp() / (double)c.getMaxHp() >= .75) health = green;
		else if((double)c.getHp() / (double)c.getMaxHp() >= .5) health = yellow;
		else if((double)c.getHp() / (double)c.getMaxHp() >= .25) health = orange;
		else health = red;
		g.drawImage(health, border * 2, factor * (ht / 3) + border * 197 / 16, (wd / 8 + border) * c.getHp() / c.getMaxHp(), border,  null);
		g.drawImage(cyan, border * 2, factor * (ht / 3) + border * 309 / 16, (wd / 8 + border) * c.getTime() / c.getTimeMax(), border, null);
		
		
	}
	
}

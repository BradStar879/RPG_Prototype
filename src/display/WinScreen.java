package display;

import game.gamestate.BaseWorld;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import player.CharacterStats;

public class WinScreen{
	
	int wd = GamePanel.WIDTH;
	int ht = GamePanel.HEIGHT;
	int border = ht / 62;
	BufferedImageLoader loader = new BufferedImageLoader();
	Image winBackground = new ImageIcon("Sprites/WorldPauseBox.png").getImage();
	Image green = new ImageIcon("Sprites/GreenInfoBar.png").getImage();
	Image yellow = new ImageIcon("Sprites/YellowInfoBar.png").getImage();
	Image orange = new ImageIcon("Sprites/OrangeInfoBar.png").getImage();
	Image red = new ImageIcon("Sprites/RedInfoBar.png").getImage();
	Image purple = new ImageIcon("Sprites/PurpleInfoBar.png").getImage();
	Image black = new ImageIcon("Sprites/BlackBar.png").getImage();
	Image warriorPortrait = loader.loadImage("/Warrior_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Image blackmagePortrait = loader.loadImage("/BlackMage_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Image whitemagePortrait = loader.loadImage("/WhiteMage_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Image archerPortrait = loader.loadImage("/Archer_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Image spearmanPortrait = loader.loadImage("/Spearman_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Image monkPortrait = loader.loadImage("/Monk_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Image health;
	public boolean won = false;
	int screenHeight = -ht;
	int screenSpeed = 0;
	int screenTick = 0;
	int xp;
	int currencyWon;
	String[] itemsWon;
	CharacterStats[] team;
	public static boolean[] leveled;

	public WinScreen(int xp, int currencyWon, String[] itemsWon) {
		this.xp = xp;
		this.currencyWon = currencyWon;
		this.itemsWon = itemsWon;
		leveled = new boolean[]{false, false, false};
	}

	
	public void tick() {
		
		if(screenTick % 2 == 0) {
			
			if(screenHeight <= 0) {
				screenSpeed += wd / 300;
				screenHeight += screenSpeed;
			}
			
			if(screenHeight >= 0) {
				screenHeight = 0;
				screenSpeed /= -2;
			}
			
		}
		
		screenTick++;
		if(screenTick >= 60) screenTick = 0;
		team = BaseWorld.team;
		
	}

	
	public void draw(Graphics g) {

		g.drawImage(winBackground, 0, screenHeight, wd, ht, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
		g.drawString("Items Won:", wd * 6 / 7, (5 * border) + screenHeight);
		for(int i = 0; i < itemsWon.length; i++) g.drawString(itemsWon[i], wd * 6 / 7, (5 * border) * (i + 2) + screenHeight);
		g.drawString("Experience", wd * 6 / 7 - border * 2, border * 50 + screenHeight);
		g.drawString("Gained: +" + xp, wd * 6 / 7 - border * 2, border * 53 + screenHeight);
		g.drawString("Money Found: " + currencyWon, wd * 6 / 7 - border * 2, border * 60 + screenHeight);
		
		for(int i = 0; i < 3; i++) {
			g.setColor(Color.WHITE);
			g.drawString(team[i].name, wd / 6, ht / 10 + (i * ht / 3) + screenHeight);
			g.drawString(team[i].className, wd / 6, ht / 10 + (i * ht / 3) + border * 3 + screenHeight);
			if(team[i].hp > 0) g.drawString("HP: " + team[i].hp + " / " + team[i].maxHp, wd / 6, ht / 10 + (i * ht / 3) + border * 6 + screenHeight);
			else {
				g.setColor(Color.RED);
				g.drawString("DEAD",  wd / 6, ht / 10 + (i * ht / 3) + border * 6 + screenHeight);
				g.setColor(Color.WHITE);
			}
			if((double)team[i].hp / (double)team[i].maxHp >= .75) health = green;
			else if((double)team[i].hp / (double)team[i].maxHp >= .5) health = yellow;
			else if((double)team[i].hp / (double)team[i].maxHp >= .25) health = orange;
			else health = red;
			g.drawImage(black, wd / 6 - border / 2, ht / 10 + (i * ht / 3) + border * 7 - border / 4 + screenHeight, wd / 8 + border, border * 3 / 2, null);
			g.drawImage(health, wd / 6, ht / 10 + (i * ht / 3) + border * 7 + screenHeight, (wd / 8) * team[i].hp / team[i].maxHp, border,  null);
			g.drawImage(black, wd / 6 - border / 2, ht / 10 + (i * ht / 3) + border * 11 - border / 4 + screenHeight, wd / 8 + border, border * 3 / 2, null);
			if(team[i].className.equals("Black Mage") || team[i].className.equals("White Mage")) {
				g.drawString("MP: " + team[i].mp + "/" + team[i].maxMp, wd / 6, ht / 10 + (i * ht / 3) + border * 10 + screenHeight);
				g.drawImage(purple, wd / 6, ht / 10 + (i * ht / 3) + border * 11 + screenHeight, (wd / 8) * team[i].mp / team[i].maxMp, border, null);
			}
			else if(team[i].className.equals("Warrior")) {
				g.drawString("Rage: " + team[i].mp + "/" + team[i].maxMp, wd / 6, ht / 10 + (i * ht / 3) + border * 10 + screenHeight);
			}
			else if(team[i].className.equals("Spearman")) {
				g.drawString("Energy: " + team[i].mp + "/" + team[i].maxMp, wd / 6, ht / 10 + (i * ht / 3) + border * 10 + screenHeight);
				g.drawImage(yellow, wd / 6, ht / 10 + (i * ht / 3) + border * 11 + screenHeight, (wd / 8) * team[i].mp / team[i].maxMp, border, null);
			}
			else if(team[i].className.equals("Monk")) {
				g.drawString("Charms: " + team[i].mp + "/" + team[i].maxMp, wd / 6, ht / 10 + (i * ht / 3) + border * 10 + screenHeight);
				g.drawImage(green, wd / 6, ht / 10 + (i * ht / 3) + border * 11 + screenHeight, (wd / 8) * team[i].mp / team[i].maxMp, border, null);
			}
			g.drawString("Attack: " + team[i].attack, wd / 3, ht / 10 + (i * ht / 3) + screenHeight);
			g.drawString("Spell Power: " + team[i].spellPower, wd / 3, ht / 10 + (i * ht / 3) + border * 3 + screenHeight);
			g.drawString("Armor: " + team[i].armor, wd / 3, ht / 10 + (i * ht / 3) + border * 6 + screenHeight);
			g.drawString("Speed: " + team[i].speed, wd / 3, ht / 10 + (i * ht / 3) + border * 9 + screenHeight);
			g.drawString("Level: " + team[i].level, wd / 2 + 7 * border, ht / 5 + (i * ht / 3) - border + screenHeight);
			g.drawString("XP: " + team[i].experience + " / " + team[i].experienceCap, wd / 2 + 7 * border, ht / 5 + (i * ht / 3) + border * 2 + screenHeight);
			g.drawImage(yellow, wd / 2 + 7 * border, ht / 5 + (i * ht / 3) + border * 29 / 8 + screenHeight, (wd / 8 + border) * team[i].experience / team[i].experienceCap, border, null);
			if(leveled[i]) {
				g.setColor(Color.YELLOW);
				g.drawString("LEVEL UP!", wd / 2 + 7 * border, ht / 5 + (i * ht / 3) - border * 3 + screenHeight);
			}
			Image port;
			if(team[i].className.equals("Warrior")) port = warriorPortrait;
			else if(team[i].className.equals("Black Mage")) port = blackmagePortrait;
			else if(team[i].className.equals("White Mage")) port = whitemagePortrait;
			else if(team[i].className.equals("Archer")) port = archerPortrait;
			else if(team[i].className.equals("Spearman")) port = spearmanPortrait;
			else port = monkPortrait;
			g.drawImage(port, border * 3, i * ht / 3 + ht / 15 + screenHeight, null);
		}
		
	}

}

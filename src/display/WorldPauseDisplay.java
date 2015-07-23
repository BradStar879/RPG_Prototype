package display;

import game.gamestate.World;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import player.CharacterStats;
import player.Saver;

public class WorldPauseDisplay {
	
	int ht = GamePanel.HEIGHT;
	int wd = GamePanel.WIDTH;
	int border = ht / 62;
	int pauseWd = wd / 4;
	int pauseHt = ht / 6;
	int saveTick = 0;
	boolean subMenu = false;
	boolean saved = false;
	BufferedImageLoader loader = new BufferedImageLoader();
	Image sideArrow = loader.loadImage("/SideArrow.png").getScaledInstance(border * 4, border * 2, Image.SCALE_SMOOTH);
	Image worldPauseBox = new ImageIcon("Sprites/WorldPauseBox.png").getImage();
	Image green = new ImageIcon("Sprites/GreenInfoBar.png").getImage();
	Image yellow = new ImageIcon("Sprites/YellowInfoBar.png").getImage();
	Image orange = new ImageIcon("Sprites/OrangeInfoBar.png").getImage();
	Image red = new ImageIcon("Sprites/RedInfoBar.png").getImage();
	Image purple = new ImageIcon("Sprites/PurpleInfoBar.png").getImage();
	Image black = new ImageIcon("Sprites/BlackBar.png").getImage();
	Image health;
	
	boolean paused = false;
	String[] pauseOptions = new String[]{"Resume", "Inventory", "Save", "Quit"};
	int pauseSelect = 0;
	int invSelect = 0;
	String[] invList;
	int[] invAmts;
	int currency;
	public static CharacterStats team[];
	
	Saver saver = new Saver();
	
	
	public WorldPauseDisplay() {}
	
	public void draw(Graphics g) {
		
		if(paused) {
			
			g.drawImage(worldPauseBox, 0, 0, wd, ht, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
			for(int i = 0; i < 3; i++) {
				g.drawString(team[i].name, wd / 6, ht / 10 + (i * ht / 3));
				g.drawString(team[i].className, wd / 6, ht / 10 + (i * ht / 3) + border * 3);
				if(team[i].hp > 0) g.drawString("HP: " + team[i].hp + " / " + team[i].maxHp, wd / 6, ht / 10 + (i * ht / 3) + border * 6);
				else {
					g.setColor(Color.RED);
					g.drawString("DEAD",  wd / 6, ht / 10 + (i * ht / 3) + border * 6);
					g.setColor(Color.WHITE);
				}
				if((double)team[i].hp / (double)team[i].maxHp >= .75) health = green;
				else if((double)team[i].hp / (double)team[i].maxHp >= .5) health = yellow;
				else if((double)team[i].hp / (double)team[i].maxHp >= .25) health = orange;
				else health = red;
				g.drawImage(black, wd / 6 - border / 2, ht / 10 + (i * ht / 3) + border * 7 - border / 4, wd / 8 + border, border * 3 / 2, null);
				g.drawImage(health, wd / 6, ht / 10 + (i * ht / 3) + border * 7, (wd / 8) * team[i].hp / team[i].maxHp, border,  null);
				g.drawString("MP: " + team[i].mp + " / " + team[i].maxMp, wd / 6, ht / 10 + (i * ht / 3) + border * 10);
				g.drawImage(black, wd / 6 - border / 2, ht / 10 + (i * ht / 3) + border * 11 - border / 4, wd / 8 + border, border * 3 / 2, null);
				if(team[i].mp > 0) g.drawImage(purple, wd / 6, ht / 10 + (i * ht / 3) + border * 11, (wd / 8) * team[i].mp / team[i].maxMp, border, null);
				g.drawString("Attack: " + team[i].attack, wd / 3, ht / 10 + (i * ht / 3));
				g.drawString("Spell Power: " + team[i].spellPower, wd / 3, ht / 10 + (i * ht / 3) + border * 3);
				g.drawString("Armor: " + team[i].armor, wd / 3, ht / 10 + (i * ht / 3) + border * 6);
				g.drawString("Speed: " + team[i].speed, wd / 3, ht / 10 + (i * ht / 3) + border * 9);
				g.drawString("Level: " + team[i].level, wd / 2 + 7 * border, ht / 5 + (i * ht / 3) - border);
				g.drawString("XP: " + team[i].experience + " / " + team[i].experienceCap, wd / 2 + 7 * border, ht / 5 + (i * ht / 3) + border * 2);
				g.drawImage(yellow, wd / 2 + 7 * border, ht / 5 + (i * ht / 3) + border * 29 / 8, (wd / 8 + border) * team[i].experience / team[i].experienceCap, border, null);
			}
			
			if(!subMenu) {
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
				for(int i = 0; i < pauseOptions.length; i++) g.drawString(pauseOptions[i], wd * 6 / 7, (5 * border) * (i + 1));
				g.drawImage(sideArrow, wd * 6 / 7 - 5 * border, (5 * border) * (pauseSelect + 1) - border * 3 / 2, null);
				g.drawString("Money: " + currency, wd * 6 / 7 - border * 3, 60 * border);
				
				if(saveTick > 0) {
					saveTick--;
					g.drawString("Game Saved", wd * 6 / 7, 30 * border);
				}
			}
			else if(subMenu) {
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
				for(int i = 0; i < World.inv.size(); i++) {
					g.drawString(invList[i] + " x " + invAmts[i], wd * 6 / 7, (5 * border) * (i + 1));
				}
				g.drawImage(sideArrow, wd * 6 / 7 - 5 * border, (5 * border) * (invSelect + 1) - border * 3 / 2, null);
			}
		}
		
		else 
			if(saveTick > 0) saveTick--;
	}
	
	public void keyPressed(int k) {
		if(paused) {
			
			if(!subMenu) {
				
				if(k == KeyEvent.VK_UP) {
					if(pauseSelect == 0) pauseSelect = pauseOptions.length - 1;
					else pauseSelect--;
				}
				if(k == KeyEvent.VK_DOWN) {
					if(pauseSelect == pauseOptions.length - 1) pauseSelect = 0;
					else pauseSelect++;
				}
				if(k == KeyEvent.VK_ENTER) {
					if(pauseSelect == 0) {
						unpause();
						World.unpause();
					}
					else if(pauseSelect == 1) {
						subMenu = true;
					}
					else if(pauseSelect == 2) {
						saver.save(World.team, World.player, World.inv);
						saved = true;
						saveTick = 120;
					}
					else if(pauseSelect == 3) World.exit();
				}
			}
			
			else if(subMenu) {
				if(k == KeyEvent.VK_UP) {
					if(invSelect <= 0) invSelect = invList.length - 1;
					else invSelect--;
					if(invSelect == -1) invSelect = 0;
				}
				if(k == KeyEvent.VK_DOWN) {
					if(invSelect >= invList.length - 1) invSelect = 0;
					else invSelect++;
				}
				if(k == KeyEvent.VK_ENTER) {
					
				}
				if(k == KeyEvent.VK_ESCAPE) {
					invSelect = 0;
					subMenu = false;
				}
				
			}
		}
	}
	
	public void pause() {
		pauseSelect = 0;
		paused = true;
		team = World.team;
		invList = World.inv.getItems();
		invAmts = World.inv.getItemAmounts();
		currency = World.inv.getCurrency();
	}
	
	public void unpause() {
		paused = false;
	}

}

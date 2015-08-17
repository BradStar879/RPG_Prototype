package display;

import game.gamestate.World;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import characters.Spells;
import player.CharacterStats;
import player.Item;
import player.Saver;

public class WorldPauseDisplay {
	
	int ht = GamePanel.HEIGHT;
	int wd = GamePanel.WIDTH;
	int border = ht / 62;
	int pauseWd = wd / 4;
	int pauseHt = ht / 6;
	int saveTick = 0;
	boolean invMenu = false;
	boolean invCharMenu = false;
	boolean preAbilityMenu = false;
	boolean abilityMenu = false;
	boolean abilityCharMenu = false;
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
	String[] pauseOptions = new String[]{"Resume", "Abilities", "Inventory", "Save", "Quit"};
	int pauseSelect = 0;
	int invSelect = 0;
	int charSelect = 0;
	int abilitySelect = 0;
	CharacterStats memberSelected;
	String[] invList;
	Vector<Spells> abilityList;
	int[] invAmts;
	int currency;
	String item;
	Spells ability;
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
				g.drawImage(black, wd / 6 - border / 2, ht / 10 + (i * ht / 3) + border * 11 - border / 4, wd / 8 + border, border * 3 / 2, null);
				if(team[i].className.equals("Black Mage") || team[i].className.equals("White Mage")) {
					g.drawString("MP: " + team[i].mp + "/" + team[i].maxMp, wd / 6, i * (ht / 3) + ht / 4 + border * 3 / 8);
					g.drawImage(purple, wd / 6, ht / 10 + (i * ht / 3) + border * 11, (wd / 8) * team[i].mp / team[i].maxMp, border, null);
				}
				else if(team[i].className.equals("Warrior")) {
					g.drawString("Rage: " + team[i].mp + "/" + team[i].maxMp, wd / 6, i * (ht / 3) + ht / 4 + border * 3 / 8);
					g.drawImage(red, wd / 6, ht / 10 + (i * ht / 3) + border * 11, (wd / 8) * team[i].mp / team[i].maxMp, border, null);
				}
				else if(team[i].className.equals("Spearman")) {
					g.drawString("Energy: " + team[i].mp + "/" + team[i].maxMp, wd / 6, i * (ht / 3) + ht / 4 + border * 3 / 8);
					g.drawImage(yellow, wd / 6, ht / 10 + (i * ht / 3) + border * 11, (wd / 8) * team[i].mp / team[i].maxMp, border, null);
				}
				else if(team[i].className.equals("Monk")) {
					g.drawString("Charms: " + team[i].mp + "/" + team[i].maxMp, wd / 6, i * (ht / 3) + ht / 4 + border * 3 / 8);
					g.drawImage(green,wd / 6, ht / 10 + (i * ht / 3) + border * 11, (wd / 8) * team[i].mp / team[i].maxMp, border, null);
				}
				g.drawString("Attack: " + (team[i].attack + team[i].weapon.attack), wd / 3, ht / 10 + (i * ht / 3));
				g.drawString("Spell Power: " + (team[i].spellPower + team[i].weapon.spellPower), wd / 3, ht / 10 + (i * ht / 3) + border * 3);
				g.drawString("Armor: " + (team[i].armor + team[i].clothes.defense), wd / 3, ht / 10 + (i * ht / 3) + border * 6);
				g.drawString("Speed: " + team[i].speed, wd / 3, ht / 10 + (i * ht / 3) + border * 9);
				g.drawString("Weapon: " + team[i].weapon.name, wd / 2 + 7 * border, ht / 5 + (i * ht / 3) - border * 7);
				g.drawString("Clothes: " + team[i].clothes.name, wd / 2 + 7 * border, ht / 5 + (i * ht / 3) - border * 4);
				g.drawString("Level: " + team[i].level, wd / 2 + 7 * border, ht / 5 + (i * ht / 3) - border);
				g.drawString("XP: " + team[i].experience + " / " + team[i].experienceCap, wd / 2 + 7 * border, ht / 5 + (i * ht / 3) + border * 2);
				g.drawImage(yellow, wd / 2 + 7 * border, ht / 5 + (i * ht / 3) + border * 29 / 8, (wd / 8 + border) * team[i].experience / team[i].experienceCap, border, null);
			}
			
			if(invMenu || invCharMenu) {
				
				invList = World.inv.getItems();
				invAmts = World.inv.getItemAmounts();
				g.setColor(Color.WHITE);
				g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
				for(int i = 0; i < World.inv.size(); i++) {
					if(Item.usable(invList[i])) g.setColor(Color.WHITE);
					else g.setColor(Color.GRAY);
					g.drawString(invList[i] + " x " + invAmts[i], wd * 6 / 7, (5 * border) * (i + 1));
				}
				g.drawImage(sideArrow, wd * 6 / 7 - 5 * border, (5 * border) * (invSelect + 1) - border * 3 / 2, null);
				if(invCharMenu) {
					g.setColor(Color.yellow);
					g.fillRect(border * 3	, (charSelect * ht / 3) + ht / 15, ht / 5, ht / 5);
				}
			}
			
			else if(abilityMenu || abilityCharMenu) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
				for(int i = 0; i < abilityList.size(); i++) {
					if(!abilityList.get(i).ooc || memberSelected.mp < abilityList.get(i).mpCost) g.setColor(Color.GRAY);
					g.drawString(abilityList.get(i).name, wd * 6 / 7, (5 * border) * (i + 1));
					g.setColor(Color.WHITE);
				}
				g.drawImage(sideArrow, wd * 6 / 7 - 5 * border, (5 * border) * (abilitySelect + 1) - border * 3 / 2, null);
				g.drawString(abilityList.get(abilitySelect).name, wd * 5 / 6, ht - border * 17);
				g.drawString(memberSelected.mpName + ": " + abilityList.get(abilitySelect).mpCost, wd * 5 / 6, ht - border * 15);
				g.drawString("Cooldown: " + abilityList.get(abilitySelect).cooldown / 60 + " sec", wd * 5 / 6, ht - border * 13);
				g.drawString("Duration: " + abilityList.get(abilitySelect).duration / 60, wd * 5 / 6, ht - border * 11);
				for(int i = 0; i < abilityList.get(abilitySelect).description.length; i++)
				g.drawString(abilityList.get(abilitySelect).description[i], wd * 5 / 6, ht - border * (9 - 2 * i));
				if(abilityCharMenu) {
					g.setColor(Color.yellow);
					g.fillRect(border * 3	, (charSelect * ht / 3) + ht / 15, ht / 5, ht / 5);
				}
			}
			
			else {
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
				for(int i = 0; i < pauseOptions.length; i++) g.drawString(pauseOptions[i], wd * 6 / 7, (5 * border) * (i + 1));
				g.drawImage(sideArrow, wd * 6 / 7 - 5 * border, (5 * border) * (pauseSelect + 1) - border * 3 / 2, null);
				g.drawString("Money: " + currency, wd * 6 / 7 - border * 3, 60 * border);
				
				if(saveTick > 0) {
					saveTick--;
					g.drawString("Game Saved", wd * 6 / 7, 30 * border);
				}
				
				if(preAbilityMenu) {
					g.setColor(Color.yellow);
					g.fillRect(border * 3	, (charSelect * ht / 3) + ht / 15, ht / 5, ht / 5);
				}
			}
		}
		
		else 
			if(saveTick > 0) saveTick--;
	}
	
	public void keyPressed(int k) {
		if(paused) {
			
			if(invMenu) {
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
					if(Item.usable(invList[invSelect])) {
						item = invList[invSelect];
						invMenu = false;
						invCharMenu = true;
					}
				}
				if(k == KeyEvent.VK_ESCAPE) {
					invSelect = 0;
					invMenu = false;
				}
				
			}
			else if(invCharMenu) {
				if(k == KeyEvent.VK_UP) {
					if(charSelect == 0) charSelect = 2;
					else charSelect--;
				}
				if(k == KeyEvent.VK_DOWN) {
					if(charSelect == 2) charSelect = 0;
					else charSelect++;
				}
				if(k == KeyEvent.VK_ENTER) {
					Item.use(team[charSelect], item);
					charSelect = 0;
					invSelect = 0;
					invMenu = true;
					invCharMenu = false;
				}
				if(k == KeyEvent.VK_ESCAPE) {
					charSelect = 0;
					invMenu = true;
					invCharMenu = false;
				}
			}
			else if(preAbilityMenu) {
				if(k == KeyEvent.VK_UP) {
					if(charSelect == 0) charSelect = 2;
					else charSelect--;
				}
				if(k == KeyEvent.VK_DOWN) {
					if(charSelect == 2) charSelect = 0;
					else charSelect++;
				}
				if(k == KeyEvent.VK_ENTER) {
					abilityMenu = true;
					preAbilityMenu = false;
					memberSelected = team[charSelect];
					abilityList = memberSelected.spells;
					charSelect = 0;
				}
				if(k == KeyEvent.VK_ESCAPE) {
					charSelect = 0;
					preAbilityMenu = false;
				}
			}
			else if(abilityMenu) {
				
				if(k == KeyEvent.VK_UP) {
					if(abilitySelect <= 0) abilitySelect = abilityList.size() - 1;
					else abilitySelect--;
					if(abilitySelect == -1) abilitySelect = 0;
				}
				if(k == KeyEvent.VK_DOWN) {
					if(abilitySelect >= abilityList.size() - 1) abilitySelect = 0;
					else abilitySelect++;
				}
				if(k == KeyEvent.VK_ENTER) {
					if(abilityList.get(abilitySelect).ooc && memberSelected.mp >= abilityList.get(abilitySelect).mpCost) {
						ability = new Spells(abilityList.get(abilitySelect).name);
						abilityMenu = false;
						abilityCharMenu = true;
					}
				}
				if(k == KeyEvent.VK_ESCAPE) {
					abilitySelect = 0;
					abilityMenu = false;
				}
			}
			else if(abilityCharMenu) {
				if(k == KeyEvent.VK_UP) {
					if(charSelect == 0) charSelect = 2;
					else charSelect--;
				}
				if(k == KeyEvent.VK_DOWN) {
					if(charSelect == 2) charSelect = 0;
					else charSelect++;
				}
				if(k == KeyEvent.VK_ENTER) {
					ability.useMenuSpell(memberSelected, team[charSelect]);
					charSelect = 0;
					abilityMenu = true;
					abilityCharMenu = false;
				}
				if(k == KeyEvent.VK_ESCAPE) {
					charSelect = 0;
					abilityMenu = true;
					abilityCharMenu = false;
				}
			}
			else {
				
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
					else if(pauseSelect == 1) preAbilityMenu = true;
					else if(pauseSelect == 2) invMenu = true;
					else if(pauseSelect == 3) {
						saver.save(World.team, World.player, World.inv);
						saved = true;
						saveTick = 120;
					}
					else if(pauseSelect == 4) World.exit();
				}
			}
		}
	}
	
	public void pause() {
		pauseSelect = 0;
		paused = true;
		team = World.team;
		currency = World.inv.getCurrency();
	}
	
	public void unpause() {
		paused = false;
	}

}

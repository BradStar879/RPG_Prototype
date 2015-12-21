package display;

import game.gamestate.BaseWorld;
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
import physics.Sounds;
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
	boolean questMenu = false;
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
	Image warriorPortrait = loader.loadImage("/Warrior_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Image blackmagePortrait = loader.loadImage("/Blackmage_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Image whitemagePortrait = loader.loadImage("/Whitemage_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Image archerPortrait = loader.loadImage("/Archer_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Image spearmanPortrait = loader.loadImage("/Spearman_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Image monkPortrait = loader.loadImage("/Monk_portrait.png").getScaledInstance(ht / 5, ht / 5, Image.SCALE_SMOOTH);
	Sounds menuSelectSound = new Sounds("Sounds/menu select.wav");
	Sounds menuBackSound = new Sounds("Sounds/menu back.wav");
	Sounds saveSound = new Sounds("Sounds/save.wav");
	Sounds itemSound = new Sounds("Sounds/item ability use.wav");
	
	boolean paused = false;
	String[] pauseOptions = new String[]{"Resume", "Abilities", "Inventory", "Quests", "Save", "Quit"};
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
	BaseWorld world;
	
	Saver saver = new Saver();
	
	
	public WorldPauseDisplay(BaseWorld world) {
		this.world = world;
	}
	
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
				
				invList = world.inv.getItems();
				invAmts = world.inv.getItemAmounts();
				g.setColor(Color.WHITE);
				g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
				for(int i = 0; i < world.inv.size(); i++) {
					if(Item.usable(invList[i])) g.setColor(Color.WHITE);
					else g.setColor(Color.GRAY);
					g.drawString(invList[i] + " x " + invAmts[i], wd * 6 / 7, (5 * border) * (i + 1));
				}
				g.drawImage(sideArrow, wd * 6 / 7 - 5 * border, (5 * border) * (invSelect + 1) - border * 3 / 2, null);
				if(invCharMenu) {
					g.setColor(Color.yellow);
					g.fillRect(border * 2, (charSelect * ht / 3) + ht / 15 - border, ht / 5 + border * 2, ht / 5 + border * 2);
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
					g.fillRect(border * 2, (charSelect * ht / 3) + ht / 15 - border, ht / 5 + border * 2, ht / 5 + border * 2);
				}
			}
			
			else if(questMenu) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
				for(int i = 0; i < World.quests.size(); i++)
					g.drawString(World.quests.elementAt(i).questName, wd * 6 / 7, (5 * border) * (i+1));
				g.drawImage(sideArrow, wd * 6 / 7 - 5 * border, (5 * border) * (pauseSelect + 1) - border * 3 / 2, null);
				if(!World.quests.isEmpty()) {
					String questType =  "" + World.quests.elementAt(pauseSelect).getClass();
					if(questType.equals("class player.KillQuest")) {
						for(int i = 0; i < World.quests.elementAt(pauseSelect).mobs.length; i++)
							g.drawString(World.quests.elementAt(pauseSelect).mobs[i] + ": " + World.quests.elementAt(pauseSelect).tracker[i] + 
									" / " + World.quests.elementAt(pauseSelect).target[i], wd * 5 / 6, ht - border * 17 + (border * i * 2));
					}
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
					g.drawString("Game Saved", wd * 6 / 7, 35 * border);
				}
				
				if(preAbilityMenu) {
					g.setColor(Color.yellow);
					g.fillRect(border * 2, (charSelect * ht / 3) + ht / 15 - border, ht / 5 + border * 2, ht / 5 + border * 2);
				}
			}
			
			for(int i = 0; i < 3; i++) {
				Image port;
				if(team[i].className.equals("Warrior")) port = warriorPortrait;
				else if(team[i].className.equals("Black Mage")) port = blackmagePortrait;
				else if(team[i].className.equals("White Mage")) port = whitemagePortrait;
				else if(team[i].className.equals("Archer")) port = archerPortrait;
				else if(team[i].className.equals("Spearman")) port = spearmanPortrait;
				else port = monkPortrait;
				g.drawImage(port, border * 3, i * ht / 3 + ht / 15, null);
			}
		}
		
		if(saveTick > 0) saveTick--;
	}
	
	public void keyPressed(int k) {
		if(paused) {
			
			if(invMenu) {
				if(k == KeyEvent.VK_UP) {
					if(invSelect <= 0 && invList.length > 0) invSelect = invList.length - 1;
					else if(invList.length > 0) invSelect--;
				}
				else if(k == KeyEvent.VK_DOWN) {
					if(invSelect >= invList.length - 1) invSelect = 0;
					else invSelect++;
				}
				else if(k == KeyEvent.VK_ENTER && invList.length > 0) {
					if(Item.usable(invList[invSelect])) {
						menuSelectSound.play();
						item = invList[invSelect];
						invMenu = false;
						invCharMenu = true;
					}
				}
				else if(k == KeyEvent.VK_ESCAPE) {
					menuBackSound.play();
					invSelect = 0;
					invMenu = false;
				}
				
			}
			else if(invCharMenu) {
				if(k == KeyEvent.VK_UP) {
					if(charSelect == 0) charSelect = 2;
					else charSelect--;
				}
				else if(k == KeyEvent.VK_DOWN) {
					if(charSelect == 2) charSelect = 0;
					else charSelect++;
				}
				else if(k == KeyEvent.VK_ENTER) {
					itemSound.play();
					Item.use(team[charSelect], item);
					charSelect = 0;
					invSelect = 0;
					invMenu = true;
					invCharMenu = false;
				}
				else if(k == KeyEvent.VK_ESCAPE) {
					menuBackSound.play();
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
				else if(k == KeyEvent.VK_DOWN) {
					if(charSelect == 2) charSelect = 0;
					else charSelect++;
				}
				else if(k == KeyEvent.VK_ENTER) {
					menuSelectSound.play();
					abilityMenu = true;
					preAbilityMenu = false;
					memberSelected = team[charSelect];
					abilityList = memberSelected.spells;
					abilityList.trimToSize();
					for(int i = 0; i < abilityList.size(); i++) System.out.println(abilityList.get(i).name);
					charSelect = 0;
				}
				else if(k == KeyEvent.VK_ESCAPE) {
					menuBackSound.play();
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
				else if(k == KeyEvent.VK_DOWN) {
					if(abilitySelect >= abilityList.size() - 1) abilitySelect = 0;
					else abilitySelect++;
				}
				else if(k == KeyEvent.VK_ENTER) {
					if(abilityList.get(abilitySelect).ooc && memberSelected.mp >= abilityList.get(abilitySelect).mpCost) {
						menuSelectSound.play();
						ability = new Spells(abilityList.get(abilitySelect).name);
						abilityMenu = false;
						abilityCharMenu = true;
					}
				}
				else if(k == KeyEvent.VK_ESCAPE) {
					menuBackSound.play();
					abilitySelect = 0;
					abilityMenu = false;
				}
			}
			else if(abilityCharMenu) {
				if(k == KeyEvent.VK_UP) {
					if(charSelect == 0) charSelect = 2;
					else charSelect--;
				}
				else if(k == KeyEvent.VK_DOWN) {
					if(charSelect == 2) charSelect = 0;
					else charSelect++;
				}
				else if(k == KeyEvent.VK_ENTER) {
					itemSound.play();
					ability.useMenuSpell(memberSelected, team[charSelect]);
					charSelect = 0;
					abilityMenu = true;
					abilityCharMenu = false;
				}
				else if(k == KeyEvent.VK_ESCAPE) {
					menuBackSound.play();
					charSelect = 0;
					abilityMenu = true;
					abilityCharMenu = false;
				}
			}
			else if(questMenu) {
				if(k == KeyEvent.VK_UP) {
					if(pauseSelect <= 0 && !World.quests.isEmpty()) pauseSelect = World.quests.size()-1;
					else if(!World.quests.isEmpty()) pauseSelect--;
				}
				else if(k == KeyEvent.VK_DOWN) {
					if(pauseSelect >= World.quests.size()-1) pauseSelect = 0;
					else pauseSelect++;
				}
				else if(k == KeyEvent.VK_ESCAPE) {
					menuBackSound.play();
					pauseSelect = 0;
					questMenu = false;
				}
			}
			else {
				
				if(k == KeyEvent.VK_UP) {
					if(pauseSelect == 0) pauseSelect = pauseOptions.length - 1;
					else pauseSelect--;
				}
				else if(k == KeyEvent.VK_DOWN) {
					if(pauseSelect == pauseOptions.length - 1) pauseSelect = 0;
					else pauseSelect++;
				}
				else if(k == KeyEvent.VK_ENTER) {
					if(pauseSelect == 0) {
						menuSelectSound.play();
						unpause();
						world.unpause();
					}
					else if(pauseSelect == 1) {
						menuSelectSound.play();
						preAbilityMenu = true;
					}
					else if(pauseSelect == 2) {
						menuSelectSound.play();
						invMenu = true;
					}
					else if(pauseSelect == 3) {
						menuSelectSound.play();
						pauseSelect = 0;
						questMenu = true;
					}
					else if(pauseSelect == 4) {
						saveSound.play();
						saver.save(world.team, world.player, world.inv, world.quests);
						saved = true;
						saveTick = 120;
					}
					else if(pauseSelect == 5) {
						menuSelectSound.play();
						world.exit();
					}
				}
			}
		}
	}
	
	public void pause() {
		menuSelectSound.play();
		pauseSelect = 0;
		paused = true;
		team = world.team;
		currency = world.inv.getCurrency();
	}
	
	public void unpause() {
		paused = false;
	}

}

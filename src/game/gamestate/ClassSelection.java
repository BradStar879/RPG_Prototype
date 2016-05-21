package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import physics.Sounds;
import player.CharacterStats;
import display.BufferedImageLoader;

public class ClassSelection extends GameState{
	
	int ht = GamePanel.HEIGHT;
	int wd = GamePanel.WIDTH;
	int border = ht / 62;
	int boxWd = wd / 4;
	int boxHt = ht / 2;
	int classSelect;
	static int selected;
	String[] classes;
	String[][] classTraits;
	static String[] classesSelected;
	CharacterStats[] team;
	static String[] names;
	boolean readyMenu;
	int cursor;
	
	CharacterStats char1;
	CharacterStats char2;
	CharacterStats char3;
	
	BufferedImageLoader loader = new BufferedImageLoader();
	Image background = new ImageIcon("Sprites/CharacterSelectionScreen.png").getImage();
	Image arrow = loader.loadImage("/SideArrow.png").getScaledInstance(wd / 20, wd / 40, Image.SCALE_SMOOTH);
	Image rDisplay = new ImageIcon("Sprites/PauseBox.png").getImage();
	Image warriorPortrait = loader.loadImage("/Warrior_portrait.png").getScaledInstance(wd / 14, wd / 14, Image.SCALE_SMOOTH);
	Image blackmagePortrait = loader.loadImage("/BlackMage_portrait.png").getScaledInstance(wd / 14, wd / 14, Image.SCALE_SMOOTH);
	Image whitemagePortrait = loader.loadImage("/WhiteMage_portrait.png").getScaledInstance(wd / 14, wd / 14, Image.SCALE_SMOOTH);
	Image archerPortrait = loader.loadImage("/Archer_portrait.png").getScaledInstance(wd / 14, wd / 14, Image.SCALE_SMOOTH);
	Image spearmanPortrait = loader.loadImage("/Spearman_portrait.png").getScaledInstance(wd / 14, wd / 14, Image.SCALE_SMOOTH);
	Image monkPortrait = loader.loadImage("/Monk_portrait.png").getScaledInstance(wd / 14, wd / 14, Image.SCALE_SMOOTH);
	Image warriorPortraitBig = loader.loadImage("/Warrior_portrait.png").getScaledInstance(wd / 9, wd / 9, Image.SCALE_SMOOTH);
	Image blackmagePortraitBig = loader.loadImage("/BlackMage_portrait.png").getScaledInstance(wd / 9, wd / 9, Image.SCALE_SMOOTH);
	Image whitemagePortraitBig = loader.loadImage("/WhiteMage_portrait.png").getScaledInstance(wd / 9, wd / 9, Image.SCALE_SMOOTH);
	Image archerPortraitBig = loader.loadImage("/Archer_portrait.png").getScaledInstance(wd / 9, wd / 9, Image.SCALE_SMOOTH);
	Image spearmanPortraitBig = loader.loadImage("/Spearman_portrait.png").getScaledInstance(wd / 9, wd / 9, Image.SCALE_SMOOTH);
	Image monkPortraitBig = loader.loadImage("/Monk_portrait.png").getScaledInstance(wd / 9, wd / 9, Image.SCALE_SMOOTH);
	Sounds menuSelectSound = new Sounds("Sounds/menu select.wav");
	Sounds menuBackSound = new Sounds("Sounds/menu back.wav");

	public ClassSelection(GameStateManager gsm) {
		super(gsm);
	}

	
	public void init() {
		classSelect = 0;
		selected = 0;
		classes = new String[]{"Warrior", "Black Mage", "White Mage", "Archer", "Spearman", "Monk"};
		classTraits = new String[][] { {"Medium", "None", "Very Strong", "Very Slow", "Melee", "Block", "Blocks attacks", "in his column", ""},
		{"Weak", "Strong", "Weak", "Slow", "Ranged", "Black Magic", "Stronger and", "charges faster from back", "row"},  
		{"Weak", "Strong", "Medium", "Medium", "Ranged", "White Magic", "Charges faster", "from back row and basic", "attacks heal allies"}, 
		{"Medium", "None", "Medium", "Fast", "Ranged", "Rapid Fire", "Has chance", "for critical strike", ""},
		{"Medium", "None", "Strong", "Slow", "Melee", "Reach", "Can attack", "from 2nd row", ""},
		{"Strong", "None", "Medium", "Medium", "Melee", "Meditate", "Does not", "use weapons", ""} };
		classesSelected = new String[]{"", "", ""};
		names = new String[]{"", "", ""};
		readyMenu = false;
		cursor = 0;
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("pixelmix.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		
	}

	
	public void tick() {
		
		if(selected == 3) readyMenu = true;
		if(cursor > 1) cursor = 0;
	}

	
	public void draw(Graphics g) {
		
		g.drawImage(background, 0, 0, wd, ht, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("pixelmix", Font.PLAIN, ht / 50));
		for(int i = 0; i < 6; i++) {
			g.drawString("Class: " + classes[i], boxWd * (i % 3) + 3 * border, boxHt * (i / 3 + 1) - border * 21);
			g.drawString("Attack: " + classTraits[i][0], boxWd * (i % 3) + 3 * border, boxHt * (i / 3 + 1) - border * 19);
			g.drawString("Spell Power: " + classTraits[i][1], boxWd * (i % 3) + 3 * border, boxHt * (i / 3 + 1) - border * 17);
			g.drawString("Defense: " + classTraits[i][2], boxWd * (i % 3) + 3 * border, boxHt * (i / 3 + 1) - border * 15);
			g.drawString("Speed: " + classTraits[i][3], boxWd * (i % 3) + 3 * border, boxHt * (i / 3 + 1) - border * 13);
			g.drawString("Range: " + classTraits[i][4], boxWd * (i % 3) + 3 * border, boxHt * (i / 3 + 1) - border * 11);
			g.drawString("Abilities: " + classTraits[i][5], boxWd * (i % 3) + 3 * border, boxHt * (i / 3 + 1) - border * 9);
			g.drawString("Passive: " + classTraits[i][6], boxWd * (i % 3) + 3 * border, boxHt * (i / 3 + 1) - border * 7);
			g.drawString(classTraits[i][7], boxWd * (i % 3) + 3 * border, boxHt * (i / 3 + 1) - border * 5);
			g.drawString(classTraits[i][8], boxWd * (i % 3) + 3 * border, boxHt * (i / 3 + 1) - border * 3);
		}

		g.drawImage(arrow, (classSelect % 3) * boxWd + 4 * border, (classSelect / 3) * (ht / 2) + border * 5, null);
		g.drawImage(warriorPortrait, 16 * border, border * 2, null);
		g.drawImage(blackmagePortrait, boxWd + 16 * border, border * 2, null);
		g.drawImage(whitemagePortrait, boxWd * 2 + 16 * border, border * 2, null);
		g.drawImage(archerPortrait, 16 * border, ht / 2 + border * 2, null);
		g.drawImage(spearmanPortrait, boxWd + 16 * border, ht / 2 + border * 2, null);
		g.drawImage(monkPortrait, boxWd * 2 + 16 * border, ht / 2 + border * 2, null);
		
		for(int i = 0; i < 3; i++) {
			g.drawString(names[i], wd - wd / 4 + 3 * border, ht / 4 + (i * ht / 3));
			if(classesSelected[i] != "") {
				g.drawString("Class: " + classesSelected[i], wd - wd / 4 + 3 * border, ((i+1) * ht / 3) - 3 * border);
				if(classesSelected[i].equals("Warrior")) g.drawImage(warriorPortraitBig, boxWd * 3 + border * 17 / 2, (i * ht / 3) + border * 2, null);
				else if(classesSelected[i].equals("Black Mage")) g.drawImage(blackmagePortraitBig, boxWd * 3 + border * 17 / 2, (i * ht / 3) + border * 2, null);
				else if(classesSelected[i].equals("White Mage")) g.drawImage(whitemagePortraitBig, boxWd * 3 + border * 17 / 2, (i * ht / 3) + border * 2, null);
				else if(classesSelected[i].equals("Archer")) g.drawImage(archerPortraitBig, boxWd * 3 + border * 17 / 2, (i * ht / 3) + border * 2, null);
				else if(classesSelected[i].equals("Spearman")) g.drawImage(spearmanPortraitBig, boxWd * 3 + border * 17 / 2, (i * ht / 3) + border * 2, null);
				else g.drawImage(monkPortraitBig, boxWd * 3 + border * 17 / 2, (i * ht / 3) + border * 2, null);
			}
		}
		
		if(readyMenu) {
			g.drawImage(rDisplay, wd / 2 - wd / 8, ht / 2 - ht / 12, wd / 4, ht / 6, null);
			g.drawString("Is everything correct?", wd / 2 - 10 * border, ht / 2 - border);
			g.drawString("Yes", wd / 2 - border * 7, ht / 2 + border * 2);
			g.drawString("No", wd / 2 + border * 6, ht / 2 + border * 2);
			g.drawImage(arrow, wd / 2 - border * 12 + (cursor * border * 13), ht / 2 + border / 2, border * 4, border * 2, null);
		}
		
	}

	
	public void keyPressed(int k) {
		
		if(!readyMenu) {
			
			if(k == KeyEvent.VK_RIGHT) {
				if(classSelect == 2) classSelect = 0;
				else if(classSelect == 5) classSelect = 3;
				else classSelect++;
			}
			if(k == KeyEvent.VK_LEFT) {
				if(classSelect == 0) classSelect = 2;
				else if(classSelect == 3) classSelect = 5;
				else classSelect--;
			}	
			if(k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN) {
				if(classSelect < 3) classSelect += 3;
				else classSelect -= 3;
			}
			if(k == KeyEvent.VK_ENTER) {
					menuSelectSound.play();
					classesSelected[selected] = classes[classSelect];
					gsm.states.push(new NameSelection(gsm));
				
			}
			if(k == KeyEvent.VK_ESCAPE) {
				menuBackSound.play();
				gsm.states.pop();
			}
		}
		
		else {
			if(k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_LEFT) cursor++;
			if(k == KeyEvent.VK_ENTER) {
				if(cursor == 0) {
					menuSelectSound.play();
					char1 = new CharacterStats(names[0], classesSelected[0]);
					char2 = new CharacterStats(names[1], classesSelected[1]);
					char3 = new CharacterStats(names[2], classesSelected[2]);
					team = new CharacterStats[]{char1, char2, char3};
					World world = new World(gsm);
					World.team = this.team;
					gsm.states.pop();
					gsm.states.push(world);
				}
				else {
					menuSelectSound.play();
					readyMenu = false;
					selected = 0;
					for(int i = 0; i < 3; i++) {
						classesSelected[i] = "";
						names[i] = "";
					}
				}
			}
		}
	}

	
	public void keyReleased(int k) {
		
		
	}

}

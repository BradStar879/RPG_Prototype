package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import player.CharacterStats;

public class ClassSelection extends GameState{
	
	int ht;
	int wd;
	int classSelect;
	int selected;
	String[] classes;
	String[] classesSelected;
	CharacterStats[] team;
	
	CharacterStats char1;
	CharacterStats char2;
	CharacterStats char3;

	public ClassSelection(GameStateManager gsm) {
		super(gsm);
	}

	
	public void init() {
		ht = GamePanel.HEIGHT;
		wd = GamePanel.WIDTH;
		classSelect = 0;
		selected = 0;
		classes = new String[]{"Warrior", "Black Mage", "White Mage", "Archer", "Spearman", "Monk"};
		classesSelected = new String[]{"", "", ""};
		
	}

	
	public void tick() {
		
		
	}

	
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, wd, ht);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, ht / 25));
		g.drawString("Warrior", wd / 18, ht / 3);
		g.drawString("Black Mage", wd / 3, ht / 3);
		g.drawString("White Mage", wd * 2 / 3, ht / 3);
		g.drawString("Archer", wd / 18, ht * 2 / 3);
		g.drawString("Spearman", wd / 3, ht * 2 / 3);
		g.drawString("Monk", wd * 2 / 3, ht * 2 / 3);
		g.setColor(Color.RED);
		g.fillRect((classSelect % 3) * (wd / 3), (classSelect / 3) * (ht / 3) + ht / 3, 40, 20);
		
		
	}

	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_RIGHT) {
			if(classSelect == 5) classSelect = 0;
			else classSelect++;
		}
		if(k == KeyEvent.VK_LEFT) {
			if(classSelect == 0) classSelect = 5;
			else classSelect--;
		}	
		if(k == KeyEvent.VK_ENTER) {
			classesSelected[selected] = classes[classSelect];
			selected++;
			if(selected == 3) {
				char1 = new CharacterStats("Tom", classesSelected[0]);
				char2 = new CharacterStats("Bob", classesSelected[1]);
				char3 = new CharacterStats("Alfred", classesSelected[2]);
				team = new CharacterStats[]{char1, char2, char3};
				BaseLevel.team = team;
				gsm.states.push(new World(gsm));
			}
		}
		if(k == KeyEvent.VK_ESCAPE) {
			gsm.states.remove(this);
		}
		
	}

	
	public void keyReleased(int k) {
		
		
	}

}

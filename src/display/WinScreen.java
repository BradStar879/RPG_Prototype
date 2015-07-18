package display;

import game.gamestate.World;
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
	Image winBackground = new ImageIcon("Sprites/WorldPauseBox.png").getImage();	
	public boolean won = false;
	int screenHeight = -ht;
	int screenSpeed = 0;
	int screenTick = 0;
	int xp;
	String[] itemsWon;
	CharacterStats[] team;

	public WinScreen(int xp, String[] itemsWon) {
		this.xp = xp;
		this.itemsWon = itemsWon;
	}

	
	public void init() {
		
	}

	
	public void tick() {
		
		if(won && screenTick % 2 == 0) {
			
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
		team = World.team;
		
	}

	
	public void draw(Graphics g) {

		g.drawImage(winBackground, 0, screenHeight, wd, ht, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
		g.drawString("Items Won:", wd * 6 / 7, (5 * border) + screenHeight);
		for(int i = 0; i < itemsWon.length; i++) g.drawString(itemsWon[i], wd * 6 / 7, (5 * border) * (i + 2) + screenHeight);
		
		for(int i = 0; i < 3; i++) {
			g.drawString(team[i].name, wd / 6, ht / 10 + (i * ht / 3) + screenHeight);
			g.drawString("Level: " + team[i].level, wd / 6, ht / 8 + (i * ht / 3) + screenHeight);
			g.drawString(team[i].experience + " / " + team[i].experienceCap, wd / 3, ht / 10 + (i * ht / 3) + screenHeight);
		}
		
	}

	
	public void keyPressed(int k) {
		
		
	}

	
	public void keyReleased(int k) {
		
		
	}

}

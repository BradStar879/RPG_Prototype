package npc; 	

import game.gamestate.BaseWorld;
import game.gamestate.World;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class BaseNPC {

	Image[] sprites = new Image[4];
	Image textBox;
	String[] dialogue;
	public int x;
	public int y;
	int edgeX;
	int edgeY;
	int stage;
	int walkCount;
	int walkPathCount;
	int stepCount;
	int direction;
	int blockSize;
	int drawX;
	int drawY;
	int drawX2;
	int drawY2;
	int gmWd, gmHt;
	int[] walkPath;
	public boolean talking;
	boolean walking;
	public boolean selling;
	BaseWorld world;
	String[][] questMobs;
	int[][] questMobAmts;
	String[][] questItems;
	int[][] questItmAmts;
	
	public BaseNPC(int x, int y, int edgeX, int edgeY, int blockSize, BaseWorld world, int stage) {
		this.x = x;
		this.y = y;
		this.edgeX = edgeX;
		this.edgeY = edgeY;
		this.blockSize = blockSize;
		this.world = world;
		this.stage = stage;
		gmWd = GamePanel.WIDTH;
		gmHt = GamePanel.HEIGHT;
		walkPathCount = 0;
		stepCount = 0;
		direction = 2;
		drawX = 0;
		drawY = 0;
		drawX2 = 0;
		drawY2 = 0;
		talking = false;
		walking = false;
		selling = false;
		walkCount = (int)(Math.random() * 120);
		textBox = new ImageIcon("Sprites/DialogueBox.png").getImage();
	}
	
	public boolean talk(int x) {
		return false;
	}
	
	public void tick() {
		if(walkCount != 300 && !talking) {
			walkCount++;
			world.world[y][x].hasPerson = true;
		}
		else if(!talking && checkStep(walkPath[walkPathCount])) {
			walking = true;
			if(stepCount == 15) {
				walking = false;
				stepCount = 0;
				walkCount = 0;
				if(walkPath[walkPathCount] == 0) {
					y--;
					drawY = 0;
					world.world[y+1][x].hasPerson = false;
				}
				else if(walkPath[walkPathCount] == 1) {
					x++;
					drawX = 0; 
					world.world[y][x-1].hasPerson = false;
				}
				else if(walkPath[walkPathCount] == 2) {
					y++;
					drawY = 0;
					world.world[y-1][x].hasPerson = false;
				}
				else {
					x--;
					drawX = 0;
					world.world[y][x+1].hasPerson = false;
				}
				if(walkPathCount == walkPath.length - 1) walkPathCount = 0;
				else walkPathCount++;
			}
			else {
				if(walkPath[walkPathCount] == 0) {
					direction = 0;
					drawY -= blockSize / 14;
					world.world[y-1][x].hasPerson = true;
				}
				else if(walkPath[walkPathCount] == 1){
					direction = 1;
					drawX += blockSize / 14;
					world.world[y][x+1].hasPerson = true;
				}
				else if(walkPath[walkPathCount] == 2) {
					direction = 2;
					drawY += blockSize / 14;
					world.world[y+1][x].hasPerson = true;
				}
				else {
					direction = 3;
					drawX -= blockSize / 14;
					world.world[y][x-1].hasPerson = true;
				}
				stepCount++;
			}
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(sprites[direction], ((x-World.player.x+edgeX) * blockSize) + drawX + drawX2, ((y-World.player.y+edgeY) * blockSize) + drawY + drawY2, blockSize, blockSize, null);
		if(talking) {
			g.drawImage(textBox, 0, 0, gmWd, gmHt / 4, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("pixelmix", Font.PLAIN, gmHt / 20));
			g.drawString(dialogue[stage], gmHt / 20, gmHt / 10);
			g.drawString(dialogue[stage+1], gmHt / 20, gmHt / 6);
		}
	}
	
	public boolean checkStep(int d) {
		if(d == 0 && World.player.x == x && World.player.y == y-1) return false;
		else if(d == 1 && World.player.x == x+1 && World.player.y == y) return false;
		else if(d == 2 && World.player.x == x && World.player.y == y+1) return false;
		else if(d == 3 && World.player.x == x-1 && World.player.y == y) return false;
		return true;
	}
	
	public void move(int d) {
		if(d == 0) drawY2 += blockSize / 14;
		else if(d == 1) drawX2 -= blockSize / 14;
		else if(d == 2) drawY2 -= blockSize / 14;
		else drawX2 += blockSize / 14;
	}
	
	public void keyPressed(int k) {};
	
	public void reset() {
		drawX2 = 0;
		drawY2 = 0;
	}
}

package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import World.BaseWorldBlock;
import World.GrassBlock;
import World.WaterBlock;

public class World extends GameState{
	
	int shiftX;
	int shiftY;
	int x;
	int y;
	int dir;
	int speed;
	int temp;
	boolean moving;
	int moveTime;
	int blockSize;
	int wd = GamePanel.WIDTH;
	int ht = GamePanel.HEIGHT;
	BaseWorldBlock[][] world;
	GrassBlock[] grassBlocks;
	WaterBlock[] waterBlocks;

	public World(GameStateManager gsm) {
		super(gsm);
		
	}

	
	public void init() {
		
		blockSize = GamePanel.HEIGHT / 12;
		x = 10;
		y = 6;
		shiftX = 4;
		shiftY = 5;
		dir = 0;
		moveTime = 0;
		speed = blockSize / 30;
		moving = false;
		world = new BaseWorldBlock[50][50];
		grassBlocks = new GrassBlock[2500]; 
		waterBlocks = new WaterBlock[10];
		for(int i = 0; i < 2500; i++) grassBlocks[i] = new GrassBlock();
		for(int i = 0; i < 10; i++) waterBlocks[i] = new WaterBlock();
		temp = 0;
		for(int i = 0; i < 50; i++) {
			for(int j = 0; j < 50; j++) {
				world[i][j] = grassBlocks[temp].set(j * blockSize + (shiftX * blockSize), i * blockSize + (shiftY * blockSize));
				temp++;
			}
		}
		world[3][3] = waterBlocks[0].set(3 * blockSize + (shiftX * blockSize), 3 * blockSize + (shiftY * blockSize));
		
	}

	
	public void tick() {
		
		temp = 0;
		
		if(moving) {
			if(dir == 0 & world[y-1][x].walkable) {
				if(moveTime == 2) y--;
				for(int i = 0; i < 50; i++) {
					for(int j = 0; j < 50; j++) {
						world[i][j] = world[i][j].set(world[i][j].x, world[i][j].y + speed);
						temp++;
					}
				}
			}
			else if(dir == 1 & world[y][x+1].walkable) {
				if(moveTime == 2) x++;
				for(int i = 0; i < 50; i++) {
					for(int j = 0; j < 50; j++) {
						world[i][j] = world[i][j].set(world[i][j].x - speed, world[i][j].y);
						temp++;
					}
				}
			}
			else if(dir == 2 & world[y+1][x].walkable) {
				if(moveTime == 2) y++;
				for(int i = 0; i < 50; i++) {
					for(int j = 0; j < 50; j++) {
						world[i][j] = world[i][j].set(world[i][j].x, world[i][j].y - speed);
						temp++;
					}
				}
			}
			else if(dir == 3 & world[y][x-1].walkable) {
				if(moveTime == 2) x--;
				for(int i = 0; i < 50; i++) {
					for(int j = 0; j < 50; j++) {
						world[i][j] = world[i][j].set(world[i][j].x + speed,world[i][j].y);
						temp++;
					}
				}
			}
			else moveTime = 2;
		}
		
		if(moveTime > 0) moveTime -= 2;
		if(moveTime <= 0) moving = false;
	}

	
	public void draw(Graphics g) {
		
		for(int i = 0; i < 50; i++) {
			for(int j = 0; j < 50; j++) {
				world[i][j].draw(g);
			}
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(wd / 2 - blockSize / 2, ht / 2 + blockSize / 4, 30, 30);
		
	}

	
	public void keyPressed(int k) {
		if(!moving) {
			if(k == KeyEvent.VK_W) {
				moving = true;
				moveTime = 60;
				dir = 0;
			}
			else if(k == KeyEvent.VK_D) {
				moving = true;
				moveTime = 60;
				dir = 1;
			}
			else if(k == KeyEvent.VK_S) {
				moving = true;
				moveTime = 60;
				dir = 2;
			}
			else if(k == KeyEvent.VK_A) {
				moving = true;
				moveTime = 60;
				dir = 3;
			}
		}
		
	}

	
	public void keyReleased(int k) {
		
		
	}

}

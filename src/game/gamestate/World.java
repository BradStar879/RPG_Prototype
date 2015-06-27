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
	int wd;
	int ht;
	int battleProb;
	BaseWorldBlock[][] world;
	GrassBlock[] grassBlocks;
	WaterBlock[] waterBlocks;

	public World(GameStateManager gsm) {
		super(gsm);
		
	}

	
	public void init() {
		
		wd = GamePanel.WIDTH;
		ht = GamePanel.HEIGHT;
		blockSize = ht / 12;
		x = 11;
		y = 6;
		shiftX = 0;
		shiftY = 0;
		dir = 0;
		moveTime = 0;
		speed = blockSize / 15;
		moving = false;
		battleProb = 8;
		world = new BaseWorldBlock[50][50];
		grassBlocks = new GrassBlock[2500]; 
		waterBlocks = new WaterBlock[500];
		for(int i = 0; i < 2500; i++) grassBlocks[i] = new GrassBlock(blockSize);
		for(int i = 0; i < 500; i++) waterBlocks[i] = new WaterBlock(blockSize);
		temp = 0;
		for(int i = 1; i < 49; i++) {
			for(int j = 0; j < 50; j++) {
				world[i][j] = grassBlocks[temp].set(j * blockSize + (shiftX * blockSize), i * blockSize + (shiftY * blockSize));
				temp++;
			}
		}
		for(int i = 0; i < 50; i++) {
			world[0][i] = waterBlocks[i].set(i * blockSize + (shiftX * blockSize), 0 * blockSize + (shiftY * blockSize));
			world[49][i] = waterBlocks[i+50].set(i * blockSize + (shiftX * blockSize), 49 * blockSize + (shiftY * blockSize));
		}
		for(int i = 1; i < 49; i++) {
			world[i][0] = waterBlocks[i+100].set(0 * blockSize + (shiftX * blockSize), i * blockSize + (shiftY * blockSize));
			world[i][49] = waterBlocks[i+150].set(49 * blockSize + (shiftX * blockSize), i * blockSize + (shiftY * blockSize));
		}
			
			world[3][3] = waterBlocks[201].set(3 * blockSize + (shiftX * blockSize), 3 * blockSize + (shiftY * blockSize));
			world[7][4] = waterBlocks[202].set(4 * blockSize + (shiftX * blockSize), 7 * blockSize + (shiftY * blockSize));
		
	}

	
	public void tick() {
		
		
		if(moving) {
			if(dir == 0 & world[y-1][x].walkable) {
				if(moveTime == 4) {
					y--;
					shiftY++;
					for(int i = 0; i < 50; i++) {
						for(int j = 0; j < 50; j++) {
							world[i][j].set(j * blockSize + (shiftX * blockSize), i * blockSize + (shiftY * blockSize));
						}
					}
					battleChance();
				}
				else {
					for(int i = 0; i < 50; i++) {
						for(int j = 0; j < 50; j++) {
							world[i][j] = world[i][j].set(world[i][j].x, world[i][j].y + speed);
						}
					}
				}
			}
			else if(dir == 1 & world[y][x+1].walkable) {
				if(moveTime == 4) {
					x++;
					shiftX--;
					for(int i = 0; i < 50; i++) {
						for(int j = 0; j < 50; j++) {
							world[i][j].set(j * blockSize + (shiftX * blockSize), i * blockSize + (shiftY * blockSize));
						}
					}
					battleChance();
				}
				else {
					for(int i = 0; i < 50; i++) {
						for(int j = 0; j < 50; j++) {
							world[i][j] = world[i][j].set(world[i][j].x - speed, world[i][j].y);
						}
					}
				}
			}
			else if(dir == 2 & world[y+1][x].walkable) {
				if(moveTime == 4) {
					y++;
					shiftY--;
					for(int i = 0; i < 50; i++) {
						for(int j = 0; j < 50; j++) {
							world[i][j].set(j * blockSize + (shiftX * blockSize), i * blockSize + (shiftY * blockSize));
						}
					}
					battleChance();
				}
				else {
					for(int i = 0; i < 50; i++) {
						for(int j = 0; j < 50; j++) {
							world[i][j] = world[i][j].set(world[i][j].x, world[i][j].y - speed);
						}
					}
				}
			}
			else if(dir == 3 & world[y][x-1].walkable) {
				if(moveTime == 4) {
					x--;
					shiftX++;
					for(int i = 0; i < 50; i++) {
						for(int j = 0; j < 50; j++) {
							world[i][j].set(j * blockSize + (shiftX * blockSize), i * blockSize + (shiftY * blockSize));
						}
					}
					battleChance();
				}
				else {
					for(int i = 0; i < 50; i++) {
						for(int j = 0; j < 50; j++) {
							world[i][j] = world[i][j].set(world[i][j].x + speed, world[i][j].y);
						}
					}
				}
			}
			else moveTime = 4;
		}
		
		if(moveTime > 0) moveTime -= 4;
		if(moveTime <= 0) moving = false;
	}

	
	public void draw(Graphics g) {
		
		for(int i = 0; i < 50; i++) {
			for(int j = 0; j < 50; j++) {
				world[i][j].draw(g);
			}
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(blockSize * 11 + blockSize / 4,  blockSize * 6 + blockSize / 4, blockSize / 2, blockSize / 2);
		
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
		if(k == KeyEvent.VK_ESCAPE) {
			gsm.states.remove(this);
		}
		
	}

	
	public void keyReleased(int k) {
		
		
	}
	
	public void battleChance() {
		if(Math.random() * 100 < battleProb) {
			gsm.states.push(new BaseLevel(gsm));
		}
	}

}

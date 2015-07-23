package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import player.CharacterStats;
import player.Inventory;
import player.PlayerMover;
import world.BaseWorldBlock;
import world.GrassBlock;
import world.WaterBlock;
import Cities.CastleWall;
import Cities.Gravel;
import Cities.StartingCity;
import display.Animations;
import display.BufferedImageLoader;
import display.WorldPauseDisplay;

public class World extends GameState{
	
	public int shiftX;
	public int shiftY;
	int dir;
	public int speed;
	int temp;
	boolean moving;
	int moveTime;
	public int blockSize;
	public int wd;
	public int ht;
	public int battleProb;
	public static boolean loading = false;
	boolean battle;
	static boolean paused;
	static boolean exit;
	public BaseWorldBlock[][] world;
	public static PlayerMover player;
	public static PlayerMover loadedPlayer;
	public static CharacterStats[] team;
	public static Inventory inv;
	
	public BufferedImageLoader loader;
	public BufferedImage worldDrawn = null;
	public int worldLength;
	public int worldHeight;
	
	public WorldPauseDisplay pDisplay;

	public World(GameStateManager gsm) {
		super(gsm);
		
	}

	
	public void init() {
		
		wd = GamePanel.WIDTH;
		ht = GamePanel.HEIGHT;
		blockSize = ht / 12;
		if(!loading) {
			shiftX = 8;
			shiftY = 8;
			player = new PlayerMover(Color.BLACK, shiftX + 11, shiftY + 6, 0);
			inv = new Inventory();
		}
		else {
			shiftX = player.x - 11;
			shiftY = player.y - 6;
		}
		dir = 0;
		moveTime = 0;
		speed = blockSize / 15;
		moving = false;
		battleProb = 8;
		battle = false;
		paused = false;
		exit = false;
		loader = new BufferedImageLoader();
		worldDrawn = loader.loadImage("/World2.png");
		worldLength = 200;
		worldHeight = 200;
		world = new BaseWorldBlock[worldHeight][worldLength];
		loadImageLevel(worldDrawn);
		pDisplay = new WorldPauseDisplay();
		inv.addItem("Fire Arrow");
		inv.addItem("Fire Arrow");
		inv.addItem("Fire Arrow");
		
	}

	
	public void tick() {
		
		
		if(moving && !paused) {
			if(dir == 0 & world[player.y-1][player.x].walkable) {
				if(moveTime == 4) {
					player.y--;
					shiftY--;
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j].set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
						}
					}
					battleChance();
				}
				else {
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j] = world[i][j].set(world[i][j].x, world[i][j].y + speed);
						}
					}
				}
			}
			else if(dir == 1 & world[player.y][player.x+1].walkable) {
				if(moveTime == 4) {
					player.x++;
					shiftX++;
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j].set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
						}
					}
					battleChance();
				}
				else {
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j] = world[i][j].set(world[i][j].x - speed, world[i][j].y);
						}
					}
				}
			}
			else if(dir == 2 & world[player.y+1][player.x].walkable) {
				if(moveTime == 4) {
					player.y++;
					shiftY++;
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j].set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
						}
					}
					battleChance();
				}
				else {
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j] = world[i][j].set(world[i][j].x, world[i][j].y - speed);
						}
					}
				}
			}
			else if(dir == 3 & world[player.y][player.x-1].walkable) {
				if(moveTime == 4) {
					player.x--;
					shiftX--;
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j].set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
						}
					}
					battleChance();
				}
				else {
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j] = world[i][j].set(world[i][j].x + speed, world[i][j].y);
						}
					}
				}
			}
			else moveTime = 4;
		}
		
		if(moveTime > 0 && !paused) moveTime -= 4;
		if(moveTime <= 0 && !paused) moving = false;
		
		if(exit) gsm.states.remove(this);
	}

	
	public void draw(Graphics g) {
		
		for(int i = player.y - 8; i < player.y + 8; i++) {
			for(int j = player.x - 13; j < player.x + 13; j++) {
				world[i][j].draw(g);
			}
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(blockSize * 11 + blockSize / 4,  blockSize * 6 + blockSize / 4, blockSize / 2, blockSize / 2);
		if(battle)
			if(Animations.splitScreenVert(g, 180)) battleEngage();
		
		pDisplay.draw(g);
		
	}

	
	public void keyPressed(int k) {
		if(!moving && !battle && !paused) {
			if(k == KeyEvent.VK_W) {
				moving = true;
				moveTime = 60;
				dir = 0;
				player.steps++;
			}
			else if(k == KeyEvent.VK_D) {
				moving = true;
				moveTime = 60;
				dir = 1;
				player.steps++;
			}
			else if(k == KeyEvent.VK_S) {
				moving = true;
				moveTime = 60;
				dir = 2;
				player.steps++;
			}
			else if(k == KeyEvent.VK_A) {
				moving = true;
				moveTime = 60;
				dir = 3;
				player.steps++;
			}
			else if(k == KeyEvent.VK_Q) battle = true;
			else if(k == KeyEvent.VK_E) enterCity();
		}
		if(k == KeyEvent.VK_ESCAPE && !battle && !paused) {
			paused = true;
			pDisplay.pause();
		}
		
		pDisplay.keyPressed(k);
		
	}

	
	public void keyReleased(int k) {
		
		
	}
	
	public void loadImageLevel(BufferedImage image) {
		
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int i = 0; i < h; i++) 
			for(int j = 0; j < w; j++) {
				
				int pixel = image.getRGB(j, i);
				//int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = (pixel) & 0xff;
				
				if(b == 255) world[i][j] = new WaterBlock(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(g == 255) world[i][j] = new GrassBlock(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(g == 195) world[i][j] = new Gravel(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(g == 73) world[i][j] = new CastleWall(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
			}
		
	}
	
	public void enterCity() {
		gsm.states.push(new StartingCity(gsm));
	}
	
	public void battleChance() {
		if(Math.random() * 100 < battleProb && player.steps > 4) {
			player.steps = 0;
			//battle = true;
		}
	}
	
	public void battleEngage() {
		battle = false;
		Animations.delay = 0;
		BaseLevel.team = World.team;
		gsm.states.push(new BaseLevel(gsm));
	}
	
	public static void exit() {
		exit = true;
	}
	
	public static void unpause() {
		paused = false;
	}

}

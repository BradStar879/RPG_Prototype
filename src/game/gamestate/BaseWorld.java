package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;

import npc.BaseNPC;
import physics.Sounds;
import player.CharacterStats;
import player.CollectQuest;
import player.Inventory;
import player.KillQuest;
import player.PlayerMover;
import player.Quests;
import world.BaseWorldBlock;
import world.ExitCityBlock;
import world.ForestBlock;
import world.GrassBlock;
import world.MountainBlock;
import world.MountainEntranceBlock;
import world.SandBlock;
import world.StartingCityBlock;
import world.StartingCityBlockBlank;
import world.TallGrassBlock;
import world.WaterBlock;
import Cities.CastleWall;
import Cities.Gravel;
import Cities.StartingCity;
import display.Animations;
import display.BufferedImageLoader;
import display.WorldPauseDisplay;

public class BaseWorld extends GameState{
	
	public int shiftX;
	public int shiftY;
	public int dir;
	public int speed;
	int temp;
	boolean moving;
	int moveTime;
	public int blockSize;
	public int wd;
	public int ht;
	public int edgeX;
	public int edgeY;
	public int battleProb;
	public static boolean loading = false;
	boolean battle;
	boolean paused;
	boolean exit;
	public BaseWorldBlock[][] world;
	public static PlayerMover player;
	public static CharacterStats[] team;
	public static Inventory inv;
	public static Vector<Quests> quests;
	
	public BufferedImageLoader loader;
	public BufferedImage worldDrawn = null;
	public int worldLength;
	public int worldHeight;
	public boolean worldMap;
	public boolean mapDisplay;
	public int mapDisplayCount;
	
	public Image walkerUp = new ImageIcon("Sprites/WalkerNewUp.png").getImage();
	public Image walkerRight = new ImageIcon("Sprites/WalkerNewRight.png").getImage();
	public Image walkerDown = new ImageIcon("Sprites/WalkerNewDown.png").getImage();
	public Image walkerLeft = new ImageIcon("Sprites/WalkerNewLeft.png").getImage();
	public Image map;
	
	public WorldPauseDisplay pDisplay;
	public static Sounds bgm;
	public Sounds enterBattle = new Sounds("Music/enteringbattle.wav");
	public Sounds stepSound = new Sounds("Sounds/short grass walk.wav");
	
	public BaseNPC[] npcs;
	public int[][] npcLocations;
	public boolean talking;
	public static boolean buying;

	public BaseWorld(GameStateManager gsm) {
		super(gsm);
		
	}	

	
	public void init() {
		
		wd = GamePanel.WIDTH;
		ht = GamePanel.HEIGHT;
		worldMap = false;
		mapDisplay = false;
		mapDisplayCount = 0;
		dir = 2;
		moveTime = 0;
		moving = false;
		battle = false;
		talking = false;
		buying = false;
		paused = false;
		exit = false;
		loader = new BufferedImageLoader();
		pDisplay = new WorldPauseDisplay(this);
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("pixelmix.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
	}

	
	public void tick() {
		
		
		if(moving && !paused) {
			if(dir == 0) {
				if(moveTime == 4) {
					player.y--;
					shiftY--;
					changeMusic();
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j].set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
						}
					}
					for(int i = 0; i < npcs.length; i++) npcs[i].reset();
					if(world[player.y][player.x].enter) enterCity();
					else if(world[player.y][player.x].exit) leaveCity();
					else battleChance();
				}
				else {
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j] = world[i][j].set(world[i][j].x, world[i][j].y + speed);
						}
					}
					for(int i = 0; i < npcs.length; i++) npcs[i].move(dir);
				}
			}
			else if(dir == 1) {
				if(moveTime == 4) {
					player.x++;
					shiftX++;
					changeMusic();
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j].set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
						}
					}
					for(int i = 0; i < npcs.length; i++) npcs[i].reset();
					if(world[player.y][player.x].enter) enterCity();
					else if(world[player.y][player.x].exit) leaveCity();
					else battleChance();
				}
				else {
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j] = world[i][j].set(world[i][j].x - speed, world[i][j].y);
						}
					}
					for(int i = 0; i < npcs.length; i++) npcs[i].move(dir);
				}
			}
			else if(dir == 2) {
				if(moveTime == 4) {
					player.y++;
					shiftY++;
					changeMusic();
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j].set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
						}
					}
					for(int i = 0; i < npcs.length; i++) npcs[i].reset();
					if(world[player.y][player.x].enter) enterCity();
					else if(world[player.y][player.x].exit) leaveCity();
					else battleChance();
				}
				else {
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j] = world[i][j].set(world[i][j].x, world[i][j].y - speed);
						}
					}
					for(int i = 0; i < npcs.length; i++) npcs[i].move(dir);
				}
			}
			else if(dir == 3) {
				if(moveTime == 4) {
					player.x--;
					shiftX--;
					changeMusic();
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j].set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
						}
					}
					for(int i = 0; i < npcs.length; i++) npcs[i].reset();
					if(world[player.y][player.x].enter) enterCity();
					else if(world[player.y][player.x].exit) leaveCity();
					else battleChance();
				}
				else {
					for(int i = 0; i < worldHeight; i++) {
						for(int j = 0; j < worldLength; j++) {
							world[i][j] = world[i][j].set(world[i][j].x + speed, world[i][j].y);
						}
					}
					for(int i = 0; i < npcs.length; i++) npcs[i].move(dir);
				}
			}
			else moveTime = 4;
		}
		
		if(moveTime > 0 && !paused) moveTime -= 4;
		if(moveTime <= 0 && !paused) moving = false;
		for(int i = 0; i < npcs.length; i++) npcs[i].tick();
		if(mapDisplay) {
			mapDisplayCount++;
			if(mapDisplayCount >= 90) mapDisplayCount = 0;
		}
		
		if(exit) gsm.states.remove(this);
	}

	
	public void draw(Graphics g) {
		
		for(int i = player.y - edgeY - 2; i < player.y + edgeY + 2; i++) {
			for(int j = player.x - edgeX - 2; j < player.x + edgeX + 4; j++) {
				world[i][j].draw(g);
			}
		}
		
		if(dir == 0) g.drawImage(walkerUp, blockSize * edgeX,  blockSize * edgeY, blockSize, blockSize, null);
		else if(dir == 1) g.drawImage(walkerRight, blockSize * edgeX,  blockSize * edgeY, blockSize, blockSize, null);
		else if(dir == 2) g.drawImage(walkerDown, blockSize * edgeX,  blockSize * edgeY, blockSize, blockSize, null);
		else if(dir == 3) g.drawImage(walkerLeft, blockSize * edgeX,  blockSize * edgeY, blockSize, blockSize, null);
		for(int i = 0; i < npcs.length; i++) npcs[i].draw(g);
		if(battle)
			if(Animations.splitScreenVert(g, 180)) battleEngage();
		
		pDisplay.draw(g);
		if(mapDisplay) {
			g.drawImage(map, (wd-ht)/2, 0, ht, ht, null);
			if(mapDisplayCount < 45) {
				g.setColor(Color.RED);
				g.fillRect((wd-ht)/2 + (player.x * ht / 200), (player.y * ht / 200), ht/200, ht/200);
			}
		}
	}

	
	public void keyPressed(int k) {
		if(!moving && !battle && !paused && !buying && !mapDisplay) {
			if(!talking) {
				if(k == KeyEvent.VK_W && player.y - edgeY - 3 >= 0 && world[player.y-1][player.x].walkable && !world[player.y-1][player.x].hasPerson) {
					moving = true;
					moveTime = 60;
					dir = 0;
					player.steps++;
					stepSound.play();
				}
				else if(k == KeyEvent.VK_D && player.x + edgeX + 4 <= worldLength && world[player.y][player.x+1].walkable && !world[player.y][player.x+1].hasPerson) {
					moving = true;
					moveTime = 60;
					dir = 1;
					player.steps++;
					stepSound.play();
				}
				else if(k == KeyEvent.VK_S && player.y + edgeY + 3 <= worldHeight && world[player.y+1][player.x].walkable && !world[player.y+1][player.x].hasPerson) {
					moving = true;
					moveTime = 60;
					dir = 2;
					player.steps++;
					stepSound.play();
				}
				else if(k == KeyEvent.VK_A && player.x - edgeX - 3 >= 0 && world[player.y][player.x-1].walkable && !world[player.y][player.x-1].hasPerson) {
					moving = true;
					moveTime = 60;
					dir = 3;
					player.steps++;
					stepSound.play();
				}
			}
			if(k == KeyEvent.VK_ENTER) {
				for(int i = 0; i < npcs.length; i++) {
					if(dir == 0 && player.x == npcs[i].x && player.y == npcs[i].y+1) {
						talking = npcs[i].talk(dir);
						break;
					}
					else if(dir == 1 && player.x == npcs[i].x-1 && player.y == npcs[i].y) {
						talking = npcs[i].talk(dir);
						break;
					}
					else if(dir == 2 && player.x == npcs[i].x && player.y == npcs[i].y-1) {
						talking = npcs[i].talk(dir);
						break;
					}
					else if(dir == 3 && player.x == npcs[i].x+1 && player.y == npcs[i].y) {
						talking = npcs[i].talk(dir);
						break;
					}
				}
			}
			else if(k == KeyEvent.VK_Q) battle = true;
		}
		if(k == KeyEvent.VK_ESCAPE && !battle && !paused && !buying && !mapDisplay) {
			paused = true;
			pDisplay.pause();
		}
		
		pDisplay.keyPressed(k);
		if(k == KeyEvent.VK_M) {
			if(!paused && !battle && !mapDisplay && !buying && !moving  && !talking) {
				mapDisplay = true;
			}
			else if(mapDisplay){
				mapDisplay = false;
			}
		}
		
	}

	
	public void keyReleased(int k) {
		
		
	}
	
	public void loadImageLevel(BufferedImage image) {
		
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int i = 0; i < h; i++) 
			for(int j = 0; j < w; j++) {
				
				int pixel = image.getRGB(j, i);
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = (pixel) & 0xff;
				
				if(b == 255) world[i][j] = new WaterBlock(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if (g == 211 || g == 209) world[i][j] = new ExitCityBlock(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(r == 239) world[i][j] = new SandBlock(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(g == 255) world[i][j] = new GrassBlock(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(g == 142) world[i][j] = new MountainEntranceBlock(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(r == 255) world[i][j] = new StartingCityBlock(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(r == 0) world[i][j] = new StartingCityBlockBlank(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(g == 195) world[i][j] = new Gravel(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(g == 73 || g == 75) world[i][j] = new CastleWall(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(g == 154) world[i][j] = new ForestBlock(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(g == 40) world[i][j] = new MountainBlock(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
				else if(g == 85) world[i][j] = new TallGrassBlock(blockSize).set(j * blockSize - (shiftX * blockSize), i * blockSize - (shiftY * blockSize));
			}
		
	}
	
	public void enterCity() {
		bgm.stop();
		gsm.states.pop();
		if(world[player.y][player.x].name.equals("Starting City Block")) gsm.states.push(new StartingCity(gsm));
	}
	
	public void leaveCity() {
		bgm.stop();
		gsm.states.pop();
		gsm.states.push(new World(gsm));
	}
	
	public void battleChance() {
		if(Math.random() * 100 < battleProb && player.steps > 4) {
			player.steps = 0;
			battle = true;
			bgm.stop();
			enterBattle.play();
		}
	}
	
	public void battleEngage() {
		battle = false;
		Animations.delay = 0;
		BaseLevel battle;
		if(world[player.y][player.x].name.equals("Grass")) battle = new PlainsBattle(gsm);
		else if(world[player.y][player.x].name.equals("Sand")) battle = new DesertBattle(gsm);
		else if(world[player.y][player.x].name.equals("Forest")) battle = new ForestBattle(gsm);
		else battle = new PlainsBattle(gsm);
		gsm.states.push(battle);
	}
	
	public void addKillQuest(String name, String[] mobs, int[] amounts) {
		quests.add(new KillQuest(name, mobs, amounts));
	}
	
	public void addCollectQuest(String name, String[] items, int[] amounts) {
		quests.add(new CollectQuest(name, items, amounts));
	}
	
	public boolean isQuestFinished(String name) {
		for(int i = 0; i < quests.size(); i++)
			if(quests.elementAt(i).questName.equals(name)) return quests.elementAt(i).isQuestFinished();
		return false;
	}
	
	public static void removeQuest(String name) {
		for(int i = 0; i < quests.size(); i++)
			if(quests.elementAt(i).questName.equals(name)) {
				quests.remove(i);
				break;
			}
	}
	
	public void exit() {
		bgm.stop();
		exit = true;
	}
	
	public void unpause() {
		paused = false;
	}
	
	public void changeMusic() {
		if(world[player.y][player.x].name.equals("Grass") && !bgm.name.equals("Plains Theme")) {
			bgm.stop();
			bgm = new Sounds("Music/plainstheme.wav");
			bgm.setName("Plains Theme");
			bgm.loop();
		}
		else if(world[player.y][player.x].name.equals("Sand") && !bgm.name.equals("Desert Theme")) {
			bgm.stop();
			bgm = new Sounds("Music/deserttheme.wav");
			bgm.setName("Desert Theme");
			bgm.loop();
		}
		else if(world[player.y][player.x].name.equals("Forest") && !bgm.name.equals("Forest Theme")) {
			bgm.stop();
			bgm = new Sounds("Music/foresttheme.wav");
			bgm.setName("Forest Theme");
			bgm.loop();
		}
	}

}

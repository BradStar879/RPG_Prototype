package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

import player.CharacterStats;
import mobs.BaseMob;
import characters.Archer;
import characters.BaseCharacter;
import characters.BlackMage;
import characters.CoordinateTester;
import characters.Monk;
import characters.Spearman;
import characters.Warrior;
import characters.WhiteMage;
import display.Grid;
import display.InfoDisplay;
import display.PauseDisplay;
import display.QueueDisplay;


public class BaseLevel extends GameState{
	static short charSelect;
	static short menuSelect;
	int ht;
	int wd;
	int border;
	
	static int[] gridX;
	static int[] gridY;
	int infoWd;
	int infoHt;
	int menuWd;
	int menuHt;
	int battleEnd = 180;
	boolean won;
	boolean lost;
	static boolean paused;
	static boolean exit;
	
	static String[] menuOptions;
	static boolean[] onCooldown;
	boolean turnActive ;
	public static Queue<BaseCharacter> attackQueue;
	
	static boolean[] mobInLane;
	static boolean[] posFilled;
	static BaseMob[] mob;
	boolean experienceRewarded;
	
	public CoordinateTester test;
	public static BaseCharacter char1;
	public static BaseCharacter char2;
	public static BaseCharacter char3;
	public static BaseCharacter[] chars;
	public BaseMob mob1;
	public BaseMob mob2;
	public BaseMob mob3;
	public InfoDisplay topInfo;
	public InfoDisplay midInfo;
	public InfoDisplay botInfo;
	public static QueueDisplay qDisplay;
	public PauseDisplay pDisplay;
	public static Grid grid;
	public static CharacterStats[] team;
	
	public BaseLevel(GameStateManager gsm) {
		super(gsm);
	}


	
	public void init() {
		
		ht = GamePanel.HEIGHT;
		wd = GamePanel.WIDTH;
		border = ht / 62;
		infoWd = wd / 6 - (2 * border);
		infoHt = ht / 3 - (2 * border);
		menuWd = wd / 7 - (2 * border);
		menuHt = ht / 4 - (2 * border);
		gridX = new int[]{wd / 5, wd / 3, wd - wd / 3, wd - wd / 5};
		gridY = new int[]{ht - ht / 20, 4 * ht / 10, 4 * ht / 10, ht - ht / 20};
		won = false;
		lost = false;
		exit = false;
		paused = false;
		charSelect = 0;
		menuSelect = 0;
		experienceRewarded = false;
		
		
		mobInLane = new boolean[3];
		for(int i = 0; i < 3; i++) mobInLane[i] = true;
		
		posFilled = new boolean[9];
		for(int i = 0; i < 9; i++) posFilled[i] = false;
		for(int i = 3; i < 6; i++) posFilled[i] = true;
		
		test = new CoordinateTester(400, 400);
		
		if(team[0].className.equals("Warrior")) char1 = new Warrior(0, 3, Color.RED, team[0].name, team[0].level, team[0].hp, 
				team[0].maxHp, team[0].mp, team[0].maxMp, team[0].speed, team[0].attack, 0);
		else if(team[0].className.equals("Black Mage")) char1 = new BlackMage(0, 3, Color.RED, team[0].name, team[0].level, team[0].hp,
				team[0].maxHp, team[0].mp, team[0].maxMp, team[0].speed, team[0].attack, 0);
		else if(team[0].className.equals("White Mage")) char1 = new WhiteMage(0, 3, Color.RED, team[0].name, team[0].level, team[0].hp, 
				team[0].maxHp, team[0].mp, team[0].maxMp, team[0].speed, team[0].attack, 0);
		else if(team[0].className.equals("Archer")) char1 = new Archer(0, 3, Color.RED, team[0].name, team[0].level, team[0].hp,
				team[0].maxHp, team[0].mp, team[0].maxMp, team[0].speed, team[0].attack, 0);
		else if(team[0].className.equals("Spearman")) char1 = new Spearman(0, 3, Color.RED, team[0].name, team[0].level, team[0].hp, 
				team[0].maxHp, team[0].mp, team[0].maxMp, team[0].speed, team[0].attack, 0);
		else if(team[0].className.equals("Monk")) char1 = new Monk(0, 3, Color.RED, team[0].name, team[0].level, team[0].hp,
				team[0].maxHp, team[0].mp, team[0].maxMp, team[0].speed, team[0].attack, 0);
			
		if(team[1].className.equals("Warrior")) char2 = new Warrior(1, 4, Color.CYAN, team[1].name, team[1].level, team[1].hp, 
				team[1].maxHp, team[1].mp, team[1].maxMp, team[1].speed, team[1].attack, 0);
		else if(team[1].className.equals("Black Mage")) char2 = new BlackMage(1, 4, Color.CYAN, team[1].name, team[1].level, team[1].hp,
				team[1].maxHp, team[1].mp, team[1].maxMp, team[1].speed, team[1].attack, 0);
		else if(team[1].className.equals("White Mage")) char2 = new WhiteMage(1, 4, Color.CYAN, team[1].name, team[1].level, team[1].hp,
				team[1].maxHp, team[1].mp, team[1].maxMp, team[1].speed, team[1].attack, 0);
		else if(team[1].className.equals("Archer")) char2 = new Archer(1, 4, Color.CYAN, team[1].name, team[1].level, team[1].hp,
				team[1].maxHp, team[1].mp, team[1].maxMp, team[1].speed, team[1].attack, 0);
		else if(team[1].className.equals("Spearman")) char2 = new Spearman(1, 4, Color.CYAN, team[1].name, team[1].level, team[1].hp,
				team[1].maxHp, team[1].mp, team[1].maxMp, team[1].speed, team[1].attack, 0);
		else if(team[1].className.equals("Monk")) char2 = new Monk(1, 4, Color.CYAN, team[1].name, team[1].level, team[1].hp, 
				team[1].maxHp, team[1].mp, team[1].maxMp, team[1].speed, team[1].attack, 0);
		
		if(team[2].className.equals("Warrior")) char3 = new Warrior(2, 5, Color.MAGENTA, team[2].name, team[2].level, team[2].hp,
				team[2].maxHp, team[2].mp, team[2].maxMp, team[2].speed, team[2].attack, 0);
		else if(team[2].className.equals("Black Mage")) char3 = new BlackMage(2, 5, Color.MAGENTA, team[2].name, team[2].level, team[2].hp,
				team[2].maxHp, team[2].mp, team[2].maxMp, team[2].speed, team[2].attack, 0);
		else if(team[2].className.equals("White Mage")) char3 = new WhiteMage(2, 5, Color.MAGENTA, team[2].name, team[2].level, team[2].hp,
				team[2].maxHp, team[2].mp, team[2].maxMp, team[2].speed, team[2].attack, 0);
		else if(team[2].className.equals("Archer")) char3 = new Archer(2, 5, Color.MAGENTA, team[2].name, team[2].level, team[2].hp,
				team[2].maxHp, team[2].mp, team[2].maxMp, team[2].speed, team[2].attack, 0);
		else if(team[2].className.equals("Spearman")) char3 = new Spearman(2, 5, Color.MAGENTA, team[2].name, team[2].level, team[2].hp,
				team[2].maxHp, team[2].mp, team[2].maxMp, team[2].speed, team[2].attack, 0);
		else if(team[2].className.equals("Monk")) char3 = new Monk(2, 5, Color.MAGENTA, team[2].name, team[2].level, team[2].hp,
				team[2].maxHp, team[2].mp, team[2].maxMp, team[2].speed, team[2].attack, 0);
		
		chars = new BaseCharacter[]{char1, char2, char3};
		mob1 = new BaseMob(0, 100, 50, 120, 10);
		mob2 = new BaseMob(1, 100, 50, 120, 8);
		mob3 = new BaseMob(2, 100, 50, 120, 5);
		
		topInfo = new InfoDisplay(infoWd, infoHt, ht, border, char1, 0);
		midInfo = new InfoDisplay(infoWd, infoHt, ht, border, char2, 1);
		botInfo = new InfoDisplay(infoWd, infoHt, ht, border, char3, 2);
		qDisplay = new QueueDisplay();
		pDisplay = new PauseDisplay();
		
		grid = new Grid(wd, gridX, gridY);
		mob = new BaseMob[]{mob1, mob2, mob3};
		
		
		char1.init();
		char2.init();
		char3.init();
		mob1.init();
		mob2.init();
		mob3.init();
		
		turnActive = false;
		menuOptions = new String[]{"", "", "", ""};
		onCooldown = new boolean[]{false, false, false, false};
		attackQueue = new LinkedList();
	}

	
	public void tick() {
		if(exit) gsm.states.remove(this);
		if(!mobInLane[0] && !mobInLane[1] && !mobInLane[2]) won = true;
		if(!char1.getAlive() && !char2.getAlive() && !char3.getAlive()) lost = true;
		if(!won && !lost && !paused) {
			test.tick();
			char1.tick();
			char2.tick();
			char3.tick();
			mob1.tick();
			mob2.tick();
			mob3.tick();
			qDisplay.tick();
		
			if(attackQueue.isEmpty()) turnActive = false;
			else { 
				if(attackQueue.peek().getAlive()) {
					turnActive = true;
					attackQueue.peek().attackMode();
				}
				else dequeueTurn();
			}
		}
		if(won) {
			if(!experienceRewarded) {
				saveStats(100);
				experienceRewarded = true;
			}
			if(team[0].experience >= team[0].level * 500) team[0].levelUp();
			if(team[1].experience >= team[1].level * 500) team[1].levelUp();
			if(team[2].experience >= team[2].level * 500) team[2].levelUp();
			battleEnd--;
			if(battleEnd == 0) {
				gsm.states.remove(this);
			}
		}
	}

	
	public void draw(Graphics g) {
		//Background
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, wd, ht);
		g.setColor(Color.WHITE);
		g.fillRect(wd - wd / 7, ht - ht / 4, wd / 7, ht / 4);
		
		//Outer part of info boxes
		if(charSelect == 0) g.setColor(Color.YELLOW);
		g.fillRect(0, 0, wd / 6, ht / 3);
		if(charSelect == 1) g.setColor(Color.YELLOW);
		else g.setColor(Color.WHITE);
		g.fillRect(0, ht / 3, wd / 6, ht / 3);
		if(charSelect == 2) g.setColor(Color.YELLOW);
		else g.setColor(Color.WHITE);
		g.fillRect(0, 2 * (ht / 3), wd / 6, ht / 3);
		
		//Info boxes and menu box
		g.setColor(Color.BLUE);
		g.fillRect(border, border, infoWd, infoHt);
		g.fillRect(border, ht / 3 + border, infoWd, infoHt);
		g.fillRect(border, 2 * ht / 3 + border, infoWd, infoHt);
		g.fillRect(wd - wd / 7 + border, ht - ht / 4 + border, menuWd, menuHt);
		
		//Menu box arrow and options
		g.setColor(Color.RED);
		if(turnActive) {
			g.fillRect(wd - wd / 8, ht - (3 * border / 2) - (((wd / 7 - (2 * border)) / 5) * (4 - menuSelect)), ht / 50, border);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, ht / 40));
			for(int i = 0; i < 4; i++) {
				if(!onCooldown[i]) g.setColor(Color.WHITE);
				else g.setColor(Color.GRAY);
				g.drawString(menuOptions[i], wd - wd / 10,  ht - (3 * border / 2) - (((wd / 7 - (2 * border)) / 5) * (4 - i)) + ht / 61);
			}
		}
		
		grid.draw(g);
		
		
		topInfo.draw(g);
		midInfo.draw(g);
		botInfo.draw(g);
		qDisplay.draw(g);
		
		//test.draw(g);
		char1.draw(g);
		char2.draw(g);
		char3.draw(g);
		mob1.draw(g);
		mob2.draw(g);
		mob3.draw(g);
		
		if(won) {
			g.setColor(Color.yellow);
			g.setFont(new Font("Comic Sans MS", Font.BOLD, wd / 10));
			g.drawString("YOU WIN!", wd / 5, ht * 2 / 3);
		}
		
		if(lost) {
			g.setColor(Color.RED);
			g.setFont(new Font("Comic Sans MS", Font.BOLD, wd / 10));
			g.drawString("YOU LOSE!", wd / 5, ht * 2 / 3);
		}
		
		pDisplay.draw(g);
		
	}
	
	public void keyPressed(int k) {
		
		test.keyPressed(k);
		if(k == KeyEvent.VK_ESCAPE && !paused) {
			paused = true;
			pDisplay.pause();
		}
		if(!won && !lost && !paused) {
			if(k == KeyEvent.VK_E) {
				charSelectForward();
			}
			if(k == KeyEvent.VK_Q) {
				charSelectBack();
			}
		
			char1.keyPressed(k);
			char2.keyPressed(k);
			char3.keyPressed(k);
		}
		pDisplay.keyPressed(k);
	}

	
	public void keyReleased(int k) {
		test.keyReleased(k);
		
	}
	
	public static boolean checkPos(int p) {
		return posFilled[p];
	}
	
	public static void changePos(int p, boolean b) {
		posFilled[p] = b;
	}
	
	public static BaseCharacter getCharAt(int pos) {
		if(char1.getPos() == pos) return char1;
		else if(char2.getPos() == pos) return char2;
		else if(char3.getPos() == pos) return char3;
		else return null;
	}
	
	public static void charSelectForward() {
		if(charSelect == 2) charSelect = 0;
		else charSelect++;
	}
	
	public static void charSelectBack() {
		if(charSelect == 0) charSelect = 2;
		else charSelect--;
		while(!chars[charSelect].getAlive()) {
			if(charSelect == 0) charSelect = 2;
			else charSelect--;
		}
	}
	
	public static int getCharSelected() {
		return charSelect;
	}
	
	public static void enqueueTurn(BaseCharacter c) {
		attackQueue.add(c);
		qDisplay.enqueue(c);
	}
	
	public static void dequeueTurn() {
		attackQueue.remove();
		qDisplay.dequeue();
	}

	public static void attackMob(int lane, int attack) {
		if(mobInLane[lane]) {
			if(mob[lane].takeDamage(attack)) mobInLane[lane] = false;
		}
	}
	
	public static void startAttackChar(int pos) {
		grid.attackWarning(pos);
	}
	
	public static void attackChar(int pos, int attack) {
		grid.attackWarningOff(pos);
		if(posFilled[pos]) {
			getCharAt(pos).takeDamage(attack);
		}
	}
	
	public static int getGridX(int i) {
		return gridX[i];
	}
	
	public static int getGridY(int i) {
		return gridY[i];
	}
	
	public static void changeMenuSelect(String s) {
		if(s.equals("UP")) {
			if(menuSelect == 0) menuSelect = 3;
			else menuSelect--;
			while(menuOptions[menuSelect].equals("")) {
				if(menuSelect == 0) menuSelect = 3;
				else menuSelect--;
			}
		}
		else if(s.equals("DOWN")) {
			if(menuSelect == 3) menuSelect = 0;
			else menuSelect++;
			while(menuOptions[menuSelect].equals("")) {
				if(menuSelect == 3) menuSelect = 0;
				else menuSelect++;
			}
		}
		else if(s.equals("LEFT") || s.equals("RIGHT")) menuSelect = 0;
	}
	
	public static void changeMenuOptions(String option, String option2, String option3, String option4, 
			boolean cool1, boolean cool2, boolean cool3, boolean cool4) {
		menuOptions[0] = option;
		menuOptions[1] = option2;
		menuOptions[2] = option3;
		menuOptions[3] = option4;
		onCooldown[0] = cool1;
		onCooldown[1] = cool2;
		onCooldown[2] = cool3;
		onCooldown[3] = cool4;
	}
	
	public static String getMenuOption() {
		return menuOptions[menuSelect];
	}
	
	public static Queue<BaseCharacter> getQueue() {
		return attackQueue;
	}
	
	public static void unpause() {
		paused = false;
	}
	
	public static void exit() {
		exit = true;
	}
	
	public static void saveStats(int exp) {
		for(int i = 0; i < 3; i++) {
			team[i].experience += exp;
			team[i].hp = chars[i].getHp();
			team[i].mp = chars[i].getMp();
		}
	}
}

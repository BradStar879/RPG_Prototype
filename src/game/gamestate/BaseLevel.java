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
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;

import mobs.BaseMob;
import physics.Sounds;
import player.CharacterStats;
import characters.Archer;
import characters.BaseCharacter;
import characters.BlackMage;
import characters.CoordinateTester;
import characters.Monk;
import characters.Spearman;
import characters.Warrior;
import characters.WhiteMage;
import display.BufferedImageLoader;
import display.Grid;
import display.InfoDisplay;
import display.LossScreen;
import display.PauseDisplay;
import display.QueueDisplay;
import display.WinScreen;


public class BaseLevel extends GameState{
	short charSelect;
	public static short menuSelect;
	int ht;
	int wd;
	int border;
	
	int[] gridX;
	int[] gridY;
	int infoWd;
	int infoHt;
	int menuWd;
	int menuHt;
	int battleEnd;
	boolean won;
	boolean lost;
	boolean paused;
	boolean exit;
	
	String[] menuOptions;
	boolean[] onCooldown;
	boolean turnActive ;
	public static Queue<BaseCharacter> attackQueue;
	
	boolean[] mobInLane;
	boolean[] posFilled;
	public BaseMob[] mob;
	boolean experienceRewarded;
	
	BufferedImageLoader loader;
	Image menuBox = new ImageIcon("Sprites/MenuBox.png").getImage();
	Image sideArrow = loader.loadImage("/SideArrow.png").getScaledInstance(ht / 40, border, Image.SCALE_SMOOTH);
	Image background;
	Sounds bgm;
	Sounds vicTheme;
	
	
	public CoordinateTester test;
	public BaseCharacter[] chars;
	public InfoDisplay topInfo;
	public InfoDisplay midInfo;
	public InfoDisplay botInfo;
	public static QueueDisplay qDisplay;
	public PauseDisplay pDisplay;
	public WinScreen wScreen;
	public LossScreen lScreen;
	public static Grid grid;
	
	int numItemsWon;
	int itemsWonChance;

	String[] itemsWon;
	int currencyWon;
	int xp;
	
	
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
		battleEnd = 600;
		loader = new BufferedImageLoader();
		vicTheme = new Sounds("Music/victorytheme.wav");
		
		mobInLane = new boolean[3];
		for(int i = 0; i < 3; i++) mobInLane[i] = true;
		
		posFilled = new boolean[9];
		for(int i = 0; i < 9; i++) posFilled[i] = false;
		for(int i = 3; i < 6; i++) posFilled[i] = true;
		
		CharacterStats[] team = new CharacterStats[3];
		chars = new BaseCharacter[3];
		mob = new BaseMob[3];
		
		for(int i = 0; i < 3; i++) {
			team[i] = BaseWorld.team[i];
			if(team[i].className.equals("Warrior")) chars[i] = new Warrior(i, i + 3, this, team[i].name, team[i].level, team[i].hp, 
				team[i].maxHp, team[i].mp, team[i].maxMp, team[i].speed, team[i].attack + team[i].weapon.attack, team[i].armor +
				team[i].clothes.defense, team[i].spellPower + team[i].weapon.spellPower, team[i].spells);
			else if(team[i].className.equals("Black Mage")) chars[i] = new BlackMage(i, i + 3, this, team[i].name, team[i].level, team[i].hp,
				team[i].maxHp, team[i].mp, team[i].maxMp, team[i].speed, team[i].attack + team[i].weapon.attack, team[i].armor + 
				team[i].clothes.defense, team[i].spellPower + team[i].weapon.spellPower, team[i].spells);
			else if(team[i].className.equals("White Mage")) chars[i] = new WhiteMage(i, i + 3, this, team[i].name, team[i].level, team[i].hp, 
				team[i].maxHp, team[i].mp, team[i].maxMp, team[i].speed, team[i].attack + team[i].weapon.attack, team[i].armor + 
				team[i].clothes.defense, team[i].spellPower + team[i].weapon.spellPower, team[i].spells);
			else if(team[i].className.equals("Archer")) chars[i] = new Archer(i, i + 3, this, team[i].name, team[i].level, team[i].hp,
				team[i].maxHp, team[i].mp, team[i].maxMp, team[i].speed, team[i].attack + team[i].weapon.attack, team[i].armor +
				team[i].clothes.defense, team[i].spellPower + team[i].weapon.spellPower, team[i].spells);
			else if(team[i].className.equals("Spearman")) chars[i] = new Spearman(i, i + 3, this, team[i].name, team[i].level, team[i].hp, 
				team[i].maxHp, team[i].mp, team[i].maxMp, team[i].speed, team[i].attack + team[i].weapon.attack, team[i].armor + 
				team[i].clothes.defense, team[i].spellPower + team[i].weapon.spellPower, team[i].spells);
			else if(team[i].className.equals("Monk")) chars[i] = new Monk(i, i + 3, this, team[i].name, team[i].level, team[i].hp,
				team[i].maxHp, team[i].mp, team[i].maxMp, team[i].speed, team[i].attack + team[i].weapon.attack, team[i].armor + 
				team[i].clothes.defense, team[i].spellPower + team[i].weapon.spellPower, team[i].spells);
		}
			
		topInfo = new InfoDisplay(chars[0], 0, this);
		midInfo = new InfoDisplay(chars[1], 1, this);
		botInfo = new InfoDisplay(chars[2], 2, this);
		qDisplay = new QueueDisplay(this);
		pDisplay = new PauseDisplay(this);
		
		grid = new Grid(wd, gridX, gridY);
		
		turnActive = false;
		menuOptions = new String[]{"", "", "", ""};
		onCooldown = new boolean[]{false, false, false, false};
		attackQueue = new LinkedList<BaseCharacter>();
		lScreen = new LossScreen();
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("pixelmix.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		
	}

	
	public void tick() {
		if(exit) {
			bgm.stop();
			gsm.states.pop();
			BaseWorld.bgm.loop();
		}
		if(!mobInLane[0] && !mobInLane[1] && !mobInLane[2] && !won) {
			bgm.stop();
			vicTheme.play();
			won = true;
		}
		if(!chars[0].getAlive() && !chars[1].getAlive() && !chars[2].getAlive() && !lost) {
			bgm.stop();
			lost = true;
		}
		if(!won && !lost && !paused) {
			for(int i = 0; i < 3; i++){
				chars[i].tick();
				mob[i].tick();
			}
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
			wScreen.tick();
			if(!experienceRewarded) {
				saveStats();
				updateQuests();
				experienceRewarded = true;
			}
			battleEnd--;
			if(battleEnd == 0) {
				gsm.states.pop();
				BaseWorld.bgm.loop();
			}
		}
		else if(lost) {
			lScreen.tick();
			battleEnd--;
			if(battleEnd == 0) {
				gsm.states.pop();
				gsm.states.pop();
			}
		}
	}

	
	public void draw(Graphics g) {
		//Background
		g.drawImage(background, 0, 0, wd, ht, null);
		g.drawImage(menuBox, wd - wd / 7, ht - ht / 4, wd / 7, ht / 4, null);
		
		//Menu box arrow and options
		if(turnActive) {
			g.drawImage(sideArrow, wd - wd * 2 / 15 + border / 2, ht - (3 * border / 2) - (((wd / 7 - (2 * border)) / 5) * (4 - menuSelect)), null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("pixelmix", Font.PLAIN, ht / 45));
			for(int i = 0; i < 4; i++) {
				if(!onCooldown[i]) g.setColor(Color.WHITE);
				else g.setColor(Color.GRAY);
				g.drawString(menuOptions[i], wd - wd / 9,  ht - (3 * border / 2) - (((wd / 7 - (2 * border)) / 5) * (4 - i)) + ht / 61);
			}
		}
		
		grid.draw(g);
		
		topInfo.draw(g);
		midInfo.draw(g);
		botInfo.draw(g);
		qDisplay.draw(g);
		
		//test.draw(g);
		for(int i = 0; i < 3; i++) mob[i].draw(g);
		for(int i = 0; i < 3; i++) chars[i].draw(g);
		
		if(won) {
			wScreen.draw(g);
		}
		
		else if(lost) {
			lScreen.draw(g);
		}
		
		pDisplay.draw(g);
		
	}
	
	public void keyPressed(int k) {
		
		if(!won && !lost && !paused) {
			if(k == KeyEvent.VK_E) {
				charSelectForward();
			}
			if(k == KeyEvent.VK_Q) {
				charSelectBack();
			}
			if(k == KeyEvent.VK_ESCAPE) {
				bgm.stop();
				paused = true;
				pDisplay.pause();
			}
		
			for(int i = 0; i < 3; i++) chars[i].keyPressed(k);
		}
		pDisplay.keyPressed(k);
	}

	
	public void keyReleased(int k) {
		
	}
	
	public boolean checkPos(int p) {
		return posFilled[p];
	}
	
	public void changePos(int p, boolean b) {
		posFilled[p] = b;
	}
	
	public BaseCharacter getCharAt(int pos) {
		for(int i = 0; i < 3; i++) if(chars[i].getPos() == pos) return chars[i];
		return null;
	}
	
	public void charSelectForward() {
		if(charSelect == 2) charSelect = 0;
		else charSelect++;
		while(!chars[charSelect].getAlive()) {
			if(charSelect == 2) charSelect = 0;
			else charSelect++;
		}
	}
	
	public void charSelectBack() {
		if(charSelect == 0) charSelect = 2;
		else charSelect--;
		while(!chars[charSelect].getAlive()) {
			if(charSelect == 0) charSelect = 2;
			else charSelect--;
		}
	}
	
	public int getCharSelected() {
		return charSelect;
	}
	
	public void enqueueTurn(BaseCharacter c) {
		attackQueue.add(c);
		qDisplay.enqueue(c);
	}
	
	public void dequeueTurn() {
		attackQueue.remove();
		qDisplay.dequeue();
	}

	public boolean attackMob(int lane, int attack) {
		if(mobInLane[lane]) {
			if(mob[lane].takeDamage(attack)) {
				mobInLane[lane] = false;
				return true;
			}
			return false;
		}
		return false;
	}
	
	public void attackMob(int lane, int attack, String elem) {
		if(mobInLane[lane]) {
			if(mob[lane].takeDamage(attack, elem)) mobInLane[lane] = false;
		}
	}
	
	public void startAttackChar(int pos) {
		grid.attackWarning(pos);
	}
	
	public void startAttackLane(int lane) {
		grid.attackWarning(lane);
		grid.attackWarning(lane + 3);
		grid.attackWarning(lane + 6);
	}
	
	public void attackChar(int pos, int attack) {
		grid.attackWarningOff(pos);
		if(posFilled[pos]) {
			getCharAt(pos).takeDamage(attack);
		}
	}
	
	public void attackChar(int pos) {
		grid.attackWarningOff(pos);
	}
	
	public int getGridX(int i) {
		return gridX[i];
	}
	
	public int getGridY(int i) {
		return gridY[i];
	}
	
	public void changeMenuSelect(String s) {
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
	
	public void changeMenuOptions(String option, String option2, String option3, String option4, 
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
	
	public String getMenuOption() {
		return menuOptions[menuSelect];
	}
	
	public Queue<BaseCharacter> getQueue() {
		return attackQueue;
	}
	
	public void unpause() {
		bgm.resume();
		paused = false;
	}
	
	public void exit() {
		exit = true;
	}
	
	public void saveStats() {
		for(int i = 0; i < 3; i++) {
			if(chars[i].level != 100) BaseWorld.team[i].experience += xp;
			BaseWorld.team[i].hp = chars[i].getHp();
			if(chars[i].className.equals("White Mage") || chars[i].className.equals("Black Mage") || chars[i].className.equals("Monk")) BaseWorld.team[i].mp = chars[i].getMp();
			if(BaseWorld.team[i].experience >= BaseWorld.team[i].experienceCap) {
				BaseWorld.team[i].levelUp();
				WinScreen.leveled[i] = true;
			}
		}
		for(int i = 0; i < itemsWon.length; i++) BaseWorld.inv.addItem(itemsWon[i]);
		BaseWorld.inv.addCurrency(currencyWon);
	}
	
	public void updateQuests() {
		for(int i = 0; i < World.quests.size(); i++) {
				String questType =  "" + World.quests.elementAt(i).getClass();
				if(questType.equals("class player.KillQuest"))
					for(int j = 0; j < mob.length; j++) World.quests.elementAt(i).updateKills(mob[j].name);
		}
	}
}

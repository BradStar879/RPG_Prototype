package characters;

import game.gamestate.BaseLevel;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import physics.Sounds;
import display.BufferedImageLoader;

public class BaseCharacter {
	
	int num;
	int pos; 
	int x;
	int y;
	boolean selected;
	boolean attacking;
	boolean queued;
	boolean alive;
	boolean dead2;
	int wd;
	int ht;
	int gmWd;
	int gmHt;
	int border;
	
	public String name;
	public int hp;
	public int maxHp;
	public int mp;
	public int maxMp;
	int time;
	int timeMax;
	public int speed;
	public int experience;
	public int level;
	public int attack;
	public int armor;
	public String className;
	int lane;
	int range;
	public int spellPower;
	int baseSpeed;
	int distance;
	int rowCoord0;
	int rowCoord1;
	int rowCoord2;
	int arrowFloat;
	int floatCount;
	
	BufferedImageLoader loader;
	Image arrow;
	Image sprite;
	
	int menuSelect;
	int menuOption;
	String[] moveSet;
	boolean[] isMoveOnCooldown;
	int[] moveCooldown;
	int[] moveMpCost;
	boolean baseMenu;
	
	Vector<Spells> spells = new Vector<Spells>();
	Vector<String> spellSet;
	Vector<Boolean> isSpellOnCooldown;
	Vector<Integer> spellCooldown;
	boolean spellMenu;
	int numSpells;
	
	boolean invMenu;
	
	int regenCount;
	int regenAmount;
	
	public String mpName;
	BaseLevel battle;
	
	Sounds damageSound = new Sounds("Sounds/playerattacked.wav");
	Image crossbones = new ImageIcon("Sprites/Crossbones.png").getImage();
	
	public BaseCharacter(int num, int pos, BaseLevel battle, String name, int level, int hp, int maxHp, int mp,
			int maxMp, int speed, int attack, int armor, int spellPower, Vector<Spells> spells) {
		this.num = num;
		this.pos = pos;
		this.battle = battle;
		this.name = name;
		this.level = level;
		this.hp = hp;
		this.maxHp = maxHp;
		if(this.hp > 0) this.alive = true;
		else this.alive = false;
		this.mp = mp;
		this.maxMp = maxMp;
		this.speed = speed;
		this.baseSpeed = speed;
		this.attack = attack;
		this.armor = armor;
		this.spellPower = spellPower;
		this.spells.setSize(spells.size());
		for(int i = 0; i < spells.size(); i++) this.spells.set(i, spells.get(i));
	}
	
	public void init() {
		selected = false;
		attacking = false;
		queued = false;
		gmWd = GamePanel.WIDTH;
		gmHt = GamePanel.HEIGHT;
		wd = GamePanel.HEIGHT / 24;
		ht = GamePanel.HEIGHT / 12;
		border = gmHt / 62;
		dead2 = false;
		time = (int)(Math.random() * 301 + 0);
		timeMax = 60000;
		rowCoord0 = gmHt * 7 / 16;
		rowCoord1 = gmHt * 89 / 144;
		rowCoord2 = gmHt * 13 / 16;
		loader = new BufferedImageLoader();
		arrow = loader.loadImage("/Arrow.png").getScaledInstance(gmHt / 36, gmHt / 18, Image.SCALE_SMOOTH);
		arrowFloat = 0;
		floatCount = 0;
		lane = pos % 3;
		distance = pos / 3;
		menuSelect = 0;
		menuOption = 0;
		numSpells = 0;
		
		
		moveSet = new String[]{"Attack", "", "", "Item"};
		isMoveOnCooldown = new boolean[]{false, false, false, false};
		moveCooldown = new int[]{0, 0, 0, 0};
		moveMpCost = new int[]{0, 0, 0, 0};
		baseMenu = true;
		
		spellSet = new Vector<String>();
		isSpellOnCooldown = new Vector<Boolean>();
		spellCooldown = new Vector<Integer>();
		spellMenu = false;
		invMenu = false;
		
		for(int i = 1; i < spells.size(); i++) {
			spellSet.add(spells.elementAt(i).name);
			spellCooldown.add(0);
			isSpellOnCooldown.add(false);
			numSpells++;
		}
		while(spellSet.size() < 4) {
			spells.add(new Spells("bla"));
			spellSet.add("");
			spellCooldown.add(0);
			isSpellOnCooldown.add(false);
		}
		
	}
	
	public void tick() {
		
		if(alive) {
			if(num == battle.getCharSelected()) {
				selected = true;
			}
			else {
				selected = false;
			}
		
			if(time < timeMax) time += speed;
			if(time >= timeMax && !queued) {
				time = timeMax;
				queued = true;
				battle.enqueueTurn(this);
			}
			lane = pos % 3;
			distance = pos / 3;
		}
		else {
			if(num == battle.getCharSelected()) {
				selected = false;
				battle.charSelectForward();
			}
		}
		
		floatCount++;
		if(floatCount >= 120) floatCount = 0;
		if(floatCount % 5 == 0) {
			if(floatCount < 20) arrowFloat += gmHt / 288;
			else if(floatCount < 40) arrowFloat += gmHt / 144;
			else if(floatCount < 60) arrowFloat += gmHt / 288;
			else if(floatCount < 80) arrowFloat -= gmHt / 288;
			else if(floatCount < 100) arrowFloat -= gmHt / 144;
			else arrowFloat -= gmHt / 288;
		}
		
		if(regenCount > 0) {
			regenCount--;
			if(regenCount % 180 == 0) hp += regenAmount;
			if(hp > maxHp) hp = maxHp;
		}
		
		for(int i = 0; i < 4; i++) {
			if(moveCooldown[i] > 0) {
				moveCooldown[i]--;
			}
			else if(mp < moveMpCost[i]) isMoveOnCooldown[i] = true;
			else isMoveOnCooldown[i] = false;
		}
		for(int i = 0; i < isSpellOnCooldown.size(); i++) {
			if(spellCooldown.elementAt(i) > 0) {
				spellCooldown.set(i, spellCooldown.elementAt(i) - 1);
			}
			else if(mp < spells.elementAt(i+1).mpCost) isSpellOnCooldown.set(i, true);
			else isSpellOnCooldown.set(i, false);
		}
		if(attacking && baseMenu) battle.changeMenuOptions(moveSet[0], moveSet[1], moveSet[2], moveSet[3], 
				isMoveOnCooldown[0], isMoveOnCooldown[1], isMoveOnCooldown[2], isMoveOnCooldown[3]);
		else if(attacking && spellMenu)
			battle.changeMenuOptions(spellSet.elementAt(menuOption-menuSelect), spellSet.elementAt(menuOption-menuSelect+1), spellSet.elementAt(menuOption-menuSelect+2), spellSet.elementAt(menuOption-menuSelect+3), 
					isSpellOnCooldown.elementAt(menuOption-menuSelect), isSpellOnCooldown.elementAt(menuOption-menuSelect+1), isSpellOnCooldown.elementAt(menuOption-menuSelect+2), isSpellOnCooldown.elementAt(menuOption-menuSelect+3));
	}
	
	
	public void draw(Graphics g) {
	
		if(pos == 0) {
			x = gmHt * 23 / 36;
			y = rowCoord0;
		}
		if(pos == 1) {
			x = gmHt * 125 / 144;
			y = rowCoord0;
		}
		if(pos == 2) {
			x = gmHt * 79 / 72;
			y = rowCoord0;
		}
		if(pos == 3) {
			x = gmHt * 85 / 144;
			y = rowCoord1;
		}
		if(pos == 4) {
			x = gmHt * 125 / 144;
			y = rowCoord1;
		}
		if(pos == 5) {
			x = gmHt * 55 / 48;
			y = rowCoord1;
		}
		if(pos == 6) {
			x = gmHt * 13 / 24;
			y = rowCoord2;
		}
		if(pos == 7) {
			x = gmHt * 125 / 144;
			y = rowCoord2;
		}
		if(pos == 8) {
			x = gmHt * 43 / 36;
			y = rowCoord2;
		}
		
		if(selected) {
			g.setColor(Color.YELLOW);
			if(distance == 0) {
				for(int i = 0; i < 40; i++) {
					for(int j = 0; j < 20; j++) {
						if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) 
							g.fillRect(x - gmHt / 36 + (gmHt * j / 9 / 20), y - gmHt / 24 + (gmHt * i / 6 / 40), gmHt / 9 / 20, gmHt / 6 / 40);
					}
				}
			}
			else if(distance == 1) {
				for(int i = 0; i < 40; i++) {
					for(int j = 0; j < 20; j++) {
						if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) 
							g.fillRect(x - gmHt / 26 + (int)(gmHt * j / 7.5 / 20), y - gmHt / 12 + (gmHt * i / 5 / 40), (int)(gmHt / 7.5 / 20), (gmHt / 5 / 40));
					}
				}
			}
			else {
				for(int i = 0; i < 40; i++) {
					for(int j = 0; j < 20; j++) {
						if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) 
							g.fillRect(x - gmHt / 19 + (gmHt * j / 6 / 20), y - gmHt / 10 + (gmHt * i / 4 / 40), (gmHt / 6 / 20), (gmHt / 4 / 40));
					}
				}
			}
		}
		
		if(distance == 0) {
			if(alive) g.drawImage(sprite, x - gmHt / 36, y - gmHt / 24, null);
			else if(!dead2) {
				g.drawImage(sprite, x - gmHt / 7, y - gmHt / 24, null);
				g.drawImage(crossbones, x - gmHt / 8, y, gmHt / 16, gmHt / 16, null);
			}
			else {
				g.drawImage(sprite, x + gmHt / 14, y - gmHt / 24, null);
				g.drawImage(crossbones, x + gmHt / 11, y, gmHt / 16, gmHt / 16, null);
			}
		}
		else if(distance == 1) {
			if(alive) g.drawImage(sprite, x - gmHt / 26, y - gmHt / 12, (int)(gmHt / 7.5), gmHt / 5, null);
			else if(!dead2) {
				g.drawImage(sprite, x - gmHt / 6, y - gmHt / 12, (int)(gmHt / 7.5), gmHt / 5, null);
				g.drawImage(crossbones, x - gmHt / 6 + border, y - gmHt / 24, gmHt / 12, gmHt / 12, null);
			}
			else {
				g.drawImage(sprite, x + gmHt / 12, y - gmHt / 12, (int)(gmHt / 7.5), gmHt / 5, null);
				g.drawImage(crossbones, x + gmHt / 10, y - gmHt / 24, gmHt / 12, gmHt / 12, null);
			}
		}
		else {
			if(alive) g.drawImage(sprite, x - gmHt / 19, y - gmHt / 10, gmHt / 6, gmHt / 4, null);
			else if(!dead2) {
				g.drawImage(sprite, x - gmHt / 5, y - gmHt / 10, gmHt / 6, gmHt / 4, null);
				g.drawImage(crossbones, x - gmHt / 5 + border * 3 / 2, y - gmHt / 24, gmHt / 10, gmHt / 10, null);
			}
			else {
				g.drawImage(sprite, x + gmHt / 10, y - gmHt / 10, gmHt / 6, gmHt / 4, null);
				g.drawImage(crossbones, x + gmHt / 9 + border, y - gmHt / 24, gmHt / 10, gmHt / 10	, null);
			}
		}
		
		if(attacking) {
			if(distance == 0) battle.arrowSet(x + wd / 2 - gmHt / 72, y - gmHt / 12 - (arrowFloat / 2), gmHt / 48, gmHt / 24); //    (g.drawImage(arrow, x + wd / 2 - gmHt / 72, y - gmHt / 12 - (arrowFloat / 2), gmHt / 48, gmHt / 24, null);
			else if(distance == 1) battle.arrowSet(x + wd / 2 - gmHt / 72, y - gmHt / 7 - (arrowFloat * 3 / 4), gmHt / 36, gmHt / 18);  //g.drawImage(arrow, x + wd / 2 - gmHt / 72, y - gmHt / 7 - (arrowFloat * 3 / 4), null);
			else battle.arrowSet(x + wd / 2 - gmHt / 72, y - gmHt / 6 - arrowFloat, gmHt / 28, gmHt / 14);  //g.drawImage(arrow, x + wd / 2 - gmHt / 72, y - gmHt / 6 - arrowFloat, gmHt / 28, gmHt / 14, null);
		}
		
	}

	public void keyPressed(int k) {
		if(selected) {
			if(k == KeyEvent.VK_W && pos > 2 && !battle.checkPos(pos - 3)) {
				pos -= 3;
				battle.changePos(pos + 3, false);
				battle.changePos(pos, true);
			}
			if(k == KeyEvent.VK_S && pos < 6 && !battle.checkPos(pos + 3)) {
				pos += 3;
				battle.changePos(pos - 3, false);
				battle.changePos(pos, true);
			}
			if(k == KeyEvent.VK_A && pos != 0  && pos != 3 && pos != 6)
				if (!battle.checkPos(pos - 1)) {
					pos -= 1;
					battle.changePos(pos + 1, false);
					battle.changePos(pos, true);
				}
			if(k == KeyEvent.VK_D && pos != 2  && pos != 5 && pos != 8)
				if (!battle.checkPos(pos + 1)) {
					pos += 1;
					battle.changePos(pos - 1, false);
					battle.changePos(pos, true);
				}
			
			}
		
		if(attacking){
			if(k == KeyEvent.VK_UP) {
				if(spellMenu && menuOption != 0 && menuSelect == 0) {
					menuOption--;
					battle.changeMenuOptions(spellSet.elementAt(menuOption), spellSet.elementAt(menuOption+1), spellSet.elementAt(menuOption+2), spellSet.elementAt(menuOption+3), 
							isSpellOnCooldown.elementAt(menuOption), isSpellOnCooldown.elementAt(menuOption+1), isSpellOnCooldown.elementAt(menuOption+2), isSpellOnCooldown.elementAt(menuOption+3));
				}
				else if(menuSelect != 0) {
					battle.changeMenuSelect("UP");
					menuSelect--;
					menuOption--;
				}
			}
			if(k == KeyEvent.VK_DOWN) {
				if(spellMenu && menuOption != numSpells - 1 && menuSelect == 3) {
					menuOption++;
					battle.changeMenuOptions(spellSet.elementAt(menuOption-3), spellSet.elementAt(menuOption-2), spellSet.elementAt(menuOption-1), spellSet.elementAt(menuOption), 
							isSpellOnCooldown.elementAt(menuOption-3), isSpellOnCooldown.elementAt(menuOption-2), isSpellOnCooldown.elementAt(menuOption-1), isSpellOnCooldown.elementAt(menuOption));
				}
				else if(menuSelect != 3 && !(spellMenu && menuOption == numSpells - 1)) {
					battle.changeMenuSelect("DOWN");
					menuSelect++;
					menuOption++;
				}
			}
			if(k == KeyEvent.VK_LEFT) {
				if(baseMenu) {
					attacking = false;
					battle.dequeueTurn();
					battle.enqueueTurn(this);
				}
				else {
					spellMenu = false;
					invMenu = false;
					baseMenu = true;
					battle.changeMenuOptions(moveSet[0], moveSet[1], moveSet[2], moveSet[3], 
							isMoveOnCooldown[0], isMoveOnCooldown[1], isMoveOnCooldown[2], isMoveOnCooldown[3]);
				}
				battle.changeMenuSelect("LEFT");
				menuSelect = 0;
				menuOption = 0;
			}
		}
	}
	
	public void keyReleased(int k) {
		
	}
	
	public int getHp() {
		return hp;
	}
	
	public int getMaxHp() {
		return maxHp;
	}
	
	public int getMp() {
		return mp;
	}
	
	public int getMaxMp() {
		return maxMp;
	}
	
	public int getLevel() {
		return level;
	}
		
	public int getTime() {
		return time;
	}
	
	public int getTimeMax() {
		return timeMax;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPos() {
		return pos;
	}
	
	public void attackMode() {
		if(!attacking) {
			battle.changeMenuOptions(moveSet[0], moveSet[1], moveSet[2], moveSet[3], 
				isMoveOnCooldown[0], isMoveOnCooldown[1], isMoveOnCooldown[2], isMoveOnCooldown[3]);
			baseMenu = true;
		}
		attacking = true;
	}
	
	public void attack(int damage) {
		time = 0;
		attacking = false;
		queued = false;
		battle.dequeueTurn();
		if(range == 1) {
			if(distance == 0) {
				battle.attackMob(lane, damage);
			}
			else {
				if(battle.checkPos(pos - 3)) battle.attackChar(pos - 3, damage);
			}
		}
		else {
			if(distance == 2) {
				if(battle.checkPos(pos - 3)) battle.attackChar(pos - 3, damage);
				else if(battle.checkPos(pos - 6)) battle.attackChar(pos - 6, damage);
				else if(distance < range) battle.attackMob(lane, damage);
			}
			else if(distance == 1) {
				if(battle.checkPos(pos - 3)) battle.attackChar(pos - 3, damage);
				else if(distance < range) battle.attackMob(lane, damage);
			}
			else if(distance < range) battle.attackMob(lane, damage);
		}
	}
	
	public void takeDamage(int damage) {
		if(hp != 0) {
			if(armor >= damage) {
				hp -= 1;
			}
			else hp -= damage - armor;
			if(hp <= 0) {
				if(!battle.checkDeadPos(pos)) {
					battle.changePos(pos, false);
					battle.changeDeadPos(pos, true);
				}
				else {
					dead2 = true;
					battle.changePos(pos, false);
					battle.changeDead2Pos(pos, true);
				}
				alive = false;
				attacking = false;
				regenCount = 0;
				hp = 0;
			}
			damageSound.play();
		}
	}
	
	public void heal(int amount) {
		if(alive) {
			hp += amount;
			if(hp > maxHp) hp = maxHp;
		}
	}
	
	public void regen(int amount, int duration) {
		regenAmount = amount;
		regenCount = duration;
	}
	
	public void resurrect(int amount) {
		if(!dead2) {
			battle.changeDeadPos(pos, false);
			battle.changePos(pos, true);
		}
		else {
			battle.changeDead2Pos(pos, false);
			battle.changePos(pos, true);
			dead2 = false;
		}
		alive = true;
		hp += amount;
		if(hp > maxHp) hp = maxHp;
	}
	
	public boolean getAlive() {
		return alive;
	}
	
	public boolean getDead2() {
		return dead2;
	}
	
	public String[] getMovesOnCooldown() {
		int k = 0;
		int j = 0;
		for(int i = 0; i < 4; i++) {
			if(isMoveOnCooldown[i]) k++;
		}
		String[] temp = new String[k];
		for(int i = 0; i < 4; i++) {
			if(isMoveOnCooldown[i]) {
				temp[j] = moveSet[i];
				j++;
			}
		}
		return temp;
		
	}
	
	public int[] getMoveSetCooldowns() {
		int k = 0;
		int j = 0;
		for(int i = 0; i < 4; i++) {
			if(isMoveOnCooldown[i]) k++;
		}
		int[] temp = new int[k];
		for(int i = 0; i < 4; i++) {
			if(isMoveOnCooldown[i]) {
				temp[j] = moveCooldown[i] / 60 + 1;
				j++;
			}
		}
		return temp;
	}
	
	public String[] getSpellsOnCooldown() {
		int k = 0;
		int j = 0;
		for(int i = 0; i < 4; i++) {
			if(isSpellOnCooldown.elementAt(i)) k++;
		}
		String[] temp = new String[k];
		for(int i = 0; i < 4; i++) {
			if(isSpellOnCooldown.elementAt(i)) {
				temp[j] = spellSet.elementAt(i);
				j++;
			}
		}
		return temp;
		
	}
	
	public int[] getSpellSetCooldowns() {
		int k = 0;
		int j = 0;
		for(int i = 0; i < 4; i++) {
			if(isSpellOnCooldown.elementAt(i)) k++;
		}
		int[] temp = new int[k];
		for(int i = 0; i < 4; i++) {
			if(isSpellOnCooldown.elementAt(i)) {
				temp[j] = spellCooldown.elementAt(i) / 60 + 1;
				j++;
			}
		}
		return temp;
	}
	
	public int getCurrentCooldown() {
		for(int i = 0; i < moveSet.length; i++)
			if(battle.getMenuOption().equals(moveSet[i])) return (moveCooldown[i] + 59) / 60;
		for(int i = 0; i < spellSet.size(); i++)
			if(battle.getMenuOption().equals(spellSet.elementAt(i))) return (spellCooldown.elementAt(i) + 59) / 60;
		return 0;
	}
	
	
	
}

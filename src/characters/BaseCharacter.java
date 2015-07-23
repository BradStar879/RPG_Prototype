package characters;

import game.gamestate.BaseLevel;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import display.BufferedImageLoader;

public class BaseCharacter {
	
	int num;
	int pos; 
	Color col;
	int x;
	int y;
	boolean selected;
	boolean attacking;
	boolean queued;
	boolean alive;
	int wd;
	int ht;
	int gmWd;
	int gmHt;
	
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
	
	String[] moveSet;
	boolean[] isMoveOnCooldown;
	int[] moveCooldown;
	boolean baseMenu;
	
	String[] spellSet;
	boolean[] isSpellOnCooldown;
	int[] spellCooldown;
	boolean spellMenu;
	
	public BaseCharacter(int num, int pos, Color col) {
		this.num = num;
		this.pos = pos;
		this.col = col;
	}
	
	public BaseCharacter(int num, int pos, Color col, String name, int level, int hp, int maxHp, int mp, int maxMp, int speed, int attack, int armor, int spellPower) {
		this.num = num;
		this.pos = pos;
		this.col = col;
		this.name = name;
		this.level = level;
		this.hp = hp;
		this.maxHp = maxHp;
		this.mp = mp;
		this.maxMp = maxMp;
		this.speed = speed;
		this.baseSpeed = speed;
		this.attack = attack;
		this.armor = armor;
		this.spellPower = spellPower;
	}
	
	public void init() {
		alive = true;
		selected = false;
		attacking = false;
		queued = false;
		gmWd = GamePanel.WIDTH;
		gmHt = GamePanel.HEIGHT;
		wd = GamePanel.HEIGHT / 24;
		ht = GamePanel.HEIGHT / 12;
		time = (int)(Math.random() * 201 + 0);
		timeMax = 60000;
		rowCoord0 = gmHt * 7 / 16;
		rowCoord1 = gmHt * 89 / 144;
		rowCoord2 = gmHt * 13 / 16;
		loader = new BufferedImageLoader();
		arrow = loader.loadImage("/Arrow.png").getScaledInstance(gmHt / 36, gmHt / 18, Image.SCALE_SMOOTH);
		arrowFloat = 0;
		floatCount = 0;
		
		
		moveSet = new String[]{"Attack", "Magic", "", "Item"};
		isMoveOnCooldown = new boolean[]{false, false, false, false};
		moveCooldown = new int[]{0, 0, 0, 0};
		baseMenu = true;
		
		spellSet = new String[]{"", "", "", ""};
		isSpellOnCooldown = new boolean[]{false, false, false, false};
		spellCooldown = new int[]{0, 0, 0, 0};
		spellMenu = false;
	}
	
	public void tick() {
		
		if(hp <= 0) {
			alive = false;
			attacking = false;
		}
		else alive = true;
		
		if(alive) {
			if(num == BaseLevel.getCharSelected()) {
				selected = true;
			}
			else {
				selected = false;
			}
		
			if(time < timeMax) time += speed;
			if(time >= timeMax && !queued) {
				time = timeMax;
				queued = true;
				BaseLevel.enqueueTurn(this);
			}
			lane = pos % 3;
			distance = pos / 3;
		}
		else {
			if(num == BaseLevel.getCharSelected()) {
				selected = false;
				BaseLevel.charSelectForward();
			}
		}

		if(attacking && baseMenu) BaseLevel.changeMenuOptions(moveSet[0], moveSet[1], moveSet[2], moveSet[3], 
				isMoveOnCooldown[0], isMoveOnCooldown[1], isMoveOnCooldown[2], isMoveOnCooldown[3]);
		
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
	}
	
	
	public void draw(Graphics g) {
		if(selected) {
			g.setColor(Color.YELLOW);
			if(distance == 0) g.fillRect(this.x - gmHt / 48, this.y - gmHt / 48, gmHt / 12, gmHt / 8);
			else if(distance == 1) g.fillRect(this.x - gmHt / 48, this.y - gmHt / 48, gmHt / 12, gmHt / 8);
			else g.fillRect(this.x - gmHt / 48, this.y - gmHt / 48, gmHt / 12, gmHt / 8);
		}
		g.setColor(col);
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
		
		if(className.equals("White Mage") || className.equals("Black Mage")) {
			if(distance == 0) g.drawImage(sprite, x - gmHt / 36, y - gmHt / 24, null);
			else if(distance == 1) g.drawImage(sprite, x - gmHt / 26, y - gmHt / 12, (int)(gmHt / 7.5), gmHt / 5, null);
			else g.drawImage(sprite, x - gmHt / 19, y - gmHt / 10, gmHt / 6, gmHt / 4, null);
			
		}
		else g.fillRect(x, y, wd, ht);
		
		if(attacking) {
			
			g.drawImage(arrow, x + wd / 2 - gmHt / 72, y - gmHt / 12 - arrowFloat, null);
		}
		
	}

	public void keyPressed(int k) {
		if(selected) {
			if(k == KeyEvent.VK_W && pos > 2 && !BaseLevel.checkPos(pos - 3)) {
				pos -= 3;
				BaseLevel.changePos(pos + 3, false);
				BaseLevel.changePos(pos, true);
			}
			if(k == KeyEvent.VK_S && pos < 6 && !BaseLevel.checkPos(pos + 3)) {
				pos += 3;
				BaseLevel.changePos(pos - 3, false);
				BaseLevel.changePos(pos, true);
			}
			if(k == KeyEvent.VK_A && pos != 0  && pos != 3 && pos != 6)
				if (!BaseLevel.checkPos(pos - 1)) {
					pos -= 1;
					BaseLevel.changePos(pos + 1, false);
					BaseLevel.changePos(pos, true);
				}
			if(k == KeyEvent.VK_D && pos != 2  && pos != 5 && pos != 8)
				if (!BaseLevel.checkPos(pos + 1)) {
					pos += 1;
					BaseLevel.changePos(pos - 1, false);
					BaseLevel.changePos(pos, true);
				}
			
			}
		
		
		if(attacking){
			if(k == KeyEvent.VK_UP) BaseLevel.changeMenuSelect("UP");
			if(k == KeyEvent.VK_DOWN) BaseLevel.changeMenuSelect("DOWN");
			if(k == KeyEvent.VK_RIGHT) {
				if(BaseLevel.getMenuOption().equals("Attack")) {
					attack(attack);
				}
				BaseLevel.changeMenuSelect("RIGHT");
			}
			if(k == KeyEvent.VK_LEFT && baseMenu) {
				BaseLevel.changeMenuSelect("LEFT");
				attacking = false;
				BaseLevel.dequeueTurn();
				BaseLevel.enqueueTurn(this);
			}
		}
	}
	
	public void keyReleased(int k) {
		
	}
	
	public void getPortrait() {
		
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
			BaseLevel.changeMenuOptions(moveSet[0], moveSet[1], moveSet[2], moveSet[3], 
				isMoveOnCooldown[0], isMoveOnCooldown[1], isMoveOnCooldown[2], isMoveOnCooldown[3]);
			baseMenu = true;
		}
		attacking = true;
	}
	
	public Color getColor() {
		return col;
	}
	
	public void attack(int damage) {
		time = 0;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		if(range == 1) {
			if(distance == 0) {
				BaseLevel.attackMob(lane, damage);
			}
			else {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.attackChar(pos - 3, damage);
			}
		}
		else {
			if(distance == 2) {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.attackChar(pos - 3, damage);
				else if(BaseLevel.checkPos(pos - 6)) BaseLevel.attackChar(pos - 6, damage);
				else if(distance < range) BaseLevel.attackMob(lane, damage);
			}
			else if(distance == 1) {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.attackChar(pos - 3, damage);
				else if(distance < range) BaseLevel.attackMob(lane, damage);
			}
			else if(distance < range) BaseLevel.attackMob(lane, damage);
		}
	}
	
	public void takeDamage(int damage) {
		if(armor >= damage) {
			hp -= 1;
		}
		else hp -= damage - armor;
		if(hp <= 0) {
			hp = 0;
		}
	}
	
	public void heal(int amount) {
		hp += amount;
		if(hp > maxHp) hp = maxHp;
	}
	
	public boolean getAlive() {
		return alive;
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
			if(isSpellOnCooldown[i]) k++;
		}
		String[] temp = new String[k];
		for(int i = 0; i < 4; i++) {
			if(isSpellOnCooldown[i]) {
				temp[j] = spellSet[i];
				j++;
			}
		}
		return temp;
		
	}
	
	public int[] getSpellSetCooldowns() {
		int k = 0;
		int j = 0;
		for(int i = 0; i < 4; i++) {
			if(isSpellOnCooldown[i]) k++;
		}
		int[] temp = new int[k];
		for(int i = 0; i < 4; i++) {
			if(isSpellOnCooldown[i]) {
				temp[j] = spellCooldown[i] / 60 + 1;
				j++;
			}
		}
		return temp;
	}
	
	
	
}

package characters;

import game.gamestate.BaseLevel;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

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
	
	String name;
	int hp;
	int maxHp;
	int mp;
	int maxMp;
	int time;
	int timeMax;
	int speed;
	int experience;
	int level;
	int attack;
	int armor;
	int lane;
	int range;
	int distance;
	
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
	
	public BaseCharacter(int num, int pos, Color col, String name, int level, int hp, int maxHp, int mp, int maxMp, int speed, int attack, int range) {
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
		this.attack = attack;
		this.range = range;
	}
	
	public void init() {
		alive = true;
		selected = false;
		attacking = false;
		queued = false;
		wd = GamePanel.HEIGHT / 24;
		ht = GamePanel.HEIGHT / 12;
		time = (int)(Math.random() * 201 + 0);
		timeMax = 10000;
		
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
	}
	
	
	public void draw(Graphics g) {
		if(selected) {
			g.setColor(Color.YELLOW);
			g.fillRect(this.x - 15, this.y - 15, 60, 90);
		}
		g.setColor(col);
		if(pos == 0) {
			x = 460;
			y = 315;
		}
		if(pos == 1) {
			x = 625;
			y = 315;
		}
		if(pos == 2) {
			x = 790;
			y = 315;
		}
		if(pos == 3) {
			x = 425;
			y = 445;
		}
		if(pos == 4) {
			x = 625;
			y = 445;
		}
		if(pos == 5) {
			x = 825;
			y = 445;
		}
		if(pos == 6) {
			x = 390;
			y = 585;
		}
		if(pos == 7) {
			x = 625;
			y = 585;
		}
		if(pos == 8) {
			x = 860;
			y = 585;
		}
		
		g.fillRect(x, y, wd, ht);
		
		if(attacking) {
			g.setColor(Color.RED);
			g.fillRect(x + 10, y - 30, 10, 20);
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
					attack();
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
	
	public void attack() {
		time = 0;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		if(range == 1) {
			if(distance == 0) {
				BaseLevel.attackMob(lane, attack);
			}
			else {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.attackChar(pos - 3, attack);
			}
		}
		else {
			if(distance == 2) {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.attackChar(pos - 3, attack);
				else if(BaseLevel.checkPos(pos - 6)) BaseLevel.attackChar(pos - 6, attack);
				else if(distance < range) BaseLevel.attackMob(lane, attack);
			}
			else if(distance == 1) {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.attackChar(pos - 3, attack);
				else if(distance < range) BaseLevel.attackMob(lane, attack);
			}
			else if(distance < range) BaseLevel.attackMob(lane, attack);
		}
	}
	
	public void takeDamage(int attack) {
		if(armor >= attack) {
			hp -= 1;
		}
		else hp -= attack - armor;
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

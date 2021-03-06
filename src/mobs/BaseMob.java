package mobs;

import game.gamestate.BaseLevel;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class BaseMob {
	
	int gmWd = GamePanel.WIDTH;
	int gmHt = GamePanel.HEIGHT;
	int barWd = gmWd / 36;
	int barHt = gmHt / 18;
	
	public String name;
	int lane;
	int hp;
	int maxHp;
	int attack;
	int delay;
	int delayCount;
	int speed;
	int time;
	int timeMax;
	int x;
	int y;
	int attackPos;
	String weak;;
	String resist;
	boolean attacking = false;
	boolean alive = true;
	Image sprite;
	public int xp;
	public String[] items;
	public String[] rareItems;
	BaseLevel battle;
	
	public BaseMob(int lane, BaseLevel battle) {
		this.lane = lane;
		this.battle = battle;
	}
	
	public BaseMob(int lane, int hp, int attack, int delay, int speed, BaseLevel battle) {
		this.lane = lane;
		this.hp = hp;
		this.maxHp = hp;
		this.attack = attack;
		this.delay = delay;
		this.speed = speed;
		this.delayCount = 0;
		this.battle = battle;
	}
	
	public void init() {
		time = (int)(Math.random() * (speed * 240) - speed * 120);
		timeMax = 60000;
		x = gmWd / 3 + (gmWd * (1 + 2 * lane) / 18);
		y = gmHt * 2 / 5;
	}
	
	public void tick() {
		
		if(alive) {
			if(time < timeMax) time += speed;
			else {
				if(!attacking) {
					startAttack();
					attacking = true;
				}
				delayCount++;
				if(delayCount == delay) {
					delayCount = 0;
					attack();
					time = (int)(Math.random() * (speed * 240) - speed * 120);
					attacking = false;
				}
			}
		}
		if(!alive && delayCount > 0) {
			BaseLevel.grid.attackWarningOff(lane);
			BaseLevel.grid.attackWarningOff(lane + 3);
			BaseLevel.grid.attackWarningOff(lane + 6);
			delayCount = 0;
		}
	}
	
	public void draw(Graphics g) {
		if(alive) {
			g.setColor(Color.BLACK);
			g.drawRect(x - barWd / 2, y - gmHt / 6, barWd, barWd / 5);
			g.setColor(Color.BLUE);
			g.fillRect(x - barWd / 2, y - gmHt / 6, barWd * hp / maxHp, barWd / 5);
			g.drawImage(sprite, x - gmHt / 18, y - gmHt / 7, null);
		}
	}
	
	public void keyPressed(int k) {
		
	}
	
	public void keyReleased(int k) {
		
	}
	
	public boolean takeDamage(int damage) {
		hp -= damage;
		if(hp <= 0) {
			alive = false;
			return true;
		}
		return false;
	}
	
	public boolean takeDamage(int damage, String elem) {
		if(elem.equals(weak)) hp -= damage * 1.2;
		else if(elem.equals(resist)) hp -= damage * 1.2;
		else hp -= damage;
		if(hp <= 0) {
			alive = false;
			return true;
		}
		return false;
	}
	
	public void startAttack() {
		attackPos = 3 * ((int)(Math.random() * 3)) + lane;
		battle.startAttackChar(attackPos);
	}
	
	public void startAttackLane() {
		battle.startAttackLane(lane);
	}
	
	public void startSmartAttack() {
		attackPos = 3 * ((int)(Math.random() * 3)) + lane;
		for(int i = 0; i < 3; i++) {
			if(battle.checkPos(attackPos)) {
				battle.startAttackChar(attackPos);
				return;
			}
			else attackPos = (attackPos + 3) % 9;
		}
		battle.startAttackChar(attackPos);
	}
	
	public void attack() {
		battle.attackChar(attackPos, attack);
	}
	
	public void attackLane() {
		battle.attackChar(lane, attack);
		if(!battle.checkPos(lane)) {
			battle.attackChar(lane + 3, attack);
			battle.attackChar(lane + 6, attack);
		}
		else if(!battle.getCharAt(lane).className.equals("Warrior")) {
			battle.attackChar(lane + 3, attack);
			battle.attackChar(lane + 6, attack);
		}
		else {
			battle.attackChar(lane + 3);
			battle.attackChar(lane + 6);
		}
	}
	
	public String getItem() {
		return items[(int)(Math.random() * items.length)];
	}
	
	public String getRareItem() {
		return rareItems[(int)(Math.random() * rareItems.length)];
	}

}

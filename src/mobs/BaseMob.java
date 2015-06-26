package mobs;

import game.gamestate.BaseLevel;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;

public class BaseMob {
	
	int wd;
	int ht;
	
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
	boolean attacking = false;
	boolean alive = true;
	
	public BaseMob(int lane, int hp, int attack, int delay, int speed) {
		this.lane = lane;
		this.hp = hp;
		this.maxHp = hp;
		this.attack = attack;
		this.delay = delay;
		this.delayCount = 0;
		this.speed = speed;
	}
	
	public void init() {
		wd = GamePanel.HEIGHT / 36;
		ht = GamePanel.HEIGHT / 18;
		time = (int)(Math.random() * 201 + 0);
		timeMax = 10000;
		x = BaseLevel.getGridX(1) - (wd / 2) + ((BaseLevel.getGridX(2) - BaseLevel.getGridX(1)) * (lane + 1) / (4));
		y = BaseLevel.getGridY(1) - ht * 2;
	}
	
	public void tick() {
		if(hp <= 0) alive = false;
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
					time = 0;
					attacking = false;
				}
			}
		}
		if(!alive && delayCount > 0) {
			BaseLevel.grid.attackWarningOff(attackPos);
			delayCount = 0;
		}
	}
	
	public void draw(Graphics g) {
		if(alive) {
			g.setColor(Color.BLACK);
			if(attacking) g.setColor(Color.RED);
			g.fillRect(x, y, wd, ht);
			g.drawRect(x, y - (ht / 2), wd, wd / 5);
			g.setColor(Color.BLUE);
			g.fillRect(x, y - (ht / 2), wd * hp / maxHp, wd / 5);
		}
		
	}
	
	public void keyPressed(int k) {
		
	}
	
	public void keyReleased(int k) {
		
	}
	
	public boolean takeDamage(int damage) {
		hp -= damage;
		if(hp <= 0) return true;
		return false;
	}
	
	public void startAttack() {
		attackPos = 3 * ((int)(Math.random() * 3)) + lane;
		BaseLevel.startAttackChar(attackPos);
	}
	
	public void attack() {
		BaseLevel.attackChar(attackPos, attack);
	}
	
	public void die() {
		
	}

}

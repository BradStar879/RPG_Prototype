package mobs;

import game.gamestate.BaseLevel;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Pig extends BaseMob{

	public Pig(int lane, int hp, int attack, int delay, int speed) {
		super(lane, hp, attack, delay, speed);
	}
	
	public Pig(int lane) {
		super(lane);
		maxHp = 100;
		hp = maxHp;
		attack = 25;
		delay = 120;
		speed = 90;
		xp = 50;
		items = new String[]{"HP Potion", "MP Potion", "Fire Arrow"};
		rareItems = new String[]{"Wood Staff", "Wood Sword", "Wood Spear", "Wood Bow", "Cloth Cloak", "Cloth Suit", "Cloth Armor", 
				"Cloth Vest", "Cloth Robe"};
		sprite = new ImageIcon("Sprites/Pig.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
	}
	
	public void startAttack() {
		BaseLevel.startAttackLane(lane);
	}
	
	public void attack() {
		BaseLevel.attackChar(lane, attack);
		if(!BaseLevel.checkPos(lane)) {
			BaseLevel.attackChar(lane + 3, attack);
			BaseLevel.attackChar(lane + 6, attack);
		}
		else if(!BaseLevel.getCharAt(lane).className.equals("Warrior")) {
			BaseLevel.attackChar(lane + 3, attack);
			BaseLevel.attackChar(lane + 6, attack);
		}
		else {
			BaseLevel.attackChar(lane + 3);
			BaseLevel.attackChar(lane + 6);
		}
	}

}

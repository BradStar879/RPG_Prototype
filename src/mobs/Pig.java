package mobs;

import game.gamestate.BaseLevel;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Pig extends BaseMob{

	public Pig(int lane, BaseLevel battle) {
		super(lane, battle);
		name = "Pig";
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
		super.startAttackLane();
	}
	
	public void attack() {
		super.attackLane();
	}

}

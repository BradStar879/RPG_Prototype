package mobs;

import game.gamestate.BaseLevel;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Deer extends BaseMob{
	
	public Deer(int lane, BaseLevel battle) {
		super(lane, battle);
		name = "Deer";
		maxHp = 150;
		hp = maxHp;
		attack = 40;
		delay = 110;
		speed = 85;
		xp = 100;
		items = new String[]{"HP Potion", "MP Potion", "Fire Arrow"};
		rareItems = new String[]{"Wood Staff", "Wood Sword", "Wood Spear", "Wood Bow", "Cloth Cloak", "Cloth Suit", "Cloth Armor", 
				"Cloth Vest", "Cloth Robe"};
		sprite = new ImageIcon("Sprites/Deer.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
	}
	
	public void startAttack() {
		super.startAttackLane();
	}
	
	public void attack() {
		super.attackLane();
	}

}

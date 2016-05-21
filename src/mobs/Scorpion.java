package mobs;

import java.awt.Image;

import javax.swing.ImageIcon;

import game.gamestate.BaseLevel;

public class Scorpion extends BaseMob{

	public Scorpion(int lane, BaseLevel battle) {
		super(lane, battle);
		name = "Scorpion";
		maxHp = 100;
		hp = maxHp;
		attack = 30;
		delay = 120;
		speed = 100;
		xp = 75;
		items = new String[]{"HP Potion", "MP Potion", "Fire Arrow"};
		rareItems = new String[]{"Wood Staff", "Wood Sword", "Wood Spear", "Wood Bow", "Cloth Cloak", "Cloth Suit", "Cloth Armor", 
				"Cloth Vest", "Cloth Robe"};
		sprite = new ImageIcon("Sprites/Scorpion.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
	}

	public void startAttack() {
		super.startAttackLane();
	}
	
	public void attack() {
		super.attackLane();
	}
}

package mobs;

import game.gamestate.BaseLevel;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Falcon extends BaseMob{

	public Falcon(int lane, BaseLevel battle) {
		super(lane, battle);
		name = "Falcon";
		maxHp = 90;
		hp = maxHp;
		attack = 25;
		delay = 100;
		speed = 150;
		xp = 65;
		items = new String[]{"HP Potion", "MP Potion", "Fire Arrow"};
		rareItems = new String[]{"Wood Staff", "Wood Sword", "Wood Spear", "Wood Bow", "Cloth Cloak", "Cloth Suit", "Cloth Armor", 
				"Cloth Vest", "Cloth Robe"};
		sprite = new ImageIcon("Sprites/Falcon.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
	}
	
	public void startAttack() {
		super.startSmartAttack();
	}
}

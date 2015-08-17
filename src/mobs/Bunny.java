package mobs;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Bunny extends BaseMob{

	public Bunny(int lane) {
		super(lane);
		maxHp = 75;
		hp = maxHp;
		attack = 25;
		delay = 120;
		speed = 125;
		xp = 40;
		items = new String[]{"HP Potion", "MP Potion", "Fire Arrow"};
		rareItems = new String[]{"Wood Staff", "Wood Sword", "Wood Spear", "Wood Bow", "Cloth Cloak", "Cloth Suit", "Cloth Armor", 
				"Cloth Vest", "Cloth Robe"};
		sprite = new ImageIcon("Sprites/Bunny.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
	}

}

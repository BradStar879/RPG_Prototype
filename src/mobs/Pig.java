package mobs;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Pig extends BaseMob{

	public Pig(int lane, int hp, int attack, int delay, int speed) {
		super(lane, hp, attack, delay, speed);
	}
	
	public Pig(int lane) {
		super(lane);
		hp = 100;
		maxHp = 100;
		attack = 25;
		delay = 120;
		speed = 100;
		xp = 50;
		items = new String[]{"HP Potion", "MP Potion", "Fire Arrow"};
		sprite = new ImageIcon("Sprites/Pig.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
	}

}

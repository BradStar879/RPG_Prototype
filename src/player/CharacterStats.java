package player;

import java.io.Serializable;


public class CharacterStats implements Serializable {
	
	private static final long serialVersionUID = 510125958167092204L;
	public String name;
	public String className;
	public int hp;
	public int maxHp;
	public int mp;
	public int maxMp;
	public int speed;
	public int experience;
	public int experienceCap;
	public int level;
	public int attack;
	public int armor;
	public int baseSpellAttack;
	
	public CharacterStats(String name, String className) {
		
		this.name = name;
		this.className = className;
		if(className.equals("Warrior")) {
			maxHp = 1000;
			maxMp = 0;
			speed = 30;
			attack = 15;
			armor = 8;
			baseSpellAttack = 0;
		}
		else if(className.equals("White Mage")) {
			maxHp = 500;
			maxMp = 200;
			speed = 18;
			attack = 5;
			armor = 2;
			baseSpellAttack = 20;
		}
		else if(className.equals("Black Mage")) {
			maxHp = 500;
			maxMp = 200;
			speed = 20;
			attack = 8;
			armor = 2;
			baseSpellAttack = 40;
		}
		else if(className.equals("Archer")) {
			maxHp = 700;
			maxMp = 0;
			speed = 45;
			attack = 13;
			armor = 4;
			baseSpellAttack = 0;
		}
		else if(className.equals("Spearman")) {
			maxHp = 900;
			maxMp = 0;
			speed = 30;
			attack = 17;
			armor = 6;
			baseSpellAttack = 0;
		}
		else if(className.equals("Monk")) {
			maxHp = 800;
			maxMp = 0;
			speed = 35;
			attack = 8;
			armor = 5;
			baseSpellAttack = 0;
		}
		
		hp = maxHp;
		mp = maxMp;
		experience = 0;
		level = 1;
		experienceCap = 500;
		
	}
	
	public void levelUp() {
		level++;
		maxHp *= 1.2;
		maxMp *= 1.2;
		hp = maxHp;
		mp = maxMp;
		speed++;
		armor++;
		attack++;
		baseSpellAttack++;
		experience = 0;
		experienceCap += 500;
	}

}

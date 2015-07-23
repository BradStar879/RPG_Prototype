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
	public int spellPower;
	public int baseHp;
	public int baseMp;
	public int baseAttack;
	public int baseArmor;
	public int baseSpellPower;
	
	public CharacterStats(String name, String className) {
		
		this.name = name;
		this.className = className;
		if(className.equals("Warrior")) {
			maxHp = 100;
			maxMp = 75;
			speed = 110;
			attack = 15;
			armor = 15;
			spellPower = 0;
		}
		else if(className.equals("White Mage")) {
			maxHp = 50;
			maxMp = 100;
			speed = 125;
			attack = 5;
			armor = 7;
			spellPower = 25;
		}
		else if(className.equals("Black Mage")) {
			maxHp = 50;
			maxMp = 100;
			speed = 100;
			attack = 5;
			armor = 5;
			spellPower = 40;
		}
		else if(className.equals("Archer")) {
			maxHp = 75;
			maxMp = 0;
			speed = 167;
			attack = 15;
			armor = 8;
			spellPower = 10;
		}
		else if(className.equals("Spearman")) {
			maxHp = 90;
			maxMp = 50;
			speed = 100;
			attack = 15;
			armor = 12;
			spellPower = 0;
		}
		else if(className.equals("Monk")) {
			maxHp = 75;
			maxMp = 4;
			speed = 125;
			attack = 25;
			armor = 10;
			spellPower = 5;
		}
		
		baseHp = maxHp;
		baseMp = maxMp;
		baseAttack = attack;
		baseArmor = armor;
		baseSpellPower = spellPower; 
		
		hp = maxHp;
		mp = maxMp;
		experience = 0;	
		level = 1;
		experienceCap = 500;
		
	}
	
	public void levelUp() {
		experience -= experienceCap;
		level++;
		maxHp *= 1.2;
		maxMp *= 1.2;
		hp = maxHp;
		mp = maxMp;
		speed++;
		armor++;
		attack++;
		spellPower++;
		experienceCap += 500;
	}

}

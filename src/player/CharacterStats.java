package player;

import java.io.Serializable;
import java.util.Vector;

import characters.Spells;


public class CharacterStats implements Serializable {
	
	private static final long serialVersionUID = 510125958167092204L;
	public String name;
	public String className;
	public int hp;
	public int maxHp;
	public int mp;
	public int maxMp;
	public String mpName;
	public int speed;
	public int experience;
	public int experienceCap;
	public int baseExperienceCap;
	public int level;
	public int attack;
	public int armor;
	public int spellPower;
	public int baseHp;
	public int baseMp;
	public int baseAttack;
	public int baseArmor;
	public int baseSpellPower;
	public Equipment weapon;
	public Equipment clothes;
	public Vector<Spells> spells = new Vector<Spells>();
	
	public CharacterStats(String name, String className) {
		
		this.name = name;
		this.className = className;
		if(className.equals("Warrior")) {
			maxHp = 100;
			maxMp = 75;
			mpName = "Rage";
			speed = 110;
			attack = 15;
			armor = 15;
			spellPower = 0;
			spells.add(new Spells("Block"));
			spells.add(new Spells("Bash"));
			spells.add(new Spells("Stone Skin"));
		}
		else if(className.equals("White Mage")) {
			maxHp = 50;
			maxMp = 100;
			mpName = "MP";
			speed = 125;
			attack = 5;
			armor = 7;
			spellPower = 25;
			spells.add(new Spells("Rejuvinate"));
			spells.add(new Spells("Heal"));
			spells.add(new Spells("Self Heal"));
			spells.add(new Spells("Mega Heal"));
			spells.add(new Spells("Regen"));
		}
		else if(className.equals("Black Mage")) {
			maxHp = 50;
			maxMp = 100;
			mpName = "MP";
			speed = 100;
			attack = 5;
			armor = 5;
			spellPower = 40;
			spells.add(new Spells("Rejuvinate"));
			spells.add(new Spells("Fire"));
			spells.add(new Spells("Ice"));
			spells.add(new Spells("Lightning"));
		}
		else if(className.equals("Archer")) {
			maxHp = 75;
			maxMp = 0;
			mpName = "Arrows";
			speed = 167;
			attack = 15;
			armor = 8;
			spellPower = 10;
			spells.add(new Spells("Speed Up"));
			spells.add(new Spells("Fire Arrow"));
		}
		else if(className.equals("Spearman")) {
			maxHp = 90;
			maxMp = 50;
			mpName = "Energy";
			speed = 100;
			attack = 15;
			armor = 12;
			spellPower = 0;
			spells.add(new Spells("Stretch"));
			spells.add(new Spells("Throw Spear"));
		}
		else if(className.equals("Monk")) {
			maxHp = 75;
			maxMp = 4;
			mpName = "Charms";
			speed = 125;
			attack = 25;
			armor = 10;
			spellPower = 5;
			spells.add(new Spells("Meditate"));
			spells.add(new Spells("Power Punch"));
			spells.add(new Spells("Cleanse"));
		}
		
		baseHp = maxHp;
		baseMp = maxMp;
		baseAttack = attack;
		baseArmor = armor;
		baseSpellPower = spellPower; 
		
		hp = maxHp;
		if(className.equals("Black Mage") || className.equals("White Mage") || className.equals("Monk") || className.equals("Spearman")) mp = maxMp;
		else mp = 0;
		experience = 0;	
		level = 1;
		experienceCap = 500;
		baseExperienceCap = experienceCap;
		weapon = new Equipment("", true);
		clothes = new Equipment("", false);
		
	}
	
	public void levelUp() {
		experience -= experienceCap;
		level++;
		
		if(level <= 5) maxHp = baseHp + (baseHp / 8 * (level - 1));
		else if(level <= 20) maxHp = baseHp + (baseHp / 10 * level);
		else if( level <= 40) maxHp = baseHp * 3 + (baseHp / 5 * (level - 20));
		else if( level <= 60) maxHp = baseHp * 7 + (baseHp * 3 / 10 * (level - 40));
		else if( level <= 80) maxHp = baseHp * 13 + (baseHp * 2 / 5 * (level - 60));
		else if( level <= 100) maxHp = baseHp * 21 + (baseHp / 2 * (level - 80));
		
		//room for improvement
		if(className.equals("White Mage") || className.equals("Black Mage")) {
			if(level <= 5) maxMp = baseMp + (baseMp / 8 * (level - 1));
			else if(level <= 20) maxMp = baseMp + (baseMp / 10 * level);
			else if( level <= 40) maxMp = baseMp * 3 + (baseMp / 5 * (level - 20));
			else if( level <= 60) maxMp = baseMp * 7 + (baseMp * 3 / 10 * (level - 40));
			else if( level <= 80) maxMp = baseMp * 13 + (baseMp * 2 / 5 * (level - 60));
			else if( level <= 100) maxMp = baseMp * 21 + (baseMp / 2 * (level - 80));
		}
		
		if(level <= 5) armor = baseArmor + (baseArmor / 8 * (level - 1));
		else if(level <= 20) armor = baseArmor + (baseArmor / 10 * level);
		else if( level <= 40) armor = baseArmor * 3 + (baseArmor / 5 * (level - 20));
		else if( level <= 60) armor = baseArmor * 7 + (baseArmor * 3 / 10 * (level - 40));
		else if( level <= 80) armor = baseArmor * 13 + (baseArmor * 2 / 5 * (level - 60));
		else if( level <= 100) armor = baseArmor * 21 + (baseArmor / 2 * (level - 80));
		
		if(level <= 5) attack = baseAttack + (baseAttack / 8 * (level - 1));
		else if(level <= 20) attack = baseAttack + (baseAttack / 10 * level);
		else if( level <= 40) attack = baseAttack * 3 + (baseAttack / 5 * (level - 20));
		else if( level <= 60) attack = baseAttack * 7 + (baseAttack * 3 / 10 * (level - 40));
		else if( level <= 80) attack = baseAttack * 13 + (baseAttack * 2 / 5 * (level - 60));
		else if( level <= 100) attack = baseAttack * 21 + (baseAttack / 2 * (level - 80));
		
		if(level <= 5) spellPower = baseSpellPower + (baseSpellPower / 8 * (level - 1));
		else if(level <= 20) spellPower = baseSpellPower + (baseSpellPower / 10 * level);
		else if( level <= 40) spellPower = baseSpellPower * 3 + (baseSpellPower / 5 * (level - 20));
		else if( level <= 60) spellPower = baseSpellPower * 7 + (baseSpellPower * 3 / 10 * (level - 40));
		else if( level <= 80) spellPower = baseSpellPower * 13 + (baseSpellPower * 2 / 5 * (level - 60));
		else if( level <= 100) spellPower = baseSpellPower * 21 + (baseSpellPower / 2 * (level - 80));
		
		if(level <= 5) experienceCap += 250;
		else if(level <= 20) experienceCap += 500;
		else if(level <= 40) experienceCap += 1000;	
		else if(level <= 60) experienceCap += 2000;
		else if(level <= 80) experienceCap += 4000;
		else if(level <= 100) experienceCap += 8000;
		
		
		else if( level <= 60) experienceCap = baseExperienceCap * 7 + (baseExperienceCap * 3 / 10 * (level - 40));
		else if( level <= 80) experienceCap = baseExperienceCap * 13 + (baseExperienceCap * 2 / 5 * (level - 60));
		else if( level <= 100) experienceCap = baseExperienceCap * 21 + (baseExperienceCap / 2 * (level - 80));
		
		hp = maxHp;
		if(className.equals("Black Mage") || className.equals("White Mage") || className.equals("Monk") || className.equals("Spearman")) mp = maxMp;
		if(level % 10 == 0) speed += 10;
	}

}

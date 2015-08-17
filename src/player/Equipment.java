package player;

import java.io.Serializable;

public class Equipment implements Serializable{
	
	private static final long serialVersionUID = -2469126349240379013L;
	public String name;
	public int attack;
	public int spellPower;
	public int defense;
	public int value;
	public boolean weapon;
	public String classType;
	
	public Equipment(String name, boolean weapon) {
		
		this.name = name;
		this.weapon = weapon;
		if(weapon) {
			defense = 0;
			
			//Mage
			if(name.equals("Wood Staff")) {
				attack = 1;
				spellPower = 3;
				value = 50;
				classType = "Mage";
			}
			//Warrior
			else if(name.equals("Wood Sword")) {
				attack = 4;
				spellPower = 0;
				value = 50;
				classType = "Warrior";
			}
			//Spearman
			else if(name.equals("Wood Spear")) {
				attack = 4;
				spellPower = 0;
				value = 50;
				classType = "Spearman";
			}
			//Archer
			else if(name.equals("Wood Bow")) {
				attack = 3;
				spellPower = 0;
				value = 50;
				classType = "Archer";
			}
			else {
				this.name = "None";
				attack = 0;
				spellPower = 0;
				value = 0;
				classType = "Any";
			}
		}
		
		else {
			attack = 0;
			spellPower = 0;
			
			//Mage
			if(name.equals("Cloth Cloak")) {
				defense = 2;
				value = 50;
				classType = "Mage";
			}
			//Warrior
			else if(name.equals("Cloth Suit")) {
				defense = 5;
				value = 50;
				classType = "Warrior";
			}
			//Spearman
			else if(name.equals("Cloth Armor")) {
				defense = 4;
				value = 50;
				classType = "Spearman";
			}
			//Archer
			else if(name.equals("Cloth Vest")) {
				defense = 3;
				value = 50;
				classType = "Archer";
			}
			//Monk
			else if(name.equals("Cloth Robe")) {
				defense = 3;
				value = 50;
				classType = "Monk";
			}
			
			else {
				this.name = "None";
				defense = 0;
				value = 0;
				classType = "Any";
			}
		}
		
		
	}
	
	public static String getClassType(String item) {
		
		if(item.indexOf("Staff") != -1 || item.indexOf("Cloak") != -1) return "Mage";
		else if(item.indexOf("Sword") != -1 || item.indexOf("Suit") != -1) return "Warrior";
		else if(item.indexOf("Spear") != -1 || item.indexOf("Armor") != -1) return "Spearman";
		else if(item.indexOf("Bow") != -1 || item.indexOf("Vest") != -1) return "Archer";
		else if(item.indexOf("Robe") != -1) return "Monk";
		else return "";
	}
	
	

}

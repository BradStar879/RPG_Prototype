package player;

import game.gamestate.BaseWorld;

import java.util.Arrays;

import physics.Lists;

public class Item {
	
	public Item() {}
	
	public static boolean use(CharacterStats team, String item) {
		
		if(item.equals("HP Potion")) {
			if(team.hp == team.maxHp) return false;
			else {
				team.hp += 50;
				if(team.hp > team.maxHp) team.hp = team.maxHp;
				BaseWorld.inv.removeItem(item);
				return true;
			}
		}
		else if(item.equals("MP Potion")) {
			if(team.mp == team.maxMp || !(team.className.equals("White Mage") || team.className.equals("Black Mage"))) return false;
			else {
				team.mp += 50;
				if(team.mp > team.maxMp) team.mp = team.maxMp;
				BaseWorld.inv.removeItem(item);
				return true;
			}
		}
		else if(isWeapon(item) && team.className.indexOf(Equipment.getClassType(item)) != -1) {
			if(!team.weapon.name.equals("None")) BaseWorld.inv.addItem(team.weapon.name);
			team.weapon = new Equipment(item, true);
			BaseWorld.inv.removeItem(team.weapon.name);
			return true;
		}
		else if(isArmor(item) && team.className.indexOf(Equipment.getClassType(item)) != -1){
			if(!team.clothes.name.equals("None")) BaseWorld.inv.addItem(team.clothes.name);
			team.clothes = new Equipment(item, false);
			BaseWorld.inv.removeItem(team.clothes.name);
			return true;
		}
		return false;
	}
	
	public static boolean usable(String item) {
		if(item.equals("HP Potion") ||
		   item.equals("MP Potion") ||
		   isWeapon(item) ||
		   isArmor(item)) return true;
		return false;
	}
	
	public static int getBuyPrice(String item) {
		if(item.indexOf("Wood") == 0 || item.indexOf("Cloth") == 0) return 100;
		else if(item.indexOf("Potion") == 3) return 40;
		else if(item.indexOf("Fire Arrow") == 0) return 10;
		return 0;
	}
	
	public static int getSellPrice(String item) {
		return getBuyPrice(item) / 2;
	}
	
	public static String[] getDescription(String item) {
		if(item.equals("HP Potion")) return new String[]{"Restores 50 HP."};
		else if(item.equals("MP Potion")) return new String[]{"Restores 50 MP."};
		else if(item.equals("Fire Arrow")) return new String[]{"Flaming arrow", "used by Archers."};
		return new String[]{""};
	}
	
	public static boolean isWeapon(String item) {
		if(item.indexOf("Staff") != -1 || item.indexOf("Sword") != -1 || item.indexOf("Spear") != -1 || 
				item.indexOf("Bow") != -1 || item.indexOf("Robe") != -1) return true;
		else return false;
	}
	
	public static boolean isArmor(String item) {
		if(item.indexOf("Cloak") != -1 || item.indexOf("Suit") != -1 || item.indexOf("Armor") != -1 ||
				item.indexOf("Vest") != -1 || item.indexOf("Robe") != -1) return true;
		else return false;
	}
	
}

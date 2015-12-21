package player;

import java.util.Arrays;

import game.gamestate.BaseWorld;

public class Item {
	
	static String[] weapons = {"Wood Staff", "Wood Sword", "Wood Spear", "Wood Bow"};
	static String[] clothes = {"Cloth Cloak", "Cloth Suit", "Cloth Armor", "Cloth Vest", "Cloth Robe"};
	
	public Item() {}
	
	public static void use(CharacterStats team, String item) {
		
		if(item.equals("HP Potion")) {
			if(team.hp == team.maxHp) return;
			else {
				team.hp += 50;
				if(team.hp > team.maxHp) team.hp = team.maxHp;
				BaseWorld.inv.removeItem(item);
			}
		}
		else if(item.equals("MP Potion")) {
			if(team.mp == team.maxMp || !(team.className.equals("White Mage") || team.className.equals("Black Mage"))) return;
			else {
				team.mp += 50;
				if(team.mp > team.maxMp) team.mp = team.maxMp;
				BaseWorld.inv.removeItem(item);
			}
		}
		else if(Arrays.asList(weapons).indexOf(item) != -1 && team.className.indexOf(Equipment.getClassType(item)) != -1) {
			if(!team.weapon.name.equals("None")) BaseWorld.inv.addItem(team.weapon.name);
			team.weapon = new Equipment(item, true);
			BaseWorld.inv.removeItem(team.weapon.name);
		}
		else if(Arrays.asList(clothes).indexOf(item) != -1 && team.className.indexOf(Equipment.getClassType(item)) != -1){
			if(!team.clothes.name.equals("None")) BaseWorld.inv.addItem(team.clothes.name);
			team.clothes = new Equipment(item, false);
			BaseWorld.inv.removeItem(team.clothes.name);
		}
		
	}
	
	public static boolean usable(String item) {
		if(item.equals("HP Potion") ||
		   item.equals("MP Potion") ||
		   Arrays.asList(weapons).indexOf(item) != -1 ||
		   Arrays.asList(clothes).indexOf(item) != -1) return true;
		return false;
	}

}

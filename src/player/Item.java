package player;

import game.gamestate.World;

public class Item {
	
	public Item() {}
	
	public static void use(CharacterStats team, String item) {
		
		if(item.equals("HP Potion")) {
			if(team.hp == team.maxHp) return;
			else {
				team.hp += 50;
				if(team.hp > team.maxHp) team.hp = team.maxHp;
				World.inv.removeItem(item);
			}
		}
		else if(item.equals("MP Potion")) {
			if(team.mp == team.maxMp) return;
			else {
				team.mp += 50;
				if(team.mp > team.maxMp) team.mp = team.maxMp;
				World.inv.removeItem(item);
			}
		}
	}
	
	public static boolean usable(String item) {
		if(item.equals("HP Potion") ||
		   item.equals("MP Potion")) return true;
		return false;
	}

}

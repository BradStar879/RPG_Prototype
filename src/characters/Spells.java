package characters;

import java.io.Serializable;

import player.CharacterStats;

public class Spells implements Serializable{
	
	private static final long serialVersionUID = 2824591198980951905L;
	public String name;
	public int mpCost;
	public int cooldown;
	public int duration = 0;
	public String[] description;
	public boolean ooc;

	public Spells(String name) {
		this.name = name;
		
		//White Mage
		if(name.equals("Rejuvinate")) {
			mpCost = 0;
			cooldown = 0;
			description = new String[]{"Recovers 10 MP."};
			ooc = false;
		}
		else if(name.equals("Heal")) {
			mpCost = 10;
			cooldown = 1800;
			description = new String[]{"Heals target", "equal to spell", "power."};
			ooc = true;
		}
		else if(name.equals("Self Heal")) {
			mpCost = 10;
			cooldown = 1800;
			description = new String[]{"Heals self", "equal to spell", "power."};
			ooc = false;
		}
		else if(name.equals("Mega Heal")) {
			mpCost = 30;
			cooldown = 3600;
			description = new String[]{"Heals target", "equal to", "2 times", "spell power."};
			ooc = true;
		}
		else if(name.equals("Regen")) {
			mpCost = 10;
			cooldown = 1800;
			description = new String[]{"Heals target", "equal to 1/4", "spell power", "every 3 seconds."};
			duration = 1080;
			ooc = false;
		}
		
		//Black Mage
		else if(name.equals("Fire")) {
			mpCost = 20;
			cooldown = 1800;
			description = new String[]{"Fire spell", "that damages", "target equal to", "spell power."};
			ooc = false;
		}
		else if(name.equals("Ice")) {
			mpCost = 20;
			cooldown = 1800;
			description = new String[]{"Ice spell that", "damages target equal", "to spell power."};
			ooc = false;
		}
		else if(name.equals("Lightning")) {
			mpCost = 20;
			cooldown = 1800;
			description = new String[]{"Lightning spell", "that damages", "target equal to", "spell power."};
			ooc = false;
		}
		
		//Warrior
		else if(name.equals("Block")) {
			mpCost = 15;
			cooldown = 3600;
			description = new String[]{"Blocks the next", "incoming attack."};
			ooc = false;
		}
		else if(name.equals("Bash")) {
			mpCost = 25;
			cooldown = 0;
			description = new String[]{"Damages target", "equal to 1.5", "times attack."};
			ooc = false;
		}
		else if(name.equals("Stone Skin")) {
			mpCost = 25;
			cooldown = 0;
			description = new String[]{"Increases defense", "by 3 for 15", "seconds."};
			ooc = false;
		}
		
		//Monk
		else if(name.equals("Meditate")) {
			mpCost = 0;
			cooldown = 3600;
			description = new String[]{"Attack and armor", "are increased by", "spell power for", "30 seconds."};
			ooc = false;
		}
		else if(name.equals("Power Punch")) {
			mpCost = 1;
			cooldown = 0;
			description = new String[]{"Damages target", "for double attack."};
			ooc = false;
		}
		else if(name.equals("Cleanse")) {
			mpCost = 1;
			cooldown = 0;	
			description = new String[]{"Heals self for", "1/4 of max HP."};
			ooc = false;
		}
		
		//Spearman
		else if(name.equals("Stretch")) {
			mpCost = 20;
			cooldown = 3600;
			description = new String[]{"Increases range", "for 20 seconds."};
			ooc = false;
		}
		else if(name.equals("Throw Spear")) {
			mpCost = 40;
			cooldown = 1800;
			description = new String[]{"Damages target", "for 1.5 times", "attack."};
			ooc = false;
		}
		
		//Archer
		else if(name.equals("Speed Up")) {
			mpCost = 0;
			cooldown = 1800;
			description = new String[]{"Speeds up timer", "for 20 seconds."};
			ooc = false;
		}
		else if(name.equals("Fire Arrow")) {
			mpCost = 1;
			cooldown = 0;
			description = new String[]{"Damages target", "for 1.5 times attack."};
			ooc = false;
		}
		
		else {
			mpCost = -1;
			cooldown = -1;
		}
		
	}
	
	public boolean useMenuSpell(CharacterStats caster, CharacterStats target) {
		if(name.equals("Heal") || name.equals("Mega Heal")) {
			if(target.hp > target.maxHp) return false;
			if(name.equals("Heal")) target.hp += caster.spellPower;
			else if(name.equals("Mega Heal")) target.hp += caster.spellPower * 2;
			if(target.hp > target.maxHp) target.hp = target.maxHp;
		}
		caster.mp -= mpCost;
		return true;
	}
	
}

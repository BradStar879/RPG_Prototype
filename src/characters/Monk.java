package characters;

import game.gamestate.BaseLevel;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import physics.Sounds;

public class Monk extends BaseCharacter{
	
	int baseAttack;
	int baseArmor;
	Sounds attackSound = new Sounds("Sounds/punch.wav");
	Sounds strongAttackSound = new Sounds("Sounds/hard punch.wav");
	Sounds rejSound = new Sounds("Sounds/charging up.wav");

	public Monk(int num, int pos, BaseLevel battle, String name, int level, int hp,
			int maxHp, int mp, int maxMp, int speed, int attack, int armor, int baseSpellAttack, Vector<Spells> spells) {
		super(num, pos, battle, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, baseSpellAttack, spells);
	}
	
	public void init() {
		super.init();
		className = "Monk";
		mpName = "Charms";
		range = 1;
		sprite = new ImageIcon("Sprites/Monk.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
		
		moveSet[1] = "Charm Spell";
		moveSet[2] = "Meditate";
		
		baseAttack = attack;
		baseArmor = armor;
	}
	
	public void tick() {
		super.tick();
		if(moveCooldown[2] > 1800) {
			attack = baseAttack + spellPower;
			armor = baseArmor + spellPower;
		}
		else {
			attack = baseAttack;
			armor = baseArmor;
		}
	}
	
	public void keyPressed(int k) {
		super.keyPressed(k);
		if(attacking) {;
			if(k == KeyEvent.VK_RIGHT) {
				if(battle.getMenuOption().equals("Attack")) {
					attack(attack);
					attackSound.play();
				}
				else if(battle.getMenuOption().equals("Charm Spell")) {
					baseMenu = false;
					spellMenu = true;
					battle.changeMenuOptions(spellSet.elementAt(0), spellSet.elementAt(1), spellSet.elementAt(2), spellSet.elementAt(3), 
						isSpellOnCooldown.elementAt(0), isSpellOnCooldown.elementAt(1), isSpellOnCooldown.elementAt(2), isSpellOnCooldown.elementAt(3));
				}
				else if(battle.getMenuOption().equals("Meditate") && !isMoveOnCooldown[2]) {
					meditate();
				}
				else if(battle.getMenuOption().equals("Power Punch") && !isSpellOnCooldown.elementAt(0)) {
					powerPunch();
					spellMenu = false;
				}
				else if(battle.getMenuOption().equals("Cleanse") && !isSpellOnCooldown.elementAt(1)) {
					cleanse();
					spellMenu = false;
				}
				battle.changeMenuSelect("RIGHT");
				menuSelect = 0;
				menuOption = 0;
			}
		}
	}
	
	public void attack(int damage) {
		time = 0;
		attacking = false;
		queued = false;
		battle.dequeueTurn();
		if(range == 1) {
			if(distance == 0) {
				if(battle.attackMob(lane, damage) && mp != maxMp) mp++;
			}
			else {
				if(battle.checkPos(pos - 3)) battle.attackChar(pos - 3, damage);
			}
		}
		else {
			if(distance == 2) {
				if(battle.checkPos(pos - 3)) battle.attackChar(pos - 3, damage);
				else if(battle.checkPos(pos - 6)) battle.attackChar(pos - 6, damage);
				else if(distance < range) 
					if(battle.attackMob(lane, damage) && mp != maxMp) mp++;
			}
			else if(distance == 1) {
				if(battle.checkPos(pos - 3)) battle.attackChar(pos - 3, damage);
				else if(distance < range)
					if(battle.attackMob(lane, damage) && mp != maxMp) mp++;
			}
			else if(distance < range) 
				if(battle.attackMob(lane, damage) && mp != maxMp) mp++;
		}
	}
	
	public void meditate() {
		time = 0;
		moveCooldown[2] = 3600;
		isMoveOnCooldown[2] = true;
		attacking = false;
		queued = false;
		battle.dequeueTurn();
		rejSound.play();
	}
	
	public void powerPunch() {
		time = 0;
		attacking = false;
		queued = false;
		mp--;
		attack((int)(attack * 2));
		strongAttackSound.play();
	}
	
	public void cleanse() {
		time = 0;
		attacking = false;
		queued = false;
		mp--;
		heal(maxHp / 4);
		battle.dequeueTurn();
		rejSound.play();
	}

}

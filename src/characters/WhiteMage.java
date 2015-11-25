package characters;

import game.gamestate.BaseLevel;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import physics.Sounds;

public class WhiteMage extends BaseCharacter {
	
	Sounds attackSound = new Sounds("Sounds/punch.wav");
	Sounds healSound = new Sounds("Sounds/heal.wav");
	Sounds rejSound = new Sounds("Sounds/charging up.wav");
	
	public WhiteMage(int num, int pos, Color col, String name, int level,
			int hp, int maxHp, int mp, int maxMp, int speed, int attack, int armor, 
			int baseSpellAttack, Vector<Spells> spells) {
		super(num, pos, col, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, baseSpellAttack, spells);
		className = "White Mage";
		mpName = "MP";
		range = 3;
	}
	
	public void init() {
		super.init();
		sprite = new ImageIcon("Sprites/WhiteMage.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
	
		moveSet[1] = "White Magic";
		moveSet[2] = "Rejuvinate";
		
	}
	
	public void tick() {
		super.tick();
		
		if(distance == 2) speed = baseSpeed + 15;
		else speed = baseSpeed;
	}
	
	public void keyPressed(int k) {
		super.keyPressed(k);
		if(attacking){
			if(k == KeyEvent.VK_RIGHT) {
				if(BaseLevel.getMenuOption().equals("Attack")) {
					healAttack();
					attackSound.play();
				}
				else if(BaseLevel.getMenuOption().equals("White Magic")) {
					baseMenu = false;
					spellMenu = true;
					BaseLevel.changeMenuOptions(spellSet.elementAt(0), spellSet.elementAt(1), spellSet.elementAt(2), spellSet.elementAt(3), 
						isSpellOnCooldown.elementAt(0), isSpellOnCooldown.elementAt(1), isSpellOnCooldown.elementAt(2), isSpellOnCooldown.elementAt(3));
				}
				else if(BaseLevel.getMenuOption().equals("Rejuvinate")) {
					rejuvinate();
				}
				else if(BaseLevel.getMenuOption().equals("Heal") && !isSpellOnCooldown.elementAt(0) && mp >= 10) {
					healMove();
					spellMenu = false;
				}
				else if(BaseLevel.getMenuOption().equals("Self Heal") && !isSpellOnCooldown.elementAt(1) && mp >= 10) {
					selfHeal();
					spellMenu = false;
				}
				else if(BaseLevel.getMenuOption().equals("Mega Heal") && !isSpellOnCooldown.elementAt(2) && mp >= 30) {
					megaHeal();
					spellMenu = false;
				}
				else if(BaseLevel.getMenuOption().equals("Regen") && !isSpellOnCooldown.elementAt(3) && mp >= 10) {
					regenMove();
					spellMenu = false;
				}
				BaseLevel.changeMenuSelect("RIGHT");
				menuSelect = 0;
				menuOption = 0;
			}
		}
	}
	
	public void healAttack() {
		time = 0;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		if(range == 1) {
			if(distance == 0) {
				BaseLevel.attackMob(lane, attack);
			}
			else {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.attackChar(pos - 3, attack);
			}
		}
		else {
			if(distance == 2) {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).heal(attack);
				else if(BaseLevel.checkPos(pos - 6)) BaseLevel.getCharAt(pos - 6).heal(attack);
				else if(distance < range) BaseLevel.attackMob(lane, attack);
			}
			else if(distance == 1) {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).heal(attack);
				else if(distance < range) BaseLevel.attackMob(lane, attack);
			}
			else if(distance < range) BaseLevel.attackMob(lane, attack);
		}
	}
	
	public void rejuvinate() {
		time = 0;
		attacking = false;
		queued = false;
		mp += 10;
		if(mp > maxMp) mp = maxMp;
		BaseLevel.dequeueTurn();
		rejSound.play();
	}
	
	public void healMove() {
		time = 0;
		mp -= 10;
		spellCooldown.set(0, 1800);
		isSpellOnCooldown.set(0, true);
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		if(distance == 2) {
			if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).heal(spellPower);
			else if(BaseLevel.checkPos(pos - 6)) BaseLevel.getCharAt(pos - 6).heal(spellPower);
		}
		else if(distance == 1) {
			if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).heal(spellPower);
		}
		healSound.play();
	}
	
	public void selfHeal() {
		time = 0;
		mp -= 10;
		spellCooldown.set(1, 1800);
		isSpellOnCooldown.set(1, true);
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		heal(spellPower);
		healSound.play();
	}
	
	public void megaHeal() {
		time = 0;
		mp -= 30;
		spellCooldown.set(2, 3600);
		isSpellOnCooldown.set(2, true);
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		if(distance == 2) {
			if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).heal(spellPower * 2);
			else if(BaseLevel.checkPos(pos - 6)) BaseLevel.getCharAt(pos - 6).heal(spellPower * 2);
		}
		else if(distance == 1) {
			if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).heal(spellPower * 2);
		}
		healSound.play();
	}
	
	public void regenMove() {
		time = 0;
		mp -= 10;
		spellCooldown.set(3, 1200);
		isSpellOnCooldown.set(3, true);
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		if(distance == 2) {
			if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).regen(spellPower / 4, 1080);
			else if(BaseLevel.checkPos(pos - 6)) BaseLevel.getCharAt(pos - 6).regen(spellPower / 4, 1080);
		}
		else if(distance == 1) {
			if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).regen(spellPower / 4, 1080);
		}
		healSound.play();
	}
}

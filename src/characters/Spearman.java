 package characters;

import game.gamestate.BaseLevel;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import physics.Sounds;

public class Spearman extends BaseCharacter{
	
	int energyCount = 0;
	Sounds attackSound = new Sounds("Sounds/warrior sword slash.wav");
	Sounds strongAttackSound = new Sounds("Sounds/warrior hard sword slash.wav");
	Sounds rejSound = new Sounds("Sounds/charging up.wav");

	public Spearman(int num, int pos, Color col, String name, int level,
			int hp, int maxHp, int mp, int maxMp, int speed, int attack, int armor, 
			int baseSpellAttack, Vector<Spells> spells) {
		super(num, pos, col, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, baseSpellAttack, spells);
	}
	
	public void init() {
		super.init();
		mp = maxMp;
		className = "Spearman";
		mpName = "Energy";
		range = 2;
		sprite = new ImageIcon("Sprites/Spearman.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
		
		moveSet[1] = "Energy";
		moveSet[2] = "Stretch";
		moveMpCost[2] = 20;
	}
	
	public void tick() {
		super.tick();
		
		energyCount++;
		if(energyCount == 120) {
			energyCount = 0;
			if(mp != maxMp) mp++;
		}
		if(moveCooldown[2] > 1800) range = 3;
		else range = 2;
	}
	
	public void keyPressed(int k) {
		super.keyPressed(k);
		if(attacking) {
			if(k == KeyEvent.VK_RIGHT) {
				if(BaseLevel.getMenuOption().equals("Attack")) {
					attack(attack);
					attackSound.play();
				}
				else if(BaseLevel.getMenuOption().equals("Energy Attack")) {
					baseMenu = false;
					spellMenu = true;
					BaseLevel.changeMenuOptions(spellSet.elementAt(0), spellSet.elementAt(1), spellSet.elementAt(2), spellSet.elementAt(3), 
						isSpellOnCooldown.elementAt(0), isSpellOnCooldown.elementAt(1), isSpellOnCooldown.elementAt(2), isSpellOnCooldown.elementAt(3));
				}
				else if(BaseLevel.getMenuOption().equals("Stretch") && !isMoveOnCooldown[2]) {
					stretch();
				}
				else if(BaseLevel.getMenuOption().equals("Throw Spear") && !isSpellOnCooldown.elementAt(0)) {
					throwSpear();
					spellMenu = false;
				}
				BaseLevel.changeMenuSelect("RIGHT");
				menuSelect = 0;
				menuOption = 0;
			}
		}
	}
	
	public void stretch() {
		time = 0;
		mp -= 20;
		moveCooldown[2] = 3600;
		isMoveOnCooldown[2] = true;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		rejSound.play();
	}
	
	public void throwSpear() {
		time = 0;
		mp -= 40;
		spellCooldown.set(0, 1800);
		isSpellOnCooldown.set(0, true);
		attacking = false;
		queued = false;
		range = 3;
		attack((int)(attack * 1.5));
		range = 2;
		strongAttackSound.play();
	}
}

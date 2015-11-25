package characters;

import game.gamestate.BaseLevel;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import physics.Sounds;

public class Warrior extends BaseCharacter{
	
	boolean block;
	int stoneSkinCount;
	int baseArmor;
	Sounds attackSound = new Sounds("Sounds/warrior sword slash.wav");
	Sounds strongAttackSound = new Sounds("Sounds/warrior hard sword slash.wav");
	Sounds shieldDingSound = new Sounds("Sounds/shield ding.wav");
	Sounds rejSound = new Sounds("Sounds/charging up.wav");

	public Warrior(int num, int pos, Color col, String name, int level, int hp,
			int maxHp, int mp, int maxMp, int speed, int attack, int armor, int baseSpellAttack, Vector<Spells> spells) {
		super(num, pos, col, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, baseSpellAttack, spells);
		
	} 
	
	public void init() {
		super.init();
		this.baseArmor = armor;
		sprite = new ImageIcon("Sprites/Warrior.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
		mp = 0;
		className = "Warrior";
		mpName = "Rage";
		range = 1;
		moveSet[1] = "Rage Attack";
		moveSet[2] = "Block";
		
		block = false;
		moveMpCost[2] = 15;
		stoneSkinCount = 0;
	}
	
	public void tick() {
		super.tick();
		
		if(stoneSkinCount > 0) {
			stoneSkinCount--;
			armor = baseArmor + 3;
		}
	}
	
	public void keyPressed(int k) {
		super.keyPressed(k);
		if(attacking){
			if(k == KeyEvent.VK_RIGHT) {
				if(BaseLevel.getMenuOption().equals("Attack")) {
					attack(attack);
					attackSound.play();
				}
				else if(BaseLevel.getMenuOption().equals("Rage Attack")) {
					baseMenu = false;
					spellMenu = true;
					BaseLevel.changeMenuOptions(spellSet.elementAt(0), spellSet.elementAt(1), spellSet.elementAt(2), spellSet.elementAt(3), 
						isSpellOnCooldown.elementAt(0), isSpellOnCooldown.elementAt(1), isSpellOnCooldown.elementAt(2), isSpellOnCooldown.elementAt(3));
				}
				else if(BaseLevel.getMenuOption().equals("Block") && !isMoveOnCooldown[2]) {
					time = 0;
					block();
				}
				else if(BaseLevel.getMenuOption().equals("Bash") && !isSpellOnCooldown.elementAt(0)) {
					time = 0;
					spellMenu = false;
					bash();
				}
				else if(BaseLevel.getMenuOption().equals("Stone Skin") && !isSpellOnCooldown.elementAt(1)) {
					time = 0;
					spellMenu = false;
					stoneSkin();
				}
				BaseLevel.changeMenuSelect("RIGHT");
				menuSelect = 0;
				menuOption = 0;
			}
		}
	}
	
	public void takeDamage(int damage) {
		mp += damage * 3 / 5;
		if(mp > maxMp) mp = maxMp;
		if(block) {
			block = false;
			shieldDingSound.play();
		}
		else {
			if(armor >= damage) {
				hp -= 1;
			}
			else hp -= damage - armor;
			if(hp <= 0) {
				hp = 0;
			}
			damageSound.play();
		}
	}
	
	public void block() {
		block = true;
		mp -= 15;
		moveCooldown[2] = 3600;
		isMoveOnCooldown[2] = true;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
	}
	
	public void bash() {
		mp -= 25;
		attacking = false;
		queued = false;
		attack((int)(attack * 1.5));
		strongAttackSound.play();
	}
	
	public void stoneSkin() {
		mp -= 25;
		attacking = false;
		queued = false;
		stoneSkinCount = 900;
		BaseLevel.dequeueTurn();
		rejSound.play();
	}
}


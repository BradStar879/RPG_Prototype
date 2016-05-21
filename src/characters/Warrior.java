package characters;

import game.gamestate.BaseLevel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import physics.Sounds;
import display.Animations;

public class Warrior extends BaseCharacter{
	
	boolean block;
	int stoneSkinCount;
	int baseArmor;
	Sounds attackSound = new Sounds("Sounds/warrior sword slash.wav");
	Sounds strongAttackSound = new Sounds("Sounds/warrior hard sword slash.wav");
	Sounds shieldDingSound = new Sounds("Sounds/shield ding.wav");
	Sounds rejSound = new Sounds("Sounds/charging up.wav");
	String[] slashString;
	Image[] slash;
	boolean slashAni;
	Animations ani;

	public Warrior(int num, int pos, BaseLevel battle, String name, int level, int hp,
			int maxHp, int mp, int maxMp, int speed, int attack, int armor, int baseSpellAttack, Vector<Spells> spells) {
		super(num, pos, battle, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, baseSpellAttack, spells);
		
	} 
	
	public void init() {
		super.init();
		slashString = new String[7];
		slash = new Image[7];
		slashAni = false;
		ani = new Animations();
		for(int i = 0; i < slashString.length; i++) {
			slashString[i] = "Sprites/Slash" + (i+1) + ".png";
			slash[i] = new ImageIcon(slashString[i]).getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
		}
		sprite = new ImageIcon("Sprites/Warrior.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
		
		this.baseArmor = armor;
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
				if(battle.getMenuOption().equals("Attack")) {
					attack(attack);
					attackSound.play();
				}
				else if(battle.getMenuOption().equals("Rage Attack")) {
					baseMenu = false;
					spellMenu = true;
					battle.changeMenuOptions(spellSet.elementAt(0), spellSet.elementAt(1), spellSet.elementAt(2), spellSet.elementAt(3), 
						isSpellOnCooldown.elementAt(0), isSpellOnCooldown.elementAt(1), isSpellOnCooldown.elementAt(2), isSpellOnCooldown.elementAt(3));
				}
				else if(battle.getMenuOption().equals("Block") && !isMoveOnCooldown[2]) {
					time = 0;
					block();
				}
				else if(battle.getMenuOption().equals("Bash") && !isSpellOnCooldown.elementAt(0)) {
					time = 0;
					spellMenu = false;
					bash();
				}
				else if(battle.getMenuOption().equals("Stone Skin") && !isSpellOnCooldown.elementAt(1)) {
					time = 0;
					spellMenu = false;
					stoneSkin();
				}
				battle.changeMenuSelect("RIGHT");
				menuSelect = 0;
				menuOption = 0;
			}
		}
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		if(slashAni) slashAni = ani.animate(g);
	}
	
	public void attack(int damage) {
		super.attack(damage);
		int xx = gmWd / 3 + (gmWd * (1 + 2 * lane) / 18) - gmHt / 18;
		int yy = gmHt * 2 / 5 - gmHt / 7;
		ani.setAnimate(slash, xx, yy);
		slashAni = true;
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
				if(!battle.checkDeadPos(pos)) {
					battle.changePos(pos, false);
					battle.changeDeadPos(pos, true);
				}
				else {
					dead2 = true;
					battle.changePos(pos, false);
					battle.changeDead2Pos(pos, true);
				}
				alive = false;
				attacking = false;
				regenCount = 0;
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
		battle.dequeueTurn();
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
		battle.dequeueTurn();
		rejSound.play();
	}
}


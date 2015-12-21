package characters;

import game.gamestate.BaseLevel;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import physics.Sounds;

public class BlackMage extends BaseCharacter{
	
	Sounds attackSound = new Sounds("Sounds/punch.wav");
	Sounds fireSound = new Sounds("Sounds/black mage fire.wav");
	Sounds iceSound = new Sounds("Sounds/black mage ice.wav");
	Sounds lightningSound = new Sounds("Sounds/black mage lightning.wav");
	Sounds rejSound = new Sounds("Sounds/charging up.wav");

	public BlackMage(int num, int pos, BaseLevel battle, String name, int level,
			int hp, int maxHp, int mp, int maxMp, int speed, int attack, int armor,
			int spellPower, Vector<Spells> spells) {
		super(num, pos, battle, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, spellPower, spells);
	}
	
	public void init() {
		super.init();
		className = "Black Mage";
		mpName = "MP";
		range = 3;
		sprite = new ImageIcon("Sprites/BlackMage.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);

		moveSet[1] = "Black Magic";
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
				if(battle.getMenuOption().equals("Attack")) {
					attack(attack);
					attackSound.play();
				}
				else if(battle.getMenuOption().equals("Black Magic")) {
					baseMenu = false;
					spellMenu = true;
					battle.changeMenuOptions(spellSet.elementAt(0), spellSet.elementAt(1), spellSet.elementAt(2), spellSet.elementAt(3), 
						isSpellOnCooldown.elementAt(0), isSpellOnCooldown.elementAt(1), isSpellOnCooldown.elementAt(2), isSpellOnCooldown.elementAt(3));
				}
				else if(battle.getMenuOption().equals("Rejuvinate")) {
					rejuvinate();
				}
				else if(battle.getMenuOption().equals("Fire") && !isSpellOnCooldown.elementAt(0)) {
					fire();
					spellMenu = false;
				}
				else if(battle.getMenuOption().equals("Ice") && !isSpellOnCooldown.elementAt(1)) {
					ice();
					spellMenu = false;
				}
				else if(battle.getMenuOption().equals("Lightning") && !isSpellOnCooldown.elementAt(2)) {
					lightning();
					spellMenu = false;
				}
				battle.changeMenuSelect("RIGHT");
				menuSelect = 0;
				menuOption = 0;
			}
		}
	}
	
	public void rejuvinate() {
		time = 0;
		attacking = false;
		queued = false;
		mp += 10;
		if(mp > maxMp) mp = maxMp;
		battle.dequeueTurn();
		rejSound.play();
	}
	
	public void fire() {
		time = 0;
		spellCooldown.set(0, 1800);
		isSpellOnCooldown.set(0, true);
		attacking = false;
		queued = false;
		mp -= 20;;
		attack(spellPower);
		fireSound.play();
	}
	
	public void ice() {
		time = 0;
		spellCooldown.set(1, 1800);
		isSpellOnCooldown.set(1, true);
		attacking = false;
		queued = false;
		mp -= 20;
		attack(spellPower);
		iceSound.play();
	}
	
	public void lightning() {
		time = 0;
		spellCooldown.set(2, 1800);
		isSpellOnCooldown.set(2, true);
		attacking = false;
		queued = false;
		mp -= 20;
		attack(spellPower);
		lightningSound.play();
	}

}

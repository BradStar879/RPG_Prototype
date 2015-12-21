package characters;

import game.gamestate.BaseLevel;
import game.gamestate.BaseWorld;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import physics.Sounds;

public class Archer extends BaseCharacter{
	
	int speedBoostDuration;
	int critChance;
	int critAttack;
	int numFireArrows;
	Sounds arrowSound = new Sounds("Sounds/arrow.wav");

	public Archer(int num, int pos, BaseLevel battle, String name, int level, int hp,
			int maxHp, int mp, int maxMp, int speed, int attack, int armor, int baseSpellAttack, Vector<Spells> spells) {
		super(num, pos, battle, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, baseSpellAttack, spells);
		this.critAttack = (int)(attack * 2.2);
	}
	
	public void init() {
		super.init();
		className = "Archer";
		mpName = "Arrows";
		range = 3;
		sprite = new ImageIcon("Sprites/Archer.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
		critChance = 10;

		moveSet[1] = "Spec. Arrow";
		moveSet[2] = "Speed Up";
		
		speedBoostDuration = 0;
		baseSpeed = speed;
	}
	
	public void tick() {
		super.tick();
		
		if(speedBoostDuration > 0) {
			speed = baseSpeed + spellPower * 5;
			speedBoostDuration--;
		}
		else speed = baseSpeed;
		
		numFireArrows = BaseWorld.inv.checkItem("Fire Arrow");
		if(numFireArrows == 0) isSpellOnCooldown.set(0, true);
	}
	
	public void keyPressed(int k) {
		super.keyPressed(k);
		if(attacking){
			if(k == KeyEvent.VK_RIGHT) {
				if(battle.getMenuOption().equals("Attack")) {
					time = 0;
					arrowSound.play();
					if(Math.random() * 100 < critChance) attack(critAttack);
					else attack(attack);
				}
				else if(battle.getMenuOption().equals("Spec. Arrow")) {
					battle.changeMenuOptions(spellSet.elementAt(0), spellSet.elementAt(1), spellSet.elementAt(2), spellSet.elementAt(3), 
							isSpellOnCooldown.elementAt(0), isSpellOnCooldown.elementAt(1), isSpellOnCooldown.elementAt(2), isSpellOnCooldown.elementAt(3));
					baseMenu = false;
					spellMenu = true;
				}
				else if(battle.getMenuOption().equals("Speed Up") && !isMoveOnCooldown[2]) {
					time = 0;
					speedUp();
				}
				else if(battle.getMenuOption().equals("Fire Arrow") && !isSpellOnCooldown.elementAt(0)) {
					time = 0;
					spellMenu = false;
					fireArrow();
				}
				battle.changeMenuSelect("RIGHT");
				menuSelect = 0;
				menuOption = 0;
			}
		}
	}
	
	public void speedUp() {
		speedBoostDuration = 1200;
		moveCooldown[2] = 1800;
		isMoveOnCooldown[2] = true;
		attacking = false;
		queued = false;
		battle.dequeueTurn();
	}
	
	public void fireArrow() {
		attacking = false;
		queued = false;
		attack((int)(attack * 1.5));
		BaseWorld.inv.removeItem("Fire Arrow");
		arrowSound.play();
	}
	
	public void poisonArrow() {
		attacking = false;
		queued = false;
		attack(attack);
		BaseWorld.inv.removeItem("Poison Arrow");
		arrowSound.play();
	}
}

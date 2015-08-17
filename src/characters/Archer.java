package characters;

import game.gamestate.BaseLevel;
import game.gamestate.World;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Archer extends BaseCharacter{
	
	int speedBoostDuration;
	int critChance;
	int critAttack;
	int numFireArrows;

	public Archer(int num, int pos, Color col, String name, int level, int hp,
			int maxHp, int mp, int maxMp, int speed, int attack, int armor, int baseSpellAttack) {
		super(num, pos, col, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, baseSpellAttack);
		this.critAttack = (int)(attack * 2.2);
	}
	
	public void init() {
		super.init();
		className = "Archer";
		mpName = "Arrows";
		range = 3;
		sprite = new ImageIcon("Sprites/Archer.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
		critChance = 10;
		moveSet[0] = "Attack";
		moveSet[1] = "Spec. Arrow";
		moveSet[2] = "Speed Up";
		moveSet[3] = "Item";
		
		isMoveOnCooldown[0] = false;
		isMoveOnCooldown[1] = false;
		isMoveOnCooldown[2] = false;
		isMoveOnCooldown[3] = false;
		
		spellSet[0] = "Fire Arrow";
		
		speedBoostDuration = 0;
		baseSpeed = speed;
	}
	
	public void tick() {
		super.tick();
		if(moveCooldown[2] > 0) {
			moveCooldown[2]--;
		}
		else isMoveOnCooldown[2] = false;
		
		if(speedBoostDuration > 0) {
			speed = baseSpeed + spellPower * 5;
			speedBoostDuration--;
		}
		else speed = baseSpeed;
		
		numFireArrows = World.inv.checkItem("Fire Arrow");
		if(numFireArrows == 0) isSpellOnCooldown[0] = true;
	}
	
	public void keyPressed(int k) {
		if(selected) {
			if(k == KeyEvent.VK_W && pos > 2 && !BaseLevel.checkPos(pos - 3)) {
				pos -= 3;
				BaseLevel.changePos(pos + 3, false);
				BaseLevel.changePos(pos, true);
			}
			if(k == KeyEvent.VK_S && pos < 6 && !BaseLevel.checkPos(pos + 3)) {
				pos += 3;
				BaseLevel.changePos(pos - 3, false);
				BaseLevel.changePos(pos, true);
			}
			if(k == KeyEvent.VK_A && pos != 0  && pos != 3 && pos != 6)
				if (!BaseLevel.checkPos(pos - 1)) {
					pos -= 1;
					BaseLevel.changePos(pos + 1, false);
					BaseLevel.changePos(pos, true);
				}
			if(k == KeyEvent.VK_D && pos != 2  && pos != 5 && pos != 8)
				if (!BaseLevel.checkPos(pos + 1)) {
					pos += 1;
					BaseLevel.changePos(pos - 1, false);
					BaseLevel.changePos(pos, true);
				}
			
			}
		
		
		if(attacking){
			if(k == KeyEvent.VK_UP) BaseLevel.changeMenuSelect("UP");
			if(k == KeyEvent.VK_DOWN) BaseLevel.changeMenuSelect("DOWN");
			if(k == KeyEvent.VK_RIGHT) {
				if(BaseLevel.getMenuOption().equals("Attack")) {
					time = 0;
					if(Math.random() * 100 < critChance) attack(critAttack);
					else attack(attack);
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Spec. Arrow")) {
					BaseLevel.changeMenuOptions(spellSet[0], spellSet[1], spellSet[2], spellSet[3], 
							isSpellOnCooldown[0], isSpellOnCooldown[1], isSpellOnCooldown[2], isSpellOnCooldown[3]);
					baseMenu = false;
					spellMenu = true;
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Speed Up") && !isMoveOnCooldown[1]) {
					time = 0;
					speedUp();
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Fire Arrow") && !isSpellOnCooldown[0]) {
					time = 0;
					spellMenu = false;
					fireArrow();
					BaseLevel.changeMenuSelect("RIGHT");
				}
			}
			if(k == KeyEvent.VK_LEFT) {
				if(baseMenu) {
					attacking = false;
					BaseLevel.dequeueTurn();
					BaseLevel.enqueueTurn(this);
				}
				else {
					baseMenu = true;
					BaseLevel.changeMenuOptions(moveSet[0], moveSet[1], moveSet[2], moveSet[3], 
							isMoveOnCooldown[0], isMoveOnCooldown[1], isMoveOnCooldown[2], isMoveOnCooldown[3]);
				}
			BaseLevel.changeMenuSelect("LEFT");
			}
		}
	}
	
	public void speedUp() {
		speedBoostDuration = 1200;
		moveCooldown[2] = 1800;
		isMoveOnCooldown[2] = true;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
	}
	
	public void fireArrow() {
		attacking = false;
		queued = false;
		attack((int)(attack * 1.5));
		World.inv.removeItem("Fire Arrow");
	}
	
	public void poisonArrow() {
		attacking = false;
		queued = false;
		attack(attack);
		World.inv.removeItem("Poison Arrow");
	}
}

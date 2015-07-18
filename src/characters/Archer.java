package characters;

import game.gamestate.BaseLevel;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Archer extends BaseCharacter{
	
	int speedBooster;
	int speedBoostDuration;
	int critChance;
	int baseAttack;
	int critAttack;

	public Archer(int num, int pos, Color col, String name, int level, int hp,
			int maxHp, int mp, int maxMp, int speed, int attack, int armor, int baseSpellAttack) {
		super(num, pos, col, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, baseSpellAttack);
		this.baseAttack = attack;
		this.critAttack = (int)(attack * 2.2);
	}
	
	public void init() {
		super.init();
		className = "Archer";
		range = 3;
		critChance = 10;
		moveSet[0] =  "Attack";
		moveSet[1] = "Speed Up";
		moveSet[2] = "";
		moveSet[3] = "Item";
		
		isMoveOnCooldown[0] = false;
		isMoveOnCooldown[1] = false;
		isMoveOnCooldown[2] = false;
		isMoveOnCooldown[3] = false;
		
		speedBoostDuration = 0;
		baseSpeed = speed;
		speedBooster = 8;
	}
	
	public void tick() {
		super.tick();
		if(moveCooldown[1] > 0) {
			moveCooldown[1]--;
		}
		else isMoveOnCooldown[1] = false;
		if(speedBoostDuration > 0) {
			speed = baseSpeed + speedBooster;
			speedBoostDuration--;
		}
		else speed = baseSpeed;
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
					if(Math.random() * 100 < critChance) {
						attack = critAttack;
						attack();
						attack = baseAttack;
					}
					else attack();
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Speed Up") && !isMoveOnCooldown[1]) {
					time = 0;
					speedUp();
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
		moveCooldown[1] = 1800;
		isMoveOnCooldown[1] = true;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
	}
}

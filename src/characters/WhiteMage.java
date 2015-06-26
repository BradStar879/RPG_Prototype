package characters;

import game.gamestate.BaseLevel;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class WhiteMage extends BaseCharacter {

	BaseCharacter regenTarget;
	
	public WhiteMage(int num, int pos, Color col, String name, int level,
			int hp, int maxHp, int mp, int maxMp, int speed, int attack,
			int range) {
		super(num, pos, col, "White Mage", level, hp, maxHp, mp, maxMp, speed, attack, 3);
	}
	
	public void init() {
		super.init();
		
		moveSet[0] =  "Attack";
		moveSet[1] = "White Magic";
		moveSet[2] = "";
		moveSet[3] = "Item";
		
		spellSet[0] = "Heal";
		spellSet[1] = "Mega Heal";
		spellSet[2] = "Regen";

	}
	
	public void tick() {
		super.tick();
		if(attacking & spellMenu) BaseLevel.changeMenuOptions(spellSet[0], spellSet[1], spellSet[2], spellSet[3], 
				isSpellOnCooldown[0], isSpellOnCooldown[1], isSpellOnCooldown[2], isSpellOnCooldown[3]);
		
		if(spellCooldown[0] > 0) {
			spellCooldown[0]--;
		}
		else isSpellOnCooldown[0] = false;
		
		if(spellCooldown[1] > 0) {
			spellCooldown[1]--;
		}
		else isSpellOnCooldown[1] = false;
		
		if(spellCooldown[2] > 0) {
			spellCooldown[2]--;
			if(spellCooldown[2] % 180 == 0 && regenTarget != null) regenTarget.heal(40);
		}
		else isSpellOnCooldown[2] = false;
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
					attack();
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("White Magic")) {
					BaseLevel.changeMenuOptions(spellSet[0], spellSet[1], spellSet[2], spellSet[3], 
							isSpellOnCooldown[0], isSpellOnCooldown[1], isSpellOnCooldown[2], isSpellOnCooldown[3]);
					baseMenu = false;
					spellMenu = true;
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Heal") && !isSpellOnCooldown[0]) {
					healMove();
					spellMenu = false;
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Mega Heal") && !isSpellOnCooldown[1]) {
					megaHeal();
					spellMenu = false;
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Regen") && !isSpellOnCooldown[2]) {
					regen();
					spellMenu = false;
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
					spellMenu = false;
					baseMenu = true;
					BaseLevel.changeMenuOptions(moveSet[0], moveSet[1], moveSet[2], moveSet[3], 
							isMoveOnCooldown[0], isMoveOnCooldown[1], isMoveOnCooldown[2], isMoveOnCooldown[3]);
				}
			BaseLevel.changeMenuSelect("LEFT");
			}
		}
	}
	
	public void healMove() {
		time = 0;
		spellCooldown[0] = 1800;
		isSpellOnCooldown[0] = true;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		if(distance == 2) {
			if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).heal(100);
			else if(BaseLevel.checkPos(pos - 6)) BaseLevel.getCharAt(pos - 6).heal(100);
		}
		else if(distance == 1) {
			if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).heal(100);
		}
	}
	
	public void megaHeal() {
		time = 0;
		spellCooldown[1] = 3600;
		isSpellOnCooldown[1] = true;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		if(distance == 2) {
			if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).heal(300);
			else if(BaseLevel.checkPos(pos - 6)) BaseLevel.getCharAt(pos - 6).heal(300);
		}
		else if(distance == 1) {
			if(BaseLevel.checkPos(pos - 3)) BaseLevel.getCharAt(pos - 3).heal(300);
		}
	}
	
	public void regen() {
		time = 0;
		spellCooldown[2] = 1200;
		isSpellOnCooldown[2] = true;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		if(distance == 2) {
			if(BaseLevel.checkPos(pos - 3)) regenTarget = BaseLevel.getCharAt(pos - 3);
			else if(BaseLevel.checkPos(pos - 6)) regenTarget = BaseLevel.getCharAt(pos - 6);
			else regenTarget = null;
		}
		else if(distance == 1) {
			if(BaseLevel.checkPos(pos - 3)) regenTarget = BaseLevel.getCharAt(pos - 3);
			else regenTarget = null;
			}
	}
}

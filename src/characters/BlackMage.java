package characters;

import game.gamestate.BaseLevel;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class BlackMage extends BaseCharacter{

	public BlackMage(int num, int pos, Color col, String name, int level,
			int hp, int maxHp, int mp, int maxMp, int speed, int attack,
			int range) {
		super(num, pos, col, "Black Mage", level, hp, maxHp, mp, maxMp, speed, attack, 3);
	}
	
	public void init() {
		super.init();
		moveSet[0] =  "Attack";
		moveSet[1] = "Magic";
		moveSet[2] = "Dark Magic";
		moveSet[3] = "Item";
		
		spellSet[0] = "Fire";
		spellSet[1] = "Ice";
		spellSet[2] = "Lightning";

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
				else if(BaseLevel.getMenuOption().equals("Magic")) {
					BaseLevel.changeMenuOptions(spellSet[0], spellSet[1], spellSet[2], spellSet[3], 
							isSpellOnCooldown[0], isSpellOnCooldown[1], isSpellOnCooldown[2], isSpellOnCooldown[3]);
					baseMenu = false;
					spellMenu = true;
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Fire")) {
					attack();
					spellMenu = false;
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Ice")) {
					attack();
					spellMenu = false;
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Lightning")) {
					attack();
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

}
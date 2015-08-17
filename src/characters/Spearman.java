package characters;

import game.gamestate.BaseLevel;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Spearman extends BaseCharacter{
	
	int energyCount = 0;

	public Spearman(int num, int pos, Color col, String name, int level,
			int hp, int maxHp, int mp, int maxMp, int speed, int attack, int armor, 
			int baseSpellAttack) {
		super(num, pos, col, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, baseSpellAttack);
	}
	
	public void init() {
		super.init();
		mp = maxMp;
		className = "Spearman";
		mpName = "Energy";
		range = 2;
		sprite = new ImageIcon("Sprites/Spearman.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
		moveSet[0] = "Attack";
		moveSet[1] = "Energy";
		moveSet[2] = "Stretch";
		moveSet[3] = "Item";
		
		spellSet[0] = "Throw Spear";
		
	}
	
	public void tick() {
		super.tick();
		if(moveCooldown[2] > 0) {
			moveCooldown[2]--;
			if(moveCooldown[2] > 2400) range = 3;
			else range = 2;
		}
		else if(mp < 20) isMoveOnCooldown[2] = true;
		else isMoveOnCooldown[2] = false;
		
		if(spellCooldown[0] > 0) {
			spellCooldown[0]--;
		}
		else if(mp < 40) isSpellOnCooldown[0] = true;
		else isSpellOnCooldown[0] = false;
		
		energyCount++;
		if(energyCount == 120) {
			energyCount = 0;
			if(mp != maxMp) mp++;
		}
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
		
		
		if(attacking) {
			if(k == KeyEvent.VK_UP) BaseLevel.changeMenuSelect("UP");
			if(k == KeyEvent.VK_DOWN) BaseLevel.changeMenuSelect("DOWN");
			if(k == KeyEvent.VK_RIGHT) {
				if(BaseLevel.getMenuOption().equals("Attack")) {
					attack(attack);
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Energy Attack")) {
					baseMenu = false;
					spellMenu = true;
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Stretch") && !isMoveOnCooldown[2]) {
					stretch();
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Throw Spear") && !isSpellOnCooldown[0]) {
					throwSpear();
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
				}
			BaseLevel.changeMenuSelect("LEFT");
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
	}
	
	public void throwSpear() {
		time = 0;
		mp -= 40;
		spellCooldown[0] = 1800;
		isSpellOnCooldown[0] = true;
		attacking = false;
		queued = false;
		range = 3;
		attack((int)(attack * 1.5));
		range = 2;
	}
}

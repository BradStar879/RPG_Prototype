package characters;

import game.gamestate.BaseLevel;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Monk extends BaseCharacter{
	
	int baseAttack;
	int baseArmor;

	public Monk(int num, int pos, Color col, String name, int level, int hp,
			int maxHp, int mp, int maxMp, int speed, int attack, int armor, int baseSpellAttack) {
		super(num, pos, col, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, baseSpellAttack);
	}
	
	public void init() {
		super.init();
		className = "Monk";
		mpName = "Charms";
		range = 1;
		sprite = new ImageIcon("Sprites/Monk.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
		moveSet[0] = "Attack";
		moveSet[1] = "Charm Spell";
		moveSet[2] = "Meditate";
		moveSet[3] = "Item";
		
		spellSet[0] = "Power Punch";
		spellSet[1] = "Cleanse";

		baseAttack = attack;
		baseArmor = armor;
	}
	
	public void tick() {
		super.tick();
		if(moveCooldown[2] > 0) {
			moveCooldown[2]--;
			if(moveCooldown[2] > 1800) {
				attack = baseAttack + spellPower;
				armor = baseArmor + spellPower;
			}
			else attack = baseAttack;
		}
		
		if(spellCooldown[0] > 0) {
			spellCooldown[0]--;
		}
		else if(mp == 0) isSpellOnCooldown[0] = true;
		else isSpellOnCooldown[0] = false;
		
		if(spellCooldown[1] > 0) {
			spellCooldown[1]--;
		}
		else if(mp == 0) isSpellOnCooldown[1] = true;
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
		
		
		if(attacking) {
			if(k == KeyEvent.VK_UP) BaseLevel.changeMenuSelect("UP");
			if(k == KeyEvent.VK_DOWN) BaseLevel.changeMenuSelect("DOWN");
			if(k == KeyEvent.VK_RIGHT) {
				if(BaseLevel.getMenuOption().equals("Attack")) {
					attack(attack);
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Charm Spell")) {
					baseMenu = false;
					spellMenu = true;
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Meditate") && !isMoveOnCooldown[1]) {
					meditate();
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Power Punch") && !isSpellOnCooldown[0]) {
					powerPunch();
					BaseLevel.changeMenuSelect("RIGHT");
					spellMenu = false;
				}
				else if(BaseLevel.getMenuOption().equals("Cleanse") && !isSpellOnCooldown[1]) {
					cleanse();
					BaseLevel.changeMenuSelect("RIGHT");
					spellMenu = false;
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
	
	public void attack(int damage) {
		time = 0;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
		if(range == 1) {
			if(distance == 0) {
				if(BaseLevel.attackMob(lane, damage) && mp != maxMp) mp++;
			}
			else {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.attackChar(pos - 3, damage);
			}
		}
		else {
			if(distance == 2) {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.attackChar(pos - 3, damage);
				else if(BaseLevel.checkPos(pos - 6)) BaseLevel.attackChar(pos - 6, damage);
				else if(distance < range) 
					if(BaseLevel.attackMob(lane, damage) && mp != maxMp) mp++;
			}
			else if(distance == 1) {
				if(BaseLevel.checkPos(pos - 3)) BaseLevel.attackChar(pos - 3, damage);
				else if(distance < range)
					if(BaseLevel.attackMob(lane, damage) && mp != maxMp) mp++;
			}
			else if(distance < range) 
				if(BaseLevel.attackMob(lane, damage) && mp != maxMp) mp++;
		}
	}
	
	public void meditate() {
		time = 0;
		moveCooldown[2] = 3600;
		isMoveOnCooldown[2] = true;
		attacking = false;
		queued = false;
		BaseLevel.dequeueTurn();
	}
	
	public void powerPunch() {
		time = 0;
		attacking = false;
		queued = false;
		mp--;
		attack((int)(attack * 2));
	}
	
	public void cleanse() {
		time = 0;
		attacking = false;
		queued = false;
		mp--;
		heal(maxHp / 4);
		BaseLevel.dequeueTurn();
	}

}

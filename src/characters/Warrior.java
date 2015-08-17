package characters;

import game.gamestate.BaseLevel;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Warrior extends BaseCharacter{
	
	boolean block;
	int stoneSkinCount;
	int baseArmor;

	public Warrior(int num, int pos, Color col, String name, int level, int hp,
			int maxHp, int mp, int maxMp, int speed, int attack, int armor, int baseSpellAttack) {
		super(num, pos, col, name, level, hp, maxHp, mp, maxMp, speed, attack, armor, baseSpellAttack);
		
	} 
	
	public void init() {
		super.init();
		this.baseArmor = armor;
		sprite = new ImageIcon("Sprites/Warrior.png").getImage().getScaledInstance(gmHt / 9, gmHt / 6, Image.SCALE_SMOOTH);
		mp = 0;
		className = "Warrior";
		mpName = "Rage";
		range = 1;
		moveSet[0] = "Attack";
		moveSet[1] = "Rage Attack";
		moveSet[2] = "Block";
		moveSet[3] = "Item";
		
		spellSet[0] = "Bash";
		spellSet[1] = "Stone Skin";
		
		block = false;
		stoneSkinCount = 0;
	}
	
	public void tick() {
		super.tick();
		if(moveCooldown[2] > 0) {
			moveCooldown[2]--;
		}
		else if(mp < 15) isMoveOnCooldown[2] = true;
		else isMoveOnCooldown[2] = false;
		
		if(mp < 25) isSpellOnCooldown[0] = true;
		else isSpellOnCooldown[0] = false;
		
		if(mp < 25) isSpellOnCooldown[1] = true;
		else isSpellOnCooldown[1] = false;
		
		if(stoneSkinCount > 0) {
			stoneSkinCount--;
			armor = baseArmor + 3;
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
		
		
		if(attacking){
			if(k == KeyEvent.VK_UP) BaseLevel.changeMenuSelect("UP");
			if(k == KeyEvent.VK_DOWN) BaseLevel.changeMenuSelect("DOWN");
			if(k == KeyEvent.VK_RIGHT) {
				if(BaseLevel.getMenuOption().equals("Attack")) {
					time = 0;
					attack(attack);
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Rage Attack")) {
					baseMenu = false;
					spellMenu = true;
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Block") && !isMoveOnCooldown[2]) {
					time = 0;
					block();
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Bash") && !isSpellOnCooldown[0]) {
					time = 0;
					spellMenu = false;
					bash();
					BaseLevel.changeMenuSelect("RIGHT");
				}
				else if(BaseLevel.getMenuOption().equals("Bash") && !isSpellOnCooldown[1]) {
					time = 0;
					spellMenu = false;
					stoneSkin();
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
				}
			BaseLevel.changeMenuSelect("LEFT");
			}
		}
	}
	
	public void takeDamage(int damage) {
		mp += damage * 3 / 5;
		if(mp > maxMp) mp = maxMp;
		if(block) block = false;
		else {
			if(armor >= damage) {
				hp -= 1;
			}
			else hp -= damage - armor;
			if(hp <= 0) {
				hp = 0;
			}
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
	}
	
	public void stoneSkin() {
		mp -= 25;
		attacking = false;
		queued = false;
		stoneSkinCount = 900;
		BaseLevel.dequeueTurn();
	}
}


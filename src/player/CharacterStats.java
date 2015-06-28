package player;


public class CharacterStats {
	
	public String name;
	public String className;
	public int hp;
	public int maxHp;
	public int mp;
	public int maxMp;
	public int speed;
	public int experience;
	public int level;
	public int attack;
	public int armor;
	
	public CharacterStats(String name, String className) {
		
		this.name = name;
		this.className = className;
		if(className.equals("Warrior")) {
			maxHp = 1000;
			maxMp = 0;
			speed = 30;
			attack = 20;
			armor = 8;
		}
		else if(className.equals("White Mage")) {
			maxHp = 500;
			maxMp = 200;
			speed = 18;
			attack = 5;
			armor = 2;
		}
		else if(className.equals("Black Mage")) {
			maxHp = 500;
			maxMp = 200;
			speed = 20;
			attack = 8;
			armor = 2;
		}
		else if(className.equals("Archer")) {
			maxHp = 700;
			maxMp = 0;
			speed = 45;
			attack = 7;
			armor = 4;
		}
		else if(className.equals("Spearman")) {
			maxHp = 900;
			maxMp = 0;
			speed = 30;
			armor = 6;
		}
		else if(className.equals("Monk")) {
			maxHp = 800;
			maxMp = 0;
			speed = 35;
			attack = 8;
			armor = 5;
		}
		
		hp = maxHp;
		mp = maxMp;
		experience = 0;
		level = 1;
		
	}
	
	public void levelUp() {
		level++;
		maxHp *= 1.2;
		maxMp *= 1.2;
		hp = maxHp;
		mp = maxMp;
		speed++;
		armor++;
		attack++;
		experience = 0;
	}

}

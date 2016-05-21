package game.gamestate;

import javax.swing.ImageIcon;

import mobs.Scorpion;
import mobs.Snake;
import physics.Sounds;
import display.WinScreen;

public class DesertBattle extends BaseLevel{
	
	public DesertBattle(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		super.init();
		background = new ImageIcon("Sprites/DesertBackground.png").getImage();
		bgm = new Sounds("Music/desertbattletheme.wav");
		bgm.loop();
		
		for(int i = 0; i < 3; i++) {
			if(Math.random() * 10 > 5) mob[i] = new Scorpion(i, this);
			else mob[i] = new Snake(i, this);
		}
		
		xp = mob[0].xp + mob[1].xp + mob[2].xp;
		currencyWon = (int)(Math.random() * 16 + 10);
		itemsWonChance = (int)(Math.random() * 20);
		if(itemsWonChance < 1) numItemsWon = 1;
		else if(itemsWonChance < 4) numItemsWon = 2;
		else if(itemsWonChance < 8) numItemsWon = 3;
		else if(itemsWonChance < 12) numItemsWon = 4;
		else if(itemsWonChance < 16) numItemsWon = 5;
		else numItemsWon = 6;
		itemsWon = new String[numItemsWon];
		while(numItemsWon > 0) {
			if((int)(Math.random() * 3) == 2)  itemsWon[numItemsWon-1] = mob[(int)(Math.random() * 3)].getRareItem();
			else itemsWon[numItemsWon-1] = mob[(int)(Math.random() * 3)].getItem();
			numItemsWon--;
		}
		wScreen = new WinScreen(xp, currencyWon, itemsWon);
		
		for(int i = 0; i < 3; i++) {
			chars[i].init();
			mob[i].init();
		}
	}

}

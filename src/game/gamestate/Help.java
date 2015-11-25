package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import physics.Sounds;

import com.sun.glass.events.KeyEvent;

import display.BufferedImageLoader;

public class Help extends GameState{
	
	int ht = GamePanel.HEIGHT;
	int wd = GamePanel.WIDTH;
	BufferedImageLoader loader = new BufferedImageLoader();
	String[] options = new String[]{"Open World", "Classes", "Fighting"};
	int currentSelection;
	int helpScreen;
	int lineSpace = ht / 15;
	Image abilities = new ImageIcon("ScreenShots/AbilitiesShot.png").getImage();
	Image archer = new ImageIcon("ScreenShots/ArcherShot.png").getImage();
	Image attackBar = new ImageIcon("ScreenShots/AttackBarShot.png").getImage();
	Image battleScreen = new ImageIcon("ScreenShots/BattleScreenShot.png").getImage();
	Image blackMage = new ImageIcon("ScreenShots/BlackMageShot.png").getImage();
	Image bunnyAttack = new ImageIcon("ScreenShots/BunnyAttackShot.png").getImage();
	Image characterAttacking = new ImageIcon("ScreenShots/CharacterAttackingShot.png").getImage();
	Image items = new ImageIcon("ScreenShots/ItemsShot.png").getImage();
	Image monk = new ImageIcon("ScreenShots/MonkShot.png").getImage();
	Image moving = new ImageIcon("ScreenShots/MovingShot.png").getImage();
	Image pause = new ImageIcon("ScreenShots/PauseShot.png").getImage();
	Image pigAttack = new ImageIcon("ScreenShots/PigAttackShot.png").getImage();
	Image save = new ImageIcon("ScreenShots/SaveShot.png").getImage();
	Image selectedCharacter = new ImageIcon("ScreenShots/SelectedCharacterShot.png").getImage();
	Image sideBar = new ImageIcon("ScreenShots/SideBarShot.png").getImage();
	Image spearman = new ImageIcon("ScreenShots/SpearmanShot.png").getImage();
	Image warrior = new ImageIcon("ScreenShots/WarriorShot.png").getImage();
	Image whiteMage = new ImageIcon("ScreenShots/WhiteMageShot.png").getImage();
	Sounds menuSelectSound = new Sounds("Sounds/menu select.wav");
	Sounds menuBackSound = new Sounds("Sounds/menu back.wav");
	
	
	public Help(GameStateManager gsm) {
		super(gsm);
	}

	
	public void init() {
		currentSelection = 0;
		helpScreen = 0;
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("pixelmix.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		
	}

	
	public void tick() {
		
		
	}

	
	public void draw(Graphics g) {
		
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, wd, ht);
		//Image help = new ImageIcon("Sprites/help.jpg").getImage();
		//g.drawImage(help, GamePanel.WIDTH / 4, GamePanel.HEIGHT / 10, GamePanel.WIDTH / 2, GamePanel.HEIGHT * 4 / 5, null);
		g.setFont(new Font("pixelmix", Font.PLAIN, ht / 7));
		if(helpScreen == 0) {
			for(int i = 0; i < options.length; i++) {
				if(i == currentSelection) g.setColor(Color.GREEN);
				else g.setColor(Color.WHITE);
				g.drawString(options[i], wd / 10, ht / 5 + (i * ht / 3));
			}
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("pixelmix", Font.PLAIN, ht / 30));
		if(helpScreen == 1) {
			g.drawImage(moving, wd / 6, ht / 20, wd * 2 / 3, ht * 2 / 3, null);
			g.drawString("While in the open world, use WASD to move around and randomly", wd / 30, ht * 4 / 5);
			g.drawString("encounter battles. Use Escape to pause.", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("1/5", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 2) {
			g.drawImage(pause, wd / 6, ht / 20, wd * 2 / 3, ht * 2 / 3, null);
			g.drawString("From the pause menu, you can use the arrows and enter to navigate", wd / 30, ht * 4 / 5);
			g.drawString("the menu on the right. Character stats are shown on the left.", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("2/5", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 3) {
			g.drawImage(abilities, wd / 6, ht / 20, wd * 2 / 3, ht * 2 / 3, null);
			g.drawString("You can see the different abilities each characters has in the ability", wd / 30, ht * 4 / 5);
			g.drawString("menu. There are details about the current ability in the bottom right and", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("if it is white, it is usable out of battle.", wd / 30, ht * 4 / 5 + (lineSpace * 2));
			g.drawString("3/5", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 4) {
			g.drawImage(items, wd / 6, ht / 20, wd * 2 / 3, ht * 2 / 3, null);
			g.drawString("In the inventory menu, you can see all of the items in your inventory.", wd / 30, ht * 4 / 5);
			g.drawString("Like items, if it is white it is either usable or equipable. However, ", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("certain equipment can only be worn by certain classes.", wd / 30, ht * 4 / 5 + (lineSpace * 2));
			g.drawString("4/5", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 5) {
			g.drawImage(save, wd / 6, ht / 20, wd * 2 / 3, ht * 2 / 3, null);
			g.drawString("In the main menu, you can select \"save\" which will save your current", wd / 30, ht * 4 / 5);
			g.drawString("game. You can later load your game any time from the menu menu. Be", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("careful, there is only one save slot so your last save will be overwritten.", wd / 30, ht * 4 / 5 + (lineSpace) * 2);
			g.drawString("5/5", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 6) {
			g.drawImage(warrior, wd * 2 / 5, ht / 10, wd / 5, ht / 3, null);
			g.drawString("This is the Warrior. He is the class that can take more damage than any", wd / 30, ht * 4 / 5 + (lineSpace) * -1);
			g.drawString("other. He is a melee class and his energy source is rage which builds", wd / 30, ht * 4 / 5);
			g.drawString("whenever damaged. His passive ability blocks his allies behind him when", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("an attack damages an entire lane. He wears suits and uses swords.", wd / 30, ht * 4 / 5 + (lineSpace) * 2	);
			g.drawString("1/6", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 7) {
			g.drawImage(blackMage, wd * 2 / 5, ht / 10, wd / 5, ht / 3, null);
			g.drawString("This is the Black Mage. He is ranged and cannot take very much damage but", wd / 30, ht * 4 / 5 + (lineSpace * -1));
			g.drawString("deals a lot of damage. His passive ability makes him faster and deal more", wd / 30, ht * 4 / 5);
			g.drawString("damage from the back row. His energy source is mana which is recovered by", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("meditating or from \"MP potions\". He wears cloaks and uses staves.", wd / 30, ht * 4 / 5 + (lineSpace) * 2);
			g.drawString("2/6", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 8) {
			g.drawImage(whiteMage, wd * 2 / 5, ht / 10, wd / 5, ht / 3, null);
			g.drawString("This is the White Mage. She is ranged and cannot take very much damage", wd / 30, ht * 4 / 5 + (lineSpace * -1));
			g.drawString("and is not very strong. However, she has a wide variety of healing spells", wd / 30, ht * 4 / 5);
			g.drawString("that she can cast from behind an ally. She also uses mana like the Black", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("Mage. Her passive ability also makes her faster from the back row and her", wd / 30, ht * 4 / 5 + (lineSpace * 2));
			g.drawString("attacks heal teammates. She wears cloaks and uses staves.", wd / 30, ht * 4 / 5 + (lineSpace * 11 / 4));
			g.drawString("3/6", wd * 9 / 10, ht * 29 / 30);
		}	
		else if(helpScreen == 9) {
			g.drawImage(archer, wd * 2 / 5, ht / 10, wd / 5, ht / 3, null);
			g.drawString("This is the Archer.  He is ranged and can take some damage and he deals", wd / 30, ht * 4 / 5 + (lineSpace * -1));
			g.drawString("damage very quickly. His passive ability allows him a chance to critical", wd / 30, ht * 4 / 5);
			g.drawString("strike an enemy once in a while. His energy source is different kinds of", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("arrows you can find from defeating enemies. He wears vests and uses bows.", wd / 30, ht * 4 / 5 + (lineSpace * 2));
			g.drawString("4/6", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 10) {
			g.drawImage(spearman, wd * 2 / 5, ht / 10, wd / 5, ht / 3, null);
			g.drawString("This is the Spearman. He is a melee class but his passive allows him to", wd / 30, ht * 4 / 5 + (lineSpace * -1));
			g.drawString("attack from the second row. He is almost as durable as the Warrior and", wd / 30, ht * 4 / 5);
			g.drawString("his energy source is energy which slowly builds up over time. He wears", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("armor and uses spears.", wd / 30, ht * 4 / 5 + (lineSpace) * 2);
			g.drawString("5/6", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 11) {
			g.drawImage(monk, wd * 2 / 5, ht / 10, wd / 5, ht / 3, null);
			g.drawString("This is the monk. He is a melee class with the highest attack damage. His", wd / 30, ht * 4 / 5 + (lineSpace * -1));
			g.drawString("passive ability makes him very strong but he cannot equip any weapons. His", wd / 30, ht * 4 / 5);
			g.drawString("energy source is charms which are only restored when he kills an enemy. He", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("wears robes and does not use weapons.", wd / 30, ht * 4 / 5 + (lineSpace * 2));
			g.drawString("6/6", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 12) {
			g.drawImage(battleScreen, wd / 6, ht / 25, wd * 2 / 3, ht * 2 / 3, null);
			g.drawString("This is the main battle screen. You can move the selected character which", wd / 30, ht * 4 / 5 - lineSpace);
			g.drawString("is highlighted in yellow. Characters are moved with WASD and the \"Q\"", wd / 30, ht * 4 / 5);
			g.drawString("and \"E\" keys are used to select characters. Melee characters can only attack the enemy", wd / 30, ht * 4 / 5 + (lineSpace ));
			g.drawString("if they are in the front row and moving helps avoid incoming attacks.", wd / 30, ht * 4 / 5 + (lineSpace * 2));
			g.drawString("1/4", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 13) {
			g.drawImage(sideBar, wd * 2 / 5, ht / 10, wd / 10, ht / 2, null);
			g.drawString("The side bar displays important information for each character. The first", wd / 30, ht * 4 / 5 - lineSpace);
			g.drawString("bar shows how much health that character has. The second bar is their", wd / 30, ht * 4 / 5);
			g.drawString("energy source. The third is the time bar, when it fills up, that character", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("can attack. The yellow outline corresponds to the selected character.", wd / 30, ht * 4 / 5 + (lineSpace * 2));
			g.drawString("2/4", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 14) {
			g.drawImage(attackBar, wd * 2 / 5, ht / 10, wd / 10, ht / 2, null);
			g.drawString("The attack bar shows all the attacks a character can use. Use the up and", wd / 30, ht * 4 / 5 + (lineSpace * -1));
			g.drawString("down arrows to navigate the menu, the left arrow to skip that turn, and", wd / 30, ht * 4 / 5);
			g.drawString("the right arrow to select. Information is displayed on top of the current", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("ability and the attack order is shown above that", wd / 30, ht * 4 / 5 + (lineSpace * 2));
			g.drawString("3/4", wd * 9 / 10, ht * 29 / 30);
		}
		else if(helpScreen == 15) {
			g.drawImage(bunnyAttack, wd * 1 / 5, ht / 10, wd / 5, ht / 2, null);
			g.drawImage(pigAttack, wd * 3 / 5, ht / 10, wd / 5, ht / 2, null);
			g.drawString("Incoming attacks are shown by red squares on the battle grid. You have", wd / 30, ht * 4 / 5 - lineSpace);
			g.drawString("about 2 seconds to avoid an incoming attack. Different enemies have ", wd / 30, ht * 4 / 5);
			g.drawString("different attack patterns. For example, the bunny attacks a single square", wd / 30, ht * 4 / 5 + (lineSpace));
			g.drawString("while the pig attacks an entire lane.", wd / 30, ht * 4 / 5 + (lineSpace * 2));
			g.drawString("4/4", wd * 9 / 10, ht * 29 / 30);
		}
		
		
	}

	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ESCAPE) {
			menuBackSound.play();
			gsm.states.pop();
		}
		if(helpScreen == 0) {
			if(k == KeyEvent.VK_DOWN){
				currentSelection++;
				if(currentSelection >= options.length){
					currentSelection = 0;
				}
			}
			else if(k == KeyEvent.VK_UP){
				currentSelection--;
				if(currentSelection < 0){
					currentSelection = options.length - 1;
				}
			}
			else if(k == KeyEvent.VK_ENTER) {
				menuSelectSound.play();
				if(currentSelection == 0) helpScreen = 1;
				else if(currentSelection == 1) helpScreen = 6;
				else if(currentSelection == 2) helpScreen = 12;
			}
		}
		else if(k == KeyEvent.VK_LEFT) {
			if(helpScreen == 1 || helpScreen == 6 || helpScreen == 12) helpScreen = 0;
			else helpScreen--;
		}
		else if(k == KeyEvent.VK_RIGHT) {
			if(helpScreen == 5 ||helpScreen == 11 || helpScreen == 15) helpScreen = 0;
			else helpScreen++;
		}
		
	}

	
	public void keyReleased(int k) {
		
		
	}
	
	

}

package npc;

import game.gamestate.BaseWorld;
import game.gamestate.World;
import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import physics.Sounds;
import player.Equipment;
import player.Item;

public class Vendor extends BaseNPC {
	
	boolean ignoreTalk, buy, sell, wepBuy, armBuy, itmBuy, confirm;
	String[] weapons, armors, items, playerItems;
	int[] playerItemAmts;
	int storeCount, confirmCount, gameWd, gameHt, border;
	Image vendorBG, cBox, arrow;
	Sounds menuSelectSound = new Sounds("Sounds/menu select.wav");
	Sounds menuBackSound = new Sounds("Sounds/menu back.wav");

	public Vendor(int x, int y, int edgeX, int edgeY, int blockSize,
			BaseWorld world, int stage, String[] weapons, String[] armors, String[] items) {
		super(x, y, edgeX, edgeY, blockSize, world, stage);
		sprites[0] = new ImageIcon("Sprites/FirstNPCup.png").getImage();
		sprites[1] = new ImageIcon("Sprites/FirstNPCright.png").getImage();
		sprites[2] = new ImageIcon("Sprites/FirstNPCdown.png").getImage();
		sprites[3] = new ImageIcon("Sprites/FirstNPCleft.png").getImage();
		ignoreTalk = false;
		buy = false;
		sell = false;
		wepBuy = false;
		armBuy = false;
		itmBuy = false;
		confirm = false;
		this.weapons = weapons;//new String[]{"Wood Staff", "Wood Sword"};
		this.armors = armors;//new String[]{"Cloth Suit", "Cloth Robe"};
		this.items = items;//new String[]{"HP Potion", "MP Potion"};
		storeCount = 0;
		confirmCount = 0;
		gameWd = GamePanel.WIDTH;
		gameHt = GamePanel.HEIGHT;
		border = gameHt / 62;
		world.world[y][x].hasPerson = true;
		vendorBG = new ImageIcon("Sprites/VendorScreen.png").getImage();
		cBox = new ImageIcon("Sprites/ConfirmationBox.png").getImage();
		arrow = new ImageIcon("Sprites/SideArrow.png").getImage();
		dialogue = new String[]{"How can I help you?", ""};
	}

	public void tick() {}
	
	public boolean talk(int d) {
		if(!talking) {
			talking = true;
		}
		else if(talking) {
			selling = true;
			talking = false;
			ignoreTalk = false;
			playerItems = World.inv.getItems();
			playerItemAmts = World.inv.getItemAmounts();
			World.buying = true;
			return false;
		}
		return true;
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		if(selling) {
			g.drawImage(vendorBG, 0, 0, gameWd, gameHt, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("pixelmix", Font.PLAIN, gameHt / 45));
			g.drawString("Money: " + World.inv.currency, gameWd * 4 / 5 + border * 8, border * 29);
			if(!buy && !sell) {
				g.drawImage(arrow, gameWd * 4 / 5 + 2 * border, border * 7 / 2 + (border * 4 * storeCount), border * 4, border * 2, null);
				g.drawString("Buy", gameWd * 4 / 5 + (border * 8), border * 5);
				g.drawString("Sell", gameWd * 4 / 5 + (border * 8), border * 9);
				g.drawString("Exit", gameWd * 4 / 5 + (border * 8), border * 13);
			}
			else if(wepBuy) {
				g.drawImage(arrow, border * 2, border * 7 / 2 + (border * 4 * storeCount), border * 4, border * 2, null);
				for(int i = 0; i < weapons.length; i++) {
					if(World.inv.currency >= Item.getBuyPrice(weapons[i])) g.setColor(Color.WHITE);
					else g.setColor(Color.GRAY);
					g.drawString(weapons[i], border * 9, border * 5 + (border * 4 * i));
				}
				g.setColor(Color.WHITE);
				g.drawString(weapons[storeCount], gameWd * 5 / 6, gameHt / 2 + border * 5);
				g.drawString("Price: " + Item.getBuyPrice(weapons[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 9);
				g.drawString("Class: " + Equipment.getClassType(weapons[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 13);
				g.drawString("Attack: " + Equipment.getAttack(weapons[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 17);
				g.drawString("Spellpower: " + Equipment.getSpellPower(weapons[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 21);
			}
			else if(armBuy) {
				g.drawImage(arrow, border * 2, border * 7 / 2 + (border * 4 * storeCount), border * 4, border * 2, null);
				for(int i = 0; i < armors.length; i++) {
					if(World.inv.currency >= Item.getBuyPrice(armors[i])) g.setColor(Color.WHITE);
					else g.setColor(Color.GRAY);
					g.drawString(armors[i], border * 9, border * 5 + (border * 4 * i));
				}
				g.setColor(Color.WHITE);
				g.drawString(armors[storeCount], gameWd * 5 / 6, gameHt / 2 + border * 5);
				g.drawString("Price: " + Item.getBuyPrice(armors[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 9);
				g.drawString("Class: " + Equipment.getClassType(armors[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 13);
				g.drawString("Defense: " + Equipment.getDefense(armors[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 17);
			}
			else if(itmBuy) {
				g.drawImage(arrow, border * 2, border * 7 / 2 + (border * 4 * storeCount), border * 4, border * 2, null);
				for(int i = 0; i < items.length; i++) {
					if(World.inv.currency >= Item.getBuyPrice(items[i])) g.setColor(Color.WHITE);
					else g.setColor(Color.GRAY);
					g.drawString(items[i], border * 9, border * 5 + (border * 4 * i));
				}
				g.setColor(Color.WHITE);
				g.drawString(items[storeCount], gameWd * 5 / 6, gameHt / 2 + border * 5);
				g.drawString("Price: " + Item.getBuyPrice(items[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 9);
				for(int i = 0; i < Item.getDescription(items[storeCount]).length; i++) 
					g.drawString(Item.getDescription(items[storeCount])[i], gameWd * 5 / 6, gameHt / 2 + border * 13 + (border * 4 * i));
			}
			else if(sell) {
				g.drawImage(arrow, border * 2, border * 7 / 2 + (border * 4 * storeCount), border * 4, border * 2, null);
				for(int i = 0; i < playerItems.length; i++) {
					g.drawString(playerItems[i] + " x " + playerItemAmts[i], border * 9, border * 5 + (border * 4 * i));
				}
				if(playerItems.length > 0) {
					g.drawString(playerItems[storeCount], gameWd * 5 / 6, gameHt / 2 + border * 5);
					g.drawString("Price: " + Item.getSellPrice(playerItems[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 9);
					if(Item.isWeapon(playerItems[storeCount])) {
						g.drawString("Class: " + Equipment.getClassType(playerItems[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 13);
						g.drawString("Attack: " + Equipment.getAttack(playerItems[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 17);
						g.drawString("Spellpower: " + Equipment.getSpellPower(playerItems[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 21);
					}
					else if(Item.isArmor(playerItems[storeCount])) {
						g.drawString("Class: " + Equipment.getClassType(playerItems[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 13);
						g.drawString("Defense: " + Equipment.getDefense(playerItems[storeCount]), gameWd * 5 / 6, gameHt / 2 + border * 17);
					}
					else {
						for(int i = 0; i < Item.getDescription(playerItems[storeCount]).length; i++) 
							g.drawString(Item.getDescription(playerItems[storeCount])[i], gameWd * 5 / 6, gameHt / 2 + border * 13 + (border * 4 * i));
					}
				}
			}
			else if(buy) {
				g.drawImage(arrow, gameWd * 4 / 5 + 2 * border, border * 7 / 2 + (border * 4 * storeCount), border * 4, border * 2, null);
				g.drawString("Weapons", gameWd * 4 / 5 + (border * 8), border * 5);
				g.drawString("Armor", gameWd * 4 / 5 + (border * 8), border * 9);
				g.drawString("Items", gameWd * 4 / 5 + (border * 8), border * 13);
			}
			if(confirm) {
				g.drawImage(cBox, gameWd / 2 - (gameHt / 6), gameHt / 2 - (gameHt / 8), gameHt / 4, gameHt / 6, null);
				g.drawImage(arrow, gameWd / 2 - (gameHt / 6) + border * 2, gameHt / 2 - (gameHt / 8) + border * 9 / 2 + (confirmCount * border * 3), border * 4, border * 2, null);
				g.drawString("Confirm?", gameWd / 2 - gameHt / 6 + border * 5, gameHt / 2 - (gameHt / 8) + border * 3);
				g.drawString("Yes", gameWd / 2 - gameHt / 6 + border * 7, gameHt / 2 - (gameHt / 8) + border * 6);
				g.drawString("No", gameWd / 2 - gameHt / 6 + border * 15 / 2, gameHt / 2 - (gameHt / 8) + border * 9);
			}
			//display details on bottom
		}
	}
	
	public void keyPressed(int k) {
		if(!buy && !sell) {
			if(k == KeyEvent.VK_UP) {
				if(storeCount == 0) storeCount = 2;
				else storeCount--;
			}
			else if(k == KeyEvent.VK_DOWN) {
				if(storeCount == 2) storeCount = 0;
				else storeCount++;
			}
			else if(k == KeyEvent.VK_ENTER) {
				if(!ignoreTalk) ignoreTalk = true;
				else if(storeCount == 0) buy = true;
				else if(storeCount == 1) {
					playerItems = World.inv.getItems();
					playerItemAmts = World.inv.getItemAmounts();
					sell = true;
					storeCount = 0;
				}
				else {
					selling = false;
					storeCount = 0;
					World.buying = false;
				}
				menuSelectSound.play();
			}
			else if(k == KeyEvent.VK_ESCAPE) {
				selling = false;
				storeCount = 0;
				World.buying = false;
			}
		}
		else if(confirm) {
			if(k == KeyEvent.VK_UP ||k == KeyEvent.VK_DOWN) {
				if(confirmCount == 0) confirmCount = 1;
				else confirmCount = 0;
			}
			else if(k == KeyEvent.VK_ENTER) {
				if(confirmCount == 0) {
					if(wepBuy) {
						World.inv.currency -= Item.getBuyPrice(weapons[storeCount]);
						World.inv.addItem(weapons[storeCount]);
					}
					else if(armBuy) {
						World.inv.currency -= Item.getBuyPrice(armors[storeCount]);
						World.inv.addItem(armors[storeCount]);
					}
					else if(itmBuy) {
						World.inv.currency -= Item.getBuyPrice(items[storeCount]);
						World.inv.addItem(items[storeCount]);
					}
					else if(sell) {
						World.inv.currency += Item.getSellPrice(playerItems[storeCount]);
						World.inv.removeItem(playerItems[storeCount]);
						storeCount = 0;
						playerItems = World.inv.getItems();
						playerItemAmts = World.inv.getItemAmounts();
					}
					menuSelectSound.play();
				}
				confirm = false;
				confirmCount = 0;
				if(confirmCount == 1) menuBackSound.play();
			}
			else if(k == KeyEvent.VK_ESCAPE) {
				confirm = false;
				confirmCount = 0;
				menuBackSound.play();
			}
		}
		else if(wepBuy) {
			if(k == KeyEvent.VK_UP) {
				if(storeCount == 0) storeCount = weapons.length - 1;
				else storeCount--;
			}
			else if(k == KeyEvent.VK_DOWN) {
				if(storeCount == weapons.length - 1) storeCount = 0;
				else storeCount++;
			}
			else if(k == KeyEvent.VK_ENTER) {
				if(World.inv.currency >= Item.getBuyPrice(weapons[storeCount])) {
					confirm = true;
					menuSelectSound.play();
				}
			}
			else if(k == KeyEvent.VK_ESCAPE) {
				storeCount = 0;
				wepBuy = false;
				buy = true;
				menuBackSound.play();
			}
		}
		else if(armBuy) {
			if(k == KeyEvent.VK_UP) {
				if(storeCount == 0) storeCount = armors.length - 1;
				else storeCount--;
			}
			else if(k == KeyEvent.VK_DOWN) {
				if(storeCount == armors.length - 1) storeCount = 0;
				else storeCount++;
			}
			else if(k == KeyEvent.VK_ENTER) {
				if(World.inv.currency >= Item.getBuyPrice(armors[storeCount])){
					confirm = true;
					menuSelectSound.play();
				}
			}
			else if(k == KeyEvent.VK_ESCAPE) {
				storeCount = 0;
				armBuy = false;
				buy = true;
				menuBackSound.play();
			}
		}
		else if(itmBuy) {
			if(k == KeyEvent.VK_UP) {
				if(storeCount == 0) storeCount = items.length - 1;
				else storeCount--;
			}
			else if(k == KeyEvent.VK_DOWN) {
				if(storeCount == items.length - 1) storeCount = 0;
				else storeCount++;
			}
			else if(k == KeyEvent.VK_ENTER) {
				if(World.inv.currency >= Item.getBuyPrice(items[storeCount])) {
					confirm = true;
					menuSelectSound.play();
				}
			}
			else if(k == KeyEvent.VK_ESCAPE) {
				storeCount = 0;
				itmBuy = false;
				buy = true;
				menuBackSound.play();
			}
		}
		else if(buy) {
			if(k == KeyEvent.VK_UP) {
				if(storeCount == 0) storeCount = 2;
				else storeCount--;
			}
			else if(k == KeyEvent.VK_DOWN) {
				if(storeCount == 2) storeCount = 0;
				else storeCount++;
			}
			else if(k == KeyEvent.VK_ENTER) {
				if(storeCount == 0) wepBuy = true;
				else if(storeCount == 1) {
					storeCount = 0;
					armBuy = true;
				}
				else if(storeCount == 2) {
					storeCount = 0;
					itmBuy = true;
				}
				menuSelectSound.play();
			}
			else if(k == KeyEvent.VK_ESCAPE) {
				storeCount = 0;
				buy = false;
				menuBackSound.play();
			}
		}
		else if(sell) {
			if(k == KeyEvent.VK_UP) {
				if(storeCount == 0 && playerItems.length > 0) storeCount = playerItems.length - 1;
				else if(playerItems.length > 0) storeCount--;
			}
			else if(k == KeyEvent.VK_DOWN) {
				if(storeCount >= playerItems.length - 1) storeCount = 0;
				else if(playerItems.length > 0) storeCount++;
			}
			else if(k == KeyEvent.VK_ENTER) {
				if(playerItems.length > 0) {
					confirm = true;
					menuSelectSound.play();
				}
			}
			else if(k == KeyEvent.VK_ESCAPE) {
				storeCount = 0;
				sell = false;
				menuBackSound.play();
			}
		}
	}
	
}

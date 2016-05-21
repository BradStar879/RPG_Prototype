package npc;

import game.gamestate.BaseWorld;
import game.gamestate.World;

import javax.swing.ImageIcon;

public class FirstNPC extends BaseNPC{

	
	
	public FirstNPC(int x, int y, int edgeX, int edgeY, int blockSize, BaseWorld world, int stage) {
		super(x, y, edgeX, edgeY, blockSize, world, stage);
		sprites[0] = new ImageIcon("Sprites/FirstNPCup.png").getImage();
		sprites[1] = new ImageIcon("Sprites/FirstNPCright.png").getImage();
		sprites[2] = new ImageIcon("Sprites/FirstNPCdown.png").getImage();
		sprites[3] = new ImageIcon("Sprites/FirstNPCleft.png").getImage();
		walkPath = new int[]{2, 2, 3, 3, 0, 1, 0, 1};
		questMobs = new String[][]{{"Pig"}};
		questMobAmts = new int[][]{{5}};
		questItems = new String[][]{{"Fire Arrow"}};
		questItmAmts = new int[][]{{3}};
		dialogue = new String[]{"Hi I have a quest for you.", "Please go kill 5 pigs.", 					//0
				"You still have to kill more pigs.", "",													//2
				"Wow you that was really fast!", "Here's 100 gold for your trouble",						//4
				"Would you mind bringing me three fire arrows as", "well? I'll make it worth your while.",	//6
				"Still need more fire arrows.", "",															//8
				"Oh thanks, I don't know what I'd do without you.", "",										//10
				"Thanks for all your help kiddo.", "I don't need any more help at the moment."};			//12
		
	}
	
	public boolean talk(int x) {
		direction = (x+2) % 4;
		if(!walking && !talking && (stage == 0 || stage == 6 || stage == 12)) {
			talking = true;
			return true;
		}
		else if(!walking && !talking && stage == 2) {
			if(world.isQuestFinished("Pig Slayer")) {
				stage = 4;
				World.player.npcStages[0] = 4;
				World.inv.addCurrency(100);
				World.removeQuest("Pig Slayer");
			}
			talking = true;
			return true;
		}
		else if(!walking && !talking && stage == 8) {
			if(world.isQuestFinished("Fire Arrow Collector")) {
				stage = 10;
				World.player.npcStages[0] = 10;
				for(int i = 0; i < 3; i++) {
					World.inv.removeItem("Fire Arrow");
					World.inv.addItem("HP Potion");
					World.inv.addItem("MP Potion");
				}
				World.removeQuest("Fire Arrow Collector");
			}
			talking = true;
			return true;
		}
		else if(talking && stage == 0) {
			world.addKillQuest("Pig Slayer", questMobs[0], questMobAmts[0]);
			talking = false;
			stage = 2;
			World.player.npcStages[0] = 2;
		}
		else if(talking && (stage == 2 || stage == 8|| stage == 12)) {
			talking = false;
		}
		else if(talking && stage == 4) {
			stage = 6;
			World.player.npcStages[0] = 6;
			talking = false;
		}
		else if(talking && stage == 6) {
			world.addCollectQuest("Fire Arrow Collector", questItems[0], questItmAmts[0]);
			talking = false;
			stage = 8;
			World.player.npcStages[0] = 8;
			
		}
		else if(talking && stage == 10) {
			talking = false;
			stage = 12;
			World.player.npcStages[0] = 12;
		}
		return false;
	}
}

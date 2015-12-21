package npc;

import game.gamestate.BaseWorld;

import javax.swing.ImageIcon;

public class FirstNPC extends BaseNPC{

	String[] questMobs;
	int[] questMobAmts;
	
	public FirstNPC(int x, int y, int edgeX, int edgeY, int blockSize, BaseWorld world, int stage) {
		super(x, y, edgeX, edgeY, blockSize, world, stage);
		sprites[0] = new ImageIcon("Sprites/FirstNPCup.png").getImage();
		sprites[1] = new ImageIcon("Sprites/FirstNPCright.png").getImage();
		sprites[2] = new ImageIcon("Sprites/FirstNPCdown.png").getImage();
		sprites[3] = new ImageIcon("Sprites/FirstNPCleft.png").getImage();
		walkPath = new int[]{2, 2, 3, 3, 0, 1, 0, 1};
		questMobs = new String[]{"Pig"};
		questMobAmts = new int[]{5};
		dialogue = new String[]{"Hi I have a quest for you.", "Please go kill 5 pigs.", 
				"You still have to kill more pigs.", "",
				"Wow you that was really fast!", "Here's 100 gold for your trouble",
				"Thanks for helping with those nasty pigs.", ""};
		
	}
	
	public boolean talk(int x) {
		direction = (x+2) % 4;
		if(!walking && !talking && (stage == 0 || stage == 6)) {
			talking = true;
			return true;
		}
		else if(talking && stage == 0) {
			world.addKillQuest("Pig Slayer", questMobs, questMobAmts);
			talking = false;
			stage = 2;
			world.player.npcStages[0] = 2;
		}
		else if(!walking && !talking && stage == 2) {
			if(world.isQuestFinished("Pig Slayer")) {
				stage = 4;
				world.player.npcStages[0] = 4;
				world.inv.addCurrency(100);
				world.removeQuest("Pig Slayer");
			}
			talking = true;
			return true;
		}
		else if(talking && (stage == 2 || stage == 6)) {
			talking = false;
		}
		else if(talking && stage == 4) {
			stage = 6;
			world.player.npcStages[0] = 6;
			talking = false;
		}
		return false;
	}
}

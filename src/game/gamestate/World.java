package game.gamestate;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;

import npc.BaseNPC;
import npc.FirstNPC;
import physics.Sounds;
import player.Inventory;
import player.PlayerMover;
import player.Quests;
import world.BaseWorldBlock;

public class World extends BaseWorld{
	
	FirstNPC npc;

	public World(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		super.init();
		worldMap = true;
		edgeX = 11;
		edgeY = 6;
		blockSize = ht / 12;
		if(!loading) {
			shiftX = 8;
			shiftY = 8;
			player = new PlayerMover(shiftX + edgeX, shiftY + edgeY, 0);
			inv = new Inventory();
			quests = new Vector<Quests>();
		}
		else {
			shiftX = player.x - edgeX;
			shiftY = player.y - edgeY;
			loading = false;
		}
		battleProb = 8;
		worldDrawn = loader.loadImage("/World2.png");
		worldLength = 200;
		worldHeight = 200;
		world = new BaseWorldBlock[worldHeight][worldLength];
		speed = blockSize / 15;
		loadImageLevel(worldDrawn);
		npc = new FirstNPC(90, 90, edgeX, edgeY, blockSize, this, player.npcStages[0]);
		npcs = new BaseNPC[]{npc};
		map = new ImageIcon("Sprites/World2.png").getImage();
		if(world[player.y][player.x].name.equals("Grass")) {
			bgm = new Sounds("Music/plainstheme.wav");
			bgm.setName("Plains Theme");
		}
		else if(world[player.y][player.x].name.equals("Sand")) {
			bgm = new Sounds("Music/deserttheme.wav");
			bgm.setName("Desert Theme");
		}
		else if(world[player.y][player.x].name.equals("Forest")) {
			bgm = new Sounds("Music/foresttheme.wav");
			bgm.setName("Forest Theme");
		}
		bgm.loop();
	}
}

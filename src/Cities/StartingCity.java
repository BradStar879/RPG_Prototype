package Cities;

import game.gamestate.BaseWorld;
import game.gamestate.GameStateManager;
import game.gamestate.World;
import npc.BaseNPC;
import npc.FirstNPC;
import npc.Vendor;
import physics.Lists;
import physics.Sounds;
import world.BaseWorldBlock;

public class StartingCity extends BaseWorld{
	
	FirstNPC npc;
	Vendor vendor;

	public StartingCity(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		super.init();
		blockSize = ht / 7;
		worldDrawn = loader.loadImage("/StartingCity2.png");
		worldHeight = 80;
		worldLength = 80;
		battleProb = 0;
		world = new BaseWorldBlock[worldHeight][worldLength];
		player.x = 39;
		player.y = 74;
		speed = blockSize / 14;
		edgeX = 6;
		edgeY = 3;
		shiftX = player.x - edgeX;
		shiftY = player.y - edgeY;
		dir = 0;
		loadImageLevel(worldDrawn);
		npc = new FirstNPC(42, 59, edgeX, edgeY, blockSize, this, player.npcStages[0]);
		vendor = new Vendor(36, 62, edgeX, edgeY, blockSize, this, player.npcStages[1], Lists.woodWeps, Lists.clothArmors, Lists.pots);
		npcs = new BaseNPC[]{npc, vendor};
		bgm.stop();
		bgm = new Sounds("Music/Castletheme.wav");
		bgm.loop();
		
	}
	
	public void keyPressed(int k) {
		super.keyPressed(k);
		if(buying) {
			if(npcs[1].selling) npcs[1].keyPressed(k);
		}
	}
	
	public void leaveCity() {
		bgm.stop();
		player.x = 21;
		player.y = 25;
		player.steps = 0;
		loading = true;
		gsm.states.pop();
		gsm.states.push(new World(gsm));
	}
}

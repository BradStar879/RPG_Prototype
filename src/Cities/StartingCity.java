package Cities;

import game.gamestate.BaseWorld;
import game.gamestate.GameStateManager;
import game.gamestate.World;

import npc.BaseNPC;
import npc.FirstNPC;
import physics.Sounds;
import world.BaseWorldBlock;

public class StartingCity extends BaseWorld{
	
	FirstNPC npc;

	public StartingCity(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		super.init();
		blockSize = ht / 7;
		worldDrawn = loader.loadImage("/StartingCity.png");
		worldHeight = 50;
		worldLength = 50;
		battleProb = 0;
		world = new BaseWorldBlock[worldHeight][worldLength];
		player.x = 23;
		player.y = 44;
		speed = blockSize / 14;
		edgeX = 6;
		edgeY = 3;
		shiftX = player.x - edgeX;
		shiftY = player.y - edgeY;
		dir = 0;
		loadImageLevel(worldDrawn);
		npc = new FirstNPC(30, 30, edgeX, edgeY, blockSize, this, player.npcStages[0]);
		npcs = new BaseNPC[]{npc};
		bgm.stop();
		bgm = new Sounds("Music/Castletheme.wav");
		bgm.loop();

		
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

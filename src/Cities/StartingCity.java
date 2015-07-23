package Cities;

import game.gamestate.GameStateManager;
import game.gamestate.World;

import java.awt.Color;
import java.awt.Graphics;

import world.BaseWorldBlock;

public class StartingCity extends World{

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
		loadImageLevel(worldDrawn);
		player.x = 20;
		player.y = 20;
		shiftX = player.x - 5;
		shiftY = player.y - 3;
		speed = blockSize / 14;
		
	}
	
	public void draw(Graphics g) {
		
		for(int i = player.y - 5; i < player.y + 5; i++) {
			for(int j = player.x - 6; j < player.x + 9; j++) {
				world[i][j].draw(g);
			}
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(blockSize * 5 + blockSize / 4,  blockSize * 3 + blockSize / 4, blockSize / 2, blockSize / 2);
		
		pDisplay.draw(g);
	}
	
	

	
}

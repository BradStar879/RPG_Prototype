package display;

import java.awt.image.BufferedImage;

public class Texture {
	
	SpriteSheet bs, ps;
	private BufferedImage blockSheet = null;
	private BufferedImage playerSheet = null;
	
	public Texture() {
		
		BufferedImageLoader loader = new BufferedImageLoader();
		blockSheet = loader.loadImage("/blockSheet.png");
		playerSheet = loader.loadImage("/playerSheet.png");
		
		bs = new SpriteSheet(blockSheet);
		ps = new SpriteSheet(playerSheet);
		getTextures();
		
	}
	
	private void getTextures() {
		
	}

}

package world;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class TallGrassBlock extends BaseWorldBlock{
	
Image blockType = new ImageIcon("Sprites/TallGrass.png").getImage();
	
	public TallGrassBlock(int wd) {
		this.wd = wd;
		walkable = true;
		enter = false;
		name = "Tall Grass";
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd, wd, null);
	}

}

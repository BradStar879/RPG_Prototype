package world;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class ForestBlock extends BaseWorldBlock{

Image blockType = new ImageIcon("Sprites/ForestBlock.png").getImage();
	
	public ForestBlock(int wd) {
		this.wd = wd;
		walkable = true;
		enter = false;
		name = "Forest";
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd, wd, null);
	}
}

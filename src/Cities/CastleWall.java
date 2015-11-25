package Cities;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import world.BaseWorldBlock;

public class CastleWall extends BaseWorldBlock{
	
Image blockType = new ImageIcon("Sprites/CastleWall.png").getImage();
	
	public CastleWall(int wd) {
		this.wd = wd;
		walkable = false;
		enter = false;
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd, wd, null);
	}

}

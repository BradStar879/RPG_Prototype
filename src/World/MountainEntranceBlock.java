package world;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class MountainEntranceBlock extends BaseWorldBlock{

Image blockType = new ImageIcon("Sprites/MountainsEntrance.png").getImage();
	
	public MountainEntranceBlock(int wd) {
		this.wd = wd;
		walkable = true;
		enter = true;
		name = "Mountain Entrance";
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd, wd, null);
	}
}

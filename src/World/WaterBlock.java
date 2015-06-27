package World;

import java.awt.Color;
import java.awt.Graphics;

public class WaterBlock extends BaseWorldBlock{
	
	public WaterBlock() {
		walkable = false;
		col = Color.BLUE;
	}
	
	public void draw(Graphics g) {
		g.setColor(col);
		g.fillRect(x, y, wd, ht);
	}

}

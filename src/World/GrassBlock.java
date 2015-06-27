package World;

import java.awt.Color;
import java.awt.Graphics;

public class GrassBlock extends BaseWorldBlock{
	
	public GrassBlock() {
		walkable = true;
		col = Color.GREEN;
	}
	
	public void draw(Graphics g) {
		g.setColor(col);
		g.fillRect(x, y, wd, ht);
	}

}

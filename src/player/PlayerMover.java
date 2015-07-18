package player;

import java.awt.Color;
import java.io.Serializable;

public class PlayerMover implements Serializable {
	
	private static final long serialVersionUID = 52309813491851015L;
	Color col;
	public int x;
	public int y;
	public int steps;
	
	public PlayerMover(Color col, int x, int y, int steps) {
		this.col = col;
		this.x = x;
		this.y = y;
		this.steps = steps;
	}

}

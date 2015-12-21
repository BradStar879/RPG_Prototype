package player;

import java.io.Serializable;

public class PlayerMover implements Serializable {
	
	private static final long serialVersionUID = 52309813491851015L;
	public int x;
	public int y;
	public int steps;
	public int[] npcStages;
	
	public PlayerMover(int x, int y, int steps) {
		this.x = x;
		this.y = y;
		this.steps = steps;
		npcStages = new int[2];
		for(int i = 0; i < npcStages.length; i++) npcStages[i] = 0;
	}

}

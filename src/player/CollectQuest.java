package player;

import game.gamestate.World;

public class CollectQuest extends Quests{

	public CollectQuest(String name, String[] items, int[] amounts) {
		super(name);
		this.items = new String[items.length];
		target = new int[items.length];
		for(int i = 0; i < items.length; i++) {
			this.items[i] = items[i];
			target[i] = amounts[i];
		}
	}
	
	public boolean isQuestFinished() {
		for(int i = 0; i < items.length; i++) {
			if(World.inv.checkItem(items[i]) < target[i]) return false;
		}
		return true;
	}
}

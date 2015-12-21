package player;

import java.io.Serializable;

public class Quests implements Serializable{
	
	private static final long serialVersionUID = 1380405989838009270L;
	public String questName;;
	public boolean finished;
	public String[] mobs;
	public int[] target;
	public int[] tracker;
	
	public Quests() {
		questName = "NULL";
	}
	public Quests(String name) {
		questName = name;
		finished = false;
	}
	
	public boolean isQuestFinished() {
		return finished;
	}
	public void updateKills(String name) {
		return;
	}

}

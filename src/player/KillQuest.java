package player;

public class KillQuest extends Quests {

	public KillQuest(String name, String[] mobs, int[] amounts) {
		super(name);
		this.mobs = new String[mobs.length];
		target = new int[mobs.length];
		tracker = new int[mobs.length];
		for(int i = 0; i < mobs.length; i++) {
			this.mobs[i] = mobs[i];
			target[i] = amounts[i];
			tracker[i] = 0;
		}
	}
	
	public void updateKills(String mob) {
		if(finished) return;
		for(int i = 0; i < mobs.length; i++)
			if(mob.equals(mobs[i]) && tracker[i] < target[i]) tracker[i]++;
		for(int i = 0; i < mobs.length; i++) {
			if(tracker[i] == target[i] && i == mobs.length-1) finished = true;
			else if(tracker[i] == target[i]) continue;
			else break;
		}
	}
}

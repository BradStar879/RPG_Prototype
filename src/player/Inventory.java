package player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Inventory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9054539503023L;
	Map<String, Integer> itemList = new HashMap<String, Integer>();
	
	public Inventory() {
		
	}
	
	public void addItem(String item) {
		if(itemList.containsKey(item)) {
			itemList.put(item, itemList.get(item) + 1);
		}
		else {
			itemList.put(item, 1);
		}
	}
	
	public String[] getItems() {
		int i = 0;
		String[] temp = new String[itemList.size()];
		for(String key: itemList.keySet()) {
			temp[i] = key;
			i++;
		}
		return temp;
	}
	
	public int[] getItemAmounts() {
		int i = 0;
		int[] temp = new int[itemList.size()];
		for(String key: itemList.keySet()) {
			temp[i] = itemList.get(key);
			i++;
		}
		return temp;
	}
	
	public int size() {
		return itemList.size();
	}
	
	public boolean isEmpty() {
		return itemList.isEmpty();
	}
}

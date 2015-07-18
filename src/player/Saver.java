package player;

import game.gamestate.MenuState;
import game.gamestate.World;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Saver implements Serializable{
	

	private static final long serialVersionUID = 1246801783532L;
	CharacterStats[] team = new CharacterStats[3];
	PlayerMover position = new PlayerMover(Color.BLACK, 1, 1, 0);
	Inventory inv = new Inventory();
	
	public Saver() {
		
	}
	
	public void save(CharacterStats[] chars, PlayerMover player, Inventory inv) {
		ObjectOutputStream outputStream;
		ObjectOutputStream outputStream2;
		ObjectOutputStream outputStream3;
		ObjectOutputStream outputStream4;
		ObjectOutputStream outputStream5;
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream("Save/Char1.dat"));
			outputStream.writeObject(chars[0]);
			outputStream2 = new ObjectOutputStream(new FileOutputStream("Save/Char2.dat"));
			outputStream2.writeObject(chars[1]);
			outputStream3 = new ObjectOutputStream(new FileOutputStream("Save/Char3.dat"));
			outputStream3.writeObject(chars[2]);
			outputStream4 = new ObjectOutputStream(new FileOutputStream("Save/Pos.dat"));
			outputStream4.writeObject(player);
			outputStream5 = new ObjectOutputStream(new FileOutputStream("Save/Inv.dat"));
			outputStream5.writeObject(inv);
			System.out.println("SAVED");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void load() {
			
			try {
				ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Save/Char1.dat")));
				team[0] = (CharacterStats) inputStream.readObject();
				ObjectInputStream inputStream2 = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Save/Char2.dat")));
				team[1] = (CharacterStats) inputStream2.readObject();
				ObjectInputStream inputStream3 = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Save/Char2.dat")));
				team[2] = (CharacterStats) inputStream3.readObject();
				ObjectInputStream inputStream4 = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Save/Pos.dat")));
				position = (PlayerMover) inputStream4.readObject();
				ObjectInputStream inputStream5 = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Save/Inv.dat")));
				inv = (Inventory) inputStream5.readObject();
				World.loading = true;
				World.player = position;
				World.team = team;
				World.inv = inv;
				MenuState.isSavedGame = true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}

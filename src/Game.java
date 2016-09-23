
import java.util.Scanner;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

public class Game {
	
	public static void main( String[] args) {
		
		System.out.println("What is your username?");

		Scanner in = new Scanner( System.in );
		String name = in.nextLine();
		
		Player player = new Player(name);

		System.out.println( "Profile was loaded" );
		player.map.printDescription();
		boolean quit = false;
		
		while( !quit ) {
			in = new Scanner( System.in );
			String command = in.nextLine();

			if( command.equals("quit") ) {
				quit = quitProcess( player );
			}
			else if( command.equals("save game") ) {
				saveProcess( player );
			}
			else if( command.equals("stats") ) {
				printStats(player);
			}
			else if( command.equals("commands") ) {
				printCommands();
			}
			else {
				player.handleCommand( command );		
			}	
		}
		
		in.close();
	}
	
	public static void printStats(Player player) {
		System.out.println("HP: " + player.getHp() );
		System.out.println("Attack: " + player.getAttack() );
		System.out.println("Defense: " + player.getDefense() );
		System.out.println("Level: " + player.getLevel() );
		System.out.println("XP: " + player.getXp() );
	}
	
	public static void printCommands() {
		Connection connection = new Connection();
		DBCursor cursor = connection.getCollection("command").find();
		if( cursor.size() > 0) {
			while( cursor.hasNext() ) {
				BasicDBObject next = (BasicDBObject)cursor.next();
				System.out.println( next.get("name").toString() );
				for(String command : next.get("aliases").toString().split(",") ) {
					command = command.replace("[", "").replace("]", "").replaceAll(" ", "").replaceAll("\"", "");
					if( !command.equals("") ) {
						System.out.println(command);
					}
				}
				
				
			}
		}
	}
	
	public static void saveProcess( Player player ) {
		player.save();
		System.out.println("Game Saved");
	}
	
	public static boolean quitProcess( Player player ) {
		System.out.println("Are you sure y/n ?");
		Scanner in = new Scanner( System.in );
		String quit_sequence = in.nextLine();
		boolean quit = false;
		try {				
			if( quit_sequence.charAt(0) == 'y' || quit_sequence.charAt(0) == 'Y') {
				quit = true;
				System.out.println("Would you like to save y/n ?");
				String save_sequence = in.nextLine();
				try {				
					if( save_sequence.charAt(0) == 'y' || save_sequence.charAt(0) == 'Y') {
						player.save();
						System.out.println("Game Saved");
					}
				}
				catch(Exception e) {}
			}
		}
		catch(Exception e) {}
		in.close();
		return quit;
	}
	
}

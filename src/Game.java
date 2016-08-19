
import java.util.Scanner;

public class Game {
	
	public static void main( String[] args) {
		
		System.out.println("What is your username?");

		Scanner in = new Scanner( System.in );
		String name = in.nextLine();
		
		Player player = new Player(name);

		System.out.println( "Profile was loaded" );
		
		boolean quit = false;
		
		while( !quit ) {
			in = new Scanner( System.in );
			String command = in.nextLine();

			if( command.equals("quit") ) {
				System.out.println("Are you sure y/n ?");
				in = new Scanner( System.in );
				String quit_sequence = in.nextLine();
				try {				
					if( quit_sequence.charAt(0) == 'y' || quit_sequence.charAt(0) == 'Y') {
						quit = true;
						System.out.println("Would you like to save y/n ?");
						in = new Scanner( System.in );
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
			}
			else if( command.equals("save game") ) {
				player.save();
				System.out.println("Game Saved");
			}
			else if( command.equals("stats") ) {
				System.out.println("HP: " + player.getHp() );
				System.out.println("Attack: " + player.getAttack() );
				System.out.println("Defense: " + player.getDefense() );
				System.out.println("Level: " + player.getLevel() );
				System.out.println("XP: " + player.getXp() );
			}
			
			
			Command c = Command.handleCommand( command );			
			System.out.println( c.getCommand() );
		}
		
		
		in.close();
	}
	
}

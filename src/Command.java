import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Command {
	public String command = "";
	public String type = "";
	public String[] parameters;
	
	public Command(String command, String[] parameters) {
		this.command = command;
		this.parameters = parameters;
	}
//	
//	public static Command handleCommand( String text ) {
//		return new Command( text, " where? ");
//	}

	public Command loadCommand(String text) {
		
		String[] hosts = { "127.0.0.1" };
		Connection connection = new Connection(hosts, "27017", "zork", "", "");
		String[] parameters = text.split(" ");
		
		DBCursor cursor = connection.getCollection("player").find( new BasicDBObject("name",parameters[0].toLowerCase()) ).limit(1);
		if( cursor.size() > 0 ) {	
			DBObject next = cursor.next();
			//return new Command();
		}
		
		return null;
	}
	
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}
	
	
}


public class Command {
	public String command = "";
	public String parameters = "";
	
	public Command(String command, String parameters) {
		this.command = command;
		this.parameters = parameters;
	}
	
	public static Command handleCommand( String text ) {
		return new Command( text, " where? ");
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	
}

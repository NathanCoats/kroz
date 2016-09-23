import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Player {

	public String id;
	public String name;
	public int hp;
	public int attack;
	public int defense;
	public int level;
	public int xp;
	public Map map;
	
	public int x;
	public int y;
	
	public HashMap<String, Item> items;
	
	public Player( String name ) {
		try {
			
			this.items = new HashMap<String, Item>();
			
			Connection connection = new Connection();
			DBCursor cursor = connection.getCollection("player").find( new BasicDBObject("name", name) ).limit(1);
	        if(cursor.size() == 0) {
	    		this.id = "";
	    		this.name = name;
	    		this.level = 1;
	    		this.hp  = 10;
	    		this.attack = 5;
	    		this.defense = 5;
	    		this.level = 1;
	    		this.xp = 0;
	    		this.x  = 0;
	    		this.y  = 0;
	    		this.map = new Map(this.x, this.y);
	    		
	        }
	        else {
	        	DBObject next = cursor.next();
	        	this.id = next.get("_id").toString();
	        	this.name = next.get("name").toString();
	        	this.x = Integer.parseInt( next.get("x").toString() );
	        	this.y = Integer.parseInt( next.get("y").toString() );
	        	this.level = Integer.parseInt( next.get("level").toString() );
	        	this.hp = Integer.parseInt( next.get("hp").toString() );
	        	this.attack = Integer.parseInt( next.get("attack").toString() );
	        	this.defense = Integer.parseInt( next.get("defense").toString() );
	        	this.xp = Integer.parseInt( next.get("xp").toString() );
	        	this.map = new Map(this.x, this.y);

	        	// this is a temporary placeholder until we get a working system for items
	        	this.items = Item.convertItems((ArrayList<String>)next.get("items"));
	        	
	        }
		}
		catch(Exception e) {
			
		}
	}
	
	public void save() {
	    Connection connection = new Connection();

	    if( this.id.equals("") ) {
		    BasicDBObject obj = new BasicDBObject("hp",this.hp)
		    	.append("attack", this.attack)
		    	.append("defense", this.defense)
		    	.append("xp", this.xp)
		    	.append("level", this.level)
		    	.append("name", this.name)
		    	.append("x", this.x)
		    	.append("y", this.y)
		    	
		    	// this is a temporary placeholder until we get a working system for items
		    	
		    	.append("items", Item.unConvertItems(this.items) );
		    
	    	connection.getCollection( "player" ).insert( obj );
	    }
	    else {
		    BasicDBObject obj = new BasicDBObject("name",this.name)
		    	.append("level", this.level)
		    	.append("hp", this.hp)
			    .append("attack", this.attack)
			    .append("defense", this.defense)
			    .append("xp", this.xp)
			    .append("x", this.x)
			    .append("y", this.y)
			    
			    // this is a temporary placeholder until we get a working system for items
		    	.append("items", Item.unConvertItems(this.items) );
		    
	    	BasicDBObject search = new BasicDBObject("name", this.name);
	    	connection.getCollection( "player" ).update(search, obj, true, false);
	    }
	    
	}
	
	public void levelUp() {
		this.setLevel(level + 1);
		this.setXp(0);
		this.setAttack(attack + 1);
		this.setDefense(defense + 1);
		this.setHp(hp + 2);
	}
	
	public void handleCommand(String text) {
		try {

			Connection connection = new Connection();
			String[] parameters = text.split(" ");
			String base_command = parameters[0].toLowerCase();
			
			BasicDBList or = new BasicDBList();
			or.add( new BasicDBObject( "name", base_command ) );
			or.add( new BasicDBObject( "aliases", base_command ) );
			
			DBCursor cursor = connection.getCollection("command").find( new BasicDBObject("$or", or) ).limit(1);
			if( cursor.size() > 0 ) {
				
				Map map = new Map(this.x, this.y);
				DBObject next = cursor.next();
				
				String type = next.get("type").toString();

				if(type.equals("move")) {
					handleMove(parameters);
				}
				else if(type.equals("obtain")) {
					handleObtain(parameters);
				}
				else if(type.equals("drop")) {
					//handleDrop(parameters);
				}
				else if(type.equals("describe")) {
					handleDescribe(base_command, parameters);
				}
				else if(type.equals("attack")) {
					
				}
				else if(type.equals("modify")) {
					
				}
				else if(type.equals("use")) {
					handleUse(parameters);
				}
				else if(type.equals("player")) {
					
				}
				else if(type.equals("inventory")) {
					handleInventory();
				}
				else if(type.equals("map")) {
					System.out.println( map.getDescription() );
				}
			}
			else throw new Exception("Invalid command");
			
		}
		catch(Exception e) {
			System.out.println( e.getMessage() );
			//System.out.println("Sorry Command Not Valid");
		}
		
	}
	
	public void addItem( String item_name) {
		this.items.put(item_name.toLowerCase(), new Item(item_name) );
	}


	public void handleUse(String[] parameters) {
		try {
			
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println(parameters[0] + " what?");
		}	
	}
	
	public void handleDrop(String[] parameters) {
		try {
			
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println(parameters[0] + " what?");
		}	
	}
	
	public void handleInventory() {
		this.printInventory();
	}
	
	public void handleObtain(String[] parameters) {
		try {
			String item_name = parameters[1];
			if( canTake( item_name) ) {
				this.addItem(item_name);
				this.map.removeItem(item_name);
				System.out.println("You took: " + item_name);
			}
			else {
				System.out.println("Sorry you can't take that");
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println(parameters[0] + " what?");
		}	
	}
	
	public void handleMove(String[] parameters) {
		try {
			if( parameters[1].equalsIgnoreCase("up") || parameters[1].equalsIgnoreCase("straight") || parameters[1].equalsIgnoreCase("north") ) {
				this.goUp();
			}
			else if( parameters[1].equalsIgnoreCase("down") || parameters[1].equalsIgnoreCase("south") ) {
				this.goDown();
			}
			else if( parameters[1].equalsIgnoreCase("left") || parameters[1].equalsIgnoreCase("west")) {
				this.goLeft();
			}
			else if( parameters[1].equalsIgnoreCase("right") || parameters[1].equalsIgnoreCase("east")) {
				this.goRight();
			}
			else {
				System.out.println("Direction unrecognized");
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println(parameters[0] + " where?");
		}
	}
	
	public void handleDescribe(String base_command, String[] parameters) {
		try {
			Connection connection = new Connection();
			DBCursor cursor = connection.getCollection("item")
					.find(new BasicDBObject("title", new BasicDBObject("$regex" , parameters[1] ).append("$options","i")) )
					.limit(1);
		
			if(cursor.size() == 0) {
				System.out.println("Nothing found with that name.");
			}
			else {
				BasicDBObject next = (BasicDBObject)cursor.next();
				System.out.println( next.get("title").toString() );
				System.out.println( next.get("description").toString() );
			}
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println( base_command + " what?" );
		}			
	}
	
	public void printInventory() {

	    Iterator it = this.items.entrySet().iterator();
	    
		for( java.util.Map.Entry<String, Item> entry : this.items.entrySet() ) {
			System.out.println(entry.getValue().getName());
    	}
		

	    
	}
	
	public boolean canAttack() {
		return false;
	}
	
	public boolean canTake( String item_name) {
		try {
			return ( this.map.items.get( item_name.toLowerCase() ).can_take );
		}
		catch(NullPointerException e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean canMove(Integer x, Integer y) {
		try {
			Path path = this.map.paths.get(x + ":" + y);
			
			return ( x == path.getX() && y == path.getY() );
		}
		catch(NullPointerException e) {
			return false;
		}
	}
	
	public void getPosition() {
		System.out.println(x + " " + y);
	}
	
	public void goUp() {
		try {
			if( this.canMove(x, y + 1) ) {
				this.setY( y + 1);
				map.printDescription();
			}
			else {
				System.out.println("Sorry You can't go there");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void goDown() {
		if( this.canMove(x, y - 1) ) {
			this.setY( y - 1);
			map.printDescription();
		}
		else {
			System.out.println("Sorry You can't go there");
		}
	}
	
	public void goRight() {
		if( this.canMove(x + 1, y) ) {
			this.setX( x + 1);
			map.printDescription();
		}
		else {
			System.out.println("Sorry You can't go there");
		}
	}
	
	public void goLeft() {
		if( this.canMove(x - 1, y) ) {
			this.setX( x - 1);
			map.printDescription();
		}
		else {
			System.out.println("Sorry You can't go there");
		}
	}
	
	public void movePlayer(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.map = new Map(this.x, this.y);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.map = new Map(this.x, this.y);
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}
	
}

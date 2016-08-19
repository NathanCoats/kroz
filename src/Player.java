import java.util.ArrayList;

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
	
	public int x;
	public int y;
	
	public ArrayList<Item> items;
	
	public Player( String name ) {
		try {
			
			String[] hosts = { "127.0.0.1" };
			Connection connection = new Connection(hosts, "27017", "zork", "", "");
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
	    		this.items = new ArrayList<Item>();
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
	        	this.items = (ArrayList<Item>)next.get("items");
	        	
	        }
		}
		catch(Exception e) {
			
		}
	}
	
	public void save() {
		String[] hosts = { "127.0.0.1" };
	    Connection connection = new Connection(hosts, "27017", "zork", "", "");

	    if( this.id.equals("") ) {
		    BasicDBObject obj = new BasicDBObject("hp",this.hp)
		    	.append("attack", this.attack)
		    	.append("defense", this.defense)
		    	.append("xp", this.xp)
		    	.append("level", this.level)
		    	.append("name", this.name)
		    	.append("x", this.x)
		    	.append("y", this.y)
		    	.append("items", this.items);
		    
	    	connection.getCollection( "player" ).insert( obj );
	    }
	    else {
		    BasicDBObject obj = new BasicDBObject("hp",this.hp)
			    .append("attack", this.attack)
			    .append("defense", this.defense)
			    .append("xp", this.xp)
			    .append("level", this.level)
			    .append("name", this.name)
			    .append("x", this.x)
			    .append("y", this.y)
		    	.append("items", this.items);
		    
	    	BasicDBObject search = new BasicDBObject("name", this.name);
	    	connection.getCollection( "player" ).update(search, obj, true, false);
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
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

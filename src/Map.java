import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Map {
	
	public String id;
	public String description;
	public ArrayList<Item> items;
	public ArrayList<Path> paths;
	public Integer x;
	public Integer y;
	
	public Map( Integer x, Integer y ) {
		try {
			
			String[] hosts = { "127.0.0.1" };
			Connection connection = new Connection(hosts, "27017", "zork", "", "");

			BasicDBList and = new BasicDBList();
			and.add( new BasicDBObject( "x", x ) );
			and.add( new BasicDBObject( "y", y ) );
			
			DBCursor cursor = connection.getCollection("map").find( new BasicDBObject("$and", and) ).limit(1);

			
	        if(cursor.size() == 0) {
	    		this.id = "";
	    		this.x  = 1;
	    		this.y  = 1;
	    		this.description = "";
	    		
	    		// this is a temporary placeholder until we get a working system for items
	    		this.items = new ArrayList<Item>();
	        }
	        else {
	        	DBObject next = cursor.next();
	        	this.id = next.get("_id").toString();
	        	this.x = Integer.parseInt( next.get("x").toString() );
	        	this.y = Integer.parseInt( next.get("y").toString() );
	        	this.description = next.get("description").toString();
	        	
	        	// this is a temporary placeholder until we get a working system for items
	        	this.items = (ArrayList<Item>)next.get("items");
	        	
	        	// this is a temporary placeholder until we get a working system for paths
	        	this.paths = (ArrayList<Path>)next.get("paths");
	        	
	        }
		}
		catch(Exception e) {
			
		}
	}
	
	public void save() {
		String[] hosts = { "127.0.0.1" };
	    Connection connection = new Connection(hosts, "27017", "zork", "", "");

	    if( this.id.equals("") ) {
		    BasicDBObject obj = new BasicDBObject("desciption",this.description)
				    .append("x", this.x)
				    .append("y", this.y)
				    .append("paths", this.paths)
				    
				    // this is a temporary placeholder until we get a working system for items
			    	.append("items", this.items);
		    
	    	connection.getCollection( "player" ).insert( obj );
	    }
	    else {
		    BasicDBObject obj = new BasicDBObject("desciption",this.description)
			    .append("x", this.x)
			    .append("y", this.y)
			    // this is a temporary placeholder until we get a working system for paths
			    .append("paths", this.paths)
			    // this is a temporary placeholder until we get a working system for items
		    	.append("items", this.items);
		    
	    	BasicDBObject search = new BasicDBObject("x", this.x).append("y", this.y);
	    	connection.getCollection( "map" ).update(search, obj, true, false);
	    }
	    
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public ArrayList<Path> getPaths() {
		return paths;
	}

	public void setPaths(ArrayList<Path> paths) {
		this.paths = paths;
	}

	public Integer getX() {
		return x;
	}
	
	public void setX(Integer x) {
		this.x = x;
	}
	
	public Integer getY() {
		return y;
	}
	
	public void setY(Integer y) {
		this.y = y;
	}
	
	
}

import java.util.ArrayList;
import java.util.HashMap;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Map {
	
	public String id;
	public String description;
	public HashMap<String, Item> items;
	public HashMap<String, Path> paths;
	public Integer x;
	public Integer y;
	
	public Map( Integer x, Integer y ) {
		try {
			Connection connection = new Connection();

			BasicDBList and = new BasicDBList();
			and.add( new BasicDBObject( "x", x ) );
			and.add( new BasicDBObject( "y", y ) );
			
			this.paths = new HashMap<String, Path>();
			this.items = new HashMap<String, Item>();
			
			DBCursor cursor = connection.getCollection("map").find( new BasicDBObject("$and", and) ).limit(1);
			
	        if(cursor.size() == 0) {
	    		this.id = "";
	    		this.x  = 1;
	    		this.y  = 1;
	    		this.description = "No Map Loaded";

	        }
	        else {
	        	DBObject next = cursor.next();
	        	this.id = next.get("_id").toString();
	        	this.x = Integer.parseInt( next.get("x").toString() );
	        	this.y = Integer.parseInt( next.get("y").toString() );
	        	this.description = next.get("description").toString();
	        	
	        	// this is ugly but it will work for now
	        	this.items = Item.convertItems( (ArrayList<String>)next.get("items") );

	        	// this is ugly but it will work for now
	        	this.paths = Path.convertPaths( (ArrayList<DBObject>)next.get("paths") );

	        }
		}
		catch(Exception e) {
			
		}
	}
	
	public void removeItem(String key) {
		try {
			this.items.remove(key);
			this.save();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
	    Connection connection = new Connection();

	    if( this.id.equals("") ) {
		    BasicDBObject obj = new BasicDBObject("description", this.description)
				    .append("x", this.x)
				    .append("y", this.y)
				    .append("paths", Path.unConvertPaths( this.paths ) )
				    
				    // this is a temporary placeholder until we get a working system for items
			    	.append("items", Item.unConvertItems(this.items));
		    
	    	connection.getCollection( "player" ).insert( obj );
	    }
	    else {
		    BasicDBObject obj = new BasicDBObject("description",this.description)
			    .append("x", this.x)
			    .append("y", this.y)
			    // this is a temporary placeholder until we get a working system for paths
			    .append("paths", Path.unConvertPaths( this.paths ) )
			    // this is a temporary placeholder until we get a working system for items
		    	.append("items", Item.unConvertItems(this.items));
		    
	    	BasicDBObject search = new BasicDBObject("x", this.x).append("y", this.y);
	    	connection.getCollection( "map" ).update(search, obj, true, false);
	    }
	    
	}
	
	public void printDescription() {
		System.out.println( this.getDescription() );
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

	public HashMap<String, Item> getItems() {
		return items;
	}

	public void setItems(HashMap<String, Item> items) {
		this.items = items;
	}

	public HashMap<String, Path> getPaths() {
		return paths;
	}

	public void setPaths(HashMap<String, Path> paths) {
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

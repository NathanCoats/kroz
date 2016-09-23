import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Item {

		public String name;
		public String title;
		public String description;
		public Integer attack;
		public boolean can_eat;
		public boolean can_take;
		public Integer hp;
		
		public Item(String name) {
			Connection connection = new Connection();

			DBCursor cursor = connection.getCollection("item").find( new BasicDBObject("name", name.toLowerCase()) ).limit(1);
			if(cursor.size() > 0) {
	        	
	        	DBObject next = cursor.next();
	        	this.setAttack( Integer.parseInt( next.get("attack").toString() ) );
	        	this.setTitle( next.get("title").toString() );
	        	this.setDescription( next.get("description").toString() );
	        	this.setHp( Integer.parseInt( next.get("hp").toString() ) );
	        	this.setCanEat( next.get("can_eat").toString().equalsIgnoreCase("true") );
	        	this.setCanTake( next.get("can_take").toString().equalsIgnoreCase("true") );
	        	
	        }
			else {
				
			}
		}
		
		public Item(String name, String title, String description, Integer attack, Integer hp, boolean can_eat) {
			this.setName(name);
			this.setTitle(title);
			this.setDescription(description);
			this.setAttack(attack);
			this.setCanEat(can_eat);
			this.setHp(hp);
		}
		
		public static HashMap<String, Item> convertItems(ArrayList<String> map_items) {
			HashMap<String, Item> items = new HashMap<String, Item>();
			
        	Iterator<String> item_it = map_items.iterator();
        	while(item_it.hasNext()) {
        		String item_name = item_it.next().toLowerCase();
        		items.put(item_name, new Item(item_name));
        	}
        	
        	return items;
		}
		
		public static ArrayList<String> unConvertItems(HashMap<String, Item> player_items) {
			ArrayList<String> items = new ArrayList<String>();
			
			for( Map.Entry<String, Item> entry : player_items.entrySet() ) {
        		items.add( entry.getKey() );
        	}
        	
        	return items;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getAttack() {
			return attack;
		}

		public void setAttack(Integer attack) {
			this.attack = attack;
		}

		public boolean getCanEat() {
			return can_eat;
		}

		public void setCanEat(boolean can_eat) {
			this.can_eat = can_eat;
		}
		
		public boolean getCanTake() {
			return can_take;
		}

		public void setCanTake(boolean can_take) {
			this.can_take = can_take;
		}

		public Integer getHp() {
			return hp;
		}

		public void setHp(Integer hp) {
			this.hp = hp;
		}
		
		
}

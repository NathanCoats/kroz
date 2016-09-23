import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class Path {
	public Integer x;
	public Integer y;
	
	public Path( Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	

	public Integer getX() {
		return x;
	}
	
	public static ArrayList<BasicDBObject> unConvertPaths( HashMap<String, Path> map_paths ) {
		ArrayList<BasicDBObject> paths = new ArrayList<BasicDBObject>();
		
		for( Map.Entry<String, Path> entry : map_paths.entrySet() ) {
			BasicDBObject path = new BasicDBObject("x", entry.getValue().getX()).append("y", entry.getValue().getY());
    		paths.add( path );
    	}
    	
    	return paths;
	}
	
	public static HashMap<String, Path> convertPaths(ArrayList<DBObject> map_paths) {
		HashMap<String, Path> paths = new HashMap<String, Path>();
    	Iterator<DBObject> it = map_paths.iterator();
    	
    	while(it.hasNext()) {
    		DBObject line = it.next();
    		Integer path_x = Integer.parseInt(line.get("x").toString() );
    		Integer path_y = Integer.parseInt(line.get("y").toString()) ;
    		Path path = new Path(path_x, path_y);
    		String path_key = path_x + ":" + path_y;
    		paths.put(path_key, path);
    	}
		return paths;
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

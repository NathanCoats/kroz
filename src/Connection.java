import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class Connection {
	
	  public String database = "" ;
	  public String username = "" ;
	  public String password = "";
	  public String[] hosts = { "127.0.0.1" } ;
	  public String port = "";
	  public String table = "";

	  // Mongo variables
	  public MongoClient mongo_client = null;
	  public DB db = null;
	  public DBCollection col = null;
	  public DBCursor cursor  = null;
	  public DBObject current = null;
	  
	  public Connection(String[] hosts, String port, String database, String username, String password) {
		    setHosts(hosts);
		    setPort(port);
		    setDatabase(database);
		    setUsername(username);
		    setPassword(password);
	  }
	  
	  // returns the current collection.
	  public DBCollection getCollection( String collection ) {
	    List<ServerAddress> servers = getServerList();
	    try {
	      mongo_client = new MongoClient( servers );
	      db = mongo_client.getDB(database);
	      return db.getCollection( collection );
	    }
	    catch(Exception e) {
	      //@TODO handle properly
	      e.printStackTrace();
	      System.exit(0);
	    }
	    return null;
	  }
	
	  // will close the current connection to the database
	  public void close() {
	    mongo_client.close();
	  }
	  
	  // UTILITY METHODS

	  // simply gets back a list of ServerAddress to create a new mongo connection, this is obtained from the hosts array
	  public List<ServerAddress> getServerList() {
	    List<ServerAddress> servers = new ArrayList<ServerAddress>();
	    try {
	      for(int i = 0; i < hosts.length; i++) {
	        servers.add( new ServerAddress( hosts[i] ) );
	      }
	    }
	    catch(Exception e) {
	      e.printStackTrace();
	    }
	    return servers;
	  }

		/**
		* Returns value of host
		* @return
		*/
		public String getHostString() {
	    String host_string = "";
	    for(int i = 0; i < hosts.length; i++) {
	      host_string += hosts[i]+",";
	    }
	    // this is just a quick method to remove the trailing comma
	    return host_string.substring( 0, host_string.length() - 1 );
		}

		/**
		* Returns value of database
		* @return
		*/
		public String getDatabase() {
			return database;
		}

		/**
		* Sets new value of database
		* @param
		*/
		public void setDatabase(String database) {
			this.database = database;
		}

	  /**
	  * Returns value of hosts
	  * @return
	  */
	  public String[] getHosts() {
	    return hosts;
	  }

	  /**
	  * Sets new value of hosts
	  * @param
	  */
	  public void setHosts(String[] hosts) {
	    // shallow setter
	    this.hosts = hosts;
	  }

		/**
		* Returns value of username
		* @return
		*/
		public String getUsername() {
			return username;
		}

		/**
		* Sets new value of username
		* @param
		*/
		public void setUsername(String username) {
			this.username = username;
		}

		/**
		* Returns value of password
		* @return
		*/
		public String getPassword() {
			return password;
		}

		/**
		* Sets new value of password
		* @param
		*/
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		* Returns value of port
		* @return
		*/
		public String getPort() {
			return port;
		}

		/**
		* Sets new value of port
		* @param
		*/
		public void setPort(String port) {
			this.port = port;
		}

		/**
		* Returns value of table
		* @return
		*/
		public String getTable() {
			return table;
		}

		/**
		* Sets new value of table
		* @param
		*/
		public void setTable(String table) {
			this.table = table;
		}

	
}

package config;

import org.exist.xmldb.XQueryService;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;

public class AccesDBExist {
	
    protected static String DRIVER = "org.exist.xmldb.DatabaseImpl"; 
    protected static String URI = "xmldb:exist://localhost:8888/exist/xmlrpc"; 
    protected static String collectionPath = "/db/Catalog"; 
    protected static String resourceName = "card_collection.xml";
	
	public static XQueryService connectExistDb() throws Exception {
		
		// initialize database driver 
        Class cl = Class.forName(DRIVER); 
        Database database = (Database) cl.newInstance(); 
        DatabaseManager.registerDatabase(database); 

        // get the collection 
        Collection col = DatabaseManager.getCollection(URI + collectionPath);
        
        // Instantiate a XQuery service 
        XQueryService service = (XQueryService) col.getService("XQueryService", 
                        "1.0"); 
        service.setProperty("indent", "yes"); 
        
        return service;		
	}

	public static String getResourceName() {
		return resourceName;
	}
	
	

}

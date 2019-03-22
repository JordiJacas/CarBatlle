package config;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AccesDBMongo {
	
	public static MongoCollection<Document> connectMongoDb() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("DeckBatlles");
		MongoCollection<Document> collection = database.getCollection("decks");
		
		return collection;
	}

}

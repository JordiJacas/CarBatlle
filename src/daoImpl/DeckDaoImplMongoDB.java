package daoImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import config.AccesDBMongo;
import iDao.IDeck;
import model.Card;
import model.Deck;

public class DeckDaoImplMongoDB implements IDeck{
	
	MongoCollection<Document> collection;
	Type type = new TypeToken<List<Card>>() {}.getType();

	@Override
	public Deck getDeckByName(String name) {
		// TODO Auto-generated method stub
		MongoCollection<Document> collection = AccesDBMongo.connectMongoDb();
		Deck currentDeck = null;
		
		BasicDBObject where = new BasicDBObject();
		where.put("deckName", name);
		
        FindIterable<Document> fi = collection.find(where);
        MongoCursor<Document> cursor = fi.iterator();
        
        
        
        try {
           Document newDocument =  cursor.next();
           
           String deckName = newDocument.get("deckName").toString();
           int deckValue = Integer.parseInt(newDocument.get("deckValue").toString());
           List<Card> deck = new Gson().fromJson(newDocument.get("deck").toString(), type);
           
           currentDeck = new Deck(deckName, deckValue, deck);
               
        } catch (Exception e) {
			currentDeck = null;
		}finally {
            cursor.close();
        }

		return currentDeck;
	}

	@Override
	public void addNewDeck(Deck deck) {
		// TODO Auto-generated method stub
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<Card>>() {}.getType();
		String json = gson.toJson(deck.getDeck(), type);
		
		System.out.println(json);
			
		MongoCollection<Document> collection = AccesDBMongo.connectMongoDb();
		
		Document newDeck = new Document();
		newDeck.put("deckName", deck.getDeckName());
		newDeck.put("deckValue", deck.getDeckValue());
		newDeck.put("deck", json);
		
		collection.insertOne(newDeck);
		
	}

	@Override
	public void updateDeck(Deck deck) {
		// TODO Auto-generated method stub
		
		MongoCollection<Document> collection = AccesDBMongo.connectMongoDb();
		String json = new Gson().toJson(deck.getDeck(), type);
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("deck", json));
		newDocument.append("$set", new BasicDBObject().append("deckValue", deck.getDeckValue()));
				
		BasicDBObject searchQuery = new BasicDBObject().append("deckName", deck.getDeckName());

		collection.updateOne(searchQuery, newDocument);
		
	}

}

package iDao;

import model.Deck;

public interface IDeck {
	
	Deck getDeckByName(String name);
	void addNewDeck(Deck deck);
	void updateDeck(Deck deck);

}

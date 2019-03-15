package model;

import java.util.List;

public class Deck {
	
	private String deckName;
	private int deckValue;
	private List<Card> deck;
	
	public Deck(String deckName, int deckValue, List<Card> deck) {
		super();
		this.deckName = deckName;
		this.deckValue = deckValue;
		this.deck = deck;
	}

	public String getDeckName() {
		return deckName;
	}

	public void setDeckName(String deckName) {
		this.deckName = deckName;
	}

	public int getDeckValue() {
		return deckValue;
	}

	public void setDeckValue(int deckValue) {
		this.deckValue = deckValue;
	}

	public List<Card> getDeck() {
		return deck;
	}

	public void setDeck(List<Card> deck) {
		this.deck = deck;
	}
}

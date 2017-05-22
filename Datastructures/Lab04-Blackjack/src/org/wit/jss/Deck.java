/*
 * Lab 04 - Group 22
 * Nathan Purpura, Jovin Schneider, Lam Pham
 * Blackjack
 * 3-17-16 
 */
package org.wit.jss;

import org.wit.lam.Card;
import org.wit.lam.Card.FACES;
import org.wit.lam.Card.SUIT;

public class Deck<T> extends Pile<T> {
	
	//The cards in the deck
	private Pile<Card> cards = new Pile<Card>();
	
	/**
	 * Constructor, calls the Pile's constructor
	 */
	public Deck()
	{
		super();
	}
	
	/**
	 * Puts 52 cards into the empty deck
	 */
	public void init()
	{
		cards = (Pile<Card>) get52();
		
	}
	
	/**
	 * adds cards to the deck
	 * @param cards a list of cards
	 */
	public void add(Card[] cards)
	{
		for(Card c : cards) add(c);
	}
	/**
	 * adds a single card to the deck
	 * @param card the card
	 */
	public void add(Card card)
	{
		cards.add(card);
	}
	
	/**
	 * Takes the top card of the deck
	 * @return
	 */
	public Card takeTop()
	{
		//We check availability first
		Card available = cards.remove(1);
		if(available != null) {
			
			return available;
		}
		else return null;
	}
	/**
	 * Takes a card from i slot
	 * @param i the space
	 * @return the card
	 */
	public Card take(int i)
	{
		
		if(i <= cards.getLength()) return cards.remove(i);
		else return null;
	}
	/**
	 * Shuffles the deck
	 */
	public void shuffle()
	{
		cards.shuffle();
		
	}
	/**
	 * Gets the amount of cards left in a deck
	 * @return the amount
	 */
	public int getCount()
	{
		return cards.getLength();
	}
	
	/**
	 * Generates a 52 card deck using cartesian product
	 * @return
	 */
	public static Pile<Card> get52()
	{
		Pile<Card> cards = new Pile<Card>();
		for(SUIT s : SUIT.values())
		{
			for(FACES f : FACES.values())
			{
				cards.add(new Card(s,f));
			}
		}
		return cards;
	}
}

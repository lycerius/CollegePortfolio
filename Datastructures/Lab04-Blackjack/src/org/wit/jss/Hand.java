/*
 * Lab 04 - Group 22
 * Nathan Purpura, Jovin Schneider, Lam Pham
 * Blackjack
 * 3-17-16 
 */
package org.wit.jss;

import org.wit.lam.Card;

public class Hand<T> extends Pile<T> {
	
	
	
	
	/**
	 * Creates a copy array of the pile of cards
	 * @return Returns the copy array
	 */
	public Card[] getHand()
	{
		Object[] ocards = this.toArray();
		Card[] cards = new Card[ocards.length];
		for(int i = 0; i < ocards.length; i++)
		{
			cards[i] = (Card) ocards[i];
		}
		return cards;
	}
	
	
	
}

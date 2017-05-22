/*
 * Lab 04 - Group 22
 * Nathan Purpura, Jovin Schneider, Lam Pham
 * Blackjack
 * 3-17-16 
 */
package org.wit.lam;

public class Card {
	
	//The suit of the card
	public final SUIT suit;
	
	//The face of the card
	public final FACES face;
	
	//Types of suits in a 52 card deck with Strings for pretty names
	public enum SUIT{
		HEARTS("Hearts"), DIAMONDS("Diamonds"), CLUBS("Clubs"), SPADES("Spades");

		String name;

		SUIT(String displayname)
		{
			this.name= displayname;
		}

		public String toString()
		{
			return name;
		}
	}

	//Types of faces in a 52 card deck with Strings for pretty names
	public enum FACES{
		KING("King"), QUEEN("Queen"), JACK("Jack"), ACE("Ace"), FACE2("2"), FACE3("3"), 
		FACE4("4"), FACE5("5"), FACE6("6"), FACE7("7"), FACE8("8"), FACE9("9"), FACE10("10");

		String name;

		FACES(String displayname)
		{
			this.name= displayname;
		}

		public String toString()
		{
			return name;
		}
	}
	
	/**
	 * Creates a new card with the given suit and face
	 * @param suit The suit of the card
	 * @param face The face of the card
	 */
	public Card(SUIT suit, FACES face ){
		this.suit = suit;
		this.face = face;
	}
	/**
	 * Returns a FACE of SUIT string
	 * EX: King of Hearts
	 */
	public String toString(){
		return face + " of " + suit;
	}

}

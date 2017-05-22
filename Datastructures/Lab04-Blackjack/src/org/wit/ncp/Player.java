/*
 * Lab 04 - Group 22
 * Nathan Purpura, Jovin Schneider, Lam Pham
 * Blackjack
 * 3-17-16 
 */
package org.wit.ncp;

import java.util.Scanner;

import org.wit.jss.Hand;
import org.wit.lam.Card;
import org.wit.lam.Card.FACES;
import org.wit.ncp.Game.WINTYPE;

/**
 * A human player
 * @author purpu
 *
 */
public class Player {

	/**
	 * Possible actions a player can respond with
	 * @author purpu
	 *
	 */
	public enum ACTION{
		HIT,STAY,LOOKAT;
	}
	
	//The players current hand
	private Hand<Card> hand = new Hand<Card>();
	
	//The players name
	private String name;
	
	//Saves the players loose state
	private boolean lost = false;
	
	//Saves the players win state
	private boolean won = false;
	
	//The way the player won
	private WINTYPE win;
	/**
	 * Creates a player with the given name
	 * @param name
	 */
	public Player(String name)
	{
		this.name = name;
	}
	/**
	 * Gets the players current hand
	 * @return the hand
	 */
	public Card[] getHand()
	{
		return hand.getHand();
	}
	/**
	 * Gets the name of the player
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Asks the player for a response
	 * @param scan the input console
	 * @param faceup the dealers current face up card
	 * @return the converted response of the player
	 */
	public ACTION getResponse(Scanner scan, Card faceup)
	{
		System.out.print("Faceup ["+faceup+"] (hit or stay): ");
		String response = scan.nextLine();
		if(response.equalsIgnoreCase("hit")) return ACTION.HIT;
		else if(response.equalsIgnoreCase("stay")) return ACTION.STAY;
		else return null;
	}
	/**
	 * Returns the calculated score of the player
	 * @return the score
	 */
	public int getScore(){
		Card[] cards = getHand();
		int aceCount = 0;
		int currentScore = 0;
		for(Card c : cards)
		{
			//Aces are counted seperately to get to 21 without busting
			if(c.face==FACES.ACE)
			{
				aceCount++;
				//If over 21 then aces will be converted to 1s later
				currentScore+=11;
			}
			else
			{
				currentScore+= Game.getBasicFaceValue(c);
			}
		}
		for(int i = 0; i < aceCount; i++)
		{
			
			//Convert aces if over 21
			if(currentScore > 21) currentScore -= 10;
			else break;
		}

		return currentScore;
	}
	/**
	 * Returns the way the player won (either a Natural Win or a Regular Win)
	 * @return the way the player won
	 */
	public WINTYPE getWinType()
	{
		return win;
	}
	/**
	 * Makes the player lose 
	 */
	public void lose()
	{
		if(!won()) lost = true;
	}
	/**
	 * Returns if the player lost
	 * @return lose state
	 */
	public boolean lost()
	{
		return lost;
	}
	/**
	 * Puts the card into the players hand
	 * @param c The Card
	 */
	public void takeCard(Card c)
	{
		hand.add(c);
	}
	
	/**
	 *returns the players name 
	 */
	@Override
	public String toString(){
		return getName();
	}
	
	/**
	 * Makes the player win a certain way
	 * @param win the way the player won
	 */
	public void win(WINTYPE win)
	{
		if(!lost())
		{
			won = true;
			this.win = win;
		}
	}
	/**
	 * Returns if the player won or not
	 * @return the win state
	 */
	public boolean won()
	{
		return won;
	}


}

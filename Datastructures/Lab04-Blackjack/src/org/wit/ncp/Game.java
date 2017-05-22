/*
 * Lab 04 - Group 22
 * Nathan Purpura, Jovin Schneider, Lam Pham
 * Blackjack
 * 3-17-16 
 */
package org.wit.ncp;

import java.util.ArrayList;
import java.util.Scanner;

import org.wit.jss.Deck;
import org.wit.lam.Card;
import org.wit.lam.Card.FACES;
import org.wit.ncp.Player.ACTION;

/**
 * https://www.pagat.com/banking/blackjack.html
 * @author Nathan Purpura
 *
 */
public class Game {

	//Enumerations of ways to win the game
	public enum WINTYPE
	{
		NATURAL,NONNATURAL;
	}
	/**
	 * Converts a card to its basic value (excluding aces which can change)
	 * @param c
	 * @return
	 */
	public static int getBasicFaceValue(Card c){
		if(c.face == FACES.JACK || c.face == FACES.KING || c.face == FACES.QUEEN || c.face == FACES.FACE10) return 10;
		else if(c.face == FACES.FACE2) return 2;
		else if(c.face == FACES.FACE3) return 3;
		else if(c.face == FACES.FACE4) return 4;
		else if(c.face == FACES.FACE5) return 5;
		else if(c.face == FACES.FACE6) return 6;
		else if(c.face == FACES.FACE7) return 7;
		else if(c.face == FACES.FACE8) return 8;
		else if(c.face == FACES.FACE9) return 9;
		else return 0;
	}
	/**
	 * Main function for game... if its a test it doesnt actually play the game, instead it does a test driver function
	 * @param args
	 */
	public static void main(String[] args)
	{
		boolean test = false;

		if(test) testDriver();
		else theActualGame();




	}
	/**
	 * A playground for testing experimental functions and objects
	 */
	public static void testDriver()
	{
		Game g = new Game(13);
		Player p1 = new Player("Test Player 1");
		Player p2 = new Player("Test Player 2");
		g.addPlayer(p1);
		g.addPlayer(p2);
		g.init();
		g.deal();
		System.out.println(p1.getScore());
		System.out.println(p2.getScore());
	}
	/**
	 * The real game
	 */
	public static void theActualGame()
	{
		Scanner scan = new Scanner(System.in);

		System.out.println("Welcome to Black Jack");
		System.out.println("How many times should house shuffle: ");
		Game g = new Game(Integer.valueOf(scan.nextLine()));
		System.out.println("\nshuffled!");
		System.out.print("How many players would you like to play: ");
		int numberOfPlayers = Integer.valueOf(scan.nextLine());
		if(numberOfPlayers >= 0)
		{

			for(int i = 1; i <= numberOfPlayers; i++)
			{
				System.out.print("Please name player "+i+": ");
				g.addPlayer(new Player(scan.nextLine()));
			}

			System.out.print("How many computer players: ");
			numberOfPlayers = Integer.valueOf(scan.nextLine());

			for(int i = 1; i <= numberOfPlayers; i++)
			{

				g.addPlayer(new ComputerPlayer("Computer "+i));
			}

			g.init();
			g.play();
			scan.close();
		}

	}

	//A List of players, including computer players but not the dealer
	private ArrayList<Player> players = new ArrayList<>();

	//The dealer gets his own spot on the table
	private Player dealer = new Player("The Dealer");

	//Deck used to play the game
	private Deck<Card> dealerDeck = new Deck<Card>();

	//Keeps track of the current player
	private Player currentPlayer;

	//Keeps track of the current position on the table
	private int currentPlayerIndex = 0;

	//Keeps track of the dealers faceup (not the hole)
	private Card dealerFaceup;

	private int shuffleCount;

	private boolean gameReady = false;

	/**
	 * Creates a game
	 * @param shuffleCount the amount of times for house to shuffle
	 */
	public Game(int shuffleCount){
		this.shuffleCount = shuffleCount;
	}

	/**
	 * Adds a player to the game
	 * @param p the new player
	 */
	public void addPlayer(Player p)
	{

		players.add(p);
	}

	/**
	 * The deal and "blackjack"
	 * At the start of a blackjack game, the players and the dealer receive two cards each. 
	 * The players' cards are normally dealt face up, while the dealer has one face down (called the hole card) 
	 * and one face up.
	 * The best possible blackjack hand is an opening deal of an ace with any ten-point card. This is called a "blackjack", or a natural 21, and the player holding this automatically wins unless the dealer also has a blackjack. If a player and the dealer each have a blackjack, the result is a push for that player. If the dealer has a blackjack, all players not holding a blackjack lose.
	 */
	public void deal()
	{
		boolean debounce = false;
		for(int i = 1; i <= 2; i++){
			System.out.println("===============================================");
			System.out.println("Deal round: "+i);
			for(Player p : players)
			{
				Card c = dealerDeck.takeTop();
				System.out.println("Player "+p+" recieved a "+c);
				dealerFaceup = c;
				p.takeCard(c);
			}
			Card c = dealerDeck.takeTop();
			if(debounce) 
			{
				System.out.println("-Dealer recieves a "+c);
			}
			else
			{
				debounce = true;
				System.out.println("-Dealer recieves hole");
			}
			dealer.takeCard(c);

		}
		System.out.println("===============================================");

		//Check Natural21
		for(Player p : players)
		{
			if(p.getScore() == 21)
			{
				System.out.println(p.getName()+" wins with a natural 21!");
				p.win(WINTYPE.NATURAL);
			}
		}
	}
	/**
	 * Initializes the game, must be called before the game can start
	 */
	public void init()
	{
		System.out.print("*");
		dealerDeck.init();
		for(int i = 0; i < shuffleCount; i++) 
		{
			System.out.print("shuffle|");
			dealerDeck.shuffle();
		}
		gameReady = true;
	}
	/**
	 * After the cards have been dealt, the game goes on with each player taking action - in clockwise order starting to dealer's left.
	 */
	public boolean next()
	{
		if(currentPlayerIndex >= players.size()) return false;
		while((currentPlayer = players.get(currentPlayerIndex++)).won())
		{
			System.out.println(currentPlayer+" already won... skipping");
			if(currentPlayerIndex >= players.size()) return false;
		}

		return true;
	}
	/**
	 * Plays the game given the current set of players
	 */
	public void play()
	{
		
		//Can only start if ready
		if(gameReady)
		{
			gameReady = false;
			Scanner scan = new Scanner(System.in);
			//Start with the initial deal
			deal();

			//players play
			while(next())
			{
				
				System.out.println("It is "+currentPlayer.getName()+"'s turn!");
				Player.ACTION rep = null;
				do{
					int score = currentPlayer.getScore();
					if(score > 21) 
					{
						System.out.println("You bust! [score="+currentPlayer.getScore()+"]");
						currentPlayer.lose();
						break;

					}
					System.out.println("Your score is "+currentPlayer.getScore()+". Dealer has a "+dealerFaceup+" faceup. What would you like to do?: ");
					rep = currentPlayer.getResponse(scan,dealerFaceup);
					System.out.println(currentPlayer.getName()+" "+rep.toString().toLowerCase()+"s");
					if(rep == ACTION.HIT)
					{
						Card c = dealerDeck.takeTop();
						System.out.println(currentPlayer.getName()+" drew a "+c);
						currentPlayer.takeCard(c);
					}
				}while(rep != ACTION.STAY);
				System.out.println("===========================================");



			}
			
			//dealer goes
			System.out.println("-Dealer flips hole and reveals a "+dealer.getHand()[0]);
			System.out.println("-Dealer's score is "+dealer.getScore());

			//Dealer gets natural 21 with 2 cards... all players loose except those who recieved blackjack
			if(dealer.getScore() == 21)
			{
				System.out.println("!!!Dealer gets a natural 21, all players lose who didnt recieve a natural 21!!!");
				for(Player p : players)
				{
					if(p.won() && p.getWinType() == WINTYPE.NATURAL)
					{
						System.out.println(p+" won!");
					}
				}
			}

			//If he doesnt win with a natural then he will draw until he hits a soft 17
			else
			{

				while(dealer.getScore() < 17)
				{
					Card draw = dealerDeck.takeTop();
					System.out.println("	Dealer drew a "+draw);
					dealer.takeCard(draw);
				}

				System.out.println("(Dealer hit soft 17)");
				System.out.println("========================================");
				//dealer has hit soft... now check for dealer bust
				if(dealer.getScore() > 21)
				{
					System.out.println("!!!Dealer busts with "+dealer.getScore()+", all who havent bust win!!!");
					for(Player p : players)
					{
						if(!p.lost())
						{
							System.out.println(p.getName()+" won!");
						}
					}
				}

				else
				{
					System.out.println("Dealer stays with a final score of "+dealer.getScore());
					System.out.println("All players who havent busted and with higher hands win");
					for(Player p : players)
					{
						if(!p.lost() && p.getScore() > dealer.getScore())
						{
							System.out.println(p.getName()+" won!");
						}
					}
				}
			}
		}
	}








}

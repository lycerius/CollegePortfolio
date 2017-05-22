/*
 * Lab 04 - Group 22
 * Nathan Purpura, Jovin Schneider, Lam Pham
 * Blackjack
 * 3-17-16 
 */
package org.wit.ncp;

import java.util.Random;
import java.util.Scanner;

import org.wit.lam.Card;
import org.wit.lam.Card.FACES;

/**
 * A player that the computer controls
 * @author purpu
 *
 */
public class ComputerPlayer extends Player{
	/**
	 * The name of the computer player
	 * @param name
	 */
	public ComputerPlayer(String name) {
		super(name);
	}
	
	/**
	 * Receives a response from the Computer Player.
	 * The computer player doesn't use the input, it automatically chooses a response based on an algorithm given the faceup card
	 * and its own score.
	 */
	@Override
	public ACTION getResponse(Scanner input, Card faceup) {

		int score = getScore();
		Random r = new Random();
		//A random time to sleep to make the computer player seem more human like
		long sleeprandom = (r.nextInt(3)+2)*1000;


		if(score >= 17 && score <= 21)
		{
			//700 ms sleep because the computer technically shouldn't have to "think" about this
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			return ACTION.STAY;
		}
		else
		{
			if(score >= 13 && score <= 14)
			{
				try{Thread.sleep(sleeprandom);}catch(InterruptedException e){}
				if(faceup.face == FACES.ACE || faceup.face == FACES.FACE10 || faceup.face == FACES.FACE9) return ACTION.STAY;
				int facevalue = Game.getBasicFaceValue(faceup);
				if(facevalue == 7 || facevalue == 8) return ACTION.HIT;
				else return ACTION.STAY;
			}
			else if(score == 12)
			{
				try{Thread.sleep(sleeprandom);}catch(InterruptedException e){}
				if(faceup.face == FACES.ACE) return ACTION.HIT;
				int facevalue = Game.getBasicFaceValue(faceup);
				if(facevalue == 2 || facevalue == 3 || (facevalue >=7 && facevalue <= 10)) return ACTION.HIT;
				else return ACTION.STAY;

			}
			else
			{
				//650 ms sleep because the computer technically shouldn't have to "think" about this
				try{Thread.sleep(650);}catch(InterruptedException e){}
				return ACTION.HIT;
			}
		}

	}

}

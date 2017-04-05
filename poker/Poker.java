import PokerPack.*;
import java.util.Scanner;

/**
* This is the game main Class.
* @author Felipe Alves Siqueira 9847706 and Rodolfo Coelho Dalapicola 8006838 
*/
class Poker{
	static final int initCredit = 200;
	static final int numOfCards = 5;
	static final int changeTimes = 2; 
	private final Deck myDeck;
	private final Card [] myHand;
	private final Score myScore; //Multithreading score computation
	private final Placar myPlacar; //Common score computation
	private int credits;

	public Poker(){
		myHand = new Card[numOfCards];
		myDeck = new Deck();
		myScore = new Score();
		myPlacar = new Placar();
		credits = initCredit;
	}

	/**
	* Simple getter.
	* @Returns Current quantity of credits.
	*/
	public int howManyCredits(){
		return credits;
	}

	/**
	* Add the given value to the user credit wallet. 
	* @Throws IllegalArgumentException, is given parameter is negative.
	* @Return True, if given integer is a positive integer. False otherwise.
	*/
	public boolean payMoreCredits(int moreCredits) throws IllegalArgumentException{
		if (moreCredits < 0)
			throw new IllegalArgumentException("What? No negative values allowed " +
				"(keep playing to do so)!!");
		credits += moreCredits;
		return (moreCredits > 0);
	}

	/**
	* Remove the given value to the user credit wallet. 
	* @Throws IllegalArgumentException, is given parameter is negative. 
	* OutOfCredits, if user have less credit than the given quantity.
	* @Return How many credits was bet.
	*/
	public int betCredits(int howMany) throws IllegalArgumentException, OutOfCredits{
		if (howMany < 0)
			throw new IllegalArgumentException("Are you SERIOUS? You CAN'T bet negative values!");
		if (howMany > credits)
			throw new OutOfCredits("You DON'T have this quantity of credits!" +
				" Pay more money to proceed!");
		credits -= howMany;
		return howMany;
	}

	/**
	* Redraw selected cards by the Player, twice (by default) in a row. Player may no redraw any cards.
	* @Return No return value.
	*/
	public void redraw(Scanner myInput){
		String [] stringAux;
		for (int i = 0, aux; i < changeTimes; ++i){
			System.out.println(myDeck.toString(myHand));
			System.out.print("> Chance "+ (1 + i) +
				"/" + changeTimes + 
				" for CHANGE your hand.\n> Type the index of the cards, " +
				"or press ENTER to continue.\n> ");
				
			stringAux = myInput.nextLine().split(" ");

			for(String j : stringAux){
				try {
					aux = Integer.parseInt(j);
					Card cardHolder = myHand[aux];
					myHand[aux] = myDeck.draw();
					myDeck.giveCardBack(cardHolder);
				} catch (ArrayIndexOutOfBoundsException aioob){
					System.out.println("That was a invalid index!");
				} catch (DeckRunOut dro){
					System.out.println("No more cards available.");
				} catch (NumberFormatException nfe){}
			}
		}
	}

	/**
	* Check if player has a valid quantity of credits, in order to keep playing.
	* @Return True, if player has some credits, or have payed more, if needed.
	*/
	public boolean checkCredits(Scanner myInput){
		boolean FLAG = true;
		if (credits <= 0){
			System.out.print("> Oh NO! You don't HAVE ANY MORE CREDITS TO SPEND!" + 
				" Get SOME MORE and continue PLAYING!!\nBuy more credits: ");
			try {
				FLAG = payMoreCredits(Integer.parseInt(myInput.next()));
			} catch (IllegalArgumentException ipe){
				System.out.println(ipe.getMessage());
				FLAG = false;
			} catch (Exception e){
				System.out.println("> Bad news fool you!");
				FLAG = false;
			} finally {
				if (!FLAG)
					System.out.println("> Guess it's game over for you!" +
						" Feel free to come back, when you have more money!");
			}
		}else if (credits < initCredit/4){
			System.out.println("> Your credits are fairly low! Put some" +
				" more to WIN MORE MONEY!!\nType your bet: ");
			try {
				payMoreCredits(Integer.parseInt(myInput.next()));
			} catch (IllegalArgumentException ipe){
				System.out.println(ipe.getMessage());
			}
		}
		return FLAG;
	}

	/**
	* Force the player to do a valid bet before proceed. Can call putMoreCredits() if needed.
	* @Return True, whether the player did a valid bet (positive integer larger than 0). False otherwise.
	*/
	public int makeBet(Scanner myInput){
		int roundBet = 0;
		boolean AUX;
		do {
			AUX = false;
			try {
				System.out.print("\n> CREDITS AVAILABLE: " + 
					credits + "!! Type your BET for this round:\n> ");
				roundBet = betCredits(Integer.parseInt(myInput.next()));
				AUX = (roundBet >= 0);
			} catch (OutOfCredits ooc){
				System.out.print(ooc.getMessage());
				System.out.print("\n> Type how many CREDITS do you want:\n> ");
				try {
					payMoreCredits(Integer.parseInt(myInput.next()));
				} catch (IllegalArgumentException iae){
					System.out.println("\n> Guess somebody is sleepy tonight!");
				}
			} catch (IllegalArgumentException iae){
				System.out.println(iae.getMessage());
			} catch (Exception e){
				System.out.print("\n> Looks like you got nervous!" +
					" However, fear not! Try it again!\n> ");
			}
		} while(!AUX);
		return roundBet;
	}

	/**
	* Draw a full hand of cards.
	* @Return No return value.
	*/
	public void draw(Scanner myInput){
		for (int i = 0; i < numOfCards; ++i){
			try {
				myHand[i] = myDeck.draw();
			} catch (DeckRunOut dro){
				System.out.println("> Whe reached the point where the rubber" + 
					" meets the road, and there's no Cards to play anymore!" +
					"(Shuffling card deck...)");
				myDeck.reset();//Force reset.
				--i;//Draw again.
			}
		}	
	}

	/**
	* Call toString() method from myDeck.
	* @Override java.lang.toString()
	* @Return String with the card hand representation.
	*/
	@Override
	public String toString(){
		return myDeck.toString(myHand);
	}

	/**
	* Reset the game deck.
	* @Return No return value.
	*/
	public void reset(){
		myDeck.reset();
	}

	/**
	* Compute the current card hand value, and multiply that parameter to the player current bet.
	* Uses multithreading.
	* @Return Bet value multiplied by the fixed hand value.  
	*/
	public int computeThreads(int roundBet){
		return roundBet * myScore.compute(myHand);
	}

	/**
	* Compute the current card hand value, and multiply that parameter to the player current bet.
	* DO not uses multithreading.
	* @Return Bet value multiplied by the fixed hand value.  
	*/
	public int computeCommon(int roundBet){
		return roundBet * myPlacar.add(roundBet, Card.handValue(myHand));
	}

	/**
	* This is where the user interface take place.
	*/
	public static void main(String[] args) {
		Poker game = new Poker();
		Scanner myInput = new Scanner(System.in);
		boolean FLAG = true;
		int roundBet = 0, roundReward;

		while(FLAG){
			System.out.println("--------------------------------\n" + 
				"|      *** NEW ROUND! ***      |" + 
				"\n--------------------------------");
			
			//Bet section
			roundBet = game.makeBet(myInput);

			//Draw a full hand of cards cards
			game.draw(myInput);
			myInput.skip("\n");

			//Card change section
			game.redraw(myInput);

			System.out.println("> FINAL HAND:\n" + game.toString());
			//Reward calculus section(send hand to score module)
			roundReward = game.computeThreads(roundBet);
			//int roundRewardAux = game.computeCommon(roundBet);

			if(!game.payMoreCredits(roundReward))
				System.out.println("> Bad luck this time, you win nothing! Keep trying!");
			else
				System.out.println("You did win " + roundReward + 
					"!! (PRIZE OF " + (100*roundReward/roundBet) + "%!!)");

			//Ask-to-repeat section
			System.out.print("> Do you want to WIN MORE?!?! (type \'n\' to stop)\n> ");
			FLAG = (!myInput.next().toLowerCase().equals("n"));
			myInput.reset();

			//Steal-your-money section
			if (FLAG){
				//Reset deck for the new Round.
				game.reset();
				FLAG = game.checkCredits(myInput);
			} else System.out.println("> Well, well. Then, I guess it's end of the game for us, my friend!");
		}
		System.out.println("> Final score: " + game.howManyCredits());
		myInput.close();
	}
}
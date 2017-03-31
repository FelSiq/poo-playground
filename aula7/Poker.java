import java.util.Scanner;

/**
* This is the game main Class.
* @author A IME lost student.
*/
class Poker{
	static final int initCredit = 200;
	static final int numOfCards = 5;
	static final int changeTimes = 2; 
	private final Deck myDeck;
	private final Card [] myHand;
	private int credits;

	Poker(){
		myHand = new Card[numOfCards];
		myDeck = new Deck();
		credits = initCredit;
	}

	private boolean payMoreCredits(int moreCredits) throws IllegalArgumentException{
		if (moreCredits < 0)
			throw new IllegalArgumentException("What? No negative values allowed (keep playing to do so)!!");
		credits += moreCredits;
		return (moreCredits > 0);
	}

	private boolean betCredits(int howMany) throws IllegalArgumentException, OutOfCredits{
		if (howMany < 0)
			throw new IllegalArgumentException("Are you SERIOUS? You CAN NOT bet negative values!");
		if (howMany > credits)
			throw new OutOfCredits("You DO NOT have this much of credits! Put more money to proceed!");
		credits -= howMany;
		return (howMany >= 0);
	}

	public static void main(String[] args) {
		Poker game = new Poker();
		Scanner myInput = new Scanner(System.in);
		String [] stringAux;
		boolean FLAG = true, AUX = false;
		int roundBet = 0;

		while(FLAG){
			System.out.println("--------------------------------\n|      *** NEW ROUND! ***      |\n--------------------------------");
			
			//Bet section
			do {
				AUX = false;
				try {
					System.out.print("\n> CREDITS AVAILABLE: " + game.credits + "!! Type your BET for this round:\n> ");
					AUX = game.betCredits(Integer.parseInt(myInput.next()));
				} catch (OutOfCredits ooc){
					System.out.print(ooc.getMessage());
					System.out.print("\n> Type how many CREDITS do you want:\n> ");
					try {
						game.payMoreCredits(Integer.parseInt(myInput.next()));
					} catch (IllegalArgumentException iae){
						System.out.println("\n> Guess somebody is sleepy tonight!");
					}
				} catch (IllegalArgumentException iae){
					System.out.println(iae.getMessage());
				} catch (Exception e){
					System.out.print("\n> Looks like you got nervous! However, fear not! Try it again!\n> ");
				}
			} while(!AUX);

			for (int i = 0; i < numOfCards; ++i){
				try {
					game.myHand[i] = game.myDeck.draw();
				} catch (DeckRunOut dro){
					System.out.println("> Whe reached the point where the rubber meets the road, and there's no Cards to play anymore.");
					game.myDeck.reset();//Force reset.
					--i;//Draw again.
				}
			}

			//Card show Section

			//Card change section
			for (int i = 0, aux; i < changeTimes; ++i){
				System.out.println(game.myDeck.toString(game.myHand));
				System.out.print("> Chance "+ (1 + i) +"/"+changeTimes+" for CHANGE your hand.\n> Type the index of the cards, or press ENTER to continue.\n> ");
				
				stringAux = myInput.next().split(" ");


				for(int j = 0; j < stringAux.length; ++j){
					try {
						aux = Integer.parseInt(stringAux[j]);
						game.myDeck.giveCardBack(game.myHand[aux]);
						game.myHand[aux] = game.myDeck.draw();
					} catch (ArrayIndexOutOfBoundsException aioob){
						System.out.println("That was a invalid index!");
					} catch (DeckRunOut dro){
						System.out.println("No more cards available.");
					} catch (NumberFormatException nfe){}
				}
			}
			System.out.println("> FINAL HAND:");
			System.out.println(game.myDeck.toString(game.myHand));
			//Reward calculus section

			//Repeat section
			System.out.print("> Do you want to WIN MORE?!?! (type \'n\' to stop)\n> ");
			FLAG = (!myInput.next().toLowerCase().equals("n"));

			//Steal-your-money section
			if (FLAG){
				//Reset deck for the new Round.
				game.myDeck.reset();
				if (game.credits <= 0){
					System.out.print("> Oh NO! You don't HAVE ANY MORE CREDITS TO SPEND! Get SOME MORE and continue PLAYING!!\nBuy more credits: ");
					try {
						FLAG = game.payMoreCredits(Integer.parseInt(myInput.next()));
					} catch (IllegalArgumentException ipe){
						System.out.println(ipe.getMessage());
						FLAG = false;
					} catch (Exception e){
						System.out.println("> Bad news fool you!");
						FLAG = false;
					} finally {
						if (!FLAG)
							System.out.println("> Guess it's game over for you! Feel free to come back, when you have more money!");
					}
				}else if (game.credits < 50){
					System.out.println("> Your credits are fairly low. Put some more to WIN MORE MONEY!!\nType your bet: ");
					try {
						game.payMoreCredits(Integer.parseInt(myInput.next()));
					} catch (IllegalArgumentException ipe){
						System.out.println(ipe.getMessage());
					}
				}
			} else System.out.println("> Well, well. Then, I guess it's end of game, my friend!");
		}
		myInput.close();
	}
}
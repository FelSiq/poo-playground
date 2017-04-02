package PokerPack;
import java.util.Random;
import java.util.Calendar;
import java.util.Arrays;


/**
* Auxiliary class for Poker main class.
* @import java.util.Random, java.util.Calendar, java.util.Arrays
* @author Felipe Alves Siqueira 9847706
*/
public class Deck{
	static final byte maxCardNum = 52;
	private final byte cardNum;
	private Card lastCard; 
	private boolean [] cardControl;

	/**
	* Standard class constructor. Number of cards will be 52.
	*/
	public Deck(){
		cardNum = 52;
		cardControl = new boolean[cardNum];
		this.reset();
	}

	/**
	* Init a Deck with the given number of cards, n > 0. However, there are some restrictions for n:
	*	1. n must be in range (0, 52].
	*	2. n must be a natural multiple of 13 (because all suits must contain all card values)
	* Both cases must be respected, or else, a IllegalArgumentException is thrown.
	* @Throws IllegalArgumentException.
	*/
	public Deck(int newCardNum) throws IllegalArgumentException{
		if (0 < newCardNum && (newCardNum % Card.valueNum) == 0 && newCardNum <= maxCardNum){
			cardNum = (byte) newCardNum;
			cardControl = new boolean[cardNum];
		}
		throw new IllegalArgumentException("Number of cards must be a positive integer, and a multiple of " + Card.valueNum + ", and less or equal than " + maxCardNum + ".");
	}

	/**
	* Check if deck has cards or it is empty.
	* @Return True, if at least one card is left on the Deck. False otherwise.
	*/
	private boolean hasCards(){
		for (boolean b : cardControl) 
			if (b) return true;
		return false;
	}

	/**
	* Draw a random Card from Deck, from all remaining possibilities. 
	* @Return a Card class object, with random Naipe and Value, always different from all others drew previously. 
	* @Throws DeckRunOut, if no card is available to draw.
	*/
	public Card draw() throws DeckRunOut{
		if (hasCards()){
			Random myRand = new Random(Calendar.getInstance().getTimeInMillis());
			int newIndex;
			try {
				do newIndex = myRand.nextInt(maxCardNum);
				while (!cardControl[newIndex]);
				//36
				//2 (suit) and 10 (value) --> 2*13 + 10 = 36
				cardControl[newIndex] = false;
				lastCard = new Card((newIndex / Card.valueNum), (newIndex % Card.valueNum));
				
				return lastCard;
			} catch (Exception e){
				System.out.println("Something went wrong with random seed generation or card creation.");
				e.printStackTrace();
				return null;
			}
		} else throw new DeckRunOut();
	}

	/**
	* Turn all deck cards drew available once again.
	* @Return no return value.
	*/	
	public void reset(){
		java.util.Arrays.fill(cardControl, true);
	}

	/**
	* Put a card back to the Deck.
	* @Return no return value.
	*/
	public void giveCardBack(Card c) {
		cardControl[c.getValue() + Card.valueNum*(c.getSuit())] = true;
	}

	/**
	* Return a concatenated single String of toString function of a full hand of Cards.
	* @Return String with all cards of the given Hand, concatenated.
	*/
	public String toString(Card [] myHand){
		String result = new String(), aux;
		String [][] slices = new String[myHand.length][9];

		for (int i = 0; i < myHand.length; ++i){
			aux = myHand[i].toString();
			for (int j = 0; j < 9; ++j)
				slices[i] = aux.split("\n");
		}

		for (int i = 0; i < 9; result += "\n", ++i)
			for (int j = 0; j < myHand.length; ++j)
				result += " " + slices[j][i];


		for (int i = 0; i < myHand.length; ++i)
			result += ("      [" + i + "]      ");

		return result + "\n";
	}

	/**
	* @Debug This method exists only for DEBUG purposes.
	*/
	public static void main(String[] args) {
		//Debug hell
		Deck myDeck = new Deck();
		try {
			for (int i = 0; i <= Deck.maxCardNum; ++i)
				System.out.println(myDeck.draw().toString());
		} catch (DeckRunOut dro){
			System.out.println();
		} catch (Exception e){	
			System.out.println("No idea of what actually happened (jk).");
			e.printStackTrace();
		}
	}
}
import java.util.Random;
/**
* Auxiliary class for Poker main class.
* @import java.util.Random
* @author Guess.
*/
class Deck{
	static final byte maxCardNum = 52;
	static final byte cardNum;
	private Card lastCard; 
	private boolean [] cardControl;

	/**
	* Standard class constructor. Number of cards will be 52.
	*/
	public Deck(){
		cardControl = new boolean[cardNum];
		cardNum = 52;
	}

	/**
	* Init a Deck with the given number of cards, n > 0. However, there are some restrictions for n:
	*	1. n must be in range (0, 52].
	*	2. n must be a natural multiple of 13 (because all naipes must contain all card values)
	* Both cases must be respected, or else, a IllegalArgumentException is thrown.
	* @Throw IllegalArgumentException.
	*/
	public Deck(int cardNum) throws IllegalArgumentException{
		if (0 < cardNum && (cardNum % 13) == 0 && cardNum <= maxCardNum)
			cardControl = new boolean[cardNum];
		throw new IllegalArgumentException("Number of cards must be a positive integer, and a multiple of 13, and less or equal than " + maxCardNum + ".");
	}

	/**
	* Draw a random Card from Deck, from all remaining possibilities. 
	* @Return a Card class object, with random Naipe and Value, always different from all others drew previously. 
	* @Throw DeckRunOut, if no card is available to draw.
	*/
	public Card draw() throws DeckRunOut{

	}

	/**
	* Turn all deck cards drew available once again. Do not shuffle then, however.
	* @return no return value.
	*/	
	public void reset(){

	}

	/**
	* Shuffles deck.
	* @return no return value.
	*/	
	public void shuffle(){

	}

	public static void main(String[] args) {
		//Debug hell
	}
}
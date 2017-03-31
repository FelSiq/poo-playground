package PokerPack;
import java.util.Random;

/**
* Auxiliary class of Deck and Poker main class. 
* @import java.util.Random
* @author Felipe Alves Siqueira 9847706
*/
public class Card{
	static final byte suitNum = 4;
	static final byte valueNum = 13;
	private int suit;
	private int value;

	/**
	* Getter.
	* @return Card suit.
	*/
	public int getSuit(){return suit;}

	/**
	* Getter.
	* @return Card value.
	*/
	public int getValue(){return value;}

	/**
	* Create a card, with a suit ranging from 0 to 3 (no semantic value imposed, at first) 
	* and a value equals to newValue (again, no semantic imposed).
	* @Throws IllegalArgumentException, if NewNaipe is out of [0, 4) range and/or newValue is out of [0, 13).
	*/
	public Card(int newNaipe, int newValue) throws IllegalArgumentException{
		if (newNaipe >= 0 && newNaipe < suitNum){
			if (newValue >= 0 && newValue < valueNum){
				suit = newNaipe;
				value = newValue;
			}else throw new IllegalArgumentException("The card Value must be a integer on range, [0, " + valueNum + ").");
		}else throw new IllegalArgumentException("The card Naipe must be a integer, [0, " + suitNum + ").");
	}

	/**
	* Return a string with current the card status representation (value and suit).
	* @Throws IllegalArgumentException, if NewNaipe is out of [0, 4) range and/or newValue is out of [0, 13).
	* @Override java.util.lang.toString()
	*/
	@Override
	public String toString(){
		String result = "--------------\n";

		switch (value) {
			case (valueNum - 5): result += "| 10         |\n"; break;
			case (valueNum - 4): result += "| J          |\n"; break;
			case (valueNum - 3): result += "| Q          |\n"; break;
			case (valueNum - 2): result += "| K          |\n"; break;
			case (valueNum - 1): result += "| A          |\n"; break;
			default: result += "| "+ (value + 2) +"          |\n"; break;
		}

		result += "|            |\n|            |\n";

		switch (suit){
			case 0: result += "|    CLUB    |\n"; break;
			case 1: result += "|    SPADE   |\n"; break;
			case 2: result += "|    HEART   |\n"; break;
			case 3: result += "|   DIAMOND  |\n"; break;
			default: result += "|            |\n"; break;
		}

		result += "|            |\n|            |\n";

		switch (value) {
			case (valueNum - 5): result += "|         10 |\n"; break;
			case (valueNum - 4): result += "|          J |\n"; break;
			case (valueNum - 3): result += "|          Q |\n"; break;
			case (valueNum - 2): result += "|          K |\n"; break;
			case (valueNum - 1): result += "|          A |\n"; break;
			default: result += "|          "+ (value + 2) +" |\n"; break;
		}
		result += "--------------\n";
		return result;
	};
}
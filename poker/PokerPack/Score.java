package PokerPack;
import java.lang.Thread;
import java.util.Arrays;

/*
* 				ISTO É SÓ UM EXPERIMENTO DE MULTITHREADING!
*/

/**
* Auxiliary class, made for the main class of Poker game.
* @imports java.util.Scanner (for input processing).
* @package PokerPackage, java.lang.Thread, java.util.Arrays
* @author  Felipe Alves Siqueira (9847706)
*/

public class Score{
	private final byte THREAD_NUM = 4;

	private class ScoreComputer implements Runnable{
		final Card [] myHand;
		final int [] result;
		final int threadID;

		static final int MULT_TWOPAIRS = 1;
		static final int MULT_TRIO = 2;
		static final int MULT_STRAIGHT = 5;
		static final int MULT_FLUSH = 10;
		static final int MULT_FULLHAND = 20;
		static final int MULT_QUADRA = 50;
		static final int MULT_SFLUSH = 100;
		static final int MULT_RSFLUSH = 200;

		private int [] requestFreqVec(Card [] myHand){
			int [] freqVec = new int[Card.valueNum];
			for (int i = 0; i < myHand.length; ++i)
				++freqVec[myHand[i].getValue()];
			return freqVec;
		}

		private boolean checkFreq(Card [] myHand, int val, int quant){
			int [] freqVec = requestFreqVec(myHand);
			int counter = 0;
			for (int i : freqVec)
				counter += (i >= val ? 1 : 0);
			return (counter >= quant);
		}

		private boolean checkSeq(Card [] myOldHand){
			Card [] myHand = new Card[myOldHand.length];
			for (int i = 0; i < myHand.length; ++i)
				myHand[i] = myOldHand[i]; 
			//Arrays.sort(myHand);
			boolean bubbleFlag = true;
			for (int i = 1; bubbleFlag && i < myHand.length; bubbleFlag = false, ++i)
				for (int j = 1; j < (myHand.length - 1); ++j)
					if (myHand[j].getValue() > myHand[j + 1].getValue()){
						Card aux = myHand[j];
						myHand[j] = myHand[j + 1];
						myHand[j + 1] = aux;
						bubbleFlag = true;
					}
			boolean FLAG = true;
			for (int i = 1; FLAG && i < myHand.length; ++i)
				FLAG = ((myHand[0].getValue() + i) == myHand[i].getValue());
			return FLAG;
		}

		private boolean checkSuit(Card [] myHand){
			int counter = 0;
			for (Card c : myHand)
				counter += (c.getSuit() == myHand[0].getSuit() ? 1 : 0);
			return (counter >= myHand.length);
		}

		private int checkTwoPairs(Card [] myHand){
			return (checkFreq(myHand, 2, 2) ? 1 : 0);
		}

		private int checkTrio(Card [] myHand){
			return (checkFreq(myHand, 3, 1) ? 1 : 0);
		}

		private int checkStraight(Card [] myHand){
			return (checkSeq(myHand) && !checkSuit(myHand) ? 1 : 0);
		}

		private int checkFlush(Card [] myHand){
			return (checkSuit(myHand) ? 1 : 0);
		}

		private int checkFullHand(Card [] myHand){
			return (checkTwoPairs(myHand) + checkTrio(myHand))/2;
		}

		private int checkQuadra(Card [] myHand){
			return (checkFreq(myHand, 4, 1) ? 1 : 0);
		}

		private int checkSFlush(Card [] myHand){
			return (((checkStraight(myHand) >= 1 ? true : false) && checkSuit(myHand)) ? 1 : 0);
		}

		private int checkRSFlush(Card [] myHand){
			return (checkSeq(myHand) && (myHand[0].getValue() >= 10) && checkSuit(myHand) ? 1 : 0);
		}

		
		@Override
		public void run(){
			switch (threadID){
				case 0: result[0] += MULT_TWOPAIRS * checkTwoPairs(myHand); break;
				case 1: result[1] += MULT_TRIO * checkTrio(myHand); break;
				case 2: result[2] += MULT_STRAIGHT * checkStraight(myHand); break;
				case 3: result[3] += MULT_FLUSH * checkFlush(myHand); break;
			}
			switch (threadID){
				case 0: result[4] += MULT_FULLHAND * checkFullHand(myHand); break;
				case 1: result[5] += MULT_QUADRA * checkQuadra(myHand); break;
				case 2: result[6] += MULT_SFLUSH * checkSFlush(myHand); break;
				case 3: result[7] += MULT_RSFLUSH * checkRSFlush(myHand); break;
			}
		}

		ScoreComputer(Card [] refMyHand, int refThreadID, int [] refResult){
			//requestFreqVec(refMyHand);
			myHand = refMyHand;
			threadID = refThreadID;
			result = refResult;
		}
	}

	/**
	* Compute the reward for your current hand, with a ((needlessly)) multi-thread approach.
	* @Throws NullPointerException, is myHand is a null pointer.
	* @Return The respective reward.
	*/
	public synchronized int compute(Card [] myHand) throws NullPointerException{
		if (myHand == null)
			throw new NullPointerException("Your hand does not exists?!");
		Thread [] myThreads = new Thread[THREAD_NUM];

		int [] result = new int[8];
		
		for (byte i = 0; i < myThreads.length; ++i){
			ScoreComputer sc = new ScoreComputer(myHand, i % myThreads.length, result);
			myThreads[i] = new Thread(sc);
			myThreads[i].start();
		}

		for (Thread t : myThreads)
			try {t.join();}
			catch (InterruptedException ie){
				System.out.println("Something went wrong on joining Threads.");
			}

		for (int i = (result.length - 1); i >= 0; --i){
			if (result[i] > 0)
				return result[i];
		}
		return 0;
	}

	public static void main(String[] args) {
		//Debug hell
		Score sc = new Score();

		Card [] myHand = new Card[5];
		Deck deck = new Deck();
		for (int i = 0; i < 5; ++i)
			try {
				myHand[i] = deck.draw();
			} catch (DeckRunOut e){}

		System.out.println(deck.toString(myHand));
		System.out.println(sc.compute(myHand));
	}
}
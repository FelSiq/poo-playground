package PokerPack;
import java.lang.Thread;

/*
* 				ISTO É SÓ UM EXPERIMENTO DE MULTITHREADING!
*/

/**
* Auxiliary class, made for the main class of Poker game.
* @imports java.util.Scanner (for input processing).
* @package PokerPackage, java.lang.Thread
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

		private int checkTwoPairs(Card [] myHand){
			System.out.println("1");
			return 0;
		}

		private int checkTrio(Card [] myHand){
			System.out.println("2");
			return 0;
		}

		private int checkStraight(Card [] myHand){
			System.out.println("3");
			return 0;
		}

		private int checkFlush(Card [] myHand){
			System.out.println("4");
			return 0;
		}

		private int checkFullHand(Card [] myHand){
			System.out.println("5");
			return 0;
		}

		private int checkQuadra(Card [] myHand){
			System.out.println("6");
			return 0;
		}

		private int checkSFlush(Card [] myHand){
			System.out.println("7");
			return 0;
		}

		private int checkRSFlush(Card [] myHand){
			System.out.println("8");
			return 0;
		}

		@Override
		public void run(){
			switch (threadID){
				case 0: result[0] = MULT_TWOPAIRS * checkTwoPairs(myHand); break;
				case 1: result[0] = MULT_TRIO * checkTrio(myHand); break;
				case 2: result[0] = MULT_STRAIGHT * checkStraight(myHand); break;
				case 3: result[0] = MULT_FLUSH * checkFlush(myHand); break;
			}
			switch (threadID){
				case 0: result[0] = MULT_FULLHAND * checkFullHand(myHand); break;
				case 1: result[0] = MULT_QUADRA * checkQuadra(myHand); break;
				case 2: result[0] = MULT_SFLUSH * checkSFlush(myHand); break;
				case 3: result[0] = MULT_RSFLUSH * checkRSFlush(myHand); break;
			}
		}

		ScoreComputer(Card [] refMyHand, int refThreadID, int [] refResult){
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

		int [] result = new int[1];
		
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
		return result[0];
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
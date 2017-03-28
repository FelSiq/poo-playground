package BozoPackage;
import java.util.Scanner;

/**
* Auxiliary class, made for the main class of Bozo' game. Rolls dice.
* @imports java.util.Scanner (for input processing).
* @package BozoPackage
* @author A barkfull dog.
*/

public class Score{
	private int [] scoreBoard;
	
	private final int boardSize = 10;

	//Constant section
	static final int DICE_SIDENUM = 6;
	static final int INVALID = (-1); 
	static final int POS_FULLHAND = 1;
	static final int POS_SEQUENCE = 4;
	static final int POS_QUADRA = 7;
	static final int POS_FIVEFLUSH = 9;

	static final int REWARD_FULLHAND = 15;
	static final int REWARD_SEQUENCE = 20;
	static final int REWARD_QUADRA = 30;
	static final int REWARD_FIVEFLUSH = 40;

	//Constructor
	public Score(){
		scoreBoard = new int[boardSize];
		for (int i = 0; i < boardSize; ++i)
			scoreBoard[i] = INVALID;
	}

	//Methods
	/**
	* Get a integer index of an position, on Scoreboard, and turns it to the right value relative to a frequency array. 
	* @throws No exceptions.
	* @return Correspondent integer index of the frequency array.
	*/
	private int translatePos(int pos){
		switch(pos){
			case 0: return 0;
			case 2: return 3;
			case 3: return 1;
			case 5: return 4;
			case 6: return 2;
			case 8: return 5;
			default: return INVALID;
		}
	}

	/**
	* Check if a determined position on the Scoreboard is equals do INVALID value.
	* @throws IndexOutOfBoundsException, is pos is range [INT_MIN, 0)U[scoreBoard.length, INT_MAX].
	* @return whether asked position is unmodified or is it.
	*/
	public boolean checkPosition(int pos) throws IndexOutOfBoundsException{
		if (pos >= 0 && pos < scoreBoard.length)
			return (scoreBoard[pos] == INVALID);
		else throw new IndexOutOfBoundsException(pos + ": This is not a valid position on score board.");
	}

	/**
	* Get the number of appearances of each possible value on data array.
	* @throws No exceptions.
	* @return Integer array, with number of appearances of each possible value.
	*/
	private int [] getFreqs(int [] data){
		int freqs[] = new int [DICE_SIDENUM];

		for (int m : data)
			++freqs[m - 1];

		return freqs;
	}

	/**
	* Set the correspondent value (i * freq_i) on the choosen position.
	* @throws No exceptions.
	* @return No return value.
	*/
	private void addScoreDefault(int pos, int [] freq){
		final int newPos = translatePos(pos);
		scoreBoard[pos] = freq[newPos] * (1 + newPos);
	}

	/**
	* Set either 0 or REWARD_FIVEFLUSH (constant value) on the POS_FIVEFLUSH position of scoreboard,
	* if conditions are met (5 dice equals setup).
	* @throws No exceptions.
	* @return No return value.
	*/
	private void addScoreFiveflush(int [] freq){
		for (int i : freq)
			scoreBoard[POS_FIVEFLUSH] += REWARD_FIVEFLUSH * (i == 5 ? 1 : 0);
	}

	/**
	* Set either 0 or REWARD_QUADRA (constant value) on the POS_QUADRA position of scoreboard,
	* if conditions are met (4 dice equals setup).
	* @throws No exceptions.
	* @return No return value.
	*/
	private void addScoreQuadra(int [] freq){
		for (int i : freq)
			scoreBoard[POS_QUADRA] += REWARD_QUADRA * (i == 4 ? 1 : 0);
	}

	/**
	* Set either 0 or REWARD_SEQUENCE (constant value) on the POS_SEQUENCE position of scoreboard,
	* if conditions are met (1-2-3-4-5 or 2-3-4-5-6 dice setup).
	* @throws No exceptions.
	* @return No return value.
	*/
	private void addScoreSeq(int [] freq){
		int seq0 = 0, seq1 = 0;
		for (int i = 0; i < (Math.min(DICE_SIDENUM, freq.length) - 1); ++i){
			seq0 += (freq[i] >= 1 ? 1 : 0);
			seq1 += (freq[i + 1] >= 1 ? 1 : 0);
		}
		scoreBoard[POS_SEQUENCE] += REWARD_SEQUENCE * ((seq0 >= 5 || seq1 >= 5) ? 1 : 0);
	}

	/**
	* Set either 0 or REWARD_FULLHAND (constant value) on the POS_FULLHAND position of scoreboard,
	* if conditions are met (3 equals - 2 equals dice setup).
	* @throws No exceptions.
	* @return No return value.
	*/
	private void addScoreFullhand(int [] freq){
		boolean FLAG = true;
		for (int i = 0; FLAG && i < Math.min(DICE_SIDENUM, freq.length); ++i)
			for (int j = 0; j < Math.min(DICE_SIDENUM, freq.length); ++j)
				if (i != j && freq[i] >= 3 && freq[j] >= 2){
					FLAG = false;
					j = freq.length;
					scoreBoard[POS_FULLHAND] += REWARD_FULLHAND;
				}

	}

	/**
	* Add a new entry on the Scoreboard. Position vality must be previously checked.
	* @throws IndexOutOfBoundsException, is pos is range [INT_MIN, 0)U[scoreBoard.length, INT_MAX].
	* @return No return value.
	*/
	public void add(int pos, int [] myData) throws IndexOutOfBoundsException{
		if (pos >= 0 && pos < scoreBoard.length){
			//Clean up the position
			scoreBoard[pos] = 0;

			int freqArray[] = getFreqs(myData);

			switch(pos){
				case POS_FULLHAND: 
					addScoreFullhand(freqArray);
					break;
				case POS_SEQUENCE: 
					addScoreSeq(freqArray);
					break;
				case POS_QUADRA:
					addScoreQuadra(freqArray);
					break;
				case POS_FIVEFLUSH:
					addScoreFiveflush(freqArray);
					break;
				default: 
					addScoreDefault(pos, freqArray);
					break;
			}
		} else throw new IllegalArgumentException(pos + ": This is not a valid position on score board.");
	}

	/**
	* Return the currently stored score on the given position. 
	* If there's no stored value, returns 0 instead. 
	* @throws IndexOutOfBoundsException, if pos is not in [0, boardSize) range.
	* @return Stored value in given position, if any. 0 otherwise. 
	*/
	public int getScorePosition(int pos) throws IndexOutOfBoundsException{
		if (pos >= 0 && pos < boardSize)
			return Math.max(scoreBoard[pos], 0);
		throw new IndexOutOfBoundsException(pos + ": must be in range [0, " + boardSize + ").");
	}

	/**
	* Sum up all valid current Scoreboard entries, and return that value.
	* @throws No exceptions.
	* @return Integer Sum of all valid Scoreboard regions.
	*/
	public int getScore(){
		int totalScore = 0;
		for (int i : scoreBoard)
			totalScore += (i != INVALID ? i : 0);
		return totalScore;
	}

	/**
	* Return a stylized Scoreboard String
	* @override java.lang.String.toString()
	* @throws No exceptions.
	* @return A fugly scoreboard character String.
	*/
	@Override public String toString(){
		String myString = new String();
		for (int i = 0; i < boardSize; ++i)
			myString += "\t[" + (1 + i) + "]: " + (scoreBoard[i] >= 0 ? scoreBoard[i] : "  ") + (((1 + i) % 3 == 0) ? "\n" : "") + (i == (boardSize - 2) ? "\t" : "");
		return myString;
	}

	public static void main(String[] args) {
		//Debug hell
		int [] ref = null;
		RollDice rd = new RollDice(5);
		Score sc = new Score();
		System.out.println("First print (upon declaration):\n" + sc.toString());

		ref = rd.roll();

		System.out.print("Indexes returned by the first roll: ");
		for (int i : ref)
			System.out.printf("%d ", i);
		System.out.println("");

		sc.add(0, ref);
		System.out.println("Second print (upon first add):\n" + sc.toString());

		sc.add(5, ref);
		System.out.println("Third print (upon second add):\n" + sc.toString());

		ref = rd.roll();

		System.out.print("Indexes returned by the second roll: ");
		for (int i : ref)
			System.out.printf("%d ", i);
		System.out.println("");

		ref[0] = 3;
		ref[1] = 2;
		ref[2] = 4;
		ref[3] = 5;
		ref[4] = 6;

		sc.add(4, ref);
		System.out.println("Last print (upon last add):\n" + sc.toString());

		System.out.println("Final score: " + sc.getScore());
	}
}
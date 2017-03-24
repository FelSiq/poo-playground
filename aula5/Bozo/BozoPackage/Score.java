package BozoPackage;
import java.util.Scanner;
import java.util.Arrays;

public class Score{
	private int [] scoreBoard;
	private final byte boardSize = 10;
	private final byte INVALID = (-1); 
	private final byte POS_FULLHAND = 2;
	private final byte POS_SEQUENCE = 5;
	private final byte POS_QUADRA = 8;
	private final byte POS_FIVEFLUSH = 10;

	private final byte REWARD_FULLHAND = 15;
	private final byte REWARD_SEQUENCE = 20;
	private final byte REWARD_QUADRA = 30;
	private final byte REWARD_FIVEFLUSH = 40;

	//Constructor
	public Score(){
		scoreBoard = new int[boardSize];
		for (int i = 0; i < boardSize; ++i)
			scoreBoard[i] = INVALID;
	}

	//Methods
	private int translatePos(int pos){
		switch(pos){
			case 1: return 1;
			case 4: return 2;
			case 7: return 3;
			case 3: return 4;
			case 6: return 5;
			case 9: return 6;
			default: return INVALID;
		}
	}

	public boolean checkPosition(int pos) throws IllegalArgumentException{
		if (pos > 0 && pos < scoreBoard.length)
			return (scoreBoard[pos] == INVALID);
		else throw new IllegalArgumentException("This is not a valid position on score board.");
	}

	private void addScoreDefault(int pos, int [] data){
		for (byte i = 0; i < data.length; ++i)
			if (translatePos(pos) == data[i])
				scoreBoard[pos] = (scoreBoard[pos] + 1) * data[i];
	}

	private void addScoreFiveflush(int [] data){
		byte i;
		for (i = 1; (i < data.length) && (data[i] == data[0]); ++i);
		if ((i >= data.length))
			scoreBoard[POS_FIVEFLUSH] = REWARD_FIVEFLUSH;
	}

	private void addScoreQuadra(int [] data){
		Arrays.sort(data);
		int i = 0, counter = 0;
		if (data[0] != data[1])
			++i;

		while ((i < data.length) && (data[i] == data[0])){
			++counter;
			++i;
		}

		if (counter >= (data.length - 1))
			scoreBoard[POS_FIVEFLUSH] = REWARD_FIVEFLUSH;
	}

	private void addScoreSeq(int [] data){
		Arrays.sort(data);
		int i = data[0], max = (4 + i); 
		
		while (i < max && data[++i] == i);

		if (i == 4)
			data[POS_SEQUENCE] = REWARD_SEQUENCE;
	}

	private void addScoreFullhand(int [] data){

	}

	public void add(int pos, int [] data) throws IllegalArgumentException{
		if (pos > 0 && pos < scoreBoard.length){
			//Clean up the position
			scoreBoard[pos] = 0;
			
			int [] freqArray = getFreqs(data);

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
		} else throw new IllegalArgumentException("This is not a valid position on score board.");
	}

	public int getScore(){
		int totalScore = 0;
		for (int i : scoreBoard)
			if (i != INVALID)
				totalScore += i;
		return totalScore;
	}

	@Override
	public String toString(){
		return null;
	}

	public static void main(String[] args) {
		
	}
}
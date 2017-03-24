package BozoPackage;
import java.util.Scanner;
import java.util.Calendar;

public class RollDice{
	final private Die [] diceArray;
	final private Random myRandGen;
	final private int diceNum;
	final private byte SIDENUM_DIE = 6;

	{
		myRandGen = new Random();
	}

	//Constructor
	public RollDice(int newDiceNum) throws IllegalArgumentException{
		if (newDiceNum > 0){
			diceNum = newDiceNum;
			diceArray = new Die[diceNum];

			for (int i = 0; i < newDiceNum; ++i){
				diceArray[i] = new Die(SIDENUM_DIE);
				try {Thread.sleep(50);} 
				catch (Exception e){}
				diceArray[i].setSeed(Calendar.getInstance().getTimeInMillis());
			}
		} else throw new IllegalArgumentException("Number of dice must be higher than 0.");
	}

	public int [] roll(){
		int [] valArray = new int[diceNum];
		for (int i = 0; i < diceNum; ++i)
			valArray[i] = diceArray[i].roll();
		return valArray;
	}

	public int [] roll(boolean [] indexes) throws IllegalArgumentException{
		int [] valArray = new int[diceNum];
		if (indexes != null){

		} else throw new IllegalArgumentException("Null argument array.");
		return valArray;
	}

	public int [] roll(String indexes) throws IllegalArgumentException{
		int [] valArray = new int[diceNum];
		if (indexes != null){
			//Scanner to process the index string
			Scanner myScan = new Scanner(indexes);
			//Keep track of which dice are already rolled, to avoid cheating
			boolean [] indexFlag = new boolean [diceNum];
			//Boolean flag to avoid fugly break point on while loop.
			boolean FLAG = true;
			//Variable to keep the parsed index 
			int i;
			//Process index string
			while (FLAG && myScan.hasNext()){
				if (myScan.hasNextInt()){
					i = Integer.parseInt(myScan.next());
					try {
						if (!indexFlag[i]){
							valArray[i] = diceArray[i].roll();
							indexFlag[i] = true;
						} else System.out.println("Can't roll the same die twice in a roll.");
					} catch (IllegalArgumentException iae){
						throw new IllegalArgumentException("Index must be positive and smaller than die side number.");
					}
				} else FLAG = false;
			}
			myScan.close();
		} else throw new IllegalArgumentException("Null argument array.");
		return valArray;
	}

	@Override
	public String toString(){
		String result = new String(), aux;
		
		String [][] slices = new String[diceNum][Die.DRAW_LINES];

		for (int i = 0; i < diceNum; ++i){
			aux = diceArray[i].toString();
			for (int j = 0; j < Die.DRAW_LINES; ++j)
				slices[i] = aux.split("\n");
		}

		for (int i = 0; i < diceNum; result += "\n", ++i)
			for (int j = 0; j < Die.DRAW_LINES; ++j)
				result += " " + slices[j][i];


		for (int i = 0; i < diceNum; ++i)
			result += ("     " + i + "    ");

		return result + "\n";
	}

	public static void main(String[] args) {

	}
}	
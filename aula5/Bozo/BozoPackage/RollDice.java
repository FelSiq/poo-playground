package BozoPackage;
import java.util.Scanner;
import java.util.Calendar;

/**
* Auxiliary class, made for the main class of Bozo' game. Rolls dice.
* @imports java.util.Scanner (for input processing) and java.util.Calendar (for pseudo-random number generation)
* @package BozoPackage
* @author judging by the "toString" method, definitely is Bob Ross.
*/

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
			this.roll();
		} else throw new IllegalArgumentException("Number of dice must be higher than 0.");
	}

	//Methods
	/**
	* Rolls all dice, and return its respectives new values.
	* @throws No exception.
	* @return Integer array, with all current dice values.
	*/
	public int [] roll(){
		int [] valArray = new int[diceNum];
		for (int i = 0; i < diceNum; ++i)
			valArray[i] = diceArray[i].roll();
		return valArray;
	}

	/**
	* Rolls all dice flagged by the boolean input, and return its respectives new values.
	* @throws IllegalArgumentException, if indexes is null or smaller than diceNum. IndexOutOfBoundsException, if greater than diceNum.
	* @return Integer array, with all current dice values.
	*/
	public int [] roll(boolean [] indexes) throws IllegalArgumentException, IndexOutOfBoundsException{
		if (indexes != null){
			if (indexes.length == diceNum){
				int [] valArray = new int[diceNum];
				for (int i = 0; i < indexes.length; ++i)
					if (indexes[i])
						diceArray[i].roll();
				return valArray;
			} else {
				if (indexes.length > diceNum)
					throw new IndexOutOfBoundsException("Input vector is larger than expected (must be exactly " + diceNum + ").");
				else
					throw new IllegalArgumentException("Input vector is smaller than expected (must be exactly " + diceNum + ").");
			}
		} else throw new IllegalArgumentException("Null argument array.");
	}

	/**
	* Rolls all dice indexed by the (integer and strictly positive) numbers inside the given string, 
	* and return its respectives new values.
	* @throws IllegalArgumentException, if indexes are a null string.
	* @return Integer array, with all current dice values.
	*/
	public int [] roll(String indexes) throws IllegalArgumentException{
		if (indexes != null){
			int [] valArray = new int[diceNum];
			//Scanner to process the index string
			Scanner myScan = new Scanner(indexes);
			//Keep track of which dice are already rolled, to avoid cheating
			boolean [] indexFlag = new boolean [diceNum];
			for (int i = 0; i < diceNum; ++i){
				valArray[i] = diceArray[i].getVal();
				indexFlag[i] = false;
			}
			//Boolean flag to avoid fugly break point on while loop.
			boolean FLAG = true;
			//Variable to keep the parsed index 
			int i;
			//Process index string
			while (FLAG && myScan.hasNext()){
				if (myScan.hasNextInt()){
					i = Integer.parseInt(myScan.next());
					if (0 <= i && i < diceNum){
						if (!indexFlag[i]){
							valArray[i] = diceArray[i].roll();
							indexFlag[i] = true;
						} else System.out.println(i + ": Can't roll the same die twice in a roll.");
					} else System.out.println(i + ": This is not a valid die index.");
				} else FLAG = false;
			}
			myScan.close();
			return valArray;
		} else throw new IllegalArgumentException("Null argument array.");
	}

	/**
	* Concatenates all representation of dice, currently stored, in a single character string, plus some more aesthetics.
	* @override java.lang.String.toString()
	* @throws No exception.
	* @return A very-stylized character string. 
	* @disclaimer No graphic interfaces was harmed during confection of this method.
	*/
	@Override public String toString(){
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
		//Debug hell
		int [] ref = null;
		RollDice rd = new RollDice(5);


		System.out.println("First print (on declaration):\n" + rd.toString());

		ref = rd.roll();
		System.out.println("Second print (on first all-dice-roll):\n" + rd.toString());

		System.out.print("Indexes returned by the first roll: ");
		for (int i : ref)
			System.out.printf("%d ", i);
		System.out.println("");

		ref = rd.roll("1 2 3");
		System.out.println("Last print (on the last roll):\n" + rd.toString());

		System.out.print("Indexes returned by the second roll: ");
		for (int i : ref)
			System.out.printf("%d ", i);
		System.out.println("");
	}
}	
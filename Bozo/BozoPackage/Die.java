package BozoPackage;

/**
* Auxiliary class, made for the main class of Bozo' game. It's a n-sided die.
* @package BozoPackage
* @author A bored githubber.
*/

class Die{
	private Random myRandGen;
	private int myVal;
	
	private final int sideNum;
	static final int DRAW_LINES = 5;
	
	{
		myRandGen = new Random();
	}

	//Constructors
	public Die(){sideNum = 6;}

	public Die(int k) throws IllegalArgumentException {
		if (k <= 0) 
			throw new IllegalArgumentException("Number of sides must be higher than 0.");
		sideNum = k;
	}

	//Getter
	/**
	* Simple getter method, return this die current integer stored value.
	* @throws No exception.
	* @return Current stored integer value on this die, always ranging from [1, sideNum].
	*/
	public int getVal(){return myVal;}

	//Methods
	/**
	* Force a new seed for the die's pseudo-random number generator.
	* @throws IllegalArgumentException, if newSeed is less or equals to 0.
	* @return No return value.
	*/
	public void setSeed(long newSeed) throws IllegalArgumentException{
		if (newSeed <= 0) throw new IllegalArgumentException("Seed must be larger than 0.");
		myRandGen.setSeed(newSeed);
	}

	/**
	* Simulates a n-sided die roll. Replace its current value with a pseudo-random value, and then return it.  
	* @throws No exception.
	* @return Pseudorandom integer, in range [1, sideNum].
	*/
	public int roll(){
		myVal = myRandGen.getIntRand(1, sideNum);
		return myVal;
	}

	/**
	* Return a hardcoded string, constructed to represent a square six-sided die face.
	* @override java.lang.String.toString()
	* @throws UnsupportedOperationException, if die is not 1-to-6 sided.
	* @return Overly aesthetic-type String, representing a true die face, and some of my graphic interface inabilities.
	*/
	@Override public String toString() throws UnsupportedOperationException{
		switch (myVal){
			case 1: 
				return "+-------+\n|       |\n|   o   |\n|       |\n+-------+\n";
			case 2:
				return "+-------+\n| o     |\n|       |\n|     o |\n+-------+\n";
			case 3:
				return "+-------+\n| o     |\n|   o   |\n|     o |\n+-------+\n";
			case 4:
				return "+-------+\n| o   o |\n|       |\n| o   o |\n+-------+\n";
			case 5:
				return "+-------+\n| o   o |\n|   o   |\n| o   o |\n+-------+\n";
			case 6:
				return "+-------+\n| o   o |\n| o   o |\n| o   o |\n+-------+\n";
			default: 
				throw new UnsupportedOperationException("This method only works with a 1 to 6 sided die. (This one has " + sideNum + ")");
		} 
	}
}
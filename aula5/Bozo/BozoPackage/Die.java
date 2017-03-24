package BozoPackage;
class Die{
	private final int sideNum;
	private int myVal;
	private Random myRandGen;
	static final int DRAW_LINES = 5;
	
	//Initializer block
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
	public int getSide(){return sideNum;}

	//Methods
	public void setSeed(long newSeed){myRandGen.setSeed(newSeed);}

	public int roll(){
		myVal = myRandGen.getIntRand(1, sideNum);
		return myVal;
	}

	@Override
	public String toString() throws UnsupportedOperationException{
		switch (myVal){
			case 1: 
				return "---------\n|       |\n|   o   |\n|       |\n---------\n";
			case 2:
				return "---------\n| o     |\n|       |\n|     o |\n---------\n";
			case 3:
				return "---------\n| o     |\n|   o   |\n|     o |\n---------\n";
			case 4:
				return "---------\n| o   o |\n|       |\n| o   o |\n---------\n";
			case 5:
				return "---------\n| o   o |\n|   o   |\n| o   o |\n---------\n";
			case 6:
				return "---------\n| o   o |\n| o   o |\n| o   o |\n---------\n";
			default: throw new UnsupportedOperationException("This method only works with a 1 to 6 sided die.");
		} 
	}

	public static void main(String[] args) {
		
	}
}
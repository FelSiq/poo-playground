import java.util.Scanner;
import java.util.Calendar;

class Quux implements MyInterfaceA <Integer, Integer>, MyInterfaceB {
	private int randIndex = 0;
	private int seed;

	//Getter
	void setSeed(int newSeed){this.seed = newSeed;}

	//Constructor
	Quux(int newSeed){this.setSeed(newSeed);}
	//Overload
	Quux(){this.setSeed((int) Calendar.getInstance().getTimeInMillis());}

	public int getInteger(){
		this.randIndex = this.randIndex % maxLength;
		return this.pseudoNumbers[this.randIndex++] * this.seed;
		//return this.seed;
	}

	public double getDouble(){
		//[0, 1[
		return (double) getInteger() / (double) MAX_INTEGER;
	}

	public int getInteger(int max){
		return (getInteger() % (1 + max));
	}

	public static void main (String [] args){
		/*
		System.out.println("> please, insert the first random seed:");
		Scanner myInput1 = new Scanner(System.in);
		
		int mySeed = 0;

		
		boolean myFlag = true;
		
		do {
			myFlag = false; 
			try {
				mySeed = Integer.parseInt(myInput.next());
			} catch (Exception e){
				System.out.println("> invalid input, try again.");
				myFlag = true;
			}
		} while (myFlag);

		*/

		int counter = 0;
		Quux myInst1 = new Quux(15);
		Quux myInst2 = new Quux();

		int a = 0, b = 0;
		do {
			a = myInst1.getInteger(500);
			b = myInst2.getInteger(500);
			System.out.println(a + "/" + b);
			++counter;
		} while(a != b);

		System.out.println("number of iterations: " + counter);

		/* Teste
		for (int i = 0; i < 10; ++i)
			System.out.println(myInst.getInteger());
		for (int i = 0; i < 10; ++i)
			System.out.println(myInst.getInteger(80));
		for (int i = 0; i < 10; ++i)
			System.out.printf("%.4f\n", myInst.getDouble());
		

		myInput.close();
		*/
	}
}
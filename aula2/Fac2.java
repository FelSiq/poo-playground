import java.util.Scanner;

class Fac2{

	final long myVal;

	private long calc(long myNum){
		if (myNum <= 1) return 1;
		return myNum * calc(myNum - 1);
	}
	//Constructor
	public Fac(long myNum){
		this.myVal = calc(myNum);
	}

	//Overloads
	private int calc(int myNum){
		if (myNum <= 1) return 1;
		return myNum * calc(myNum - 1);
	}
	public Fac(int myNum){
		this.myVal = calc(myNum);
	}

	public void print(){
		System.out.println(myVal);
	}

	public static void main(String[] args) {
		Scanner myInput = new Scanner(System.in);
		long myNum = 0;
		boolean FLAG = true;

		do {
			FLAG = false;
			try {
				myNum = Integer.parseInt(myInput.next());
			} catch (Exception e) {
				System.out.println("The input must be a integer. Please try again: ");
				myInput.reset();
				FLAG = true;
			}
		} while (FLAG);

		Fac myFac = new Fac(myNum);
		myFac.print();

		myInput.close();		
	}
}
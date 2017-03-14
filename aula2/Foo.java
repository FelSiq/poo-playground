import java.util.Scanner;

class Foo{
	private static void calculate(final double[] coefs){
		//Declare variables and calculate delta
		final double sol0, sol1, 
		delta = (Math.pow(coefs[1], 2) - 4.0*coefs[0]*coefs[2]);;

		//Verify delta
		if (delta >= 0){
			//Calculate and print results
			sol0 = (-coefs[1] + Math.sqrt(delta))/(2.0 * coefs[0]);
			sol1 = (-coefs[1] - Math.sqrt(delta))/(2.0 * coefs[0]);
			System.out.printf("The solutions are:\n%.3f and %.3f.\n", sol0, sol1);
		} else System.out.println("There's no solution.");
	}

	private static double[] getCoefs(){
		//No args from command line, read from stdin then.
		Scanner myInput = new Scanner(System.in);
		
		final double[] coefs = new double[3];

		//Get coefs
		for(byte i = 0; i < 3; ++i){
			try {
				coefs[i] = Double.parseDouble(myInput.next());
			} catch (Exception e){
				System.out.println("Quadratic coefs must be a real number. Try again:");
				--i;
			}
		}

		//Close scanner to avoid mem. leak.
		myInput.close();

		return coefs;
	}

	public static void main (String [] args){
		//Function approach #3
		calculate(getCoefs());
	}
}
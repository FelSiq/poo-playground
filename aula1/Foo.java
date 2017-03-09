import java.util.Scanner;

/*
	There's 4 approaches of the quadratic equation problem in this source:
		1. Simple one, using a, b, c coefs, separately
		2. More complicated one, using a double[] coefs.
		3. Function call + doube[] coefs.
		4. Extension for getting the coefs directly from the command line (args)

		:)
*/

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

	private static double[] getCoefs(Scanner myInput){
		final double[] coefs = new double[3];

		//Get coefs
		for(byte i = 0; i < 3 && myInput.hasNextDouble(); ++i)
			coefs[i] = Double.parseDouble(myInput.next());

		return coefs;
	}

	//Overload on getCoefs
	private static double[] getCoefs(String[] args){
		final double[] coefs = new double[3];

		//Get coefs
		for(byte i = 0; i < Math.min(3, args.length); ++i)
			coefs[i] = Double.parseDouble(args[i]);

		//Warning, if insufficient number of args
		if (args.length < 3)
			System.out.println("warning: a quadratic equation needs three double parameters.");

		return coefs;
	}

	public static void main (String [] args){
		//Approach #4, get the values directly from the args, if possible
		if (args.length == 0){
			//No args from command line, read from stdin then.
			Scanner myInput = new Scanner(System.in);
			//Function approach #3
			calculate(getCoefs(myInput));

			//Close scanner to avoid mem. leak.
			myInput.close();
		} else {
			//If theres args from command line avaliable
			calculate(getCoefs(args));
		}

		//############################################
		//Simplier approaches

		//Lazy approach #0
		/*
		double sol0 = 0, sol1 = 0, delta = 0;
		double a = 0, b = 0, c = 0;

		a = Double.parseDouble(myInput.next());
		b = Double.parseDouble(myInput.next());
		c = Double.parseDouble(myInput.next());
		
		delta = (Math.pow(b, 2) - 4*a*c);
		if ((delta) >= 0){
			sol0 = (-b + Math.sqrt(delta))/2.0*a;
			sol1 = (-b - Math.sqrt(delta))/2.0*a;
			System.out.printf("The solutions are:\n%.3f and %.3f.\n", sol0, sol1);
		} else System.out.println("There's no solution.");
		*/
		
		//More complicated approach #1
		/*
		double sol0 = 0, sol1 = 0, delta = 0;
		double[] coefs = new double[3];

		for(int i = 0; i < 3 && myInput.hasNext(); ++i){
			//Not needed on Java
			//coefs[i] = 0;

			if (myInput.hasNextDouble())
				coefs[i] = Double.parseDouble(myInput.next());
		}

		delta = (Math.pow(coefs[1], 2) - 4.0*coefs[0]*coefs[2]);
		if (delta >= 0){
			sol0 = (-coefs[1] + Math.sqrt(delta))/(2.0 * coefs[0]);
			sol1 = (-coefs[1] - Math.sqrt(delta))/(2.0 * coefs[0]);
			System.out.printf("The solutions are:\n%.3f and %.3f.\n", sol0, sol1);
		} else System.out.println("There's no solution.");
		*/
		//############################################
	}
}
package BozoPackage;
import java.util.Calendar;

/**
* A little prototype of a Random class, which, of course, has methods to generate pseudorandom values.
* @imports java.util.Calendar (for pseudo-random number generation)
* @package BozoPackage
* @autor Santa Claus and its little elfs.
*/

class Random{
	private long seed;
	
	//Constructors
	Random(long newSeed) throws IllegalArgumentException{
		if (newSeed <= 0) 
			throw new IllegalArgumentException("Seed must be larger than 0.");
		seed = newSeed;
	}

	Random(){
		seed = Calendar.getInstance().getTimeInMillis();
	}

	/**
	* Generate a pseudorandom integer, from INT_MIN to INT_MAX.
	* @throws No exception.
	* @return Pseudorandom integer.
	*/
	public int getIntRand(){
		seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
		return (int) (seed >>> (16));
	}

	/**
	* Generate a pseudorandom integer, from 0 to maxVal (inclusive).
	* @throws No exception.
	* @return Pseudorandom integer, belonging to [0, maxVal].
	*/
	public int getIntRand(int maxVal){
		return Math.abs((getIntRand() % (maxVal + 1)));
	}

	/**
	* Generate a pseudorandom integer, from minVal to maxVal (both inclusive).
	* @throws No xception.
	* @return Pseudorandom integer, belonging to [minVal, maxVal].
	*/
	public int getIntRand(int minVal, int maxVal){
		return getIntRand(maxVal - minVal) + minVal;
	}

	/**
	* Generate a pseudorandom double in a range of [0.0, 1.0].
	* @throws No exceptions.
	* @return Pseudorandom double, belonging to [0.0, 1.0].
	*/
	public double getRand(){
		return Math.abs((double) getIntRand()/(1.0 * Integer.MAX_VALUE));
	}

	/**
	* Force a new (strictly positive) seed to this object.
	* @throws IllegalArgumentException, if seed is less or equals to 0.
	* @return No return value.
	*/
	public void setSeed(long newSeed) throws IllegalArgumentException {
		if (seed <= 0) throw new IllegalArgumentException("Seed must be larger than 0.");
		seed = newSeed;
	}
}
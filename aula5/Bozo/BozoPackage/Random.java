package BozoPackage;
import java.util.Calendar;

class Random{
	private long seed;

	public int getIntRand(){
		seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
		return (int) (seed >>> (16));
	}
	
	public int getIntRand(int maxVal){
		return Math.abs((getIntRand() % (maxVal + 1)));
	}

	public int getIntRand(int minVal, int maxVal){
		return getIntRand(maxVal - minVal) + minVal;
	}

	public double getRand(){
		return Math.abs((double) getIntRand()/(1.0 * Integer.MAX_VALUE));
	}

	public void setSeed(long newSeed){seed = newSeed;}

	//Constructors
	Random(long newSeed){
		seed = newSeed;
	}

	Random(){
		seed = Calendar.getInstance().getTimeInMillis();
	}

	public static void main(String[] args) {

	}
}
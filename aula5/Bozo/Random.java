class Random{
	long seed;

	public int get(){
		seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
		return (int) (seed >>> (16));
	}
	
	public int get(int maxVal){
		return Math.abs((get() % (maxVal + 1)));
	}

	public int get(int minVal, int maxVal){
		return get(maxVal - minVal) + minVal;
	}

	Random(long newSeed){
		seed = newSeed;
	}

	Random(){
		seed = 123;
	}
}
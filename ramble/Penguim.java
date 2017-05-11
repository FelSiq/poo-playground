public class Penguim extends Bird <Penguim> implements Comparable <Penguim>{
	final static private boolean AUTHENTIC_PENGUIM_SEAL = true;
	final public String penguimName;

	Penguim (String aNewPenguimIsBorn) {
		super();
		this.penguimName = aNewPenguimIsBorn;
	}

	public int compareTo (Penguim myPenguimBrother){
		return this.penguimName.compareTo(myPenguimBrother.penguimName);
	}

	public void makeNoise () {
		System.out.println("Arrrrgh");
	}
}
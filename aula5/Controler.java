class Controler extends TV{
	//Constructor
	Controler(String [] chanNames){
		super(chanNames);
	}
	
	//Getters
	public int vol(){return volume;}
	public String channel(){return super.channel();}

	//Methods
	public void chanSet(int chanId){super.chanSet(chanId);}
	public void chanNext(){super.chanNext();}
	public void chanPrev(){super.chanPrev();}
	public void volInc(){super.volInc();}
	public void volDec(){super.volDec();}

	public static void main(String[] args) {
		





		//Debug hell
		/*
		String [] chanNames = new String [10];
		for (int i = 0; i < 10; ++i)
			chanNames[i] = new String("MyChannel " + i);
		Controler myControl = new Controler (chanNames);

		for (int i = 0; i < 22; ++i){
			System.out.println(myControl.channel());
			myControl.chanNext();
		}

		System.out.println("------------------");

		for (int i = 0; i < 21; ++i){
			System.out.println(myControl.channel());
			myControl.chanPrev();
		}

		System.out.println("------------------");
		myControl.chanSet(8);
		System.out.println(myControl.channel());
		myControl.chanSet(9);
		System.out.println(myControl.channel());

		System.out.println(myControl.vol());
		for (int i = 0; i < 63; ++i)
			myControl.volInc();

		System.out.println(myControl.vol());
		for (int i = 0; i < 63; ++i)
			myControl.volInc();
		
		System.out.println(myControl.vol());
		for (int i = 0; i < 63; ++i)
			myControl.volDec();
		
		System.out.println(myControl.vol());
		for (int i = 0; i < 63; ++i)
			myControl.volDec();
		
		System.out.println(myControl.vol());
		*/

	}
}
import java.util.Scanner;

class Foo{
	private String item;
	private int k;

	//Initializer block
	{
		k = 0;
	}

	//Inner interface to lambda function (THIS IS A FUNCTIONAL INTERFACE)
	interface MyLambda <T1> {
		//Can have a single abstract method at time
		public T1 operation(T1 a, T1 b);
	}

	//Defining the function that uses the lambda function
	String myGeneralMethod (String a, String b, MyLambda <String> myFunction){
		return myFunction.operation(a, b);
	}

	//Constructor 1
	public Foo(){
		this.item = null;
	}

	//Constructor 2
	public Foo(String myString){
		this.item = new String(myString);
	}

	//Getter
	public String string(){
		return this.item;
	}

	public static void main(String[] args) {
		/*
		Foo myInst[] = new Foo[10];
		
		for (int i = 0; i < 10; ++i)
			myInst[i] = new Foo(Integer.toString(i) + " is this string number!");
		
		for (int i = 0; i < 10; ++i)
			System.out.println(myInst[i].string());*/

		//Defining my lambda function
		//MyLambda <String> add = (a, b) -> a + b
		MyLambda <String> myFunc = (
			(a,b) -> System.out.println("Hell on world!");
		);

		/*
		Scanner myInput = new Scanner(System.in);
		String vowels = new String("aeiou");
		int freq[] = new int[5];
		String myString = myInput.nextLine();

		String auxStr = myString.toLowerCase();
		for (int i = 0; i < auxStr.length(); ++i)
			for(int j = 0; j < vowels.length(); ++j)
				if (auxStr.charAt(i) == (vowels.charAt(j)))
					++freq[j];
			
		for (int i = 0; i < 5; ++i)
			System.out.println(vowels.charAt(i) + ": " + freq[i]);

		System.out.println(myString.toUpperCase());

		myInput.reset();
		myString = myInput.nextLine();

		System.out.println(auxStr.startsWith(myString) || auxStr.endsWith(myString));

		myInput.close();*/
	}
}
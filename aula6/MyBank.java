import java.util.Arrays;

class MyBank{
	public static void main(String[] args) {
		PersonalAccount [] imLazy = new PersonalAccount[10];

		for (int i = 0; i < 10; ++i){
			imLazy[i] = new PersonalAccount();
			try{
				imLazy[i].putMoney(100.0 * (11 - i));
			} catch (MoneyException e){}
		}

		for (int i = 0; i < 10; ++i){
			System.out.println(imLazy[i].balance());
		}

		Arrays.sort(imLazy);

		for (int i = 0; i < 10; ++i){
			System.out.println(imLazy[i].balance());
		}

	}
}
public class PersonalAccount implements BankAccount{
	final static personalLimit = 1500;

	protected double howManyMoney;

	public double getMoney(double howMany) throws MoneyException, LimitException, IllegalArgumentException{
		if (howMany > 0){
			if (howManyMoney >= howMany){
				if (howMany <= personalLimit){
					howManyMoney -= howMany;
					return howMany;
				} else throw new LimitException();
			} else throw new MoneyException();
		} else throw new IllegalArgumentException("Negative value not permitted.");
	}

	public void putMoney(double howMany) throws MoneyException, IllegalArgumentException{
		if(howMany > 0) 
			howManyMoney += howMany; 
		throw new IllegalArgumentException("Negative or zero value not permitted.");
	}

	public double balance(){
		return howManyMoney;
	}

	PersonalAccount(){
		howManyMoney = 0.0;
	}

	public static void main(String[] args) {
		//Debug hell
		PersonalAccount pa = new PersonalAccount();

		try {
			pa.putMoney(1900);
			System.out.println(pa.balance());
			System.out.println(pa.getMoney(-9));
		} catch (MoneyException me) {
			System.out.println(me.getMessage());
		} catch (IllegalArgumentException ipe) {
			System.out.println(ipe.getMessage());
		} catch (LimitException le) {
			System.out.println(le.getMessage());
		} finally {
			//Executed always
			System.out.println("Operation finished.");
		}
	}
}
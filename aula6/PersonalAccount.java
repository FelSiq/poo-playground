public class PersonalAccount implements BankAccount {
	final static int personalLimit = 1500;

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
		else throw new IllegalArgumentException("Negative or zero value not permitted.");
	}

	public double balance(){
		return howManyMoney;
	}

	PersonalAccount(){
		howManyMoney = 0.0;
	}

	@Override
	public int compareTo(BankAccount o){
		if (this.balance() == o.balance())
			return 0;
		else if (this.balance() > o.balance())
			return 1;
		else
			return -1;
	}
}
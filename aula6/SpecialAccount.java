public class SpecialAccount implements BankAccount{
	static final specialLimit = 2000;
	static final debtLimit = 2000;

	protected double howManyMoney;

	public double getMoney(double howMany) throws MoneyException, LimitException, IllegalArgumentException{
		if (howMany > 0){
			if (howManyMoney - howMany >= debtLimit){
				if (howMany <= specialLimit){
					howManyMoney -= howMany;
					return howMany;
				} else throw new LimitException();
			} else throw new MoneyException();
		} else throw new IllegalArgumentException("Negative value not permitted.");
	}

	public double balance(){
		return howManyMoney;
	}
	public void putMoney(double howMany) throws MoneyException, IllegalArgumentException{

	}

	SpecialAccount(){

	}	
}
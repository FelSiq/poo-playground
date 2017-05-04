public interface BankAccount extends Comparable <BankAccount>{
	public double getMoney(double howMany) throws MoneyException, LimitException, IllegalArgumentException;
	public void putMoney(double howMany) throws MoneyException, IllegalArgumentException;
	public double balance();

	@Override
	public int compareTo(BankAccount o);
}
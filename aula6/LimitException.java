class LimitException extends Throwable{
	public LimitException(){
		super("You can't break your account limit.");
	}
}
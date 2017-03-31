package PokerPack;
/**
* Unique exception class for Poker game.
* @extends Throwable
* @author Felipe Alves Siqueira 9847706
*/
public class OutOfCredits extends Throwable{
	public OutOfCredits(){super("You DO NOT have that much of credits! BET MORE to proceed!");}
	public OutOfCredits(String str){super(str);}
}
/**
* Unique exception class for Poker game.
* @extends Throwable
* @author I don't know.
*/
class OutOfCredits extends Throwable{
	OutOfCredits(){super("You DO NOT have that much of credits! BET MORE to proceed!");}
	OutOfCredits(String str){super(str);}
}
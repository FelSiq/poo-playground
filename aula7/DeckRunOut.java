/**
* Unique exception class for Poker game.
* @extends Throwable
* @author Santa Claus.
*/
class DeckRunOut extends Throwable{
	DeckRunOut(){super("Sorry, but there's no cards on this deck to draw.");}
	DeckRunOut(String str){super(str);}
}
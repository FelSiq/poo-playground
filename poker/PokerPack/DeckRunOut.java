package PokerPack;
/**
* Unique exception class for Poker game.
* @extends Throwable
* @author Felipe Alves Siqueira 9847706
*/
public class DeckRunOut extends Throwable{
	public DeckRunOut(){super("Sorry, but there's no cards on this deck to draw.");}
	public DeckRunOut(String str){super(str);}
}
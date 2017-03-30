import BozoPackage.*;

/**
* Main class of Bozo' game.
* @imports BozoPackage
* @author Felipe Alves Siqueira (9847706) e Gabriel Cyrillo (9763022)
*/

class Bozo{
	public final RollDice rd;
	public final Score sb;

	static final int REROLLS = 2;
	static final int ROUNDS = 10; 

	Bozo(){
		rd = new RollDice(5);
		sb = new Score();
	}

	public static void main(String[] args) throws Exception {
		//Main game initialization
		Bozo game = new Bozo();

		//Indexes and auxiliary variables
		int i = 0, aux = 0, valHolder = 0;
		int [] values = null;
		boolean LOOP_FLAG;
		EntradaTeclado myInput = new EntradaTeclado();

		System.out.println("Welcome to the Bozo' game! Let's begin!");
		//Main loop
		while(ROUNDS > i++){
			//Start of this round.
			System.out.println("\t------------ Round #" + i + " ------------ ");
			game.rd.roll(); //Rolls all dice.
			System.out.println(game.rd.toString()); //Prints all dice.

			//Reroll section
			for (int j = 0; j < Bozo.REROLLS; ++j){
				System.out.printf("Choose the dice you want to reroll (#%d/%d chance), press <ENTER> to finish:\n> ", j + 1, REROLLS);
				values = game.rd.roll(myInput.leString());
				System.out.println(game.rd.toString());
			}
			//End of reroll section
		
			//Set-value-on-scoreboard section
			System.out.println("Choose the position to fill in the ScoreBoard:\n" + game.sb.toString() + "\n> ");
			do {
				LOOP_FLAG = false; //Keeps looping while input is invalid.
				try {
					aux = (myInput.leInt() - 1);
					
					while (!game.sb.checkPosition(aux)){ //Keeps looping while second input is invalid.
						System.out.println("This position is already filled, choose another one!\n> ");
						aux = (myInput.leInt() - 1);
					}

					game.sb.add(aux, values); //Add the correspondent value on the choosen position.
				} catch (Exception e){
					System.out.println("Invalid input, try again:\n> ");
					LOOP_FLAG = true;
				}
			} while (LOOP_FLAG);
			//end of scoreboard-fill section

			//Aesthetics purposes.
			valHolder = game.sb.getScorePosition(aux); //Get value of this round, and prints.
			System.out.printf("This round was %d points worth%s!\tCurrent score: %d!\n",
				valHolder,
				(valHolder > 0 ? "" : " (too bad!)"),
				game.sb.getScore());
			//End of this round.
		}

		System.out.println("Final score: " + game.sb.getScore());
	}
}
import java.util.HashSet;
import myNaiveGraph.*;
import java.util.Scanner;

public class Graph extends HashSet {
	protected String label;

	public Graph() {
		super();
		this.label = "Unlabeled graph";
	}

	public Graph(String newLabel) {
		super();
		this.label = newLabel;
	}

	public String getLabel() {
		return this.label;
	}

	public String setLabel(String newLabel) {
		if (newLabel == null)
			newLabel = "Unlabeled graph";

		this.label = newLabel;
		
		return newLabel;
	}

	public static void main(String[] args) {
		System.out.println("Welcome to a Naive Graph Manager. I'll be your guide.");
		
		Scanner myInput = new Scanner(System.in);

		System.out.print("Please, insert a graph label:\n> ");
		Graph myGraph = new Graph(myInput.next());

		System.out.print("Please, insert the edges of the graph:\n> ");
		double aux = -1;
		do {
			try {
				aux = myInput.nextDouble();
				//if (aux > 0)
					//myGraph.add(new Vertex(aux));
			} catch (Exception e) {
				System.out.println("e: something went wrong with your Input. Try again.");
			}
		} while (aux > 0);

		myInput.close();
		System.out.println("sys: program will now exit.");
	}
}
import java.util.TreeMap; //To vertex collection
import java.util.TreeSet; //To vertex collection
import java.util.HashSet; //To edge colletion (graph)
import java.util.PriorityQueue; //To dijkstra's algorithm
import java.util.Stack; 
import java.util.Scanner; //Main input class
import java.util.Locale; //To set up correct scanner parameters
import java.io.File; //To command line input path
import java.io.FileReader; //To command line input path
import myNaiveGraph.*; //Custom package

@SuppressWarnings("serial") //To ignore 'serialVersionUID' serializable class warning

/**
* Main class of this project. Implicity a Edge collection, with real number as weigths.
*/
public class Graph <T> extends HashSet <Edge <T>> {
	protected String label;
	public TreeMap <String, Vertex <T>> vertexSet = new TreeMap <String, Vertex <T>> ();

	/**
	* Non parametrized constructor.
	* @Throws no exception.
	*/
	public Graph() {
		super();
		this.label = "Unlabeled graph";
	}

	/**
	* Parametrized constructor.
	* @Throws no exception.
	*/
	public Graph(String newLabel) {
		super();
		this.label = newLabel;
	}

	/**
	* Get graph label.
	* @Throws no exception.
	*/
	public String getLabel() {
		return this.label;
	}

	/**
	* Set a brand new graph label.
	* @Throws no exception.
	*/
	public String setLabel(String newLabel) {
		if (newLabel == null)
			newLabel = "Unlabeled graph";

		this.label = newLabel;
		
		return newLabel;
	}

	/**
	* Add a new graph edge.
	* @Throws no exception.
	*/
	public void edgeAdd(T weigth, Vertex <T> vA, Vertex <T> vB) {
		try {
			this.add(new Edge <T>(weigth, vA, vB));
		} catch (IllegalArgumentException iae) {
			System.out.print(iae.getMessage() + 
			"e: something went wrong with your Input. Try again.\n> ");
		}
	}

	/**
	* Runs a naive Dijkstras algorithm, and prints whatever was asked on the problem pdf. 
	* @Throws IllegalArgumentException, if at least one given vertex is null.
	*/
	public void naiveDijkstra(Vertex <T> start, Vertex <T> end) throws IllegalArgumentException{
		if (start == null || end == null)
			throw new IllegalArgumentException("Given vertexes must exists.");

		PriorityQueue <Vertex <T>> pq = new PriorityQueue <Vertex <T>> (this.vertexSet.size());
		//Starts start vertex with zero initial value
		start.setCurValue(0.0);
		
		Vertex <T> curVert = null, auxVert = null;
		Double auxDouble = -1.0;
		
		start.setMarked();
		pq.add(start);

		while (!pq.isEmpty()) {
			curVert = pq.poll();
			TreeSet <Edge <T>> curAdjList = curVert.getEdgeList();

			if (curAdjList != null && !curAdjList.isEmpty()){
				for (Edge <T> adj : curVert.getEdgeList()) {
					auxVert = adj.getConvergent();
					if (!auxVert.check()){
						pq.add(auxVert);
						auxVert.setMarked();
					}

					auxDouble = curVert.getCurValue() + (Double) adj.getWeigth();
					if (auxVert.getCurValue() > auxDouble) {
						auxVert.setCurValue(auxDouble);
						auxVert.setPredecessor (curVert);
					}
				}
			}
		}

		//Check if there's result.
		System.out.println("Distance between the two given nodes: " + 
			(end.check() ? end.getCurValue() : "impossible path."));
	}

	/**
	* Main user interface. Check out inner documentation for deeper details.
	* @Throws no exception.
	*/
	public static void main(String[] args) {
		Scanner myInput = null;
		File myInputFile = null;
		FileReader fs = null;

		if (args.length == 0){
			System.out.println("Welcome to a Naive Graph Manager. I'll be your guide.");
			//No input file is given, use stdin
			System.out.println("Tip: you can give a input file via command line arguments " +
				"(e.g: javac Graph <input path>\n");

			//Get input from stdin
			myInput = new Scanner(System.in);
		} else {
			try {
				myInputFile = new File(args[0]);
				fs = new FileReader (myInputFile);
				myInput = new Scanner(fs);
			} catch (Exception e) {
				System.out.println("e: something went wrong on this input file. abort.\n");
				System.exit(1);
			}
		}

		//Get graph input 
		if (myInputFile == null)
			System.out.print("Please, insert a graph label:\n> ");
		Graph <Double> myGraph = new Graph <Double>(myInput.nextLine());

		//Print user instructions to manual insertion step
		if (myInputFile == null)
			System.out.print("Please, insert the edges of the graph\n" +
				"- Input model: <real positive weigth> <divergent vertex label> <convergent vertex label>\n" +
				"- Example: 4.5 My-Div-Vertex0 My-Conv-Vertex1\n" +
				"- Type a negative value to ends this process:\n> ");

		//Declare needed resources and set up a few important things
		String vertexString0 = null, vertexString1 = null;
		double auxWeigth = -1;
		myInput.useLocale(Locale.US); 

		//Construct the graph
		do {
			try {
				//Get new edge weigth
				auxWeigth = myInput.nextDouble();

				if (auxWeigth > 0) {
					//Vertex A
					vertexString0 = myInput.next();
					if (!myGraph.vertexSet.containsKey(vertexString0))
						myGraph.vertexSet.put(vertexString0, new Vertex <Double>(vertexString0));

					//Vertex B
					vertexString1 = myInput.next();
					if (!myGraph.vertexSet.containsKey(vertexString1))
						myGraph.vertexSet.put(vertexString1, new Vertex <Double>(vertexString1));

					//Insert the new edge 
					myGraph.edgeAdd(auxWeigth, 
						myGraph.vertexSet.get(vertexString0), 
						myGraph.vertexSet.get(vertexString1));

					//Prepare interface to the next user input
					if (myInputFile == null)
						System.out.print("> ");
				}
			} catch (java.util.InputMismatchException ime ) {
				System.out.println("e: invalid input.\n");

			} finally {
				vertexString0 = null;
				vertexString1 = null;
			}
		} while (auxWeigth > 0);

		//Prepare to run dijkstra's algorithm
		//Get the initial and final vertexes
		if (!myGraph.isEmpty()) {
			if (myInputFile == null)
				System.out.print("Please specify a initial node label and the final node label:\n> ");
			vertexString0 = myInput.next();
			vertexString1 = myInput.next();

			try {
				myGraph.naiveDijkstra(
					myGraph.vertexSet.get(vertexString0), 
					myGraph.vertexSet.get(vertexString1));
			} catch (IllegalArgumentException iae){
				System.out.println(iae.getMessage());
			} catch (Exception e) {
				System.out.println("e: Something went wrong on dijkstra's algorithm.");
				e.printStackTrace();
			}
		} else System.out.println("e: Given graph is empty or invalid.");

		//Close scanner, input file and ends the program
		myInput.close();
		if (fs != null){
			try {
				fs.close();
			} catch (Exception e){
				//Do nothing.
			}
		} else System.out.println("Program will now exit.");
	}
}
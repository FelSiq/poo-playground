package myNaiveGraph;
import java.util.TreeSet;
import java.util.Comparator;

public class Vertex <T> implements Comparable <Vertex <T>>{
	protected String label;
	protected TreeSet <Edge <T>> myAdjEdges;
	protected Double currentValue = Double.MAX_VALUE;
	protected Vertex <T> predecessor = null;
	protected boolean visited = false;

	public String getLabel() {
		return label;
	}

	public void setLabel (String newLabel) {
		this.label = newLabel; 
	}

	public Vertex(String newLabel) {
		super();
		this.label = newLabel; 
		this.myAdjEdges = new TreeSet <Edge <T>> ();
	}

	public void addEdge(Edge <T> newIncidentEdge) {
		this.myAdjEdges.add(newIncidentEdge);
	}

	public boolean hasEdge(Edge <T> checkEdge) {
		return this.myAdjEdges.contains(checkEdge);
	}

	public TreeSet <Edge <T>> getEdgeList (){
		return this.myAdjEdges;
	}

	public void setCurValue(Double newValue) {
		this.currentValue = newValue;
	}

	public Double getCurValue() {
		return this.currentValue;
	}

	public boolean check() {
		return this.visited;
	}

	public void setMarked(){
		this.visited = true;
	}

	public void resetMarked(){
		this.visited = false;
	}

	public void setPredecessor (Vertex <T> myPred) {
		this.predecessor = myPred;
	}

	public int compareTo (Vertex <T> otherVertex) {
		return this.label.compareTo(otherVertex.label);
	}
}
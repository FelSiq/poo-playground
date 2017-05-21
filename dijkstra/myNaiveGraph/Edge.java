package myNaiveGraph;

public class Edge <T> implements Comparable <Edge <T>>{
	protected T weigth;
	private Vertex aVert, bVert;

	public Edge(T myWeigth, Vertex newAVert, Vertex newBVert) throws IllegalArgumentException {
		super();
		if ((Double) myWeigth <= 0)
			throw new IllegalArgumentException("Edge weigths, on this program, must always be a positive value.");
		if (newAVert == null || newBVert == null)
			throw new IllegalArgumentException("You must specify the two incident vertexes of the edge.");
		this.weigth = myWeigth;
		this.aVert = newAVert;
		this.bVert = newBVert;
		this.aVert.addEdge(this);
	}

	public T getWeigth() {
		return this.weigth;
	}

	public Vertex <T> getDivergent() {
		return this.aVert;
	}

	public Vertex <T> getConvergent() {
		return this.bVert;
	}

	public int compareTo (Edge <T> other) {
		return ((Double) this.weigth).compareTo((Double) other.weigth);
	}
}
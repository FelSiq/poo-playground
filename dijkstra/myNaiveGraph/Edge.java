package myNaiveGraph;

public class Edge <T> {
	protected T weigth;
	private Vertex aVert, bVert;

	public Edge(T myWeigth, Vertex newAVert, Vertex newBVert) throws IllegalArgumentException {
		super();
		if ((Double) myWeigth <= 0)
			throw new IllegalArgumentException("Edge weigths, on this program, must always be a positive value.");
		if (newAVert == null || newBVert == null)
			throw new IllegalArgumentException("");
		this.weigth = myWeigth;
	}

	public T getWeigth() {
		return this.weigth;
	}
}
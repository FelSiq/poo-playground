package myNaiveGraph;

public class Vertex {
	protected String label;

	public String getLabel() {
		return label;
	}

	public void setLabel (String newLabel) {
		this.label = newLabel; 
	}

	public Vertex(String newLabel) {
		super();
		this.label = newLabel; 
	}
}
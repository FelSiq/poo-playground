class BinTree <T>{
	Node <T> root;
	int size;

	private class Node <T> {
		Node <T> sonl, sonr;
		int key;
		T item;

		//Getters
		T getItem(){return this.item;}
		int getKey(){return this.key;}
		Node <T> getSonL(){return this.sonl;}
		Node <T> getSonR(){return this.sonr;}
		//Constructor
		Node(T newItem, int newKey){
			this.item = newItem;
			this.key = newKey;
			this.sonl = null;
			this.sonr = null;
		}
		//Setters
		boolean setSonL(Node <T> newSonL){
			this.sonl = newSonL;
			return true;
		}
		boolean setSonR(Node <T> newSonR){
			this.sonr = newSonR;
			return true;
		}
	}

	//Constructor
	public BinTree(){
		this.root = null;
		this.size = 0;
	}

	//getters
	public int getSize(){return this.size;}

	//Methods
	private T getItem_rec(Node <T> root, int key){
		if (root != null){
			if (key == root.key)
				return root.getItem();
			else if (key > root.key)
				return getItem_rec(root.getSonR(), key);
			else
				return getItem_rec(root.getSonL(), key);
		}
		return null;
	}

	public T getItem(int key){
		return getItem_rec(this.root, key);
	}

	private boolean insertItem_rec(Node <T> root, Node <T> newNode){
		if (root.getKey() > newNode.getKey()){
			if (root.getSonL() != null)
				return insertItem_rec(root.getSonL(), newNode);
			else 
				return root.setSonL(newNode);
		} else if (root.getKey() < newNode.getKey()){
			if (root.getSonR() != null)
				return insertItem_rec(root.getSonR(), newNode);
			else
				return root.setSonR(newNode);
		}
		return false;
	}

	public void insertItem(T newItem, int newKey){
		Node <T> newNode = new Node <T> (newItem, newKey);
		if (this.root != null)
			if(!insertItem_rec(this.root, newNode))
				System.out.println("This key is already used.");
		else
			this.root = newNode;
		this.size += 1;
	}

	private void print_rec(Node <T> root){
		if (root != null){
			System.out.println(root.getItem());
			print_rec(root.getSonL());
			print_rec(root.getSonR());
		}
	}

	public void print(){
		print_rec(this.root);
	}
}
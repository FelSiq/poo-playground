class BinTree <T1, T2>{
	Node <T1, T2> root;
	int size;

	private class Node <T1, T2> {
		Node <T1, T2> sonl, sonr;
		T2 key;
		T1 item;

		//Getters
		T1 getItem(){return this.item;}
		T2 getKey(){return this.key;}
		Node <T1, T2> getSonL(){return this.sonl;}
		Node <T1, T2> getSonR(){return this.sonr;}
		//Constructor
		Node(T1 newItem, T2 newKey){
			this.item = newItem;
			this.key = newKey;
			this.sonl = null;
			this.sonr = null;
		}
		//Setters
		boolean setSonL(Node <T1, T2> newSonL){
			this.sonl = newSonL;
			return true;
		}
		boolean setSonR(Node <T1, T2> newSonR){
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
	private T1 getItem_rec(Node <T1, T2> root, T2 key){
		if (root != null){
			if (key.equals(root.key))
				return root.getItem();
			else if (key > root.key)
				return getItem_rec(root.getSonR(), key);
			else
				return getItem_rec(root.getSonL(), key);
		}
		return null;
	}

	public T1 getItem(int key){
		return getItem_rec(this.root, key);
	}

	private boolean insertItem_rec(Node <T1, T2> root, Node <T1, T2> newNode){
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
		Node <T1, T2> newNode = new Node <T1, T2> (newItem, newKey);
		if (this.root != null){
			if(!insertItem_rec(this.root, newNode))
				System.out.println("This key is already used.");
			else
				this.size += 1;
		} else {
			this.root = newNode;
			this.size += 1;
		}
	}

	private void print_rec(Node <T1, T2> root, int offset){
		if (root != null){
			for (int i = 0; i < offset; ++i)
				System.out.print(" ");
			System.out.println(root.getItem() + "/" + root.getKey());

			print_rec(root.getSonL(), offset + 1);
			print_rec(root.getSonR(), offset + 1);
		};
	}

	public void print(){
		print_rec(this.root, 0);
	}
}
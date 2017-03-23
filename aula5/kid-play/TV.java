/**
* This class was a laboratory exercise.
* @autor felsiq
*/

class TV{
	//Parameters
	protected int volume;
	private final int volMax = 100;

	private String [] myChan_array; //(poor) Approach 1: use a String array to identify the current Channel (Static)
	private int currentIndex; //Auxiliary variable for approach 1.

	private final Channel noSignal; //Approach 2: use a circular double linked list to identify the channel (dynamic)
	private Channel myChan_list; //Auxiliary variable for approach 2.

	//Private Inner class
	private class Channel{
		Channel next, prev;
		String name;
		final int identifier; 
			//This is the channel primary key. 
			//If its not found in the search, then
			//The current channel must be kept.

		//Initializer block
		{
			next = this; //This linked list will be circular.
			prev = this; //And double linked.
		}

		private Channel(){
			name = new String("No signal");
			identifier = -1; //The primary key should be immutable.
			//The "No signal" Channel will be the only one
			//with negative index;
		}

		//Constructor for common channels
		private Channel(String newName, int newId){
			name = newName;
			identifier = newId;
		}

		/**
		* This method recovers the next channel of this TV, using the two implemented approaches. 
		* @return The asked channel, if found. Else, the current channel.
		*/
		private Channel chanFind(int idChan) throws IllegalArgumentException{
			if (idChan >= 0){
				Channel ref = noSignal.prev;

				while(idChan < ref.identifier)
					ref = ref.prev;

				if (ref.identifier == idChan)
					return ref;
			} else throw new IllegalArgumentException("This index is not valid (must be positive).");
			return this; //Return the current channel by default
		}

		/**
		* Insert a new channel on the linked list
		*/
		private void chanInsert(Channel newChan) throws IllegalArgumentException{
			if (newChan != null){
				Channel ref = this.prev;

				while (newChan.identifier < ref.identifier)
					ref = ref.prev;
				ref = ref.next;

				//Insert the new node ate the right position
				newChan.next = ref;
				newChan.prev = ref.prev;
				ref.prev.next = newChan;
				ref.prev = newChan;
			} else throw new IllegalArgumentException("Invalid new channel (null refererence)");
		}

	}
	//Methods
	/**
	* This method recovers the next channel of this TV, using the two implemented approaches. 
	*/
	protected void chanNext(){
		//Approach 1:
		currentIndex = (currentIndex + 1) % myChan_array.length;

		//Approach 2:
		myChan_list = myChan_list.next;
	} 

	/**
	* This method recovers the previous channel of this TV, using the two implemented approaches. 
	*/
	protected void chanPrev(){
		//Approach 1:
		if (--currentIndex < 0)
			currentIndex = myChan_array.length - 1;

		//Approach 2:
		myChan_list = myChan_list.prev;
	} 

	/**
	* This method set a specific channel of this TV, if it exists, using the two implemented approaches. 
	*/
	protected void chanSet(int idChan){
		//Approach 1:
		if (0 < idChan && idChan < myChan_array.length)
			currentIndex = idChan;

		//Approach 2:
		try {
			myChan_list = myChan_list.chanFind(idChan);
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		}
	}

	/**
	* Increment the TV volume, if possible (vol < volMax).
	*/
	protected void volInc(){
		volume = Math.min(volMax, volume + 1);
	}

	/**
	* Decrement the TV volume, if possible (vol > 0).
	*/
	protected void volDec(){
		volume = Math.max(0, volume - 1);
	}

	//Constructor
	protected TV(String [] chanNames){
		currentIndex = 0;

		//Approach 1:
		myChan_array = chanNames;

		//Approach 2
		noSignal = new Channel(); //Create the "No singnal" channel
		myChan_list = noSignal;
		for (int i = 0; i < chanNames.length; ++i){
			Channel aux = new Channel(chanNames[i], i);
			try {
				noSignal.chanInsert(aux);
			} catch (IllegalArgumentException iae){
				iae.printStackTrace();
			}
		}
	}

	//Getter
	protected String channel(){
		return myChan_list.name;
	}
}
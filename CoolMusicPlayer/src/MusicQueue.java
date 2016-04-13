import java.util.ArrayList;

public class MusicQueue {
	
	private ArrayList<Integer> queue;
	
	/**
	 * default constructor of the queue, that creates an
	 * arraylist to act as our music queue
	 */
	public MusicQueue() {
		queue = new ArrayList<Integer>();
	}
	
	/**
	 * adds a new song to the end of the queue
	 * @param song
	 */
	public void enqueue(int sID){
		queue.add(sID);
	}
	
	/**
	 * removes a song from the top of the 
	 * @returns the song removed from the front of the queue
	 */
	public int pop(){
		return queue.remove(0);
	}
	
	/**
	 * get size of queue
	 * @returns queue.size()
	 */
	public int getSize(){
		return queue.size();
	}
	
	/**
	 * removes all the songs coming before the given index
	 * and then returns and removes the song at the given index.
	 * @param index the index of the song needing to be removed in the queue
	 */
	public int pop(int index){
		for(int i = 0; i<index; i++){
			queue.remove(i);
		}
		return queue.remove(0);
	}
	
	public void removeSong(int index){
		queue.remove(index);
	}
	/**
	 * removes all songs from the queue
	 */
	public void clearQueue(){
		while(!queue.isEmpty()){
			queue.remove(0);
		}	
	}
	
	/**
	 * get song ID at index
	 * @returns queue.get(i)
	 */
	public int getSongAt(int i){
		return queue.get(i);
	}
}



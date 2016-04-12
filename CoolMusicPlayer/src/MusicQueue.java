import java.util.ArrayList;

public class MusicQueue {
	
	private ArrayList<Song> queue;
	
	/**
	 * default constructor of the queue, that creates an
	 * arraylist to act as our music queue
	 */
	public MusicQueue() {
		queue = new ArrayList<Song>();
	}
	
	/**
	 * adds a new song to the end of the queue
	 * @param song
	 */
	public void enqueue(Song song){
		queue.add(song);
	}
	
	/**
	 * removes a song from the top of the 
	 * @returns the song removed from the front of the queue
	 */
	public Song pop(){
		return queue.remove(0);
	}
	
	/**
	 * removes all the songs coming before the given index
	 * and then returns and removes the song at the given index.
	 * @param index the index of the song needing to be removed in the queue
	 */
	public Song pop(int index){
		for(int i = 0; i<index; i++){
			queue.remove(i);
		}
		return queue.remove(0);
	}
	
	/**
	 * removes all songs from the queue
	 */
	public void clearQueue(){
		while(!queue.isEmpty()){
			queue.remove(0);
		}	
	}
}



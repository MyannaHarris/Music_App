import java.util.ArrayList;

public class Playlist {
	private ArrayList<Integer> numbers;
	private int ID;
	private String name;
	
	/**
	 * default constructor sets id to 0, name to "new Playlist"
	 * and creates the new array of Song ID's
	 */
	public Playlist() {
		// TODO Auto-generated constructor stub
		numbers = new ArrayList<Integer>();
		ID = 0;
		name = "New Playlist";
	}
	
	/**
	 * constructor that take sin a playlist name, and
	 * the first song to be added's song ID and instantiates
	 * the arraylist of song id's
	 * @param pname the name of the playlist
	 * @param sID the id of the first song added to the new playlist
	 * @param pID the id of the playlist
	 */
	public Playlist(String pname, int sID, int pID){
		ID = pID;
		numbers = new ArrayList<Integer>();
		numbers.add(sID);
		name = pname;
	}	
	
	/**
	 * adds a song to the playlist
	 * @param sID id of song to be added
	 */
	public void addSong(int sID){
		numbers.add(sID);
	}
	
	/**
	 * removes the given song corresponding to the SID
	 * @param sID id of song to be removed
	 */
	public void removeSong(int sID){
		for(int i = 0; i<numbers.size(); i++){
			if(numbers.get(i) == sID){
				numbers.remove(i);
				i = numbers.size();
			}
		}
	}

	public void removeAt(int sIndex){
		numbers.remove(sIndex);
	}
	
	/**
	 * removes all songs from the playlist
	 */
	public void clear(){
		while(!numbers.isEmpty()){
			numbers.remove(0);
		}
	}
	
	/**
	 * gets the arraylist of the songs
	 * @return an arraylist of all the songs
	 */
	public ArrayList<Integer> getList(){
		return numbers;
	}
	
	/**
	 * sets the name of the playlist
	 * @param pname name of the playlist
	 */
	public void setName(String pname){
		name = pname;
	}
	
	/**
	 * gets the id of the playlist
	 * @return ID of the playlist
	 */
	public int getID(){
		return ID;
	}

	/**
	 * gets the name of the playlist
	 * @return name of the playlist
	 */
	public String getName(){
		return name;
	}
	
	public int getSize() {
		return numbers.size();
	}
}


public class Song {

	private String name;
	private String album;
	private String artist;
	private String artDesc;
	private String genre;
	private String alDesc;
	private int ID;
	private String path;
	
	/**
	 * Default contructor sets all strings to "" and ints to 0
	 */
	public Song() {
		// TODO Auto-generated constructor stub
		name = "";
		album = "";
		artist ="";
		artDesc = "";
		alDesc = "";
		genre = "";
		ID = 0;
		path = "";
	}
	
	/**
	 * Creates a new song with all attributes taken in a parameters.
	 * @param sName name of song
	 * @param sAlbum name of album of song
	 * @param sArtist name of artist of song
	 * @param sArtDesc description of artist of song
	 * @param sAlDesc description of album of song
	 * @param sGenre genre of song
	 * @param sID ID of song
	 * @param sPath path to the song file
	 */
	public Song(String sName, String sAlbum, String sArtist, 
			String sArtDesc, String sAlDesc, String sGenre, int sID, String sPath){
		name = sName;
		album = sAlbum;
		artist = sArtist;
		artDesc = sArtDesc;
		alDesc = sAlDesc;
		genre = sGenre;
		ID = sID;
		path = sPath;
	}

	/**
	 * gets the name of the song
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * gets the name of the album of the song.
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * gets the name of the artist of the song
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * gets the description of the artist of the song
	 * @return the artDesc
	 */
	public String getArtDesc() {
		return artDesc;
	}

	/**
	 * gets the genre of the song
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * gets the album description of the album of the song
	 * @return the alDesc
	 */
	public String getAlDesc() {
		return alDesc;
	}

	/**
	 * gets the id of the song
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * gets the path of the song file
	 * @return the path
	 */
	public String getPath() {
		return path;
	}	

}

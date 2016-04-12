/**
 * @author Myanna Harris
 * CPSC 224
 * Project
 * Cool Music Player
 * 
 * Interface between GUI and rest of classes
 */

import java.util.*;

public class Music {
	
	// Variables
	
	private ArrayList<Playlist> listPlaylists;
	private MusicQueue queue;
	private ArrayList<Song> listSongs;
	private DataBase db;
	private MusicPlayer musicPlayer;
	
	/** Constructor
	  * @pre called
	  * @post Music object created, other objects instantiated
	  * */
	public Music()
	{
		listPlaylists = new ArrayList<Playlist>();
		//listPlaylists = getAllPlaylist();
		//queue = = new MusicQueue();
		listSongs = new ArrayList<Song>();
		//listSongs = getAllSongs();
		db = new DataBase();
		musicPlayer = new MusicPlayer();
	}
	
	/** Plays the current song if paused, else does nothing
	  * @pre User hits play button
	  * @post Plays current song if paused, else does nothing
	  * */
	public void playButton()
	{
		musicPlayer.play();
	}
	
	/** Pauses current song if playing, else does nothing
	  * @pre User hits pause button
	  * @post Pauses current song if playing, else nothing
	  * */
	public void pauseButton()
	{
		musicPlayer.pause();
	}
	
	/** Restarts currently playing song
	  * @pre User hits restart button
	  * @post Restarts currently playing song
	  * */
	public void restartButton()
	{
		musicPlayer.restart();
	}
	
	/** Skips to next song
	  * @pre User presses skip
	  * @post current song skipped, next song playing if exists
	  * */
	public void skip()
	{
		/*if(!queue.isEmpty())
		{
			String songNextID = queue.pop();
			String path = "";
			for (int i=0;  i<listSongs.size()); i++) {
				if (listSongs.get(i).getID() == songNextID) {
					path = listSongs.get(i).getPath();
				}
			}
			musicPlayer.play(path);
		}*/
	}
	
	/** Plays the queue using MusicPlayer object
	  * @pre User hits play on queue
	  * @post 1st song in queue is playing
	  * */
	public void playQueue()
	{
		if(!musicPlayer.isPlaying())
		{
			skip();
		}
	}
	
	/** Plays playlist by adding it to the queue
	  * @pre User hits play on playlist
	  * @post Playlist added to queue and starts playing
	  * @param pIndex - index of playlist to play
	  * */
	public void playPlaylist(int pIndex)
	{
		//for all songs in playlist: addToQueue
		Playlist tempP = listPlaylists.get(pIndex);
		ArrayList<Integer> tempS = tempP.getList();
=======
		ArrayList<Playlist tempP = listPlaylists.get(pIndex);
>>>>>>> ac8734d80be0ff01546cb5d98f0c17e8045611aa
		emptyQueue();
		for (int i=0; i<tempS.size(); i++) {
			addToQueue(tempS.get(i));
		}
		playQueue();
	}
	
	/** Returns the song object
	  * @pre Song list exists
	  * @post song object returned, or null if not exist
	  * @param sIndex - index of song
	  * @return listSongs.get(sIndex)
	  * */
	public Song getSong(int sIndex)
	{
	    if (sIndex < listSongs.size())
	    {
			return listSongs.get(sIndex);
		}
		else
		{
			return null;
		}
	}
	
	public Song getSongInfo (int id) {
		for (int i=0;  i<listSongs.size(); i++) {
			if (listSongs.get(i).getID() == id) {
				return listSongs.get(i);
			}
		}
		return null;
	}
	
	/** Returns the song object
	  * @pre queue exists
	  * @post song object returned, or null if nothing there
	  * @return listSongs.get(sIndex)
	  * */
	/*public Song getQueueSong(int sIndex)
	{
	    if (sIndex < )
	    {
			return ;
		}
		else
		{
			return null;
		}
	}*/
	
	/** Checks if an artist already has a description
	  * @pre Song is being added
	  * @post return saved description, or ""
	  * @param aName - the artist name
	  * @return String description
	  * */
	public String checkArtistDesc(String aName)
	{
		String description = "";
		description = db.getAlbumDesc(aName);
		return description;
	}
	
	/** Checks if an album already has a description
	  * @pre Song is being added
	  * @post return saved description, or ""
	  * @param aName - the album name
	  * @return String description
	  * */
	public String checkAlbumDesc(String aName)
	{
		String description = "";
		description = db.getArtistDesc(aName);
		return description;
	}
	
	public void addArtist(String name, String desc) {
		String tempDesc;
		if (desc.equals("")) {
			tempDesc = "N/A";
		}
		else {
			tempDesc = desc;
		}
		db.addArtist(name,tempDesc);
	}
	
	public void addAlbum(String name, String desc) {
		String tempDesc;
		if (desc.equals("")) {
			tempDesc = "N/A";
		}
		else {
			tempDesc = desc;
		}
		db.addAlbum(name,tempDesc);
	}
	
	/** Adds a new song to the database and "All Music"
	  * @pre User adds song to App
	  * @post Song added to database and "All Music"
	  * @param song - song name
	  * @param artist - artist name
	  * @param album - album name
	  * @param artDesc - artist description
	  * @param albDesc - album description
	  * @param path - path to song file
	  * @param genre - genre of song
	  * */
	public void addSong(String song,String artist,String album,String artDesc,String albDesc,String path,String genre)
	{
		String tempArt;
		String tempAlb;
		if (artDesc.equals("")) {
			tempArt = "N/A";
		}
		else {
			tempArt = artDesc;
		}
		if (albDesc.equals("")) {
			tempAlb = "N/A";
		}
		else {
			tempAlb = albDesc;
		}
		int id = db.addSong(song, artist, album, path, genre);
		Song songTemp = new Song(song, album, artist, artDesc, albDesc, genre,id,path);
		listSongs.add(songTemp);
	}
	
	/** Plays selected song, empties queue
	  * @pre User presses play on song
	  * @post Song playing, song only one in queue
	  * @param sIndex - song index to play
	  * */
	public void playSong(int sIndex)
	{
		emptyQueue();
		addToQueue(sIndex);
		playQueue();
	}
	
	/** Returns the playlist object
	  * @pre playlist list exists
	  * @post playlist object returned, or null if not exist
	  * @param pIndex - index of playlist
	  * @return listPlaylists.get(pIndex)
	  * */
	public Playlist getPlaylists(int pIndex)
	{
		return listPlaylists.get(pIndex);
	}
	
	/** Returns song object to get song info
	  * @pre Song info asked for and song object exists
	  * @post Return song object
	  * @return songObj
	  * */
	public Song viewInfo(int sIndex)
	{
		Song songObj = listSongs.get(sIndex);
		return songObj;
	}
	
	/** Empties the queue
	  * @pre Queue exists
	  * @post Queue is empty
	  * */
	public void emptyQueue()
	{
		//queue = null;
	}
	
	/** Add song to queue
	  * @pre queue and song exist
	  * @post Song added to queue
	  * @param sIndex - song index
	  * @return boolean - true if worked, false otherwise
	  * */
	public boolean addToQueue(int sIndex)
	{
		queue.enqueue(listSongs.get(sIndex).getID());
		return true;
	}
	
	/** Creates new playlist
	  * @pre playlist doesn't exist, song exists
	  * @post new playlist created with the song in it
	  * @param pIndex - playlist index
	  * @param sIndex - song index
	  * @return boolean - true if worked, false otherwise
	  * */
	public boolean makePlaylist(String pName, int sIndex)
	{
		int songID = listSongs.get(sIndex).getID();
		int id = db.addPlaylist(pName, songID);
		Playlist playTemp = new Playlist(pName, songID, id);
		listPlaylists.add(playTemp);
		if (id==0) {
			return false;
		}
		return true;
	}
	
	/** Deletes playlist
	  * @pre given playlist index to delete
	  * @post playlist deleted from app and database
	  * @param pIndex - playlist index
	  * @return boolean - true if worked, false otherwise
	  * */
	public boolean deletePlaylist(int pIndex)
	{
		if (playlistExists(pIndex)) {
			db.removePlaylist(listPlaylists.get(pIndex).getID());
			listPlaylists.remove(pIndex);
			return true;
		}
		return false;
	}
	
	/** Add song to playlist
	  * @pre song exist
	  * @post Song added to playlist, playlist created if it doesn't exist
	  * @param pIndex - playlist index
	  * @param sIndex - song index
	  * @return boolean - true if worked, false otherwise
	  * */
	public boolean addToPlaylist(int pIndex, int sIndex)
	{
		int pID = listPlaylists.get(pIndex).getID();
		int sID = listSongs.get(sIndex).getID();
		listPlaylists.get(pIndex).addSong(sID);
		return db.addToPlaylist(pID, sID);
	}
	
	/** Delete song from app and database
	  * @pre song index given
	  * @post Song removed from app and database if exists
	  * @param sIndex - song index
	  * @return boolean - true if worked, false otherwise
	  * */
	public boolean deleteSong(int sIndex)
	{
		if (sIndex > 0 && sIndex < listSongs.size())
		{
			boolean worked = db.removeSong(id);
			if (worked) {
				int id = listSongs.get(sIndex).getID();
				for (int a=0; a<listPlaylists.size(); a++) {
					Playlist currPlaylist = listPlaylists.get(a);
					ArrayList<Integer> list = currPlaylist.getList();
					int s = currPlaylist.getSize();
					for (int b=0; b<s; b++) {
						if (list.get(b)==id) {
							currPlaylist.removeAt(b);
							b--;
							s--;
						}
					}
				}
				listSongs.remove(sIndex);
			}
		    return worked;
		}
		return false;
	}
	
	/** Checks if a playlist exists
	  * @pre index given
	  * @post Return true if exists, false otherwise
	  * @param pIndex - playlist index
	  * @return boolean - true if exists, false otherwise
	  * */
	public boolean playlistExists(int pIndex)
	{
		return (pIndex>=0 && pIndex<listPlaylists.size());
	}

}

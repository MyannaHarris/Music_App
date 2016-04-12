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
	
	//private ArrayList<Playlist> listPlaylists;
	//private MusicQueue queue;
	//private ArrayList<Song> listSongs;
	private DataBase db;
	private MusicPlayer musicPlayer;
	
	/** Constructor
	  * @pre called
	  * @post Music object created, other objects instantiated
	  * */
	public Music()
	{
		//listPlaylists = new ArrayList<Playlist>();
		//listPlaylists = getAllPlaylist();
		//queue = = new MusicQueue();
		//listSongs = new ArrayList<Song>;
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
	public void skipTo()
	{
		/*if(!queue.isEmpty())
		{
			String songNextID = queue.pop();
			//Find song path
			String path = "";
			playQueue(path);
		}*/
	}
	
	/** Plays the queue using MusicPlayer object
	  * @pre User hits play on queue
	  * @post 1st song in queue is playing
	  * */
	public void playQueue()
	{
		musicPlayer.play();
	}
	
	/** Plays playlist by adding it to the queue
	  * @pre User hits play on playlist
	  * @post Playlist added to queue and starts playing
	  * @param pIndex - index of playlist to play
	  * */
	public void playPlaylist(int pIndex)
	{
		//for all songs in playlist: addToQueue
		playQueue();
	}
	
	/** Returns the ArrayList of all song objects
	  * @pre Song objects created from info in database
	  * @post ArrayList of all songs returned
	  * @return listSongs
	  * */
	/*public Song getSong(int sIndex) // Return list of song objects?
	{
	    if (sIndex < listSongs.size())
	    {
			return listSongs.get(sIndex);
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
		//return db.getAlbumDesc(String aName);
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
		//return db.getArtistDesc(String aName);
		return description;
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
		//db.addSong(song, artist, album, artDesc, albDesc, path, genre);
		//Get ID?
		//Song songTemp = new Song(song, artist, album, artDesc, albDesc, path, genre);
		//listSongs.add(songTemp);
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
	
	/** Returns list of playlists ..........????????????????
	  * @pre List of playlists exists
	  * @post Return list of playlists
	  * @return listPlaylists
	  * */
	/*public Playlist getPlaylists(int pIndex)
	{
		return listPlaylists.get(pIndex);
	}*/
	
	/** Returns song object to get song info
	  * @pre Song info asked for and song object exists
	  * @post Return song object
	  * @return songObj
	  * */
	/*public Song viewInfo(int sIndex)
	{
		Song songObj = listSongs.get(sIndex);
		return songObj;
	}*/
	
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
		//queue.enqueue(sIndex); // ?????
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
		//db.addPlaylist(aName, sIndex)
		//Get ID?
		//Playlist playTemp = new Playlist(aName, sIndex);
		//listPlaylists.add(playTemp);
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
		//if (sIndex > 0 && sIndex < listPlaylists.size())
		//{
		//    listPlaylists.remove(sIndex);
		//    return true;
		//}
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
		//if (!playlistExists(pIndex))
		//{
		//    db.addPlaylist(String name, ArrayList<Songs>)
		//    return true;
		//}
		//else
		//{
		//    listPlaylists.get(pIndex).addSong(sIndex); //sID ????
		//}
		return false;
	}
	
	/** Delete song from app and database
	  * @pre song index given
	  * @post Song removed from app and database if exists
	  * @param sIndex - song index
	  * @return boolean - true if worked, false otherwise
	  * */
	public boolean deleteSong(int sIndex)
	{
		//if (sIndex > 0 && sIndex < listSongs.size())
		//{
		//    listSongs.remove(sIndex);
		//    return true;
		//}
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
		return true;
	}

}

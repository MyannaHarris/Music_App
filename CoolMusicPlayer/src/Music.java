/**
 * @author Myanna Harris
 * CPSC 224
 * Project
 * Cool Music Player
 * 
 * Interface between GUI and rest of classes
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Music {
	
	// Variables
	
	private ArrayList<Playlist> listPlaylists;
	private MusicQueue queue;
	private ArrayList<Song> listSongs;
	private DBConnector db;
	private MusicPlayer musicPlayer;
	
	/** Constructor
	  * @pre called
	  * @post Music object created, other objects instantiated
	  * */
	public Music()
	{
		db = new DBConnector();
		listPlaylists = new ArrayList<Playlist>();
		getAllPlaylist();
		queue = new MusicQueue();
		listSongs = new ArrayList<Song>();
		getAllSongs();
		musicPlayer = new MusicPlayer();
	}
	
	public void getAllPlaylist() {
		ResultSet result = db.getAllPlaylist();
		if(result != null)
		{
			try {
				int oldId = 0;
				int pIndex = -1;
				while(result.next())
				{
					int id = result.getInt(1);
					if(oldId == id)
					{
						int sId = result.getInt(3);
						listPlaylists.get(pIndex).addSong(sId);
					}
					else
					{
						pIndex++;
						oldId = id;
						String name = result.getString(2);
						int sId = result.getInt(3);
						Playlist p = new Playlist(name,sId,id);
						listPlaylists.add(p);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//playlist id
		//playlist name
		//song id
	}
	
	public void getAllSongs() {
		ResultSet result = db.getAllSongs();
		if(result != null)
		{
			try {
				while(result.next())
				{
					int id = result.getInt(1);
					String n = result.getString(2);
					String g = result.getString(3);
					String p = result.getString(4);
					String art = result.getString(5);
					String artD = result.getString(6);
					String al = result.getString(7);
					String alD = result.getString(8);
					Song s = new Song(n,al,art,artD,alD,g,id,p);
					listSongs.add(s);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//song id
		//song name
		//genre
		//song path
		//artist name
		//artist desc
		//album name
		//album desc
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
	
	/** Remove song from queue
	  * @pre User hits delete
	  * @post Song deleted from queue
	  * */
	public void removeSongFromQueue(int sIndex)
	{
		queue.removeSong(sIndex);
	}
	
	/** Skips to next song
	  * @pre User presses skip
	  * @post current song skipped, next song playing if exists
	  * */
	public void skip()
	{
		if(queue.getSize() > 0)
		{
			int songNextID = queue.pop();
			String path = "";
			for (int i=0;  i<listSongs.size(); i++) {
				if (listSongs.get(i).getID() == songNextID) {
					path = listSongs.get(i).getPath();
				}
			}
			musicPlayer.play(path);
		}
		else {
			musicPlayer.stop();
		}
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
	    if (sIndex >= 0 && sIndex < listSongs.size())
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
	public Song getQueueSong(int sIndex)
	{
	    if (sIndex >= 0 && sIndex < queue.getSize())
	    {
	    	int id = queue.getSongAt(sIndex);
			return getSongInfo(id);
		}
		else
		{
			return null;
		}
	}
	
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
	public Song addSong(String song,String artist,String album,String artDesc,String albDesc,String path,String genre)
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
		int id = db.addSong(song, artist, album, artDesc, albDesc, path, genre);
		Song songTemp = new Song(song, album, artist, artDesc, albDesc, genre,id,path);
		listSongs.add(songTemp);
		return songTemp;
	}
	
	/** Plays selected song, empties queue
	  * @pre User presses play on song
	  * @post Song playing, song only one in queue
	  * @param sIndex - song index to play
	  * */
	public boolean playSong(int sIndex)
	{
		//musicPlayer.play(listSongs.get(sIndex).getPath());
		emptyQueue();
		addToQueue(listSongs.get(sIndex).getID());
		playQueue();
		return true;
	}
	
	/** Returns the playlist object
	  * @pre playlist list exists
	  * @post playlist object returned, or null if not exist
	  * @param pIndex - index of playlist
	  * @return listPlaylists.get(pIndex)
	  * */
	public Playlist getPlaylist(int pIndex)
	{
		if (pIndex < 0 || pIndex >= listPlaylists.size())
			return null;
		return listPlaylists.get(pIndex);
	}
	
	public ArrayList<String> getPlaylistNames() {
		ArrayList<String> pNames = new ArrayList<String>();
		for (int i=0; i<listPlaylists.size(); i++) {
			pNames.add(listPlaylists.get(i).getName());
		}
		return pNames;
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
	
	public Song viewSongInPlaylist(int pIndex, int sIndex) {
		Song songObj = null;
		int id = listPlaylists.get(pIndex).getList().get(sIndex);
		for (int i = 0; i<listSongs.size(); i++) {
			if (listSongs.get(i).getID() == id) {
				songObj = listSongs.get(i);
			}
		}
		return songObj;
	}
	
	/** Empties the queue
	  * @pre Queue exists
	  * @post Queue is empty
	  * */
	public void emptyQueue()
	{
		queue = new MusicQueue();
	}
	
	/** Add song to queue
	  * @pre queue and song exist
	  * @post Song added to queue
	  * @param sIndex - song index
	  * @return boolean - true if worked, false otherwise
	  * */
	public boolean addToQueue(int sID)
	{
		queue.enqueue(sID);
		return true;
	}
	
	/** Creates new playlist
	  * @pre playlist doesn't exist, song exists
	  * @post new playlist created with the song in it
	  * @param pIndex - playlist index
	  * @param sIndex - song index
	  * @return boolean - true if worked, false otherwise
	  * */
	public Playlist makePlaylist(String pName, int sID)
	{
		int songID = sID;
		int id = db.addPlaylist(pName, songID);
		/*if (id==0) {
			return null;
		}*/
		Playlist playTemp = new Playlist(pName, songID, id);
		listPlaylists.add(playTemp);
		return playTemp;
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
	public boolean addToPlaylist(int pIndex, int sIDp)
	{
		int pID = listPlaylists.get(pIndex).getID();
		int sID = sIDp;
		listPlaylists.get(pIndex).addSong(sID);
		return db.addToPlaylist(pID, sID);
	}
	
	public void deleteFromPlaylist(int pIndex, int sIndex) {
		int pID = listPlaylists.get(pIndex).getID();
		int sID = listPlaylists.get(pIndex).getList().get(sIndex);
		db.removeFromPlaylist(pID,sID);
		listPlaylists.get(pIndex).removeAt(sIndex);
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
			int id = listSongs.get(sIndex).getID();
			for (int a=0; a<listPlaylists.size(); a++) {
				Playlist currPlaylist = listPlaylists.get(a);
				db.removeFromPlaylist(currPlaylist.getID(),id);
				currPlaylist.removeSong(id);
			}
			listSongs.remove(sIndex);
			db.removeSong(id);
		    return true;
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

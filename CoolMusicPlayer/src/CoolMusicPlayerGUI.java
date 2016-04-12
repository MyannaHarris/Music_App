/**
 * @author Myanna Harris
 * CPSC 224
 * Project
 * Cool Music Player
 * 
 * Main GUI
 */

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;


public class CoolMusicPlayerGUI extends JFrame {

	//Variables
	
	//Music class
	private Music music;
	
	//Component container
	private Container pane;
	
	//Music class variable
	//private Music music;
	
	/** Constructor
	  * @pre called
	  * @post CoolMusicPlayerGUI object created, GUI open and running
	  * */
	public CoolMusicPlayerGUI() {
		//Set title of frame
		super("Cool Music Player");
		
		//Create new music variable
		music = new Music();
		
		//Set up basic pane and add grid layout
		pane = getContentPane();
		GridBagLayout layout = new GridBagLayout();
		pane.setLayout(layout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1;
	    gbc.weighty = 1;
	    
	    //Create components
	    //Tab panel
	    JTabbedPane tabbedPane = new JTabbedPane();
	    
	    JPanel panel1 = new JPanel();
	    tabbedPane.addTab("All Music", panel1);
	    
	    JPanel panel2 = new JPanel();
	    tabbedPane.addTab("Playlists", panel2);
	    
	    JPanel panel3 = new JPanel();
	    tabbedPane.addTab("Queue", panel3);
	    
	    //All Music
	    JPanel panelAll = new JPanel();
	    /*DefaultListModel allItems = new DefaultListModel();
		JList allList = new JList(allItems);
		allList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		allList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane allScroll = new JScrollPane(allList);*/
	    JScrollPane allScroll = new JScrollPane(panelAll);
		allScroll.setMinimumSize(new Dimension(860, 600));
		allScroll.setPreferredSize(new Dimension(860,600));
		
		ArrayList<JPanel> allPanels = new ArrayList<JPanel>();
		
		int i = 0;
		/*Song tempSong = getSong(i);
		while(tempSong != null)
		{
			allPanels.add(createSongPanel(tempSong));
			panelAll.add(allPanels.get(i));
			i++;
			tempSong = getSong(i);
		}*/
		
		panel1.add(allScroll);
	    
	    //Playlists
		JPanel panelPlaylists = new JPanel();
		/*DefaultListModel playItems = new DefaultListModel();
		JList playList = new JList(playItems);
		playList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane playScroll = new JScrollPane(playList);*/
		JScrollPane playScroll = new JScrollPane(panelPlaylists);
		playScroll.setMinimumSize(new Dimension(860, 600));
		playScroll.setPreferredSize(new Dimension(860,600));
		
		ArrayList<JPanel> playlistPanels = new ArrayList<JPanel>();
		
		i = 0;
		/*Song tempPlaylist = getPlaylist(i);
		while(tempPlaylist != null)
		{
			allPanels.add(createSongPanel(tempSong));
			panelPlaylists.add(allPanels.get(i));
			i++;
			tempPlaylist = getPlaylist(i);
		}*/
		
		panel2.add(playScroll);
	    
	    //Queue
		JPanel panelQueue = new JPanel();
		/*DefaultListModel qItems = new DefaultListModel();
		JList qList = new JList(qItems);
		qList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		qList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane qScroll = new JScrollPane(qList);*/
		JScrollPane qScroll = new JScrollPane(panelQueue);
		qScroll.setMinimumSize(new Dimension(860, 600));
		qScroll.setPreferredSize(new Dimension(860,600));
		
		//getQueueSong
		
		panel3.add(qScroll);
	    
	    
	    //Music playing components
	    JButton restartButton = new JButton("<<");
	    restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    JButton playButton = new JButton(">");
	    playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    JButton pauseButton = new JButton("||");
	    pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    JButton skipButton = new JButton(">>");
	    skipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    //Song info label
	    JLabel songInfoLabel = new JLabel("Song - Artist - Album");
	    songInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    //Song time label
	    JLabel timeLabel = new JLabel("1:00");
	    timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    //Make grid 9 columns and 3 rows (2 for tabs)
	    //Add components to grid
	    //Tab panel
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 9;
	    pane.add(tabbedPane, gbc);
	    
	    //Music playing components
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.gridwidth = 1;
	    pane.add(restartButton, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    gbc.gridwidth = 1;
	    pane.add(playButton, gbc);
	    
	    gbc.gridx = 2;
	    gbc.gridy = 2;
	    gbc.gridwidth = 1;
	    pane.add(pauseButton, gbc);
	    
	    gbc.gridx = 3;
	    gbc.gridy = 2;
	    gbc.gridwidth = 1;
	    pane.add(skipButton, gbc);
	    
	    gbc.gridx = 4;
	    gbc.gridy = 2;
	    gbc.gridwidth = 4;
	    pane.add(songInfoLabel, gbc);
	    
	    gbc.gridx = 8;
	    gbc.gridy = 2;
	    gbc.gridwidth = 1;
	    pane.add(timeLabel, gbc);
	    
	    // Add listeners
	    
	    restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						music.restartButton();
					}
				});
			}
        });
	    
	    playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						music.playButton();
					}
				});
			}
        });
	    
	    pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						music.pauseButton();
					}
				});
			}
        });
	    
	    skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						music.skipTo();
					}
				});
			}
        });
	    /*
	     * private String getArtistDesc(String aName)
	private String getAlbumDesc(String aName)
	private void playQueue()
	private void playPlaylist(int pIndex)
	private void addToPlaylist(int pIndex, int sIndex)
	private void addToQueue(int sIndex)
	
	//private void addPlaylist(int sIndex) already exists
	
	private void removePlaylist(int pIndex)
	private void removeSong(int sIndex)
	private void addSongPage()
	private void playSong(int sIndex)
	private void updatePlay(int ID)	
	private void viewSongInfo()
	private void success(String msg)
	     */
	    
	    setSize(900,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/** Create panel for a song in the JScrollPane
	  * @pre CoolMusicPlayerGUI object exists
	  * @post return panel for song
	  * @param - Song info
	  * @return songPanel
	  * */
	private JPanel createSongPanel()
	{
		JPanel songPanel = new JPanel();
		
		
		
		return songPanel;
	}
	
	/** Create panel for a playlist in the JScrollPane
	  * @pre CoolMusicPlayerGUI object exists
	  * @post return panel for playlist
	  * @param - Song info
	  * @return playlistPanel
	  * */
	private JPanel createPlaylistPanel()
	{
		JPanel playlistPanel = new JPanel();
		
		
		
		return playlistPanel;
	}
	
	/** Checks if an artist already has a description
	  * @pre Song is being added
	  * @post return saved description, or ""
	  * @param aName - the artist name
	  * @return String music.checkArtistDesc(aName)
	  * */
	private String getArtistDesc(String aName)
	{
		return music.checkArtistDesc(aName);
	}
	
	/** Checks if an album already has a description
	  * @pre Song is being added
	  * @post return saved description, or ""
	  * @param aName - the album name
	  * @return String music.checkAlbumDesc(aName)
	  * */
	private String getAlbumDesc(String aName)
	{
		return music.checkAlbumDesc(aName);
	}
	
	/** Plays the queue
	  * @pre User hits play on queue
	  * @post 1st song in queue is playing
	  * */
	private void playQueue()
	{
		playQueue();
	}
	
	/** Plays playlist
	  * @pre User hits play on playlist
	  * @post Playlist added to queue and starts playing
	  * @param pIndex - index of playlist to play
	  * */
	private void playPlaylist(int pIndex)
	{
		playPlaylist(pIndex);
	}
	
	/** Adds song to playlist, creates playlist if it doesn't exist
	  * @pre User adds song to playlist
	  * @post (Playlist created and) song added to playlist
	  * @param pIndex - playlist index in list
	  * @param sIndex - song index in list
	  * @exception FailException if adding fails
	  * */
	private void addToPlaylist(int pIndex, int sIndex)
	{
		try
		{
			if (music.addToPlaylist(pIndex,sIndex))
			    success("Song added to playlist successfully!");
			else
				throw new FailException("Song not added");
		}
		catch (FailException e)
		{
			e.printStackTrace();
		}
	}
	
	/** Adds song to queue
	  * @pre User adds song to queue
	  * @post song added to queue
	  * @param sIndex - song index in list
	  * @exception FailException if adding fails
	  * */
	private void addToQueue(int sIndex)
	{
		try
		{
			if (music.addToQueue(sIndex))
				success("Added to queue successfully!");
			else
				throw new FailException("Song not added");
		}
		catch (FailException e)
		{
			e.printStackTrace();
		}
	}
	
	//private void addPlaylist(int sIndex) already exists
	
	/** Deletes playlist
	  * @pre User deletes a playlist
	  * @post Playlist no longer exists
	  * @param pIndex - playlist index in list
	  * @exception FailException if deleting fails
	  * */
	private void removePlaylist(int pIndex)
	{
		try
		{
			if (music.deletePlaylist(pIndex))
				success("Playlist deleted successfully!");
			else
				throw new FailException("Playlist not deleted");
		}
		catch (FailException e)
		{
			e.printStackTrace();
		}
	}
	
	/** Song deleted from "All Music" and all playlists
	  * @pre User deletes song
	  * @post Song removed from "All Music", playlists, and database
	  * @param sIndex - song index in list
	  * @exception FailException if deleting fails
	  * */
	private void removeSong(int sIndex)
	{
		try
		{
			if (music.deleteSong(sIndex))
				success("Song deleted successfully!");
			else
				throw new FailException("Song not deleted");
		}
		catch (FailException e)
		{
			e.printStackTrace();
		}
	}
	
	/** Creates addSong pop-up page
	  * Adds song to database and "All Music"
	  * @pre User adds song to App
	  * @post Song added to database and "All Music"
	  * */
	private void addSongPage()
	{
		String song = "";
		String artist = "";
		String album = "";
		String artDesc = "";
		String albDesc = "";
		String path = "";
		String genre = "";
		music.addSong(song, artist, album, artDesc, albDesc, path, genre);
	}
	
	/** Plays song
	  * Clears out queue, adds song to queue, plays queue
	  * @pre User plays singular song
	  * @post Queue emptied, Song added to queue and played
	  * @param sIndex - song index in list
	  * @exception FailException if playing fails
	  * */
	private void playSong(int sIndex)
	{
		try
		{
			music.emptyQueue();
			if (music.addToQueue(sIndex))
				playQueue();
			else
				throw new FailException("Song not played");
		}
		catch (FailException e)
		{
			e.printStackTrace();
		}
	}
	
	/** Updates the information for what song is playing
	  * @pre New song starts to play
	  * @post New song's info shown on app
	  * @param ID - song ID
	  * */
	private void updatePlay(int ID)
	{
		int sIndex = 0;
		playSong(sIndex);
	}
	
	/** Creates and shows pop-up of selected song's info
	  * @pre User clicks on "i" for song
	  * @post Pop-up shows selected song's info (modeless)
	  * @param ID - song ID
	  * */
	private void viewSongInfo(int ID)
	{
		//Song songCurr = viewInfo(sIndex);
	}
	
	/** Success pop-up dialog so user knows when things work
	  * @pre User does something like add a song
	  * @post If successful, Success dialogbox pops up
	  * @param msg - message to print in dialogbox
	  * */
	private void success(String msg)
	{
		JOptionPane.showMessageDialog(pane, msg);
	}
	
}

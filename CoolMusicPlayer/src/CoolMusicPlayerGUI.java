/**
 * @author Myanna Harris
 * CPSC 224
 * Project
 * Cool Music Player
 * 
 * Main GUI
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class CoolMusicPlayerGUI extends JFrame {

	//Variables
	
	//Component container
	private Container pane;
	
	//Music class variable
	private Music music;
	
	//lists of panels for scrollpanes
	ArrayList<JPanel> allPanels;
	ArrayList<JPanel> playlistPanels;
	ArrayList<JPanel> queuePanels;
	
	//list of panels for songs in playlists
	ArrayList<JPanel> subSongPanels;
	ArrayList<JPanel> currPlaylistSongPanels;
	//gridbagconstraints for the subsong panels
	GridBagConstraints gbcSub;
	
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
	    
	    
	    //Constraints for gridbag for scroll panels
	    GridBagConstraints gbc2 = new GridBagConstraints();
	    gbc2.anchor = GridBagConstraints.NORTHWEST;
        gbc2.gridwidth = GridBagConstraints.REMAINDER;
        gbc2.weightx = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(3,3,3,3);
        
        //Constraints for gridbag for sub songs on playlists
	    gbcSub = new GridBagConstraints();
	    gbcSub.anchor = GridBagConstraints.NORTHWEST;
	    gbcSub.gridwidth = GridBagConstraints.REMAINDER;
	    //gbcSub.weightx = .5;
	    //gbcSub.weighty = .5;
	    gbcSub.fill = GridBagConstraints.HORIZONTAL;
        
        //Constraints for gridbag for blank panels
	    GridBagConstraints gbc3 = new GridBagConstraints();
	    gbc3.anchor = GridBagConstraints.NORTHWEST;
        gbc3.gridwidth = GridBagConstraints.REMAINDER;
        gbc3.weightx = 1;
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.weighty = 1.0;
	    
	    //All Music
	    JPanel panelAll = new JPanel(new GridBagLayout());
	    JScrollPane allScroll = new JScrollPane(panelAll);
		allScroll.setMinimumSize(new Dimension(1460, 600));
		allScroll.setPreferredSize(new Dimension(1460,600));
		
		allPanels = new ArrayList<JPanel>();
		
		/*allPanels.add(createSongPanel(new Song()));
		panelAll.add(allPanels.get(0),gbc2,-1);
		allPanels.add(createSongPanel(new Song()));
		panelAll.add(allPanels.get(1),gbc2,-1);*/
		
		int i = 0;
		Song tempSong = music.getSong(i);
		while(tempSong != null)
		{
			allPanels.add(createSongPanel(tempSong));
			panelAll.add(allPanels.get(i),gbc2, -1);
			i++;
			tempSong = music.getSong(i);
		}
		
		panelAll.add(new JPanel(),gbc3,-1);
		
		panel1.add(allScroll);
	    
	    //Playlists
		JPanel panelPlaylists = new JPanel(new GridBagLayout());
		JScrollPane playScroll = new JScrollPane(panelPlaylists);
		playScroll.setMinimumSize(new Dimension(1460, 600));
		playScroll.setPreferredSize(new Dimension(1460,600));

		playlistPanels = new ArrayList<JPanel>();
		
		//panels for sub songs in playlists
	    subSongPanels = new ArrayList<JPanel>();
	    currPlaylistSongPanels = new ArrayList<JPanel>();
	    
		/*playlistPanels.add(createPlaylistPanel(new Playlist()));
		panelPlaylists.add(playlistPanels.get(0),gbc2,-1);
		
		subSongPanels.add(new JPanel(new GridBagLayout()));
		panelPlaylists.add(subSongPanels.get(0),gbc2,-1);
		
		playlistPanels.add(createPlaylistPanel(new Playlist()));
		panelPlaylists.add(playlistPanels.get(1),gbc2,-1);
		
		subSongPanels.add(new JPanel(new GridBagLayout()));
		
		currPlaylistSongPanels.add(createSubSongPanel(""));
		subSongPanels.get(1).add(currPlaylistSongPanels.get(0),gbcSub,-1);
		
		currPlaylistSongPanels.add(createSubSongPanel(""));
		subSongPanels.get(1).add(currPlaylistSongPanels.get(1),gbcSub,-1);
		
		panelPlaylists.add(subSongPanels.get(1),gbc2,-1);*/
		
		i = 0;
		Playlist tempPlaylist = music.getPlaylist(i);
		while(tempPlaylist != null)
		{
			playlistPanels.add(createPlaylistPanel(tempPlaylist));
			panelPlaylists.add(playlistPanels.get(i),gbc2,-1);
			subSongPanels.add(new JPanel());
			panelPlaylists.add(subSongPanels.get(i),gbc2,-1);
			i++;
			tempPlaylist = music.getPlaylist(i);
		}
		
		panelPlaylists.add(new JPanel(),gbc3,-1);
		
		panel2.add(playScroll);
	    
	    //Queue
		JPanel panelQueue = new JPanel(new GridBagLayout());
		JScrollPane qScroll = new JScrollPane(panelQueue);
		qScroll.setMinimumSize(new Dimension(1460, 600));
		qScroll.setPreferredSize(new Dimension(1460,600));

		//getQueueSong
		queuePanels = new ArrayList<JPanel>();
		
		i = 0;
		Song tempQ = music.getQueueSong(i);
		while(tempQ != null)
		{
			queuePanels.add(createQueuePanel(tempQ));
			panelQueue.add(queuePanels.get(i),gbc2,-1);
			i++;
			tempQ = music.getQueueSong(i);
		}
		
		panelQueue.add(new JPanel(),gbc3,-1);
		
		panel3.add(qScroll);
	    
	    
	    //Music playing components
		JPanel songButtonsPanel = new JPanel();
	    JButton restartButton = new JButton("<<");
	    restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    JButton playButton = new JButton(">");
	    playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    JButton pauseButton = new JButton("||");
	    pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    JButton skipButton = new JButton(">>");
	    skipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    songButtonsPanel.add(restartButton);
	    songButtonsPanel.add(playButton);
	    songButtonsPanel.add(pauseButton);
	    songButtonsPanel.add(skipButton);
	    
	    //Add song button
	    JPanel addSongButtonPanel = new JPanel();
	    JButton addSongButton = new JButton("Add Song");
	    addSongButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    addSongButtonPanel.add(addSongButton);
	    
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
	    gbc.gridwidth = 2;
	    pane.add(songButtonsPanel, gbc);
	    
	    gbc.gridx = 2;
	    gbc.gridy = 2;
	    gbc.gridwidth = 5;
	    pane.add(songInfoLabel, gbc);
	    
	    gbc.gridx = 7;
	    gbc.gridy = 2;
	    gbc.gridwidth = 1;
	    pane.add(timeLabel, gbc);
	    
	    gbc.gridx = 8;
	    gbc.gridy = 2;
	    gbc.gridwidth = 1;
	    pane.add(addSongButtonPanel, gbc);
	    
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
						music.skip();
					}
				});
			}
        });
	    
	    addSongButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						addSongPage();
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
	    
	    setSize(1500,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/** Create panel for a song in the JScrollPane
	  * @pre CoolMusicPlayerGUI object exists
	  * @post return panel for song
	  * @param - Song info
	  * @return songPanel
	  * */
	private JPanel createSongPanel(Song s)
	{
		JPanel songPanel = new JPanel();
		songPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx=1;
		
		JPanel namePanel = new JPanel(new GridBagLayout());
		namePanel.setPreferredSize(new Dimension(270,50));
		JPanel artistPanel = new JPanel(new GridBagLayout());
		artistPanel.setPreferredSize(new Dimension(270,50));
		JPanel albumPanel = new JPanel(new GridBagLayout());
		albumPanel.setPreferredSize(new Dimension(270,50));
		JPanel genrePanel = new JPanel(new GridBagLayout());
		genrePanel.setPreferredSize(new Dimension(270,50));
		
		JButton playButtonP = new JButton(">");
		JLabel nameL = new JLabel(s.getName());
		namePanel.add(nameL,gbc);
		JButton addButtonP = new JButton("+");
		JLabel artistL = new JLabel(s.getArtist());
		artistPanel.add(artistL,gbc);
		JLabel albumL = new JLabel(s.getAlbum());
		albumPanel.add(albumL,gbc);
		JLabel genreL = new JLabel(s.getGenre());
		genrePanel.add(genreL,gbc);
		JButton infoButtonP = new JButton("i");
		JButton deleteButtonP = new JButton("Delete");
		
	    songPanel.add(playButtonP);
	    songPanel.add(namePanel);
	    songPanel.add(addButtonP);
	    songPanel.add(artistPanel);
	    songPanel.add(albumPanel);
	    songPanel.add(genrePanel);
	    songPanel.add(infoButtonP);
	    songPanel.add(deleteButtonP);
	    
	    playButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<allPanels.size(); k++)
		    	{
		    		Component[] listC = allPanels.get(i).getComponents();
		    		if(listC[0] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	playSong(i);
		    }  
		});
	    
	    addButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<allPanels.size(); k++)
		    	{
		    		Component[] listC = allPanels.get(i).getComponents();
		    		if(listC[2] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	plusSongPopup(music.getSong(i));
		    }  
		});
	    
	    infoButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<allPanels.size(); k++)
		    	{
		    		Component[] listC = allPanels.get(i).getComponents();
		    		if(listC[6] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	viewSongInfo(music.getSong(i));
		    }  
		});
	    
	    deleteButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<allPanels.size(); k++)
		    	{
		    		Component[] listC = allPanels.get(i).getComponents();
		    		if(listC[7] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	removeSong(i);
		    }  
		});
		
		return songPanel;
	}
	
	/** Create panel for a song in the JScrollPane
	  * @pre CoolMusicPlayerGUI object exists
	  * @post return panel for song
	  * @param - Song info
	  * @return songPanel
	  * */
	private JPanel createQueuePanel(Song s)
	{
		JPanel songPanel = new JPanel();
		songPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx=1;
		
		JPanel namePanel = new JPanel(new GridBagLayout());
		namePanel.setPreferredSize(new Dimension(270,50));
		JPanel artistPanel = new JPanel(new GridBagLayout());
		artistPanel.setPreferredSize(new Dimension(270,50));
		JPanel albumPanel = new JPanel(new GridBagLayout());
		albumPanel.setPreferredSize(new Dimension(270,50));
		JPanel genrePanel = new JPanel(new GridBagLayout());
		genrePanel.setPreferredSize(new Dimension(270,50));
		
		JButton playButtonP = new JButton(">");
		JLabel nameL = new JLabel(s.getName());
		JLabel artistL = new JLabel(s.getArtist());
		artistPanel.add(artistL,gbc);
		JLabel albumL = new JLabel(s.getAlbum());
		albumPanel.add(albumL,gbc);
		JLabel genreL = new JLabel(s.getGenre());
		genrePanel.add(genreL,gbc);
		JButton infoButtonP = new JButton("i");
		JButton deleteButtonP = new JButton("Delete");
		
	    songPanel.add(namePanel);
	    songPanel.add(artistPanel);
	    songPanel.add(albumPanel);
	    songPanel.add(genrePanel);
	    songPanel.add(infoButtonP);
	    songPanel.add(deleteButtonP);
	    
	    infoButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<queuePanels.size(); k++)
		    	{
		    		Component[] listC = queuePanels.get(i).getComponents();
		    		if(listC[6] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	viewSongInfo(music.getQueueSong(i));
		    }  
		});
	    
	    deleteButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<queuePanels.size(); k++)
		    	{
		    		Component[] listC = queuePanels.get(i).getComponents();
		    		if(listC[7] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	//removeSongFromQueue(i);
		    }  
		});
		
		return songPanel;
	}
	
	/** Create panel for a playlist in the JScrollPane
	  * @pre CoolMusicPlayerGUI object exists
	  * @post return panel for playlist
	  * @param - Song info
	  * @return playlistPanel
	  * */
	private JPanel createPlaylistPanel(Playlist p)
	{
		JPanel playlistPanel = new JPanel();
		playlistPanel.setBorder(BorderFactory.createLineBorder(Color.black)); 
		
		JPanel namePanel = new JPanel(new GridBagLayout());
		namePanel.setPreferredSize(new Dimension(1100,50));
		
		JButton playButtonP = new JButton(">");
		JButton songButtonP = new JButton("Show Songs");
		JLabel nameL = new JLabel(p.getName());
		namePanel.add(nameL);
		JButton deleteButtonP = new JButton("Delete");
		
		playlistPanel.add(playButtonP);
		playlistPanel.add(songButtonP);
		playlistPanel.add(namePanel);
		playlistPanel.add(deleteButtonP);
		
		songButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	currPlaylistSongPanels.clear();
		    	int i = 0;
		    	for(int k=0; k<playlistPanels.size(); k++)
		    	{
		    		Component[] listC = playlistPanels.get(i).getComponents();
		    		if(listC[1] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	Playlist tempPlaylist = music.getPlaylist(i);
		    	
		    	ArrayList<Integer> songIDs;
		    	songIDs = tempPlaylist.getList();
		    	
		    	for(int m = 0; m<songIDs.size(); m++)
		    	{
		    		Song temp = music.getSongInfo(songIDs.get(m));

		    		currPlaylistSongPanels.add(createSubSongPanel(temp));
		    		subSongPanels.get(i).add(currPlaylistSongPanels.get(m),gbcSub,-1);
		    	}
		    	validate();
		        repaint();
		    }  
		});
		
		playButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<playlistPanels.size(); k++)
		    	{
		    		Component[] listC = playlistPanels.get(i).getComponents();
		    		if(listC[0] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	playPlaylist(i);
		    }  
		});

		deleteButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<playlistPanels.size(); k++)
		    	{
		    		Component[] listC = playlistPanels.get(i).getComponents();
		    		if(listC[3] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	removePlaylist(i);
		    }  
		});
		
		return playlistPanel;
	}
	
	/** Create sub panel for a song in a playlist
	  * @pre CoolMusicPlayerGUI object exists
	  * @post return panel for song
	  * @param - Song info
	  * @return songPanel
	  * */
	private JPanel createSubSongPanel(Song s)
	{
		JPanel songPanel = new JPanel();
		songPanel.setPreferredSize(new Dimension(1300,60));
		songPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx=1;
		
		JPanel namePanel = new JPanel(new GridBagLayout());
		namePanel.setPreferredSize(new Dimension(270,50));
		JPanel artistPanel = new JPanel(new GridBagLayout());
		artistPanel.setPreferredSize(new Dimension(270,50));
		JPanel albumPanel = new JPanel(new GridBagLayout());
		albumPanel.setPreferredSize(new Dimension(270,50));
		JPanel genrePanel = new JPanel(new GridBagLayout());
		genrePanel.setPreferredSize(new Dimension(270,50));
		
		JLabel nameL = new JLabel(s.getName());
		namePanel.add(nameL,gbc);
		JLabel artistL = new JLabel(s.getArtist());
		artistPanel.add(artistL,gbc);
		JLabel albumL = new JLabel(s.getAlbum());
		albumPanel.add(albumL,gbc);
		JLabel genreL = new JLabel(s.getGenre());
		genrePanel.add(genreL,gbc);
		JButton infoButtonP = new JButton("i");
		JButton deleteButtonP = new JButton("Delete");
		
	    songPanel.add(namePanel);
	    songPanel.add(artistPanel);
	    songPanel.add(albumPanel);
	    songPanel.add(genrePanel);
	    songPanel.add(infoButtonP);
	    songPanel.add(deleteButtonP);
	    
	    infoButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<allPanels.size(); k++)
		    	{
		    		Component[] listC = allPanels.get(i).getComponents();
		    		if(listC[4] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	viewSongInfo(music.getSong(i));
		    }  
		});
	    
	    deleteButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<allPanels.size(); k++)
		    	{
		    		Component[] listC = allPanels.get(i).getComponents();
		    		if(listC[5] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	//music.deleteFromPlaylist(i);
		    }  
		});
		
		return songPanel;
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
		String song = "Test";
		String artist = "Test";
		String album = "Test";
		String artDesc = "Test";
		String albDesc = "Test";
		String path = "Test";
		String genre = "Test";
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
	private void viewSongInfo(Song s)
	{
		//Song songCurr = viewInfo(sIndex);
	}
	
	/** Creates add "+" menu
	  * @pre User clicks on "+"
	  * @post Pop-up shows options
	  * @param ID - song ID
	  * */
	private void plusSongPopup(Song s)
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

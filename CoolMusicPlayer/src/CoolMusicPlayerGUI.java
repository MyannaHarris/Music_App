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
import java.awt.Toolkit;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class CoolMusicPlayerGUI extends JFrame {

	//Variables
	
	//Component container
	private Container pane;
	
	//Music class variable
	private Music music;
	
	//panels for scrollpanes
	GridBagConstraints gbc2;
	GridBagConstraints gbc3;
	JPanel panelAll;
	ArrayList<JPanel> allPanels;
	JPanel panelPlaylists;
	ArrayList<JPanel> playlistPanels;
	boolean openSub; // flag to check if subsongs are showing
	int openSubIdx;
	ArrayList<JPanel> queuePanels;
	JPanel panelQueue;
	JPanel playQueueButtonPanel;
	
	//list of panels for songs in playlists
	ArrayList<JPanel> subSongPanels;
	ArrayList<JPanel> currPlaylistSongPanels;
	GridBagConstraints gbcSub;
	
	//Text boxes for adding a song
	JTextField artistField;
	JTextField artDescField;
	JTextField albumField;
	JTextField alDescField;
	
	//Song object for actions
	Song currSong;
	JLabel songInfoLabel;
	
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
	    gbc2 = new GridBagConstraints();
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
	    gbc3 = new GridBagConstraints();
	    gbc3.anchor = GridBagConstraints.NORTHWEST;
        gbc3.gridwidth = GridBagConstraints.REMAINDER;
        gbc3.weightx = 1;
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.weighty = 1.0;
	    
	    //All Music
	    panelAll = new JPanel(new GridBagLayout());
	    JScrollPane allScroll = new JScrollPane(panelAll);
		allScroll.setMinimumSize(new Dimension(1460, 600));
		allScroll.setPreferredSize(new Dimension(1460,600));
		
		allPanels = new ArrayList<JPanel>();
		
		//Comment
		/*
		JButton addButtonP = new JButton("Add");
		panelAll.add(addButtonP,gbc2,-1);
		addButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	plusSongPopup(new Song("name","art","al","Desc1","Desc2","Genre",1,"Path"));
		    }  
		});*/
		//out
		
		//comment
		/*allPanels.add(createSongPanel(new Song()));
		panelAll.add(allPanels.get(0),gbc2,-1);
		allPanels.add(createSongPanel(new Song()));
		panelAll.add(allPanels.get(1),gbc2,-1);*/
		//out
		
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
		openSub = false;
		openSubIdx = 0;
		panelPlaylists = new JPanel(new GridBagLayout());
		JScrollPane playScroll = new JScrollPane(panelPlaylists);
		playScroll.setMinimumSize(new Dimension(1460, 600));
		playScroll.setPreferredSize(new Dimension(1460,600));

		playlistPanels = new ArrayList<JPanel>();
		
		//panels for sub songs in playlists
	    subSongPanels = new ArrayList<JPanel>();
	    currPlaylistSongPanels = new ArrayList<JPanel>();
	    
	    //comment
		/*playlistPanels.add(createPlaylistPanel(new Playlist()));
		panelPlaylists.add(playlistPanels.get(0),gbc2,-1);
		
		subSongPanels.add(new JPanel(new GridBagLayout()));
		panelPlaylists.add(subSongPanels.get(0),gbc2,-1);
		
		playlistPanels.add(createPlaylistPanel(new Playlist()));
		panelPlaylists.add(playlistPanels.get(1),gbc2,-1);
		
		subSongPanels.add(new JPanel(new GridBagLayout()));
		
		currPlaylistSongPanels.add(createSubSongPanel(new Song()));
		subSongPanels.get(1).add(currPlaylistSongPanels.get(0),gbcSub,-1);
		
		currPlaylistSongPanels.add(createSubSongPanel(new Song()));
		subSongPanels.get(1).add(currPlaylistSongPanels.get(1),gbcSub,-1);
		
		panelPlaylists.add(subSongPanels.get(1),gbc2,-1);*/
		//out
		
		i = 0;
		Playlist tempPlaylist = music.getPlaylist(i);
		while(tempPlaylist != null)
		{
			playlistPanels.add(createPlaylistPanel(tempPlaylist));
			panelPlaylists.add(playlistPanels.get(i),gbc2,-1);
			subSongPanels.add(new JPanel(new GridBagLayout()));
			panelPlaylists.add(subSongPanels.get(i),gbc2,-1);
			i++;
			tempPlaylist = music.getPlaylist(i);
		}
		
		panelPlaylists.add(new JPanel(),gbc3,-1);
		
		panel2.add(playScroll);
	    
	    //Queue
		panelQueue = new JPanel(new GridBagLayout());
		JScrollPane qScroll = new JScrollPane(panelQueue);
		qScroll.setMinimumSize(new Dimension(1460, 600));
		qScroll.setPreferredSize(new Dimension(1460,600));
		
		//play queue
		playQueueButtonPanel = new JPanel();
		playQueueButtonPanel.setPreferredSize(new Dimension(270,50));
	    JButton playQueueButton = new JButton("Play Queue");
	    playQueueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    playQueueButtonPanel.add(playQueueButton);
	    panelQueue.add(playQueueButtonPanel,gbc2,-1);

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
	    songInfoLabel = new JLabel("Song - Artist - Album");
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
						
						Song s = music.getQueueSong(0);
						int sID = s.getID();
						updatePlay(sID);
						music.skip();
						panelQueue.remove(queuePanels.get(0));
				    	queuePanels.remove(0);
				    	validate();
				        repaint();
				        panelQueue.updateUI();
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
	    
	    playQueueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if(queuePanels.size()>0)
						{
							Song s = music.getQueueSong(0);
							int sID = s.getID();
							updatePlay(sID);
							music.playQueue();
							panelQueue.remove(queuePanels.get(0));
					    	queuePanels.remove(0);
					    	validate();
					        repaint();
					        panelQueue.updateUI();
						}
					}
				});
			}
        });
	    
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    double width = screenSize.getWidth();
	    double height = screenSize.getHeight();
	    
	    if(width < 1500 || height < 800)
	    	setSize((int)width,(int)height);
	    else
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
		    		Component[] listC = allPanels.get(k).getComponents();
		    		if(listC[0] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	panelQueue.removeAll();
		    	queuePanels = new ArrayList<JPanel>();
		    	panelQueue.add(playQueueButtonPanel,gbc2,-1);
		    	panelQueue.add(new JPanel(),gbc3,-1);
		    	validate();
		        repaint();
		        panelQueue.updateUI();
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
		    		Component[] listC = allPanels.get(k).getComponents();
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
		    		Component[] listC = allPanels.get(k).getComponents();
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
		    		Component[] listC = allPanels.get(k).getComponents();
		    		if(listC[7] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	panelAll.remove(allPanels.get(i));
		    	allPanels.remove(i);
		    	validate();
		        repaint();
		        panelAll.updateUI();
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
		    	for(int k=0; k<queuePanels.size(); k++)
		    	{
		    		Component[] listC = queuePanels.get(k).getComponents();
		    		if(listC[4] == e.getComponent())
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
		    		Component[] listC = queuePanels.get(k).getComponents();
		    		if(listC[5] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	panelQueue.remove(queuePanels.get(i));
		    	queuePanels.remove(i);
		    	validate();
		        repaint();
		        panelQueue.updateUI();
		    	music.removeSongFromQueue(i);
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
		    	if(!openSub)
		    	{
			    	currPlaylistSongPanels.clear();
			    	int i = 0;
			    	for(int k=0; k<playlistPanels.size(); k++)
			    	{
			    		Component[] listC = playlistPanels.get(k).getComponents();
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
			    	openSub = true;
			    	openSubIdx = i;
			    	validate();
			        repaint();
			        subSongPanels.get(i).updateUI();
		    	}
		    	else
		    	{
		    		int i = 0;
			    	for(int k=0; k<playlistPanels.size(); k++)
			    	{
			    		Component[] listC = playlistPanels.get(k).getComponents();
			    		if(listC[1] == e.getComponent())
			    		{
			    			i=k;
			    			break;
			    		}
			    	}
			    	
			    	if (i == openSubIdx)
			    	{
			    		subSongPanels.get(openSubIdx).removeAll();
			    		currPlaylistSongPanels.clear();
			    		openSub = false;
			    		validate();
				        repaint();
				        subSongPanels.get(openSubIdx).updateUI();
			    	}
		    	}
		    }  
		});
		
		playButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<playlistPanels.size(); k++)
		    	{
		    		Component[] listC = playlistPanels.get(k).getComponents();
		    		if(listC[0] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	panelQueue.removeAll();
		    	queuePanels = new ArrayList<JPanel>();
		    	panelQueue.add(new JPanel(),gbc3,-1);
		    	validate();
		        repaint();
		        panelQueue.updateUI();
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
		    		Component[] listC = playlistPanels.get(k).getComponents();
		    		if(listC[3] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	panelPlaylists.remove(playlistPanels.get(i));
		    	playlistPanels.remove(i);
		    	panelPlaylists.remove(subSongPanels.get(i));
		    	subSongPanels.remove(i);
		    	validate();
		        repaint();
		        panelPlaylists.updateUI();
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
		    	for(int k=0; k<currPlaylistSongPanels.size(); k++)
		    	{
		    		Component[] listC = currPlaylistSongPanels.get(k).getComponents();
		    		if(listC[4] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	int p = 0;
		    	for(int k=0; k<subSongPanels.size();k++)
		    	{
		    		Component[] listSub = subSongPanels.get(k).getComponents();
		    		if(listSub.length > 0 && currPlaylistSongPanels.get(0) == listSub[0])
		    		{
		    			p=k;
		    			break;
		    		}
		    	}
		    	viewSongInfo(music.viewSongInPlaylist(p,i));
		    }  
		});
	    
	    deleteButtonP.addMouseListener(new MouseAdapter()
		{  
		    public void mouseClicked(MouseEvent e)  
		    {
		    	int i = 0;
		    	for(int k=0; k<currPlaylistSongPanels.size(); k++)
		    	{
		    		Component[] listC = currPlaylistSongPanels.get(k).getComponents();
		    		if(listC[5] == e.getComponent())
		    		{
		    			i=k;
		    			break;
		    		}
		    	}
		    	
		    	int s = 0;
		    	for(int k=0; k<subSongPanels.size();k++)
		    	{
		    		Component[] listSub = subSongPanels.get(k).getComponents();
		    		if(listSub.length > 0 && currPlaylistSongPanels.get(0) == listSub[0])
		    		{
		    			s=k;
		    			break;
		    		}
		    	}
		    	
		    	(subSongPanels.get(s)).remove(currPlaylistSongPanels.get(i));
		    	currPlaylistSongPanels.remove(i);
		    	validate();
		        repaint();
		        (subSongPanels.get(s)).updateUI();
		    	music.deleteFromPlaylist(s,i);
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
	
	/** Plays playlist
	  * @pre User hits play on playlist
	  * @post Playlist added to queue and starts playing
	  * @param pIndex - index of playlist to play
	  * */
	private void playPlaylist(int pIndex)
	{
		music.playPlaylist(pIndex);

		updatePlay(music.getPlaylist(pIndex).getList().get(0));
		
		queuePanels.clear();
		panelQueue.removeAll();
		
		panelQueue.add(playQueueButtonPanel,gbc2,-1);
		
		int i = 0;
		Song tempQ = music.getQueueSong(i);
		while(tempQ != null)
		{
			queuePanels.add(createQueuePanel(tempQ));
			panelQueue.add(queuePanels.get(i),gbc2,-1);
			i++;
			tempQ = music.getQueueSong(i);
		}
		
		panelQueue.add(new JPanel(),gbc3,-1);
	}
	
	/** Adds song to playlist, creates playlist if it doesn't exist
	  * @pre User adds song to playlist
	  * @post (Playlist created and) song added to playlist
	  * @param pIndex - playlist index in list
	  * @param sIndex - song index in list
	  * @exception FailException if adding fails
	  * */
	private void addToPlaylist(int pIndex, int sID)
	{
		try
		{
			if (music.addToPlaylist(pIndex,sID))
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
	private void addToQueue(int sID)
	{
		try
		{
			if (music.addToQueue(sID))
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
		JTextField nameField = new JTextField(20);
		artistField = new JTextField(20);
		artDescField = new JTextField(20);
		albumField = new JTextField(20);
		alDescField = new JTextField(20);
		JTextField genreField = new JTextField(20);
		JTextField pathField = new JTextField(20);
		
		GridBagConstraints gbcPopup = new GridBagConstraints();
		gbcPopup.gridwidth = GridBagConstraints.REMAINDER;
		gbcPopup.weightx = 1;
		gbcPopup.fill = GridBagConstraints.HORIZONTAL;
		gbcPopup.insets = new Insets(3,3,3,3);
		
		JPanel myPanel = new JPanel(new GridBagLayout());
		gbcPopup.gridx = 0;
		gbcPopup.gridy = 0;
		gbcPopup.gridwidth = 1;
		myPanel.add(new JLabel("Name:"),gbcPopup);
		gbcPopup.gridx = 1;
		gbcPopup.gridy = 0;
		gbcPopup.gridwidth = 1;
		myPanel.add(nameField,gbcPopup);
		
		gbcPopup.gridx = 0;
		gbcPopup.gridy = 1;
		gbcPopup.gridwidth = 1;
		myPanel.add(new JLabel("Artist:"),gbcPopup);
		gbcPopup.gridx = 1;
		gbcPopup.gridy = 1;
		gbcPopup.gridwidth = 1;
		myPanel.add(artistField,gbcPopup);
		
		gbcPopup.gridx = 0;
		gbcPopup.gridy = 2;
		gbcPopup.gridwidth = 1;
		myPanel.add(new JLabel("Description:"),gbcPopup);
		gbcPopup.gridx = 1;
		gbcPopup.gridy = 2;
		gbcPopup.gridwidth = 1;
		myPanel.add(artDescField,gbcPopup);
		
		gbcPopup.gridx = 0;
		gbcPopup.gridy = 3;
		gbcPopup.gridwidth = 1;
		myPanel.add(new JLabel("Album:"),gbcPopup);
		gbcPopup.gridx = 1;
		gbcPopup.gridy = 3;
		gbcPopup.gridwidth = 1;
		myPanel.add(albumField,gbcPopup);
		
		gbcPopup.gridx = 0;
		gbcPopup.gridy = 4;
		gbcPopup.gridwidth = 1;
		myPanel.add(new JLabel("Description:"),gbcPopup);
		gbcPopup.gridx = 1;
		gbcPopup.gridy = 4;
		gbcPopup.gridwidth = 1;
		myPanel.add(alDescField,gbcPopup);
		
		gbcPopup.gridx = 0;
		gbcPopup.gridy = 5;
		gbcPopup.gridwidth = 1;
		myPanel.add(new JLabel("Genre:"),gbcPopup);
		gbcPopup.gridx = 1;
		gbcPopup.gridy = 5;
		gbcPopup.gridwidth = 1;
		myPanel.add(genreField,gbcPopup);
		
		gbcPopup.gridx = 0;
		gbcPopup.gridy = 6;
		gbcPopup.gridwidth = 1;
		myPanel.add(new JLabel("Path:"),gbcPopup);
		gbcPopup.gridx = 1;
		gbcPopup.gridy = 6;
		gbcPopup.gridwidth = 1;
		myPanel.add(pathField,gbcPopup);
		
		String song = "";
		String artist = "";
		String album = "";
		String artDesc = "";
		String albDesc = "";
		String path = "";
		String genre = "";
		
		artistField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				//String desc = artistField.getText();
				String desc = getArtistDesc(artistField.getText());
		    	if(!desc.equals(""))
				{
		    		artDescField.setText(desc);
		    		artDescField.setEditable(false);
				}  
		    	else
		    	{
		    		artDescField.setEditable(true);
		    	}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				//String desc = artistField.getText();
				String desc = getArtistDesc(artistField.getText());
		    	if(!desc.equals(""))
				{
		    		artDescField.setText(desc);
		    		artDescField.setEditable(false);
				}  
		    	else
		    	{
		    		artDescField.setEditable(true);
		    	}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				//String desc = artistField.getText();
				String desc = getArtistDesc(artistField.getText());
		    	if(!desc.equals(""))
				{
		    		artDescField.setText(desc);
		    		artDescField.setEditable(false);
				}  
		    	else
		    	{
		    		artDescField.setEditable(true);
		    	}
			}
		});
		
		albumField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				String desc = getAlbumDesc(albumField.getText());
		    	if(!desc.equals(""))
				{
		    		alDescField.setText(desc);
		    		alDescField.setEditable(false);
				}  
		    	else
		    	{
		    		alDescField.setEditable(true);
		    	}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				String desc = getAlbumDesc(albumField.getText());
		    	if(!desc.equals(""))
				{
		    		alDescField.setText(desc);
		    		alDescField.setEditable(false);
				}  
		    	else
		    	{
		    		alDescField.setEditable(true);
		    	}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				String desc = getAlbumDesc(albumField.getText());
		    	if(!desc.equals(""))
				{
		    		alDescField.setText(desc);
		    		alDescField.setEditable(false);
				}  
		    	else
		    	{
		    		alDescField.setEditable(true);
		    	}
			}
		});
		
		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Please Enter Song Info", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION && 
				!nameField.getText().equals("") 
				&& !artistField.getText().equals("")
				&& !albumField.getText().equals("") 
				&& !artDescField.getText().equals("")
				&& !alDescField.getText().equals("") 
				&& !pathField.getText().equals("")
				&& !genreField.getText().equals("")) {
			song = nameField.getText();
			artist = artistField.getText();
			album = albumField.getText();
			artDesc = artDescField.getText();
			albDesc = alDescField.getText();
			path = pathField.getText();
			genre = genreField.getText();
			Song tempSong = music.addSong(song, artist, album, artDesc, albDesc, path, genre);
			
			
	    	Component[] listC = panelAll.getComponents();
	    	panelAll.remove(listC[listC.length-1]);
			
			allPanels.add(createSongPanel(tempSong));
			panelAll.add(allPanels.get(allPanels.size()-1),gbc2, -1);

			panelAll.add(new JPanel(),gbc3,-1);
			
			validate();
	        repaint();
	        panelAll.updateUI();
	        success("Song added");
		}
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
			if (!music.playSong(sIndex))
				throw new FailException("Song not played");
			
			int sID = music.getSong(sIndex).getID();
			updatePlay(sID);
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
	private void updatePlay(int sID)
	{
		Song s = music.getSongInfo(sID);
		songInfoLabel.setText(s.getName() + " - " + s.getArtist()
				+ " - " + s.getAlbum());
	}
	
	/** Creates and shows pop-up of selected song's info
	  * @pre User clicks on "i" for song
	  * @post Pop-up shows selected song's info (modeless)
	  * @param ID - song ID
	  * */
	private void viewSongInfo(Song s)
	{
		String infoS = "";
		infoS = "Song: " + s.getName() + "\n";
		infoS += "Artist: " + s.getArtist() + "\n";
		infoS += "Description: " + s.getArtDesc() + "\n";
		infoS += "Album: " + s.getAlbum() + "\n";
		infoS += "Description: " + s.getAlDesc() + "\n";
		infoS += "Genre: " + s.getGenre() + "\n";
		infoS += "Path: " + s.getPath();
		JOptionPane.showMessageDialog(pane, infoS,"Song Info",
				JOptionPane.PLAIN_MESSAGE);
	}
	
	/** Creates add "+" menu
	  * @pre User clicks on "+"
	  * @post Pop-up shows options
	  * @param ID - song ID
	  * */
	private void plusSongPopup(Song s)
	{
		currSong = s;
		
		GridBagConstraints gbcPopup = new GridBagConstraints();
		gbcPopup.gridwidth = GridBagConstraints.REMAINDER;
		gbcPopup.weightx = 1;
		gbcPopup.fill = GridBagConstraints.HORIZONTAL;
		gbcPopup.insets = new Insets(3,3,3,3);
		
		JPanel myPanel = new JPanel(new GridBagLayout());
		JButton queueButton = new JButton("Add to Queue");
		JButton newPlaylistButton = new JButton("Add to New Playlist");
		JButton playlistButton = new JButton("Add to Playlist");
		
		gbcPopup.gridx = 0;
		gbcPopup.gridy = 0;
		gbcPopup.gridwidth = 1;
		myPanel.add(queueButton,gbcPopup);
		
		gbcPopup.gridx = 0;
		gbcPopup.gridy = 1;
		gbcPopup.gridwidth = 1;
		myPanel.add(newPlaylistButton,gbcPopup);
		
		gbcPopup.gridx = 0;
		gbcPopup.gridy = 2;
		gbcPopup.gridwidth = 1;
		myPanel.add(playlistButton,gbcPopup);
		
		queueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						addToQueue(currSong.getID());
						
						Component[] listC = panelQueue.getComponents();
						panelQueue.remove(listC[listC.length-1]);
						
						
						queuePanels.add(createQueuePanel(currSong));
						panelQueue.add(queuePanels.get(queuePanels.size()-1),gbc2, -1);

						panelQueue.add(new JPanel(),gbc3,-1);
						
						validate();
				        repaint();
				        panelQueue.updateUI();
					}
				});
			}
        });
		
		newPlaylistButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						String name = JOptionPane.showInputDialog(
						        null, 
						        "What is the new playlist name?", 
						        "Playlist name", 
						        JOptionPane.QUESTION_MESSAGE);
						
						if ((name != null) && (name.length() > 0)) {
							Playlist tempPlaylist = music.makePlaylist(name,currSong.getID());
							
							if (tempPlaylist != null)
							{
								Component[] listC = panelPlaylists.getComponents();
								panelPlaylists.remove(listC[listC.length-1]);
								
								playlistPanels.add(createPlaylistPanel(tempPlaylist));
								panelPlaylists.add(playlistPanels.get(playlistPanels.size()-1),gbc2,-1);
								subSongPanels.add(new JPanel());
								panelPlaylists.add(subSongPanels.get(subSongPanels.size()-1),gbc2,-1);
							
								panelPlaylists.add(new JPanel(),gbc3,-1);
								
								validate();
						        repaint();
						        panelPlaylists.updateUI();
							}
						    return;
						}
					}
				});
			}
        });
		
		playlistButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						ArrayList<String> optionsList = music.getPlaylistNames();
						Object[] options = optionsList.toArray();
						String s = (String)JOptionPane.showInputDialog(
								            null,
						                    "Choose playlist",
						                    "Add to playlist",
						                    JOptionPane.PLAIN_MESSAGE,
						                    null,
						                    options,
						                    "Task");
						
						if ((s != null) && (s.length() > 0)) {
							int i = optionsList.indexOf(s);
							addToPlaylist(i, currSong.getID());
						    return;
						}
					}
				});
			}
        });
		
		Object[] options = {"Cancel"};
	    int n = JOptionPane.showOptionDialog(pane, myPanel,
	                   "Add Menu",
	                   JOptionPane.PLAIN_MESSAGE,
	                   JOptionPane.PLAIN_MESSAGE,
	                   null,
	                   options,
	                   options[0]);
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

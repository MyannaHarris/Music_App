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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;


public class CoolMusicPlayerGUI extends JFrame {

	//Variables
	
	//Component container
	private Container pane;
	
	//Music class variable
	//private Music music;
	
	public CoolMusicPlayerGUI() {
		//Set title of frame
		super("Cool Music Player");
		
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
	    DefaultListModel allItems = new DefaultListModel();
		JList allList = new JList(allItems);
		allList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		allList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane allScroll = new JScrollPane(allList);
		allScroll.setMinimumSize(new Dimension(860, 600));
		allScroll.setPreferredSize(new Dimension(860,600));
		
		panel1.add(allScroll);
	    
	    //Playlists
		DefaultListModel playItems = new DefaultListModel();
		JList playList = new JList(playItems);
		playList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane playScroll = new JScrollPane(playList);
		playScroll.setMinimumSize(new Dimension(860, 600));
		playScroll.setPreferredSize(new Dimension(860,600));
		
		panel2.add(playScroll);
	    
	    //Queue
		DefaultListModel qItems = new DefaultListModel();
		JList qList = new JList(qItems);
		qList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		qList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane qScroll = new JScrollPane(qList);
		qScroll.setMinimumSize(new Dimension(860, 600));
		qScroll.setPreferredSize(new Dimension(860,600));
		
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
	    
	    setSize(900,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
}

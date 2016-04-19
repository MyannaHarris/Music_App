/**
 * 
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Allen
 *
 */
public class DBConnector {

	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "jblack2";

	/** The password for the MySQL account (or empty for anonymous) */
	//comment change
	private final String password = "jblack88947341";

	/** The name of the computer running MySQL */
	private final String serverName = "ada.gonzaga.edu";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/** The name of the database we are testing with (this default is installed with MySQL) */
	private final String dbName = "jblack2_DB";
	
	/** The name of the table we are testing with */
	private final String tableName = "Song";
	
	private Connection conn;
	
	public DBConnector() {
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a new database connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);
		Connection conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);
		return conn;
	}
	
	public int addSong(String song, String artist, String album, String artDesc, String albDesc, String path, String genre) {
		try {
			int songID = addSongDB(song, genre, path, artist, album, artDesc, albDesc);
			return songID;
		} finally {
		}
	}
	
	public int addSongDB(String song, String genre, String path, String artist, String album, String artDesc, String albDesc) {
		try {
			int maxID = 1;
			Statement s2 = conn.createStatement();
			s2.executeQuery("SELECT MAX(song_id) FROM Song");
			ResultSet rs2 = s2.getResultSet();
			if (rs2.next() ) {
				maxID = rs2.getInt(1);
			}
			maxID++;
			String query = " insert into Song (song_id, song_name, genre_name, song_path)" + " values(?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, maxID);
			stmt.setString(2, song);
			stmt.setString(3, genre);
			stmt.setString(4,  path);
			stmt.execute();
			Statement s3 = conn.createStatement();
			s3.executeQuery("SELECT artist_name FROM Artist WHERE artist_name = " + "'" + artist + "'");
			ResultSet rs3 = s3.getResultSet();
			if(!(rs3.next())) {
				this.addArtist(artist, artDesc);
			}
			Statement s4 = conn.createStatement();
			s4.executeQuery("SELECT album_name FROM Album WHERE album_name = " + "'" + album + "'");
			ResultSet rs4 = s4.getResultSet();
			if(!(rs4.next())) {
				this.addAlbum(album, albDesc);
			}
			this.addContributingArtistsDB(artist, song, maxID);
			this.addSongLocationDB(song, album, maxID);
			this.addCollaborativeCreditDB(artist, album);
			return maxID;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void addAlbum(String album, String albDesc) {
		try {
			int maxID = 1;
			Statement s2 = conn.createStatement();
			s2.executeQuery("SELECT MAX(album_id) FROM Album");
			ResultSet rs2 = s2.getResultSet();
			if (rs2.next() ) {
				maxID = rs2.getInt(1);
			}
			maxID++;
			String query = " insert into Album (album_id, album_name, album_desc)" + " values(?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, maxID);
			stmt.setString(2, album);
			stmt.setString(3, albDesc);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	public void addArtist(String artist, String artDesc) {
		try {
			int maxID = 1;
			Statement s2 = conn.createStatement();
			s2.executeQuery("SELECT MAX(artist_id) FROM Artist");
			ResultSet rs2 = s2.getResultSet();
			if (rs2.next() ) {
				maxID = rs2.getInt(1);
			}
			maxID++;
			String query = " insert into Artist(artist_id, artist_name, artist_desc)" + " values(?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, maxID);
			stmt.setString(2, artist);
			stmt.setString(3, artDesc);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	public boolean addContributingArtistsDB(String artist, String song, int sID){
		try {
			Statement s2 = conn.createStatement();
			s2.executeQuery("SELECT artist_id FROM Artist WHERE artist_name = " + "'" + artist + "'");
			ResultSet rs2 = s2.getResultSet();
			rs2.next();
			int aID = rs2.getInt(1);
			String query = " insert into Contributing_Artists (artist_id, song_id)" + " values(?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, aID);
			stmt.setInt(2, sID);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addSongLocationDB(String song, String album, int sID) {
		try {
			Statement s2 = conn.createStatement();
			s2.executeQuery("SELECT album_id FROM Album WHERE album_name = " + "'" + album + "'");
			ResultSet rs2 = s2.getResultSet();
			rs2.next();
			int aID = rs2.getInt(1);
			String query = " insert into Song_Location (song_id, album_id)" + " values(?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, sID);
			stmt.setInt(2, aID);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addCollaborativeCreditDB(String artist, String album) {
		try {
			Statement s2 = conn.createStatement();
			s2.executeQuery("SELECT artist_id FROM Artist WHERE artist_name = " + "'" + artist + "'");
			ResultSet rs2 = s2.getResultSet();
			int artID = 0;
			rs2.first();
			if(rs2.first()) {
				artID = rs2.getInt(1);
			}
			else {
				return false;
			}
			Statement s3 = conn.createStatement();
			s3.executeQuery("SELECT album_id FROM Album WHERE album_name = " + "'" + album + "'");
			ResultSet rs3 = s3.getResultSet();
			int albID = 0;
			rs3.first();
			if(rs3.first()) {
				albID = rs3.getInt(1);
			}
			else {
				return false;
			}
			String query = " insert into Collaborative_Credit (artist_id, album_id)" + " values(?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, artID);
			stmt.setInt(2, albID);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getAlbumDesc(String album) {
		try {
			String albDesc = "";
			Statement s2 = conn.createStatement();
			s2.executeQuery("SELECT album_desc FROM Album WHERE album_name = " + "'" + album + "'");
			ResultSet rs2 = s2.getResultSet();
			if (rs2.next()) {
				albDesc = rs2.getString(1);
			}
			return albDesc;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return "";
	}
	
	public String getArtistDesc(String artist) {
		try {
			String artDesc = "";
			Statement s2 = conn.createStatement();
			s2.execute("SELECT artist_desc FROM Artist WHERE artist_name = " + "'" + artist + "'");
			ResultSet rs2 = s2.getResultSet();
			if (rs2.next()) {
				artDesc = rs2.getString(1);
			}
			return artDesc;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return "";
	}
	
	public ResultSet getAllSongs() {
		try {
			Statement s2 = conn.createStatement();
			s2.execute("SELECT song_id, song_name, genre_name, song_path, artist_name, artist_desc, album_name, album_desc FROM Song natural join Artist natural join Album natural join Contributing_Artists natural join Collaborative_Credit natural join Song_Location");
			ResultSet resultAllSongs = s2.getResultSet();
			return resultAllSongs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet getAllPlaylist() {
		try {
			Statement s2 = conn.createStatement();
			s2.execute("SELECT playlist_id, playlist_name, song_id FROM Playlist natural join Song natural join Playlist_assignment");
			ResultSet resultAllPlaylist = s2.getResultSet();
			return resultAllPlaylist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean addToPlaylist(int pID, int sID) {
		try {
			int maxID = 1;
			Statement s2 = conn.createStatement();
			s2.executeQuery("SELECT MAX(assignment_id) FROM Playlist_assignment");
			ResultSet rs2 = s2.getResultSet();
			if (rs2.next() ) {
				maxID = rs2.getInt(1);
			}
			maxID++;
			Statement s3 = conn.createStatement();
			s3.executeQuery("SELECT song_name FROM Song WHERE song_id = " + sID);
			ResultSet rs3 = s3.getResultSet();
			rs3.next();
			String songName = rs3.getString("song_name");
			String query = " insert into Playlist_assignment (assignment_id, playlist_id, song_id, song_name)" + " values(?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, maxID);
			stmt.setInt(2, pID);
			stmt.setInt(3, sID);
			stmt.setString(4, songName);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int addPlaylist(String playlist, int sID) {
		try {
			int maxID = 1;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT MAX(playlist_id) FROM Playlist");
			ResultSet rs2 = s2.getResultSet();
			if (rs2.next() ) {
				maxID = rs2.getInt("MAX(playlist_id)");
			}
			maxID++;
			String query = " insert into Playlist (playlist_id, playlist_name)" + " values(?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, maxID);
			stmt.setString(2, playlist);
			stmt.execute();
			this.addToPlaylist(maxID, sID);
			return maxID;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void removePlaylist(int pID) {
		try {
			String query = " delete from Playlist where playlist_id=" + "?";
			PreparedStatement stmt = conn.prepareStatement(query);
	    	stmt.setInt(1, pID);
	    	stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeSong(int sID) {
		try {
			String query = " delete from Song where song_id=" + "?";
			PreparedStatement stmt = conn.prepareStatement(query);
	    	stmt.setInt(1, sID);
	    	stmt.execute();
			String query2 = " delete from Song_Location where song_id=" + "?";
			PreparedStatement stmt2 = conn.prepareStatement(query2);
			stmt2.setInt(1, sID);
			stmt2.execute();
			String query3 = " delete from Contributing_Artists where song_id=" + "?";
			PreparedStatement stmt3 = conn.prepareStatement(query3);
			stmt3.setInt(1, sID);
			stmt3.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeFromPlaylist(int pID, int sID) {
		try {
			String query = " delete from Playlist_assignment where playlist_id= " + "?" + " and " + "song_id=" + "?";
			PreparedStatement stmt = conn.prepareStatement(query);
	    	stmt.setInt(1, pID);
			stmt.setInt(2, sID);
	    	stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

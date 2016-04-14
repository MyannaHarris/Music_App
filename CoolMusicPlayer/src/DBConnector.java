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
	
	public void addAlbum(String album, String albDesc) {
		try {
			int maxID = 1;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT MAX(album_id) FROM album");
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
			s2.execute("SELECT MAX(artist_id) FROM Artist");
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
	
	public int addSongDB(String song, String genre, String path, String artist, String album, String artDesc, String albDesc) {
		try {
			int maxID = 1;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT MAX(song_id) FROM Song");
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
			s3.execute("SELECT artist_name FROM Artist WHERE artist_name = " + "'" + artist + "'");
			ResultSet rs3 = s3.getResultSet();
			if(rs3.getString("artist_name") != artist) {
				this.addArtist(artist, artDesc);
			}
			Statement s4 = conn.createStatement();
			s4.execute("SELECT album_name FROM Album WHERE album_name = " + "'" + album + "'");
			ResultSet rs4 = s4.getResultSet();
			if(rs4.getString("album_name") != album) {
				this.addAlbum(album, albDesc);
			}
			this.addContributingArtistsDB(artist, song, maxID);
			this.addSongLocationDB(song, album, maxID);
			return maxID;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return 0;
		}
	}
	
	public boolean addContributingArtistsDB(String artist, String song, int sID){
		try {
			Statement s2 = conn.createStatement();
			s2.execute("SELECT artist_id FROM Artist WHERE artist_name = " + "'" + artist + "'");
			ResultSet rs2 = s2.getResultSet();
			int aID = rs2.getInt(1);
			String query = " insert into Contributing_Artists (artist_id, song_id)" + " values(?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, aID);
			stmt.setInt(2, sID);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return false;
		}
	}
	
	public boolean addSongLocationDB(String song, String album, int sID) {
		try {
			Statement s2 = conn.createStatement();
			s2.execute("SELECT album_id FROM Album WHERE album_name = " + "'" + album + "'");
			ResultSet rs2 = s2.getResultSet();
			int aID = rs2.getInt(1);
			String query = " insert into Song_Location (song_id, album_id)" + " values(?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, sID);
			stmt.setInt(2, aID);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return false;
		}
	}
	
	public String getAlbumDesc(String album) {
		try {
			String albDesc;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT album_desc FROM Album WHERE album_name = " + "'" + album + "'");
			ResultSet rs2 = s2.getResultSet();
			albDesc = rs2.getString(1);
			return albDesc;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return "";
	}
	
	public String getArtistDesc(String artist) {
		try {
			String artDesc;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT artist_desc FROM Artist WHERE artist_name = " + "'" + artist + "'");
			ResultSet rs2 = s2.getResultSet();
			artDesc = rs2.getString(1);
			return artDesc;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return "";
	}
	
	public ResultSet getAllSongs() {
		return null;
	}
	
	public ResultSet getAllPlaylist () {
		return null;
	}
	
	public boolean addToPlaylist(int pID, int sID) {
		try {
			int maxID = 1;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT MAX(assignment_id) FROM Playlist_assignment");
			ResultSet rs2 = s2.getResultSet();
			if (rs2.next() ) {
				maxID = rs2.getInt(1);
			}
			maxID++;
			Statement s3 = conn.createStatement();
			s3.execute("SELECT song_name FROM Song WHERE song_id = " + sID);
			ResultSet rs3 = s3.getResultSet();
			String songName = rs3.getString(1);
			String query = " insert into Playlist_assignment (assignment_id, playlist_id, song_id, song_name)" + " values(?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, maxID);
			stmt.setInt(2, pID);
			stmt.setInt(3, sID);
			stmt.setString(4, songName);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return false;
		}
	}
	
	public int addPlaylist(String playlist, int sID) {
		try {
			int maxID = 1;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT MAX(playlist_id) FROM Playlist");
			ResultSet rs2 = s2.getResultSet();
			if (rs2.next() ) {
				maxID = rs2.getInt(1);
			}
			maxID++;
			String query = " insert into Playlist (playlist_id, playlist_name)" + " values(?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, maxID);
			stmt.setString(2, playlist);
			stmt.execute();
			if(this.addToPlaylist(maxID, sID)) {
				return maxID;
			}
			else {
				maxID = 0;
				return maxID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return 0;
		}
	}
	
	public void removePlaylist(int pID) {
		
	}
	
	public boolean removeSongs(int sID) {
		return false;
	}
	
	public void removeFromPlaylist(int pID, int sID) {
		return;
	}
}

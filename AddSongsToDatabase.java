import java.sql.*;

public class AddSongsToDatabase {

    public static void main(String[] args) {
        // Konfigurasi koneksi ke database
        String jdbcURL = "jdbc:mysql://localhost:3307/music_player"; 
        String dbUser = "root"; 
        String dbPassword = "12345"; 

        // Lagu-lagu untuk dimasukkan ke database
        Song[] songs = {
            new Song(1, "Song 1", "Artist 1", "res/song1.wav", "res/thumb1.jpg"),
            new Song(2, "Song 2", "Artist 2", "res/song2.wav", "res/thumb2.jpg"),
            new Song(3, "Song 3", "Artist 3", "res/song3.wav", "res/thumb3.jpg"),
            new Song(4, "Song 4", "Artist 4", "res/song4.wav", "res/thumb4.jpg"),
            new Song(5, "Song 5", "Artist 5", "res/song5.wav", "res/thumb5.jpg")
        };

        // Menambahkan lagu ke database
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String insertQuery = "INSERT INTO songs (id, title, artist, songPath, thumbnailPath) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = conn.prepareStatement(insertQuery)) {
                for (Song song : songs) {
                    statement.setInt(1, song.getId());
                    statement.setString(2, song.getTitle());
                    statement.setString(3, song.getArtist());
                    statement.setString(4, song.getSongPath());
                    statement.setString(5, song.getThumbnailPath());
                    statement.executeUpdate();
                    System.out.println("Added: " + song.getTitle());
                }
            }

            System.out.println("All songs added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Kelas Song untuk mendefinisikan lagu
class Song {
    private int id;
    private String title, artist, songPath, thumbnailPath;

    public Song(int id, String title, String artist, String songPath, String thumbnailPath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.songPath = songPath;
        this.thumbnailPath = thumbnailPath;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getSongPath() { return songPath; }
    public String getThumbnailPath() { return thumbnailPath; }
}
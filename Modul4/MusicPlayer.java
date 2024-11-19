package Modul4;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer extends JFrame {
    private JList<Song> playlist;
    private JLabel songInfoLabel, durationLabel, thumbnailLabel;
    private JProgressBar progressBar;
    private JButton toggleButton; // Combined Play/Stop button
    private JButton prevButton;
    private JButton nextButton;
    private Clip clip;
    private List<Song> songs;
    private boolean isPlaying = false;
    private long pausedPosition = 0;
    private int lastPlayedIndex = -1; // Track the last played song index

    public MusicPlayer() {
        super("Music Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Playlist
        playlist = new JList<>();
        playlist.setCellRenderer(new SongListCellRenderer());
        playlist.addListSelectionListener(e -> displaySongDetails());

        JScrollPane playlistScrollPane = new JScrollPane(playlist);
        playlistScrollPane.setPreferredSize(new Dimension(250, 0));
        playlist.setBackground(Color.WHITE);
        add(playlistScrollPane, BorderLayout.WEST);

        // Song Details Panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        songInfoLabel = new JLabel("Select a song");
        songInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        detailsPanel.add(songInfoLabel, gbc);

        gbc.gridy = 1;
        thumbnailLabel = new JLabel(new ImageIcon("none.png"));
        thumbnailLabel.setPreferredSize(new Dimension(150, 150));
        detailsPanel.add(thumbnailLabel, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout()); // Use FlowLayout for horizontal arrangement

        // Add Prev Button
        prevButton = new JButton("Prev");
        prevButton.addActionListener(e -> playPrevSong());
        buttonPanel.add(prevButton);

        // Add Play/Stop Button
        toggleButton = new JButton("Play");
        toggleButton.addActionListener(e -> {
            if (isPlaying) {
                stopPlayback();
                toggleButton.setText("Play");
            } else {
                playSelectedSong();
                toggleButton.setText("Stop");
            }
        });
        buttonPanel.add(toggleButton);

        // Add Next Button
        nextButton = new JButton("Next");
        nextButton.addActionListener(e -> playNextSong());
        buttonPanel.add(nextButton);

        gbc.gridy = 2;
        detailsPanel.add(buttonPanel, gbc);

        // Progress Panel
        JPanel progressPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 3;
        gbc.gridx = 0;

        durationLabel = new JLabel("Duration: 0:00 / 0:00");
        progressBar = new JProgressBar();
        progressBar.setStringPainted(false);

        progressPanel.add(durationLabel, gbc);
        gbc.gridy = 1;
        progressPanel.add(progressBar, gbc);

        gbc.gridy = 4;
        detailsPanel.add(progressPanel, gbc);

        add(detailsPanel, BorderLayout.CENTER);

        loadSongsFromDatabase();
        setVisible(true);
    }

    private void loadSongsFromDatabase() {
        songs = new ArrayList<>();
        DefaultListModel<Song> listModel = new DefaultListModel<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM songs");

            while (rs.next()) {
                Song song = new Song(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("artist"),
                        rs.getString("songPath"),
                        rs.getString("thumbnailPath")
                );
                songs.add(song);
                listModel.addElement(song);
            }

            playlist.setModel(listModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading songs from database: " + e.getMessage());
        }
    }

    private void displaySongDetails() {
        int selectedIndex = playlist.getSelectedIndex();
        if (selectedIndex != -1) {
            Song song = songs.get(selectedIndex);
            songInfoLabel.setText(song.getTitle() + " - " + song.getArtist());
            durationLabel.setText("Duration: " + getSongDuration(song.getSongPath()) + " / " + getTotalDuration(song.getSongPath()));
            thumbnailLabel.setIcon(new ImageIcon(song.getThumbnailPath()));
        }
    }

    private void playSelectedSong() {
        int selectedIndex = playlist.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a song to play.");
            return;
        }

        Song song = songs.get(selectedIndex);

        try {
            // If the same song is selected, continue from the paused position
            if (lastPlayedIndex == selectedIndex && clip != null && clip.isOpen()) {
                clip.setMicrosecondPosition(pausedPosition);
                clip.start();
            } else {
                // Reset paused position for a new song
                pausedPosition = 0;
                stopPlayback(); // Stop any currently playing song

                // Start playing the new song from the beginning
                File audioFile = new File(song.getSongPath());
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.setMicrosecondPosition(0); // Start from the beginning
                clip.start();
                lastPlayedIndex = selectedIndex; // Update last played index

                // Automatically change toggle button to "Stop"
                toggleButton.setText("Stop");
            }

            isPlaying = true;

            // Update progress bar
            progressBar.setMaximum((int) clip.getMicrosecondLength() / 1000);
            new Thread(() -> {
                while (clip.isRunning()) {
                    long currentPosition = clip.getMicrosecondPosition() / 1000;
                    progressBar.setValue((int) currentPosition);
                    updateDurationLabel(currentPosition, clip.getMicrosecondLength() / 1000);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error playing song: " + e.getMessage());
        }
    }

    private void updateDurationLabel(long currentPosition, long totalLength) {
        String currentTime = formatTime(currentPosition);
        String totalTime = formatTime(totalLength);
        durationLabel.setText("Duration: " + currentTime + " / " + totalTime);
    }

    private String formatTime(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void stopPlayback() {
        if (clip != null && clip.isRunning()) {
            pausedPosition = clip.getMicrosecondPosition(); // Save the last position
            clip.stop(); // Stop playback without closing the clip
            isPlaying = false; // Update state
        }
    }

    private String getSongDuration(String songPath) {
        return "3:30"; // Example, replace with actual duration logic if necessary
    }

    private String getTotalDuration(String songPath) {
        return "3:30"; // Example, replace with actual duration logic if necessary
    }

    private void playPrevSong() {
        int currentIndex = playlist.getSelectedIndex();
        if (currentIndex > 0) {
            playlist.setSelectedIndex(currentIndex - 1);
            playSelectedSong();
        } else {
            JOptionPane.showMessageDialog(this, "No previous song.");
        }
    }

    private void playNextSong() {
        int currentIndex = playlist.getSelectedIndex();
        if (currentIndex < songs.size() - 1) {
            playlist.setSelectedIndex(currentIndex + 1);
            playSelectedSong(); // This will now play the next song from the beginning
        } else {
            JOptionPane.showMessageDialog(this, "No next song.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MusicPlayer::new);
    }

    // Custom cell renderer for displaying song title and thumbnail
    static class SongListCellRenderer extends DefaultListCellRenderer {
        private final int thumbnailWidth = 50;
        private final int thumbnailHeight = 50;
        private final ImageIcon defaultIcon = new ImageIcon("default_music_thumbnail.png");

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Song song = (Song) value;
            JLabel label = new JLabel(song.getTitle() + " - " + song.getArtist());

            ImageIcon icon = loadImageIcon(song.getThumbnailPath());
            label.setIcon(icon != null ? icon : defaultIcon);

            label.setIconTextGap(10);

            if (isSelected) {
                label.setBackground(Color.LIGHT_GRAY);
                label.setOpaque(true);
            } else {
                label.setOpaque(false);
            }

            return label;
        }

        private ImageIcon loadImageIcon(String path) {
            try {
                ImageIcon icon = new ImageIcon(path);
                if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
                    return new ImageIcon(icon.getImage().getScaledInstance(thumbnailWidth, thumbnailHeight, Image.SCALE_SMOOTH));
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
            return null;
        }
    }
}



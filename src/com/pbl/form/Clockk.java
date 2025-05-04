
package com.pbl.form;
import com.pbl.dao.TrackDAO;
import com.pbl.dao.songDAO;
import com.pbl.model.Song;
import static com.pbl.model.Song.DEFAULT_SONG_PATHS;
import static com.pbl.model.Song.DEFAULT_SONG_TITLES;
import com.pbl.model.Track;
import static com.pbl.model.Track.DEFAULT_TONE_PATHS;
import static com.pbl.model.Track.DEFAULT_TONE_TITLES;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.util.List;    
import javax.swing.filechooser.FileNameExtensionFilter;


public class Clockk extends javax.swing.JPanel implements Runnable{
    String sound, title;
    private FileInputStream alarmInputStream;
    private BufferedInputStream alarmBufferedInputStream;
    Player alarmPlayer;
    long all;

    private String customSoundPath;
    private String customTitle;
    private InputStream musicInputStream;
    private BufferedInputStream musicBufferedInputStream;
    private Player musicPlayer;
    private long musicTotalSongLength;
    private long musicPausedOnFrame;
    private boolean musicIsPaused = false;

    String hours, hh, mm;
    String hourAlarm, minuteAlarm;
    private volatile boolean alarmCanceled = false;

    private JButton jBListen;
    private JButton jBRingtone;
    private JButton jBSetAlarm;
    private JButton jBCancelAlarm;
    private JButton jButtonSetSong;
    private JButton jButtonPause;
    private JComboBox<String> jComboBox1;
    private JComboBox<String> jComboBox2; 
    private JLabel jLabelTitle;
    private JLabel jLabelClock;
    private JLabel jLabelHour;
    private JLabel jLabelMinute;
    private JLabel jLabelToneIcon;
    private JLabel jLabelToneName;
    private JLabel jLabelStatus;
    private JLabel jLabelMusicIcon;
    private JLabel jLabelSongName;
    private JLayeredPane jPlayered;
    private JComboBox<Track> cboTone;
    private JComboBox<Song>  cboSong;
    private JButton jBAddSong;
    private JButton jBAddTone;
    private byte[] currentToneBytes;    
    private byte[] currentSongBytes;
    private Player tonePlayer;
    
    private final ImageIcon checkMarkIcon = new ImageIcon(getClass().getResource("/com/pbl/icon/check-mark.png"));
    private final ImageIcon checkedIcon = new ImageIcon(getClass().getResource("/com/pbl/icon/checked.png"));
   
    public Clockk() {
       buildGUI();
        Thread t = new Thread(this);
        t.start();
        loadIcons();
        Calendar c = Calendar.getInstance();
        hh = new SimpleDateFormat("HH").format(c.getTime());
        mm = new SimpleDateFormat("mm").format(c.getTime());
        jComboBox1.setSelectedItem(hh);
        jComboBox2.setSelectedItem(mm);

        jBListen.setEnabled(false);
        jBCancelAlarm.setEnabled(false);
    }
    
    private void loadIcons() {
        jLabelToneIcon.setIcon(new ImageIcon(getClass().getResource("/com/pbl/icon/bell (1).png")));
        jLabelStatus.setIcon(checkMarkIcon);
        jLabelMusicIcon.setIcon(new ImageIcon(getClass().getResource("/com/pbl/icon/listen.png")));
        jLabelTitle.setIcon(new ImageIcon(getClass().getResource("/com/pbl/icon/alarm.png")));
    }
    
     
    private void buildGUI() {
        Color ivory = new Color(255, 255, 255);

        setLayout(new BorderLayout(20, 20));
        setBackground(ivory);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        header.setBackground(ivory);
        jLabelTitle = new JLabel("ALARM CLOCK", SwingConstants.CENTER);
        jLabelTitle.setFont(new Font("Segoe UI", Font.BOLD, 44)); 
        header.add(jLabelTitle);
        add(header, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        mainPanel.setOpaque(true);
        mainPanel.setBackground(ivory);

        // Alarm panel
        JPanel alarmPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        alarmPanel.setOpaque(true);
        alarmPanel.setBackground(ivory);
        TitledBorder alarmBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 50), 2),
            "Alarm", TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 38), new Color(0, 100, 0)
        );
        alarmPanel.setBorder(alarmBorder);

        // Clock display
        jLabelClock = new JLabel("00:00:00", SwingConstants.CENTER);
        jLabelClock.setFont(new Font("Segoe UI", Font.BOLD, 32)); // in đậm
        jLabelClock.setOpaque(true);
        jLabelClock.setBackground(new Color(255,239,213));
        alarmPanel.add(jLabelClock);

        // Spinner giờ và phút
        JPanel hm = new JPanel(new GridLayout(1, 2, 10, 10));
        hm.setOpaque(true);
        hm.setBackground(ivory);
        jLabelHour = new JLabel("Hour", SwingConstants.CENTER);
        jLabelHour.setFont(new Font("Segoe UI", Font.BOLD, 28)); // in đậm
        jLabelMinute = new JLabel("Minute", SwingConstants.CENTER);
        jLabelMinute.setFont(new Font("Segoe UI", Font.BOLD, 28));

        hm.add(jLabelHour); hm.add(jLabelMinute);;
        alarmPanel.add(hm);

        JPanel combo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 100, 10));
        jComboBox1 = new JComboBox<>(generateArray(24));
        jComboBox1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jComboBox1.setPreferredSize(new Dimension(110, 40));

        jComboBox2 = new JComboBox<>(generateArray(60));
        jComboBox2.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jComboBox2.setPreferredSize(new Dimension(110, 40));
        combo.add(jComboBox1);
        combo.add(jComboBox2);
        combo.setBackground(ivory);
        alarmPanel.add(combo);

        // Tone selection
        JPanel tone = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        tone.setOpaque(true);
        tone.setBackground(ivory);

        jBAddTone = new JButton("Add Tone");
        jBAddTone.setMargin(new Insets(2, 10, 2, 10));
        jBAddTone.setPreferredSize(new Dimension(100, 35));

        jLabelToneIcon = new JLabel();

        cboTone = new JComboBox<>();
        cboTone.setPreferredSize(new Dimension(250, 40));
        loadCombo(cboTone);

        tone.add(jLabelToneIcon);
        tone.add(cboTone);
        tone.add(jBAddTone);
        alarmPanel.add(tone);

        // Buttons row 1: Set Ring và Listen
        JPanel ringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        ringPanel.setOpaque(true);
        ringPanel.setBackground(ivory);
        jBRingtone = new JButton("Set Ring");
        jBRingtone.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jBRingtone.setBackground(new Color(144, 238, 144));
        jBRingtone.setForeground(Color.DARK_GRAY);
        jBListen = new JButton("Listen");
        jBListen.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jBListen.setBackground(new Color(255, 215, 0)); 
        jBListen.setForeground(Color.DARK_GRAY);
        ringPanel.add(jBRingtone); ringPanel.add(jBListen);
        alarmPanel.add(ringPanel);

        // Buttons row 2: Set Alarm, Status icon, Cancel
            JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            controlPanel.setOpaque(true);
            controlPanel.setBackground(ivory);
            jBSetAlarm = new JButton("Set Alarm");
            jBSetAlarm.setFont(new Font("Segoe UI", Font.BOLD, 24));
            jBSetAlarm.setBackground(new Color(255, 182, 193)); // hồng nhạt
            jBSetAlarm.setForeground(Color.DARK_GRAY);
            jLabelStatus = new JLabel();
            jBCancelAlarm = new JButton("Cancel");
            jBCancelAlarm.setFont(new Font("Segoe UI", Font.BOLD, 24));
            jBCancelAlarm.setBackground(new Color(211, 211, 211)); // xám
            jBCancelAlarm.setForeground(Color.DARK_GRAY);
            controlPanel.add(jBSetAlarm); controlPanel.add(jLabelStatus); controlPanel.add(jBCancelAlarm);
            alarmPanel.add(controlPanel);

        mainPanel.add(alarmPanel);

        // Music panel
        JPanel musicPanel = new JPanel(new GridLayout(3,1,10, 10));
        musicPanel.setOpaque(true);
        musicPanel.setBackground(ivory);
        TitledBorder musicBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 50), 2),
            "Music Player", TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 36), new Color(0, 100, 0)
        );
        musicPanel.setBorder(musicBorder);

        JPanel topMusic = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topMusic.setOpaque(true);
        topMusic.setBackground(ivory);
        jLabelMusicIcon = new JLabel();
        cboSong = new JComboBox<>();
        cboSong.setPreferredSize(new Dimension(300,37));
        loadSongCombo(cboSong);
        jBAddSong = new JButton("Add Song");
        jBAddSong.setMargin(new Insets(2, 10, 2, 10));
        jBAddSong.setPreferredSize(new Dimension(100, 35));
        jBAddSong.addActionListener(e -> onAddSong());

        jLabelMusicIcon = new JLabel();
        jLabelSongName = new JLabel();
        topMusic.add(jLabelMusicIcon); topMusic.add(cboSong); topMusic.add(jBAddSong);

         JPanel top = new JPanel(new BorderLayout());
        top.add(topMusic, BorderLayout.SOUTH);
        musicPanel.add(topMusic);

        JPanel set2 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        JLabel musicon = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/pbl/icon/headphones (1).png"));
        musicon.setIcon(icon);
        set2.add(musicon);
        set2.setBackground(ivory);
        musicPanel.add(set2);

        JPanel musicButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        musicButtons.setOpaque(true);
        musicButtons.setBackground(ivory);
        jButtonSetSong = new JButton("Set Song");
        jButtonSetSong.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jButtonSetSong.setBackground(new Color(135, 206, 235)); 
        jButtonSetSong.setForeground(Color.DARK_GRAY);
        jButtonPause = new JButton("Pause");
        jButtonPause.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jButtonPause.setBackground(new Color(255, 160, 122)); 

        Dimension btnSize = new Dimension(160, 45);        
        jButtonSetSong.setPreferredSize(btnSize);
        jButtonSetSong.setMinimumSize(btnSize);
        jButtonSetSong.setMaximumSize(btnSize);

        jButtonPause.setPreferredSize(btnSize);
        jButtonPause.setMinimumSize(btnSize);
        jButtonPause.setMaximumSize(btnSize);

        musicButtons.add(jButtonSetSong); musicButtons.add(jButtonPause);
        musicPanel.add(musicButtons);
        mainPanel.add(musicPanel);

        add(mainPanel, BorderLayout.CENTER);

        // --- Listeners ---

        jBListen.addActionListener(e -> {
        if (alarmPlayer != null) {
            alarmPlayer.close();
            alarmPlayer = null;
            jBListen.setText("Listen");
        } else if (sound != null) {
            playTone(sound);
            jBListen.setText("Stop");
        }
    });
        jBAddTone.addActionListener(e -> onAddTone());
        jBSetAlarm.addActionListener(e -> setAlarm());
        jBCancelAlarm.addActionListener(e -> cancelAlarm());
        jButtonPause.addActionListener(e -> {
      if (musicPlayer != null) {
        pauseSong();   // dừng
        jButtonPause.setText("Continue");
      } else {
        continueSong();
        jButtonPause.setText("Pause");
      }
    });
        jBRingtone.addActionListener(e -> chooseToneFromCombo());
        jButtonSetSong.addActionListener(e -> chooseSongFromCombo());
          }
        private void loadCombo(JComboBox<Track> cb) {
        DefaultComboBoxModel<Track> m = new DefaultComboBoxModel<>();

        for (int i = 0; i < DEFAULT_TONE_PATHS.length; i++) {
            m.addElement(new Track(
                -1,
                DEFAULT_TONE_TITLES[i],
                "RES:" + DEFAULT_TONE_PATHS[i]
            ));
        }

        List<Track> userList = TrackDAO.loadTracks();       
        userList.forEach(m::addElement);

        cb.setModel(m);
        if (m.getSize() > 0) cb.setSelectedIndex(0);
    }
          private void loadSongCombo(JComboBox<Song> cb) {
        DefaultComboBoxModel<Song> m = new DefaultComboBoxModel<>();

        for (int i = 0; i < DEFAULT_SONG_PATHS.length; i++) {
            m.addElement(new Song(
                -1,
                DEFAULT_SONG_TITLES[i],
                "RES:" + DEFAULT_SONG_PATHS[i]
            ));
        }
        List<Song> userList = songDAO.loadSongs();           
        userList.forEach(m::addElement);

        cb.setModel(m);
        if (m.getSize() > 0) cb.setSelectedIndex(0);
}
 
    private void chooseToneFromCombo(){
         Track t = (Track)cboTone.getSelectedItem();
        if (t == null) return;
        sound = t.getFilePath();
        jBListen.setEnabled(true);
        jBListen.setText("Listen");
    }

    private void chooseSongFromCombo() {
        Song s = (Song)cboSong.getSelectedItem();
        if (s == null) return;
        customSoundPath = s.getFilePath();
        customTitle     = s.getTitle();
        jLabelSongName.setText(customTitle);
        playSong(customSoundPath);
}

  
    
    private String[] generateArray(int n) {
        String[] arr = new String[n];
        for (int i = 0; i < n; i++) arr[i] = String.format("%02d", i);
        return arr;
    }

    private void startAlarm() throws Exception {
    InputStream in;
    if (sound.startsWith("RES:")) {
        String res = sound.substring(4);
        in = getClass().getClassLoader()
                     .getResourceAsStream(res);
        if (in == null) throw new FileNotFoundException(res);
    } else {
        in = new FileInputStream(sound);
    }
    BufferedInputStream buf = new BufferedInputStream(in);
    alarmPlayer = new Player(buf);
    new Thread(() -> {
        try { alarmPlayer.play(); }
        catch (Exception ex){ ex.printStackTrace(); }
    }).start();
}


    public void stopAlarm() {
        if (alarmPlayer != null) alarmPlayer.close();
    }

    public void alarmTime(int h, int m) {
        alarmCanceled = false;
    new Thread(() -> {
        while (!alarmCanceled) {
            Calendar now = Calendar.getInstance();
            if (now.get(Calendar.HOUR_OF_DAY) == h &&
                now.get(Calendar.MINUTE)      == m) {

                try { 
                    startAlarm();
                } catch (Exception ex) {
                    Logger.getLogger(Clockk.class.getName()).log(Level.SEVERE, null, ex);
                }
                SwingUtilities.invokeLater(() -> {
                    
                    JOptionPane.showMessageDialog(this, "DA DEN GIO HEN");
                    stopAlarm();                 
                    jLabelStatus.setIcon(checkMarkIcon); 
                    jBCancelAlarm.setEnabled(false);
                });
                break;          
            }
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }).start();
    }

    private void pauseSong() {
        try {
            musicPausedOnFrame = musicBufferedInputStream.available();
            if (musicPlayer != null) musicPlayer.close();
            musicPlayer = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    public void continueSong() {
        if (customSoundPath == null) {
            return;
        }
        new Thread(() -> {
            try {
                InputStream base;
                String fp = customSoundPath;
                if (fp.startsWith("RES:")) {
                    String res = fp.substring(4);
                    base = getClass().getClassLoader().getResourceAsStream(res);
                    if (base == null) throw new FileNotFoundException("Resource không tìm thấy: " + res);
                } else {
                    base = new FileInputStream(fp);
                }
                long toSkip = musicTotalSongLength - musicPausedOnFrame;
                base.skip(toSkip);

                musicBufferedInputStream = new BufferedInputStream(base);
                if (musicPlayer != null) musicPlayer.close();
                musicPlayer = new Player(musicBufferedInputStream);

                musicPlayer.play();

            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(
                        this,
                        "Lỗi tiếp tục nhạc: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    )
                );
            }
        }).start();
    }




   private void playSong(String fp) {
        new Thread(() -> {
            try {
                if (fp.startsWith("RES:")) {
                    String resPath = fp.substring(4);
                    musicInputStream = getClass()
                        .getClassLoader()
                        .getResourceAsStream(resPath);
                    if (musicInputStream == null)
                        throw new FileNotFoundException("Không tìm thấy resource: " + resPath);
                } else {
                    musicInputStream = new FileInputStream(fp);
                }
                musicTotalSongLength = musicInputStream.available();

                musicBufferedInputStream = new BufferedInputStream(musicInputStream);
                if (musicPlayer != null) musicPlayer.close();
                musicPlayer = new Player(musicBufferedInputStream);

                musicPlayer.play();
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this,
                        "Lỗi phát nhạc: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE)
                );
            }
        }).start();
    }

    private void playTone(String fp) {
        new Thread(() -> {
            try {
                InputStream in;
                if (fp.startsWith("RES:")) {
                    String resPath = fp.substring(4);  
                    in = getClass().getClassLoader()
                                  .getResourceAsStream(resPath);
                    if (in == null) {
                        throw new FileNotFoundException("Không tìm thấy resource: " + resPath);
                    }
                } else {
                    in = new FileInputStream(fp);
                }

                BufferedInputStream buf = new BufferedInputStream(in);
                if (alarmPlayer != null) {
                    alarmPlayer.close();  
                }
                alarmPlayer = new Player(buf);
                alarmPlayer.play();

            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(
                        this,
                        "Lỗi phát chuông: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    )
                );
            }
        }).start();
    }
    private void toggleListen() {
         if (jButtonPause.getText().equals("Pause")) {
            pauseSong();
            jButtonPause.setText("Continue");
        } else {                    
            continueSong();
            jButtonPause.setText("Pause");
        }
    }

    private void setAlarm() {
        hourAlarm  = (String) jComboBox1.getSelectedItem();
        minuteAlarm = (String) jComboBox2.getSelectedItem();
        alarmTime(Integer.parseInt(hourAlarm), Integer.parseInt(minuteAlarm));
        jLabelStatus.setIcon(checkedIcon);    
        jBCancelAlarm.setEnabled(true);
    }

    private void cancelAlarm() {
        alarmCanceled = true;
        stopAlarm();
        jLabelStatus.setIcon(checkMarkIcon); 
        jBCancelAlarm.setEnabled(false);
    }

    private void setSong() {
        JFileChooser jfc = new JFileChooser();
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            customSoundPath = f.getAbsolutePath();
            customTitle = f.getName();
            jLabelSongName.setText(customTitle);
            playSong(customSoundPath);
        }
    }

    private void toggleSong() {
        if (jButtonPause.getText().equals("Pause")) {
            pauseSong();
            jButtonPause.setText("Continue");
        } else {
            continueSong();
            jButtonPause.setText("Pause");
        }
    }
    
    private int currentUserId;

    private void onAddTone() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileNameExtensionFilter("MP3 Files", "mp3"));
        chooser.setDialogTitle("Chọn file MP3");

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            String title = f.getName();                    // "alarm.mp3"
            String path  = f.getAbsolutePath();            // "C:\Users\DELL\Music\alarm.mp3"

            // **KHÔNG** làm thêm: + ".mp3"
            TrackDAO.insertTrack(1, title, path);

            loadCombo(cboTone);
            cboTone.setSelectedIndex(cboTone.getItemCount() - 1);
            JOptionPane.showMessageDialog(this, "Đã thêm file: " + title);
        }
    }

    private void onAddSong() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setFileFilter(new FileNameExtensionFilter("MP3 Files", "mp3"));
    chooser.setDialogTitle("Chọn file MP3 để thêm vào Songs");

    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        File f = chooser.getSelectedFile();
        String title = f.getName();
        String path  = f.getAbsolutePath();

        songDAO.insertSong(1, title, path);
        loadSongCombo(cboSong);
        cboSong.setSelectedIndex(cboSong.getItemCount() - 1);
        JOptionPane.showMessageDialog(
            this,
            "Đã thêm bài: " + title,
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
    public void run() {
        while (true) {
            String t = new SimpleDateFormat("HH:mm:ss").format(new Date());
            jLabelClock.setText(t);
            try { Thread.sleep(500); } catch (Exception e) {}
        }
    }
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // </editor-fold>
@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
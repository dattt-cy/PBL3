
package com.pbl.form;
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

public class Clockk extends javax.swing.JPanel implements Runnable{
    String sound, title;
    private FileInputStream alarmInputStream;
    private BufferedInputStream alarmBufferedInputStream;
    Player alarmPlayer;
    long all;

    private String customSoundPath;
    private String customTitle;
    private FileInputStream musicInputStream;
    private BufferedInputStream musicBufferedInputStream;
    private Player musicPlayer;
    private long musicTotalSongLength;
    private long musicPausedOnFrame;
    private boolean musicIsPaused = false;

    String hours, hh, mm;
    String hourAlarm, minuteAlarm;
    private volatile boolean alarmCanceled = false;

    // Variables declaration - do not modify
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
    
    private final ImageIcon checkMarkIcon = new ImageIcon(getClass().getResource("/com/pbl/icon/check-mark.png"));
    private final ImageIcon checkedIcon = new ImageIcon(getClass().getResource("/com/pbl/icon/checked.png"));
   
    public Clockk() {
       buildGUI();
        Thread t = new Thread(this);
        t.start();
        loadIcons();
        // initialize time spinners
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
      // Màu nền ivory nhạt cho các panel
    Color ivory = new Color(255, 255, 255);

    // Thiết lập layout chính cho panel
    setLayout(new BorderLayout(20, 20));
    setBackground(ivory);

    // --- Header ---
    JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    header.setBackground(ivory);
    jLabelTitle = new JLabel("ALARM CLOCK", SwingConstants.CENTER);
    jLabelTitle.setFont(new Font("Segoe UI", Font.BOLD, 44)); // in đậm to hơn
    jLabelTitle.setForeground((Color.RED));
    header.add(jLabelTitle);
    add(header, BorderLayout.NORTH);

    // --- Main content (Alarm và Music) ---
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
    JPanel tone = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 20));
    tone.setOpaque(true);
    tone.setBackground(ivory);
    jLabelToneIcon = new JLabel();
    jLabelToneName = new JLabel("No tone selected");
    jLabelToneName.setFont(new Font("Segoe UI", Font.ITALIC, 20)); // in đậm
    jLabelToneName.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    jLabelToneName.setOpaque(true);
    jLabelToneName.setBackground(ivory);
    jLabelToneName.setPreferredSize(new Dimension(300,37));
    tone.add(jLabelToneIcon); tone.add(jLabelToneName);
    alarmPanel.add(tone);

    // Buttons row 1: Set Ring và Listen
    JPanel ringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
    ringPanel.setOpaque(true);
    ringPanel.setBackground(ivory);
    jBRingtone = new JButton("Set Ring");
    jBRingtone.setFont(new Font("Segoe UI", Font.BOLD, 24));
    jBRingtone.setBackground(new Color(144, 238, 144)); // xanh lá nhẹ
    jBRingtone.setForeground(Color.DARK_GRAY);
    jBListen = new JButton("Listen");
    jBListen.setFont(new Font("Segoe UI", Font.BOLD, 24));
    jBListen.setBackground(new Color(255, 215, 0)); // vàng
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
    jLabelSongName = new JLabel("No song selected");
    jLabelSongName.setFont(new Font("Segoe UI", Font.ITALIC, 18)); // in đậm
    jLabelSongName.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    jLabelSongName.setOpaque(true);
    jLabelSongName.setBackground(ivory);
    jLabelSongName.setPreferredSize(new Dimension(300,37));
    topMusic.add(jLabelMusicIcon); topMusic.add(jLabelSongName);
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
    jButtonSetSong.setBackground(new Color(135, 206, 235)); // xanh dương nhạt
    jButtonSetSong.setForeground(Color.DARK_GRAY);
    jButtonPause = new JButton("Pause");
    jButtonPause.setFont(new Font("Segoe UI", Font.BOLD, 24));
    jButtonPause.setBackground(new Color(255, 160, 122)); // cam nhạt
    jButtonPause.setForeground(Color.DARK_GRAY);
    
    Dimension btnSize = new Dimension(160, 45);        // có thể đổi số tùy ý
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
    jBRingtone.addActionListener(e -> chooseSong());
    jBListen.addActionListener(e -> toggleListen());
    jBSetAlarm.addActionListener(e -> setAlarm());
    jBCancelAlarm.addActionListener(e -> cancelAlarm());
    jButtonSetSong.addActionListener(e -> setSong());
    jButtonPause.addActionListener(e -> toggleSong());
      }
       private String[] generateArray(int n) {
        String[] arr = new String[n];
        for (int i = 0; i < n; i++) arr[i] = String.format("%02d", i);
        return arr;
    }
        public void chooseSong() {
        JFileChooser jfc = new JFileChooser();
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();

            sound = f.getAbsolutePath();      // ① lưu đường dẫn chuông
            title = f.getName();              //  (nếu muốn hiển thị tên file)
            jLabelToneName.setText(title);

            jBListen.setEnabled(true);        // ② kích hoạt Listen
            jBListen.setText("Listen");       // ③ bảo đảm nhãn về mặc định
        }
    }

    public void startAlarm() {
        try {
        alarmInputStream = new FileInputStream(sound);   // dùng đúng file vừa chọn
        alarmBufferedInputStream = new BufferedInputStream(alarmInputStream);
        alarmPlayer = new Player(alarmBufferedInputStream);
        new Thread(() -> { try {
            alarmPlayer.play();
            } catch (JavaLayerException ex) {
                Logger.getLogger(Clockk.class.getName()).log(Level.SEVERE, null, ex);
            }
}).start();
    } catch (Exception e) { /* log nếu cần */ }
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

                startAlarm();    // phát chuông ngay
                // Tất cả phần UI phải chạy trên EDT
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "DA DEN GIO HEN");
                    stopAlarm();                     // dừng chuông
                    jLabelStatus.setIcon(checkMarkIcon); // trả icon gốc
                    jBCancelAlarm.setEnabled(false); // vô hiệu nút Cancel
                });
                break;           // thoát vòng lặp chờ
            }
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }).start();
    }

    public void pauseSong() {
         try {
            musicPausedOnFrame = musicInputStream.available();   // <-- đảm bảo đã lấy
            if (musicPlayer != null) musicPlayer.close();
        } catch (Exception e) {}
    }

    public void continueSong() {
         try {
            musicInputStream        = new FileInputStream(customSoundPath);
            long skipBytes          = musicTotalSongLength - musicPausedOnFrame;
            musicInputStream.skip(skipBytes);                    // <-- đúng byte cần bỏ qua
            musicBufferedInputStream = new BufferedInputStream(musicInputStream);
            musicPlayer             = new Player(musicBufferedInputStream);

            new Thread(() -> { try { musicPlayer.play(); } catch (Exception ex) {} }).start();
        } catch (Exception e) {}
    }

    public void playSong(String fp) {
        try {
            if (musicPlayer != null) musicPlayer.close();

            musicInputStream        = new FileInputStream(fp);
            musicTotalSongLength    = musicInputStream.available();   // <-- THÊM
            musicBufferedInputStream = new BufferedInputStream(musicInputStream);
            musicPlayer             = new Player(musicBufferedInputStream);

            new Thread(() -> { try { musicPlayer.play(); } catch (Exception ex) {} }).start();
        } catch (Exception e) {}
    }

    private void toggleListen() {
         if (jButtonPause.getText().equals("Pause")) {
            pauseSong();
            jButtonPause.setText("Continue");
        } else {                      // “Continue” được bấm
            continueSong();
            jButtonPause.setText("Pause");
        }
    }

    private void setAlarm() {
        hourAlarm  = (String) jComboBox1.getSelectedItem();
        minuteAlarm = (String) jComboBox2.getSelectedItem();
        alarmTime(Integer.parseInt(hourAlarm), Integer.parseInt(minuteAlarm));

        jLabelStatus.setIcon(checkedIcon);    //  <-- đặt ảnh checked.png
        jBCancelAlarm.setEnabled(true);
    }

    private void cancelAlarm() {
        alarmCanceled = true;
        stopAlarm();

        jLabelStatus.setIcon(checkMarkIcon);  //  <-- trở về ảnh check‑mark.png
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
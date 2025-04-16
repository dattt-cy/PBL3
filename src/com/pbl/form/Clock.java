
package com.pbl.form;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javazoom.jl.player.Player;

public class Clock extends javax.swing.JPanel implements Runnable {
    String sound, title;
    FileInputStream fileInputStream;
    BufferedInputStream bufferedInputStream;
    Player player;
    long all;
    String hours, hh, mm;
    String hourAlarm, minuteAlarm;
    private volatile boolean alarmCacelled = false;
    private boolean alarmSet = false;
    
    private String customSoundPath;
    private String customTitle;
    private long totalSongLength;
    private long pausedOnFrame;
    private boolean isPaused = false;
   
    public Clock() {
        initComponents();
        Thread t = new Thread(this);
        t.start();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm");
        Date date = c.getTime();
        hh = simpleDateFormat1.format(date);
        mm = simpleDateFormat2.format(date);
        jComboBox1.setSelectedItem(hh);
        jComboBox2.setSelectedItem(mm);
        jBListen.setEnabled(false);
        
        jButton2.setText("Cancel");
        jButton2.setEnabled(false);
        
   
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/pbl/icon/alarm.png"));
        jLabel9.setIcon(icon);
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/com/pbl/icon/bell (1).png"));
        jLabel8.setIcon(icon1);
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/com/pbl/icon/check-mark.png"));
        jLabel10.setIcon(icon2);
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/com/pbl/icon/listen.png"));
        jLabelMusic.setIcon(icon3);
        ImageIcon icon4 = new ImageIcon(getClass().getResource("/com/pbl/icon/musical-note.png"));  
        jLabel12Amnhac.setIcon(icon4);
    }
    
    public void chooseSong(){
        JFileChooser jfc= new JFileChooser();
        int soundINT = jfc.showOpenDialog(null);
        if(soundINT == JFileChooser.APPROVE_OPTION){
            File alarmMusic = jfc.getSelectedFile();
            sound = alarmMusic.getAbsolutePath();
            title = jfc.getSelectedFile().getName();
            System.out.println(title);
        }
        else if(soundINT == JFileChooser.CANCEL_OPTION){
            JOptionPane.showMessageDialog(null, "You don't choose alarm music....");
        }
    }
    public void startAlarm()
{
    try
    {
        fileInputStream = new FileInputStream(sound);
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        player = new Player(bufferedInputStream);
        all = fileInputStream.available();
        new Thread()
        {
            public void run()
            {
                try
                {
                    player.play();
                }
                catch(Exception e)
                {
                }
            }
        }.start();
    }
    catch(Exception e)
    {
    }
}
    
  
    public void alarmTime(final int hour, final int minute)
{
    Thread t = new Thread()
    {
        public void run()
        {
            int time = 0;
            while(!alarmCacelled)
            {
                Calendar c = Calendar.getInstance();
                int h = c.get(Calendar.HOUR);
                int m = c.get(Calendar.MINUTE);

                if(hour == h && minute == m)
                {
                    startAlarm();
                    JOptionPane.showMessageDialog(null, "DA DEN GIO HEN");
                 
                        stopAlarm();
                        alarmSet = false;
                        ImageIcon icon3 = new ImageIcon(getClass().getResource("/com/pbl/icon/check-mark.png"));
                        jLabel10.setIcon(icon3);
                        jButton2.setEnabled(false);
                        break;
                    
                }
            
            }
    }
    }; t.start();
}

   public void stopAlarm(){
       if(player != null){
           player.close();
       }
   }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPlayered = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jBSetAlarm = new javax.swing.JButton();
        jBRingtone = new javax.swing.JButton();
        jBListen = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabelSong = new javax.swing.JLabel();
        jButtonSetSong = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabelMusic = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12Amnhac = new javax.swing.JLabel();

        jPlayered.setBackground(new java.awt.Color(250, 234, 255));
        jPlayered.setOpaque(true);
        jPlayered.setPreferredSize(new java.awt.Dimension(800, 400));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ALARM  CLOCK");

        jLabel2.setBackground(new java.awt.Color(255, 204, 204));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel3.setText("TIMER");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel4.setText("Minute");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel5.setText("Hour");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "43", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText(":");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N

        jBSetAlarm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBSetAlarm.setForeground(java.awt.Color.red);
        jBSetAlarm.setText("Set Alarm");
        jBSetAlarm.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBSetAlarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSetAlarmActionPerformed(evt);
            }
        });

        jBRingtone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBRingtone.setForeground(new java.awt.Color(0, 153, 0));
        jBRingtone.setText(" Set Ring");
        jBRingtone.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBRingtone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRingtoneActionPerformed(evt);
            }
        });

        jBListen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBListen.setForeground(new java.awt.Color(255, 153, 0));
        jBListen.setText("Listen");
        jBListen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBListen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBListenActionPerformed(evt);
            }
        });

        jLabel10.setText("jLabelStatus");

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 51, 255));
        jButton2.setText("Cancel ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabelSong.setBackground(new java.awt.Color(153, 204, 255));
        jLabelSong.setOpaque(true);

        jButtonSetSong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonSetSong.setForeground(new java.awt.Color(204, 0, 102));
        jButtonSetSong.setText("Set Song");
        jButtonSetSong.setToolTipText("");
        jButtonSetSong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetSongActionPerformed(evt);
            }
        });

        jButtonCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonCancel.setForeground(new java.awt.Color(0, 153, 153));
        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Music ");

        jPlayered.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jComboBox1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jComboBox2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jBSetAlarm, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jBRingtone, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jBListen, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabelSong, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jButtonSetSong, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jButtonCancel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabelMusic, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPlayered.setLayer(jLabel12Amnhac, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPlayeredLayout = new javax.swing.GroupLayout(jPlayered);
        jPlayered.setLayout(jPlayeredLayout);
        jPlayeredLayout.setHorizontalGroup(
            jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPlayeredLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPlayeredLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPlayeredLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(76, 76, 76))
                            .addGroup(jPlayeredLayout.createSequentialGroup()
                                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPlayeredLayout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPlayeredLayout.createSequentialGroup()
                                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabelMusic, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addGroup(jPlayeredLayout.createSequentialGroup()
                                                        .addComponent(jButtonSetSong, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(jLabelSong, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(42, 42, 42)
                                                .addComponent(jLabel12Amnhac, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(33, 33, 33))
                                            .addGroup(jPlayeredLayout.createSequentialGroup()
                                                .addComponent(jBRingtone, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(72, 72, 72)
                                                .addComponent(jBListen, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(83, 83, 83)
                                                .addComponent(jBSetAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(49, 49, 49)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPlayeredLayout.createSequentialGroup()
                                        .addGap(95, 95, 95)
                                        .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPlayeredLayout.createSequentialGroup()
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(86, 86, 86)
                                                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPlayeredLayout.createSequentialGroup()
                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(49, 49, 49)
                                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(27, 27, 27)
                                                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(43, 43, 43))
                                            .addGroup(jPlayeredLayout.createSequentialGroup()
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap(114, Short.MAX_VALUE))))
                    .addGroup(jPlayeredLayout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPlayeredLayout.setVerticalGroup(
            jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPlayeredLayout.createSequentialGroup()
                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPlayeredLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPlayeredLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBRingtone)
                    .addComponent(jBListen)
                    .addComponent(jBSetAlarm)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPlayeredLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel12Amnhac, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPlayeredLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPlayeredLayout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabelSong, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelMusic, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68)))
                .addGroup(jPlayeredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSetSong)
                    .addComponent(jButtonCancel))
                .addGap(602, 602, 602))
        );

        jLabel10.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPlayered, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPlayered, javax.swing.GroupLayout.PREFERRED_SIZE, 662, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBSetAlarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSetAlarmActionPerformed
        // TODO add your handling code here:
        hourAlarm = jComboBox1.getSelectedItem().toString();
    minuteAlarm = jComboBox2.getSelectedItem().toString();

    if (jLabel1.getText() != "") {
        String alarmClock = hourAlarm + ":" + minuteAlarm;
        System.out.println(alarmClock);
        alarmCacelled = false;
        alarmSet = true;
        alarmTime(Integer.valueOf(hourAlarm), Integer.valueOf(minuteAlarm));
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/com/pbl/icon/checked.png"));
        jLabel10.setIcon(icon3);
        jButton2.setEnabled(true);
    } else {
    JOptionPane.showMessageDialog(null, "You don't choose alarm music.", "Warning", JOptionPane.INFORMATION_MESSAGE);
}

    }//GEN-LAST:event_jBSetAlarmActionPerformed

    private void jBRingtoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRingtoneActionPerformed
        chooseSong();
        if(!sound.equals(null)){
            jLabel7.setText("Alarm music: " + title);        
       }
        jBListen.setEnabled(true);
    }//GEN-LAST:event_jBRingtoneActionPerformed

    private void jBListenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBListenActionPerformed
            if (jBListen.getText().equals("Listen"))
      {
          startAlarm();
          jBListen.setText("Stop Sound");
          jBSetAlarm.setEnabled(false);
      }
      else if (jBListen.getText().equals("Stop Sound"))
      {
          stopAlarm();
          jBListen.setText("Listen");
          jBSetAlarm.setEnabled(true);
      }

    }//GEN-LAST:event_jBListenActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                            // TODO add your handling code here:
        alarmCacelled = true;
        alarmSet = false;
        stopAlarm();
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/com/pbl/icon/check-mark.png"));
        jLabel10.setIcon(icon2);
        jButton2.setEnabled(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonSetSongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetSongActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            customSoundPath = selectedFile.getAbsolutePath();
            customTitle = selectedFile.getName();
            
            jLabelSong.setText(customTitle);
            jButtonCancel.setEnabled(true);
            
        playSong(customSoundPath);
        }
    }//GEN-LAST:event_jButtonSetSongActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // TODO add your handling code here:
        if(jButtonCancel.getText().equals("Cancel")){
            pauseSong();
            jButtonCancel.setText("Continue");
        } else{
            continueSong();
            jButtonCancel.setText("Cancel");
        }
        
    }//GEN-LAST:event_jButtonCancelActionPerformed
        public void continueSong() {
    try {
        // Mở lại input stream
        fileInputStream = new FileInputStream(customSoundPath);
        bufferedInputStream = new BufferedInputStream(fileInputStream);

        // totalSongLength = tổng số bytes
        // pausedOnFrame   = số bytes còn lại (chưa đọc)
        // => Ta skip (totalSongLength - pausedOnFrame) bytes đã phát
        long skipBytes = totalSongLength - pausedOnFrame;
        fileInputStream.skip(skipBytes);

        // Tạo player mới
        player = new Player(bufferedInputStream);

        // Chạy nhạc trong thread
        new Thread(() -> {
            try {
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        isPaused = false;
    } catch (Exception e) {
        e.printStackTrace();
    }
}

        public void pauseSong() {
    try {
        // pausedOnFrame = số bytes còn lại sau khi dừng.
        // => Tức "còn lại" thay vì "đã phát", do javazoom cung cấp .available() = bytes chưa đọc
        pausedOnFrame = fileInputStream.available();
        // Đóng player để dừng phát
        if (player != null) {
            player.close();
        }
        isPaused = true;
    } catch (Exception e) {
        e.printStackTrace();
    }
}

       public void playSong(String filePath) {
    try {
        // Nếu player đang mở (đang chạy 1 bài cũ), ta tắt trước
        if (player != null) {
            player.close();
        }

        fileInputStream = new FileInputStream(filePath);
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        player = new Player(bufferedInputStream);

        // Lưu chiều dài (tổng số bytes) để khi tạm dừng có thể skip
        totalSongLength = fileInputStream.available();
        pausedOnFrame = 0;       // Bắt đầu nghe từ đầu
        isPaused = false;        // Chưa tạm dừng

        // Tạo luồng để play
        new Thread(() -> {
            try {
                player.play(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

        
        
        
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBListen;
    private javax.swing.JButton jBRingtone;
    private javax.swing.JButton jBSetAlarm;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSetSong;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12Amnhac;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMusic;
    private javax.swing.JLabel jLabelSong;
    private javax.swing.JLayeredPane jPlayered;
    // End of variables declaration//GEN-END:variables
     public void run(){
        while(true){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss ");
            Date date = c.getTime();
            hours = simpleDateFormat.format(date);
            jLabel2.setText(hours);
            
        }
     }
}


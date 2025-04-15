
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
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/pbl/icon/alarm.png"));
        jLabel9.setIcon(icon);
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/com/pbl/icon/bell (1).png"));
        jLabel8.setIcon(icon1);
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
            while(time == 0)
            {
                Calendar c = Calendar.getInstance();
                int h = c.get(Calendar.HOUR);
                int m = c.get(Calendar.MINUTE);

                if(hour == h && minute == m)
                {
                    startAlarm();
                    JOptionPane.showMessageDialog(null, "ALARM");
                    int stop = JOptionPane.showConfirmDialog(null, "STOP ALARM", "", JOptionPane.YES_OPTION);
                    if(stop == JOptionPane.YES_OPTION){
                        stopAlarm();
                        break;
                    }
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

        jLayeredPane1 = new javax.swing.JLayeredPane();
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

        jLayeredPane1.setBackground(new java.awt.Color(250, 215, 252));
        jLayeredPane1.setOpaque(true);
        jLayeredPane1.setPreferredSize(new java.awt.Dimension(800, 400));

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
        jBRingtone.setText("Ring");
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

        jLabel9.setText("jLabel9");

        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jComboBox1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jComboBox2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jBSetAlarm, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jBRingtone, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jBListen, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
                        .addGap(76, 76, 76))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(86, 86, 86)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(49, 49, 49)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(27, 27, 27)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(379, Short.MAX_VALUE))))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(353, 353, 353)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jBRingtone, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(jBListen, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(jBSetAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBRingtone)
                    .addComponent(jBListen)
                    .addComponent(jBSetAlarm))
                .addGap(804, 804, 804))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1002, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 436, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 436, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBSetAlarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSetAlarmActionPerformed
        // TODO add your handling code here:
        hourAlarm = jComboBox1.getSelectedItem().toString();
    minuteAlarm = jComboBox2.getSelectedItem().toString();

    if (jLabel1.getText() != "") {
        String alarmClock = hourAlarm + ":" + minuteAlarm;
        System.out.println(alarmClock);
        alarmTime(Integer.valueOf(hourAlarm), Integer.valueOf(minuteAlarm));
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBListen;
    private javax.swing.JButton jBRingtone;
    private javax.swing.JButton jBSetAlarm;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
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


package com.pbl.form;

import com.pbl.chart.ModelChart;
import com.pbl.main.Main;
import com.pbl.model.ModelCard;
import com.pbl.model.Task;
import com.pbl.service.TaskService;

import com.pbl.swing.noticeboard.ModelNoticeBoard;

import java.awt.Color;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Form_Home extends javax.swing.JPanel {

    public Form_Home(int user_id) {
        initComponents();
        setOpaque(false);
        initData(user_id);
    }

    private void initData(int user_id) {

        initNoticeBoard(user_id);
      

    }


    private void initNoticeBoard(int userId) {

        TaskService taskService = new TaskService();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate today = LocalDate.now();
        List<Task> tasks = taskService.getAllTasksByUser(userId);
        tasks.sort(Comparator.comparing(Task::getDateTime));

        boolean printedToday = false;
        for (Task task : tasks) {
            if (task.getDateTime().toLocalDate().equals(today)) {
                if (!printedToday) {
                    noticeBoard.addDate(today.format(dateFormatter));
                    printedToday = true;
                }
                noticeBoard.addNoticeBoard(new ModelNoticeBoard(
                        new Color(27, 188, 204),
                        task.getTitle(),
                        task.getDateTime().format(timeFormatter),
                        task.getDescription()
                ));
            }
        }

        Map<LocalDate, List<Task>> overdueMap = new TreeMap<>(Comparator.reverseOrder());
        for (Task task : tasks) {
            LocalDate d = task.getDateTime().toLocalDate();
            if (d.isBefore(today) && !task.isDone()) {
                overdueMap
                        .computeIfAbsent(d, k -> new ArrayList<>())
                        .add(task);
            }
        }

        for (Map.Entry<LocalDate, List<Task>> e : overdueMap.entrySet()) {
            noticeBoard.addDate(e.getKey().format(dateFormatter));
            for (Task task : e.getValue()) {
                noticeBoard.addNoticeBoard(new ModelNoticeBoard(
                        new Color(238, 46, 57),
                        task.getTitle(),
                        task.getDateTime().format(timeFormatter),
                        task.getDescription()
                ));
            }
        }

        noticeBoard.scrollToTop();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        noticeBoard = new com.pbl.swing.noticeboard.NoticeBoard();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(4, 72, 210));
        jLabel1.setText("Dashboard / Home");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(76, 76, 76));
        jLabel2.setText("Notice Board");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(105, 105, 105));
        jLabel3.setText("Simple Miglayout API Doc");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        jLabel4.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(noticeBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 257, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addGap(9, 9, 9)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(noticeBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 623, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(150, 150, 150)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private com.pbl.swing.noticeboard.NoticeBoard noticeBoard;
    // End of variables declaration//GEN-END:variables
}

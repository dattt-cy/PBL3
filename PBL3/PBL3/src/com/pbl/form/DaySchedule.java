package com.pbl.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DaySchedule extends JPanel {

    public DaySchedule(MainForm main_form, LocalDate selectedDay) {
    setPreferredSize(new Dimension(1000, 650));
    setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
    setLayout(new GridBagLayout());
    setBackground(null);

    // Tạo đối tượng GridBagConstraints để quản lý bố cục
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.insets = new Insets(5, 15, 5, 15);

    // ========== Hàng 0: Tiêu đề ==========
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.gridwidth = 2;        // Tiêu đề chiếm 2 cột
    constraints.weightx = 1.0;        // Dàn đều theo chiều ngang
    constraints.weighty = 0.0;        // Không chiếm thêm chiều cao
    JLabel todayLabel = createHeader(selectedDay, main_form);
    add(todayLabel, constraints);

    // ========== Hàng 1: Bảng nhiệm vụ (bên trái) ==========
    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.gridwidth = 1;        // Quay lại 1 cột
    constraints.weightx = 0.995;        // Tasks chiếm 60% chiều rộng
    constraints.weighty = 1.0;        // Chiếm phần lớn chiều cao
    DayScheduleTasks tasks = new DayScheduleTasks(selectedDay, main_form, this);
    add(tasks, constraints);

    // ========== Hàng 1: Khu vực ghi chú (bên phải) ==========
    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.gridwidth = 1;
    constraints.weightx = 0.005;        // Notes chiếm 40% chiều rộng
    constraints.weighty = 1.0;
    JPanel notesPanel = createNotesSection();
    add(notesPanel, constraints);

    // ========== Hàng 2: Câu nói động lực (bên dưới) ==========
    constraints.gridx = 0;
    constraints.gridy = 2;
    constraints.gridwidth = 2;        // Chiếm 2 cột
    constraints.weightx = 1.0;
    constraints.weighty = 0.0;        // Không chiếm thêm chiều cao
    constraints.insets = new Insets(10, 15, 10, 15); // Lề dưới
    JTextArea quoteLabel = createQuoteLabel();
    add(quoteLabel, constraints);
}


    // Tạo tiêu đề hiển thị ngày tháng
    private JLabel createHeader(LocalDate selectedDay, MainForm main_form) {
        String dateString = selectedDay.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, EEEE"));
        JLabel todayLabel = new JLabel(dateString);
        todayLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        todayLabel.setForeground(Color.decode("#4D2508"));
        todayLabel.setHorizontalAlignment(JLabel.CENTER);
        todayLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        todayLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Xử lý sự kiện click để quay lại trang Home
        todayLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetCalendarSchedular(main_form);
            }
        });
        return todayLabel;
    }

    // Khu vực ghi chú
    // Khu vực ghi chú
 private JPanel createNotesSection() {
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.setMinimumSize(new Dimension(150, 150)); // Giữ cho nó lớn
        textAreaPanel.setBackground(Color.LIGHT_GRAY);

        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        notesLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        textAreaPanel.add(notesLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Helvetica", Font.PLAIN, 18));
        textArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        textAreaPanel.add(scrollPane, BorderLayout.CENTER);
        
        return textAreaPanel;
    }


    // Câu nói động lực
    private JTextArea createQuoteLabel() {
        JTextArea quoteLabel = new JTextArea("Stay positive and keep moving forward!");
        quoteLabel.setEditable(false);
        quoteLabel.setWrapStyleWord(true);
        quoteLabel.setLineWrap(true);
        quoteLabel.setFont(new Font("Helvetica", Font.ITALIC, 14));
        quoteLabel.setForeground(Color.BLUE);
        quoteLabel.setPreferredSize(new Dimension(700, 50));
        quoteLabel.setBackground(null);
        return quoteLabel;
    }

    // Hàm reset về trang Home
    private void resetCalendarSchedular(MainForm main_form) {
        main_form.showForm(new Form2(main_form, LocalDate.now()));
    }
}

package com.pbl.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DaySchedule extends JPanel {

    public DaySchedule(MainForm main_form, LocalDate selectedDay) {  // Bỏ database khỏi tham số
        setPreferredSize(new Dimension(700, 500));
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        setLayout(new BorderLayout());
        setBackground(null);

        // Định dạng ngày tháng
        DateTimeFormatter dbDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = selectedDay.format(dbDateFormatter);

        // Hiển thị ngày tháng trên đầu, căn trái
        String dateString = selectedDay.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, EEEE"));
        JLabel todayLabel = new JLabel(dateString);
        todayLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
        todayLabel.setForeground(Color.decode("#4D2508"));
        todayLabel.setHorizontalAlignment(JLabel.LEFT);  // Căn trái
        todayLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Khoảng cách bên trong
        add(todayLabel, BorderLayout.NORTH);  // Thêm vào phía trên (NORTH) của BorderLayout

        // Quay lại trang Home khi nhấn vào ngày
        todayLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        todayLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetCalendarSchedular(main_form);  // Gọi hàm reset với main_form
            }
        });

        // Bảng nhiệm vụ, tăng kích thước
        DayScheduleTasks tasks = new DayScheduleTasks(selectedDay, main_form, this);  // Bỏ database
        tasks.setPreferredSize(new Dimension(1000, 900));  // To hơn
        add(tasks, BorderLayout.CENTER);

        // Khu vực ghi chú
        JPanel notesPanel = createNotesSection();
        add(notesPanel, BorderLayout.EAST);

        // Hiển thị câu nói động lực
        JTextArea quoteLabel = new JTextArea("Stay positive and keep moving forward!");  // Bỏ câu từ database
        quoteLabel.setEditable(false);
        quoteLabel.setWrapStyleWord(true);
        quoteLabel.setLineWrap(true);
        quoteLabel.setFont(new Font("Helvetica", Font.PLAIN | Font.ITALIC, 13));
        quoteLabel.setPreferredSize(new Dimension(700, 50));
        add(quoteLabel, BorderLayout.SOUTH);
    }

    // Khu vực ghi chú không dùng database
    private JPanel createNotesSection() {
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.setPreferredSize(new Dimension(175, 250));  // To hơn chút
        textAreaPanel.setBackground(null);

        JLabel notesLabel = new JLabel("Notes: ");
        notesLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        notesLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        textAreaPanel.add(notesLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Helvetica", Font.PLAIN, 15));
        textArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        textArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        textAreaPanel.add(scrollPane);
        return textAreaPanel;
    }

    // Hàm reset về trang Home với main_form
    private void resetCalendarSchedular(MainForm main_form) {
        main_form.showForm(new Form2(main_form, LocalDate.now()));
    }
}

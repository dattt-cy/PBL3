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

        // Định dạng ngày tháng phù hợp với database
        DateTimeFormatter dbDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = selectedDay.format(dbDateFormatter);

        // Hiển thị ngày tháng trên đầu
        JLabel todayLabel = createHeader(selectedDay, main_form);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 0.2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(5, 15, 5, 5);
        add(todayLabel, constraints);

        // Bảng nhiệm vụ
        constraints.gridwidth = 1;
        constraints.weightx = 0.8;
        constraints.weighty = 10;
        constraints.gridx = 0;
        constraints.gridy = 1;
        DayScheduleTasks tasks = new DayScheduleTasks(selectedDay, main_form, this);
        add(tasks, constraints);

        // Khu vực ghi chú
       constraints.fill = GridBagConstraints.BOTH; // Cho phép mở rộng toàn bộ
constraints.weightx = 2;  // Tăng độ rộng
constraints.weighty = 10;  // Tăng chiều cao
constraints.gridx = 1;
constraints.gridy = 1;
JPanel notesPanel = createNotesSection();
add(notesPanel, constraints);

   

        // Hiển thị câu nói động lực
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(20, 35, 0, 0);
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
        textAreaPanel.setMinimumSize(new Dimension(250, 250)); // Giữ cho nó lớn
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

package com.pbl.form;

import com.pbl.model.Task;
import com.pbl.service.TaskService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class TaskEditor {

    // Mảng danh mục cho drop down menu
    String[] categories = {"General", "Holiday", "Personal", "Meeting", "Social"};

    /**
     * Constructor của TaskEditor.
     *
     * @param t Có thể là một task mới hoặc task đã tồn tại.
     * @param mainForm Đối tượng MainForm.
     * @param parentPanel Panel cha chứa giao diện.
     */
    public TaskEditor(Task t, MainForm mainForm, JPanel parentPanel) {
        int year = t.getDate().getYear();
        int month = t.getDate().getMonthValue();

        // Cấu hình tiêu đề của frame
        JFrame frame = new JFrame("Add Task");
        if (t.getTitle() != null) {
            frame.setTitle(t.getTitle());
        }

        // Cấu hình frame cho TaskEditor
        ImageIcon img = new ImageIcon("resources/add.png");
        frame.setIconImage(img.getImage());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(300, 300));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(15, 30, 0, 30);

        // Tiêu đề (Title)
        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        titleLabel.setPreferredSize(new Dimension(120, 40));
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        mainPanel.add(titleLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        JTextField titleField = new JTextField();
        titleField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        titleField.setPreferredSize(new Dimension(200, 40));
        mainPanel.add(titleField, constraints);

        // Thời gian (Time)
        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        timeLabel.setPreferredSize(new Dimension(120, 40));
        timeLabel.setHorizontalAlignment(JLabel.LEFT);
        mainPanel.add(timeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        JTextField timeField = new JTextField();
        timeField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        timeField.setPreferredSize(new Dimension(200, 40));
        mainPanel.add(timeField, constraints);

        // Danh mục (Category)
        constraints.gridx = 0;
        constraints.gridy = 2;
        JLabel categoriesLabel = new JLabel("Category:");
        categoriesLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        categoriesLabel.setPreferredSize(new Dimension(100, 40));
        categoriesLabel.setHorizontalAlignment(JLabel.LEFT);
        mainPanel.add(categoriesLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        final JComboBox<String> categoriesField = new JComboBox<>(categories);
        categoriesField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        categoriesField.setPreferredSize(new Dimension(200, 40));
        ((JLabel) categoriesField.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(categoriesField, constraints);

        // Mô tả (Description)
        constraints.gridx = 0;
        constraints.gridy = 3;
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        descLabel.setPreferredSize(new Dimension(120, 40));
        descLabel.setHorizontalAlignment(JLabel.LEFT);
        mainPanel.add(descLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.weightx = 1;
        JTextArea descField = new JTextArea(3, 0);
        descField.setPreferredSize(new Dimension(200, 300));
        descField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        descField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        JScrollPane scroll = new JScrollPane(descField);
        mainPanel.add(scroll, constraints);

        // Bottom panel cho nút Save và Delete
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));
        bottomPanel.setBackground(null);

        JButton deleteTaskButton = new JButton("Delete");
        deleteTaskButton.setFont(new Font("Helvetica", Font.PLAIN, 15));
        deleteTaskButton.setBackground(Color.decode("#e3deca"));
        deleteTaskButton.setForeground(Color.decode("#3c3a1e"));
        bottomPanel.add(deleteTaskButton);

        JButton saveTaskButton = new JButton("Save");
        saveTaskButton.setFont(new Font("Helvetica", Font.PLAIN, 15));
        saveTaskButton.setBackground(Color.decode("#3c3a1e"));
        saveTaskButton.setForeground(Color.WHITE);
        bottomPanel.add(saveTaskButton);

        // Thiết lập giá trị mặc định cho các trường nhập liệu
        timeField.setText(t.getTimeToString());
        if (t.getTitle() != null) {
            titleField.setText(t.getTitle());
            descField.setText(t.getDescription());
            categoriesField.setSelectedItem(t.getCategory());
        } else {
            deleteTaskButton.setVisible(false);
        }

        frame.add(mainPanel);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        // Khởi tạo lớp nghiệp vụ TaskService
        TaskService taskService = new TaskService();

        // Xử lý sự kiện Save
        saveTaskButton.addActionListener(e -> {
            String newTitle = titleField.getText().trim();
            String newDesc = descField.getText().trim();
            String newCategory = (String) categoriesField.getSelectedItem();
            String newTime = timeField.getText().trim();

            t.setTitle(newTitle);
            t.setDescription(newDesc);
            t.setCategory(newCategory);
            t.setTime(newTime);

            if (t.getID() != 0) {
                taskService.updateTask(t);
            } else {
                taskService.createTask(t);
            }
            Form2.getInstance().updateTasks(t.getDate());

            frame.dispose();
        });

        deleteTaskButton.addActionListener(e -> {
            if (t.getID() != 0) {
                taskService.deleteTask(t.getID());
            }
            frame.dispose();
        });
    }
}

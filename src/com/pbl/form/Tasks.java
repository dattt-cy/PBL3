/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbl.form;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.pbl.model.Task;

/**
 * @author Audreen Soh
 * <p>
 * This class retrieves and shows the list of tasks.
 */
public class Tasks extends JPanel {

    /**
     * Class constructor.
     *
     * @param date      The date and time of the task
     * @param mainForm  The main form object
     * @param mainPanel The parent panel object
     */
    public Tasks(LocalDate date, MainForm mainForm, JPanel mainPanel) {
        // Set up tasks panel
        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(199, 215, 251));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 15, 10));

        // Add tasks to panel
        createTasksSection(date, mainForm, mainPanel);
    }

    /**
     * createTasksSection - Set up the panels to show the list of tasks.
     *
     * @param date      The date and time of the task
     * @param mainForm  The main form object
     * @param mainPanel The parent panel object
     */
    private void createTasksSection(LocalDate date, MainForm mainForm, JPanel mainPanel) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        // Placeholder for tasks list
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, "Họp nhóm", "Họp với nhóm về dự án", "Meeting", false, date.atTime(9, 0)));
        tasks.add(new Task(2, "Đi chơi", "Đi chơi với bạn bè", "Social", false, date.atTime(18, 0)));
        tasks.add(new Task(3, "Làm bài tập", "Hoàn thành bài tập lập trình", "Personal", true, date.atTime(20, 0)));
        tasks.add(new Task(4, "Nghỉ lễ", "Nghỉ lễ Quốc khánh", "Holiday", false, date.atTime(0, 0)));

        int rows = Math.max(4, tasks.size());
        JPanel list = new JPanel(new GridLayout(rows, 1, 10, 5));
        list.setBackground(new Color(199, 215, 251));
        JScrollPane scrollPane = new JScrollPane(list);

        // Loop through tasks and create every task item for that specific date
        for (int i = 0; i < tasks.size(); i++) {
            final int j = i;
            JPanel task = new JPanel(new GridLayout(2, 2));
            task.setPreferredSize(new Dimension(400, 80));
            task.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(20, 20, 20, 20),
                    BorderFactory.createMatteBorder(0, 10, 0, 0, Color.decode(getTaskColor(tasks.get(i).getCategory())))));
            task.setBackground(Color.decode("#F1B0DA"));
            task.setCursor(new Cursor(Cursor.HAND_CURSOR));
            task.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Opens task editor to edit selected task
                    new TaskEditor(tasks.get(j), mainForm, mainPanel);
                }
            });

            JPanel taskTop = new JPanel(new BorderLayout());
            taskTop.setBackground(null);
            JLabel title = new JLabel(tasks.get(i).getTitle());
            title.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
            title.setFont(new Font("Helvetica", Font.PLAIN, 20));
            title.setForeground(Color.decode("#5c2c0c"));
            taskTop.add(title, BorderLayout.WEST);

            JCheckBox checkBox = new JCheckBox();
            checkBox.setBackground(null);
            checkBox.setSelected(tasks.get(i).isDone());
            checkBox.addItemListener(e -> tasks.get(j).setDone(checkBox.isSelected()));
            taskTop.add(checkBox, BorderLayout.EAST);

            JLabel time = new JLabel(tasks.get(i).getDateTimeToString());
            time.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            time.setFont(new Font("Helvetica", Font.PLAIN, 15));
            time.setForeground(Color.DARK_GRAY);
            task.add(taskTop);
            task.add(time);
            list.add(task);
        }
        add(scrollPane, BorderLayout.CENTER);

        JButton newTaskButton = new JButton("New");
        newTaskButton.setFont(new Font("Helvetica", Font.PLAIN, 15));
        newTaskButton.setBackground(Color.decode("#dda35d"));
        newTaskButton.setForeground(Color.WHITE);
        newTaskButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        newTaskButton.addActionListener(e -> {
            // Opens task editor to add new task
            new TaskEditor(new Task(date), mainForm, mainPanel);
        });
        add(newTaskButton, BorderLayout.SOUTH);
    }

    /**
     * getTaskColor - Get the corresponding color base on the task category
     *
     * @param category The task category passed in for checking
     * @return A string that represents the color hex
     */
    private String getTaskColor(String category) {
        switch (category) {
            case "General":
                return "#666822";
            case "Holiday":
                return "#c67713";
            case "Personal":
                return "#c1380a";
            case "Meeting":
                return "#742505";
            case "Social":
                return "#4d2508";
            default:
                return "#666822";
        }
    }
}

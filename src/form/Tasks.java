/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Task;

public class Tasks extends JPanel {

    public Tasks(LocalDate date, JPanel mainPanel) {
        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 10, 15, 10));

        createTasksSection(date, mainPanel);
    }

    private void createTasksSection(LocalDate date, JPanel mainPanel) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ArrayList<Task> tasks = new ArrayList<>(); // Placeholder for tasks list

        int rows = Math.max(4, tasks.size());
        JPanel list = new JPanel(new GridLayout(rows, 1, 10, 5));
        list.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(list);

        for (int i = 0; i < tasks.size(); i++) {
            final int j = i;
            JPanel task = new JPanel(new GridLayout(2, 2));
            task.setPreferredSize(new Dimension(400, 80));
            task.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(20, 20, 20, 20),
                    BorderFactory.createMatteBorder(0, 10, 0, 0, Color.decode(getTaskColor(tasks.get(i).getCategory())))));
            task.setBackground(Color.decode("#e3deca"));
            task.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
        newTaskButton.addActionListener(e -> {});
        add(newTaskButton, BorderLayout.SOUTH);
    }

    private String getTaskColor(String category) {
        switch (category) {
            case "General": return "#666822";
            case "Holiday": return "#c67713";
            case "Personal": return "#c1380a";
            case "Meeting": return "#742505";
            case "Social": return "#4d2508";
            default: return "#666822";
        }
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component;

import form.MainForm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Task;

/**
 * @author Audreen Soh
 * 
 * This class creates the tasks table of a specific day.
 */
public class DayScheduleTasks extends JPanel {

    /**
     * Class constructor.
     *
     * @param frame       The main frame object
     * @param date        The selected date
     * @param parentPanel The parent panel object
     */
    public DayScheduleTasks(LocalDate date, MainForm frame, JPanel parentPanel) {

        // Set up tasks panel
        setPreferredSize(new Dimension(1000, 700));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(("dd-MM-yyyy"));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Set up tasks header to show at the left, on top of the table.
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        topPanel.setBackground(null);

        JLabel tasksLabel = new JLabel("Tasks");
        tasksLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        topPanel.add(tasksLabel, BorderLayout.WEST);

        // Add the add button to show at the right, on top of the table.
        JLabel addButton = new JLabel(new ImageIcon("resources/add.png"));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Opens task editor to add a task
     //           new TaskEditor(new Task(date), frame, parentPanel);
            }
        });
        topPanel.add(addButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Retrieve the tasks (mock data as Database is removed)
        ArrayList<Task> tasks = new ArrayList<>(); // Sử dụng danh sách rỗng tạm thời hoặc có thể thay bằng dữ liệu khác.

        // Default rows to 6(header + 5 tasks), reserves the default size of the tasks panel if tasks arraylist size is less than 6
        int rows = 6;
        if (tasks.size() > 5) {
            rows = tasks.size() + 1; // Adding 1 to include the header row
        }

        // Set up the table
        JPanel list = new JPanel(new GridLayout(rows, 1, 2, 2));
        list.setBackground(Color.WHITE);

        JPanel header = new JPanel(new GridLayout(1, 6));
        header.setPreferredSize(new Dimension(350, 40));
        header.setBackground(Color.decode("#aa6231"));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        // Add table headers
        header.add(createHeaderLabel(""));
        header.add(createHeaderLabel("Time"));
        header.add(createHeaderLabel("Task"));
        header.add(createHeaderLabel("Category"));
        header.add(createHeaderLabel("Done"));
        list.add(header);

        // Add tasks to table
        for (int i = 0; i < tasks.size(); i++) {
            final int j = i;
            JPanel task = new JPanel(new GridLayout(1, 6));
            task.setPreferredSize(new Dimension(350, 30));
            task.setBackground(Color.decode("#f0f0f0"));
            task.setCursor(new Cursor(Cursor.HAND_CURSOR));
            task.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Opens task editor to edit selected task
                   // new TaskEditor(tasks.get(j), frame, parentPanel);
                }
            });

            // Add task detail labels to the task panel
            task.add(createBodyLabel(Integer.toString(i + 1), "#000000"));
            task.add(createBodyLabel(tasks.get(i).getTimeToString(), "#000000"));
            task.add(createBodyLabel(tasks.get(i).getTitle(), "#000000"));
            task.add(createBodyLabel(tasks.get(i).getCategory(), getTaskColor(tasks.get(i).getCategory())));

            // Add checkbox using custom image icon to the task panel
            Icon notSelected = new ImageIcon("resources/check-box-not-selected.png");
            Icon selected = new ImageIcon("resources/check-box-selected.png");
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected(tasks.get(i).isDone());
            checkBox.setIcon(notSelected);
            checkBox.setSelectedIcon(selected);
            checkBox.addItemListener(e -> {
                Task t = tasks.get(j);
                t.setDone(checkBox.isSelected());
                parentPanel.revalidate();
            });
            task.add(checkBox);
            list.add(task);
        }

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * createHeaderLabel - Creates the header label for the task table
     */
    private JLabel createHeaderLabel(String label) {
        JLabel headerLabel = new JLabel(label);
        headerLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return headerLabel;
    }

    /**
     * createBodyLabel - Creates the label which is used in the task body
     */
    private JLabel createBodyLabel(String label, String fontColor) {
        JLabel bodyLabel = new JLabel(label);
        bodyLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        bodyLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        bodyLabel.setForeground(Color.decode(fontColor));
        return bodyLabel;
    }

    /**
     * getTaskColor - Get the corresponding color base on the task category
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

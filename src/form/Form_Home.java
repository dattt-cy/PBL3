/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ADMIN
 */
public class Form_Home extends JPanel{
    private static final Font HEADER_FONT = new Font("Helvetica", Font.BOLD, 40);
    private static final Color HEADER_COLOR = Color.decode("#4D2508");
    private static final Dimension PANEL_SIZE = new Dimension(900, 500);

    public Form_Home(LocalDate selectedDay){
        setPreferredSize(PANEL_SIZE);
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
      setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        

        // Hiển thị ngày hiện tại
        LocalDate date = LocalDate.now();
        String dateString = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, EEEE"));
        JLabel todayLabel = new JLabel(dateString);
        todayLabel.setFont(HEADER_FONT);
        todayLabel.setForeground(HEADER_COLOR);
        todayLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Thêm sự kiện chuyển trang
        todayLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
             
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(5, 15, 5, 15);
        add(todayLabel, constraints);

        // Lịch
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        
           constraints.insets = new Insets(0, 0, 0, 0);
       add(new Calendar(date.getYear(), date.getMonthValue(), selectedDay, this), constraints);

        // Nhiệm vụ
        constraints.gridx = 1;
        constraints.gridy = 1;
       add(new Tasks(selectedDay, this), constraints);
    }
}


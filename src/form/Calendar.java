/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import swing.DayLabel;

public class Calendar extends JPanel {
    public Calendar(int year, int month, LocalDate selectedDay, JPanel parentPanel) {
        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 0));
        setBackground(null);

        JPanel top = new JPanel(new BorderLayout());
        top.setPreferredSize(new Dimension(350, 50));
        top.setBackground(null);

        JLabel date = new JLabel(LocalDate.of(year, month, 1).format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        date.setHorizontalAlignment(JLabel.CENTER);
        date.setFont(new Font("Helvetica", Font.BOLD, 25));
        date.setForeground(Color.decode("#c1380a"));
        top.add(date, BorderLayout.CENTER);

        JLabel left = new JLabel(new ImageIcon(getClass().getResource("/icon/arrow-left.png")));
        left.setCursor(new Cursor(Cursor.HAND_CURSOR));
        left.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentPanel.removeAll();
                if (month != 1) {
                    resetMainPanel(parentPanel, selectedDay, new Calendar(year, month - 1, selectedDay, parentPanel));
                } else {
                    resetMainPanel(parentPanel, selectedDay, new Calendar(year - 1, 12, selectedDay, parentPanel));
                }
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        top.add(left, BorderLayout.WEST);

        JLabel right = new JLabel(new ImageIcon(getClass().getResource("/icon/arrow-right.png")));
        right.setCursor(new Cursor(Cursor.HAND_CURSOR));
        right.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (month != 12) {
                    resetMainPanel(parentPanel, selectedDay, new Calendar(year, month + 1, selectedDay, parentPanel));
                } else {
                    resetMainPanel(parentPanel, selectedDay, new Calendar(year + 1, 1, selectedDay, parentPanel));
                }
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        top.add(right, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel days = new JPanel(new GridLayout(7, 7));
        days.setBackground(null);

        Color header = Color.decode("#aa6231");
        days.add(new DayLabel("S", header, Color.white, false));
        days.add(new DayLabel("M", header, Color.white, false));
        days.add(new DayLabel("T", header, Color.white, false));
        days.add(new DayLabel("W", header, Color.white, false));
        days.add(new DayLabel("T", header, Color.white, false));
        days.add(new DayLabel("F", header, Color.white, false));
        days.add(new DayLabel("S", header, Color.white, false));

        String[] weekDays = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        LocalDate firstDay = LocalDate.of(year, month, 1);
        int j = 0;
        while (!firstDay.getDayOfWeek().toString().equals(weekDays[j])) {
            days.add(new DayLabel("", Color.decode("#e3deca"), Color.BLACK, false));
            j++;
        }

        int daysNum = YearMonth.of(year, month).lengthOfMonth();
        for (int i = 1; i <= daysNum; i++) {
            final int day = i;
            DayLabel dayLabel = new DayLabel(i + "", Color.decode("#e3deca"), Color.BLACK, true);
            LocalDate today = LocalDate.now();
            if (today.getYear() == year && today.getMonthValue() == month && today.getDayOfMonth() == i) {
                dayLabel = new DayLabel(i + "", Color.decode("#3c3a1e"), Color.WHITE, true);
            }
            dayLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    LocalDate selected = LocalDate.of(year, month, day);
                    resetMainPanel(parentPanel, selected, new Calendar(year, month, selected, parentPanel));
                }
                public void mousePressed(MouseEvent e) {}
                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {}
                public void mouseExited(MouseEvent e) {}
            });
            days.add(dayLabel);
        }
        for (int i = 0; i < (42 - (j + daysNum)); i++) {
            days.add(new DayLabel("", Color.decode("#e3deca"), Color.BLACK, false));
        }
        add(days, BorderLayout.CENTER);
    }

 private static void resetMainPanel(JPanel mainPanel, LocalDate selectedDay, Calendar newCalendar) {
    Component[] components = mainPanel.getComponents();
    
    for (Component comp : components) {
        if (comp instanceof Calendar) { 
            mainPanel.remove(comp); // Chỉ xóa Calendar cũ
            break;
        }
    }

    // Thêm Calendar mới
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 1; // Đặt Calendar ở dòng thứ 2, dưới tiêu đề
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;

    mainPanel.add(newCalendar, constraints);

    mainPanel.revalidate();
    mainPanel.repaint();
}



}

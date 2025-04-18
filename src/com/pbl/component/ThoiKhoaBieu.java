
package com.pbl.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ThoiKhoaBieu extends javax.swing.JPanel {

    private JTextField txtName, txtClass;
    private String[][] tkbData = new String[10][7]; // Lưu thời khóa biểu
    private final int startX = 50 + 100, startY = 120; // Điểm bắt đầu bảng
    private final int cellWidth = 100, cellHeight = 50;
    
    public ThoiKhoaBieu() {
        initComponents();
        setLayout(null);
        setOpaque(false);
        
      
        txtName = new JTextField(10);
        txtClass = new JTextField(10);
        txtName.setBounds(460, 35, 100, 25);
        txtClass.setBounds(650, 35, 100, 25);
        add(txtName);
        add(txtClass);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = (e.getX() - startX) / cellWidth;
                int row = (e.getY() - startY) / cellHeight;

                if (col >= 0 && col < 7 && row >= 0 && row < 10) {
                    String input = JOptionPane.showInputDialog("Nhập môn học:");
                    if (input != null) {
                        tkbData[row][col] = input;
                        repaint(); 
                    }
                }
            }
        });
    }
    
    
    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ tiêu đề
        g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
        int panelWidth = 250, panelHeight = 40;
        g2d.setColor(new Color(255, 204, 204));
        g2d.fillRect(0, 0, panelWidth, panelHeight);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth("THỜI KHÓA BIỂU");
        int x = (panelWidth - textWidth) / 2;
        int y = panelHeight / 2 + fm.getAscent() / 2;
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("THỜI KHÓA BIỂU", x + 2, y + 2);
        g2d.setColor(Color.WHITE);
        g2d.drawString("THỜI KHÓA BIỂU", x, y);

        // Vẽ nhãn "NAME" và "CLASS"
        g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2d.setColor(Color.WHITE);
        g2d.drawString("NAME", 400, 50);
        g2d.drawString("CLASS", 590, 50);
        

        // Vẽ tiêu đề bảng
        String[] headers = { "Tiết", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ Nhật" };
        g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2d.setColor(new Color(250, 250, 235));
        g2d.fillRect(startX - 100, startY - 40, 8 * cellWidth, 40);

        for (int i = 0; i < 8; i++) {
            g2d.setColor(Color.BLACK);
            g2d.drawRect(startX - 100 + i * cellWidth, startY - 40, cellWidth, 40);
            g2d.drawString(headers[i], startX - 100 + i * cellWidth + 15, startY - 15);
        }

        // Vẽ cột số thứ tự tiết học
        for (int i = 0; i < 10; i++) {
            g2d.setColor(new Color(240, 150, 150));
            g2d.fillRect(startX - 100, startY + i * cellHeight, cellWidth, cellHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(startX - 100, startY + i * cellHeight, cellWidth, cellHeight);
            g2d.drawString(String.valueOf(i + 1), startX - 100 + 40, startY + i * cellHeight + 30);
        }

        // Vẽ ô trống của thời khóa biểu
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 10; j++) {
                g2d.setColor(new Color(250, 250, 235));
                g2d.fillRect(startX + i * cellWidth, startY + j * cellHeight, cellWidth, cellHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(startX + i * cellWidth, startY + j * cellHeight, cellWidth, cellHeight);

                // Hiển thị dữ liệu đã nhập
                if (tkbData[j][i] != null) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(tkbData[j][i], startX + i * cellWidth + 15, startY + j * cellHeight + 30);
                }
            }
        }
    }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


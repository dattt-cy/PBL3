
package com.pbl.component; 

import com.pbl.event.ComponentImageExporter;
import com.pbl.event.EventThoiKhoaBieu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThoiKhoaBieu extends JPanel {

   
    private Map<Integer, Map<Integer, String>> displayData;

    // --- TRẠNG THÁI CHỈNH SỬA ---
    private JTextField editingField;
    private int editingDay = -1;
    private int editingSlot = -1;

    // --- KÍCH THƯỚC THAM CHIẾU (BASE SIZE) - Dựa trên thiết kế ban đầu  ---
    // Đây là kích thước gốc, chúng ta sẽ co giãn các thành phần dựa trên các giá trị này.
    private static final int BASE_PANEL_WIDTH = 1200;
    private static final int BASE_PANEL_HEIGHT = 700;
    private static final int BASE_START_X = 70;
    private static final int BASE_TIET_COLUMN_WIDTH = 50;
    private static final int BASE_CELL_WIDTH = 153;
    private static final int BASE_CELL_HEIGHT = 55;
    private static final int BASE_HEADER_HEIGHT = 30;
    private static final int BASE_TITLE_MARGIN_TOP = 25;
    private static final int BASE_PADDING = 4;
    private static final int BASE_CELL_GAP = 3;
    private static final int BASE_SEPARATOR_HEIGHT = 5;
    private static final int BASE_BUTTON_WIDTH = 100;
    private static final int BASE_BUTTON_HEIGHT = 30;
    private static final int BASE_BUTTON_MARGIN_RIGHT = 15;
    private static final int BASE_BUTTON_MARGIN_TOP = 10;

    // --- FONT THAM CHIẾU ---
    private static final Font BASE_TITLE_FONT = new Font("SansSerif", Font.BOLD, 20);
    private static final Font BASE_HEADER_FONT = new Font("SansSerif", Font.BOLD, 14);
    private static final Font BASE_SLOT_FONT = new Font("SansSerif", Font.BOLD, 11);
    private static final Font BASE_CONTENT_FONT = new Font("SansSerif", Font.PLAIN, 16);

    // --- MÀU SẮC (Không thay đổi) ---
    private final Color backgroundColor = new Color(235, 245, 255);
    private final Color titleColor = new Color(0, 51, 102);
    private final Color headerBgColor = new Color(0xB4, 0xEB, 0xE6);
    private final Color headerBorderColor = new Color(0x64, 0x95, 0xED);
    private final Color headerTextColor = new Color(0x00, 0x1C, 0x30);
    private final Color slotBgColor = new Color(0xE3, 0xF6, 0xFF);
    private final Color slotBorderColor = headerBorderColor;
    private final Color slotTextColor = Color.BLACK;
    private final Color gridBgColor = new Color(252, 252, 252);
    private final Color gridBorderColor = new Color(200, 200, 200);
    private final Color gridTextColor = Color.BLACK;
    private final Color separatorColor = new Color(150, 180, 210);
    private final Color editingFieldBgColor = new Color(255, 255, 220);
    private final Color buttonBgColor = new Color(0xE3, 0xB8, 0xB8);
    private final Color buttonFgColor = new Color(0x03, 0x4C, 0x36);

    // --- CÁC THÀNH PHẦN CON ---
    private JButton btnUpload;
    private JButton btnDownload;

    public ThoiKhoaBieu() {
        // *** Quan trọng: Vẫn giữ layout null để toàn quyền kiểm soát vị trí ***
        setLayout(null);
        setOpaque(true);
        setBackground(backgroundColor);

        initButtons();
        initMouseListener();

        // Thêm ComponentListener để phát hiện sự kiện resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Khi panel thay đổi kích thước, cập nhật lại vị trí các nút và vẽ lại toàn bộ
                updateComponentLayout();
                repaint();
            }
        });

        setData(new HashMap<>()); // Khởi tạo với dữ liệu rỗng
    }

    private void initButtons() {
        btnUpload = new JButton("Upload");
        btnDownload = new JButton("Download");

        configureButton(btnUpload);
        configureButton(btnDownload);

        add(btnUpload);
        add(btnDownload);

        btnUpload.addActionListener(e -> {
           
            cancelEdit();
            try {
                new EventThoiKhoaBieu(ThoiKhoaBieu.this);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ThoiKhoaBieu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ThoiKhoaBieu.class.getName()).log(Level.SEVERE, "Lỗi khi xử lý upload", ex);
                JOptionPane.showMessageDialog(ThoiKhoaBieu.this, "Lỗi khi xử lý upload:\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDownload.addActionListener(e -> {
            ComponentImageExporter.exportComponentAsImage(this, this);
        });
    }

    private void configureButton(JButton button) {
        button.setBackground(buttonBgColor);
        button.setForeground(buttonFgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
    }
    
    // Phương thức này sẽ được gọi mỗi khi kích thước panel thay đổi
    private void updateComponentLayout() {
        // Tính toán layout động
        DynamicLayout layout = calculateDynamicLayout(getWidth(), getHeight());

        // Cập nhật font và vị trí cho các button
        btnUpload.setFont(layout.headerFont);
        btnDownload.setFont(layout.headerFont);
        
        int uploadButtonX = getWidth() - layout.buttonWidth - layout.buttonMarginRight;
        btnUpload.setBounds(uploadButtonX, layout.buttonMarginTop, layout.buttonWidth, layout.buttonHeight);

        int downloadButtonX = uploadButtonX - layout.buttonWidth - 15; // 15 là khoảng cách giữa 2 nút
        btnDownload.setBounds(downloadButtonX, layout.buttonMarginTop, layout.buttonWidth, layout.buttonHeight);
        
        // Nếu đang chỉnh sửa, hủy bỏ để tránh lỗi vị trí
        cancelEdit();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Lấy kích thước hiện tại của panel
        int currentWidth = getWidth();
        int currentHeight = getHeight();
        
        // Không vẽ gì nếu panel quá nhỏ
        if (currentWidth < 100 || currentHeight < 100) return;

        // Tính toán tất cả các kích thước và vị trí dựa trên kích thước hiện tại
        DynamicLayout layout = calculateDynamicLayout(currentWidth, currentHeight);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        try {
            drawTitle(g2d, layout);
            drawHeader(g2d, layout);
            drawSlotColumn(g2d, layout);
            drawSeparator(g2d, layout);
            drawGridAndData(g2d, layout);
        } finally {
            g2d.dispose();
        }
    }
    
    private void drawTitle(Graphics2D g2d, DynamicLayout layout) {
        g2d.setFont(layout.titleFont);
        FontMetrics fmTitle = g2d.getFontMetrics();
        String title = "THỜI KHÓA BIỂU";
        int titleWidth = fmTitle.stringWidth(title);
        
        int titleContentWidth = 7 * (layout.cellWidth + layout.cellGap) - layout.cellGap;
        int titleX = layout.startX + (titleContentWidth - titleWidth) / 2;
        int titleY = layout.titleMarginTop + fmTitle.getAscent();
        
        g2d.setColor(titleColor);
        g2d.drawString(title, titleX, titleY);
    }

    private void drawHeader(Graphics2D g2d, DynamicLayout layout) {
        String[] headers = { "Tiết", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ Nhật" };
        g2d.setFont(layout.headerFont);
        FontMetrics fmHeader = g2d.getFontMetrics();
        
        for (int i = 0; i < headers.length; i++) {
            int currentHeaderX;
            int currentHeaderWidth;
            if (i == 0) { // Cột "Tiết"
                currentHeaderWidth = layout.tietColumnWidth;
                currentHeaderX = layout.startX - layout.tietColumnWidth - layout.cellGap;
            } else { // Các cột ngày
                currentHeaderWidth = layout.cellWidth;
                currentHeaderX = layout.startX + (i - 1) * (layout.cellWidth + layout.cellGap);
            }
            
            g2d.setColor(headerBgColor);
            g2d.fillRect(currentHeaderX, layout.headerY, currentHeaderWidth, layout.headerHeight);
            g2d.setColor(headerBorderColor);
            g2d.drawRect(currentHeaderX, layout.headerY, currentHeaderWidth, layout.headerHeight);
            
            int textWidth = fmHeader.stringWidth(headers[i]);
            int xText = currentHeaderX + (currentHeaderWidth - textWidth) / 2;
            int yText = layout.headerY + (layout.headerHeight - fmHeader.getHeight()) / 2 + fmHeader.getAscent();
            
            g2d.setColor(headerTextColor);
            g2d.drawString(headers[i], xText, yText);
        }
    }

    private void drawSlotColumn(Graphics2D g2d, DynamicLayout layout) {
        g2d.setFont(layout.slotFont);
        FontMetrics fmSlot = g2d.getFontMetrics();
        int tietColumnX = layout.startX - layout.tietColumnWidth - layout.cellGap;

        for (int i = 0; i < 10; i++) {
            int currentSlotY = layout.calculateRowY(i);
            
            g2d.setColor(slotBgColor);
            g2d.fillRect(tietColumnX, currentSlotY, layout.tietColumnWidth, layout.cellHeight);
            g2d.setColor(slotBorderColor);
            g2d.drawRect(tietColumnX, currentSlotY, layout.tietColumnWidth, layout.cellHeight);
            
            String slotText = String.valueOf(i + 1);
            int textWidth = fmSlot.stringWidth(slotText);
            int xText = tietColumnX + (layout.tietColumnWidth - textWidth) / 2;
            int yText = currentSlotY + (layout.cellHeight - fmSlot.getHeight()) / 2 + fmSlot.getAscent();
            
            g2d.setColor(slotTextColor);
            g2d.drawString(slotText, xText, yText);
        }
    }
    
    private void drawSeparator(Graphics2D g2d, DynamicLayout layout) {
        int separatorStartX = layout.startX - layout.tietColumnWidth - layout.cellGap;
        int separatorEndX = layout.startX + 7 * (layout.cellWidth + layout.cellGap) - layout.cellGap;
        int separatorWidth = separatorEndX - separatorStartX;
        
        g2d.setColor(separatorColor);
        g2d.fillRect(separatorStartX, layout.separatorY, separatorWidth, layout.separatorHeight);
    }
    
    private void drawGridAndData(Graphics2D g2d, DynamicLayout layout) {
        g2d.setFont(layout.contentFont);
        FontMetrics fmContent = g2d.getFontMetrics();
        int availableWidth = layout.cellWidth - 2 * layout.padding;

        for (int dayIndex = 0; dayIndex < 7; dayIndex++) {
            Map<Integer, String> daySchedule = displayData.get(dayIndex);
            int currentX = layout.startX + dayIndex * (layout.cellWidth + layout.cellGap);

            for (int slotIndex = 0; slotIndex < 10; slotIndex++) {
                int currentY = layout.calculateRowY(slotIndex);

                g2d.setColor(gridBgColor);
                g2d.fillRect(currentX, currentY, layout.cellWidth, layout.cellHeight);
                g2d.setColor(gridBorderColor);
                g2d.drawRect(currentX, currentY, layout.cellWidth, layout.cellHeight);

                if (dayIndex != editingDay || slotIndex != editingSlot) {
                    if (daySchedule != null) {
                        String cellContent = daySchedule.getOrDefault(slotIndex, "");
                        if (!cellContent.isEmpty()) {
                            g2d.setColor(gridTextColor);
                            drawWrappedText(g2d, cellContent, currentX + layout.padding, currentY + layout.padding, 
                                            availableWidth, layout.cellHeight - 2 * layout.padding, fmContent);
                        }
                    }
                }
            }
        }
    }

    // --- XỬ LÝ SỰ KIỆN CHUỘT VÀ CHỈNH SỬA ---
    private void initMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (editingField != null && editingField.isVisible()) {
                    if (editingField.getBounds().contains(e.getPoint())) {
                        return; // Click vào chính ô đang sửa
                    }
                    confirmEdit(); // Click ra ngoài, xác nhận
                }

                if (e.getClickCount() == 1 && editingField == null) {
                    findCellAndStartEditing(e.getX(), e.getY());
                }
            }
        });
    }

    private void findCellAndStartEditing(int clickX, int clickY) {
        DynamicLayout layout = calculateDynamicLayout(getWidth(), getHeight());

        if (clickX < layout.startX || clickY < layout.gridStartY) {
            cancelEdit();
            return;
        }

        int effectiveClickX = clickX - layout.startX;
        int dayIndex = effectiveClickX / (layout.cellWidth + layout.cellGap);
        int xInDayCell = effectiveClickX % (layout.cellWidth + layout.cellGap);

        if (dayIndex >= 7 || xInDayCell >= layout.cellWidth) {
            cancelEdit();
            return;
        }

        int slotIndex = -1;
        if (clickY >= layout.gridStartY && clickY < layout.separatorY) {
            int relativeY = clickY - layout.gridStartY;
            slotIndex = relativeY / (layout.cellHeight + layout.cellGap);
            if (slotIndex >= 5 || relativeY % (layout.cellHeight + layout.cellGap) >= layout.cellHeight) {
                cancelEdit(); return;
            }
        } else if (clickY >= layout.separatorY + layout.separatorHeight + layout.cellGap) {
            int relativeY = clickY - (layout.separatorY + layout.separatorHeight + layout.cellGap);
            slotIndex = 5 + (relativeY / (layout.cellHeight + layout.cellGap));
             if (slotIndex >= 10 || relativeY % (layout.cellHeight + layout.cellGap) >= layout.cellHeight) {
                cancelEdit(); return;
            }
        } else {
            cancelEdit(); return;
        }

        if (dayIndex >= 0 && slotIndex >= 0) {
            startEditing(dayIndex, slotIndex);
        } else {
            cancelEdit();
        }
    }

    private void startEditing(int day, int slot) {
        if (editingField != null) {
            confirmEdit();
        }
        
        DynamicLayout layout = calculateDynamicLayout(getWidth(), getHeight());

        editingDay = day;
        editingSlot = slot;

        int cellX = layout.startX + editingDay * (layout.cellWidth + layout.cellGap);
        int cellY = layout.calculateRowY(editingSlot);

        editingField = new JTextField();
        editingField.setFont(layout.contentFont);
        editingField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLUE, 1),
            BorderFactory.createEmptyBorder(2, layout.padding, 2, layout.padding)
        ));
        editingField.setBackground(editingFieldBgColor);
        
        String currentText = displayData.computeIfAbsent(editingDay, k -> new HashMap<>())
                                      .getOrDefault(editingSlot, "");
        editingField.setText(currentText);
        editingField.setBounds(cellX, cellY, layout.cellWidth, layout.cellHeight);

        editingField.addActionListener(e -> confirmEdit());
        editingField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                 confirmEdit();
            }
        });
        editingField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancelEdit();
                }
            }
        });

        add(editingField);
        editingField.selectAll();
        editingField.requestFocusInWindow();
        setComponentZOrder(editingField, 0); // Vẽ trên cùng
        repaint(cellX, cellY, layout.cellWidth, layout.cellHeight);
    }
    
    private void confirmEdit() {
        if (editingField == null) return;
        String newText = editingField.getText().trim();
        displayData.computeIfAbsent(editingDay, k -> new HashMap<>()).put(editingSlot, newText);
        removeEditingField();
        repaint();
    }

    private void cancelEdit() {
        if (editingField == null) return;
        int oldX = editingField.getX();
        int oldY = editingField.getY();
        int oldW = editingField.getWidth();
        int oldH = editingField.getHeight();
        removeEditingField();
        repaint(oldX, oldY, oldW, oldH);
    }

    private void removeEditingField() {
        if (editingField != null) {
            editingField.setVisible(false);
            remove(editingField);
            editingField = null;
            editingDay = -1;
            editingSlot = -1;
        }
    }

    // --- CÁC HÀM HELPER VÀ GETTER/SETTER ---
    public void setData(Map<Integer, Map<Integer, String>> data) {
        cancelEdit();
        this.displayData = data != null ? data : new HashMap<>();
        repaint(); // Chỉ cần vẽ lại
    }
    
    public Map<Integer, Map<Integer, String>> getDisplayData() {
        return displayData;
    }
    
    // Hàm helper để scale font
    private Font scaleFont(Font baseFont, float scale) {
        // scale có thể là scaleX hoặc scaleY, hoặc trung bình 2 cái
        float newSize = baseFont.getSize2D() * scale;
        return baseFont.deriveFont(newSize);
    }
    
    // Hàm helper vẽ text xuống dòng
    private void drawWrappedText(Graphics2D g2d, String text, int x, int y, int maxWidth, int maxHeight, FontMetrics fm) {
        // (Giữ nguyên logic của bạn)
        int lineHeight = fm.getHeight();
        int currentY = y + fm.getAscent();
        String[] manualLines = text.split(";");

        for (String manualLine : manualLines) {
            manualLine = manualLine.trim();
            if (manualLine.isEmpty()) continue;

            String[] words = manualLine.split("\\s+");
            StringBuilder currentLine = new StringBuilder();
            for (String word : words) {
                String testLine = currentLine.length() > 0 ? currentLine + " " + word : word;
                if (fm.stringWidth(testLine) <= maxWidth) {
                    if (currentLine.length() > 0) currentLine.append(" ");
                    currentLine.append(word);
                } else {
                    if (currentY <= y + maxHeight - fm.getDescent()) {
                        g2d.drawString(currentLine.toString(), x, currentY);
                    } else {
                        return;
                    }
                    currentY += lineHeight;
                    currentLine = new StringBuilder(word);
                    if (fm.stringWidth(currentLine.toString()) > maxWidth) {
                       currentLine.setLength(0);
                       break; 
                    }
                }
            }
            if (currentLine.length() > 0 && currentY <= y + maxHeight - fm.getDescent()) {
                g2d.drawString(currentLine.toString(), x, currentY);
                currentY += lineHeight;
            }
            if (currentY > y + maxHeight - fm.getDescent()) break;
        }
    }
    
    /**
     * Lớp nội bộ để chứa tất cả các giá trị layout đã được tính toán động.
     * Điều này giúp giữ code gọn gàng và đảm bảo paintComponent và các listener
     * sử dụng cùng một bộ giá trị.
     */
    private static class DynamicLayout {
        // Tỷ lệ co giãn
        float scaleX, scaleY;
        
        // Kích thước và vị trí đã scale
        int startX, cellWidth, cellHeight, headerHeight, tietColumnWidth;
        int cellGap, padding, separatorHeight, titleMarginTop;
        int buttonWidth, buttonHeight, buttonMarginTop, buttonMarginRight;

        // Tọa độ Y tính toán
        int headerY, gridStartY, separatorY;
        
        // Font đã scale
        Font titleFont, headerFont, slotFont, contentFont;

        // Hàm helper để tính Y của hàng
        int calculateRowY(int rowIndex) {
            if (rowIndex < 5) {
                return gridStartY + rowIndex * (cellHeight + cellGap);
            } else {
                return separatorY + separatorHeight + cellGap + (rowIndex - 5) * (cellHeight + cellGap);
            }
        }
    }
    
    /**
     * Tính toán tất cả các kích thước và vị trí dựa trên kích thước panel hiện tại.
     * Đây là "bộ não" của logic responsive.
     */
    private DynamicLayout calculateDynamicLayout(int currentWidth, int currentHeight) {
        DynamicLayout layout = new DynamicLayout();
        
        // Tính tỷ lệ co giãn so với kích thước gốc
        layout.scaleX = (float) currentWidth / BASE_PANEL_WIDTH;
        layout.scaleY = (float) currentHeight / BASE_PANEL_HEIGHT;
        // Sử dụng tỷ lệ nhỏ hơn để giữ đúng tỷ lệ khung hình, tránh bị méo
        float scale = Math.min(layout.scaleX, layout.scaleY);

        // Áp dụng tỷ lệ để tính toán các kích thước mới
        layout.startX = (int) (BASE_START_X * layout.scaleX);
        layout.cellWidth = (int) (BASE_CELL_WIDTH * layout.scaleX);
        layout.cellHeight = (int) (BASE_CELL_HEIGHT * layout.scaleY);
        layout.headerHeight = (int) (BASE_HEADER_HEIGHT * layout.scaleY);
        layout.tietColumnWidth = (int) (BASE_TIET_COLUMN_WIDTH * layout.scaleX);
        layout.cellGap = (int) (BASE_CELL_GAP * scale); // Gap nên scale đồng bộ
        layout.padding = (int) (BASE_PADDING * scale);
        layout.separatorHeight = (int) (BASE_SEPARATOR_HEIGHT * layout.scaleY);
        layout.titleMarginTop = (int) (BASE_TITLE_MARGIN_TOP * layout.scaleY);

        // Tính kích thước nút
        layout.buttonWidth = (int) (BASE_BUTTON_WIDTH * layout.scaleX);
        layout.buttonHeight = (int) (BASE_BUTTON_HEIGHT * layout.scaleY);
        layout.buttonMarginTop = (int) (BASE_BUTTON_MARGIN_TOP * layout.scaleY);
        layout.buttonMarginRight = (int) (BASE_BUTTON_MARGIN_RIGHT * layout.scaleX);
        
        // Scale các font chữ
        layout.titleFont = scaleFont(BASE_TITLE_FONT, scale);
        layout.headerFont = scaleFont(BASE_HEADER_FONT, scale);
        layout.slotFont = scaleFont(BASE_SLOT_FONT, scale);
        layout.contentFont = scaleFont(BASE_CONTENT_FONT, scale);
        
        // Tính toán lại các tọa độ Y quan trọng
        FontMetrics fmTitle = this.getFontMetrics(layout.titleFont);
        layout.headerY = layout.titleMarginTop + fmTitle.getHeight() + 10;
        layout.gridStartY = layout.headerY + layout.headerHeight + layout.cellGap;
        layout.separatorY = layout.gridStartY + 5 * (layout.cellHeight + layout.cellGap);
        
        return layout;
    }

}
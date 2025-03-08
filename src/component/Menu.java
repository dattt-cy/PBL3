/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;
import swing.MenuAnimation;
import event.EventMenu;
import event.EventMenuSelected;
import event.EventShowPopupMenu;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Scrollbar;
import java.awt.image.renderable.RenderContext;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.ModelMenu;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import scrollBar.ScrollBarCustom;
import swing.MenuItem;

/**
 *
 * @author ADMIN
 */
public class Menu extends javax.swing.JPanel {


    public boolean isShowMenu() {
        return showMenu;
    }
    public void AddEvent(EventMenuSelected event) {
        this.event = event;
    }
    public void setEnableMenu(boolean enableMenu) {
        this.enbaleMenu = enableMenu;
    }

   
    public void setEnbaleMenu(boolean enbaleMenu) {
        this.enbaleMenu = enbaleMenu;
    }

  

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }
    public void addEventShowPopup(EventShowPopupMenu eventShowPopup){
        this.eventShowPopup = eventShowPopup;
    }
    
    
    private final MigLayout layout;
    private EventMenuSelected event;
    private EventShowPopupMenu eventShowPopup;
    private boolean enbaleMenu = true;
    private boolean showMenu = true;
    
    public Menu() {
        initComponents();
        setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setVerticalScrollBar(new ScrollBarCustom());
        layout = new MigLayout("wrap, fillx, insets 0", "[fill]", "[]0[]");
        panel.setLayout(layout);
        
        
    }
    public void initMenuItem(){
       addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/message.png")), "Dashboard", "Home", "Buttons", "Cards", "Tabs", "Accordions", "Modals"));
       

    }
    private void addMenu(ModelMenu menu){          
        panel.add(new MenuItem(menu, getEventMenu(), event, panel.getComponentCount()), "h 40!");

        
    }
    private EventMenu getEventMenu(){
        return new EventMenu() {
            @Override
            public boolean menuPressed(Component com, boolean open) {
                if(enbaleMenu){
                    if(showMenu){
                        if(open){
                            new MenuAnimation(layout, com).openMenu();
                        }else{
                            new MenuAnimation(layout, com).closeMenu();
                        }
                        return true;
                    }else{
                        eventShowPopup.showPopUp(com);
                    }
                }
                return false;
            }
        };
    }
    
     public void hideallMenu() {
        for (Component com : panel.getComponents()) {
            MenuItem item = (MenuItem) com;
            if (item.isOpen()) {
                new MenuAnimation(layout, com, 500).closeMenu();
                item.setOpen(false);
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        profile1 = new component.Profile();

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.setBackground(new java.awt.Color(153, 255, 255));
        panel.setOpaque(false);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );

        sp.setViewportView(panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(profile1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(profile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sp, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    @Override
    protected void paintComponent(Graphics grphcs) {
    Graphics2D g2 = (Graphics2D) grphcs;
      GradientPaint gra = new GradientPaint(
        0, 0, new Color(60, 40, 75),          // Màu tím đen
        (int)(getWidth() * 0.85), 0,          // Điểm chuyển màu (85% màn hình)
        new Color(143, 137, 183)              // Màu tím nhạt
    );
    g2.setPaint(gra);
    g2.fillRect(0, 0, getWidth(), getHeight());
    super.paintComponent(grphcs);
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel;
    private component.Profile profile1;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}

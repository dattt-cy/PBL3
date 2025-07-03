/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pbl.main;

import com.pbl.component.Login;
import com.pbl.component.PanelVerifyCode;
import com.pbl.component.PanelVerifyGmail;
import com.pbl.component.Register;
import com.pbl.component.SetNewPassword;
import com.pbl.dao.UsersDAO;
import com.pbl.dao.UsersDAOImp;
import com.pbl.form.MainForm;
import com.pbl.model.Users;
import com.pbl.service.AuthService;
import com.pbl.service.EmailNotificationService;
import com.pbl.service.UserService;
import com.pbl.utility.PasswordUtils;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAVEN
 */
public class LoginFrame extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    private Register register;
    private Login login;
    private PanelVerifyCode verify;
    private PanelVerifyGmail verifyGm;
    private SetNewPassword setPass;
    private UsersDAO userDAO;
    private UserService userService;
    private boolean isForget;
    private Users ForgotUser;
    private AuthService athService;

    public LoginFrame() {
        initComponents();
        setSize(599, 728);
        setLocationRelativeTo(null);
        userDAO = new UsersDAOImp();
        userService = new UserService();
        verify = new PanelVerifyCode();
        verifyGm = new PanelVerifyGmail();
        athService = new AuthService();
        ActionListener eventLogin = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    performLogin();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        login = new Login(eventLogin);
        setPass = new SetNewPassword(eventLogin);

        ActionListener eventRegister = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                registerForm();
            }
        };
        ActionListener eventForget = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                performForget();
            }
        };
        register = new Register(eventRegister);
        ActionListener eventSetPass = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                performSetNewPassword();
            }
        };
        setPass = new SetNewPassword(eventSetPass);
        slide.setAnimate(10);
        slide.init(login, register, verify, verifyGm, setPass);
        login.addEventRegister(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(1);
                register.register();
            }
        });
        login.addEventForget(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(3);

            }
        });

        register.addEventBackLogin(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(0);
                login.login();

            }
        });

        verify.addEventRegister(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Users user = register.getUser();
                if(user != null){
                userService.deleteUser(user.getUser_id());
                slide.show(1);
                register.register();
                } else {
                    slide.show(3);
                }

            }
        });
        verify.addEventButtonOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // Nếu đang ở luồng đăng ký (isForget == false), kiểm tra register.getUser()
                if (!isForget) {
                    Users user = register.getUser();
                    if (user == null) {
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                "Đang có lỗi, vui lòng thử lại.",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    boolean ok = userService.verifyCodeWithUser(
                            user.getUser_id(),
                            verify.getInputCode().trim()
                    );
                    if (ok) {
                        userService.doneVerify(user.getUser_id());
                        System.out.println("Xác minh đăng ký thành công");
                        login.setTxtUser("");
                        login.setTxtPass("");
                        login.setTxtmes("");
                        slide.show(0);
                        login.login();
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                "Mã xác minh không đúng.",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if (ForgotUser == null) {
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                "Đang có lỗi, vui lòng thử lại.",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;

                    }
                    boolean ok2 = userService.verifyCodeWithUser(
                            ForgotUser.getUser_id(),
                            verify.getInputCode().trim()
                    );
                    if (ok2) {
                        userService.doneVerify(ForgotUser.getUser_id());
                        slide.show(4);  
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                "Mã xác minh không đúng.",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);;
                    }
                }
            }
        });

        verifyGm.addEventButtonOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String email = verifyGm.getEmail().trim();
                Users user = userService.getUserByEmail(email);

                if (user != null) {
                    String code = userService.generateVerifyCode();
                    user.setVerifyCode(code);
                    athService.sendMain(user);
                    ForgotUser = user;
                    userService.UpdateUser(user);
                    isForget = true;

                    slide.show(2);
                    verify.verify();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Email không tồn tại.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        verifyGm.addEventCancel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(0);
                login.login();
            }
        });

    }

    private void registerForm() {
        performRegister();
    }

    private void performLogin() throws SQLException {

        String email = login.getTxtUser().getText().trim();
        String password = new String(login.getTxtPass().getPassword()).trim();
        if (email.isEmpty() || password.isEmpty()) {
            login.setTxtmes("Vui lòng điền đầy đủ thông tin.");
            return;
        }
        AuthService authService = new AuthService();

        Users user = authService.Login(email, password);

        if (user != null) {
            Main showMain = new Main(user);

            showMain.setVisible(true);

            this.dispose();

        } else {
            login.setTxtmes("Sai email hoặc mật khẩu.");
            return;
        }
    }

    private void performSetNewPassword() {
        String pass = setPass.getNewPassword().trim();
        String confirm = setPass.getConfirmPassword().trim();

        if (pass.isEmpty() || confirm.isEmpty()) {
            setPass.setMessage("Vui lòng điền đầy đủ thông tin.");
            return;
        }
        if (!pass.equals(confirm)) {
            setPass.setMessage("Mật khẩu không khớp.");
            return;
        }
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(confirm, salt);

        // Cập nhật mật khẩu mới cho forgotUser
        ForgotUser.setPassword(hashedPassword);
        ForgotUser.setSalt(salt);
        userService.UpdateUser(ForgotUser);

        JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công! Mời đăng nhập lại.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        ForgotUser = null;

        login.setTxtUser("");
        login.setTxtPass("");
        login.setTxtmes("");
        slide.show(0);
        login.login();
    }

    private void performForget() {
        String email = verifyGm.getEmail().trim();
        if (email.isEmpty()) {
            login.setTxtmes("Vui lòng điền đầy đủ thông tin.");
            return;
        }
        Users user = userService.getUserByEmail(email);
        if (user != null) {
            userService.generateVerifyCode();
            slide.show(2);
            verify.verify();

        } else {

        }
    }

    private void performRegister() {
        String name = register.getTxtUser().getText().trim();
        String password = new String(register.getTxtPass().getPassword()).trim();
        String confirmPassword = new String(register.getTxtPass1().getPassword()).trim();
        String email = register.getTxtUser1().getText().trim();

        if (userService.checkDuplicateUser(name)) {
            register.setMessage("Username đã tồn tại.");
            return;
        }
        if (userService.checkDuplicateEmail(email)) {
            register.setMessage("Mail đã tồn tại.");
            return;
        }
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            register.setMessage("Vui lòng điền đầy đủ thông tin.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            register.setMessage("Mật khẩu và mật khẩu xác nhận không khớp.");
            return;
        }

        try {
            AuthService authService = new AuthService();
            authService.register(name, email, password, "user");
            slide.show(2);
            verify.verify();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

        panelGradiente1 = new com.pbl.swing.PanelGradiente();
        panelBorder1 = new com.pbl.swing.PanelBorder2();
        slide = new com.pbl.swing.PanelSlide();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelGradiente1.setColorPrimario(new java.awt.Color(146, 233, 251));
        panelGradiente1.setColorSecundario(new java.awt.Color(12, 137, 163));

        panelBorder1.setPreferredSize(new java.awt.Dimension(385, 520));

        javax.swing.GroupLayout slideLayout = new javax.swing.GroupLayout(slide);
        slide.setLayout(slideLayout);
        slideLayout.setHorizontalGroup(
            slideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );
        slideLayout.setVerticalGroup(
            slideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 528, Short.MAX_VALUE)
        );

        panelBorder1.setLayer(slide, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slide, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slide, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelGradiente1.setLayer(panelBorder1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout panelGradiente1Layout = new javax.swing.GroupLayout(panelGradiente1);
        panelGradiente1.setLayout(panelGradiente1Layout);
        panelGradiente1Layout.setHorizontalGroup(
            panelGradiente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGradiente1Layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
        panelGradiente1Layout.setVerticalGroup(
            panelGradiente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGradiente1Layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGradiente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.pbl.swing.PanelBorder2 panelBorder1;
    private com.pbl.swing.PanelGradiente panelGradiente1;
    private com.pbl.swing.PanelSlide slide;
    // End of variables declaration//GEN-END:variables
}

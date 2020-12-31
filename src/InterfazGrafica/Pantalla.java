/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfazGrafica;

import Excepciones.UsuarioNoExisteException;
import Excepciones.UsuarioYaExisteException;
import Excepciones.WrongPasswordException;
import MasterMind.Almacen_Login;
import MasterMind.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Yaros
 */
public class Pantalla extends javax.swing.JFrame {
    
    private Almacen_Login almacen = new Almacen_Login();
    private Usuario usuario_logueado;
    
    /**
     * Creates new form Pantalla
     */
    public Pantalla() {
        
        //añado un usuario para pruebas
        try {
            almacen.registrar(new Usuario("test", "123"));
        } catch (UsuarioYaExisteException ex) {
            Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initComponents();
        
        Login.setVisible(true);
        NuevoUsuario.setVisible(false);
        MenuPrincipal.setVisible(false);
    }
    
    private void showMessage(String s){
        JOptionPane.showMessageDialog(rootPane, s);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Login = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldLoginUsuario = new javax.swing.JTextField();
        jTextFieldPasswordUsuario = new javax.swing.JTextField();
        jButtonNuevoUsuario = new javax.swing.JButton();
        jButtonLogin = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        NuevoUsuario = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldNombreNUsuario = new javax.swing.JTextField();
        jTextFieldPasswordNUsuario = new javax.swing.JTextField();
        jButtonOKNUsuario = new javax.swing.JButton();
        jButtonVolverNUsuario = new javax.swing.JButton();
        MenuPrincipal = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Master Mind");
        setPreferredSize(new java.awt.Dimension(500, 500));
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        Login.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel1.setText("Usuario");

        jLabel2.setText("Clave");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Master Mind");

        jButtonNuevoUsuario.setText("Nuevo Usuario");
        jButtonNuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoUsuarioActionPerformed(evt);
            }
        });

        jButtonLogin.setText("Login");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jButton3.setText("Cargar Datos");

        javax.swing.GroupLayout LoginLayout = new javax.swing.GroupLayout(Login);
        Login.setLayout(LoginLayout);
        LoginLayout.setHorizontalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginLayout.createSequentialGroup()
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(LoginLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, LoginLayout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, LoginLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(LoginLayout.createSequentialGroup()
                                .addComponent(jButtonNuevoUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonLogin))
                            .addGroup(LoginLayout.createSequentialGroup()
                                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(87, 87, 87)
                                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldLoginUsuario)
                                    .addComponent(jTextFieldPasswordUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))))))
                .addGap(103, 103, 103))
        );
        LoginLayout.setVerticalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel3)
                .addGap(62, 62, 62)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldLoginUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldPasswordUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNuevoUsuario)
                    .addComponent(jButtonLogin))
                .addGap(76, 76, 76)
                .addComponent(jButton3)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        getContentPane().add(Login, "card2");

        NuevoUsuario.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel4.setText("Usuario");

        jLabel5.setText("Clave");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Nuevo Usuario");

        jButtonOKNUsuario.setText("Ok");
        jButtonOKNUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKNUsuarioActionPerformed(evt);
            }
        });

        jButtonVolverNUsuario.setText("Volver");
        jButtonVolverNUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVolverNUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout NuevoUsuarioLayout = new javax.swing.GroupLayout(NuevoUsuario);
        NuevoUsuario.setLayout(NuevoUsuarioLayout);
        NuevoUsuarioLayout.setHorizontalGroup(
            NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jLabel6))
                    .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                                .addComponent(jButtonOKNUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonVolverNUsuario))
                            .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGap(87, 87, 87)
                                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldNombreNUsuario)
                                    .addComponent(jTextFieldPasswordNUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))))))
                .addGap(103, 103, 103))
        );
        NuevoUsuarioLayout.setVerticalGroup(
            NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel6)
                .addGap(62, 62, 62)
                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldNombreNUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldPasswordNUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOKNUsuario)
                    .addComponent(jButtonVolverNUsuario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(NuevoUsuario, "card3");

        MenuPrincipal.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel10.setText("Login OK");

        javax.swing.GroupLayout MenuPrincipalLayout = new javax.swing.GroupLayout(MenuPrincipal);
        MenuPrincipal.setLayout(MenuPrincipalLayout);
        MenuPrincipalLayout.setHorizontalGroup(
            MenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuPrincipalLayout.createSequentialGroup()
                .addContainerGap(227, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(224, 224, 224))
        );
        MenuPrincipalLayout.setVerticalGroup(
            MenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuPrincipalLayout.createSequentialGroup()
                .addContainerGap(256, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(228, 228, 228))
        );

        getContentPane().add(MenuPrincipal, "card4");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoUsuarioActionPerformed
        // TODO add your handling code here:
        Login.setVisible(false);
        NuevoUsuario.setVisible(true);
    }//GEN-LAST:event_jButtonNuevoUsuarioActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
        String nombre = jTextFieldLoginUsuario.getText().trim();
        String clave = jTextFieldPasswordUsuario.getText().trim();
        System.out.println(nombre+" "+clave);
        
        if(nombre.isEmpty() || clave.isEmpty() || "".equals(nombre) || "".equals(clave)){
            showMessage("No se permiten campos vacios");
        } else {
            Usuario usuario = new Usuario(nombre, clave);
            try {
                usuario_logueado=almacen.identificar(usuario);
            } catch (WrongPasswordException ex) {
                showMessage("Clave incorrecta");
            } catch (UsuarioNoExisteException ex) {
                showMessage("Usuario no existe");
            } finally {
                nombre="";
                clave="";
                jTextFieldLoginUsuario.setText("");
                jTextFieldPasswordUsuario.setText("");
            }
            if(usuario_logueado!=null){
                showMessage("Bienvenido "+usuario_logueado.getNombre());
                Login.setVisible(false);
                MenuPrincipal.setVisible(true);
            }
        }
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonVolverNUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVolverNUsuarioActionPerformed
        // TODO add your handling code here:
        NuevoUsuario.setVisible(false);
        Login.setVisible(true);
    }//GEN-LAST:event_jButtonVolverNUsuarioActionPerformed

    private void jButtonOKNUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKNUsuarioActionPerformed
        // TODO add your handling code here:
        String nombre = jTextFieldNombreNUsuario.getText().trim();
        String clave = jTextFieldPasswordNUsuario.getText().trim();
        if(nombre.isEmpty() || clave.isEmpty() || "".equals(nombre) || "".equals(clave)){
            showMessage("Debes rellenar todos los campos");
        } else {
            Usuario usuario = new Usuario(nombre, clave);
            try {
                usuario=almacen.registrar(usuario);
            } catch (UsuarioYaExisteException ex) {
                usuario=null;
                showMessage("El usuario "+nombre+" ya existe");
            } finally {
                nombre="";
                clave="";
                jTextFieldNombreNUsuario.setText("");
                jTextFieldPasswordNUsuario.setText("");
            }
            
            if(usuario!=null){
                showMessage("Usuario "+usuario.getNombre()+" registrado");
            }
        }
    }//GEN-LAST:event_jButtonOKNUsuarioActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Login;
    private javax.swing.JPanel MenuPrincipal;
    private javax.swing.JPanel NuevoUsuario;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonNuevoUsuario;
    private javax.swing.JButton jButtonOKNUsuario;
    private javax.swing.JButton jButtonVolverNUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextFieldLoginUsuario;
    private javax.swing.JTextField jTextFieldNombreNUsuario;
    private javax.swing.JTextField jTextFieldPasswordNUsuario;
    private javax.swing.JTextField jTextFieldPasswordUsuario;
    // End of variables declaration//GEN-END:variables
}

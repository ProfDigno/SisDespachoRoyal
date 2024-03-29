/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.LOCAL.VariablesBD;
import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class JDiaLogin extends javax.swing.JDialog {

    /**
     * Creates new form JDiaLogin
     */
    EvenJFRAME evetbl = new EvenJFRAME();
    entidad_usuario ENTusu = new entidad_usuario();
    BO_usuario bo_usu = new BO_usuario();
    dao_usuario dao_usu = new dao_usuario();//dao_usuario
    EvenJTextField evejtf = new EvenJTextField();
    EvenMensajeJoptionpane evemsj =new EvenMensajeJoptionpane();
    Connection conn = ConnPostgres.getConnPosgres();
    Connection connser = ConnPostgres.getConnPosgres();
    EvenConexion eveconn = new EvenConexion();
    VariablesBD var = new VariablesBD();
//    entidad_caja_cierre cjcie = new entidad_caja_cierre();
//    dao_caja_cierre cjcie_dao = new dao_caja_cierre();
    cla_color_palete clacolor = new cla_color_palete();
//    dao_pro_producto dao_pro = new dao_pro_producto();
//    DAO_backup bdao = new DAO_backup();
//    private int ano_actual;

    /**
     * Creates new form FrmZonaDelivery
     */
    void abrir_formulario() {
        this.setTitle("INGRESAR USUARIO");
//        ano_actual=eveconn.getInt_ano_actual(conn);
        color_formulario();
        habilitar_evento_menu_bloquear();
    }

    void color_formulario() {
        panel_insert.setBackground(clacolor.getColor_insertar_primario());
    }

    private boolean validar_ingreso() {
        if (evejtf.getBoo_JTextField_vacio(txtusuario, "DEBE CARGAR UN USUARIO")) {
            return false;
        }
        if (evejtf.getBoo_JPasswordField_vacio(jPassword, "DEBE CARGAR UNA SENHA")) {
            return false;
        }
        return true;
    }

    void buscar_usuario() {
        if (dao_usu.getBoolean_buscar_usuario_existente(conn, ENTusu)) {
            JOptionPane.showMessageDialog(this, "BIENVENIDO\n" + ENTusu.getGlobal_nombre());
            habilitar_evento_menu();
            if(var.getPsCrea_backup().equals("SI")){
                evetbl.abrir_TablaJinternal(new FrmCrearBackup());
            }
            this.dispose();
//            if (bdao.getBoolean_backup_creado_hoy(conn)) {
//                evetbl.abrir_TablaJinternal(new FrmCrearBackup());
//            }
//            int idcaja_cierre = (eveconn.getInt_ultimoID_max(conn, cjcie.getTb_caja_cierre(), cjcie.getId_idcaja_cierre()));
//            if (idcaja_cierre > 0) {
//                cjcie_dao.cargar_caja_cierre(conn, cjcie, idcaja_cierre);
//                dao_pro.actualizar_inventario_abc(conn);
//                if (cjcie.getC4estado().equals("CERRADO")) {
//                    JOptionPane.showMessageDialog(null, "NO HAY CAJA ABIERTA SE DEBE ABRIR UNO NUEVO");
//                    evetbl.abrir_TablaJinternal(new FrmCaja_Abrir());
//                }
//            }
        } else {
            txtusuario.setText(null);
            jPassword.setText(null);
            txtusuario.grabFocus();
        }
    }

    void boton_entrar() {
        if (validar_ingreso()) {
            ENTusu.setC2usuario(txtusuario.getText());
            String senha = String.valueOf(jPassword.getPassword());
            ENTusu.setC3senha(senha);
            buscar_usuario();
        }
    }
    void habilitar_evento_menu_bloquear() {
        FrmMenuDespacho.btnliquidacion_proforma.setEnabled(false);
        FrmMenuDespacho.btntercero.setEnabled(false);
        FrmMenuDespacho.btnliquidacion.setEnabled(false);
        FrmMenuDespacho.btngasto.setEnabled(false);
        FrmMenuDespacho.btnvale.setEnabled(false);
//        FrmMenuDespacho.jMenu_proforma.setEnabled(false);
        FrmMenuDespacho.jMenu_tercero.setEnabled(false);
        FrmMenuDespacho.jMenu_configuracion.setEnabled(false);
        FrmMenuDespacho.jMenu_liquidacion.setEnabled(false);
        FrmMenuDespacho.jMenu_gasto.setEnabled(false);
        FrmMenuDespacho.jMenu_informe.setEnabled(false);
        FrmMenuDespacho.jMenu_vale.setEnabled(false);
    }
    void habilitar_evento_menu() {
        FrmMenuDespacho.btnliquidacion_proforma.setEnabled(dao_usu.getBoolean_hab_evento(conn, "1"));
        FrmMenuDespacho.btntercero.setEnabled(dao_usu.getBoolean_hab_evento(conn,"2"));
//        FrmMenuDespacho.jMenu_proforma.setEnabled(dao_usu.getBoolean_hab_evento(conn,"3"));
        FrmMenuDespacho.jMenu_tercero.setEnabled(dao_usu.getBoolean_hab_evento(conn,"4"));
        FrmMenuDespacho.jMenu_configuracion.setEnabled(dao_usu.getBoolean_hab_evento(conn,"5"));
        FrmMenuDespacho.jMenu_liquidacion.setEnabled(dao_usu.getBoolean_hab_evento(conn,"1"));
        FrmMenuDespacho.jMenu_gasto.setEnabled(dao_usu.getBoolean_hab_evento(conn,"1"));
        FrmMenuDespacho.jMenu_informe.setEnabled(dao_usu.getBoolean_hab_evento(conn,"1"));
        FrmMenuDespacho.jMenu_vale.setEnabled(dao_usu.getBoolean_hab_evento(conn,"1"));
//        FrmMenuDespacho.jMenuItem_nueva_proforma.setEnabled(dao_usu.getBoolean_hab_evento(conn,"6"));
        FrmMenuDespacho.jMenuItem_tipo_comprobante.setEnabled(dao_usu.getBoolean_hab_evento(conn,"7"));
//        FrmMenuDespacho.jMenuItem_tipo_mercaderia.setEnabled(dao_usu.getBoolean_hab_evento(conn,"8"));
        FrmMenuDespacho.jMenuItem_local_aduana.setEnabled(dao_usu.getBoolean_hab_evento(conn,"9"));
        FrmMenuDespacho.jMenuItem_nuevo_tercero.setEnabled(dao_usu.getBoolean_hab_evento(conn,"10"));
        FrmMenuDespacho.jMenuItem_tipo_registro.setEnabled(dao_usu.getBoolean_hab_evento(conn,"11"));
        FrmMenuDespacho.jMenuItem_tipo_dependencia.setEnabled(dao_usu.getBoolean_hab_evento(conn,"12"));
        FrmMenuDespacho.jMenuItem_tipo_institucion.setEnabled(dao_usu.getBoolean_hab_evento(conn,"13"));
        FrmMenuDespacho.jMenuItem_moneda_cambio.setEnabled(dao_usu.getBoolean_hab_evento(conn,"14"));
        FrmMenuDespacho.jMenuItem_honorario_despacho.setEnabled(dao_usu.getBoolean_hab_evento(conn,"15"));
        FrmMenuDespacho.jMenu_usuario.setEnabled(dao_usu.getBoolean_hab_evento(conn,"16"));
        FrmMenuDespacho.jMenuItem_crear_usuario.setEnabled(dao_usu.getBoolean_hab_evento(conn,"17"));
        FrmMenuDespacho.jMenuItem_rol_usuario.setEnabled(dao_usu.getBoolean_hab_evento(conn,"18"));
        FrmMenuDespacho.jMenuItem_evento_rol_usuario.setEnabled(dao_usu.getBoolean_hab_evento(conn,"19"));
        FrmMenuDespacho.jMenuItem_usuario_formulario.setEnabled(dao_usu.getBoolean_hab_evento(conn,"20"));
        FrmMenuDespacho.jMenuItem_usuario_tipo_evento.setEnabled(dao_usu.getBoolean_hab_evento(conn,"21"));
        
        FrmMenuDespacho.btnliquidacion.setEnabled(dao_usu.getBoolean_hab_evento(conn, "1"));
        FrmMenuDespacho.btngasto.setEnabled(dao_usu.getBoolean_hab_evento(conn, "1"));
        FrmMenuDespacho.btnvale.setEnabled(dao_usu.getBoolean_hab_evento(conn, "1"));
    }

    public JDiaLogin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        abrir_formulario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_insert = new javax.swing.JPanel();
        lblimagen = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtusuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPassword = new javax.swing.JPasswordField();
        btnentrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(102, 204, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panel_insert.setBackground(new java.awt.Color(153, 204, 255));
        panel_insert.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO DE USUARIO"));

        lblimagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblimagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/MENU/LOGO.png"))); // NOI18N
        lblimagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("USUARIO:");

        txtusuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtusuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtusuarioKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("SEÑA:");

        jPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordKeyPressed(evt);
            }
        });

        btnentrar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnentrar.setText("ENTRAR");
        btnentrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnentrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertLayout = new javax.swing.GroupLayout(panel_insert);
        panel_insert.setLayout(panel_insertLayout);
        panel_insertLayout.setHorizontalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertLayout.createSequentialGroup()
                        .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblimagen)
                            .addGroup(panel_insertLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPassword)
                                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnentrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_insertLayout.setVerticalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addComponent(lblimagen, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnentrar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnentrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnentrarActionPerformed
        // TODO add your handling code here:
        boton_entrar();
    }//GEN-LAST:event_btnentrarActionPerformed

    private void txtusuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusuarioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jPassword.grabFocus();
        }
    }//GEN-LAST:event_txtusuarioKeyPressed

    private void jPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_entrar();
        }
    }//GEN-LAST:event_jPasswordKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if(evemsj.MensajeGeneral_question("DESEAS SALIR DEL SISTEMA","SALIR", "ACEPTAR", "CANCELAR")){
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDiaLogin dialog = new JDiaLogin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnentrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JLabel lblimagen;
    private javax.swing.JPanel panel_insert;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}

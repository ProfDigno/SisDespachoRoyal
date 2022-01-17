/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.LOCAL.VariablesBD;
import Config_JSON.json_config;
import Config_JSON.json_imprimir_pos;
import Evento.Color.cla_color_palete;
import Evento.Fecha.EvenFecha;
import Evento.Jframe.EvenJFRAME;
import REPORTE.PRODUCTO.cla_InvPro_compra_todos;
import java.awt.Color;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmMenuDespacho extends javax.swing.JFrame {

    Connection conn = null;
    ConnPostgres conPs = new ConnPostgres();
    VariablesBD var = new VariablesBD();
    json_config config = new json_config();
    EvenJFRAME evetbl = new EvenJFRAME();
    EvenFecha evefec = new EvenFecha();
    cla_color_palete clacolor = new cla_color_palete();
    EvenConexion eveconn = new EvenConexion();
//    dao_usuario dao_usu=new dao_usuario();
    cla_InvPro_compra_todos inv_com = new cla_InvPro_compra_todos();
    json_imprimir_pos jsprint = new json_imprimir_pos();

    void abrir_formulario() {
        config.cargar_jsom_configuracion();
        jsprint.cargar_jsom_imprimir_pos();
        conPs.ConnectDBpostgres(false);
        conn = conPs.getConnPosgres();
        this.setExtendedState(MAXIMIZED_BOTH);
        titulo_sistema();
        cargar_colores();
        actualizacion_version_v1();
    }

    void cargar_colores() {
        clacolor.setCol_btn_exit(new Color(93, 84, 164));
        clacolor.setCol_btn_move(new Color(42, 61, 102));
        clacolor.setCol_btn_move_error(new Color(93, 84, 164));
        clacolor.setCol_btn_move_ok(Color.magenta);
        clacolor.setCol_panel_dato(new Color(154, 179, 245));
        clacolor.setCol_panel_tabla(new Color(163, 216, 244));
        clacolor.setColor_referencia(new Color(102, 153, 255));//[102,153,255]
        clacolor.setColor_insertar_primario(new Color(217, 228, 221));
        clacolor.setColor_insertar_secundario(new Color(205, 201, 195));
        clacolor.setColor_importacion(new Color(255, 253, 162));
        clacolor.setColor_exportacion(new Color(163, 218, 141));
    }

    void titulo_sistema() {
        String titulo = " BD: " + var.getPsLocalhost() + "/" + var.getPsPort() + "/" + var.getPsNomBD()
                + "/ Nombre:" + config.getNombre_sistema() + "/ Version:" + config.getVersion();
        this.setTitle(titulo);

    }

    void actualizacion_version_v1() {
        /**
         * ALTER TABLE cliente ADD COLUMN delivery boolean; update cliente set
         * delivery=true; update gastos set fk_idusuario=7 where fk_idusuario=1;
         * alter table itemventacomanda alter column preciocompra type
         * numeric(14,0) using preciocompra::numeric;
         */
        String sql = "DO $$ \n"
                + "    BEGIN\n"
                + "        BEGIN\n"
                + "CREATE TABLE \"liquidacion_final\" (\n"
                + "	\"idliquidacion_final\" INTEGER NOT NULL ,\n"
                + "	\"fecha_creado\" TIMESTAMP NOT NULL ,\n"
                + "	\"creado_por\" TEXT NOT NULL ,\n"
                + "	\"fecha_despacho\" DATE NOT NULL ,\n"
                + "	\"despacho_numero\" TEXT NOT NULL ,\n"
                + "	\"tipo_liquidacion\" TEXT NOT NULL ,\n"
                + "	\"estado\" TEXT NOT NULL ,\n"
                + "	\"observacion\" TEXT NOT NULL ,\n"
                + "	\"contenedor_nro\" TEXT NOT NULL ,\n"
                + "	\"contenedor_tipo\" TEXT NOT NULL ,\n"
                + "	\"via_transporte\" TEXT NOT NULL ,\n"
                + "	\"transporte_condicion\" TEXT NOT NULL ,\n"
                + "	\"monto_imponible\" NUMERIC(20,0) NOT NULL ,\n"
                + "	\"monto_ajuste_incluir\" NUMERIC(20,0) NOT NULL ,\n"
                + "	\"monto_factura\" NUMERIC(20,2) NOT NULL ,\n"
                + "	\"monto_flete\" NUMERIC(15,2) NOT NULL ,\n"
                + "	\"monto_seguro\" NUMERIC(15,2) NOT NULL ,\n"
                + "	\"monto_cif\" NUMERIC(20,2) NOT NULL ,\n"
                + "	\"monto_total_despacho\" NUMERIC(20,0) NOT NULL ,\n"
                + "	\"monto_adelanto\" NUMERIC(20,0) NOT NULL ,\n"
                + "	\"monto_pagar\" NUMERIC(20,0) NOT NULL ,\n"
                + "	\"tasa_cambio_aduana\" NUMERIC(10,2) NOT NULL ,\n"
                + "	\"tasa_cambio_mercado\" NUMERIC(10,2) NOT NULL ,\n"
                + "	\"tipo_tasa_cambio\" TEXT NOT NULL ,\n"
                + "	\"factura_numero\" TEXT NOT NULL ,\n"
                + "	\"monto_letra\" TEXT NOT NULL ,\n"
                + "	\"fk_idtipo_comprobante\" INTEGER NOT NULL ,\n"
                + "	\"fk_idtercero_ciudad\" INTEGER NOT NULL ,\n"
                + "	\"fk_idaduana\" INTEGER NOT NULL ,\n"
                + "	\"fk_iddespacho_zona\" INTEGER NOT NULL ,\n"
                + "	\"fk_idtransporte_empresa\" INTEGER NOT NULL ,\n"
                + "	\"fk_idtercero_importador\" INTEGER NOT NULL ,\n"
                + "	\"fk_idtercero_transportadora\" INTEGER NOT NULL ,\n"
                + "	\"fk_idmoneda_cambio\" INTEGER NOT NULL ,\n"
                + "	\"fk_idregimen\" INTEGER NOT NULL ,\n"
                + "	\"fk_idincoterms\" INTEGER NOT NULL ,\n"
                + "	PRIMARY KEY(\"idliquidacion_final\")\n"
                + ");\n"
                + "CREATE TABLE \"transporte_empresa\" (\n"
                + "	\"idtransporte_empresa\" INTEGER NOT NULL ,\n"
                + "	\"nombre\" TEXT NOT NULL ,\n"
                + "	\"sigla\" TEXT NOT NULL ,\n"
                + "	\"direccion\" TEXT NOT NULL ,\n"
                + "	\"telefono\" TEXT NOT NULL ,\n"
                + "	PRIMARY KEY(\"idtransporte_empresa\")\n"
                + ");\n"
                + "CREATE TABLE \"despacho_zona\" (\n"
                + "	\"iddespacho_zona\" INTEGER NOT NULL ,\n"
                + "	\"nombre\" TEXT NOT NULL ,\n"
                + "	PRIMARY KEY(\"iddespacho_zona\")\n"
                + ");\n"
                + "CREATE TABLE \"regimen\" (\n"
                + "	\"idregimen\" INTEGER NOT NULL ,\n"
                + "	\"nombre\" TEXT NOT NULL ,\n"
                + "	\"sigla\" TEXT NOT NULL ,\n"
                + "	\"descripcion\" TEXT NOT NULL ,\n"
                + "	PRIMARY KEY(\"idregimen\")\n"
                + ");\n"
                + "CREATE TABLE \"incoterms\" (\n"
                + "	\"idincoterms\" INTEGER NOT NULL ,\n"
                + "	\"nombre\" TEXT NOT NULL ,\n"
                + "	\"sigla\" TEXT NOT NULL ,\n"
                + "	\"descripcion\" TEXT NOT NULL ,\n"
                + "	PRIMARY KEY(\"idincoterms\")\n"
                + ");\n"
                + "CREATE TABLE \"item_liquidacion_final\" (\n"
                + "	\"iditem_liquidacion_final\" INTEGER NOT NULL ,\n"
                + "	\"fecha_creado\" TIMESTAMP NOT NULL ,\n"
                + "	\"creado_por\" TEXT NOT NULL ,\n"
                + "	\"orden\" INTEGER NOT NULL ,\n"
                + "	\"descripcion\" TEXT NOT NULL ,\n"
                + "	\"comprobante_nro\" TEXT NOT NULL ,\n"
                + "	\"total_guarani\" NUMERIC(20,0) NOT NULL ,\n"
                + "	\"desglose\" NUMERIC(20,0) NOT NULL ,\n"
                + "	\"descriminacion_iva\" NUMERIC(20,0) NOT NULL ,\n"
                + "	\"fk_idliquidacion_final\" INTEGER NOT NULL ,\n"
                + "	\"fk_idtipo_comprobante\" INTEGER NOT NULL ,\n"
                + "	PRIMARY KEY(\"iditem_liquidacion_final\")\n"
                + ");\n"
                //                + " ALTER TABLE producto ADD COLUMN alquilado boolean; \n"
                //                + " update producto set alquilado=false;\n"
                //                + " ALTER TABLE compra ADD COLUMN alquilado boolean; \n"
                //                + " update compra set alquilado=false;\n"

                + "        EXCEPTION\n"
                + "            WHEN duplicate_column THEN RAISE NOTICE 'duplicate_column.';\n"
                + "        END;\n"
                + "    END;\n"
                + "$$ ";
        eveconn.SQL_execute_libre(conn, sql);
    }

    public FrmMenuDespacho() {
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

        escritorio = new javax.swing.JDesktopPane();
        btnliquidacion_proforma = new javax.swing.JButton();
        btntercero = new javax.swing.JButton();
        btnliquidacion = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu_proforma = new javax.swing.JMenu();
        jMenuItem_nueva_proforma = new javax.swing.JMenuItem();
        jMenuItem_tipo_comprobante = new javax.swing.JMenuItem();
        jMenuItem_tipo_mercaderia = new javax.swing.JMenuItem();
        jMenuItem_local_aduana = new javax.swing.JMenuItem();
        jMenu_tercero = new javax.swing.JMenu();
        jMenuItem_nuevo_tercero = new javax.swing.JMenuItem();
        jMenuItem_pais = new javax.swing.JMenuItem();
        jMenuItem_ciudad = new javax.swing.JMenuItem();
        jMenuItem_tipo_registro = new javax.swing.JMenuItem();
        jMenuItem_tipo_dependencia = new javax.swing.JMenuItem();
        jMenuItem_tipo_institucion = new javax.swing.JMenuItem();
        jMenu_configuracion = new javax.swing.JMenu();
        jMenuItem_moneda_cambio = new javax.swing.JMenuItem();
        jMenuItem_honorario_despacho = new javax.swing.JMenuItem();
        jMenu_usuario = new javax.swing.JMenu();
        jMenuItem_crear_usuario = new javax.swing.JMenuItem();
        jMenuItem_rol_usuario = new javax.swing.JMenuItem();
        jMenuItem_evento_rol_usuario = new javax.swing.JMenuItem();
        jMenuItem_usuario_formulario = new javax.swing.JMenuItem();
        jMenuItem_usuario_tipo_evento = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btnliquidacion_proforma.setText("PROFORMA");
        btnliquidacion_proforma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnliquidacion_proformaActionPerformed(evt);
            }
        });

        btntercero.setText("TERCERO");
        btntercero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnterceroActionPerformed(evt);
            }
        });

        btnliquidacion.setText("LIQUIDACION");
        btnliquidacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnliquidacionActionPerformed(evt);
            }
        });

        escritorio.setLayer(btnliquidacion_proforma, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btntercero, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnliquidacion, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnliquidacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnliquidacion_proforma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btntercero, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(692, Short.MAX_VALUE))
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnliquidacion_proforma, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                    .addComponent(btntercero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnliquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(499, Short.MAX_VALUE))
        );

        jMenu_proforma.setText("PROFORMA");

        jMenuItem_nueva_proforma.setText("NUEVA PROFORMA");
        jMenuItem_nueva_proforma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_nueva_proformaActionPerformed(evt);
            }
        });
        jMenu_proforma.add(jMenuItem_nueva_proforma);

        jMenuItem_tipo_comprobante.setText("TIPO COMPROBANTE");
        jMenuItem_tipo_comprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_tipo_comprobanteActionPerformed(evt);
            }
        });
        jMenu_proforma.add(jMenuItem_tipo_comprobante);

        jMenuItem_tipo_mercaderia.setText("TIPO DE MERCADERIA");
        jMenuItem_tipo_mercaderia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_tipo_mercaderiaActionPerformed(evt);
            }
        });
        jMenu_proforma.add(jMenuItem_tipo_mercaderia);

        jMenuItem_local_aduana.setText("LOCAL ADUANA");
        jMenuItem_local_aduana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_local_aduanaActionPerformed(evt);
            }
        });
        jMenu_proforma.add(jMenuItem_local_aduana);

        jMenuBar1.add(jMenu_proforma);

        jMenu_tercero.setText("TERCERO");

        jMenuItem_nuevo_tercero.setText("NUEVO TERCERO");
        jMenuItem_nuevo_tercero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_nuevo_terceroActionPerformed(evt);
            }
        });
        jMenu_tercero.add(jMenuItem_nuevo_tercero);

        jMenuItem_pais.setText("PAIS");
        jMenuItem_pais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_paisActionPerformed(evt);
            }
        });
        jMenu_tercero.add(jMenuItem_pais);

        jMenuItem_ciudad.setText("CIUDAD");
        jMenuItem_ciudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ciudadActionPerformed(evt);
            }
        });
        jMenu_tercero.add(jMenuItem_ciudad);

        jMenuItem_tipo_registro.setText("TIPO REGISTRO");
        jMenuItem_tipo_registro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_tipo_registroActionPerformed(evt);
            }
        });
        jMenu_tercero.add(jMenuItem_tipo_registro);

        jMenuItem_tipo_dependencia.setText("TIPO DEPENDENCIA");
        jMenuItem_tipo_dependencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_tipo_dependenciaActionPerformed(evt);
            }
        });
        jMenu_tercero.add(jMenuItem_tipo_dependencia);

        jMenuItem_tipo_institucion.setText("TIPO INSTITUCION");
        jMenuItem_tipo_institucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_tipo_institucionActionPerformed(evt);
            }
        });
        jMenu_tercero.add(jMenuItem_tipo_institucion);

        jMenuBar1.add(jMenu_tercero);

        jMenu_configuracion.setText("CONFIGURACION");

        jMenuItem_moneda_cambio.setText("MONEDA DE CAMBIO");
        jMenuItem_moneda_cambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_moneda_cambioActionPerformed(evt);
            }
        });
        jMenu_configuracion.add(jMenuItem_moneda_cambio);

        jMenuItem_honorario_despacho.setText("HONORARIO DESPACHO");
        jMenuItem_honorario_despacho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_honorario_despachoActionPerformed(evt);
            }
        });
        jMenu_configuracion.add(jMenuItem_honorario_despacho);

        jMenu_usuario.setText("USUARIO");

        jMenuItem_crear_usuario.setText("CREAR USUARIO");
        jMenuItem_crear_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_crear_usuarioActionPerformed(evt);
            }
        });
        jMenu_usuario.add(jMenuItem_crear_usuario);

        jMenuItem_rol_usuario.setText("ROL USUARIO");
        jMenuItem_rol_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_rol_usuarioActionPerformed(evt);
            }
        });
        jMenu_usuario.add(jMenuItem_rol_usuario);

        jMenuItem_evento_rol_usuario.setText("EVENTO ROL USUARIO");
        jMenuItem_evento_rol_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_evento_rol_usuarioActionPerformed(evt);
            }
        });
        jMenu_usuario.add(jMenuItem_evento_rol_usuario);

        jMenuItem_usuario_formulario.setText("USUARIO FORMULARIO");
        jMenuItem_usuario_formulario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_usuario_formularioActionPerformed(evt);
            }
        });
        jMenu_usuario.add(jMenuItem_usuario_formulario);

        jMenuItem_usuario_tipo_evento.setText("USUARIO TIPO EVENTO");
        jMenuItem_usuario_tipo_evento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_usuario_tipo_eventoActionPerformed(evt);
            }
        });
        jMenu_usuario.add(jMenuItem_usuario_tipo_evento);

        jMenu_configuracion.add(jMenu_usuario);

        jMenuItem1.setText("ENCOMIENDA");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu_configuracion.add(jMenuItem1);

        jMenuBar1.add(jMenu_configuracion);

        jMenu1.setText("LIQUIDACION");

        jMenuItem6.setText("LIQUIDACION FINAL");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem2.setText("REGIMEN");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("INCOTERMS");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("TRANSPORTE EMPRESA");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("DESPACHO ZONA");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        JDiaLogin log = new JDiaLogin(this, true);
        log.setVisible(true);
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem_tipo_registroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_tipo_registroActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmTipo_Registro());
    }//GEN-LAST:event_jMenuItem_tipo_registroActionPerformed

    private void jMenuItem_tipo_dependenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_tipo_dependenciaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmTipo_dependencia());
    }//GEN-LAST:event_jMenuItem_tipo_dependenciaActionPerformed

    private void jMenuItem_tipo_institucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_tipo_institucionActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmTipo_institucion());
    }//GEN-LAST:event_jMenuItem_tipo_institucionActionPerformed

    private void jMenuItem_ciudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ciudadActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmTercero_ciudad());
    }//GEN-LAST:event_jMenuItem_ciudadActionPerformed

    private void jMenuItem_tipo_mercaderiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_tipo_mercaderiaActionPerformed
        // TODO add your handling code here:
//        evetbl.abrir_TablaJinternal(new FrmMercaderia());
    }//GEN-LAST:event_jMenuItem_tipo_mercaderiaActionPerformed

    private void jMenuItem_local_aduanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_local_aduanaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmAduana());
    }//GEN-LAST:event_jMenuItem_local_aduanaActionPerformed

    private void jMenuItem_tipo_comprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_tipo_comprobanteActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmTipo_comprobante());
    }//GEN-LAST:event_jMenuItem_tipo_comprobanteActionPerformed

    private void jMenuItem_nuevo_terceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_nuevo_terceroActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmTercero());
    }//GEN-LAST:event_jMenuItem_nuevo_terceroActionPerformed

    private void jMenuItem_paisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_paisActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmTercero_pais());
    }//GEN-LAST:event_jMenuItem_paisActionPerformed

    private void jMenuItem_nueva_proformaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_nueva_proformaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmLiquidacion_proforma());
    }//GEN-LAST:event_jMenuItem_nueva_proformaActionPerformed

    private void jMenuItem_moneda_cambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_moneda_cambioActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmMoneda_cambio());
    }//GEN-LAST:event_jMenuItem_moneda_cambioActionPerformed

    private void jMenuItem_honorario_despachoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_honorario_despachoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHonorario_despacho());
    }//GEN-LAST:event_jMenuItem_honorario_despachoActionPerformed

    private void btnliquidacion_proformaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnliquidacion_proformaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmLiquidacion_proforma());
    }//GEN-LAST:event_btnliquidacion_proformaActionPerformed

    private void jMenuItem_crear_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_crear_usuarioActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario());
    }//GEN-LAST:event_jMenuItem_crear_usuarioActionPerformed

    private void jMenuItem_rol_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_rol_usuarioActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario_rol());
    }//GEN-LAST:event_jMenuItem_rol_usuarioActionPerformed

    private void jMenuItem_evento_rol_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_evento_rol_usuarioActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario_evento());
    }//GEN-LAST:event_jMenuItem_evento_rol_usuarioActionPerformed

    private void jMenuItem_usuario_formularioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_usuario_formularioActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario_formulario());
    }//GEN-LAST:event_jMenuItem_usuario_formularioActionPerformed

    private void jMenuItem_usuario_tipo_eventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_usuario_tipo_eventoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmUsuario_tipo_evento());
    }//GEN-LAST:event_jMenuItem_usuario_tipo_eventoActionPerformed

    private void btnterceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnterceroActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmTercero());
    }//GEN-LAST:event_btnterceroActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmEncomienda());
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRegimen());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmIncoterms());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmTransporte_empresa());
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmDespacho_zona());
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmLiquidacion_final());
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void btnliquidacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnliquidacionActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmLiquidacion_final());
    }//GEN-LAST:event_btnliquidacionActionPerformed

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
            java.util.logging.Logger.getLogger(FrmMenuDespacho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMenuDespacho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMenuDespacho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMenuDespacho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenuDespacho().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnliquidacion;
    public static javax.swing.JButton btnliquidacion_proforma;
    public static javax.swing.JButton btntercero;
    public static javax.swing.JDesktopPane escritorio;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem_ciudad;
    public static javax.swing.JMenuItem jMenuItem_crear_usuario;
    public static javax.swing.JMenuItem jMenuItem_evento_rol_usuario;
    public static javax.swing.JMenuItem jMenuItem_honorario_despacho;
    public static javax.swing.JMenuItem jMenuItem_local_aduana;
    public static javax.swing.JMenuItem jMenuItem_moneda_cambio;
    public static javax.swing.JMenuItem jMenuItem_nueva_proforma;
    public static javax.swing.JMenuItem jMenuItem_nuevo_tercero;
    private javax.swing.JMenuItem jMenuItem_pais;
    public static javax.swing.JMenuItem jMenuItem_rol_usuario;
    public static javax.swing.JMenuItem jMenuItem_tipo_comprobante;
    public static javax.swing.JMenuItem jMenuItem_tipo_dependencia;
    public static javax.swing.JMenuItem jMenuItem_tipo_institucion;
    public static javax.swing.JMenuItem jMenuItem_tipo_mercaderia;
    public static javax.swing.JMenuItem jMenuItem_tipo_registro;
    public static javax.swing.JMenuItem jMenuItem_usuario_formulario;
    public static javax.swing.JMenuItem jMenuItem_usuario_tipo_evento;
    public static javax.swing.JMenu jMenu_configuracion;
    public static javax.swing.JMenu jMenu_proforma;
    public static javax.swing.JMenu jMenu_tercero;
    public static javax.swing.JMenu jMenu_usuario;
    // End of variables declaration//GEN-END:variables
}
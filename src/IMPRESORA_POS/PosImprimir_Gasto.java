/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IMPRESORA_POS;

import BASEDATO.EvenConexion;
import Config_JSON.json_config;
import Evento.Mensaje.EvenMensajeJoptionpane;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.print.PrintException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Digno
 */
public class PosImprimir_Gasto {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private static json_config config = new json_config();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    private static String tk_idgastos = "500";
    private static String tk_fecha_emision = "08-03-2019";
    private static String tk_descripcion = "la descripcion";
    private static String tk_tipogasto = "el tipo de gasto";
    private static String tk_monto = "15.000";
    private static String tk_usuario = "digno";
    private static String tk_nombre_empresa = "";
    private static String tk_ruta_archivo = "ticket_gasto.txt";
    private static FileInputStream inputStream = null;
    private static String nombre_ticket = "-GASTO";
    private void cargar_datos_gasto(Connection conn, int idgasto) {
        String titulo = "cargar_datos_gasto";
        String sql = "select g.idgasto as idg,(tg.nombre) as descripcion,\n"
                + "g.descripcion as des_gasto,\n"
                + "to_char(g.fecha_emision,'yyyy-MM-dd HH:MI') as fecha, \n"
                + "g.estado as estado,g.monto_gasto as monto,\n"
                + "TRIM(to_char(g.monto_gasto,'999G999G999')) as monto_pos,\n"
                + "us.nombre as usuario \n"
                + "from gasto g, gasto_tipo tg,usuario us   \n"
                + "where g.fk_idgasto_tipo=tg.idgasto_tipo  \n"
                + "and g.fk_idusuario=us.idusuario \n"
                + "and g.idgasto=" + idgasto;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                tk_idgastos = rs.getString("idg");
                tk_fecha_emision = rs.getString("fecha");
                tk_descripcion = rs.getString("des_gasto");
                tk_tipogasto = rs.getString("descripcion");
                tk_monto = rs.getString("monto_pos");
                tk_usuario = rs.getString("usuario");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private String cargar_datos_para_mensaje_textarea() {
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "\t";
        mensaje_impresora = mensaje_impresora + "=======" + "" + nombre_ticket+"========" + saltolinea;
        mensaje_impresora = mensaje_impresora + "CODIGO:" + tk_idgastos + saltolinea;
        mensaje_impresora = mensaje_impresora + "FECHA: " + tk_fecha_emision + saltolinea;
        mensaje_impresora = mensaje_impresora + "USUARIO: " + tk_usuario + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "DESCRIPCION: " + tk_descripcion + saltolinea;
        mensaje_impresora = mensaje_impresora + "TIPO: " + tk_tipogasto + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL :" + tk_monto + saltolinea;
        return mensaje_impresora;
    }

    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = 48;
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = 13;
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, 1, totalColumna, "=============" +"" + nombre_ticket+"=============");
        printer.printTextWrap(2 + tempfila, 2, 1, totalColumna, "CODIGO:" + tk_idgastos);
        printer.printTextWrap(2 + tempfila, 2, 20, totalColumna, "FECHA: " + tk_fecha_emision);
        printer.printTextWrap(3 + tempfila, 3, 1, totalColumna, "USUARIO: " + tk_usuario);
        printer.printTextWrap(4 + tempfila, 4, 1, totalColumna, "==========================================");
        printer.printTextWrap(5 + tempfila, 5, 1, 200, "DESCRIPCION: " + tk_descripcion);
        printer.printTextWrap(6 + tempfila, 6, 1, 100, "TIPO: " + tk_tipogasto);
        printer.printTextWrap(7 + tempfila, 7, 1, totalColumna, "==========================================");
        printer.printTextWrap(8 + tempfila, 8, 25, totalColumna, "TOTAL :" + tk_monto);
        printer.toFile(tk_ruta_archivo);
        try {
            inputStream = new FileInputStream(tk_ruta_archivo);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.err.println(ex);
        }
        if (inputStream == null) {
            return;
        }

    }

    void crear_archivo_enviar_impresora() {
        String titulo = "crear_archivo_enviar_impresora";
        try {
            crear_archivo_texto_impresion();
            pos.setInputStream(inputStream);
            pos.imprimir_ticket_Pos();
        } catch (Exception e) {
            evemen.mensaje_error(e, titulo);
        }
    }

    private void crear_mensaje_textarea_y_confirmar() {
        JTextArea ta = new JTextArea(20, 30);
        ta.setText(cargar_datos_para_mensaje_textarea());
        System.out.println(cargar_datos_para_mensaje_textarea());
        Object[] opciones = {"IMPRIMIR", "CANCELAR"};
        int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(ta), nombre_ticket,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "IMPRIMIR");
        if (eleccion == JOptionPane.YES_OPTION) {
            crear_archivo_enviar_impresora();
        }
    }

    public void boton_imprimir_pos_GASTO(Connection conn, int idgasto) {
        cargar_datos_gasto(conn, idgasto);
        crear_mensaje_textarea_y_confirmar();
    }
}

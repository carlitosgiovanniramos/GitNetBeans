/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class ReporteFrame extends javax.swing.JFrame {

  
    public ReporteFrame() {
        initComponents();
        cargarColumnas();
    }

    DefaultTableModel modelo; 
    public void cargarColumnas() {
    String[] columnas = {"Fecha", "E. Mañana", "S. Mañana", "E. Tarde", "S. Tarde", "Atraso", "Horas", "Desc."};
    modelo = new DefaultTableModel(null, columnas);
    tblReporte.setModel(modelo); 
}
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtAnio = new javax.swing.JTextField();
        btnConsultar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnExportar = new javax.swing.JButton();
        lblSueldoFijo = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        lblDescuentoTotal = new javax.swing.JLabel();
        txtMes = new javax.swing.JTextField();
        lblSueldoPagar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReporte = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(146, 53, 53));

        txtAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnioActionPerformed(evt);
            }
        });

        btnConsultar.setText("CONSULTA");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CEDULA");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MES");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("AÑO");

        btnExportar.setText("REPORTE");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        lblSueldoFijo.setBackground(new java.awt.Color(153, 0, 51));

        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCedula)
                            .addComponent(txtMes)
                            .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSueldoFijo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(lblDescuentoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSueldoPagar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(144, 144, 144))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnExportar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnConsultar))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSueldoPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDescuentoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSueldoFijo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 752, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        tblReporte.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tblReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblReporte);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnioActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
   try {
        String cedula = txtCedula.getText().trim();
        
        if (!cedula.matches("\\d{10}")) {
            javax.swing.JOptionPane.showMessageDialog(this, "La cédula debe tener exactamente 10 números.", "Error", javax.swing.JOptionPane.WARNING_MESSAGE);
            return; 
        }

        int mes = Integer.parseInt(txtMes.getText().trim());
        int anio = Integer.parseInt(txtAnio.getText().trim());

        if (mes < 1 || mes > 12) {
            javax.swing.JOptionPane.showMessageDialog(this, "El mes debe estar entre 1 y 12.", "Error", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (anio < 2000 || anio > 2100) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor ingrese un año válido.", "Error", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

       
        controlador.ReporteController control = new controlador.ReporteController();
        modelo.ReporteTiempoCompleto data = control.generarReporteTiempoCompleto(cedula, mes, anio);

        // si el mes está vacío
        if (data.getAsistencias().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "El empleado existe, pero no tiene asistencias registradas en " + mes + "/" + anio + ".", "Sin datos", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            modelo.setRowCount(0); // Vaciamos la tabla
            lblSueldoFijo.setText("Sueldo fijo: $1500.00");
            lblDescuentoTotal.setText("Descuento total: $0.00");
            lblSueldoPagar.setText("Sueldo a pagar: $1500.00");
            return; // Corta la ejecución para no llenar la tabla de gana
        }

        //Limpia y cargamos las filas si todo está bien
        modelo.setRowCount(0);
        String[] registros = new String[8]; 
        
        for (modelo.Asistencia asis : data.getAsistencias()) {
            registros[0] = asis.getFecha().toString();
            registros[1] = (asis.getHoraEntradaManana() != null) ? asis.getHoraEntradaManana().toString() : "--:--";
            registros[2] = (asis.getHoraSalidaManana() != null) ? asis.getHoraSalidaManana().toString() : "--:--";
            registros[3] = (asis.getHoraEntradaTarde() != null) ? asis.getHoraEntradaTarde().toString() : "--:--";
            registros[4] = (asis.getHoraSalidaTarde() != null) ? asis.getHoraSalidaTarde().toString() : "--:--";
            registros[5] = String.valueOf(asis.getMinutosAtraso());
            registros[6] = String.valueOf(asis.getHorasTrabajadas());
            registros[7] = String.format("%.2f", asis.getDescuento());
            
            modelo.addRow(registros);
        }

        // Llenamos los Labels
        lblSueldoFijo.setText("Sueldo fijo: $1500.00");
        lblDescuentoTotal.setText(String.format("Descuento total: $%.2f", data.getDescuentoTotal()));
        lblSueldoPagar.setText(String.format("Sueldo a pagar: $%.2f", data.getSueldoFinal()));

    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Mes y Año deben ser números.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    } catch (IllegalArgumentException e) {
        javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Atención", javax.swing.JOptionPane.WARNING_MESSAGE);
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error crítico: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnConsultarActionPerformed
    
    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
    try (java.sql.Connection con = new conexion.Conexion().conectar()) {
        
        java.util.Map<String, Object> parametros = new java.util.HashMap<>();
        parametros.put("cedula", txtCedula.getText().trim());
        parametros.put("mes", Integer.parseInt(txtMes.getText().trim()));
        parametros.put("anio", Integer.parseInt(txtAnio.getText().trim()));

        String ruta = "src/main/java/reportes/reporteCompleto.jasper";
        net.sf.jasperreports.engine.JasperPrint jp = net.sf.jasperreports.engine.JasperFillManager.fillReport(ruta, parametros, con);
        
        // Evitamos que cierre todo el sistema al cerrar el PDF
        net.sf.jasperreports.view.JasperViewer jv = new net.sf.jasperreports.view.JasperViewer(jp, false);
        jv.setTitle("Reporte Mensual");
        jv.setVisible(true);
        
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Asegúrese de haber consultado primero los datos válidos.\nError: " + e.getMessage(), "Error al generar PDF", javax.swing.JOptionPane.ERROR_MESSAGE);
    }  
    }//GEN-LAST:event_btnExportarActionPerformed

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
            java.util.logging.Logger.getLogger(ReporteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReporteFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescuentoTotal;
    private javax.swing.JLabel lblSueldoFijo;
    private javax.swing.JLabel lblSueldoPagar;
    private javax.swing.JTable tblReporte;
    private javax.swing.JTextField txtAnio;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtMes;
    // End of variables declaration//GEN-END:variables
}

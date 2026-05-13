package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import controlador.AsistenciaController;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import modelo.Asistencia;

public class AsistenciaFrame extends javax.swing.JFrame {

    private int idEmpleado;
    private String nombreEmpleado;
    private AsistenciaController controller;
    private DefaultTableModel modeloTablaAsistencia;
    private Timer relojTimer;

    public AsistenciaFrame() {
        initComponents();
        this.idEmpleado = 0;
        this.nombreEmpleado = "";
        this.controller = new AsistenciaController();

        jlblEmpleado.setText("Sin empleado");
        jlblFecha.setText(LocalDate.now().toString());

        cargarColumnasTablaAsistencia();
        limpiarChecks();
        iniciarReloj();
        aplicarDiseno();
    }

    public AsistenciaFrame(int idEmpleado, String nombreEmpleado) {
        initComponents();

        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.controller = new AsistenciaController();

        jlblEmpleado.setText(nombreEmpleado);
        jlblFecha.setText(LocalDate.now().toString());

        cargarColumnasTablaAsistencia();
        configurarEventos();
        cargarResumen();
        iniciarReloj();
        aplicarDiseno();
    }

    private void iniciarReloj() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

        jlblReloj.setText(LocalTime.now().format(formato));

        relojTimer = new Timer(1000, e -> {
            jlblReloj.setText(LocalTime.now().format(formato));
        });

        relojTimer.start();
    }

    public void cargarColumnasTablaAsistencia() {
        String[] columnas = {
            "Entrada Mañana",
            "Salida Mañana",
            "Entrada Tarde",
            "Salida Tarde",
            "Min. Atraso",
            "Horas trabajadas",
            "Descuento"
        };

        modeloTablaAsistencia = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jtblAsistencia.setModel(modeloTablaAsistencia);
    }

    private void configurarEventos() {
        jchxEntradaManana.addActionListener(e -> registrarEntradaManana());
        jchxSalidaManana.addActionListener(e -> registrarSalidaManana());
        jchxEntradaTarde.addActionListener(e -> registrarEntradaTarde());
        jchxSalidaTarde.addActionListener(e -> registrarSalidaTarde());
    }

    private void registrarEntradaManana() {
        String mensaje = controller.registrarEntradaManana(idEmpleado);
        JOptionPane.showMessageDialog(this, mensaje);
        cargarResumen();
    }

    private void registrarSalidaManana() {
        String mensaje = controller.registrarSalidaManana(idEmpleado);
        JOptionPane.showMessageDialog(this, mensaje);
        cargarResumen();
    }

    private void registrarEntradaTarde() {
        String mensaje = controller.registrarEntradaTarde(idEmpleado);
        JOptionPane.showMessageDialog(this, mensaje);
        cargarResumen();
    }

    private void registrarSalidaTarde() {
        String mensaje = controller.registrarSalidaTarde(idEmpleado);
        JOptionPane.showMessageDialog(this, mensaje);
        cargarResumen();
    }

    private void cargarResumen() {
        modeloTablaAsistencia.setRowCount(0);

        if (idEmpleado <= 0) {
            limpiarChecks();
            return;
        }

        Asistencia a = controller.buscarAsistenciaDelDia(idEmpleado);

        if (a != null) {
            modeloTablaAsistencia.addRow(new Object[]{
                a.getHoraEntradaManana(),
                a.getHoraSalidaManana(),
                a.getHoraEntradaTarde(),
                a.getHoraSalidaTarde(),
                a.getMinutosAtraso(),
                a.getHorasTrabajadas(),
                a.getDescuento()
            });

            actualizarEstadoChecks(a);
        } else {
            limpiarChecks();
        }
    }

    private void limpiarChecks() {
        jchxEntradaManana.setSelected(false);
        jchxSalidaManana.setSelected(false);
        jchxEntradaTarde.setSelected(false);
        jchxSalidaTarde.setSelected(false);

        jchxEntradaManana.setEnabled(idEmpleado > 0);
        jchxSalidaManana.setEnabled(false);
        jchxEntradaTarde.setEnabled(false);
        jchxSalidaTarde.setEnabled(false);
    }

    private void actualizarEstadoChecks(Asistencia a) {
        jchxEntradaManana.setSelected(a.getHoraEntradaManana() != null);
        jchxSalidaManana.setSelected(a.getHoraSalidaManana() != null);
        jchxEntradaTarde.setSelected(a.getHoraEntradaTarde() != null);
        jchxSalidaTarde.setSelected(a.getHoraSalidaTarde() != null);

        jchxEntradaManana.setEnabled(a.getHoraEntradaManana() == null);
        jchxSalidaManana.setEnabled(a.getHoraEntradaManana() != null && a.getHoraSalidaManana() == null);
        jchxEntradaTarde.setEnabled(a.getHoraSalidaManana() != null && a.getHoraEntradaTarde() == null);
        jchxSalidaTarde.setEnabled(a.getHoraEntradaTarde() != null && a.getHoraSalidaTarde() == null);
    }
    
   private void aplicarDiseno() {
    Color fondo = new Color(242, 242, 242);
    Color rojo = new Color(150, 55, 60);
    Color rojoOscuro = new Color(120, 35, 40);
    Color grisHeader = new Color(65, 65, 65);
    Color verdeEntrada = new Color(170, 255, 170);
    Color rojoSalida = new Color(255, 170, 170);

    getContentPane().setBackground(fondo);
    jPanel1.setBackground(fondo);
    jPanel2.setBackground(Color.WHITE);
    jPanel3.setBackground(Color.WHITE);
    jPanel4.setBackground(Color.WHITE);

    jPanel2.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
    jPanel3.setBorder(BorderFactory.createLineBorder(rojo, 2));
    jPanel4.setBorder(BorderFactory.createLineBorder(rojo, 2));

    jLabel1.setForeground(Color.BLACK);

    jLabel4.setForeground(rojoOscuro);
    jLabel9.setForeground(rojoOscuro);
    jLabel14.setForeground(rojoOscuro);

    jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 14));
    jLabel9.setFont(new Font("Segoe UI", Font.BOLD, 14));
    jLabel14.setFont(new Font("Segoe UI", Font.BOLD, 14));

    jLabel15.setForeground(Color.DARK_GRAY);
    jLabel16.setForeground(Color.DARK_GRAY);

    jlblReloj.setOpaque(true);
    jlblReloj.setBackground(rojo);
    jlblReloj.setForeground(Color.WHITE);
    jlblReloj.setHorizontalAlignment(SwingConstants.CENTER);
    jlblReloj.setBorder(BorderFactory.createLineBorder(rojoOscuro, 2));

    jtblAsistencia.setRowHeight(28);
    jtblAsistencia.setGridColor(new Color(220, 220, 220));
    jtblAsistencia.setForeground(Color.BLACK);

    JTableHeader header = jtblAsistencia.getTableHeader();
    header.setReorderingAllowed(false);
    header.setResizingAllowed(true);
    header.setOpaque(true);
    header.setBackground(grisHeader);
    header.setForeground(Color.WHITE);
    header.setFont(new Font("Segoe UI", Font.BOLD, 12));

    DefaultTableCellRenderer renderHeader = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(
                javax.swing.JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            c.setBackground(grisHeader);
            c.setForeground(Color.WHITE);
            c.setFont(new Font("Segoe UI", Font.BOLD, 12));
            setHorizontalAlignment(SwingConstants.CENTER);
            setBorder(BorderFactory.createLineBorder(new Color(20, 20, 20)));
            return c;
        }
    };

    for (int i = 0; i < jtblAsistencia.getColumnModel().getColumnCount(); i++) {
        jtblAsistencia.getColumnModel().getColumn(i).setHeaderRenderer(renderHeader);
    }

    jtblAsistencia.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(
                javax.swing.JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            setHorizontalAlignment(SwingConstants.CENTER);

            if (!isSelected) {
                if (column == 0 || column == 2) {
                    c.setBackground(verdeEntrada);
                } else if (column == 1 || column == 3) {
                    c.setBackground(rojoSalida);
                } else {
                    c.setBackground(Color.WHITE);
                }

                c.setForeground(Color.BLACK);
            }

            return c;
        }
    });

    jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

    jchxEntradaManana.setBackground(Color.WHITE);
    jchxSalidaManana.setBackground(Color.WHITE);
    jchxEntradaTarde.setBackground(Color.WHITE);
    jchxSalidaTarde.setBackground(Color.WHITE);

    jchxEntradaManana.setFocusPainted(false);
    jchxSalidaManana.setFocusPainted(false);
    jchxEntradaTarde.setFocusPainted(false);
    jchxSalidaTarde.setFocusPainted(false);
    jtblAsistencia.getTableHeader().repaint();
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jlblEmpleado = new javax.swing.JLabel();
        jlblFecha = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jchxEntradaManana = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jchxSalidaManana = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jchxEntradaTarde = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jchxSalidaTarde = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblAsistencia = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jlblReloj = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel1.setText("Registrar Asistencia");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlblEmpleado.setText("Empleado");

        jlblFecha.setText("Fecha");

        jLabel2.setText("Empleado:");

        jLabel3.setText("Fecha:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jlblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jlblEmpleado))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jlblFecha))
                .addGap(22, 22, 22))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Entrada:");

        jchxEntradaManana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchxEntradaMananaActionPerformed(evt);
            }
        });

        jLabel6.setText("8:00 am");

        jLabel7.setText("Salida:");

        jLabel8.setText("13:00 pm");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 357, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jchxEntradaManana)
                    .addComponent(jchxSalidaManana))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jchxEntradaManana)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchxSalidaManana)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setText("Jornada de la Mañana");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel9.setText("Jornada de la Tarde");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setText("Entrada:");

        jLabel11.setText("14:00 pm");

        jLabel12.setText("Salida:");

        jLabel13.setText("17:00 pm");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jchxEntradaTarde)
                    .addComponent(jchxSalidaTarde))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jchxEntradaTarde)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchxSalidaTarde)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel14.setText("Resumen del Dia");

        jtblAsistencia.setForeground(new java.awt.Color(102, 102, 102));
        jtblAsistencia.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblAsistencia);

        jLabel15.setText("8:00 am - 13:00 pm");

        jLabel16.setText("14:00 pm - 17:00");

        jlblReloj.setFont(new java.awt.Font("Segoe UI Semibold", 0, 48)); // NOI18N
        jlblReloj.setText("00:00:00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(259, 259, 259))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15))
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 87, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jlblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jchxEntradaMananaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchxEntradaMananaActionPerformed
        
    }//GEN-LAST:event_jchxEntradaMananaActionPerformed

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
            java.util.logging.Logger.getLogger(AsistenciaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AsistenciaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AsistenciaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AsistenciaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AsistenciaFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox jchxEntradaManana;
    private javax.swing.JCheckBox jchxEntradaTarde;
    private javax.swing.JCheckBox jchxSalidaManana;
    private javax.swing.JCheckBox jchxSalidaTarde;
    private javax.swing.JLabel jlblEmpleado;
    private javax.swing.JLabel jlblFecha;
    private javax.swing.JLabel jlblReloj;
    private javax.swing.JTable jtblAsistencia;
    // End of variables declaration//GEN-END:variables
}

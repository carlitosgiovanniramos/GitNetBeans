/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.EmpleadoController;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import modelo.Empleado;
import modelo.EmpleadoTiempoCompleto;
import modelo.EmpleadoTiempoParcial;

/**
 *
 * @author Lenovo LOQ
 */
public class EmpleadoFrame extends javax.swing.JFrame {

    /**
     * Creates new form EmpleadoFrame
     */
    private int idEmpleadoSeleccionado = 0;
    private boolean estadoEmpleadoSeleccionado;
    int xMouse, yMouse;
    DefaultTableModel modeloEmpleados;
    DefaultComboBoxModel modeloComboTipoEmpleado;

    public EmpleadoFrame() {
        this.setUndecorated(true);

        initComponents();

        this.setLocationRelativeTo(null);
        cargarColumnaEmpleados();
        cargarComboTipoEmpleado();
        cargarFilasEmpleados();
        selectRowEmpleado();

        validarIngresoValores();

    }

    public void validarIngresoValores() {
        validarSoloNumeros(jtxtFCedula, 10);
        validarSoloLetras(jtxtFNombres);
        validarSoloLetras(jtxtFApellidos);
        validarSoloNumeros(jtxtFTelefono, 10);
        validarSoloNumeros(jtxtFBuscarCedula, 10);
    }

    public void cargarColumnaEmpleados() {
        String[] columnas = {
            "ID", "Estado", "Cedula", "Nombre", "Apellido",
            "Telefono", "Correo", "Tipo Empleado", "Sueldo Fijo", "Valor Hora"
        };

        modeloEmpleados = new DefaultTableModel(null, columnas);
        jtblEmpleados.setModel(modeloEmpleados);

        ocultarColumnas();
    }

    private void ocultarColumnas() {
        ocultarColumna(0); // ID
        ocultarColumna(1); // Estado
    }

    private void ocultarColumna(int columna) {
        jtblEmpleados.getColumnModel().getColumn(columna).setMinWidth(0);
        jtblEmpleados.getColumnModel().getColumn(columna).setMaxWidth(0);
        jtblEmpleados.getColumnModel().getColumn(columna).setPreferredWidth(0);
        jtblEmpleados.getColumnModel().getColumn(columna).setWidth(0);
    }

    public void cargarFilasEmpleados() {

        modeloEmpleados.setRowCount(0);

        EmpleadoController controller = new EmpleadoController();

        List<Empleado> lista = controller.listarEmpleados();

        for (Empleado empleado : lista) {

            Object[] fila = {
                empleado.getIdEmpleado(),
                empleado.isEstado(),
                empleado.getCedula(),
                empleado.getNombres(),
                empleado.getApellidos(),
                empleado.getTelefono(),
                empleado.getCorreo(),
                empleado.getTipoEmpleado(),
                obtenerSueldoFijo(empleado),
                obtenerValorHora(empleado)
            };

            modeloEmpleados.addRow(fila);
        }
    }

    public void cargarComboTipoEmpleado() {
        String[] tipoEmpleado = {"TIEMPO_COMPLETO", "TIEMPO_PARCIAL"};
        modeloComboTipoEmpleado = new DefaultComboBoxModel(tipoEmpleado);
        jcbxTipoEmpleado.setModel(modeloComboTipoEmpleado);
    }

    public void registrarEmpleado() {

        Empleado empleado = crearEmpleadoDesdeFormulario();

        EmpleadoController controller = new EmpleadoController();

        String respuesta = controller.registrarEmpleado(empleado);

        if (respuesta.equals("REGISTRADO")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Empleado y usuario registrados correctamente."
            );

            cargarFilasEmpleados();
            nuevoEmpleado();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    respuesta,
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void actualizarEmpleado() {

        if (idEmpleadoSeleccionado <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un empleado de la tabla.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Empleado empleado = crearEmpleadoDesdeFormulario();

        empleado.setIdEmpleado(idEmpleadoSeleccionado);

        EmpleadoController controller = new EmpleadoController();

        String respuesta = controller.actualizarEmpleado(empleado);

        if (respuesta.equals("ACTUALIZADO")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Empleado actualizado correctamente."
            );

            cargarFilasEmpleados();
            nuevoEmpleado();

            idEmpleadoSeleccionado = 0;

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    respuesta,
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void eliminarEmpleado() {

        if (idEmpleadoSeleccionado <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un empleado de la tabla.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea eliminar el empleado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            EmpleadoController controller = new EmpleadoController();

            String respuesta = controller.eliminarEmpleado(idEmpleadoSeleccionado);

            if (respuesta.equals("ELIMINADO")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Empleado eliminado correctamente."
                );

                cargarFilasEmpleados();
                nuevoEmpleado();

                idEmpleadoSeleccionado = 0;

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        respuesta,
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    public void buscarEmpleadoPorCedula() {

        String cedula = jtxtFBuscarCedula.getText().trim();

        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula para buscar.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EmpleadoController controller = new EmpleadoController();
        Empleado empleado = controller.buscarEmpleadoPorCedula(cedula);

        if (empleado == null) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún empleado con esa cédula.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        modeloEmpleados.setRowCount(0);

        Object[] fila = {
            empleado.getIdEmpleado(),
            empleado.isEstado(),
            empleado.getCedula(),
            empleado.getNombres(),
            empleado.getApellidos(),
            empleado.getTelefono(),
            empleado.getCorreo(),
            empleado.getTipoEmpleado(),
            obtenerSueldoFijo(empleado),
            obtenerValorHora(empleado)
        };

        modeloEmpleados.addRow(fila);
    }

    public void volverMenuAdministrador() {

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de volver? Se perderán los cambios no guardados.",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            MenuAdminFrame menu = new MenuAdminFrame();
            menu.setVisible(true);
            menu.setLocationRelativeTo(null);
            this.dispose();
        }
    }

    private void validarSoloNumeros(JTextField campo, int limite) {
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();

                if (!Character.isDigit(c) || campo.getText().length() >= limite) {
                    evt.consume();
                }
            }
        });
    }

    private void validarSoloLetras(JTextField campo) {
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();

                if (!Character.isLetter(c) && c != ' ') {
                    evt.consume();
                }
            }
        });
    }

    public void nuevoEmpleado() {

        idEmpleadoSeleccionado = 0;
        estadoEmpleadoSeleccionado = false;

        jtxtFBuscarCedula.setText("");

        jtxtFCedula.setText("");
        jtxtFNombres.setText("");
        jtxtFApellidos.setText("");
        jtxtFTelefono.setText("");
        jtxtFCorreo.setText("");

        jcbxTipoEmpleado.setSelectedIndex(0);

        cargarFilasEmpleados();
    }

    private Empleado crearEmpleadoDesdeFormulario() {
        String tipoEmpleado = jcbxTipoEmpleado.getSelectedItem().toString();
        Empleado empleado;

        if ("TIEMPO_COMPLETO".equals(tipoEmpleado)) {
            empleado = new EmpleadoTiempoCompleto();
        } else {
            empleado = new EmpleadoTiempoParcial();
        }

        empleado.setCedula(jtxtFCedula.getText().trim());
        empleado.setNombres(jtxtFNombres.getText().trim());
        empleado.setApellidos(jtxtFApellidos.getText().trim());
        empleado.setTelefono(jtxtFTelefono.getText().trim());
        empleado.setCorreo(jtxtFCorreo.getText().trim());
        empleado.setTipoEmpleado(tipoEmpleado);

        return empleado;
    }

    private double obtenerSueldoFijo(Empleado empleado) {
        if (empleado instanceof EmpleadoTiempoCompleto) {
            return ((EmpleadoTiempoCompleto) empleado).getSueldoFijo();
        }
        return 0.0;
    }

    private double obtenerValorHora(Empleado empleado) {
        if (empleado instanceof EmpleadoTiempoParcial) {
            return ((EmpleadoTiempoParcial) empleado).getValorHora();
        }
        return 0.0;
    }

    public void selectRowEmpleado() {
        jtblEmpleados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int fila = jtblEmpleados.getSelectedRow();

                if (fila >= 0) {

                    idEmpleadoSeleccionado = Integer.parseInt(
                            jtblEmpleados.getValueAt(fila, 0).toString()
                    );

                    estadoEmpleadoSeleccionado = Boolean.parseBoolean(
                            jtblEmpleados.getValueAt(fila, 1).toString()
                    );

                    jtxtFCedula.setText(
                            jtblEmpleados.getValueAt(fila, 2).toString()
                    );

                    jtxtFNombres.setText(
                            jtblEmpleados.getValueAt(fila, 3).toString()
                    );

                    jtxtFApellidos.setText(
                            jtblEmpleados.getValueAt(fila, 4).toString()
                    );

                    jtxtFTelefono.setText(
                            jtblEmpleados.getValueAt(fila, 5).toString()
                    );

                    jtxtFCorreo.setText(
                            jtblEmpleados.getValueAt(fila, 6).toString()
                    );

                    jcbxTipoEmpleado.setSelectedItem(
                            jtblEmpleados.getValueAt(fila, 7).toString()
                    );
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtxtFCedula = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtFNombres = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtFApellidos = new javax.swing.JTextField();
        jtxtFTelefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtFCorreo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jcbxTipoEmpleado = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblEmpleados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jbtnBuscarId = new javax.swing.JButton();
        jbtnNuevo = new javax.swing.JButton();
        jbtnGuardar = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jtxtFBuscarCedula = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(150, 55, 60));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cedula");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nombre");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Apellido");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Telefono");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Correo");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tipo Empleado");

        jcbxTipoEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbxTipoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtFCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtFTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtFApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtFNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtFCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtFCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtFNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtFApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtFTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtFCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbxTipoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblEmpleados);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("GESTION EMPLEADOS");

        jbtnBuscarId.setText("Buscar por ID");
        jbtnBuscarId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBuscarIdActionPerformed(evt);
            }
        });

        jbtnNuevo.setText("Nuevo");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });

        jbtnGuardar.setText("Guardar");
        jbtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarActionPerformed(evt);
            }
        });

        jbtnEditar.setText("Editar");
        jbtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditarActionPerformed(evt);
            }
        });

        jbtnEliminar.setText("Eliminar");
        jbtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEliminarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Buscar por Cedula");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel8MouseDragged(evt);
            }
        });
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel8MousePressed(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(120, 35, 40));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("X");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1420, 0, 20, -1));

        jPanel10.setBackground(new java.awt.Color(153, 153, 153));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("-");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 20, -1));

        jPanel7.setBackground(new java.awt.Color(120, 35, 40));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("REGRESAR AL MENU");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(19, 19, 19))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtFBuscarCedula)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jbtnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnGuardar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnNuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnBuscarId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(474, 474, 474)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtFBuscarCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnBuscarId, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jbtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
        // TODO add your handling code here:
        actualizarEmpleado();
    }//GEN-LAST:event_jbtnEditarActionPerformed

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        // TODO add your handling code here:
        registrarEmpleado();
    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarActionPerformed
        // TODO add your handling code here:
        eliminarEmpleado();
    }//GEN-LAST:event_jbtnEliminarActionPerformed

    private void jbtnBuscarIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscarIdActionPerformed
        // TODO add your handling code here:
        buscarEmpleadoPorCedula();
    }//GEN-LAST:event_jbtnBuscarIdActionPerformed

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        // TODO add your handling code here:
        nuevoEmpleado();
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
        volverMenuAdministrador();

    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        // TODO add your handling code here:
                this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel8MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel8MouseDragged

    private void jPanel8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jPanel8MousePressed

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
            java.util.logging.Logger.getLogger(EmpleadoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmpleadoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmpleadoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmpleadoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmpleadoFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnBuscarId;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JComboBox<String> jcbxTipoEmpleado;
    private javax.swing.JTable jtblEmpleados;
    private javax.swing.JTextField jtxtFApellidos;
    private javax.swing.JTextField jtxtFBuscarCedula;
    private javax.swing.JTextField jtxtFCedula;
    private javax.swing.JTextField jtxtFCorreo;
    private javax.swing.JTextField jtxtFNombres;
    private javax.swing.JTextField jtxtFTelefono;
    // End of variables declaration//GEN-END:variables
}

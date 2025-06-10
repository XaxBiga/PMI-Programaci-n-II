package com.mycompany.pmi_ii.View;

import com.mycompany.pmi_ii.Controller.JugadorController;
import com.mycompany.pmi_ii.Controller.ArbitroController;
import com.mycompany.pmi_ii.Model.Arbitro;
import com.mycompany.pmi_ii.Model.Fecha;
import com.mycompany.pmi_ii.Model.Jugador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter; //rama_tomi
import javax.swing.table.TableRowSorter;//rama_tomi
/*.*/

/**
 *
 * @author Usuario
 */
public class MenuInicial extends javax.swing.JFrame {
    private JugadorController controller = new JugadorController();
    private ArbitroController controllerArbitro = new ArbitroController();
    private TableRowSorter<DefaultTableModel> sorterJugador;
    private TableRowSorter<DefaultTableModel> sorterArbitro;

    /**
     * Creates new form AgregarJugador
     */
    public MenuInicial() {
        initComponents();
        CargarArbitroArchivo();
        CargarJugadorArchivo();
        crearTablaArbitro();
        crearTablaJugador();
        ActualizarTablaJugador();

        configurarTablaJugadorClickListener();
        configurarjSpinners();
        configurarTablaArbitroClickListener();
    }
        private void configurarjSpinners(){
            jSpinnerGolesJugador.setModel(new javax.swing.SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
            jSpinnerAmarillaJugador.setModel(new javax.swing.SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
            jSpinnerRojaJugador.setModel(new javax.swing.SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
            jSpinnerTarjetaSacadas.setModel(new javax.swing.SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
        }
        private void configurarTablaJugadorClickListener() {
        jTableJugador.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int filaSeleccionada = jTableJugador.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int filaModelo = jTableJugador.convertRowIndexToModel(filaSeleccionada);

                        List<Jugador> jugadores = controller.getJugadores();
                        Jugador jugadorSeleccionado = jugadores.get(filaModelo);

                        abrirVentanaModificarJugador(jugadorSeleccionado);
                    }
                }
            }
        });
    }
    private void abrirVentanaModificarJugador(Jugador jugador) {
        ModificarVentanaJugador ventana = new ModificarVentanaJugador();
        ventana.setJugador(jugador);
        ventana.setController(controller);
        ventana.setMenuInicial(this);
        ventana.setLocationRelativeTo(null);

        ventana.setVisible(true);

    }
    private void configurarTablaArbitroClickListener(){
        
        jTableArbitro.addMouseListener(new java.awt.event.MouseAdapter() {
        
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt){
            
            if(evt.getClickCount() == 2){
                int filaSeleccionada = jTableArbitro.getSelectedRow();
                if (filaSeleccionada != -1) {
                        int filaModelo = jTableArbitro.convertRowIndexToModel(filaSeleccionada);

                        List<Arbitro> arbitros = controllerArbitro.getArbitros();
                        Arbitro arbitroSeleccionado = arbitros.get(filaModelo);
                        
                        abrirVentanaModificarArbitro(arbitroSeleccionado);
                    }
            
                }
            }
        });
    }
    private void abrirVentanaModificarArbitro(Arbitro arbitro){
    
        ModificarVentanaArbitro ventana = new ModificarVentanaArbitro();
        ventana.setArbitro(arbitro);
        ventana.setController(controllerArbitro);
        ventana.setMenuInicial(this);
        ventana.setLocationRelativeTo(null);
        
        ventana.setVisible(true);
    }
    public void tablaJugadorConGolesMayoresA(int minGoles) {
    DefaultTableModel dtmJugador = (DefaultTableModel) jTableJugador2.getModel();
    dtmJugador.setRowCount(0);
    for (Jugador jug : controller.getJugadores()) {
        if (jug.GetGoles() > minGoles) {
            Object[] fila = {
                jug.GetClubActual(),
                jug.GetGoles(),
                jug.GetTarjetasRojas(),
                jug.GetPosicion(),
                jug.GetNombre(),
                jug.GetApellido(),
            };
            dtmJugador.addRow(fila);
            }
        }
    jTableJugador2.setAutoCreateRowSorter(true);
    sorterJugador = new TableRowSorter<>(dtmJugador);
    jTableJugador2.setRowSorter(sorterJugador);
    }   
    public void crearTablaJugador() {
    DefaultTableModel dtmJugador = (DefaultTableModel) jTableJugador2.getModel();
    dtmJugador.setRowCount(0);

    for (Jugador jug : controller.getJugadores()) {
        Object[] fila = {
            jug.GetClubActual(),
            jug.GetGoles(),
            jug.GetTarjetasRojas(),
            jug.GetPosicion(),
            jug.GetNombre(),
            jug.GetApellido(),
        };
        dtmJugador.addRow(fila);
        }
    jTableJugador2.setAutoCreateRowSorter(true);
    sorterJugador = new TableRowSorter<>(dtmJugador);
    jTableJugador2.setRowSorter(sorterJugador);
    }
    public void crearTablaArbitro() {
        DefaultTableModel modelo = (DefaultTableModel) jTableArbitro2.getModel();
        modelo.setRowCount(0);

        for (Arbitro arb : controllerArbitro.getArbitros()) {
            Object[] fila = {
                    arb.GetNombre(),
                    arb.getInternacional(),
                    arb.GetNacionalidad(),
                    arb.GetTarjetasSacadas(),
                    arb.GetApellido()
                    
            };
            modelo.addRow(fila);
        }

        jTableArbitro2.setAutoCreateRowSorter(true);
        sorterArbitro = new TableRowSorter<>(modelo);
        jTableArbitro2.setRowSorter(sorterArbitro);

        jLabelCantidadDeFilasJugador.setText("Filas visibles: " + jTableArbitro2.getRowCount());
    }
    public void jugadorConMasExpulsiones() {
        DefaultTableModel dtmJugador = (DefaultTableModel) jTableJugador2.getModel();
        dtmJugador.setRowCount(0);
        Jugador jugadorMax = null;
        for (Jugador jug : controller.getJugadores()) {
            if (jugadorMax == null || jug.GetTarjetasRojas() > jugadorMax.GetTarjetasRojas()) {
                jugadorMax = jug;
            }
        }
        if (jugadorMax != null) {
            Object[] fila = {
                jugadorMax.GetClubActual(),
                jugadorMax.GetGoles(),
                jugadorMax.GetTarjetasRojas(),
                jugadorMax.GetPosicion(),
                jugadorMax.GetNombre(),
                jugadorMax.GetApellido(),
            };
            dtmJugador.addRow(fila);
        }
        jTableJugador2.setAutoCreateRowSorter(true);
        sorterJugador = new TableRowSorter<>(dtmJugador);
        jTableJugador2.setRowSorter(sorterJugador);
    }

    public void agregarJugadorTabla() {
    DefaultTableModel dtmJugador = (DefaultTableModel) jTableJugador.getModel();
    dtmJugador.setRowCount(0);

    for (Jugador jug : controller.getJugadores()) {
        Object[] fila = {
            jug.GetNombre(),
            jug.GetApellido(),
            jug.GetClubActual(),
            jug.GetPosicion(),
            jug.GetNacionalidad(),
            jug.GetGoles(), 
            jug.GetTarjetasAmarillas(),
            jug.GetTarjetasRojas(),
        };
        
        dtmJugador.addRow(fila);
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

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButtonSalir = new javax.swing.JButton();
        jTextFieldApellidoJugador = new javax.swing.JTextField();
        jButtonGuardarJugador = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldNombreJugador = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        comboboxDiaJugador = new javax.swing.JComboBox<>();
        comboboxMesJugador = new javax.swing.JComboBox<>();
        comboboxAnioJugador = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        comboboxNacionalidadJugador = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jSpinnerGolesJugador = new javax.swing.JSpinner();
        jSpinnerAmarillaJugador = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jComboBoxClubJugador = new javax.swing.JComboBox<>();
        jComboBoxPosicionJugador = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jSpinnerRojaJugador = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableJugador = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableArbitro = new javax.swing.JTable();
        jButtonSalirArbitro = new javax.swing.JButton();
        jButtonGuardarArbitro = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        comboboxDiaArbitro = new javax.swing.JComboBox<>();
        comboboxMesArbitro = new javax.swing.JComboBox<>();
        comboboxAnioArbitro = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        comboboxNacionalidadArbitro = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jSpinnerTarjetaSacadas = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        comboboxInternacional = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxJugador = new javax.swing.JComboBox<>();
        jBuscadorJugador = new javax.swing.JTextField();
        jComboBoxArbitro = new javax.swing.JComboBox<>();
        jBuscadorArbitro = new javax.swing.JTextField();
        jLabelCantidadDeFilasJugador = new javax.swing.JLabel();
        jLabelCantidadDeFilasArbitro = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabelCantidadDeFilasJugador1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableJugador2 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableArbitro2 = new javax.swing.JTable();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonSalir.setBackground(new java.awt.Color(204, 204, 204));
        jButtonSalir.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButtonSalir.setForeground(new java.awt.Color(102, 102, 102));
        jButtonSalir.setText("Salir");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 620, 90, 30));

        jTextFieldApellidoJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jTextFieldApellidoJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        jTextFieldApellidoJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldApellidoJugadorActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldApellidoJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 173, -1));

        jButtonGuardarJugador.setBackground(new java.awt.Color(0, 153, 153));
        jButtonGuardarJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButtonGuardarJugador.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGuardarJugador.setText("Cargar Jugador");
        jButtonGuardarJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarJugadorActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonGuardarJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 190, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 102));
        jLabel13.setText("Agregar Jugador");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nombre");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Apellido");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));

        jTextFieldNombreJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jTextFieldNombreJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        jTextFieldNombreJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreJugadorActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldNombreJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 173, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Fecha nacimiento");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        comboboxDiaJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        comboboxDiaJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        comboboxDiaJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        comboboxDiaJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxDiaJugadorActionPerformed(evt);
            }
        });
        jPanel1.add(comboboxDiaJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, -1));

        comboboxMesJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        comboboxMesJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        comboboxMesJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jPanel1.add(comboboxMesJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 250, -1, -1));

        comboboxAnioJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        comboboxAnioJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "2010", "2009", "2008", "2007", "2006", "2005", "2004" }));
        comboboxAnioJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        jPanel1.add(comboboxAnioJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Dia");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Mes");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, -1, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Año");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 280, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Nacionalidad ");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        comboboxNacionalidadJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        comboboxNacionalidadJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "America del Sur", "America del Norte", "Centro America", "Africa", "Asia", "Europa" }));
        comboboxNacionalidadJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jPanel1.add(comboboxNacionalidadJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 162, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Goles");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Tarjeta Amarilla");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, -1, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Tarjeta Roja");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, -1, -1));

        jSpinnerGolesJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jSpinnerGolesJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jPanel1.add(jSpinnerGolesJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, 98, -1));

        jSpinnerAmarillaJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jSpinnerAmarillaJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jPanel1.add(jSpinnerAmarillaJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 480, 88, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Club");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Posición");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, -1, -1));

        jComboBoxClubJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jComboBoxClubJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Boca Juniors", "River Plate", "Racing Club", "San Lorenzo", "Independiente", "Huracán", "Talleres", "Estudiantes de La Plata", "Newell's Old Boys", "Rosario Central" }));
        jComboBoxClubJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jComboBoxClubJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxClubJugadorActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBoxClubJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, -1, -1));

        jComboBoxPosicionJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jComboBoxPosicionJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Arquero", "Defensor", "MedioCampista", "Delantero" }));
        jComboBoxPosicionJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jComboBoxPosicionJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPosicionJugadorActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBoxPosicionJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 400, -1, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel25.setText("Doble clic sobre un jugador para modificar o eliminar");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 520, 418, 55));

        jLabel27.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 102, 102));
        jLabel27.setText("    Sistema de Gestion y Administracion de Jugadores ");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 500, 40));

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jSpinnerRojaJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jSpinnerRojaJugador.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jSpinnerRojaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(405, Short.MAX_VALUE)
                .addComponent(jSpinnerRojaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 330, 450));

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTableJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTableJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jTableJugador.setForeground(new java.awt.Color(51, 51, 51));
        jTableJugador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellido", "Club", "Posición", "Nacionalidad", "Goles", "Amarillas", "Rojas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableJugador);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 922, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 960, 410));

        jLabel28.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 102, 102));
        jLabel28.setText("Tabla de Jugadores");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, -1, -1));

        jTabbedPane1.addTab("Jugador", jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Agregar Arbitro");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 160, 35));

        jTableArbitro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTableArbitro.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jTableArbitro.setForeground(new java.awt.Color(51, 51, 51));
        jTableArbitro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellido", "Nacionalidad", "Tarjetas Sacadas", "Internacional"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableArbitro);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, 735, 303));

        jButtonSalirArbitro.setBackground(new java.awt.Color(204, 204, 204));
        jButtonSalirArbitro.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButtonSalirArbitro.setForeground(new java.awt.Color(102, 102, 102));
        jButtonSalirArbitro.setText("Salir");
        jButtonSalirArbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirArbitroActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonSalirArbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 80, 30));

        jButtonGuardarArbitro.setBackground(new java.awt.Color(0, 153, 153));
        jButtonGuardarArbitro.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButtonGuardarArbitro.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGuardarArbitro.setText("Cargar Arbitro");
        jButtonGuardarArbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarArbitroActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonGuardarArbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 160, 30));

        jLabel26.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel26.setText("Doble clic sobre un Arbitro para modificar o eliminar");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 480, 418, 55));

        jLabel29.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 102, 102));
        jLabel29.setText("Sistema de Gestion y Administracion de Arbitros");
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, -1, -1));

        jPanel6.setBackground(new java.awt.Color(0, 102, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 796, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, 800, 340));

        jPanel7.setBackground(new java.awt.Color(0, 102, 102));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre ");

        txtNombre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Apellido ");

        txtApellido.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Fecha de Nacimiento ");

        comboboxDiaArbitro.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        comboboxDiaArbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        comboboxDiaArbitro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));

        comboboxMesArbitro.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        comboboxMesArbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        comboboxMesArbitro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));

        comboboxAnioArbitro.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        comboboxAnioArbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "2010", "2009", "2008", "2007", "2006", "2005", "2004" }));
        comboboxAnioArbitro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        comboboxAnioArbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxAnioArbitroActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Dia");

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Mes");

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Año");

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nacionalidad ");

        comboboxNacionalidadArbitro.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        comboboxNacionalidadArbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "America del Sur", "America del Norte", "Centro America", "Africa", "Asia", "Europa" }));
        comboboxNacionalidadArbitro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Cantidad de Tarjetas sacadas");

        jSpinnerTarjetaSacadas.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jSpinnerTarjetaSacadas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Es internacional ");

        comboboxInternacional.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        comboboxInternacional.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Si", "No" }));
        comboboxInternacional.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboboxInternacional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSpinnerTarjetaSacadas, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboboxNacionalidadArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(txtApellido))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboboxDiaArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboboxMesArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel7))
                            .addComponent(comboboxAnioArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboboxDiaArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboboxMesArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboboxAnioArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboboxNacionalidadArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jSpinnerTarjetaSacadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboboxInternacional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 140, 310, 340));

        jLabel30.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 102, 102));
        jLabel30.setText("Tabla de Arbitros");
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, -1, -1));

        jTabbedPane1.addTab("Árbitro", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jComboBoxJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "por equipo", "gol/es", "expulsion/es", "posicion" }));
        jComboBoxJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.darkGray, java.awt.Color.darkGray, java.awt.Color.darkGray, java.awt.Color.darkGray));
        jComboBoxJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxJugadorActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBoxJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, -1, -1));

        jBuscadorJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jBuscadorJugador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBuscadorJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBuscadorJugadorActionPerformed(evt);
            }
        });
        jBuscadorJugador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBuscadorJugadorKeyReleased(evt);
            }
        });
        jPanel3.add(jBuscadorJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 198, -1));

        jComboBoxArbitro.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jComboBoxArbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "todos", "internacional" }));
        jComboBoxArbitro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.darkGray, java.awt.Color.darkGray, java.awt.Color.darkGray, java.awt.Color.darkGray));
        jComboBoxArbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxArbitroActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBoxArbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, -1, -1));

        jBuscadorArbitro.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jBuscadorArbitro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBuscadorArbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBuscadorArbitroActionPerformed(evt);
            }
        });
        jBuscadorArbitro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBuscadorArbitroKeyReleased(evt);
            }
        });
        jPanel3.add(jBuscadorArbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 380, 203, -1));

        jLabelCantidadDeFilasJugador.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jPanel3.add(jLabelCantidadDeFilasJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 380, 120, 25));

        jLabelCantidadDeFilasArbitro.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabelCantidadDeFilasArbitro.setForeground(new java.awt.Color(0, 102, 102));
        jLabelCantidadDeFilasArbitro.setText("Filtrar arbitro por:");
        jPanel3.add(jLabelCantidadDeFilasArbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, 25));

        jLabel24.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 102, 102));
        jLabel24.setText("Filtrar jugador por:");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabelCantidadDeFilasJugador1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jPanel3.add(jLabelCantidadDeFilasJugador1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, 130, 28));

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 102));
        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 600, 90, 40));

        jLabel31.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 102, 102));
        jLabel31.setText("Sistema de Consultas");
        jPanel3.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, -1, -1));

        jPanel8.setBackground(new java.awt.Color(0, 102, 102));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTableJugador2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jTableJugador2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Club", "Gol/es", "Expulsion/es", "Posicion", "Nombre", "Apellido"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableJugador2);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 610, 220));

        jPanel9.setBackground(new java.awt.Color(0, 102, 102));
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTableArbitro2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jTableArbitro2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Internacional", "Nacionalidad", "Tarjetas Sacadas", "Apellido"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTableArbitro2);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 610, 230));

        jTabbedPane1.addTab("Consulta", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 212, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        exit(0);
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed

    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed

    }//GEN-LAST:event_txtApellidoActionPerformed

    private void jTextFieldApellidoJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldApellidoJugadorActionPerformed

    }//GEN-LAST:event_jTextFieldApellidoJugadorActionPerformed

    
    //Guarda Jugador en la Lista
    private void jButtonGuardarJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarJugadorActionPerformed
        String nombre = jTextFieldNombreJugador.getText();
        String apellido = jTextFieldApellidoJugador.getText();
        int dia = Integer.parseInt((String) comboboxDiaJugador.getSelectedItem());
        int mes = Integer.parseInt((String) comboboxMesJugador.getSelectedItem());
        int anio = Integer.parseInt((String) comboboxAnioJugador.getSelectedItem());
        Fecha fecha = new Fecha(dia, mes, anio);
        
        String nacionalidadJugador = (String) comboboxNacionalidadJugador.getSelectedItem();
        int goles = (int) jSpinnerGolesJugador.getValue();
        int amarillas = (int) jSpinnerAmarillaJugador.getValue();
        int roja = (int) jSpinnerRojaJugador.getValue();
        String club = (String) jComboBoxClubJugador.getSelectedItem();
        String posicion = (String) jComboBoxPosicionJugador.getSelectedItem();
        
        boolean exito = controller.guardarJugador(nombre, apellido, fecha, nacionalidadJugador, club, posicion, goles, amarillas, roja);
        
        agregarJugadorTabla();
        crearTablaJugador();
        GuardarJugadorArchivo();
        if (exito == true){
            jTextFieldNombreJugador.setText("");
            jTextFieldApellidoJugador.setText("");
            comboboxDiaJugador.setSelectedIndex(0);
            comboboxMesJugador.setSelectedIndex(0);
            comboboxAnioJugador.setSelectedIndex(0);
            comboboxNacionalidadJugador.setSelectedIndex(0);
            jSpinnerGolesJugador.setValue(0);
            jSpinnerAmarillaJugador.setValue(0);
            jSpinnerRojaJugador.setValue(0);
            jComboBoxPosicionJugador.setSelectedIndex(0);
            jComboBoxClubJugador.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jButtonGuardarJugadorActionPerformed

    private void jTextFieldNombreJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreJugadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreJugadorActionPerformed

    private void comboboxDiaJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxDiaJugadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboboxDiaJugadorActionPerformed

    private void comboboxAnioArbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxAnioArbitroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboboxAnioArbitroActionPerformed

    private void jComboBoxJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxJugadorActionPerformed
        filtrar();
    }//GEN-LAST:event_jComboBoxJugadorActionPerformed

    private void jBuscadorJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBuscadorJugadorActionPerformed
        
    }//GEN-LAST:event_jBuscadorJugadorActionPerformed

    private void jComboBoxArbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxArbitroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxArbitroActionPerformed

    private void jBuscadorArbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBuscadorArbitroActionPerformed
       
    }//GEN-LAST:event_jBuscadorArbitroActionPerformed

    private void jBuscadorJugadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBuscadorJugadorKeyReleased
        filtrar();
    }//GEN-LAST:event_jBuscadorJugadorKeyReleased

    private void jBuscadorArbitroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBuscadorArbitroKeyReleased
        filtrar2();
    }//GEN-LAST:event_jBuscadorArbitroKeyReleased

    //Precarga de Arbitros - Lee del archivo y pasa a la lista
    private void CargarArbitroArchivo() {
        File archivoArbitro = new File("Arbitros.txt");

        try (BufferedReader lector = new BufferedReader(new FileReader(archivoArbitro))) {
            String nombre, apellido, nacionalidad, internacional;
            Fecha fecha = new Fecha();
            int tarjetas;

            while ((nombre = lector.readLine()) != null) {
                apellido = lector.readLine();
                fecha.SetDia(Integer.parseInt(lector.readLine()));
                fecha.SetMes(Integer.parseInt(lector.readLine()));
                fecha.SetAnio( Integer.parseInt(lector.readLine()));
                nacionalidad = lector.readLine();
                tarjetas = Integer.parseInt(lector.readLine());
                internacional = lector.readLine();

                controllerArbitro.guardarArbitro(nombre, apellido, fecha, nacionalidad, tarjetas, internacional);

            }
            //guardar a la tabla
            ActualizarTablaArbitro();
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Ocurrió un error al leer el archivo de Árbitros:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Guarda la Lista de Arbitro en un Archivo - Escribe la lista en un Archivo
    public void GuardarArbitroArchivo() {
        File archivoArbitro = new File("Arbitros.txt");

        try (FileWriter escritor = new FileWriter(archivoArbitro, false)) {
            for (Arbitro arbitro : controllerArbitro.getArbitros()) {
                Fecha f = arbitro.GetFechaNacimiento();
                escritor.write(arbitro.GetNombre() + "\n");
                escritor.write(arbitro.GetApellido() + "\n");
                escritor.write(f.GetDia() + "\n");
                escritor.write(f.GetMes() + "\n");
                escritor.write(f.GetAnio() + "\n");
                escritor.write(arbitro.GetNacionalidad() + "\n");
                escritor.write(arbitro.GetTarjetasSacadas() + "\n");
                escritor.write(arbitro.getInternacional() + "\n");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Ocurrió un error al escribir el archivo de Árbitros",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Guarda la Lista de Jugadores en un Archivo - Escribe la lista en un Archivo
    public void GuardarJugadorArchivo(){
        File ArchivoJugador = new File("Jugadores");
        try (FileWriter escritor = new FileWriter(ArchivoJugador, false)) {
            for (Jugador jug : controller.getJugadores()) {
                Fecha f = jug.GetFechaNacimiento();
                escritor.write(jug.GetNombre() + "\n");
                escritor.write(jug.GetApellido() + "\n");
                escritor.write(f.GetDia() + "\n");
                escritor.write(f.GetMes() + "\n");
                escritor.write(f.GetAnio() + "\n");
                escritor.write(jug.GetClubActual() + "\n");
                escritor.write(jug.GetPosicion() + "\n");
                escritor.write(jug.GetNacionalidad() + "\n");
                escritor.write(jug.GetGoles() + "\n");
                escritor.write(jug.GetTarjetasAmarillas() + "\n");
                escritor.write(jug.GetTarjetasRojas() + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Ocurrió un error al escribir el archivo de Jugador",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Precarga de Arbitros - Lee del archivo y pasa a la lista
    private void CargarJugadorArchivo(){
        File archivo = new File("Jugadores");

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String nombre, apellido, nacionalidad, club, posicion;
            Fecha fecha = new Fecha();
            int goles, amarillas, rojas;

            while ((nombre = lector.readLine()) != null) {
                apellido = lector.readLine();
                fecha.SetDia(Integer.parseInt(lector.readLine()));
                fecha.SetMes(Integer.parseInt(lector.readLine()));
                fecha.SetAnio(Integer.parseInt(lector.readLine()));
                club = lector.readLine();
                posicion = lector.readLine();
                nacionalidad = lector.readLine();
                goles = Integer.parseInt(lector.readLine());
                amarillas = Integer.parseInt(lector.readLine());
                rojas = Integer.parseInt(lector.readLine());


                controller.guardarJugador(
                        nombre, apellido, fecha,
                        nacionalidad, club, posicion,
                        goles, amarillas, rojas
                );
            }

            // actualiza la tabla
            ActualizarTablaJugador();

        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Ocurrió un error al leer el archivo de jugadores:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Funciones para cargar en la Tabla Arbitro
    public void ActualizarTablaArbitro(){

    // Creo el DefaultTableModel que me permite editar la tabla correspondiente
    DefaultTableModel tabla = (DefaultTableModel) jTableArbitro.getModel();

        tabla.setRowCount(0); //Resetea la Tabla para actualizar

        for(Arbitro aux : controllerArbitro.getArbitros()){

            Object[] fila = {

            aux.GetNombre(),
            aux.GetApellido(),
            aux.GetNacionalidad(),
            aux.GetTarjetasSacadas(),
            aux.getInternacional(),

        };
            tabla.addRow(fila); //agrego una persona a la lista
        }
    }
    //Funciones para cargar en la Tabla Arbitro
    public void ActualizarTablaJugador(){

        // Creo el DefaultTableModel que me permite editar la tabla correspondiente
        DefaultTableModel tabla = (DefaultTableModel) jTableJugador.getModel();

        tabla.setRowCount(0); //Resetea la Tabla para actualizar

        for(Jugador jug : controller.getJugadores()){

            Object[] fila = {

                    jug.GetNombre(),
                    jug.GetApellido(),
                    jug.GetClubActual(),
                    jug.GetPosicion(),
                    jug.GetNacionalidad(),
                    jug.GetGoles(),
                    jug.GetTarjetasAmarillas(),
                    jug.GetTarjetasRojas(),

            };
            tabla.addRow(fila); //agrego un Jugador a la lista
        }
    }
    private void jButtonGuardarArbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarArbitroActionPerformed
        // TODO add your handling code here:
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        
        int dia = Integer.parseInt((String) comboboxDiaArbitro.getSelectedItem());
        int mes = Integer.parseInt((String) comboboxMesArbitro.getSelectedItem());
        int anio = Integer.parseInt((String) comboboxAnioArbitro.getSelectedItem());
        if (dia == 0 || mes == 0 || anio == 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Fecha fecha = new Fecha(dia,mes,anio);
        
        String nacionalidad = (String) comboboxNacionalidadArbitro.getSelectedItem();
        
        int tarjetasSacadas = (int) jSpinnerTarjetaSacadas.getValue();
        
        String Internacional = (String) comboboxInternacional.getSelectedItem();
        
        Boolean exito = controllerArbitro.guardarArbitro(nombre, apellido, fecha, nacionalidad, tarjetasSacadas , Internacional);//carga el arbitro a la lista
        crearTablaArbitro();
        ActualizarTablaArbitro(); //Actualiza la tabla para mostrar al nuevo Arbitro
        GuardarArbitroArchivo(); //Guarda la modificacion en un archivo Arbitros.txt
        
        //resetea los valores luego de la carga
        if (exito == true){
        txtNombre.setText("");
        txtApellido.setText("");
        
        comboboxDiaArbitro.setSelectedIndex(0);
        comboboxMesArbitro.setSelectedIndex(0);
        comboboxAnioArbitro.setSelectedIndex(0);
        
        comboboxNacionalidadArbitro.setSelectedIndex(0);
        
        jSpinnerTarjetaSacadas.setValue(0);
        
        comboboxInternacional.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jButtonGuardarArbitroActionPerformed

    private void jButtonSalirArbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirArbitroActionPerformed
        // TODO add your handling code here:
        exit(0);
    }//GEN-LAST:event_jButtonSalirArbitroActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBoxPosicionJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPosicionJugadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxPosicionJugadorActionPerformed

    private void jComboBoxClubJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxClubJugadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxClubJugadorActionPerformed
    
    private void filtrar() {
    int columna = jComboBoxJugador.getSelectedIndex(); // índice de opción seleccionada
    String texto = jBuscadorJugador.getText();
    switch (columna) {
        case 1: // Filtrar por goles mayores a cierta cantidad
            try {
                int minGoles = Integer.parseInt(texto);
                tablaJugadorConGolesMayoresA(minGoles);
            } catch (NumberFormatException e) {
                crearTablaJugador(); // Si no se puede convertir, mostrar todo
            }
            break;
        case 2: // Mostrar jugador con más expulsiones
            jugadorConMasExpulsiones();
            break;
        default: // Filtrar por texto usando regex (ej. nombre, club, etc.)
            crearTablaJugador();
            sorterJugador.setRowFilter(RowFilter.regexFilter("(?i)" + texto, columna));
            break;
    }
    jLabelCantidadDeFilasJugador1.setText("Filas visibles: " + jTableJugador2.getRowCount());
    }
    private void filtrar2(){
int columna = jComboBoxArbitro.getSelectedIndex(); // columna elegida
        String texto = jBuscadorArbitro.getText();
        sorterArbitro.setRowFilter(RowFilter.regexFilter("(?i)" + texto, columna)); // (?i) ignora mayúsculas
        jLabelCantidadDeFilasJugador.setText("Filas visibles: " + jTableArbitro2.getRowCount());
    }
    
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
            java.util.logging.Logger.getLogger(MenuInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuInicial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboboxAnioArbitro;
    private javax.swing.JComboBox<String> comboboxAnioJugador;
    private javax.swing.JComboBox<String> comboboxDiaArbitro;
    private javax.swing.JComboBox<String> comboboxDiaJugador;
    private javax.swing.JComboBox<String> comboboxInternacional;
    private javax.swing.JComboBox<String> comboboxMesArbitro;
    private javax.swing.JComboBox<String> comboboxMesJugador;
    private javax.swing.JComboBox<String> comboboxNacionalidadArbitro;
    private javax.swing.JComboBox<String> comboboxNacionalidadJugador;
    private javax.swing.JTextField jBuscadorArbitro;
    private javax.swing.JTextField jBuscadorJugador;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonGuardarArbitro;
    private javax.swing.JButton jButtonGuardarJugador;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JButton jButtonSalirArbitro;
    private javax.swing.JComboBox<String> jComboBoxArbitro;
    private javax.swing.JComboBox<String> jComboBoxClubJugador;
    private javax.swing.JComboBox<String> jComboBoxJugador;
    private javax.swing.JComboBox<String> jComboBoxPosicionJugador;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCantidadDeFilasArbitro;
    private javax.swing.JLabel jLabelCantidadDeFilasJugador;
    private javax.swing.JLabel jLabelCantidadDeFilasJugador1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinnerAmarillaJugador;
    private javax.swing.JSpinner jSpinnerGolesJugador;
    private javax.swing.JSpinner jSpinnerRojaJugador;
    private javax.swing.JSpinner jSpinnerTarjetaSacadas;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableArbitro;
    private javax.swing.JTable jTableArbitro2;
    private javax.swing.JTable jTableJugador;
    private javax.swing.JTable jTableJugador2;
    private javax.swing.JTextField jTextFieldApellidoJugador;
    private javax.swing.JTextField jTextFieldNombreJugador;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}

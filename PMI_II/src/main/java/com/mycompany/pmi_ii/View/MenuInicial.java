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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableJugador = new javax.swing.JTable();
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
        jSpinnerRojaJugador = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jComboBoxClubJugador = new javax.swing.JComboBox<>();
        jComboBoxPosicionJugador = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        comboboxNacionalidadArbitro = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        comboboxDiaArbitro = new javax.swing.JComboBox<>();
        comboboxMesArbitro = new javax.swing.JComboBox<>();
        comboboxAnioArbitro = new javax.swing.JComboBox<>();
        jSpinnerTarjetaSacadas = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboboxInternacional = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableArbitro = new javax.swing.JTable();
        jButtonSalirArbitro = new javax.swing.JButton();
        jButtonGuardarArbitro = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxJugador = new javax.swing.JComboBox<>();
        jBuscadorJugador = new javax.swing.JTextField();
        jComboBoxArbitro = new javax.swing.JComboBox<>();
        jBuscadorArbitro = new javax.swing.JTextField();
        jLabelCantidadDeFilasJugador = new javax.swing.JLabel();
        jLabelCantidadDeFilasArbitro = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableJugador2 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableArbitro2 = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jLabelCantidadDeFilasJugador1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

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

        jButtonSalir.setText("Salir");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });

        jTextFieldApellidoJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldApellidoJugadorActionPerformed(evt);
            }
        });

        jButtonGuardarJugador.setText("Cargar Jugador");
        jButtonGuardarJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarJugadorActionPerformed(evt);
            }
        });

        jLabel13.setText("Agregar Jugador");

        jLabel14.setText("Nombre");

        jLabel15.setText("Apellido");

        jTextFieldNombreJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreJugadorActionPerformed(evt);
            }
        });

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

        jLabel10.setText("Fecha nacimiento");

        comboboxDiaJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        comboboxDiaJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxDiaJugadorActionPerformed(evt);
            }
        });

        comboboxMesJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        comboboxAnioJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "2010", "2009", "2008", "2007", "2006", "2005", "2004" }));

        jLabel12.setText("Dia");

        jLabel16.setText("Mes");

        jLabel17.setText("Año");

        jLabel18.setText("Nacionalidad :");

        comboboxNacionalidadJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "America del Sur", "America del Norte", "Centro America", "Africa", "Asia", "Europa" }));

        jLabel19.setText("Goles");

        jLabel20.setText("Tarjeta Amarilla");

        jLabel21.setText("Tarjeta Roja");

        jLabel22.setText("Club");

        jLabel23.setText("Posición");

        jComboBoxClubJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Boca Juniors", "River Plate", "Racing Club", "San Lorenzo", "Independiente", "Huracán", "Talleres", "Estudiantes de La Plata", "Newell's Old Boys", "Rosario Central" }));

        jComboBoxPosicionJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Arquero", "Cierre", "Alas", "Pivote" }));

        jLabel25.setText("Doble clic sobre un jugador para modificar o eliminar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(47, 47, 47)
                            .addComponent(jLabel13))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel15))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextFieldNombreJugador, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                                        .addComponent(jTextFieldApellidoJugador)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(103, 103, 103))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel12)
                                                .addGap(65, 65, 65))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(comboboxDiaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comboboxMesJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel16))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel17))
                                            .addComponent(comboboxAnioJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel22)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(comboboxNacionalidadJugador, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addGap(18, 18, 18)
                                    .addComponent(jSpinnerGolesJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel21)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jSpinnerRojaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel20)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jSpinnerAmarillaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel23)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jComboBoxClubJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBoxPosicionJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonGuardarJugador)
                            .addComponent(jButtonSalir))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 922, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(502, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextFieldNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTextFieldApellidoJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboboxAnioJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboboxMesJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboboxDiaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(comboboxNacionalidadJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jComboBoxClubJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxPosicionJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jSpinnerGolesJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerAmarillaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jSpinnerRojaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonGuardarJugador)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSalir))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(547, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Jugador", jPanel1);

        jLabel7.setText("Año");

        jLabel8.setText("Nacionalidad :");

        comboboxNacionalidadArbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "America del Sur", "America del Norte", "Centro America", "Africa", "Asia", "Europa" }));

        jLabel9.setText("Cantidad de Tarjetas sacadas");

        comboboxDiaArbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        comboboxMesArbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        comboboxAnioArbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "2010", "2009", "2008", "2007", "2006", "2005", "2004" }));
        comboboxAnioArbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxAnioArbitroActionPerformed(evt);
            }
        });

        jLabel11.setText("Es internacional :");

        jLabel1.setText("Ingrese los Datos del Arbitro");

        jLabel2.setText("Nombre :");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel3.setText("Apellido :");

        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha de Nacimiento :");

        jLabel5.setText("Dia");

        comboboxInternacional.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Si", "No" }));

        jLabel6.setText("Mes");

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

        jButtonSalirArbitro.setText("Salir");
        jButtonSalirArbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirArbitroActionPerformed(evt);
            }
        });

        jButtonGuardarArbitro.setText("Cargar Arbitro");
        jButtonGuardarArbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarArbitroActionPerformed(evt);
            }
        });

        jLabel26.setText("Doble clic sobre un Arbitro para modificar o eliminar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtApellido))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNombre))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(comboboxDiaArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel5)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(comboboxMesArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel7))
                            .addComponent(comboboxAnioArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboboxNacionalidadArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSpinnerTarjetaSacadas, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(comboboxInternacional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonGuardarArbitro)
                            .addComponent(jButtonSalirArbitro))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(693, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboboxDiaArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboboxMesArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboboxAnioArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(comboboxNacionalidadArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jSpinnerTarjetaSacadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboboxInternacional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonGuardarArbitro)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSalirArbitro))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(663, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Árbitro", jPanel2);

        jComboBoxJugador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "por equipo", "gol/es", "expulsion/es", "posicion" }));
        jComboBoxJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxJugadorActionPerformed(evt);
            }
        });

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

        jComboBoxArbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "todos", "internacional" }));
        jComboBoxArbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxArbitroActionPerformed(evt);
            }
        });

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

        jLabelCantidadDeFilasArbitro.setText("Filtrar arbitro por:");

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

        jLabel24.setText("Filtrar jugador por:");

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelCantidadDeFilasJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel24)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBoxJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jBuscadorJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabelCantidadDeFilasArbitro)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBoxArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jBuscadorArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabelCantidadDeFilasJugador1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1103, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBuscadorJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelCantidadDeFilasJugador1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCantidadDeFilasArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBuscadorArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCantidadDeFilasJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(479, 479, 479))
        );

        jTabbedPane1.addTab("Consulta", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
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
    private javax.swing.JLabel jLabel3;
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

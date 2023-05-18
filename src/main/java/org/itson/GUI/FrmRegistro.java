/**
 * FrmRegistro.java creada el 16/05/2023.
 */
package org.itson.GUI;

import ObjNegocio.Dias;
import ObjNegocio.Especie;
import ObjNegocio.Itinerario;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DELETE;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import org.bson.types.ObjectId;
import org.itson.implementacion.RegistarItinerario;
import org.itson.implementacion.Validador;

/**
 * Frame que permite a un guía registar un itinerario.
 *
 * @author kim, marki, elmer, yorx
 */
public class FrmRegistro extends javax.swing.JFrame {

    /**
     * Atributo que sirve para validar los valores ingresados por el usario,
     * para verificar que su formato sea correcto.
     */
    private Validador validador = new Validador();
    RegistarItinerario registrar = new RegistarItinerario();
    private List<String> listaEspecies = new ArrayList<String>();
    Itinerario itinerarioRegistro;

    private void cargarEspecies() {
        List<Especie> lista = registrar.cargarTablaRegistroCompleto();
        DefaultTableModel model = (DefaultTableModel) jTableRegistro.getModel();
        model.setRowCount(lista.size());
        for (int i = 0; i < lista.size(); i++) {
            model.setValueAt(lista.get(i).getNomEspanol(), i, 0);
            model.setValueAt(lista.get(i).getZona().getNombre(), i, 1);
        }
    }

    /**
     * Creates new form FrmRegistro
     */
    public FrmRegistro() {
        initComponents();
        imagenMapa.setIcon(new javax.swing.ImageIcon("src\\main\\java\\org\\itson\\img\\mapa.jpg"));
        cargarEspecies();
        txtFldNumMaxVisitantes.setEditable(false);
        txtFldDuracionMin.setEditable(false);
    }

    /**
     * Método para mostrar un mensaje en pantalla, recibe una cadena de texto la
     * cual es la que se desea mostrar en el mensaje informativo.
     */
    private void mostrarMensaje(String msj) {
        JOptionPane.showMessageDialog(null, msj, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Método para obtener el nombre del itinerario que ingresa el guía para
     * validarla, si el formato es correcto la regresa, de lo contrario regresa
     * null.
     *
     * @return nombre del itinerario.
     */
    private String obtenerNombreItinerario() {
        String nom = txtFldNombreItinerario.getText();
        try {
            validador.validaNombreItinerario(nom);
            return nom;
        } catch (Exception ex) {
            mostrarMensaje(ex.getMessage());
        }
        return null;
    }

    //(longitud, maxVisitantes, numEspecies, nombre, id, guia, dias, horaInicio, horaFin, especies);
    private List<Dias> construirListaDias() {
        List<Dias> dias = new ArrayList();
        if (jCheckBoxDomingo.isSelected()) {
            dias.add(Dias.DOMINGO);
        }
        if (jCheckBoxLunes.isSelected()) {
            dias.add(Dias.LUNES);
        }
        if (jCheckBoxMartes.isSelected()) {
            dias.add(Dias.MARTES);
        }
        if (jCheckBoxMiercoles.isSelected()) {
            dias.add(Dias.MIERCOLES);
        }
        if (jCheckBoxJueves.isSelected()) {
            dias.add(Dias.JUEVES);
        }
        if (jCheckBoxViernes.isSelected()) {
            dias.add(Dias.VIERNES);
        }
        if (jCheckBoxSabado.isSelected()) {
            dias.add(Dias.SABADO);
        }

        return dias;
    }

    private LocalTime construirHoras(String hora, String minuto) {
        /*

        String cadena = hora;
        String[] partes = cadena.split(":");
        String parte1 = partes[0];
        String parte2 = partes[1];
         */

        LocalTime horaInicio = LocalTime.of(Integer.parseInt(hora), Integer.parseInt(minuto));
        System.out.println(horaInicio);
        return horaInicio;
    }

    private int duracionItinerario() {
        //horas
        String horaInicio = this.txtFldHoraInicio.getText();
        int horaInicioEntero = Integer.parseInt(horaInicio);
        String horaFin = this.txtFldHoraFin.getText();
        int horaFinEntero = Integer.parseInt(horaFin);
        //minutos
        String minutosInicio = this.txtFldMinutosInicio.getText();
        int minutosInicioEntero = Integer.parseInt(minutosInicio);
        String minutosFin = this.txtFldMinutosFin.getText();
        int minutosFinEntero = Integer.parseInt(minutosFin);

        //pasamos horas a min
        horaInicioEntero = horaInicioEntero * 60;
        horaFinEntero = horaFinEntero * 60;

        //sumamos minutos a las horas
        horaInicioEntero += minutosInicioEntero;
        horaFinEntero += minutosFinEntero;

        //sumar
        int duracion = horaFinEntero - horaInicioEntero;
        return duracion;
    }

    private void aramarItinerario() {
        ObjectId idGuia = new ObjectId("64647b7c99af833b487c674e");
        if (duracionItinerario() <= 0) {
            mostrarMensaje("Horario incorrecto.");
            return;
        } else {
        txtFldDuracionMin.setText(String.valueOf(duracionItinerario()));
        //aqui utilizar el metodo construirhorafin e inicio falta implementarlo
        itinerarioRegistro = registrar.crearItinerario(0, 0, 0, obtenerNombreItinerario(), idGuia,
                construirListaDias(), construirHoras(this.txtFldHoraFin.getText(), txtFldMinutosInicio.getText()), construirHoras(this.txtFldHoraFin.getText(), txtFldMinutosFin.getText()), this.listaEspecies);
        registrar.registarItinerario(itinerarioRegistro);
        txtFldNumMaxVisitantes.setText(String.valueOf(itinerarioRegistro.getMaxVisitantes()));
        }
    }

    private boolean espaciosVacios() {
        if (txtFldHoraFin.getText().isEmpty() || txtFldMinutosFin.getText().isEmpty()
                || txtFldNombreItinerario.getText().isBlank() || construirListaDias().isEmpty()||
                this.listaEspecies.isEmpty()) {
            mostrarMensaje("El itinerario no tiene los datos completos.");
            return true;
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        pnlRegistro = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRegistro = new javax.swing.JTable();
        jCheckBoxDomingo = new javax.swing.JCheckBox();
        jCheckBoxLunes = new javax.swing.JCheckBox();
        jCheckBoxMartes = new javax.swing.JCheckBox();
        jCheckBoxMiercoles = new javax.swing.JCheckBox();
        jCheckBoxJueves = new javax.swing.JCheckBox();
        jCheckBoxViernes = new javax.swing.JCheckBox();
        jCheckBoxSabado = new javax.swing.JCheckBox();
        txtFldMinutosFin = new java.awt.TextField();
        jLblHoraI = new javax.swing.JLabel();
        jLblHoraF = new javax.swing.JLabel();
        jLblMinutos = new javax.swing.JLabel();
        txtFldNumMaxVisitantes = new java.awt.TextField();
        jLblNomItinerario = new javax.swing.JLabel();
        txtFldNombreItinerario = new java.awt.TextField();
        txtFldDuracionMin = new java.awt.TextField();
        jLblNumMaxVisitantes = new javax.swing.JLabel();
        jLblFormato1 = new javax.swing.JLabel();
        jLblTituloDias = new javax.swing.JLabel();
        jLblTituloDias2 = new javax.swing.JLabel();
        jLblTituloHorario1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnRegistrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtFldMinutosInicio = new java.awt.TextField();
        txtFldHoraFin = new java.awt.TextField();
        jLblFormato2 = new javax.swing.JLabel();
        jLblHora = new javax.swing.JLabel();
        txtFldMinutosFin1 = new java.awt.TextField();
        txtFldHoraInicio = new java.awt.TextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        imagenMapa = new javax.swing.JLabel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro");
        setMaximumSize(new java.awt.Dimension(1200, 800));
        setMinimumSize(new java.awt.Dimension(1200, 800));
        setPreferredSize(new java.awt.Dimension(1200, 800));
        setResizable(false);

        pnlRegistro.setBackground(new java.awt.Color(102, 153, 0));
        pnlRegistro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableRegistro.setForeground(new java.awt.Color(51, 102, 0));
        jTableRegistro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Especies", "Zonas", "Seleccionado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableRegistroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableRegistro);

        pnlRegistro.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 410, 330));

        jCheckBoxDomingo.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jCheckBoxDomingo.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxDomingo.setText("Domingo");
        pnlRegistro.add(jCheckBoxDomingo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 450, 110, -1));

        jCheckBoxLunes.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jCheckBoxLunes.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxLunes.setText("Lunes");
        pnlRegistro.add(jCheckBoxLunes, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 450, 100, -1));

        jCheckBoxMartes.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jCheckBoxMartes.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxMartes.setText("Martes");
        pnlRegistro.add(jCheckBoxMartes, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, 110, -1));

        jCheckBoxMiercoles.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jCheckBoxMiercoles.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxMiercoles.setText("Miércoles");
        pnlRegistro.add(jCheckBoxMiercoles, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 500, 110, -1));

        jCheckBoxJueves.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jCheckBoxJueves.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxJueves.setText("Jueves");
        pnlRegistro.add(jCheckBoxJueves, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 500, 110, -1));

        jCheckBoxViernes.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jCheckBoxViernes.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxViernes.setText("Viernes");
        pnlRegistro.add(jCheckBoxViernes, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 500, 110, -1));

        jCheckBoxSabado.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jCheckBoxSabado.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxSabado.setText("Sábado");
        pnlRegistro.add(jCheckBoxSabado, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 100, -1));

        txtFldMinutosFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFldMinutosFinKeyTyped(evt);
            }
        });
        pnlRegistro.add(txtFldMinutosFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 700, 60, 30));

        jLblHoraI.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLblHoraI.setForeground(new java.awt.Color(255, 255, 255));
        jLblHoraI.setText("Hora inicio:");
        pnlRegistro.add(jLblHoraI, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 660, 89, 20));

        jLblHoraF.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLblHoraF.setForeground(new java.awt.Color(255, 255, 255));
        jLblHoraF.setText("Hora fin:");
        pnlRegistro.add(jLblHoraF, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 700, 80, 20));

        jLblMinutos.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLblMinutos.setForeground(new java.awt.Color(255, 255, 255));
        jLblMinutos.setText("Minutos");
        pnlRegistro.add(jLblMinutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 640, -1, 20));
        pnlRegistro.add(txtFldNumMaxVisitantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 530, 82, 30));

        jLblNomItinerario.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLblNomItinerario.setForeground(new java.awt.Color(255, 255, 255));
        jLblNomItinerario.setText("Nombre del itinerario:");
        pnlRegistro.add(jLblNomItinerario, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 650, -1, 20));
        pnlRegistro.add(txtFldNombreItinerario, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 680, 210, 30));
        pnlRegistro.add(txtFldDuracionMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 530, 82, 30));

        jLblNumMaxVisitantes.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLblNumMaxVisitantes.setForeground(new java.awt.Color(255, 255, 255));
        jLblNumMaxVisitantes.setText("Número máximo de visitantes:");
        pnlRegistro.add(jLblNumMaxVisitantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 490, -1, 20));

        jLblFormato1.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLblFormato1.setForeground(new java.awt.Color(255, 255, 255));
        jLblFormato1.setText("Duración del recorrido en minutos:");
        pnlRegistro.add(jLblFormato1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 490, -1, 20));

        jLblTituloDias.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLblTituloDias.setForeground(new java.awt.Color(255, 255, 255));
        jLblTituloDias.setText("Selecciona los días que se realizará el recorrido:");
        pnlRegistro.add(jLblTituloDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, 520, -1));

        jLblTituloDias2.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        pnlRegistro.add(jLblTituloDias2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 234, -1));

        jLblTituloHorario1.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLblTituloHorario1.setForeground(new java.awt.Color(255, 255, 255));
        jLblTituloHorario1.setText("Especificaciones del recorrido:");
        pnlRegistro.add(jLblTituloHorario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 420, 270, 30));

        jPanel1.setBackground(new java.awt.Color(103, 142, 25));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );

        pnlRegistro.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 460, 10, 130));

        btnRegistrar.setBackground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(0, 0, 0));
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        pnlRegistro.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 680, 160, 30));

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("Cancelar");
        pnlRegistro.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 680, 150, 30));

        jPanel2.setBackground(new java.awt.Color(103, 142, 25));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );

        pnlRegistro.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 460, -1, -1));

        jPanel4.setBackground(new java.awt.Color(103, 142, 25));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        pnlRegistro.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 460, 420, 10));

        jPanel5.setBackground(new java.awt.Color(103, 142, 25));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        pnlRegistro.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 580, 410, 10));

        txtFldMinutosInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFldMinutosInicioKeyTyped(evt);
            }
        });
        pnlRegistro.add(txtFldMinutosInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 660, 60, 30));

        txtFldHoraFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFldHoraFinKeyTyped(evt);
            }
        });
        pnlRegistro.add(txtFldHoraFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 700, 60, 30));

        jLblFormato2.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLblFormato2.setForeground(new java.awt.Color(255, 255, 255));
        jLblFormato2.setText("Formato 24 hrs");
        pnlRegistro.add(jLblFormato2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 610, 80, 30));

        jLblHora.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLblHora.setForeground(new java.awt.Color(255, 255, 255));
        jLblHora.setText("Hora");
        pnlRegistro.add(jLblHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 640, -1, 20));

        txtFldMinutosFin1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFldMinutosFin1KeyTyped(evt);
            }
        });
        pnlRegistro.add(txtFldMinutosFin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 700, 60, 30));

        txtFldHoraInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldHoraInicioActionPerformed(evt);
            }
        });
        txtFldHoraInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFldHoraInicioKeyTyped(evt);
            }
        });
        pnlRegistro.add(txtFldHoraInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 660, 60, 30));

        jLabel1.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setText("G");
        pnlRegistro.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 250, 20, 20));

        jLabel2.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 0, 0));
        jLabel2.setText("A");
        pnlRegistro.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 80, 20, 20));

        jLabel3.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 0, 0));
        jLabel3.setText("C");
        pnlRegistro.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 80, 20, 20));

        jLabel4.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 0, 0));
        jLabel4.setText("A");
        pnlRegistro.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 80, 20, 20));

        jLabel6.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 0));
        jLabel6.setText("B");
        pnlRegistro.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 80, 20, 20));

        jLabel7.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 0, 0));
        jLabel7.setText("D");
        pnlRegistro.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 80, 20, 20));

        jLabel8.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 0, 0));
        jLabel8.setText("E");
        pnlRegistro.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 140, 20, 20));

        jLabel9.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 0, 0));
        jLabel9.setText("F");
        pnlRegistro.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 180, 20, 20));
        pnlRegistro.add(imagenMapa, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 450, 390));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 1318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("JRegistro");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Botón que se encarga de llamar al método que permite registar un
     * itinerario.
     *
     * @param evt
     */
    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:
        if (espaciosVacios() == true) {

        } else {
            aramarItinerario();
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void txtFldMinutosInicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFldMinutosInicioKeyTyped
        validador.validarHorario(evt, this.txtFldMinutosInicio, 60);
    }//GEN-LAST:event_txtFldMinutosInicioKeyTyped

    private void txtFldMinutosFinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFldMinutosFinKeyTyped
        // TODO add your handling code here:
        validador.validarHorario(evt, this.txtFldMinutosFin, 60);
    }//GEN-LAST:event_txtFldMinutosFinKeyTyped

    private void txtFldHoraFinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFldHoraFinKeyTyped
        // TODO add your handling code here:
        validador.validarHorario(evt, this.txtFldHoraFin, 24);
    }//GEN-LAST:event_txtFldHoraFinKeyTyped

    private void jTableRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRegistroMouseClicked
        if (this.jTableRegistro.getValueAt(this.jTableRegistro.getSelectedRow(), 2).equals(true)) {
            this.listaEspecies.add((String) this.jTableRegistro.getValueAt(this.jTableRegistro.getSelectedRow(), 0));
            System.out.println(this.jTableRegistro.getValueAt(this.jTableRegistro.getSelectedRow(), 0));
        }
    }//GEN-LAST:event_jTableRegistroMouseClicked

    private void txtFldMinutosFin1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFldMinutosFin1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldMinutosFin1KeyTyped

    private void txtFldHoraInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldHoraInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldHoraInicioActionPerformed

    private void txtFldHoraInicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFldHoraInicioKeyTyped
        // TODO add your handling code here:
        validador.validarHorario(evt, this.txtFldHoraInicio, 24);
    }//GEN-LAST:event_txtFldHoraInicioKeyTyped

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
            java.util.logging.Logger.getLogger(FrmRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmRegistro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel imagenMapa;
    private javax.swing.JCheckBox jCheckBoxDomingo;
    private javax.swing.JCheckBox jCheckBoxJueves;
    private javax.swing.JCheckBox jCheckBoxLunes;
    private javax.swing.JCheckBox jCheckBoxMartes;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBox jCheckBoxMiercoles;
    private javax.swing.JCheckBox jCheckBoxSabado;
    private javax.swing.JCheckBox jCheckBoxViernes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblFormato1;
    private javax.swing.JLabel jLblFormato2;
    private javax.swing.JLabel jLblHora;
    private javax.swing.JLabel jLblHoraF;
    private javax.swing.JLabel jLblHoraI;
    private javax.swing.JLabel jLblMinutos;
    private javax.swing.JLabel jLblNomItinerario;
    private javax.swing.JLabel jLblNumMaxVisitantes;
    private javax.swing.JLabel jLblTituloDias;
    private javax.swing.JLabel jLblTituloDias2;
    private javax.swing.JLabel jLblTituloHorario1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableRegistro;
    private javax.swing.JPanel pnlRegistro;
    private java.awt.TextField txtFldDuracionMin;
    private java.awt.TextField txtFldHoraFin;
    private java.awt.TextField txtFldHoraInicio;
    private java.awt.TextField txtFldMinutosFin;
    private java.awt.TextField txtFldMinutosFin1;
    private java.awt.TextField txtFldMinutosInicio;
    private java.awt.TextField txtFldNombreItinerario;
    private java.awt.TextField txtFldNumMaxVisitantes;
    // End of variables declaration//GEN-END:variables
}

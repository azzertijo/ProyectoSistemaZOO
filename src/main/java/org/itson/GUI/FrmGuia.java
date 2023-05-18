/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.itson.GUI;

import ObjNegocio.Guia;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.itson.implementacion.SeleccionarGuia;

/**
 * Frame que permite visualizar todos los guías que hay en la base y seleccionar uno para las pruebas
 * @author kim, marki, elmer, yorx
 */
public class FrmGuia extends javax.swing.JFrame {

    /**
     * Creates new form FrmGuia
     */
    public FrmGuia() {
        initComponents();
        cargarComboBoxGuias();
        guiaSeleccionado = (Guia) cbxGuias.getSelectedItem();
    }

    private SeleccionarGuia seleccionGuia = new SeleccionarGuia();
    private DefaultComboBoxModel<Guia> modeloComboBox = new DefaultComboBoxModel<Guia>();
    private Guia guiaSeleccionado;

    /**
     * Método que carga con los datos de los guías la combo box principal
     */
    private void cargarComboBoxGuias() {
        List<Guia> guias = seleccionGuia.recuperarGuias();
        for (Guia guia : guias) {
            modeloComboBox.addElement(guia);
        }
        cbxGuias.setModel(modeloComboBox);
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
        cbxGuias = new javax.swing.JComboBox<>();
        btnIniciarRegistro = new javax.swing.JToggleButton();
        btnBuscarItin = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Seleccionar guía");
        setMinimumSize(new java.awt.Dimension(1040, 730));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(102, 153, 0));

        jLabel1.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Seleccionar Guía:");

        cbxGuias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxGuiasActionPerformed(evt);
            }
        });

        btnIniciarRegistro.setBackground(new java.awt.Color(0, 0, 0));
        btnIniciarRegistro.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        btnIniciarRegistro.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciarRegistro.setText("Regitrar Itinerario");
        btnIniciarRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarRegistroActionPerformed(evt);
            }
        });

        btnBuscarItin.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscarItin.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        btnBuscarItin.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarItin.setText("Buscar Itinerario");
        btnBuscarItin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarItinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxGuias, javax.swing.GroupLayout.PREFERRED_SIZE, 805, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(398, 398, 398)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscarItin, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnIniciarRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(122, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cbxGuias, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(182, 182, 182)
                .addComponent(btnIniciarRegistro)
                .addGap(18, 18, 18)
                .addComponent(btnBuscarItin)
                .addContainerGap(265, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Acción que permite seleccionar el guía eligiendolo en la combo box
     * @param evt evento que desencadena la acción
     */
    private void cbxGuiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxGuiasActionPerformed
        // TODO add your handling code here:
        guiaSeleccionado = (Guia) cbxGuias.getSelectedItem();
    }//GEN-LAST:event_cbxGuiasActionPerformed

    /**
     * Botón que te redirige a la ventana de registro de itinerarios con los datos del guía seleccionados
     * @param evt evento que desencadena la acción
     */
    private void btnIniciarRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarRegistroActionPerformed
        // TODO add your handling code here:
        FrmRegistro registro = new FrmRegistro(guiaSeleccionado);
        this.dispose();
        registro.setVisible(true);
    }//GEN-LAST:event_btnIniciarRegistroActionPerformed

    /**
     * Botón que te redirige a la ventana de busqueda de itinerarios con los datos del guía seleccionados
     * @param evt evento que desencadena la acción
     */
    private void btnBuscarItinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarItinActionPerformed
        FrmBuscar registro = new FrmBuscar(guiaSeleccionado);
        this.dispose();
        registro.setVisible(true);
    }//GEN-LAST:event_btnBuscarItinActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnBuscarItin;
    private javax.swing.JToggleButton btnIniciarRegistro;
    private javax.swing.JComboBox<Guia> cbxGuias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

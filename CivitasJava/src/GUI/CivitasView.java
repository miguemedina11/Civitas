package GUI;

import java.util.ArrayList;
import civitas.CivitasJuego;
import civitas.OperacionesJuego;
import civitas.Jugador;
import civitas.SalidasCarcel;

/**
 *
 * @author escribano
 */
public class CivitasView extends javax.swing.JFrame {
    CivitasJuego juego;
    JugadorPanel jugadorPanel;
    GestionarDialog gestionarD;

    /**
     * Creates new form CivitasView
     */
    public CivitasView() {
        initComponents();
        setLocationRelativeTo(null);
        jugadorPanel=new JugadorPanel();
        gestionarD=new GestionarDialog(this);
        contenedorVistaJugador.add(jugadorPanel);
        elegirCompra.setVisible(false);
        ranking.setVisible(false);
        field_ranking.setVisible(false);
        repaint();
        revalidate();
    }
    
    void setCivitasJuego(CivitasJuego juego){
        this.juego=juego;
        setVisible(true);
    }
    
    void actualizarVista(){
        jugadorPanel.setJugador(juego.getJugadorActual());
        contenedorCasilla.setText(juego.getCasillaActual().toString());
        
        pack();
        repaint();
        revalidate();
    }
    
    void ranking(){
        ArrayList<Jugador> listaRanking=juego.ranking();
        String lista="";

        for(int i=0;i<listaRanking.size();i++){
            Jugador jugador=listaRanking.get(i);
            lista+=jugador.getNombre()+ " (Saldo: " + Float.toString(jugador.getSaldo()) + ")\n";
        }
        field_ranking.setText(lista);
        ranking.setVisible(true);
        field_ranking.setVisible(true);
    }
    
    void mostrarSiguienteOperacion(OperacionesJuego op){
        field_siguienteOperacion.setText(op.toString());
        actualizarVista();
    }
    
    void mostrarEventos() {
        DiarioDialog diarioD= new DiarioDialog(this); //crea la ventana del diario
    }
    
    Respuestas comprar(){
        int opcion= elegirCompra.showConfirmDialog(null, "¿Quieres comprar la calle actual?",
                "Compra", elegirCompra.YES_NO_OPTION);
        
        return Respuestas.values()[opcion];
    }
    
    SalidasCarcel salirCarcel(){
        String[] opciones = {"Pagar", "Tirar"};
        
        int respuesta=elegirSalirCarcel.showOptionDialog(null, "¿Cómo quieres salir de la cárcel?",
                        "Salir de la cárcel", elegirSalirCarcel.DEFAULT_OPTION,
                        elegirSalirCarcel.QUESTION_MESSAGE,null, opciones, opciones[0] );
        
        return SalidasCarcel.values()[respuesta];
    }
    
    int getGestion(){
        return gestionarD.getGestion();
    }
    
    int getPropiedad(){
        return gestionarD.getPropiedad();
    }
    
    public void gestionar(){
        gestionarD.gestionar(juego.getJugadorActual());
        gestionarD.pack();
        gestionarD.repaint();
        gestionarD.revalidate();
        gestionarD.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo = new javax.swing.JLabel();
        contenedorVistaJugador = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        contenedorCasilla = new javax.swing.JTextArea();
        siguienteOperacion = new javax.swing.JLabel();
        field_siguienteOperacion = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        field_ranking = new javax.swing.JTextArea();
        ranking = new javax.swing.JLabel();
        elegirCompra = new javax.swing.JOptionPane();
        elegirSalirCarcel = new javax.swing.JOptionPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 720));

        titulo.setText("Civitas (GOTY edition)");
        titulo.setEnabled(false);

        contenedorVistaJugador.setEnabled(false);

        contenedorCasilla.setColumns(20);
        contenedorCasilla.setRows(5);
        contenedorCasilla.setEnabled(false);
        jScrollPane1.setViewportView(contenedorCasilla);

        siguienteOperacion.setText("Siguiente Operación:");
        siguienteOperacion.setEnabled(false);

        field_siguienteOperacion.setText("siguiente op");
        field_siguienteOperacion.setEnabled(false);
        field_siguienteOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_siguienteOperacionActionPerformed(evt);
            }
        });

        field_ranking.setColumns(20);
        field_ranking.setRows(5);
        field_ranking.setEnabled(false);
        jScrollPane2.setViewportView(field_ranking);

        ranking.setText("Ranking:");
        ranking.setEnabled(false);
        ranking.setFocusable(false);

        elegirCompra.setEnabled(false);

        elegirSalirCarcel.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(elegirSalirCarcel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(elegirCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ranking)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(siguienteOperacion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(field_siguienteOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)))
                        .addGap(115, 115, 115)
                        .addComponent(contenedorVistaJugador, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                        .addGap(91, 91, 91))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(titulo)
                        .addGap(522, 522, 522))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(titulo)
                    .addComponent(elegirSalirCarcel, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_siguienteOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(siguienteOperacion))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(elegirCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(ranking)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(contenedorVistaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void field_siguienteOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_siguienteOperacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_siguienteOperacionActionPerformed

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
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CivitasView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea contenedorCasilla;
    private javax.swing.JPanel contenedorVistaJugador;
    private javax.swing.JOptionPane elegirCompra;
    private javax.swing.JOptionPane elegirSalirCarcel;
    private javax.swing.JTextArea field_ranking;
    private javax.swing.JTextField field_siguienteOperacion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel ranking;
    private javax.swing.JLabel siguienteOperacion;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
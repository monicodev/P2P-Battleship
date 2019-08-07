package menuPrincipal;

import conexiones.Peer;
import conexiones.Server;
import conexiones.ServerBroadcast;
import java.applet.AudioClip;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class MainWindow extends javax.swing.JFrame implements Runnable{

    private Server servidor;
    private Thread T_servidor;
    private ArrayList<Peer> peerList;
    private String[] peerListIP; 
    private DefaultListModel model;
    private AudioClip mainTheme;
    private boolean sonido;
    
    public MainWindow() {
        initComponents();
        servidor = new Server();
        T_servidor = new Thread(servidor);
        T_servidor.start();
        model = new DefaultListModel();
        peerList = servidor.getPeerList().getList();
        for(int i = 0; i<peerList.size(); i++){
            peerListIP[i] = peerList.get(i).getIP();
        }
        new Thread(this).start();
        
        jList_peers.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Desea jugar con "+list.getSelectedValue().toString()+"?", "Confirmacion", JOptionPane.YES_NO_OPTION);
                    
                    if(dialogResult == 0) { //HIZO DOBLE CLICK EN LA IP, SE PROCEDE A CONECTARSE CON EL OTRO PEER
                        String IP_peer = list.getSelectedValue().toString();
                        Integer PORT = 3333; 
                        try {     
                            System.out.println(this.getClass().getName()+" -> Conectandose con "+IP_peer);
                            DatagramSocket enviador = new DatagramSocket();

                            // El dato a enviar, como array de bytes.
                            String mensaje = "NEW";
                            byte[] B_mensaje = mensaje.getBytes();
                            int L_mensaje = mensaje.length();

                            // El destinatario es IP_peer y se hace el envío por el PORT
                            DatagramPacket dgp = new DatagramPacket(B_mensaje, L_mensaje, InetAddress.getByName(IP_peer), PORT);

                            //envío del paquete			
                            enviador.send(dgp);
                            System.out.println(this.getClass().getName()+" -> Solicitud enviada...");
                            
                            sonido = false;
                            mainTheme.stop();
                            jButton_sonido.setIcon(new ImageIcon(getClass().getResource("/imagenes/sonidoDesactivado.png")));
                          
                        } catch (SocketException ex) {
                            Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnknownHostException ex) {
                            System.out.println(this.getClass().getName()+" -> No se puede enviar al IP: "+IP_peer);
                            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                        }  
                    } 
                     // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                }
            }
        });
        sonido = true;
        mainTheme = java.applet.Applet.newAudioClip(getClass().getResource("/sonidos/BARCO.wav"));
        mainTheme.loop();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList_peers = new javax.swing.JList<>();
        jLabel_titulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton_sonido = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel_background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList_peers.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jList_peers.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Cargando..." };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList_peers);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 190, 130, 140));

        jLabel_titulo.setFont(new java.awt.Font("Magneto", 1, 36)); // NOI18N
        jLabel_titulo.setForeground(new java.awt.Color(0, 0, 204));
        jLabel_titulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo.png"))); // NOI18N
        getContentPane().add(jLabel_titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 350, 41));

        jLabel1.setFont(new java.awt.Font("Lucida Fax", 1, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Version: 0.1a");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Lucida Fax", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 0));
        jLabel2.setText("JUGADORES");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, 80, -1));

        jLabel3.setFont(new java.awt.Font("Lucida Fax", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("By DistroTeam");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        jButton_sonido.setBackground(new java.awt.Color(0, 0, 0));
        jButton_sonido.setForeground(new java.awt.Color(0, 0, 0));
        jButton_sonido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sonidoActivado.png"))); // NOI18N
        jButton_sonido.setFocusPainted(false);
        jButton_sonido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sonidoActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_sonido, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 50, 50));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ardilla.png"))); // NOI18N
        jLabel5.setText("jLabel5");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 30, 30));

        jLabel_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/main.png"))); // NOI18N
        getContentPane().add(jLabel_background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 340));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_sonidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_sonidoActionPerformed
        if(sonido == true){
            sonido = false;
            mainTheme.stop();
            jButton_sonido.setIcon(new ImageIcon(getClass().getResource("/imagenes/sonidoDesactivado.png")));
        } else {
            sonido = true;
            mainTheme.loop();
            jButton_sonido.setIcon(new ImageIcon(getClass().getResource("/imagenes/sonidoActivado.png")));
        }
    }//GEN-LAST:event_jButton_sonidoActionPerformed

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_sonido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel_background;
    private javax.swing.JLabel jLabel_titulo;
    private javax.swing.JList<String> jList_peers;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            
            try {
                model.clear();
                for(int i = 0; i<peerList.size(); i++){
                    model.addElement(peerList.get(i).getIP()); 
                }
                jList_peers.setModel(model);
                
                Thread.sleep(5000);

            } catch (InterruptedException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

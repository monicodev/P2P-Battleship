package ventanasSwing;

import conexiones.Server;
import conexiones.ServerBroadcast;
import java.applet.AudioClip;
import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameWindow extends javax.swing.JFrame {
    public ArrayList<Celda> TARGETS = new ArrayList<>();
    public boolean running = false;
    public Tablero enemyBoard, playerBoard;
    private int shipsToPlace = 5;
    private int shipsSize = 5;
    private Random random = new Random();
    private String enemy_IP;
    private Server server;
    public AudioClip mainTheme;
    private boolean sonido;
    
    public GameWindow(String enemy_IP, Server server) {
        this.server = server;
        this.enemy_IP = enemy_IP;
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        jLabel_enemy_IP.setText("IP enemigo: "+this.enemy_IP);
        
        playerBoard = new Tablero(false, 
            event -> {
                if (running) return;
                Celda celda = (Celda) event.getSource();
                playerBoard.placeShip(
                        new Barco(
                                shipsToPlace, 
                                shipsSize, 
                                jRadioButton1.isSelected(), 
                                celda.x, 
                                celda.y)
                );
            }
        );
       
        enemyBoard = new Tablero(true, 
            event -> {
            if (!running) return;
            
            Celda celda = (Celda) event.getSource();
            if (celda.wasShot) return;
            
            if (!celda.wasTag){
                if (TARGETS.size() < playerBoard.listaDeBarcos.size()) {
                    TARGETS.add(celda);
                    celda.wasTag = true;
                    celda.setBackground(Color.GREEN);
                }
            } else {
                TARGETS.remove(celda);
                celda.wasTag = false;
                celda.setBackground(Color.BLUE);
            }
        });
        
        jPanel_player_board.add(playerBoard);
        jPanel_enemy_board.add(enemyBoard);
        
        sonido = true;
        mainTheme = java.applet.Applet.newAudioClip(getClass().getResource("/sonidos/batalla.wav"));
    }
    
    public GameWindow() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        playerBoard = new Tablero(false, 
            event -> {
                if (running) return;
                Celda celda = (Celda) event.getSource();
                playerBoard.placeShip(
                        new Barco(
                                shipsToPlace, 
                                shipsSize, 
                                jRadioButton1.isSelected(), 
                                celda.x, 
                                celda.y)
                );
            }
        );
       
        enemyBoard = new Tablero(true, event -> {
            if (!running)
                return;
            
            Celda celda = (Celda) event.getSource();
            if (celda.wasShot)
                return;
            
            if (!celda.wasTag){
                if (TARGETS.size() < playerBoard.listaDeBarcos.size()) {
                    TARGETS.add(celda);
                    celda.wasTag = true;
                    celda.setBackground(Color.GREEN);
                }
            } else {
                TARGETS.remove(celda);
                celda.wasTag = false;
                celda.setBackground(Color.BLUE);
            }
        });
        
        jPanel_player_board.add(playerBoard);
        jPanel_enemy_board.add(enemyBoard);
        
        sonido = true;
        mainTheme = java.applet.Applet.newAudioClip(getClass().getResource("/sonidos/batalla.wav"));
    }
    
    public void dispose() {
        super.dispose();
        mainTheme.stop();
    }
    
    public void setVisible(boolean bool){
        super.setVisible(bool);
        mainTheme.stop();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        orientacion = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jButton_clear = new javax.swing.JButton();
        jButton_shoot = new javax.swing.JButton();
        jButton_barco_Acorazado = new javax.swing.JButton();
        jButton_barco_Crucero = new javax.swing.JButton();
        jButton_barco_Submarino = new javax.swing.JButton();
        jButton_barco_Destructor = new javax.swing.JButton();
        jButton_reset = new javax.swing.JButton();
        jPanel_enemy_board = new javax.swing.JPanel();
        jButton_empezar = new javax.swing.JButton();
        jPanel_player_board = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton_barco_pAviones = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel_enemy_IP = new javax.swing.JLabel();
        jLabel_waiting_message = new javax.swing.JLabel();
        jButton_sonido = new javax.swing.JButton();
        jLabel_turno = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(570, 750));
        setResizable(false);
        setSize(new java.awt.Dimension(570, 750));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton_clear.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jButton_clear.setText("CLEAR");
        jButton_clear.setFocusPainted(false);
        jButton_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 130, -1));

        jButton_shoot.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jButton_shoot.setText("SHOOT");
        jButton_shoot.setFocusPainted(false);
        jButton_shoot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_shootActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_shoot, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 130, -1));

        jButton_barco_Acorazado.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jButton_barco_Acorazado.setText("Acorazado");
        jButton_barco_Acorazado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_barco_AcorazadoActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_barco_Acorazado, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 460, 130, -1));

        jButton_barco_Crucero.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jButton_barco_Crucero.setText("Crucero");
        jButton_barco_Crucero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_barco_CruceroActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_barco_Crucero, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 490, 130, -1));

        jButton_barco_Submarino.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jButton_barco_Submarino.setText("Submarino");
        jButton_barco_Submarino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_barco_SubmarinoActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_barco_Submarino, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 520, 130, -1));

        jButton_barco_Destructor.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jButton_barco_Destructor.setText("Destructor");
        jButton_barco_Destructor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_barco_DestructorActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_barco_Destructor, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 550, 130, -1));

        jButton_reset.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jButton_reset.setText("RESET");
        jButton_reset.setFocusPainted(false);
        jButton_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_resetActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 656, 110, -1));

        jPanel_enemy_board.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_enemy_board.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_enemy_board.setLayout(new javax.swing.BoxLayout(jPanel_enemy_board, javax.swing.BoxLayout.X_AXIS));
        getContentPane().add(jPanel_enemy_board, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 33, 320, 291));

        jButton_empezar.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jButton_empezar.setText("EMPEZAR");
        jButton_empezar.setFocusPainted(false);
        jButton_empezar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_empezarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_empezar, new org.netbeans.lib.awtextra.AbsoluteConstraints(229, 656, 110, -1));

        jPanel_player_board.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_player_board.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_player_board.setLayout(new javax.swing.BoxLayout(jPanel_player_board, javax.swing.BoxLayout.X_AXIS));
        getContentPane().add(jPanel_player_board, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 347, 320, 291));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 336, 554, 11));

        jButton_barco_pAviones.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jButton_barco_pAviones.setText("Porta-aviones");
        jButton_barco_pAviones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_barco_pAvionesActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_barco_pAviones, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 430, 130, -1));

        orientacion.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Vertical");
        jRadioButton1.setFocusPainted(false);
        getContentPane().add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(428, 347, 89, -1));

        orientacion.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("DialogInput", 1, 12)); // NOI18N
        jRadioButton2.setText("Horizontal");
        jRadioButton2.setFocusPainted(false);
        getContentPane().add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(428, 378, -1, -1));

        jLabel_enemy_IP.setText("IP enemigo: 255.255.255.255");
        getContentPane().add(jLabel_enemy_IP, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        jLabel_waiting_message.setForeground(new java.awt.Color(255, 0, 0));
        jLabel_waiting_message.setText("     ");
        getContentPane().add(jLabel_waiting_message, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 670, -1, -1));

        jButton_sonido.setBackground(new java.awt.Color(0, 0, 0));
        jButton_sonido.setForeground(new java.awt.Color(0, 0, 0));
        jButton_sonido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sonidoActivado.png"))); // NOI18N
        jButton_sonido.setFocusPainted(false);
        jButton_sonido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sonidoActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_sonido, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 50, 50));
        getContentPane().add(jLabel_turno, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_barco_pAvionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_barco_pAvionesActionPerformed
        if (running) return;
        System.out.println("preparando el Porta-aviones");
        shipsToPlace = 5;
        shipsSize = 5;
    }//GEN-LAST:event_jButton_barco_pAvionesActionPerformed

    private void jButton_barco_AcorazadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_barco_AcorazadoActionPerformed
        if (running) return;
        System.out.println("preparando el Acorazado ");
        shipsToPlace = 4;
        shipsSize = 4;
    }//GEN-LAST:event_jButton_barco_AcorazadoActionPerformed

    private void jButton_barco_CruceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_barco_CruceroActionPerformed
        if (running) return;
        System.out.println("preparando el Crucero ");
        shipsToPlace=3;
        shipsSize = 3;
    }//GEN-LAST:event_jButton_barco_CruceroActionPerformed

    private void jButton_barco_SubmarinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_barco_SubmarinoActionPerformed
        if (running) return;
        System.out.println("preparando el Submarino ");
        shipsToPlace = 2;
        shipsSize = 3;
    }//GEN-LAST:event_jButton_barco_SubmarinoActionPerformed

    private void jButton_barco_DestructorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_barco_DestructorActionPerformed
        if (running) return;
        System.out.println("preparando el Destructor ");
        shipsToPlace = 1;
        shipsSize = 2;
    }//GEN-LAST:event_jButton_barco_DestructorActionPerformed

    private void jButton_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearActionPerformed
        if (!running) return;
        for (int i=0;i<TARGETS.size();i++){
            TARGETS.get(i).wasTag = false;
            TARGETS.get(i).setBackground(Color.BLUE);
        }
        TARGETS.clear();
    }//GEN-LAST:event_jButton_clearActionPerformed

    private void jButton_shootActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_shootActionPerformed
        if (!running) return;
                
        if (TARGETS.size() == playerBoard.listaDeBarcos.size()){
            
            try{
                DatagramSocket enviador = new DatagramSocket();
                
                String mensaje = "ATAQUE";
                for(Celda celda : TARGETS){
                    mensaje = mensaje +":"+ celda.x +"/"+ celda.y;
                }
                // El dato a enviar, como array de bytes.
                byte[] B_mensaje = mensaje.getBytes();
                int L_mensaje = mensaje.length();
                // El destinatario es IP_server y se hace el envío por el PORT
                DatagramPacket dpg_respuesta = new DatagramPacket(B_mensaje, L_mensaje, InetAddress.getByName(this.enemy_IP), 3333);
                //envío del paquete			
                enviador.send(dpg_respuesta);
                System.out.println(this.getClass().getName()+" -> Atacando a: "+this.enemy_IP);
                
                jLabel_waiting_message.setText(null);
                pintarMisDisparos();
                setTurno(false);                  
                
            } catch (SocketException ex) {
                        Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                        System.out.println(this.getClass().getName()+" -> [ERROR] No se puede enviar al IP: "+this.enemy_IP);
                        //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                        Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
            } 
        } else {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Seguro que no quiere utilizar todos los disparos", "Confirmacion", dialogButton);
            if(dialogResult == 0) {
                try{
                    DatagramSocket enviador = new DatagramSocket();
                    String mensaje = "ATAQUE";
                    for(Celda celda : TARGETS){
                        mensaje = mensaje +":"+ celda.x +"/"+ celda.y;
                    }
                    // El dato a enviar, como array de bytes.
                    byte[] B_mensaje = mensaje.getBytes();
                    int L_mensaje = mensaje.length();
                    // El destinatario es IP_server y se hace el envío por el PORT
                    DatagramPacket dpg_respuesta = new DatagramPacket(B_mensaje, L_mensaje, InetAddress.getByName(this.enemy_IP), 3333);
                    //envío del paquete			
                    enviador.send(dpg_respuesta);
                    System.out.println(this.getClass().getName()+" -> Atacando a: "+this.enemy_IP);
                    pintarMisDisparos();
                    jLabel_waiting_message.setText(null);
                    setTurno(false);                    
                } catch (SocketException ex) {
                            Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnknownHostException ex) {
                            System.out.println(this.getClass().getName()+" -> [ERROR] No se puede enviar al IP: "+this.enemy_IP);
                } catch (IOException ex) {
                            Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
    }//GEN-LAST:event_jButton_shootActionPerformed

    private void jButton_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_resetActionPerformed
        if (running) return;
        System.out.println("Resetenado estrategia");
        playerBoard.ResetBoard();
    }//GEN-LAST:event_jButton_resetActionPerformed

    private void jButton_empezarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_empezarActionPerformed
        if (running) return;
        if (playerBoard.listaDeBarcos.size() == 5){
            if(this.server.otherPlayerIsReady == false){
                try{
                    DatagramSocket enviador = new DatagramSocket();
                    String mensaje = "ESPERANDO";
                    for(Barco barco: playerBoard.listaDeBarcos){
                        mensaje = mensaje +":"+barco.id+"/"+barco.size+"/"+barco.vertical+"/"+barco.x+"/"+barco.y;
                    }
                    // El dato a enviar, como array de bytes.
                    byte[] B_mensaje = mensaje.getBytes();
                    int L_mensaje = mensaje.length();
                    // El destinatario es IP_server y se hace el envío por el PORT
                    DatagramPacket dpg_respuesta = new DatagramPacket(B_mensaje, L_mensaje, InetAddress.getByName(this.enemy_IP), 3333);
                    //envío del paquete			
                    enviador.send(dpg_respuesta);
                    System.out.println(this.getClass().getName()+" -> Confirmando solicitud..."+" AL "+this.enemy_IP);
                         
                    jLabel_waiting_message.setText("Esperando por el otro jugador...");
                    jButton_empezar.setEnabled(false);
                    jButton_reset.setEnabled(false);
                    jRadioButton1.setEnabled(false);
                    jRadioButton2.setEnabled(false);
                    jButton_barco_pAviones.setEnabled(false);
                    jButton_barco_Crucero.setEnabled(false);
                    jButton_barco_Destructor.setEnabled(false);
                    jButton_barco_Submarino.setEnabled(false);
                    jButton_barco_Acorazado.setEnabled(false);
                } catch (SocketException ex) {
                        Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnknownHostException ex) {
                        System.out.println(this.getClass().getName()+" -> [ERROR] No se puede enviar al IP: "+this.enemy_IP);
                        //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                        Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                } 
            } else {

                try{
                    DatagramSocket enviador = new DatagramSocket();
                    // El dato a enviar, como array de bytes.
                    String mensaje = "LISTO";
                    
                    for(Barco barco: playerBoard.listaDeBarcos){
                        mensaje = mensaje +":"+barco.id+"/"+barco.size+"/"+barco.vertical+"/"+barco.x+"/"+barco.y;
                    }
                    
                    byte[] B_mensaje = mensaje.getBytes();
                    int L_mensaje = mensaje.length();
                    // El destinatario es IP_server y se hace el envío por el PORT
                    DatagramPacket dpg_respuesta = new DatagramPacket(B_mensaje, L_mensaje, InetAddress.getByName(this.enemy_IP), 3333);
                    //envío del paquete			
                    enviador.send(dpg_respuesta);
                    System.out.println(this.getClass().getName()+" -> Confirmando solicitud..."+" AL "+this.enemy_IP);
                    jButton_empezar.setEnabled(false);
                    jButton_reset.setEnabled(false);  
                             
                } catch (SocketException ex) {
                        Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnknownHostException ex) {
                        System.out.println(this.getClass().getName()+" -> [ERROR] No se puede enviar al IP: "+this.enemy_IP);
                        //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                        Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: No tiene suficientes barcos para la batalla"); 
        }
    }//GEN-LAST:event_jButton_empezarActionPerformed

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

    public void pintarMisDisparos(){
        while (!TARGETS.isEmpty()){
            if (TARGETS.size()>0){
                TARGETS.get(0).shoot(enemyBoard);
                TARGETS.remove(0);
            }
            
            if (enemyBoard.listaDeBarcos.isEmpty() && playerBoard.listaDeBarcos.isEmpty()) {
                jLabel_turno.setText(null);
                JOptionPane.showMessageDialog(null, "EMPATE!");
                System.out.println("EMPATE");
            }
            
            if (enemyBoard.listaDeBarcos.isEmpty()) {
                jLabel_turno.setText(null);
                JOptionPane.showMessageDialog(null, "GANASTE!");
                
                System.out.println("YOU WIN");
            }
            
            if (playerBoard.listaDeBarcos.isEmpty()) {
                jLabel_turno.setText(null);
                JOptionPane.showMessageDialog(null, "PERDISTE");
                System.out.println("YOU LOSE");
            }
        }
    }
    
    public void setTurno(boolean turno){
        if(turno){
            jButton_clear.setEnabled(true);
            jButton_shoot.setEnabled(true);
            jLabel_turno.setText("Turno: TU");  
            jLabel_turno.setForeground(Color.GREEN);
        } else {
            jButton_clear.setEnabled(false);
            jButton_shoot.setEnabled(false);
            jLabel_turno.setText("Turno: ENEMIGO");  
            jLabel_turno.setForeground(Color.RED);
        }
    }

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
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton_barco_Acorazado;
    public javax.swing.JButton jButton_barco_Crucero;
    public javax.swing.JButton jButton_barco_Destructor;
    public javax.swing.JButton jButton_barco_Submarino;
    public javax.swing.JButton jButton_barco_pAviones;
    public javax.swing.JButton jButton_clear;
    public javax.swing.JButton jButton_empezar;
    public javax.swing.JButton jButton_reset;
    public javax.swing.JButton jButton_shoot;
    private javax.swing.JButton jButton_sonido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_enemy_IP;
    public javax.swing.JLabel jLabel_turno;
    public javax.swing.JLabel jLabel_waiting_message;
    private javax.swing.JPanel jPanel_enemy_board;
    private javax.swing.JPanel jPanel_player_board;
    public javax.swing.JRadioButton jRadioButton1;
    public javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.ButtonGroup orientacion;
    // End of variables declaration//GEN-END:variables
}

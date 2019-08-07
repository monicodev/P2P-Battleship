package conexiones;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ventanasSwing.Barco;
import ventanasSwing.Celda;
import ventanasSwing.GameWindow;

public class Server extends Thread{
    
    public boolean otherPlayerIsReady = false;
    private Integer PORT;
    private ServerBroadcast broadcast;
    private Thread T_broadcast;
    private PeerList peerList;
    private GameWindow ventanaJuego;
    private boolean esEstrategia = false;
    
    public Server() {
        this.broadcast = new ServerBroadcast();
        this.T_broadcast = new Thread(broadcast);
        this.PORT = 3333; //PUERTO PREDETERMINADOR PARA LA APLICACION
        peerList = new PeerList();
    }

    //HILO
    @Override
    public void run() {
        
        try {
            // Puerto por el que queremos escuchar 3333
            DatagramSocket escucha = new DatagramSocket(this.PORT);
            System.out.println(this.getClass().getName()+" -> Servidor iniciado");
            T_broadcast.start(); //SERVIDOR HACE UN BROADCAST PARA AVISAR A LOS PEERS
            
            // Un array de bytes lo suficientemente grande para contener
            // cualquier dato que podamos recibir.
            byte [] dato = new byte[1024]; //1024 bytes son mas que suficiente para recibir datos basicos
            DatagramPacket dgp = new DatagramPacket(dato, dato.length);
            
            // La llamada se queda bloqueada hasta que recibamos algún
            // mensaje por ese puerto 3333
            while(true){
                peerList.imprimirLista();
                escucha.receive(dgp);
                
                // En datos obtenemos lo que acabamos de recibir. 
                String S_datos = new String(dgp.getData(),0, dgp.getLength());   //NOTA ESTA LINEA HACE FLUSH (UDP NO TIENE METODO FLUSH COMO TAL)
                System.out.println(this.getClass().getName()+" -> "+dgp.getAddress()+" dice: "+S_datos); 
                
                if(S_datos.contains("ADV")) { //ES UN MENSAJE DE PUBLICACION, SE AGREGA A LA LISTA DE VECINOS
                    System.out.println(this.getClass().getName()+" -> "+dgp.getAddress()+" es vecino");
                    peerList.añadir(dgp.getAddress().toString().split("/")[1]);
                    peerList.getPeer(dgp.getAddress().toString().split("/")[1]).setEstado(false);                       
                } 
                
                if(S_datos.contains("NEW")) { //UN JUGADOR ESTA INVITANDO A UNA NUEVA PARTIDA
                    String remote_IP = dgp.getAddress().toString().split("/")[1];
                    Integer PORT = 3333;
                    try {     
                        DatagramSocket enviador = new DatagramSocket();
                        // El dato a enviar, como array de bytes.
                        String mensaje = "OK";
                        byte[] B_mensaje = mensaje.getBytes();
                        int L_mensaje = mensaje.length();
                        // El destinatario es IP_server y se hace el envío por el PORT
                        DatagramPacket dpg_respuesta = new DatagramPacket(B_mensaje, L_mensaje, InetAddress.getByName(remote_IP), PORT);
                        //envío del paquete			
                        enviador.send(dpg_respuesta);
                        System.out.println(this.getClass().getName()+" -> Confirmando solicitud..."+" AL "+remote_IP);
                        //######################################################
                        //AQUI SE ABRE LA INTERFAZ GRAFICA PARA JUGAR, ES DECIR UNA NUEVA VENTANA POR QUE YA LE CONFIRME AL OTRO PEER QUE JUGARE
                        iniciarVentanaJuego(remote_IP);     
                        //######################################################
                    } catch (SocketException ex) {
                        Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnknownHostException ex) {
                        System.out.println(this.getClass().getName()+" -> [ERROR] No se puede enviar al IP: "+remote_IP);
                        //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
                
                if(S_datos.contains("OK")){ //NUEVO JUEGO CON UN PEER, INICIAR VENTANAS
                    try{
                        //RECIBI CONFIRMACION DE MI SOLICITUD, ABRO MI INTERFAZ GRAFICA PARA JUGAR, ES DECIR UNA NUEVA VENTANA
                        String remote_IP = dgp.getAddress().toString().split("/")[1];
                        iniciarVentanaJuego(remote_IP);
                    } catch (Exception e) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
                    }  
                }
                
                if(S_datos.contains("ESPERANDO")){ //EL OTRO JUGADOR ESTA LISTO
                    obtenerEstrategiaEnemiga(S_datos);
                    otherPlayerIsReady = true;
                    ventanaJuego.jLabel_waiting_message.setText("Jugador esperando...");
                }
                
                if(S_datos.contains("LISTO")){ //AMBOS ESTAMOS LISTO Y EMPEZAMOS A JUGAR
                    obtenerEstrategiaEnemiga(S_datos);
                    String remote_IP = dgp.getAddress().toString().split("/")[1];
                    Integer PORT = 3333;
                    try {                 
                        DatagramSocket enviador = new DatagramSocket();
                        // El dato a enviar, como array de bytes.
                        String mensaje = "JUGUEMOS";
                        byte[] B_mensaje = mensaje.getBytes();
                        int L_mensaje = mensaje.length();
                        // El destinatario es IP_server y se hace el envío por el PORT
                        DatagramPacket dpg_respuesta = new DatagramPacket(B_mensaje, L_mensaje, InetAddress.getByName(remote_IP), PORT);
                        //envío del paquete			
                        enviador.send(dpg_respuesta);
                        System.out.println(this.getClass().getName()+" -> Empezando juego con; "+remote_IP);
                    } catch (SocketException ex) {
                        Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnknownHostException ex) {
                        System.out.println(this.getClass().getName()+" -> [ERROR] No se puede enviar al IP: "+remote_IP);
                        //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    startGameAnnounce(false); //EMPEZAMOS EL JUEGO
                }
                
                if(S_datos.contains("JUGUEMOS")){
                    startGameAnnounce(true); //EMPEZAMOS EL JUEGO
                }
                
                if(S_datos.contains("ATAQUE")){
                    obtenerJugadaEnemiga(S_datos);
                    setTurno(true);
                }
            }
        } catch (IOException ex) {
            System.out.println(this.getClass().getName()+" -> el puerto "+this.PORT+" ya esta en uso");
            //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obtenerJugadaEnemiga(String datos){
        String[] disparoEnemigo = datos.split(":");
        ArrayList<Celda> TARGETSENEMY = new ArrayList<>();
        
        for(int i = 1; i<disparoEnemigo.length;i++){
            String[] celdas = disparoEnemigo[i].split("/");
            System.out.println("Celda: "+Arrays.toString(celdas));
            Celda celda = ventanaJuego.playerBoard.getCell(Integer.valueOf(celdas[0]),Integer.valueOf(celdas[1]));
            if (!celda.wasShot) TARGETSENEMY.add(celda);
        }
        
        while (!TARGETSENEMY.isEmpty()){
            if (TARGETSENEMY.size() > 0){
                TARGETSENEMY.get(0).shoot(ventanaJuego.playerBoard);
                TARGETSENEMY.remove(0);
            }
            if (ventanaJuego.enemyBoard.listaDeBarcos.isEmpty() && ventanaJuego.playerBoard.listaDeBarcos.isEmpty()) {
                ventanaJuego.jLabel_turno.setText(null);
                JOptionPane.showMessageDialog(null, "EMPATE!");
                System.out.println("EMPATE");
             }
            if (ventanaJuego.enemyBoard.listaDeBarcos.isEmpty()) {
                ventanaJuego.jLabel_turno.setText(null);
                JOptionPane.showMessageDialog(null, "GANASTE!");
                System.out.println("YOU WIN");
            }
            if (ventanaJuego.playerBoard.listaDeBarcos.isEmpty()) {
                ventanaJuego.jLabel_turno.setText(null);
                JOptionPane.showMessageDialog(null, "PERDISTE");
                System.out.println("YOU LOSE");
            }
        }
        setTurno(true);
    }
    
    public void obtenerEstrategiaEnemiga(String datos){
        String[] estrategiaEnemigo = datos.split(":");
        for(int i = 1; i<estrategiaEnemigo.length; i++){
            String[] barco = estrategiaEnemigo[i].split("/"); 
            ventanaJuego.enemyBoard.placeShip(
                new Barco(
                    Integer.parseInt(barco[0]),
                    Integer.parseInt(barco[1]),
                    Boolean.valueOf(barco[2]),
                    Integer.parseInt(barco[3]),
                    Integer.parseInt(barco[4])
                )
            );
        }
    }
    
    public void startGameAnnounce(boolean primero){
        otherPlayerIsReady = false;
        ventanaJuego.jLabel_waiting_message.setText(null);
        ventanaJuego.jButton_empezar.setEnabled(false);
        ventanaJuego.jButton_reset.setEnabled(false);
        ventanaJuego.jRadioButton1.setEnabled(false);
        ventanaJuego.jRadioButton2.setEnabled(false);
        ventanaJuego.jButton_barco_pAviones.setEnabled(false);
        ventanaJuego.jButton_barco_Crucero.setEnabled(false);
        ventanaJuego.jButton_barco_Destructor.setEnabled(false);
        ventanaJuego.jButton_barco_Submarino.setEnabled(false);
        ventanaJuego.jButton_barco_Acorazado.setEnabled(false);
        ventanaJuego.running = true; 
        System.out.println(this.getClass().getName()+" -> EL JUEGO EMPIEZA!");
        ventanaJuego.jLabel_waiting_message.setText(null);
        setTurno(primero);
    }
    
    public void setTurno(boolean turno){
        if(turno){
            ventanaJuego.jButton_clear.setEnabled(true);
            ventanaJuego.jButton_shoot.setEnabled(true);
            ventanaJuego.jLabel_turno.setText("Turno: TU");  
            ventanaJuego.jLabel_turno.setForeground(Color.GREEN);
        } else {
            ventanaJuego.jButton_clear.setEnabled(false);
            ventanaJuego.jButton_shoot.setEnabled(false);
            ventanaJuego.jLabel_turno.setText("Turno: ENEMIGO");  
            ventanaJuego.jLabel_turno.setForeground(Color.RED);
        }
    }
    
    public void iniciarVentanaJuego(String remote_IP){
        System.out.println(this.getClass().getName()+" -> Creando nuevo juego...");
        ventanaJuego = new GameWindow(remote_IP,this);
        ventanaJuego.setTitle("Tablero");
        ventanaJuego.setVisible(true);
        ventanaJuego.jButton_clear.setEnabled(false);
        ventanaJuego.jButton_shoot.setEnabled(false);
        ventanaJuego.mainTheme.loop();
    }
    
    public PeerList getPeerList(){ 
        return this.peerList; 
    }
}

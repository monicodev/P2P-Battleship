package conexiones;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerBroadcast extends Thread{
    
    String IP_broadcast;
    Integer PORT;
    
    public ServerBroadcast() {
        this.IP_broadcast = "255.255.255.255";  //DIRECCION DE BROADCAST LOCAL PREDETERMINADO
        this.PORT = 3333;                     //PUERTO PREDETERMINADOR
    }

    @Override
    public void run() {
        try {     
            System.out.println(this.getClass().getName()+" -> Avisando a los peers...");
            DatagramSocket enviador = new DatagramSocket();
            enviador.setBroadcast(true);
            String mensaje = "ADV";
            // El dato a enviar, como array de bytes.
            byte[] B_mensaje = mensaje.getBytes();
            int L_mensaje = mensaje.length();
            // El destinatario es IP_broadcast y se hace el envío por el PORT
            DatagramPacket dgp = new DatagramPacket(B_mensaje, L_mensaje, InetAddress.getByName(this.IP_broadcast), this.PORT);
            while(true){
                //envío del paquete			
                enviador.send(dgp);
                System.out.println(this.getClass().getName()+" broadcast enviado...");
                sleep(5000);
            }
        } catch (SocketException ex) {
            Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            System.out.println(this.getClass().getName()+" -> [ERROR] No se puede enviar al IP: "+this.IP_broadcast);
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
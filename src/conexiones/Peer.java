package conexiones;
public class Peer {
    
    private String IP;
    private boolean estado; //Si esta jugando (true) o no esta jugando (false)

    public Peer(String IP) {
        this.IP = IP;
        this.estado = false;
    }
    
    public String getIP(){ return this.IP; }
    
    public void setEstado(boolean estado){ this.estado = estado; }
    public boolean getEstado(){ return this.estado; }
}

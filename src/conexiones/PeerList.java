package conexiones;
import java.util.ArrayList;

public class PeerList  {
    
    private static ArrayList<Peer> peerList;
    
    public PeerList(){
        peerList = new ArrayList<Peer>();
    }
    
    //Añade un peer a la lista
    public boolean añadir(String IP){
        if(!existePeer(IP)){
            peerList.add(new Peer(IP));
        }    
        return false;
    }
    
    //Borra un peer de la lista dado su IP
    public boolean borrar(String IP){
        for(int i = 0; i<peerList.size(); i++){
            if(peerList.get(i).getIP().equals(IP)){
                peerList.remove(i);
                return true;
            }
        }
        return false;
    }
    
    //Retorna un peer dato su IP
    public Peer getPeer(String IP){
        for(Peer peer: peerList){
            if(peer.getIP().equals(IP)) return peer;
        }
        return null;
    }
    
    //Retorna el ArrayList de peer
    public ArrayList<Peer> getList(){
        return this.peerList;
    }
    
    //Verifica si ya existe un peer en la lista dado su IP
    private boolean existePeer(String IP){
        for(Peer peer: peerList){
            if(peer.getIP().equals(IP)) return true;
        }
        return false;
    }
    
    //Imprime la lista de peers conectados por consola
    public void imprimirLista(){
        System.out.println("-------------------------------------");
        System.out.println("LISTA DE PEERS CONECTADOS:");
        if(peerList.isEmpty()){
            System.out.println("No hay peers conectados");
            return;
        }
        for(Peer peer: peerList){
            System.out.println("(@) "+peer.getIP()+" / Jugando: "+peer.getEstado());
        }
        System.out.println("-------------------------------------");
    }
}

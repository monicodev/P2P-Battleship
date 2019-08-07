package ventanasSwing;

import java.awt.Color;
import javax.swing.JButton;

public class Celda extends JButton {
    public int x, y;
    public Barco barco = null;
    public boolean wasShot = false;
    public boolean wasTag = false;

    public Celda(int x, int y) {
        this.setSize(30,30);
        this.x = x;
        this.y = y;
        setBorderPainted(false);
        setBackground(Color.BLUE);
        setFocusPainted(false);
        setText(" ");
    }
  
    public boolean shoot(Tablero flota) {
        wasShot = true;
        setBackground(Color.BLACK);
        
        if (barco != null) {
            barco.hit();
            setBackground(Color.YELLOW);
            if (!barco.isAlive()) {
                flota.killShip(barco);
                flota.listaDeBarcos.remove(barco);
            }
            return true;
        }
        return false;
    }
}

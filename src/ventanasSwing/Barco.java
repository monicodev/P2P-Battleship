package ventanasSwing;

import javax.swing.JPanel;

public class Barco extends JPanel {
    public int id;
    public int size;
    public boolean vertical = true;
    private int health;
    public int x;
    public int y;
    
    public Barco(int id,int size, boolean vertical, int positionX, int positionY) {
        this.id = id;
        this.size = size;
        this.vertical = vertical;
        this.x = positionX;
        this.y = positionY;
        health = size;
    }
    
    public void hit() {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }
    
}

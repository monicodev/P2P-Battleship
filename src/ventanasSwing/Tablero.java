package ventanasSwing;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Tablero extends JPanel {
    public ArrayList<Barco> listaDeBarcos = new ArrayList<>();
    private Box rows = new Box(BoxLayout.Y_AXIS);
    private boolean enemy = false;
    
    public Tablero(boolean enemy, ActionListener handler) {
        this.setBackground(Color.BLUE);
        this.enemy = enemy;
        for (int y = 0; y < 10; y++) {
            Box row = new Box(BoxLayout.X_AXIS);
            for (int x = 0; x < 10; x++) {
                Celda c = new Celda(x, y);
                c.addActionListener(handler);
                row.add(c);
            }
            rows.add(row);
        }
        this.add(rows);
    }
    
    public boolean placeShip(Barco barco) {
        chechShip(barco);
        if (canPlaceShip(barco, barco.x, barco.y)) {
            int length = barco.size;
            if (barco.vertical) {
                for (int i = barco.y; i < barco.y + length; i++) {
                    Celda celda = getCell(barco.x, i);
                    celda.barco = barco;
                    if (!enemy) {
                        celda.setBackground(Color.GRAY);
                    }
                }
            }
            else {
                for (int i = barco.x; i < barco.x + length; i++) {
                    Celda celda = getCell(i, barco.y);
                    celda.barco = barco;
                    if (!enemy) {
                        celda.setBackground(Color.GRAY);
                    }
                }
            }
            listaDeBarcos.add(barco);
            return true;
        }
        return false;
    }
    
    public void ResetBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Celda celda = getCell(i, j);
                celda.barco = null;
                celda.setBackground(Color.BLUE);
            }
        }
        listaDeBarcos.clear();
    }
    
    public void chechShip(Barco barco) {
        for (int i = 0; i < listaDeBarcos.size(); i++){
            if (listaDeBarcos.get(i).id == barco.id){
                removeShip(listaDeBarcos.get(i));
                listaDeBarcos.remove(i);
                break;
            }
        }
    }
    
    public void removeShip(Barco barco) {
        if (barco.vertical) {
                for (int i = barco.y; i < barco.y + barco.size; i++) {
                    Celda celda = getCell(barco.x, i);
                    celda.barco = null;
                    if (!enemy) {
                        celda.setBackground(Color.BLUE);
                    }
                }
            }
            else {
                for (int i = barco.x; i < barco.x + barco.size; i++) {
                    Celda celda = getCell(i, barco.y);
                    celda.barco = null;
                    if (!enemy) {
                        celda.setBackground(Color.BLUE);
                    }
                }
            }
    }

    public void killShip(Barco barco) {
        if (barco.vertical) {
                for (int i = barco.y; i < barco.y + barco.size; i++) {
                    Celda celda = getCell(barco.x, i);
                        celda.setBackground(Color.RED);
                }
            }
            else {
                for (int i = barco.x; i < barco.x + barco.size; i++) {
                    Celda celda = getCell(i, barco.y);
                        celda.setBackground(Color.RED);
                }
            }
    }
    
    
    public Celda getCell(int x, int y) {
        return (Celda)((Box) rows.getComponent(y)).getComponent(x);
    }

    private boolean canPlaceShip(Barco barco, int x, int y) {
        int length = barco.size;

        if (barco.vertical) {
            for (int i = y; i < y + length; i++) {
                if (!isValidPoint(x, i))
                    return false;

                Celda celda = getCell(x, i);
                if (celda.barco != null)
                    return false;
            }
        }
        else {
            for (int i = x; i < x + length; i++) {
                if (!isValidPoint(i, y))
                    return false;

                Celda celda = getCell(i, y);
                if (celda.barco != null)
                    return false;

            }
        }
        return true;
    }

    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }
}
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class Shooter extends Figura {

    public final static int X_INICIAL = 600;
    public final static int Y_INICIAL = 600;
    public final static double fps = 30;
    
    public Shooter(Color color){
        super(X_INICIAL, Y_INICIAL, 600 /fps, 0);
        this.color = color;
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, 20, 40);
    }

    public void movimiento(HashSet<Integer> teclas){
        if (teclas.contains(KeyEvent.VK_LEFT) || teclas.contains(KeyEvent.VK_A)){
            x -= Vx;
            } 
        if (teclas.contains(KeyEvent.VK_RIGHT) || teclas.contains(KeyEvent.VK_D) ){
            x += Vx;
            }
    }

    public void check_bordes(int ancho_pant, int alto_pant){
        
        if (x <= 0 ){
            x = 0;
        } else if (x + 20 >= ancho_pant){
            x = ancho_pant-20;
        }
    }

}

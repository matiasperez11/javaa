package back;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class Bloque extends Figura {

    public static final double VELOCIDAD = 400 / 30;
    public static final int  HEIGHT = 20;
    public static final int WIDTH = 150;


    
    public Bloque(Color color, int ancho_pant, int alto_pant){
        super(ancho_pant/2, alto_pant-40, VELOCIDAD, 0);
        this.color = color;
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public void movimiento( HashSet<Integer> teclas){
        if (teclas.contains(KeyEvent.VK_D) || teclas.contains(KeyEvent.VK_RIGHT) ){
            x += Vx;
        } else if (teclas.contains(KeyEvent.VK_LEFT) || teclas.contains(KeyEvent.VK_A)){
            x -= Vx;
        }
    }

    public void check_bordes(int alto_pant, int ancho_pant){
        if (x < 0){
            x = 0;
        } else if (x+WIDTH >= ancho_pant){
            x = ancho_pant-WIDTH;
        }


    }
}

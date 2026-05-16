import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class Jugador extends Figura {
    
    public static final double VINICIAL_X = 300;
    public static final double VINICIAL_Y = 300;
    public int WIDTH = 50;
    public int HEIGHT = 50;
    public  final double fps = 60;


    public Jugador(int ancho_pant, int alto_pant){
        super(ancho_pant/2, alto_pant /2, VINICIAL_X, VINICIAL_Y);
        this.color = Color.BLACK;
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public void movimiento (HashSet<Integer> teclas){
        if (teclas.contains(KeyEvent.VK_A) || teclas.contains(KeyEvent.VK_LEFT)){
            x -= Vx /fps ;
        } else if (teclas.contains(KeyEvent.VK_D) || teclas.contains(KeyEvent.VK_RIGHT)){
            x += Vx /fps;
        }

        if (teclas.contains(KeyEvent.VK_W) || teclas.contains(KeyEvent.VK_UP)){
            y -= Vy /fps;
        } else if (teclas.contains(KeyEvent.VK_S) || teclas.contains(KeyEvent.VK_DOWN)){
            y += Vy /fps;
        }
    }

    public void check_bordes(int ancho_pant, int alto_pant){
        if (x <= 0){
            x = 0;
        }
        if (x  + WIDTH >= ancho_pant){
            x = ancho_pant-WIDTH;
        }
        if (y <= 0){
            y = 0;
        } 
        if (y + HEIGHT >= alto_pant){
            y = alto_pant-HEIGHT;
        }
    }

    public boolean check_comer(Moneda moneda){

        Rectangle j = new Rectangle(x, y, WIDTH, HEIGHT);
        Rectangle m = new Rectangle (moneda.x, moneda.y, Moneda.width, Moneda.height);

        if (j.intersects(m)){
            return true;
        } return false;

    }
}

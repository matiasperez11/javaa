import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class Jugador extends Figura{

    public  int width = 40;
    public  int height = 40;
    public final static  int fps = 60;

    public Jugador(int ancho_pant, int alto_pant){
        super(ancho_pant/2, alto_pant/ 2, 500 / fps, 500 / fps );
        this.color = Color.BLACK;
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    /*public void movimiento(HashSet<Integer> teclas){
        if (teclas.contains(KeyEvent.VK_A) || teclas.contains(KeyEvent.VK_LEFT)){
            x -= Vx ;
        } else if (teclas.contains(KeyEvent.VK_D) || teclas.contains(KeyEvent.VK_RIGHT)){
            x += Vx ;
        }
        if (teclas.contains(KeyEvent.VK_W) || teclas.contains(KeyEvent.VK_UP)){
            y -= Vy ;
        } else if (teclas.contains(KeyEvent.VK_S) || teclas.contains(KeyEvent.VK_DOWN)){
            y += Vy ;
        }
    }*/

    public void check_bordes( int ancho_pant, int alto_pant){

        if (x <= 0){
            x = 0;
        }
        if (x + width >= ancho_pant){
            x = ancho_pant-width;
        }
        if (y <= 0){
            y = 0;
        }
        if (y + height >= alto_pant){
            y = alto_pant-height;
        }
    }

    public boolean comer(Comida comida){
        Rectangle j = new Rectangle(x, y, width, height);
        Rectangle c = new Rectangle(comida.x, comida.y, comida.width, comida.height);

        if (j.intersects(c)){
            return true;
        } return false;
    }
}
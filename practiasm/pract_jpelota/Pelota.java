import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class Pelota extends Figura {
    
    public static int VX_INICIAL = 300;
    public static int VY_INICIAL = 300;
    public final static int ACEL = 20;
    public final static double fps = 60;

    public final static int width = 30;
    public final static int height = 30;

    public Pelota(int ancho_pant, int alto_pant){
        super(ancho_pant/2, alto_pant/2, VX_INICIAL, VY_INICIAL);
        this.color = Color.CYAN;
    }


    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

    public void movimiento(){
        x += Vx  / fps;
        y += Vy /fps;
    }


    public void acelerar(HashSet<Integer> teclas){
        
        if(teclas.contains(KeyEvent.VK_RIGHT)){
            Vx += ACEL;
        } else if (teclas.contains(KeyEvent.VK_LEFT)){
            Vx -= ACEL;
        } 

        if (teclas.contains(KeyEvent.VK_UP)){
            Vy -= ACEL;
        } else if (teclas.contains(KeyEvent.VK_DOWN)){
            Vy += ACEL;
        }
    }


    public void decelerar(){
         if(Vx>0){
            Vx -= ACEL;
        }
        if (Vx <0){
            Vx += ACEL;
        }
         if(Vy>0){
            Vy -= ACEL;
        }
        if (Vy <0){
            Vy += ACEL;
        }
    }

    public boolean check_bordes(int ancho_pant, int alto_pant){
        if(x <= 0 || x + width>= ancho_pant || y <= 0 || y + height>= alto_pant){
            return true;
        } return false;
    }
    
    public boolean check_colision_bl(Bloque bloque){
        Rectangle p = new Rectangle(x, y, width, height);
        Rectangle b = new Rectangle(bloque.x, bloque.y, Bloque.WIDTH, Bloque.HEIGHT);
        if(p.intersects(b)){
            Vx = -Vx;
            Vy = -Vy;
            return true;
        } return false;
    }
}
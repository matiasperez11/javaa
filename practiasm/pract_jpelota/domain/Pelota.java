package domain;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class Pelota extends Figura {
    
    public static final int SIZE = 30;
    public static final int X_INICIAL = 50;
    public static final int Y_INICIAL = 450;
    public static final int ACELERACION_X = 20;
    public static final int ACELERACION_Y = 20;
    public static final int DESACELERACION = 40;

    public static final double fps = 30;

    public double Vx = 0;
    public double Vy = 0;

    public Pelota(Color color){
        super(X_INICIAL, Y_INICIAL, color);     //justo cuando se crea solo quiero que tenga esto, vx vy ya los pondre luego
    }


    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, SIZE, SIZE);
    }


    public void movimiento(){
        x += Vx/fps;
        y += Vy/fps;
    }


    public void acelerar(HashSet<Integer> teclas){
        
        if(teclas.contains(KeyEvent.VK_RIGHT)){
            Vx += ACELERACION_X;
        } else if (teclas.contains(KeyEvent.VK_LEFT)){
            Vx -= ACELERACION_X;
        } 

        if (teclas.contains(KeyEvent.VK_UP)){
            Vy -= ACELERACION_Y;
        } else if (teclas.contains(KeyEvent.VK_DOWN)){
            Vy += ACELERACION_Y;
        }
    }


    public void desacelerar(){

        if(Vx>0){
            Vx = Math.max(0, Vx-DESACELERACION/fps);
        } else if (Vx<0){
            Vx = Math.min(0, Vx+DESACELERACION/fps);
        }
        //se usan dos ifs para que se pueda acceder a los dos a la vez

        if (Vy>0){
            Vy = Math.max(0, Vy-DESACELERACION/fps);
        } else if (Vy<0){
            Vy = Math.min(0, Vy+DESACELERACION/fps);
        }
    }


    public void check_colision(Bloque bloque){

        Rectangle p = new Rectangle( (int) x, (int) y, SIZE, SIZE);
        Rectangle b = new Rectangle( bloque.x, bloque.y, Bloque.WIDTH, Bloque.HEIGHT);

        if(p.intersects(b)){
            desacelerar();

            if (x >= bloque.x  &  x <= bloque.x + Bloque.HEIGHT){

                if (Vy >0 || Vy >0){
                    Vx = -Vx;
                }
            } else if (x < bloque.x || x > bloque.x + Bloque.HEIGHT) {
                
                if (Vx>0 || Vx <0){
                    Vy = -Vy;
                }
        }

            if (x >= bloque.x & x <= bloque.x + Bloque.HEIGHT){
                Vx = -Vx;    
            } Vy = -Vy;
        }
    }


    public boolean check_bordes(int alto_pant, int ancho_pant){

        if (x + SIZE > ancho_pant || x <=0 || y <= 0 || y+SIZE >= alto_pant){
            return true;
        } return false;
    }


    public double getVelocidad(){
        return Math.sqrt(Vx*Vx + Vy*Vy);

    }
}

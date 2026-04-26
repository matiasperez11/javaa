import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Bloque extends Figura{


    public static final double fps = 30;
    public static final int  WIDTH = 50;
    public static final int HEIGHT = 20;
    private static final Random random = new Random();

    public Bloque(int x, int y, Color color){
        super(x, y, 
            (random.nextInt(100, 300) * (Math.random() > 0.5 ?1: -1) ) / (fps), 
            (random.nextInt(100, 300) * (Math.random() > 0.5 ?1: -1) ) / (fps));
        this.color = color;
        
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, 50, 20);
    }

    public void mobimiento(){
        x += Vx;
        y += Vy;
    }

    public void check_bordes (int ancho_pant, int alto_pant){

        if (x+WIDTH >= ancho_pant || x <= 0){
            Vx = -Vx;
        } else if ( y< 0 || y> alto_pant /2){
            Vy = -Vy;
        }
    }

    public void check_colision_otros(ArrayList<Bloque> bloques){
    for (Bloque bl : bloques){
        if (bl == this) continue;
        
        Rectangle r = new Rectangle(x, y, WIDTH, HEIGHT);
        Rectangle re = new Rectangle(bl.x, bl.y, WIDTH, HEIGHT);

        if (r.intersects(re)){
           if (this.y > bl.y && this.y < bl.y + HEIGHT){
            Vx = -Vx;
           } else {
            Vy = -Vy;
           }
           break;
        }
    }
}

   /*  public boolean check_colision_bullet(Bullet bullet){

        Rectangle b = new Rectangle(x, y, WIDTH, HEIGHT);
        Rectangle bl = new Rectangle(bullet.x, bullet.y, 10, 20);

        if (b.intersects(bl)){
            
            return true;
        } else{
            return false;
        }
    } */
}
    

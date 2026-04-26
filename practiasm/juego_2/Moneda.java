import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Moneda  extends Figura{
    
    public static final int width = 20;
    public static final int height = 20;
    public  int  VX = 300;
    public  int VY = 300;
    public static final double fps = 60;

    private static Random rand = new Random();

    public Moneda(int x, int y, Color color ){
        super(x, y, 
            (rand.nextInt(100, 400)  * (Math.random() > 0.5 ? 1 :-1)) / (fps),
            (rand.nextInt(100,400) * (Math.random() >0.5 ? 1 : -1)) / (fps)
        );
        this.color = color;
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

        public void movimiento( int ancho_pant, int alto_pant){
        x += Vx ;
        y += Vy ;

        if ( x <= 0 || x + width >= ancho_pant -10){
            Vx = -Vx;
        }
        if ( y <= 0 || y + height >= alto_pant -10){
            Vy = -Vy;
        }
    }
    

    public void check_colision(ArrayList<Moneda> monedas){

        for (Moneda m: monedas){
            if (m == this) continue;

        Rectangle m1 = new Rectangle(x, y, width, height);

        Rectangle m2 = new Rectangle( m.x, m.y, Moneda.width, Moneda.height);

        if (m1.intersects(m2)){

            if (y > m2.y && y <= m2.y + m2.height){
                Vx = -Vy;
            } else {
                Vy = -Vy;
            }
        }

        }
    }



}

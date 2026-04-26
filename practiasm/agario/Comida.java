import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

public class Comida extends Figura{
    
    private static Random rand = new Random();
    public int width = 20;
    public int height = 20;
    public final static double fps = 60;

    public Comida(int x, int y, Color color){
        super(x, y, 
            (rand.nextInt(100,300) * (Math.random() >0.5 ? 1: -1)) / fps,
            (rand.nextInt(100, 300) * (Math.random() >0.5 ? 1 : -1)) /fps );
        this.color = color;
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void movimiento(int ancho_pant, int alto_pant){
        x += Vx;
        y += Vy;

        if (x <= 0 || x + width >= ancho_pant-50){
            Vx = -Vx;
        }
        if (y <0 || y+height >= alto_pant-50){
            Vy =-Vy;
        }
    }

    public void check_colision(ArrayList<Comida> comida){
        for (Comida com: comida){
            if(com == this) continue;

            Rectangle c1 = new Rectangle(x, y, width, height);
            Rectangle c2 = new Rectangle(com.x, com.y, width, height);

            if (c1.intersects(c2)){

                if (x > com.x && x < com.x + width){
                    Vy = -Vy;
                }
                if (y> com.y && y < com.y + height){
                    Vx = -Vx;
                }
            } 
        }
    }

    
}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Bloque extends Figura{
    
    public final static double VX = 200;
    public final static double VY = 200.0;
    public final static int WIDTH = 50;
    public final static int HEIGHT = 30;
    public final static double fps = 60;

    public int sentido = Math.random() >0.5 ? 1: -1;

    public Bloque(int x, int y, Color color){
        super(x, y, VX/fps, VY/fps);
        this.color = color;
    }

    public void paint(Graphics g){
        g.setColor(color);
        if (sentido == 1){
            g.fillRect(x, y, WIDTH, HEIGHT);
        } else {
            g.fillRect(x, y, HEIGHT, WIDTH);
        }
    }

    public void movimiento(int ancho_pant, int alto_pant){
        if (sentido == 1){
            Vy = 0;
            x += Vx;
            if(x <= 0 || x+WIDTH >= ancho_pant){
            Vx = -Vx;
        }
    }
        if (sentido ==-1){
            y += Vy;
            Vx = 0;
             if (y <= 0 || y+HEIGHT >= alto_pant){
            Vy = -Vy;
            }
        }
    }
     
    
    public void check_colision_otros(ArrayList<Bloque> bloques){
        for (Bloque bl : bloques){
            if (bl == this) continue;

            Rectangle r1 = new Rectangle(x, y, WIDTH, HEIGHT);
            Rectangle r2 = new Rectangle (bl.x, bl.y, WIDTH, HEIGHT);

            if (r1.intersects(r2)){
                if (x> bl.x && x<bl.x+WIDTH){
                    Vx = -Vx;
                }
                if (y> bl.y && y<bl.y+HEIGHT){
                    Vy = -Vy;
                }
            }
        }
    }


}

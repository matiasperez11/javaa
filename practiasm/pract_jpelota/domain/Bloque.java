package domain;
import java.awt.Color;
import java.awt.Graphics;


public class Bloque extends Figura{
    
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    public static final int VELOCIDAD = 40;

    public static final double fps = 30;


    public boolean HORIZONTAL = Math.random() > 0.5;    //devolvera o true o false

    public int sentido = Math.random() > 0.5 ? 1 : -1;

    public Bloque(int x, int y, Color color){   //x e y vendran desse fuera, en gamepanel con randoms
        super(x, y, color);
    }


    public void paint(Graphics g){
        g.setColor(color);
        
        if (HORIZONTAL){
            g.fillRect(x, y, WIDTH, HEIGHT);
        } else {
            g.fillRect(x, y, WIDTH, HEIGHT);
        }
    }


    public void movimiento(int alto_pant, int ancho_pant){
        
        if(HORIZONTAL){
            x += VELOCIDAD * sentido / fps;
             if ( x+WIDTH >= ancho_pant || x < 0){
            sentido = -sentido;
                    }
        } else {
            y += VELOCIDAD * sentido /fps;
            if (y+HEIGHT > alto_pant || y<0){
                sentido = -sentido;
            }
        }
    
}               
}

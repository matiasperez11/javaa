import java.awt.Canvas;
import java.awt.Graphics;

public class Lienzo extends Canvas {       //tengo q llamar a getfiguras asi q necesitare algo de dibujo, no le paso dibujo directmanete. le paso Jventana q tendra dibujo como atributo eterno pq lo crea. (prinicpio de diagrama)

    private JVentana jv; //lo declaro aqi arriba para tenerlo eterno y que no se me esfume cuando acabe el constructor. pq lo usare luego en el paint, pq dentro del jv esta el dibujo, con dibujo puedo acceder a las figuras con getfiguras

    public Lienzo (JVentana jventana){
        jv = jventana;
    }
                                            //gracias a jventana tiene las figuras a travez de dibujo
    public void paint(Graphics g){
        jv.getDibujo().get_figuras().stream().forEach(f ->f.pintar(g));
    }

    
}

import java.awt.*;

public class Bullet extends Figura{
    
    public final static double fps = 30;
    public final static int WIDTH = 10;
    public final static int HEIGHT = 20;

    
    public Bullet(Color color, int x, int y){
        super(x, y, 0, 800/fps);
        this.color = color;
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public void movimiento(){
        y += -Vy;
    }

    public boolean check_colision(Bloque bloque){

        Rectangle b = new Rectangle(x, y, WIDTH, HEIGHT);
        Rectangle bl = new Rectangle(bloque.x, bloque.y, Bloque.WIDTH, Bloque.HEIGHT);

        if (b.intersects(bl)){
            
            return true;
        } else{
            return false;
        }
    }
    
}



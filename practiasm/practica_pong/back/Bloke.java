package back;
import java.awt.*;

public class Bloke extends Figura{
;
    public static final int width = 190;
    public static final int height = 20;

   

    public Bloke(int x, int y, Color color){
        super(x, y, 0, 0);
        this.color = color;
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, width, height );
    }

}
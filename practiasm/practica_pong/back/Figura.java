package back;
import java.awt.*;

public abstract class Figura {
    public int x;
    public int y;
    public double Vx;
    public double Vy;
    public Color color;

    public Figura(int x, int y, double Vx, double Vy){
        this.x = x;
        this.y = y;
        this.Vx = Vx;
        this.Vy = Vy;
    }

    public abstract void paint(Graphics g);
}

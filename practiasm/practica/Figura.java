import java.awt.Color;
public abstract class Figura {
    
    protected int x;
    protected int y;
    protected boolean relleno;
    protected Color color;
    protected boolean visible;

    public Figura(int x, int y, boolean relleno, Color color){
        this.x = x;
        this.y = y;
        this.relleno = relleno;
        this.color = color;
        this.visible = true;   //para empezar las figuras son visibles
    }

    public boolean getVisible(){
        return this.visible;
    }

    public void setVisible(boolean b){
        this.visible = b;
    }

    public abstract void pintar(java.awt.Graphics g);
}

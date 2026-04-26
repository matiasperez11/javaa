package back;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Pelota extends Figura{

    public static final int X_INICIAL = 300;
    public static final int Y_INICIAL = 200;
    public static final double VX_INICIAL = 350;
    public static final double VY_INICIAL = 350;
    public static final double fps = 30;

    ArrayList<Color> colores;
    
    public Pelota(){
        super(X_INICIAL, Y_INICIAL, VX_INICIAL, VY_INICIAL);
        colores = new ArrayList<>();
        colores.add(Color.MAGENTA);
        colores.add(Color.RED);
        colores.add(Color.BLUE);
        colores.add(Color.GREEN);
        color = Color.WHITE;

    };

    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, 30, 30);
    }

    public void movimiento(){
        x += Vx /fps;
        y += Vy /fps;
    }

    public void cambiaColor(){
        color = colores.get( (int) (Math.random() * colores.size()));   // (int) como un floor
        }

    
    public void check_bordes(int alto_pant, int ancho_pant){

        if (x <= 0 || x+30 >= ancho_pant){
            if (Vy>0){
                Vy += 5;
            } else {
                Vy -= 5;
            }
            Vx = -Vx;
            cambiaColor();
            Vx -= 5;
        }

        if (y<=0){
            if (Vx>0){
                Vx += 5;
            } else {
                Vx -= 5;
            }
            Vy = -Vy;
            cambiaColor();
            Vy -= 5;
        }
        
        
    }

    public boolean check_suelo(int alto_pant){
        if (y+20 > alto_pant){
            return true;
        }
        return false;
    }

    public double get_Velocidad(){
        return Math.sqrt(Vx*Vx + Vy*Vy);
    }
    

    public void check_colision(Bloque bloque){

        Rectangle p = new Rectangle( (int) x, (int)y, 30, 30);
        Rectangle b = new Rectangle((int) bloque.x, (int) bloque.y, (int) Bloque.WIDTH, (int) Bloque.HEIGHT);

        if (p.intersects(b)){

            if (x < bloque.x + Bloque.WIDTH/2){
                Vy = -Vy;
                cambiaColor();
                Vx = -1* Math.abs(Vx);
                Vy -= 2;
                if (Vx >0){
                    Vx += 5;
                } else {
                    Vx -= 5;
                }
            
            } else if (x> bloque.x/2 + Bloque.WIDTH/2){
                Vy = - Vy;
                cambiaColor();
                Vx = Math.abs(Vx);
                Vy -= 2;
                if (Vx >0){
                    Vx += 5;
                } else {
                    Vx -= 5;
                }
            }
        }    
}

    public boolean check_kolision(Bloke bloke){

        Rectangle p = new Rectangle(  x, y, 30, 30);
        Rectangle blokke = new Rectangle(bloke.x, bloke.y, Bloke.width, Bloke.height);

        if (p.intersects(blokke)){
            Vy = -Vy;
            if (Vx>0){
                Vx += 5;
            } else if (Vx < 0){
                Vx -= 5;
            }
            cambiaColor();
            return true;
        } else {
            return false;
        }
    }


}


            
            
    


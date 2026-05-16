import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    
    public final static int ancho_pant = 1200;
    public final static int alto_pant = 700;
    public final static int fps = 60;

    Random rand = new Random();

    Jugador jugador;
    Comida comida;


    ArrayList<Comida> comidas;
    HashSet<Integer> teclas;
    TreeSet<Integer> puntuaciones;

    public Thread hilo;
    boolean fin_juego = false;
    public int puntuacion = 0;
    public int ca = 3;
    public int ncuadrados;

    public GamePanel(){

        this.setSize(ancho_pant, alto_pant);

        jugador = new Jugador(ancho_pant, alto_pant);
        crearComida();

        teclas = new HashSet<>();
        puntuaciones = new TreeSet<>();

        this.addKeyListener( new KeyAdapter(){
            
            public void keyPressed( KeyEvent e){
                teclas.add(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_R){
                        restart();
                    }
            }
            public void keyReleased (KeyEvent e){
                teclas.remove(e.getKeyCode());
            }
        });

        
        this.addMouseMotionListener(new MouseMotionAdapter() {
            
            public void mouseMoved(MouseEvent m){
                
                jugador.x = m.getX();
                jugador.y = m.getY();
                
            } 
        });

        //setFocusable(true);
        
        //this.requestFocus();
        arrancaJuego();
}

    public void arrancaJuego(){

        hilo = new Thread( () -> {

            while (!fin_juego){

                //jugador.movimiento(teclas);
                jugador.check_bordes(ancho_pant, alto_pant);
                
                for (Comida com: comidas){
                    com.movimiento(ancho_pant, alto_pant);
                    com.check_colision(comidas);
                }

                int ncomidas = comidas.size();
                comidas.removeIf( c-> jugador.comer(c));

              

                if (comidas.size() < ncomidas){
                    puntuacion += 1;
                    jugador.height += 10;
                    jugador.width += 10;
                }

                if (comidas.size() == 0){
                    fin_juego = true;

                    for (int ct = 3; ct>0; ct--){
                        ca = ct;
                        repaint();
                        try{
                            Thread.sleep(1000);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    restart();
                    return;
                }

                try{
                    Thread.sleep(1000/fps);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                repaint();
            }

        
        });
        hilo.start();

    }

    public void restart(){
        fin_juego = true;
        try{
            Thread.sleep(100);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        fin_juego = false;

        jugador = new Jugador(ancho_pant, alto_pant);
        comidas.clear();
        crearComida();
        puntuacion = 0;
        ca = 3;
        arrancaJuego();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        jugador.paint(g);
        comidas.stream().forEach( c -> c.paint(g));

        g.setColor(Color.BLUE);
        g.setFont( new Font ( Font.SANS_SERIF, Font.BOLD, 20));
        g.drawString("vida: " + puntuacion, ancho_pant-200, alto_pant-200);
        g.drawString("n cuadrados: " + ncuadrados, ancho_pant-300, alto_pant-100);

        if (fin_juego == true){
            g.setColor(Color.RED);
            g.drawString(String.valueOf(ca), ancho_pant/2-100, alto_pant/2);
        }
    }

    public void crearComida(){
        comidas = new ArrayList<>();

        ArrayList<Color> kolores = new ArrayList<>();
        kolores.add(Color.PINK);
        kolores.add(Color.YELLOW);
        kolores.add(Color.ORANGE);

        ncuadrados = rand.nextInt(1, 20);

        for (int q = 0; q<ncuadrados; q++){
            Color kolor = kolores.get(rand.nextInt(kolores.size()));
            int x = rand.nextInt(10, ancho_pant-50);
            int y = rand.nextInt(10, alto_pant-50);

            comidas.add( new Comida(x, y, kolor));
        }
        
    }

}

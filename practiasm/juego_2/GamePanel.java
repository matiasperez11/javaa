import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

import javax.swing.JPanel;

public class GamePanel extends JPanel{

    public static final int ancho_pant = 1200;
    public static final int alto_pant = 700;
    public static final int fps = 60;

    Random rand = new Random();
    
    Jugador jugador;
    Moneda moneda;

    ArrayList<Moneda> monedas;
    HashSet<Integer> teclas;
    TreeSet<Integer> puntuaciones;
    TreeSet<Double> tiempos;

    public Thread hilo;

    public boolean fin_juego = false;
    public int ca = 3;
    public int puntuacion = 0;
    public int nmonedas;


    public GamePanel(){

        this.setSize(ancho_pant, alto_pant);

        jugador = new Jugador(ancho_pant, alto_pant);
        crearMonedas(); //ya crea el arraylist de monedas
        teclas = new HashSet<>();
        puntuaciones = new TreeSet<>();
        tiempos = new TreeSet<>();

        this.addKeyListener( new KeyAdapter(){
            public void keyPressed( KeyEvent e){
                teclas.add(e.getKeyCode());

                if (e.getKeyCode() == KeyEvent.VK_R){
                    restart();
                }
            }

            public void keyReleased( KeyEvent e){
                teclas.remove(e.getKeyCode());
            }
        });

        setFocusable(true);
        this.requestFocus();
        arrancaJuego();

    }

    public void arrancaJuego(){

        hilo = new Thread( () -> {
            while (!fin_juego){

                jugador.movimiento(teclas);
                jugador.check_bordes(ancho_pant, alto_pant);
                
                for (Moneda m: monedas){
                    m.movimiento(ancho_pant, alto_pant);
                    m.check_colision(monedas);
                }

                nmonedas = monedas.size();

                monedas.removeIf( mon -> jugador.check_comer(mon));

                if (monedas.size() < nmonedas){
                    puntuacion += 1;
                    jugador.HEIGHT += 10;
                    jugador.WIDTH += 10;

                }

                if (monedas.size() == 0){
                    fin_juego = true;

                    for (int ct = 3; ct >0; ct--){
                        //int cuentaAtras = cp;
                        ca = ct;
                        repaint();
                        try {
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
                } catch (InterruptedException e){
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
            Thread.sleep(200);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        fin_juego= false;
        jugador = new Jugador(ancho_pant, alto_pant);
        monedas.clear();
        crearMonedas();
        puntuacion = 0;
        ca = 3;
        arrancaJuego();
    
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        jugador.paint(g);
        monedas.stream().forEach(m -> m.paint(g));

        g.setColor(Color.BLUE);
        g.drawString( "n monedas: " + nmonedas, ancho_pant-150, alto_pant-150);
        g.drawString(" punturacion: " + puntuacion, ancho_pant -150, alto_pant -170);

        if (fin_juego == true){

            g.setFont(new Font( Font.SANS_SERIF,Font.BOLD, 60));
            g.setColor(Color.RED);
            g.drawString("fin juego", ancho_pant/2 -100, alto_pant/2);
            g.drawString(String.valueOf(ca), ancho_pant/2 -100, alto_pant/2 +100);
        }

    }







    public void crearMonedas(){
        monedas = new ArrayList<>();

        ArrayList<Color> colores = new ArrayList<>();
        colores.add(Color.YELLOW);
        colores.add(Color.ORANGE);
        colores.add(Color.PINK);

        int w = rand.nextInt(2, 20);

        for (int q = 0; q < w; q++){
            
            Color kolor = colores.get(rand.nextInt(colores.size()));
            int x = rand.nextInt(0, ancho_pant-50);
            int y = rand.nextInt(0, alto_pant-50);

            monedas.add( new Moneda(x, y, kolor));
        }
    }


}

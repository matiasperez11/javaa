package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

import javax.swing.JPanel;

import domain.Pelota;
import domain.Bloque;

public class GamePanel extends JPanel{
    
    public static final int alto_pant = 700;
    public static final int ancho_pant = 1200;
    public static final double fps = 30;

    Pelota pelota;
    ArrayList<Bloque> bloques;
    
    HashSet<Integer> teclas;
    TreeSet<Double> puntuaciones;

    public Thread hilo;

    public boolean fin_juego = false;

    public double max_velocidad = 0;

    public GamePanel(){

        this.setSize(ancho_pant, alto_pant);

        pelota = new Pelota(Color.MAGENTA);

        bloques = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i <6; i++){
            int xb = random.nextInt(ancho_pant);
            int yb = random.nextInt(alto_pant);
            bloques.add(new Bloque(xb, yb , Color.GRAY));
        }

        teclas = new HashSet<>();
        puntuaciones = new TreeSet<>();

        this.addKeyListener( new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                teclas.add(e.getKeyCode());

                if (e.getKeyCode() == KeyEvent.VK_SPACE && fin_juego){
                    restart();
                }
            }

            public void keyReleased(KeyEvent e){
                teclas.remove(e.getKeyCode());
            }
        });

        setFocusable(true);
        requestFocus();
        arrancaJuego();
    }


    public void arrancaJuego(){

        hilo = new Thread( ()-> {

            while (!fin_juego){

                pelota.movimiento();
                pelota.acelerar(teclas);
                bloques.stream().forEach( bl -> pelota.check_colision(bl));
                bloques.stream().forEach(b -> b.movimiento(alto_pant, ancho_pant));

                if (pelota.check_bordes(alto_pant, ancho_pant)){
                    fin_juego = true;
                    max_velocidad = pelota.getVelocidad();
                    puntuaciones.add(max_velocidad);

                    if (puntuaciones.size() > 3){
                        puntuaciones.remove(puntuaciones.first());
                    }

                    /*try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    } 
                    restart(); */
                }
                

            try {
                Thread.sleep( (long) (1000/fps));
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            repaint();
            }
        });
        hilo.start();


    }

    public void restart(){
        pelota = new Pelota(Color.MAGENTA);
        fin_juego = false;
        arrancaJuego();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        pelota.paint(g);
        bloques.stream().forEach(bs -> bs.paint(g));

        int i = 0;
        for (Double p :puntuaciones.descendingSet()){
            
            g.drawString(String.valueOf(p), ancho_pant-200, 50 + i);
            i += 20;
        }

        double velo = pelota.getVelocidad();
        g.drawString(String.valueOf(velo), ancho_pant-100, 30);

        if(fin_juego){
            g.setColor(Color.RED);
            g.setFont( new Font( Font.SANS_SERIF, Font.BOLD, 50));
            g.drawString("fin del juego", ancho_pant /2 -100, alto_pant/2);
        }


        
    }
}

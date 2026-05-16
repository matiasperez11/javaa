import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

import javax.swing.JPanel;

public class GamePanel extends JPanel{

    public final static int ancho_pant = 1200;
    public final static int alto_pant = 700;
    public final static double fps = 60;
    Random rand = new Random();

    Pelota pelota;
    Bloque bloque;

    ArrayList<Bloque> bloques;
    HashSet<Integer> teclas;
    TreeSet<Double> puntuaciones;
    
    int puntuacion = 0;
    boolean fin_juego = false;
    int ca;
    int nbloques;
    Thread hilo;
    long tiempojeugo;

    public GamePanel(){

        pelota = new Pelota(ancho_pant, alto_pant);
        crearBloques();

        teclas = new HashSet<>();
        puntuaciones = new TreeSet<>();

        this.addKeyListener( new KeyAdapter() {
            public void keyPressed (KeyEvent e){
                teclas.add(e.getKeyCode());
                if (teclas.contains(KeyEvent.VK_R)){
                    restart();
                }
            }
            public void keyReleased( KeyEvent e){
                teclas.remove(e.getKeyCode());
            }
        });

        setFocusable(true);
        this.requestFocus();
        arrancJuego();
    }

    public void arrancJuego(){
        hilo = new Thread( () -> {
            double startTime = System.currentTimeMillis();

            while(!fin_juego){

            pelota.movimiento();
            pelota.acelerar(teclas);
            tiempojeugo = (long) (System.currentTimeMillis() - startTime);
            
            for (Bloque bl : bloques){
                bl.check_colision_otros(bloques);
                bl.movimiento(ancho_pant, alto_pant);
            }

            int nbloques = bloques.size();

            bloques.removeIf(b -> pelota.check_colision_bl(b));
            

            if( bloques.size() < nbloques){
                puntuacion ++;
            }

            if ( pelota.check_bordes(ancho_pant, alto_pant)){
                fin_juego = true;
                tiempojeugo = (long) (System.currentTimeMillis() - startTime);

                for (int ct = 3; ct > 0; ct--){
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

            if ( bloques.isEmpty()){
                fin_juego = true;
                tiempojeugo = (long) (System.currentTimeMillis() - startTime);


                for (int ct = 3; ct > 0; ct--){
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
                Thread.sleep((long) (1000/fps));
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
            Thread.sleep(100); 
        } catch(InterruptedException e){
             e.printStackTrace(); }
        fin_juego = false;
        puntuacion = 0;
        bloques.clear();
        pelota = new Pelota(ancho_pant, alto_pant);
        crearBloques();
        arrancJuego();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        pelota.paint(g);
        bloques.stream().forEach( b -> b.paint(g));
        g.drawString(String.valueOf(tiempojeugo), ancho_pant-100, alto_pant-200);

        if ( fin_juego == true && pelota.check_bordes(ancho_pant, alto_pant)){
            g.drawString("derrota", ancho_pant/2 -100, alto_pant/2);
              g.drawString(String.valueOf(ca), ancho_pant/2 -100, alto_pant/2+50);
        }
        if ( fin_juego == true && bloques.isEmpty()){
            g.drawString("victoria", ancho_pant/2 -100, alto_pant/2);
            g.drawString(String.valueOf(ca), ancho_pant/2 -100, alto_pant/2+50);
        }

    }





    public void crearBloques(){
        bloques = new ArrayList<>();
        ArrayList<Color> colores = new ArrayList<>();
        colores.add(Color.GRAY);
        colores.add(Color.BLACK);

        nbloques = rand.nextInt(2,10);
        for (int q =0; q <  nbloques; q++){
            int x = rand.nextInt(2, ancho_pant-100);
            int y = rand.nextInt(2, alto_pant-100);
            Color kolor = colores.get(rand.nextInt(colores.size()));
            bloques.add(new Bloque(x, y, kolor));
        }

    }
    
}

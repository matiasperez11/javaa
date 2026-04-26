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

import back.Bloke;
import back.Bloque;
import back.Pelota;

public class GamePanel extends JPanel{

    public static final int ancho_pant = 1200;
    public static final int alto_pant = 700;
    public static final double fps = 30;

    Pelota pelota;
    Bloque bloque;
    
    HashSet<Integer> teclas;
    TreeSet<Double> tiempos;
    ArrayList<Bloke> blokes;
    TreeSet<Integer> puntuaciones;
    public int puntuacion;

    public Thread hilo;

    public boolean fin_juego = false;

    public double tiempojuego;

    public GamePanel(){

        this.setSize(ancho_pant, alto_pant);
        
        pelota = new Pelota();
        bloque = new Bloque(Color.GRAY, ancho_pant, alto_pant-Bloque.HEIGHT);

        blokes = new ArrayList<>();
        crearBlokes();

        teclas = new HashSet<>();
        tiempos = new TreeSet<>();
        puntuaciones = new TreeSet<>();


        this.addKeyListener(new KeyAdapter() {
            public void keyPressed( KeyEvent e){
                teclas.add(e.getKeyCode());

                if (e.getKeyCode() == KeyEvent.VK_W){
                    restart();
                }
                    }
                
    
            public void keyReleased (KeyEvent e){
                teclas.remove(e.getKeyCode());
                    }
    });

        setFocusable(true);
        requestFocus();

        arrancaJuego();
    }

    public void arrancaJuego(){

        hilo = new Thread( () -> {
            double starTime = System.currentTimeMillis();

            while(!fin_juego){

                pelota.movimiento();
                pelota.check_bordes(alto_pant, ancho_pant);
                pelota.check_colision(bloque);

                bloque.movimiento(teclas);
                bloque.check_bordes(alto_pant, ancho_pant);

                int nblokes = blokes.size();
                blokes.removeIf( k -> pelota.check_kolision(k));
                if (blokes.size() < nblokes){
                    puntuacion += 1;
                    
                }


                if (pelota.check_suelo(alto_pant)){
                    fin_juego = true;

                    //JOptionPane.showMessageDialog(this, "has perdido, tiempo: " + tiempojuego, "FIN",  JOptionPane.PLAIN_MESSAGE);
                    
                    tiempos.add(tiempojuego);
                    if(tiempos.size() > 3){
                        tiempos.remove(tiempos.first());
                    }

                    puntuaciones.add(puntuacion);

                    

                    try{
                        Thread.sleep (2000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    restart();
                    //return;

                }

                tiempojuego = (System.currentTimeMillis() - starTime ) /1000 ;

                try{
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
        fin_juego = true;
        try {
            Thread.sleep(200);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        fin_juego = false;
        pelota = new Pelota();
        bloque = new Bloque(Color.GRAY, ancho_pant/2, alto_pant - Bloque.HEIGHT);
        blokes.clear();
        crearBlokes();
        puntuacion  = 0;

        arrancaJuego();
        }


    public void crearBlokes(){
        ArrayList<Color> kolores = new ArrayList<>();
        kolores.add(Color.BLUE);
        kolores.add(Color.RED);
        kolores.add(Color.GREEN);
        kolores.add(Color.PINK);
        kolores.add(Color.YELLOW);
        kolores.add(Color.BLACK);

        Random random = new Random();

        for (int fila = 0; fila <3 ; fila++){
            for (int column =0 ;column <5; column++){

                int x = column * (Bloke.width + 5) +1;
                int y = fila * (Bloke.height+  5) +1;

                Color ckolor = kolores.get(random.nextInt(kolores.size()));
                
                blokes.add( new Bloke(x, y, ckolor));
            }
        }
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        pelota.paint(g);
        bloque.paint(g);
        blokes.stream().forEach(bl -> bl.paint(g));

        g.setColor(Color.RED);
        g.drawString("velocidad : " + pelota.get_Velocidad() + " s", ancho_pant -120, 200);

        g.drawString("tiempo juego: " + tiempojuego +" s", ancho_pant-320, 200);
        int i = 0;
        for (Double t : tiempos.descendingSet()){
            g.setColor(Color.RED);
            g.drawString(String.valueOf(t), ancho_pant-100, 350 + i);
            i += 30;
        }

        g.drawString ("puntuacion: " +puntuacion, ancho_pant-100, 300);
        int j = 0;
        int q = 1;
        for (Integer p: puntuaciones.descendingSet()){
            g.setColor(Color.ORANGE);
            g.drawString(String.valueOf(q)+ ". " + String.valueOf(p), ancho_pant-50, 350 + j);
            j+= 40;
            q+= 1;
        }

        if (fin_juego){
            g.setColor(Color.BLUE);
            g.setFont(new Font (Font.MONOSPACED, Font.BOLD, 60));
            g.drawString("fin juego", ancho_pant/2-100, alto_pant/2);
        }


    }
    }
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    
    public static final int ancho_pant = 1200;
    public static final int alto_pant = 700;
    public static final double fps = 30;

    Bloque bloque;
    Bullet bullet;
    Shooter shooter;

    ArrayList<Bloque> bloques;
    ArrayList<Bullet> bullets;
    HashSet<Integer> teclas;
    TreeSet<Integer> puntuaciones;
    TreeSet<Double> tiempos;


    public Thread hilo;
    public boolean fin_juego = false;
    public int puntuacion = 0;
    public double tiempojuego  = 0;
    public int cuentaAtras = 0;
    public int num = 0;

    public GamePanel(){

        this.setSize(ancho_pant, alto_pant);

        puntuaciones = new TreeSet<>();
        bullets = new ArrayList<>();
        teclas = new HashSet<>();
        tiempos = new TreeSet<>();

        shooter = new Shooter(Color.BLACK);
        crearBloques();

        this.addKeyListener( new KeyAdapter() {
            public void keyPressed( KeyEvent e){
                teclas.add(e.getKeyCode());
                
                if (teclas.contains(KeyEvent.VK_SPACE)){
                    bullets.add(new Bullet(Color.MAGENTA, shooter.x, shooter.y));
                }
                if (teclas.contains(KeyEvent.VK_W)){
                    restart();

                }
            }

            public void keyReleased( KeyEvent e){
                teclas.remove(e.getKeyCode());
            }
        });

        setFocusable(true);
        this.requestFocus();
        arranca_juego();


    }

    public void arranca_juego(){

        hilo = new Thread ( () ->{

            double startTime = System.currentTimeMillis();

            while (!fin_juego){

                shooter.movimiento(teclas);
                shooter.check_bordes(ancho_pant, alto_pant);
                bullets.stream().forEach( bu -> bu.movimiento());

                bullets.removeIf(  bullet -> bullet.y < 0);
                bloques.stream().forEach( b -> b.mobimiento());
                bloques.stream().forEach( bl -> bl.check_bordes(ancho_pant, alto_pant));
                bloques.stream().forEach( blo -> blo.check_colision_otros(bloques));


                int nbloques = bloques.size();
                for(Bullet bullet : bullets){
                    bloques.removeIf(bl -> bullet.check_colision(bl));
                }

                if (bloques.size() < nbloques){
                    puntuacion += 1;
                }

                tiempojuego = (System.currentTimeMillis() - startTime) / (1000);
                
                if (bloques.size() == 0 ){

                    fin_juego = true;
                    puntuaciones.add(puntuacion);

                    tiempos.add(tiempojuego);
                    if (tiempos.size() >3){
                        tiempos.remove(tiempos.first());
                    }

                    /*try{
                        Thread.sleep(2000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }*/
                   cuentaAtras = 0;
                   for (int ct = 3; ct > 0; ct--) {
                        cuentaAtras = ct;
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
                    Thread.sleep( (long) (1000/fps));
                } catch (InterruptedException e) {
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
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        fin_juego = false;
        bloques.clear();
        bullets.clear();
        shooter = new Shooter(Color.BLACK);
        crearBloques();
        puntuacion = 0;

        arranca_juego();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        shooter.paint(g);
        bloques.stream().forEach( bl -> bl.paint(g));
        bullets.stream().forEach( bu -> bu.paint(g));


        g.setColor(Color.BLACK);
        g.drawString("n de bloques: " + (num -puntuacion ), ancho_pant-200, 100 );
        g.drawString("puntuacion actual: " + puntuacion, ancho_pant-200, 300);
        g.drawString ("top puntuaciones: ", ancho_pant-200, 400);
        g.drawString("tiempo: " + tiempojuego +" s", ancho_pant-100, 200);

        int j = 0;
        int i = 1;
        for (int p :puntuaciones.descendingSet()){
            g.drawString (String.valueOf(i) +". " + p, ancho_pant-200, 420+j);
            i+= 1;
            j += 30;
        }
        int jj = 0;
        for (double d : tiempos.descendingSet()){
            g.drawString(String.valueOf(d) + " s", ancho_pant-100, 420 +jj);
            jj+= 30;
        }

        if (fin_juego){
            g.setColor(Color.GREEN);
            g.setFont(new Font( Font.SANS_SERIF, Font.ITALIC, 90));
            g.drawString("has ganado", ancho_pant/2-100, alto_pant/2);

            g.drawString(String.valueOf(cuentaAtras), ancho_pant-2 -200, alto_pant/2 +200);
        }
       /*  if (tiempojuego >5.0){
            g.setColor(Color.RED);
            g.setFont(new Font( Font.SANS_SERIF, Font.ITALIC, 90));
            g.drawString("has perdido", ancho_pant/2-100, alto_pant/2);
        }*/
    }




    public void crearBloques(){
        bloques = new ArrayList<>();

        ArrayList<Color> colores = new ArrayList<>();
        colores.add(Color.BLUE);
        colores.add(Color.CYAN);
        colores.add(Color.RED);
        colores.add(Color.YELLOW);
        colores.add(Color.GREEN);
        colores.add(Color.PINK);

        Random random = new Random();

        num = random.nextInt(3, 15);

        for (int i = 0; i<num; i++){
            int x = random.nextInt(1100);
            int y = random.nextInt(300);
            Color color = colores.get(random.nextInt(colores.size()));
            bloques.add(new Bloque(x, y, color));
        }

        

    }
}

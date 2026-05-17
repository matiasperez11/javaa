import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class PanelJuego extends JPanel implements Runnable {

    static final double GRAVEDAD      = 0.16;
    static final double WAVE_DRIFT    = 0.13;  // corriente de la ola: arrastra izquierda-abajo
    static final double AERIAL_THRESH = 3.0;
    static final int    AERIAL_BONUS  = 50;
    static final int    TUBO_PTS      = 4;     // puntos por tick dentro del tubo
    static final int    TRAIL_MAX     = 8;
    static final int    ESPUMA_N      = 25;

    Spot spot;
    public Thread    hilo;
    public boolean   jugando   = false;
    public boolean   game_over = false;
    public int       puntuacion = 0;

    public Surfista surfista;
    int[] estelaX = new int[TRAIL_MAX];
    int[] estelaY = new int[TRAIL_MAX];

    HashSet<Integer> teclas = new HashSet<>();

    public int ola_offset = 0;
    public int ola_vel;
    public int tick = 0;

    public boolean aerial_active = false;
    public int     aerial_timer  = 0;
    public boolean en_tubo       = false;
    public int     tubo_ticks    = 0;

    int[][] espuma = new int[ESPUMA_N][4];
    Random rand = new Random();
    BufferedImage imgWave;
    BufferedImage imgTubo;
    BufferedImage imgEspuma;

    PanelJuego(Spot spot) {
        this.spot = spot;

        if      ("Lenta".equals(spot.velocidadOla))   ola_vel = 1;
        else if ("Media".equals(spot.velocidadOla))   ola_vel = 2;
        else if ("Rápida".equals(spot.velocidadOla))  ola_vel = 3;
        else                                           ola_vel = 4;

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e)  { teclas.add(e.getKeyCode()); }
            public void keyReleased(KeyEvent e) { teclas.remove(e.getKeyCode()); }
        });

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (game_over) reiniciar();
            }
        });

        setFocusable(true);
        requestFocus();
        cargarWave();
        iniciar();
    }

    public void addNotify() {
        super.addNotify();
        SwingUtilities.invokeLater(() -> {
            int cx = getWidth()  / 2;
            int cy = getHeight() / 2;
            surfista.x = cx;
            surfista.y = cy;
            for (int i = 0; i < TRAIL_MAX; i++) { estelaX[i] = cx; estelaY[i] = cy; }
        });
    }

    void iniciar() {
        jugando       = true;
        game_over     = false;
        puntuacion    = 0;
        ola_offset    = 0;
        tick          = 0;
        aerial_active = false;
        aerial_timer  = 0;
        en_tubo       = false;
        tubo_ticks    = 0;
        teclas.clear();
        surfista = new Surfista(400, 250, 50, 50);
        for (int i = 0; i < TRAIL_MAX; i++) { estelaX[i] = 400; estelaY[i] = 250; }
        // generar espuma estática con semilla fija
        Random r = new Random(42L);
        for (int i = 0; i < ESPUMA_N; i++) {
            espuma[i][0] = r.nextInt(160) - 80;
            espuma[i][1] = r.nextInt(80)  - 40;
            int len = r.nextInt(11) + 10;
            double ang = r.nextDouble() * Math.PI * 2;
            espuma[i][2] = espuma[i][0] + (int)(Math.cos(ang) * len);
            espuma[i][3] = espuma[i][1] + (int)(Math.sin(ang) * len);
        }
        hilo = new Thread(this);
        hilo.start();
    }

    void reiniciar() {
        jugando = false;
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        iniciar();
    }

    void cargarWave() {
        Color agua = colorAgua();
        Color tinte = new Color(
            Math.min(255, agua.getRed()   + 50),
            Math.min(255, agua.getGreen() + 50),
            Math.min(255, agua.getBlue()  + 50)
        );
        imgWave   = procesarImg("resources/img/wave.png",   Color.WHITE);
        imgTubo   = procesarImg("resources/img/tubo.png",   tinte);
        imgEspuma = procesarImg("resources/img/espuma.png", Color.WHITE);
    }

    BufferedImage procesarImg(String ruta, Color target) {
        try {
            BufferedImage orig = ImageIO.read(new File(ruta));
            if (orig == null) return null;
            int argb = (255 << 24) | (target.getRed() << 16) | (target.getGreen() << 8) | target.getBlue();
            BufferedImage proc = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int py = 0; py < orig.getHeight(); py++) {
                for (int px = 0; px < orig.getWidth(); px++) {
                    int rgb = orig.getRGB(px, py);
                    int r  = (rgb >> 16) & 0xFF;
                    int gr = (rgb >>  8) & 0xFF;
                    int b  =  rgb        & 0xFF;
                    proc.setRGB(px, py, (r < 50 && gr < 50 && b < 50) ? argb : 0x00000000);
                }
            }
            return proc;
        } catch (Exception e) { e.printStackTrace(); return null; }
    }

    int[] waveZone() {
        int h = getHeight() > 0 ? getHeight() : 500;
        return new int[]{ (int)(h * 0.35), (int)(h * 0.75) };
    }

    double parseAltura() {
        if (spot.alturaOla == null || spot.alturaOla.isEmpty()) return 1.75;
        try {
            return Double.parseDouble(spot.alturaOla.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) { return 1.75; }
    }

    Color colorAgua() {
        String n = spot.nombre != null ? spot.nombre : "";
        if (n.equals("Pipeline") || n.equals("Hookipa"))                         return new Color(0, 180, 200);
        if (n.equals("Teahupo'o"))                                               return new Color(0, 160, 180);
        if (n.equals("Mundaka"))                                                  return new Color(30,  80,  70);
        if (n.equals("Hossegor"))                                                 return new Color(20,  90, 100);
        if (n.equals("El Palmar"))                                                return new Color(0,  130, 160);
        if (n.equals("Castelldefels"))                                            return new Color(0,  150, 180);
        if (n.equals("Uluwatu"))                                                  return new Color(0,  170, 190);
        if (n.equals("Desert Point") || n.equals("Mawi") || n.equals("Yoyo's")) return new Color(0,  165, 175);
        return new Color(0, 140, 170);
    }

    public void run() {
        while (jugando) {
            tick++;
            ola_offset += ola_vel;

            int[] z      = waveZone();
            int lip_y    = z[0];
            int base_y   = z[1];
            int w        = getWidth()  > 0 ? getWidth()  : 800;
            int h        = getHeight() > 0 ? getHeight() : 500;

            // guardar posición en estela (desplazar hacia atrás)
            for (int i = TRAIL_MAX - 1; i > 0; i--) {
                estelaX[i] = estelaX[i-1];
                estelaY[i] = estelaY[i-1];
            }
            estelaX[0] = surfista.x;
            estelaY[0] = surfista.y;

            // corriente de la ola: arrastra izquierda-abajo pasivamente
            surfista.vx -= WAVE_DRIFT;
            surfista.vy += GRAVEDAD;

            // input de teclado: flechas y WASD
            surfista.acelerar(teclas);
            surfista.mover();

            // limitar X al panel
            surfista.x = Math.max(10, Math.min(w - 10, surfista.x));

            // aerial: solo cuando está en el cielo (por encima del labio de la ola)
            // labio_y = h*0.29 igual que en dibujarOla — por encima es "el cielo"
            int labio_y = (int)(h * 0.29);
            if (aerial_timer > 0) {
                aerial_timer--;
                if (aerial_timer == 0) aerial_active = false;
            } else if (surfista.y < labio_y && surfista.vy < -AERIAL_THRESH) {
                aerial_active = true;
                aerial_timer  = 60;
                puntuacion   += AERIAL_BONUS;
            }

            // wipeout: zona de espuma calibrada desde Paint (imagen 3018x1376)
            // puntos ref: (295,928),(489,1019),(838,1190) → x<28% y>67% del panel
            if (surfista.x < w * 0.28 && surfista.y > h * 0.67) {
                jugando = false; game_over = true;
            }

            // tubo: calibrado desde Paint — apertura en x≈16-22%, y∈36-62%
            // puntos ref: (668,500),(533,611),(485,734),(470,840) sobre imagen 3018x1376
            en_tubo = surfista.x < w * 0.20
                   && surfista.y > h * 0.36
                   && surfista.y < h * 0.62;

            if (jugando) {
                if (en_tubo) { puntuacion += TUBO_PTS; tubo_ticks++; }
                else         { puntuacion++;            tubo_ticks = 0; }
            }

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();

        dibujarOla(g, w, h);

        // ajustar escala del surfista según altura de ola y panel
        surfista.alto = (int) Math.max(20, Math.min(h * 0.45, h * 0.5 * 1.75 / parseAltura()));
        surfista.x    = Math.max(10, Math.min(w - 10, surfista.x));
        surfista.y    = Math.max(0,  Math.min(h,       surfista.y));

        surfista.dibujarEstela(g, estelaX, estelaY);
        surfista.paint(g);

        // HUD
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString(spot.nombre + "   " + puntuacion + " pts", 10, 20);

        if (en_tubo) {
            g.setFont(new Font("SansSerif", Font.BOLD, 22));
            g.setColor(new Color(100, 220, 255));
            g.drawString("TUBO  +" + (tubo_ticks * TUBO_PTS) + " pts", w / 2 - 80, 85);
        }

        if (aerial_active) {
            g.setFont(new Font("SansSerif", Font.BOLD, 24));
            g.setColor(new Color(255, 230, 50));
            g.drawString("🤙 AERIAL! +" + AERIAL_BONUS, w / 2 - 95, 55);
        }

        if (game_over) {
            g.setColor(new Color(10, 20, 40));
            g.fillRoundRect(w/2 - 130, h/2 - 55, 260, 120, 10, 10);
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 30));
            g.drawString("WIPEOUT!", w/2 - 76, h/2 - 10);
            g.setFont(new Font("SansSerif", Font.PLAIN, 16));
            g.drawString("Puntuación: " + puntuacion, w/2 - 62, h/2 + 18);
            g.setFont(new Font("SansSerif", Font.ITALIC, 12));
            g.drawString("clic para reintentar", w/2 - 68, h/2 + 46);
        }
    }

    void dibujarOla(Graphics g, int w, int h) {
        Color agua = colorAgua();
        int labio_y = (int)(h * 0.29);

        Color cielo = new Color(
            Math.min(255, agua.getRed()   + 80),
            Math.min(255, agua.getGreen() + 80),
            Math.min(255, agua.getBlue()  + 80)
        );
        g.setColor(cielo);
        g.fillRect(0, 0, w, labio_y);
        g.setColor(agua.darker());
        g.fillRect(0, labio_y, w, h - labio_y);

        if (imgTubo != null)
            g.drawImage(imgTubo, 0, 0, w / 2, h, this);

        if (imgEspuma != null)
            g.drawImage(imgEspuma, 0, (int)(h * 0.60), (int)(w * 0.30), h, this);

        if (imgWave != null)
            g.drawImage(imgWave, 0, 0, w, h, this);
    }
}

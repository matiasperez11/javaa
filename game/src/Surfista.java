import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class Surfista {

    public int x, y, ancho, alto;
    public double vx = 0, vy = 0;

    // acumuladores sub-píxel para que velocidades pequeñas muevan al surfista
    double dpx = 0, dpy = 0;

    static final double ACEL    = 0.6;   // px/frame añadidos por tecla pulsada
    static final double FRICCION = 0.88; // amortiguación por frame — da inercia fluida

    public Surfista(int x, int y, int ancho, int alto) {
        this.x     = x;
        this.y     = y;
        this.ancho = ancho;
        this.alto  = alto;
    }

    public void paint(Graphics g) {
        double s = alto / 90.0;
        int hr   = Math.max(2, (int)(5  * s));
        int hcy  = y - (int)(17 * s);
        int tt   = y - (int)(12 * s);
        int tb   = y + (int)(2  * s);
        int ay   = y - (int)(8  * s);
        int ax   = Math.max(3, (int)(12 * s));
        int lb   = y + (int)(14 * s);
        int lx   = Math.max(2, (int)(7  * s));
        int bw   = Math.max(5, (int)(22 * s));
        int by   = y + (int)(13 * s);
        int bh   = Math.max(2, (int)(5  * s));
        g.setColor(new Color(30, 30, 30));
        g.fillOval(x - hr, hcy - hr, hr * 2, hr * 2);
        g.drawLine(x,    tt,  x,    tb);
        g.drawLine(x-ax, ay,  x+ax, ay);
        g.drawLine(x,    tb,  x-lx, lb);
        g.drawLine(x,    tb,  x+lx, lb);
        g.setColor(Color.BLACK);
        g.fillRoundRect(x - bw, by, bw * 2, bh, 3, 3);
    }

    // flechas + WASD — igual que acelerar() en Pelota
    public void acelerar(HashSet<Integer> teclas) {
        if (teclas.contains(KeyEvent.VK_RIGHT) || teclas.contains(KeyEvent.VK_D))
            vx += ACEL;
        else if (teclas.contains(KeyEvent.VK_LEFT) || teclas.contains(KeyEvent.VK_A))
            vx -= ACEL;
        if (teclas.contains(KeyEvent.VK_UP) || teclas.contains(KeyEvent.VK_W))
            vy -= ACEL;
        else if (teclas.contains(KeyEvent.VK_DOWN) || teclas.contains(KeyEvent.VK_S))
            vy += ACEL;
    }

    // aplica fricción y actualiza posición con acumulador sub-píxel
    public void mover() {
        vx *= FRICCION;
        vy *= FRICCION;
        dpx += vx; x += (int) dpx; dpx -= (int) dpx;
        dpy += vy; y += (int) dpy; dpy -= (int) dpy;
    }

    // pinta la estela desde el extremo trasero (punta izquierda) de la tabla
    public void dibujarEstela(Graphics g, int[] estelaX, int[] estelaY) {
        double s   = alto / 90.0;
        int bw     = Math.max(5, (int)(22 * s));
        int byOff  = (int)(15 * s);
        int n      = estelaX.length;
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < n; i++) {
            int   sz    = (n - i) / 2 + 1;
            float alpha = (n - i) / (float) n * 0.5f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setColor(Color.WHITE);
            g2.fillOval(estelaX[i] - bw - sz, estelaY[i] + byOff - sz, sz * 2, sz * 2);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}

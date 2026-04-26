package ui;
import javax.swing.JFrame;

public class JPong extends JFrame{
    
    GamePanel gp;

    public JPong(){

        gp = new GamePanel();

        this.setSize(GamePanel.ancho_pant, GamePanel.alto_pant);
        this.setTitle("pong juego");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gp);
        this.setVisible(true);
    }

    public static void main (String args[]){
        new JPong();
    }
}


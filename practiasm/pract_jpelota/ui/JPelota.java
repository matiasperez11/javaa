package ui;
import javax.swing.JFrame;

public class JPelota extends JFrame {
    
    public GamePanel gp;

    public JPelota(){

        gp = new GamePanel();

        this.setSize(GamePanel.ancho_pant, GamePanel.alto_pant);
        this.setTitle("juego java");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gp);
        this.setVisible(true);
    }
    
    public static void main (String args[]){
        new JPelota();
    }
}

import javax.swing.JFrame;

public class JArcade extends JFrame{
    
    GamePanel gp;

    public JArcade(){

        gp = new GamePanel();
        this.add(gp);

        this.setSize(GamePanel.ancho_pant, GamePanel.alto_pant);
        this.setTitle("juego  space invaders");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);    
    }

    public static void main (String args[]){
        new JArcade();
    }
}
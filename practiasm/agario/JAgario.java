import javax.swing.JFrame;

public class JAgario extends JFrame{

    GamePanel gp;

    public JAgario(){
        this.setSize(GamePanel.ancho_pant, GamePanel.alto_pant);

        gp = new GamePanel();

        this.add(gp);
        this.setTitle("juego agario");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main (String args[]){
        new JAgario();
    }
    
}

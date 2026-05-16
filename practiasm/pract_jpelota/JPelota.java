import javax.swing.JFrame;

public class JPelota extends JFrame {
    
    GamePanel gp;

    public JPelota(){

        gp = new GamePanel();
        this.add(gp);
        
        this.setSize(GamePanel.ancho_pant, GamePanel.alto_pant);
        this.setTitle("juego jpelota");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public static void main(String args[]){
        new JPelota();
    }
}

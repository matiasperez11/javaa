import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JMonedas extends JFrame{
    
    GamePanel gp;

    public JMonedas(){

        gp = new GamePanel();
        this.setSize(GamePanel.ancho_pant, GamePanel.alto_pant);

        this.add(gp);
        this.setTitle("juego monedas");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel j1 = new JPanel();
        JComboBox<Color> colores = new JComboBox<>();
        colores.addItem(Color.BLACK);
        colores.addItem(Color.BLUE);
        colores.addItem(Color.CYAN);

        colores.addActionListener( g ->{
            gp.jugador.color = (Color) colores.getSelectedItem();
            gp.requestFocus();
        });
        j1.add(colores);


        this.add(j1, BorderLayout.NORTH);
      

       this.setVisible(true);
       gp.requestFocus();
    }

    public static void main (String args[]){
        new JMonedas();
    }
}

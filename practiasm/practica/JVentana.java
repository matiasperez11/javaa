import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

//import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JVentana extends JFrame{

    private Dibujo dibujo;
    private Lienzo lienzo;  //lo pongo como eterno pq luego lo llamare en lienzo.repaint
    
    public JVentana(){
        
        dibujo = new Dibujo();      //atributo eterno para añadir la figura a dibujo, y para devolverlo para que lienzo lo pueda tocar ya que a lienzo le paso jventana
        lienzo =  new Lienzo(this);     //eterno para usar repaint en el dibujar junto a dibujo

        this.setSize(2560,1400);
        this.setTitle("practica java");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        this.add(lienzo, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        
        //JLabel a1 = new JLabel("x:");
        //JTextField a11 = new JTextField(5);
        //JLabel b1 = new JLabel("y:");
        //JTextField b11 = new JTextField(5);
        JLabel c1 = new JLabel("lado - radio:");
        JTextField c11 = new JTextField(5);
        //JButton d1= new JButton("círculo");
        //JButton e1= new JButton("cuadrado");
        JLabel d1 = new JLabel("figura");
        JComboBox<String> figuras = new JComboBox<>();
        figuras.addItem("circulo");
        figuras.addItem("cuadrado");

        JLabel re = new JLabel("relleno");
        JComboBox<Boolean> rellenos = new JComboBox<>();
        rellenos.addItem(true);
        rellenos.addItem(false);

        JLabel f1 = new JLabel("colores");
        JComboBox<String> colores = new JComboBox<>();
        colores.addItem("azul");
        colores.addItem("rojo");
        colores.addItem("pink");


        //botones.add(a1);
        //botones.add(a11);
        //botones.add(b1);
        //botones.add(b11);
        botones.add(c1);
        botones.add(c11);
        botones.add(d1);
        botones.add(figuras);
        //botones.add(e1);
        botones.add(re);
        botones.add(rellenos);
        botones.add(f1);
        botones.add(colores);

        this.add(botones, BorderLayout.NORTH);

        JVentana ventana = this;

        lienzo.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){

                int r = Integer.parseInt(c11.getText());

                int x = e.getX() - r/2;
                int y = e.getY() - r/2;

                String colour = (String) colores.getSelectedItem();

                Color color = Color.RED;

                if (colour == "azul"){
                    color = Color.BLUE;
                } else if (colour == "rojo"){
                    color = Color.RED;
                } else if (colour == "pink"){
                    color = Color.PINK;
                }

                Boolean relleno = (Boolean) rellenos.getSelectedItem();

                String figure = (String) figuras.getSelectedItem();

                Figura figura = new Circulo(x, y, false, color, r);

                if(figure == "circulo"){
                    figura = new Circulo(x, y, relleno, color, r);
                } else if (figure == "cuadrado"){
                    figura = new Cuadrado(x, y, relleno, color, r);
                }

                ventana.pintar(figura);

        }});

        /*lienzo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e){

                int r = Integer.parseInt(c11.getText());

                int x = e.getX() - r/2;
                int y = e.getY() - r/2;

                String colour = (String) colores.getSelectedItem();

                Color color = Color.RED;

                if (colour == "azul"){
                    color = Color.BLUE;
                } else if (colour == "rojo"){
                    color = Color.RED;
                } else if (colour == "pink"){
                    color = Color.PINK;
                }

                Boolean relleno = (Boolean) rellenos.getSelectedItem();

                String figure = (String) figuras.getSelectedItem();

                Figura figura = new Circulo(x, y, false, color, r);

                if(figure == "circulo"){
                    figura = new Circulo(x, y, relleno, color, r);
                } else if (figure == "cuadrado"){
                    figura = new Cuadrado(x, y, relleno, color, r);
                }

                ventana.pintar(figura);

        }}); */
        
        this.setVisible(true);

    }

    public Dibujo getDibujo(){
        return  dibujo;
    }

    public void pintar(Figura f){
        dibujo.añadir_figura(f);
        lienzo.repaint();       //java crea el objeto graphics y llama al lienzo.paint(g), por eso no se hace lienzo.paint
    }

    public static void main( String args[]){
    new JVentana();
}
}

import java.util.ArrayList;

public class Dibujo {

    public ArrayList<Figura> figuras;

    public Dibujo(){
        this.figuras = new ArrayList<>();
    }

    
    public void añadir_figura(Figura f){
        figuras.add(f);
    }


    public ArrayList<Figura> get_figuras(){
        return figuras;
    }
    
}

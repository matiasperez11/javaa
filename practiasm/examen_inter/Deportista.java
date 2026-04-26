import java.util.ArrayList;

public abstract class Deportista{

    protected String nombre;
    protected double peso;
    protected String patrocinador;
    protected ArrayList <Entrenamiento> entrenamientos;   // <Entrenamiento> nombre de los entrenamientos, podria ser <> entenamientosfuerza pej.

    public Deportista(String nombre, int peso, String patrocinador){
        this.nombre = nombre;
        this.peso = peso;
        this.patrocinador = patrocinador;
        this.entrenamientos = new ArrayList<>();
    }

    public void addEntrenamientos(Entrenamiento e){
        entrenamientos.add(e);
    }

    public double kcal_basal(){
        return 24*peso*7;
    }

    public abstract double kcal_competicion();  //los metodos abstract del padre no tienen nada dentro de {}, se omite

    public abstract double tiempo_recuperacion();

    public abstract int getDeporte();
    

    public double gasto_energetico_semanal(){

        double kcalent = 0;

        for (Entrenamiento e: entrenamientos){
            kcalent += e.kcal_entrenamiento(peso);
        }
        return kcal_basal() + kcalent + kcal_competicion();     //este kcal compoeticion pillara el del hijo por lo de override
    }


    public String toString(){
        String tot = "Nombre: " + this.nombre + ", peso: " + this.peso + ", patrocinador: " + patrocinador +", \n";

        for (Entrenamiento e: entrenamientos){
            tot += e.toString() + ",";
        }
        return tot;
      }
}
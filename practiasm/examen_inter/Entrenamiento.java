public class Entrenamiento {
    
    public String tipo;
    public int intensidad;
    public int duracion;

    public Entrenamiento(String tipo, int intensidad, int duracion){
        this.tipo =tipo;
        this.intensidad = intensidad;
        this. duracion = duracion;
    }

    public double kcal_entrenamiento(double peso){
        double kcal = 0;
        kcal = duracion * intensidad * peso *0.08;
        return kcal;
    }


    public String getTipo(){
        return this.tipo;
    }

    public String toString(){
        return "Entrenamientos: "+ tipo +" " + duracion + " (" + intensidad +")" ;
    }
    
}

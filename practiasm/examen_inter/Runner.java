public class Runner extends Deportista {

    public int km;

    public Runner(String nombre, int peso, String patrocinador, int km){
        super(nombre, peso, patrocinador);
        this.km = km;
    }

    @Override
    public double tiempo_recuperacion(){
        int intot = 0;
        double media = 0;
        for (Entrenamiento e: entrenamientos){
            intot = e.intensidad;
            media = intot / entrenamientos.size();
        }
        return km/10 + media;
    }

    @Override
    public double kcal_competicion(){return 0;}

    @Override
    public int getDeporte(){return 0;}
    
}

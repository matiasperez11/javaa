public class Tenista extends Deportista{

    //como no tiene ningun rasgo propio de tenista, hereda todo:

    public Tenista(String nombre, int peso, String patrocinador){
        super(nombre, peso, patrocinador);
    }

    @Override
    public double tiempo_recuperacion(){

        double minsgym = 0;
        double minspista = 0;

        for (Entrenamiento e: entrenamientos){
            if (e.getTipo().equals("Gimnasio")){
                minsgym += e.duracion;
            }
            if (e.getTipo().equals("Pista")){
                minspista += e.duracion;
            }
        }
        
        return ( minspista/60 ) + ( minsgym/120 );

    }

    @Override
    public double kcal_competicion(){
        return  Competicion.getCompeticionesSemanales(this.nombre, 2) * 0.11;
    }
    

    @Override
    public int getDeporte(){
        return Competicion.TENIS;
    }
}


public class Futbolista extends Deportista {
    
    //public String nombre; 
    //public int peso;                  estas no se ponen aqui pq ya las tienee el padre, si tuviera un rasgo propio solo de futbolista si q declararia alguno nuevo
    //public String patrocinador;       
   // public Arraylist <Entrenamiento> entrenamientos;

    public Futbolista(String nombre, int peso, String patrocinador){
        super(nombre, peso, patrocinador);
    }
    
    @Override
    public double tiempo_recuperacion(){
        double minstot = 0;
        for (Entrenamiento e : entrenamientos){
            return minstot += e.duracion;
        }
        return ( Competicion.getCompeticionesSemanales(this.nombre, 1) * 10 ) + (minstot/100);  //rhis.nombre para que pille el nombre de ESTE deportista cuando se use
    }


    @Override
    public double kcal_competicion(){
        return Competicion.getCompeticionesSemanales(this.nombre, 1) * 900;
    }

    @Override
    public int getDeporte(){
        return Competicion.FUTBOL;
    }
}

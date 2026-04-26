import java.util.HashSet;

public static void main (String argvs[]) {

    Deportista Messi = new Futbolista("Messi", 80, "adidas");       //primero lo del padre luego lo del hijo. entonces en el hash los ppuedpo meter como deportsitas,
    Deportista Carlos = new Tenista("Carlos", 85, "Nike");
    Deportista Kristian = new Runner("Kristian", 92, "RedBull", 102);

    Entrenamiento fut_rondo = new Entrenamiento("rondo", 4, 60);
    Entrenamiento tenis_gym = new Entrenamiento("Gimnasio", 7, 120);
    Entrenamiento tenis_pista = new Entrenamiento("Pista", 9, 160);
    //Entrenamiento runner_trote = new Entrenamiento("trote", 2, 90);
    
    Messi.addEntrenamientos(fut_rondo);
    Carlos.addEntrenamientos(tenis_gym);
    Carlos.addEntrenamientos(tenis_pista);
    Kristian.addEntrenamientos(new Entrenamiento("trote", 2, 90));

    HashSet<Deportista> deportistas = new HashSet<>();      //dentro de un metodo no se pone modacceso delante: pej public HashSet <Deportista> deportistas = new HashSet<>();
    deportistas.add(Messi);
    deportistas.add(Carlos);
    deportistas.add(Kristian);

    for (Deportista d: deportistas){
        System.out.println(d.toString());
        System.out.println("Gasto energetico semanal: " + d.gasto_energetico_semanal());
        System.out.println("Tiempo de recuperacion: " + d.tiempo_recuperacion());
        System.out.println("competis semanales. " + Competicion.getCompeticionesSemanales(d.nombre, d.getDeporte()));
        System.out.println("------------------------------------------------------------------------");
    }

}

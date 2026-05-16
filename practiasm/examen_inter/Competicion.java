import java.util.Random; 
public class Competicion { 
    // Campos estáticos para identificar los deportes 

    public static int FUTBOL = 1; 
    public static int TENIS = 2; 

    // Método que devuelve un número aleatorio entre 0 y 3 
    
    public static int getCompeticionesSemanales(String nombre, int deporte){  
        Random rand = new Random(); 
        return rand.nextInt(4); } } // devuelve 0, 1, 2 o 3 
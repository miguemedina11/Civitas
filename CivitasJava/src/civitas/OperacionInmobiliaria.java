package civitas;

public class OperacionInmobiliaria {
    int numPropiedad;
    GestionesInmobiliarias gestion;
    
    public OperacionInmobiliaria(GestionesInmobiliarias g, int i){
        gestion = g;
        numPropiedad = i;
    }
    
    public GestionesInmobiliarias getGestion(){
        return gestion;
    }
    
    public int getNumPropiedad(){
        return numPropiedad;
    }
}

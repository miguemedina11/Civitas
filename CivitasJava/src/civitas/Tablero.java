package civitas;

import java.util.ArrayList;

public class Tablero {
    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;
    
    public Tablero(int numCasillaCarcel){
        if(numCasillaCarcel >= 1){
            this.numCasillaCarcel = numCasillaCarcel;
        }
        
        else{
            this.numCasillaCarcel = 1;
        }
        
        this.casillas = new ArrayList<Casilla>();
        Casilla salida = new Casilla ("Salida");
        casillas.add(salida);
        
        porSalida=0;
        tieneJuez=false;        
    }
    
    private boolean correcto(){
        return((casillas.size() > numCasillaCarcel) && tieneJuez);
    }
    
    private boolean correcto(int numCasilla){
        return(correcto() && numCasilla >= 0 && numCasilla < casillas.size());
    }
    
     int getCarcel(){
        return numCasillaCarcel;
    }
    
    int getPorSalida(){
        int auxPorSalida = porSalida;
        
        if(porSalida>0){
            porSalida--;
        }
        
        return auxPorSalida;
    }
    
    
    void añadeCasilla(Casilla casilla){
        if(casillas.size() == numCasillaCarcel){
            Casilla carcel = new Casilla("Cárcel");
            casillas.add(carcel);
        }
        
        casillas.add(casilla);
        
        if(casillas.size() == numCasillaCarcel){
            Casilla carcel = new Casilla("Cárcel");
            casillas.add(carcel);
        }
    }
    
    void añadeJuez(){
        if(!tieneJuez){
            CasillaJuez juez = new CasillaJuez(numCasillaCarcel, "Juez");
            casillas.add(juez);
            tieneJuez=true;
        }
    }
    
    Casilla getCasilla(int numCasilla){
        Casilla casilla=null;
        if(correcto(numCasilla)){
            casilla = casillas.get(numCasilla);           
        }
        
        return casilla;
    }
    
    public ArrayList<Casilla> getCasillas(){
        return casillas;
    }
    
    int nuevaPosicion(int actual, int tirada){
        int nuevaPosicion = -1;

        if(correcto()){
            nuevaPosicion = actual + tirada;
            if (nuevaPosicion > casillas.size()){
                nuevaPosicion = nuevaPosicion%casillas.size();
                porSalida++;
            }

        }

        return nuevaPosicion;
    }
    
    int calcularTirada(int origen, int destino){
        int tirada = destino - origen;
        
        if(tirada < 0){
            tirada = tirada + casillas.size();           
            porSalida++;
        }
        
        return tirada;
    }
}

package civitas;

import java.util.*;

public abstract class Sorpresa {
    String texto;
    int valor;
    MazoSorpresas mazo;
    Tablero tablero;
    
    void init(){
        valor=-1;
        mazo=null;
        tablero=null;
    }
    
    Sorpresa(String texto){
        init();
        this.texto=texto;
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        boolean esJugadorCorrecto=false;
        
        if(actual >= 0 && actual < todos.size()){
            esJugadorCorrecto=true;
        }
        
        return esJugadorCorrecto;
    }
            
    void informe(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            Diario.getInstance().ocurreEvento(todos.get(actual).getNombre() + ": " + this.toString());
        } 
    }
    
    abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    
    public String toString(){
        return texto;
    }
}


package civitas;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla{

    CasillaSorpresa(MazoSorpresas mazo, String nombre){
        super(nombre);
        this.mazo=mazo;
    }

    @Override
    public void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            todos.get(actual).puedeComprar=false;
            informe(actual, todos);
            sorpresa=mazo.siguiente();
            sorpresa.aplicarAJugador(actual, todos);
        }
    }

    @Override
     public String toString(){

        String str="******************************";
        str+="\n " + getNombre() + " ¯|(º o)/¯";
        str+="\n******************************\n";
    
        return str;
    }
}
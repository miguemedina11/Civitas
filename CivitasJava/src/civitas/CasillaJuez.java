package civitas;

import java.util.ArrayList;

public class CasillaJuez extends Casilla{

    CasillaJuez(int numCasillaCarcel, String nombre){
        super(nombre);
        carcel=numCasillaCarcel;
    }

    @Override
    public void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            todos.get(actual).puedeComprar = false;
            informe(actual, todos);
            todos.get(actual).encarcelar(carcel);
        }
    }

    @Override
    public String toString(){

        String str="******************************";
        str+="\n " + getNombre() + "    |    Ve a la Carcel (◣_◢)";
        str+="\n******************************\n";
    
        return str;
    }
}
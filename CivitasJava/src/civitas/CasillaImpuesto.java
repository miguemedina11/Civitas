package civitas;

import java.util.ArrayList;

public class CasillaImpuesto extends Casilla{

    CasillaImpuesto(float cantidad, String nombre){
        super(nombre);
        importe=cantidad;
    }

    @Override
    public void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            todos.get(actual).puedeComprar=false;
            informe(actual, todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }

    @Override
    public String toString(){

        String str="******************************";
        str+="\n " + getNombre() + "    |    " + importe;
        str+="\n******************************\n";
    
        return str;
    }
}
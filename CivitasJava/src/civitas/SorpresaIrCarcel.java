package civitas;

import java.util.ArrayList;

public class SorpresaIrCarcel extends Sorpresa{

    SorpresaIrCarcel(Tablero tablero, String texto){
        super(texto);
        this.tablero=tablero;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            todos.get(actual).encarcelar(this.tablero.getCarcel());
        }
    }
}
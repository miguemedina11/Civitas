package civitas;

import java.util.ArrayList;

public class SorpresaIrACasilla extends Sorpresa{

    SorpresaIrACasilla(Tablero tablero, int valor, String texto){
        super(texto);
        this.tablero=tablero;
        this.valor=valor;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).moverACasilla(valor);
            //int casillaActual=todos.get(actual).getNumCasillaActual();
            //int tirada=tablero.calcularTirada(casillaActual,valor);
            //int posicionNueva=this.tablero.nuevaPosicion(casillaActual,tirada);
            //todos.get(actual).moverACasilla(valor);
            //tablero.getCasilla(valor).recibeJugador(actual,todos);
        }
    }
}
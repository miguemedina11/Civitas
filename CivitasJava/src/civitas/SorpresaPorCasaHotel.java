package civitas;

import java.util.ArrayList;

public class SorpresaPorCasaHotel extends Sorpresa{

    SorpresaPorCasaHotel(int valor, String texto){
        super(texto);
        this.valor=valor;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(super.jugadorCorrecto(actual, todos)){
            super.informe(actual, todos);
            Jugador jugador=todos.get(actual);
            jugador.modificarSaldo(valor*jugador.cantidadCasasHoteles());
        }
    }
}
package civitas;

import java.util.ArrayList;

public class SorpresaPagarCobrar extends Sorpresa{
 
    SorpresaPagarCobrar(int valor, String texto){
        super(texto);
        this.valor=valor;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }
}
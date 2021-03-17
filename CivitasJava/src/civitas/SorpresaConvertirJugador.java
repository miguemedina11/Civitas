package civitas;

import java.util.*;

public class SorpresaConvertirJugador extends Sorpresa{

    SorpresaConvertirJugador(String texto, int valor){
        super(texto);
        this.valor=valor;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            JugadorEspeculador nuevo = new JugadorEspeculador(todos.get(actual), valor);
            todos.set(actual, nuevo);
        }
    }
}
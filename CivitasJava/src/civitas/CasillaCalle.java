package civitas;

import java.util.ArrayList;

public class CasillaCalle extends Casilla{
    
    CasillaCalle(TituloPropiedad titulo){
        super(titulo.getNombre());
        importe = titulo.getPrecioCompra();
        tituloPropiedad=titulo;
    }

    @Override
    public void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            Jugador jugador=todos.get(actual);

            if(!tituloPropiedad.tienePropietario()){
                jugador.puedeComprarCasilla();
                jugador.puedeComprar=true;
            }

            else{
                tituloPropiedad.tramitarAlquiler(jugador);
                jugador.puedeComprar=false;
            }
        }
    }

    @Override
    public String toString(){
        String str=tituloPropiedad.toString();


        //str="******************";
        //str+="Calle " + getNombre() + "    |    titulo propiedad: " + tituloPropiedad;
        //str+="******************";
        
        return str;
    }

}
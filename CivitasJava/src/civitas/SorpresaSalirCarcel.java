package civitas;

import java.util.ArrayList;

public class SorpresaSalirCarcel extends Sorpresa{

    SorpresaSalirCarcel(MazoSorpresas mazo, String texto){
        super(texto);
        this.mazo=mazo;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            boolean encontrado=false;
            for(int i=0; i<todos.size() && !encontrado;i++){
                if(todos.get(i).tieneSalvoconducto()){
                    encontrado=true;
                }
            }
            
            if(!encontrado){
                todos.get(actual).obtenerSalvoconducto(this);
                salirDelMazo();
            }
        }
    }

    void salirDelMazo(){
        mazo.inhabilitarCartaEspecial(this);
    }

    void usada(){
        mazo.habilitarCartaEspecial(this);
    }
}
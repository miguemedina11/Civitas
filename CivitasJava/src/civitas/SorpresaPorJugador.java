package civitas;

import java.util.ArrayList;

public class SorpresaPorJugador extends Sorpresa{

    SorpresaPorJugador(int valor, String texto){
        super(texto);
        this.valor=valor;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            
            int valor_pagar= valor*-1;
            SorpresaPagarCobrar pagar=new SorpresaPagarCobrar(valor_pagar, "Pierdes 400 euros");
            for(int i=0;i<todos.size();i++){
                if(i!=actual){
                    pagar.aplicarAJugador(i, todos);
                }
            }
            
            int valor_cobrar= (valor*(todos.size()-1));
            SorpresaPagarCobrar cobrar=new SorpresaPagarCobrar(valor_cobrar, "Recibes todo el dinero");
            cobrar.aplicarAJugador(actual, todos);
        }
    }
}
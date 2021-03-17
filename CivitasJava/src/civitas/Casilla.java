package civitas;

import java.util.ArrayList;


public class Casilla {
    private String nombre;
    int carcel;
    float importe;
    Sorpresa sorpresa;
    TituloPropiedad tituloPropiedad;
    MazoSorpresas mazo;
    
    Casilla(String nombre){
        init();
        this.nombre=nombre;  
    }

    public String getNombre(){
        return nombre;
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        boolean bool = false;
        if(actual>=0 && actual<todos.size()){
            bool=true;
        }
        return bool;
    }

    public String toString(){

        String str="******************************";
        str+="\n " + getNombre() + " ‹(ᵒᴥᵒ )›";
        str+="\n******************************\n";
    
        return str;
    }
    
    TituloPropiedad getTituloPropiedad(){
        return this.tituloPropiedad;
    }
    
    public void recibeJugador(int actual, ArrayList<Jugador> todos){
        informe(actual, todos);
    }
    
    void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(actual).getNombre() + " ha caido en la casilla: " + this.getNombre());
                
    }

    void init(){
        nombre=null;
        tituloPropiedad=null;
        importe=0;
        carcel=0;
        sorpresa=null;
        mazo=null;
    }
}

package civitas;

import java.util.*;

public class MazoSorpresas {
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    
    private void init(){
        this.sorpresas = new ArrayList<Sorpresa>();
        this.cartasEspeciales = new ArrayList<Sorpresa>();
        this.barajada=false;
        this.usadas=0;
    }
    
    MazoSorpresas(boolean debug){
        this.debug=debug;
        init();
        if(debug){
            Diario.getInstance().ocurreEvento("El modo debug esta activado\n");
        }
    }
    
    MazoSorpresas(){
        debug=false;
        init();
    }
    
    void alMazo(Sorpresa s){
        if(!barajada){
            sorpresas.add(s);
        }
    }
    
    Sorpresa siguiente(){
        if(!barajada){
            usadas=0;
            Collections.shuffle(sorpresas);
            barajada = true;
        }
        usadas++;
        sorpresas.add(sorpresas.get(0));
        ultimaSorpresa = sorpresas.get(0);
        sorpresas.remove(0);
        
        return ultimaSorpresa;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        boolean encontrado=false;
        int posicionEncontrado=0;
        for(int i=0;i<sorpresas.size() || !encontrado;i++){
            if(sorpresas.get(i) == sorpresa){
                encontrado=true;
                posicionEncontrado=i;
            }
        }
        if(encontrado){
            sorpresas.remove(posicionEncontrado);
            cartasEspeciales.add(sorpresa);
            Diario.getInstance().ocurreEvento("Se ha inhabilitado una carta especial\n");
        }
    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa){
        boolean encontrado=false;
        int posicionEncontrado=0;
        for(int i=0;i<cartasEspeciales.size() || !encontrado;i++){
            if(cartasEspeciales.get(i) == sorpresa){
                encontrado=true;
                posicionEncontrado=i;
            }
        }
        if(encontrado){
            cartasEspeciales.remove(posicionEncontrado);
            sorpresas.add(sorpresa);
            Diario.getInstance().ocurreEvento("Se ha habilitado una carta especial\n");
        }
    }
    
    Sorpresa getUltimaSorpresa(){
        return ultimaSorpresa;
    }
}

package civitas;

import GUI.*;
import java.util.*;

public class Jugador implements Comparable<Jugador>{
    protected static int CasasMax=4;
    protected static int CasasPorHotel=4;
    protected boolean encarcelado;
    protected static int HotelesMax=4;
    private String nombre;
    private int numCasillaActual;
    protected static float PasoPorSalida=1000;
    protected static float PrecioLibertad=200;
    boolean puedeComprar;
    private float saldo;
    private static float SaldoInicial=7500;
    SorpresaSalirCarcel salvoconducto;
    ArrayList<TituloPropiedad> arrayPropiedades;
    boolean especulador;
            
    boolean vender(int ip){
        boolean done = false;
        if(!encarcelado){
            if(existeLaPropiedad(ip)){
                boolean done2 = arrayPropiedades.get(ip).vender(this);
                if(done2){
                    arrayPropiedades.remove(ip);
                    Diario.getInstance().ocurreEvento("Se vende propiedad ");
                    done=true;
                }
            }  
        }
        
        return done;
    }
    
    public String toString(){
        String mensaje="******************************\n";
        mensaje += nombre + "\n******************************\n\n"; 
        mensaje += "ENCARCELADO = " + encarcelado; 
        mensaje += "\n Numero Casilla Actual: " + numCasillaActual;   
        mensaje += "\n Saldo: " + saldo;
        mensaje += "\n Puede comprar = " + puedeComprar;
        mensaje += "\n CasasMax= " + CasasMax;
        mensaje += "\n Casas por hotel= " + CasasPorHotel;
        mensaje += "\n Hoteles max= " + HotelesMax;
        mensaje += "\n Paso por salida= " + PasoPorSalida;
        mensaje += "\n Precio Libertad = " + PrecioLibertad;
        mensaje += "\n Saldo inicial = " + SaldoInicial;
        
        return mensaje;
    }
    
    boolean tieneSalvoconducto(){
        return (salvoconducto != null);
    }
    
    boolean tieneAlgoQueGestionar(){
        return arrayPropiedades.size() > 0;
    }
    
    boolean salirCarcelTirando(){
        boolean done = false;
        if(encarcelado && Dado.getInstance().salgoDeLaCarcel()){
            done=true;
            Diario.getInstance().ocurreEvento("Jugador sale de la carcel tirando");
            encarcelado=false;
        }
        
        return done;
    }
    
    boolean salirCarcelPagando(){
        boolean done = false;
        if(encarcelado && puedeSalirCarcelPagando()){
            paga(PrecioLibertad);
            encarcelado=false;
            done=true;
            Diario.getInstance().ocurreEvento("Jugador sale de la carcel pagando");
        }
        
        return done;
    }
    
    boolean recibe(float cantidad){
        boolean done=false;
        if(!encarcelado){
            modificarSaldo(cantidad);
            done=true;
        }
        
        return done;
    }
    
    boolean puedoGastar(float precio){
        boolean puedo=(saldo >= precio);
        
        return puedo;
    }
    
    boolean puedoEdificarHotel(TituloPropiedad propiedad){
        boolean done=false;
        float precio=propiedad.getPrecioEdificar();
        if(puedoGastar(precio)){
            if(propiedad.getNumHoteles()<HotelesMax){
                if(propiedad.getNumCasas()>=CasasPorHotel){
                    done=true;
                }
            }
        }
        
        return done;
    }
    
    boolean puedoEdificarCasa(TituloPropiedad propiedad){
        boolean done=false;
        float precio=propiedad.getPrecioEdificar();
        if(puedoGastar(precio)){
            if(propiedad.getNumCasas()<CasasMax){
                done=true;
            }
        }
        
        return done;
    }
    
    private boolean puedeSalirCarcelPagando(){
        return puedoGastar(PrecioLibertad);
    }
    
    public boolean puedeComprarCasilla(){
        boolean puede=false;
        if(!encarcelado){
            if(puedeComprar){
                puede=true;
            }
        }
        
        return puede;
    }
    
    void perderSalvoconducto(){
        Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " pierde salvoconducto");
        salvoconducto.usada();
        salvoconducto=null;
    }
    
    boolean pasaPorSalida(){
        modificarSaldo(PasoPorSalida);
        Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " pasa por la casilla de salida");
        return true;
    }
    
    boolean pagaImpuesto(float cantidad){
        boolean done=false;
        if(!encarcelado){
            done=paga(cantidad);    
        }
        
        return done;
    }
    
    boolean pagaAlquiler(float cantidad){
        boolean done=false;
        if(!encarcelado){
            paga(cantidad);
            done=true;
        }
        
        return done;
    }
    
    boolean paga(float cantidad){
        Diario.getInstance().ocurreEvento("Jugador " + getNombre() + " paga " + cantidad + " euros");
        return modificarSaldo(cantidad * (-1));
    }
    
    boolean obtenerSalvoconducto(SorpresaSalirCarcel salvoconducto){
        boolean done=false;
        if(!encarcelado){
            this.salvoconducto=salvoconducto;
            done=true;
        }
        return done;
    }
    
    boolean moverACasilla(int numCasilla){
        boolean done=false;
        if(!encarcelado){
            numCasillaActual=numCasilla;
            puedeComprar=false;
            Diario.getInstance().ocurreEvento("Jugador se mueve a la casilla " + numCasilla);
            done=true;
        }
        
        return done;
    }
    
    boolean modificarSaldo(float cantidad){
        saldo += cantidad;
        
        return true;
    }
    
    protected Jugador(Jugador otro){
        this.nombre=otro.nombre;
        this.saldo=otro.saldo;
        this.arrayPropiedades=otro.arrayPropiedades;
        this.encarcelado=otro.encarcelado;
        this.numCasillaActual=otro.numCasillaActual;
        this.puedeComprar=otro.puedeComprar;
        this.salvoconducto=otro.salvoconducto;
    }
    
    Jugador(String nombre){
        this.nombre=nombre;
        this.encarcelado=false;
        this.numCasillaActual=0;
        this.puedeComprar=true;
        this.saldo=SaldoInicial;
        this.salvoconducto = null;
        this.arrayPropiedades=new ArrayList<>();
        this.especulador=false;
    }
    
    public boolean isEncarcelado(){
        return encarcelado;
    }
    
    boolean hipotecar(int ip){
        boolean result = false;
        if (!isEncarcelado()){
            if (existeLaPropiedad(ip)){
                TituloPropiedad propiedad = arrayPropiedades.get(ip);
                result = propiedad.hipotecar(this);
                if (result){
                    Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " hipoteca la propiedad " + propiedad.toString());
                }
            }
        }
        return result;
    }
    
    public float getSaldo(){
        return saldo;
    }
    
    boolean getPuedeComprar(){
        return puedeComprar;
    }
    
    public ArrayList<TituloPropiedad> getPropiedades(){
        ArrayList<TituloPropiedad> aux = new ArrayList<>();
        
        if (arrayPropiedades.size() == 1){
            aux.add(arrayPropiedades.get(0));
        }
        else{
            for (int i=0 ; i<arrayPropiedades.size(); i++){
                aux.add(arrayPropiedades.get(i));
            }
        }
        
        return aux;
    }

    public ArrayList<String> getPropiedadesString(){
        
        ArrayList<String> aux = new ArrayList<>();
        
        if (arrayPropiedades.size() == 1){
            aux.add(arrayPropiedades.get(0).getNombre());
        }
        else{
            for (int i=0 ; i<arrayPropiedades.size() ; i++){
                aux.add(arrayPropiedades.get(i).getNombre());
            }
        }
        
        return aux;
    }
    
    private float getPremioPasoSalida(){
        return PasoPorSalida;
    }
    
    private float getPrecioLibertad(){
        return PrecioLibertad;
    }
    
    int getNumCasillaActual(){
        return numCasillaActual;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    int getHotelesMax(){
        return HotelesMax;
    }
    
    int getCasasPorHotel(){
        return CasasPorHotel;
    }
    
    int getCasasMax(){
        return CasasMax;
    }
    
    boolean existeLaPropiedad(int ip){
        boolean done=false;
        if(tieneAlgoQueGestionar()){
            done = (arrayPropiedades.size() >= ip);
        }
        
        return done;
    }
    
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado()){
            moverACasilla(numCasillaCarcel);
            encarcelado=true;
            Diario.getInstance().ocurreEvento("Jugador encarcelado");
        }
        
        return encarcelado;
    }
    
    boolean enBancarrota(){
        return saldo < 0;
    }
    
    protected boolean debeSerEncarcelado(){
        boolean carcel=false;
        if(!encarcelado){
            if(!tieneSalvoconducto()){
                carcel=true;
            }
            else{
                perderSalvoconducto();
                Diario.getInstance().ocurreEvento("Jugador pierde salvoconducto");
            }
        }
        
        return carcel;
    }
    
    boolean construirHotel(int ip){
        boolean result=false;
        if(!isEncarcelado()){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = arrayPropiedades.get(ip);
                if(puedoEdificarHotel(propiedad)){
                    result=propiedad.construirHotel(this);
                    propiedad.derruirCasas(CasasPorHotel, this);
                    Diario.getInstance().ocurreEvento("El jugador " + nombre + " construye un hotel en la propiedad " + propiedad.toString());
                }
            }
        }
        
        return result;
    }
    
    
    boolean construirCasa(int ip){
        boolean result=false;
        if(!isEncarcelado()){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad= arrayPropiedades.get(ip);
                if(puedoEdificarCasa(propiedad)){
                    result=propiedad.construirCasa(this);
                    Diario.getInstance().ocurreEvento("El jugador " + nombre + " construye una casa en la propiedad " + propiedad.toString());
                }
            }
        }

        return result;
    }
    
    
    boolean comprar(TituloPropiedad titulo){
        boolean result=false;
        if(!isEncarcelado()){
            if(puedeComprarCasilla()){
                float precio=titulo.getPrecioCompra();
                if(puedoGastar(precio)){
                    result=titulo.comprar(this);
                    if(result){
                        arrayPropiedades.add(titulo);
                        Diario.getInstance().ocurreEvento("El jugador " + nombre + " compra la propiedad " + titulo.toString());
                    }
                    puedeComprar=false;
                }
            }
        }
        
        return result;
    }
    
    

    public int compareTo(Jugador otro){
        Float aux=otro.getSaldo();
        Float thisSaldo=saldo;
        int resultado = thisSaldo.compareTo(aux);
        return resultado;
    }
    
    int cantidadCasasHoteles(){
        int cantidad=0;
        for(int i=0;i<arrayPropiedades.size();i++){
            cantidad += arrayPropiedades.get(i).cantidadCasasHoteles();
        }
        
        return cantidad;
    }
    
    boolean cancelarHipoteca(int ip){
        boolean result=false;
        if(!isEncarcelado()){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad=arrayPropiedades.get(ip);
                float cantidad = propiedad.getImporteCancelarHipoteca();
                if(puedoGastar(cantidad)){
                    result=propiedad.cancelarHipoteca(this);
                    
                    if(result){
                        Diario.getInstance().ocurreEvento("El jugador " + nombre + " cancela la hipoteca de la propiedad " + ip);
                    }
                }
            }
        }
        return result;
    }
    
    public boolean isEspeculador(){
        return especulador;
    }
}

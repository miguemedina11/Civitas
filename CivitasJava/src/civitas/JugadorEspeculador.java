package civitas;

public class JugadorEspeculador extends Jugador{
    private static int FactorEspeculador=2;
    private float fianza;

    JugadorEspeculador(Jugador jugador, float fianza){
        super(jugador);
        this.fianza=fianza;
        for(int i=0; i<jugador.getPropiedades().size();i++){
            jugador.getPropiedades().get(i).actualizaPropietarioPorConversion(this);
        }
        
        especulador=true;
    }

    @Override
    protected boolean debeSerEncarcelado(){
        boolean result = false;
        if(super.debeSerEncarcelado()){
            if(!puedePagarFianza()){
                result = true;
            }
        }
        return result;
    }


    private boolean puedePagarFianza(){
        boolean result= false;

        if (getSaldo() >= fianza){
            modificarSaldo((-1.0f)*(fianza));
            result = true;
        }

        return result;
    }

    @Override 
    boolean puedoEdificarCasa(TituloPropiedad propiedad){
        boolean done=false;
        float precio=propiedad.getPrecioEdificar();
        if(puedoGastar(precio)){
            if(propiedad.getNumCasas()<getCasasMax()){
                done=true;
            }
        }
        
        return done;
    }

    @Override
    boolean puedoEdificarHotel(TituloPropiedad propiedad){
        boolean done=false;
        float precio=propiedad.getPrecioEdificar();
        if(puedoGastar(precio)){
            if(propiedad.getNumHoteles()<getHotelesMax()){
                if(propiedad.getNumCasas()>=CasasPorHotel){
                    done=true;
                }
            }
        }
        
        return done;
    }

    @Override
    int getHotelesMax(){
        return(super.getHotelesMax()*FactorEspeculador);
    }

    @Override
    int getCasasMax(){
        return(super.getCasasMax()*FactorEspeculador);
    }

    @Override
    boolean pagaImpuesto(float cantidad){
        return (super.pagaImpuesto(cantidad/FactorEspeculador));
    }

}
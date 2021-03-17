package civitas;

public class TituloPropiedad {
    private double alquilerBase;
    private static float factorInteresesHipoteca=1.1f;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    Jugador propietario;
    
    TituloPropiedad(String nom, float ab, float fr, float hb, float pc, float pe){
        nombre=nom;
        alquilerBase=ab;
        factorRevalorizacion=fr;
        hipotecaBase=hb;
        precioCompra=pc;
        precioEdificar=pe;
        numCasas=0;
        numHoteles=0;
        hipotecado=false;
        propietario=null;
    }
    
    public String toString(){
      String mensaje="******************************\n";
      mensaje += " " + nombre + "\n Propietario = ";
      if (tienePropietario()){
        mensaje+= propietario.getNombre();
      }
      else{
        mensaje += "Nadie";
      }
      // mensaje += "\nHIPOTECADO = " + hipotecado;
      // mensaje += "\n Hipoteca Base: " + hipotecaBase;
      // mensaje += "\nFactor Interes de Hioteca: " + factorInteresesHipoteca;
      mensaje += "\n Precio de Compra: " + precioCompra;
      // mensaje += "\nFactor Revalorizacion: " + factorRevalorizacion;
      // mensaje += "\n Precio Edificar: " + precioEdificar;
      mensaje += "\n " + numCasas + " casas y " + numHoteles + " hoteles (" + precioEdificar + ")";
      mensaje +=  "\n******************************\n\n"; 
      
      return mensaje;
    }
    
    void actualizaPropietarioPorConversion(Jugador jugador){
        propietario=jugador;
    }
    
    boolean cancelarHipoteca(Jugador jug){
        boolean result = false;
        if (hipotecado){
            if (esEsteElPropietario(jug)){
                hipotecado = false;
                jug.paga(getImporteCancelarHipoteca());
                result = true;
            }
        }
        
        return result;
    }
    
    int cantidadCasasHoteles(){
        return numCasas + 5 * numHoteles;
    }
    
    boolean comprar(Jugador jug){
        boolean result = false;
        if (!tienePropietario()){
            propietario = jug;
            result = true;
            jug.paga(precioCompra);
        }
        
        return result;
    }
    
    boolean construirCasa(Jugador jug){
        boolean result = false;
        if (esEsteElPropietario(jug)){
            jug.paga(precioEdificar);
            numCasas++;
            result = true;
        }
        
        return result;
    }
    
    boolean construirHotel(Jugador jug){
        boolean result = false;
        if (esEsteElPropietario(jug)){
            jug.paga(precioEdificar);
            numHoteles++;
            result = true;
        }
        
        return result;
    }
    
    boolean derruirCasas(int n, Jugador jugador){
       boolean done=false;
       if(esEsteElPropietario(jugador)){
           if(n>=numCasas){
               numCasas -= n;
               done=true;
           }
       }
       
       return done;
    }
    
    private boolean esEsteElPropietario(Jugador jugador){
        return (propietario == jugador);
    }
    
    public boolean getHipotecado(){
        return hipotecado;
    }
    
    float getImporteCancelarHipoteca(){
        return (getImporteHipoteca() * factorRevalorizacion); 
    }
    
    private float getImporteHipoteca(){
        return (hipotecaBase*(1+cantidadCasasHoteles() * 0.5f));
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public int getNumCasas(){
        return numCasas;
    }
    
    public int getNumHoteles(){
        return numHoteles;
    }
    
    private float getPrecioAlquiler(){
        float precio=0;
        if (!(hipotecado || propietarioEncarcelado())){
            precio=(float)(alquilerBase * (1.0 + cantidadCasasHoteles() * 0.5));
        }
        
        return precio;
    }
    
    float getPrecioCompra(){
        return precioCompra;
    }
    
    float getPrecioEdificar(){
        return precioEdificar;
    }
    
    private float getPrecioVenta(){
        return precioCompra + cantidadCasasHoteles() * precioEdificar * factorRevalorizacion;
    }
    
    Jugador getPropietario(){
        return propietario;
    }
    
    boolean hipotecar(Jugador jug){
        boolean salida = false;
        if (!hipotecado && esEsteElPropietario(jug)){
            jug.recibe(getImporteHipoteca());
            hipotecado = true;
            salida = true;
        }
        
        return salida;
    }
    
    private boolean propietarioEncarcelado(){
        return propietario.isEncarcelado();
    }
    
    boolean tienePropietario(){
        return (propietario != null);
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(tienePropietario() && !esEsteElPropietario(jugador)){
            float alquiler=getPrecioAlquiler();
            jugador.pagaAlquiler(alquiler);
            propietario.recibe(alquiler);
        }
    }
    
    boolean vender(Jugador jugador){
        boolean done=false;
        if(esEsteElPropietario(jugador)){
            if(!hipotecado){
                jugador.recibe(getPrecioVenta());
                propietario=null;
                numCasas=0;
                numHoteles=0;
                done=true;
            }
        }
        
        return done;
    }   
}

package civitas;

import GUI.*;

import java.util.*;

public class CivitasJuego {
    private int indiceJugadorActual;
    ArrayList<Jugador> jugadores;
    EstadosJuego estado;
    Tablero tablero;
    MazoSorpresas mazo;
    GestorEstados gestorEstados;

    public CivitasJuego(ArrayList<String> nombres){
        indiceJugadorActual=Dado.getInstance().quienEmpieza(nombres.size());
        jugadores=new ArrayList<>();
        for(int i=0;i<nombres.size();i++){
            Jugador un_jugador = new Jugador(nombres.get(i));
            jugadores.add(un_jugador);
        }
        gestorEstados=new GestorEstados();
        estado=gestorEstados.estadoInicial();
        mazo=new MazoSorpresas();
        tablero=new Tablero(5);
        inicializarMazoSorpresas(tablero);
        inicializarTablero(mazo);
    } 
    
    
    public boolean comprar(){
        Jugador jugadorActual=jugadores.get(indiceJugadorActual);
        int numCasillaActual=jugadorActual.getNumCasillaActual();
        Casilla casilla=tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo=casilla.getTituloPropiedad();
        boolean resultado=jugadorActual.comprar(titulo);
        
        return resultado;
    }
    
    
    public boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }

    public boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }
    
    public boolean finalDelJuego(){
        boolean finalJuego=false;
        for(int i=0;i < jugadores.size();i++){
            if(jugadores.get(i).enBancarrota()){
                finalJuego=true;
            }
        }
        
        return finalJuego;
    }

    public boolean hipotecar(int ip){
        return jugadores.get(indiceJugadorActual).hipotecar(ip);
    }

    public boolean cancelarHipoteca(int ip){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    } 

    public String infoJugadorTexto(){
        String str;
        str="\nNombre del jugador: " + jugadores.get(indiceJugadorActual).getNombre() +"\n";
        str+="Saldo del jugador: " + jugadores.get(indiceJugadorActual).getSaldo() +"\n";
        str+="Numero de casas y hoteles: " + jugadores.get(indiceJugadorActual).cantidadCasasHoteles() +"\n";
        str+="Casilla actual del jugador: " + jugadores.get(indiceJugadorActual).getNumCasillaActual() +"\n";
        
        for(int j=0;j<jugadores.size();j++){
            if(jugadores.get(j).enBancarrota()){
                str+=ranking().toString() + "\n";
            }
        }
        
        return str;
    }

    public boolean salirCarcelPagando(){
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }

    public boolean salirCarcelTirando(){
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }

    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual=jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion=gestorEstados.operacionesPermitidas(jugadorActual, estado);
        if(operacion==civitas.OperacionesJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operacion);
        }
        
        else if(operacion==civitas.OperacionesJuego.AVANZAR){
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }
        
        return operacion;
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        estado = gestorEstados.siguienteEstado(jugadores.get(indiceJugadorActual), estado, operacion);
    }
    
    public boolean vender(int ip){
        return jugadores.get(indiceJugadorActual).vender(ip);
    }
    
    private void avanzaJugador(){
        Jugador jugadorActual=jugadores.get(indiceJugadorActual);
        int posicionActual=jugadorActual.getNumCasillaActual();
        int tirada= Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        contabilizarPasosPorSalida(jugadorActual);
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
      while(tablero.getPorSalida() > 0){
          jugadorActual.pasaPorSalida();
      }
    }
    
    private void inicializarMazoSorpresas(Tablero tablero){
        SorpresaPagarCobrar pagar = new SorpresaPagarCobrar(1000, "te pagan 1000 euros por un premio al informatico con menos futuro");
        mazo.alMazo(pagar);

        SorpresaPagarCobrar cobrar = new SorpresaPagarCobrar(-1000, "Mensaje de un numero privado\n ******************\n HAS GANADO UN IPHONE 11\nIntroduce tus datos de la tarjeta de credito para reclamarlo\n******************\n Pierdes 1000 euros");
        mazo.alMazo(cobrar);

        SorpresaIrACasilla calle_real = new SorpresaIrACasilla(tablero, 11, "Te desplazas a la Calle Real");
        mazo.alMazo(calle_real);

        SorpresaIrACasilla carretera_sierra = new SorpresaIrACasilla(tablero, 17, "Te desplazas a la Carretera de la Sierra");
        mazo.alMazo(carretera_sierra);

        SorpresaIrACasilla ver_carcel = new SorpresaIrACasilla(tablero, 5, "Te desplazas a la Carcel de visita full chill");
        mazo.alMazo(ver_carcel);
        
        SorpresaPorCasaHotel pagar_xcasa = new SorpresaPorCasaHotel(200, "Te pagan 200 euros por cada casa y hotel que tienes");
        mazo.alMazo(pagar_xcasa);

        SorpresaPorCasaHotel cobrar_xcasa = new SorpresaPorCasaHotel(-200, "Debido a una invasion de nutrias tienes que hacer reformas en cada casa y hotel por valor de 200 euros");
        mazo.alMazo(cobrar_xcasa);

        SorpresaPorJugador pagar_jug = new SorpresaPorJugador(400, "Los keyloggers que le pasaste a tus amigos empiezan a generar sus beneficios. Cada jugador te da 400euros");
        mazo.alMazo(pagar_jug);

        SorpresaPorJugador cobrar_jug = new SorpresaPorJugador(-400, "Los otros tres jugadores hacen team y te atracan de forma conjunta. Pierdes 1200 euros que se reparten entre los tres (400 por jugador)");
        mazo.alMazo(cobrar_jug);

        SorpresaSalirCarcel salir_carcel = new SorpresaSalirCarcel(mazo, "Sales de la carcel");
        mazo.alMazo(salir_carcel);
        
        SorpresaIrCarcel ir_carcel = new SorpresaIrCarcel(tablero, "Vas a la carcel");
        mazo.alMazo(ir_carcel);

        SorpresaConvertirJugador conv_jugador = new SorpresaConvertirJugador("El jugador se convierte en jugador especulador", 200);
        mazo.alMazo(conv_jugador);
    }
    
    private void inicializarTablero(MazoSorpresas mazo){
        TituloPropiedad uno = new TituloPropiedad("Calle Velazquez", 100f, 0.5f, 500f, 500f, 750f);
        CasillaCalle velazquez = new CasillaCalle(uno);
        tablero.añadeCasilla(velazquez);
        
        TituloPropiedad dos = new TituloPropiedad("Calle San Felipe", 200f, 0.5f, 700f, 700f, 900f);
        CasillaCalle san_felipe = new CasillaCalle(dos);
        tablero.añadeCasilla(san_felipe);
        
        CasillaSorpresa sorpresa1 = new CasillaSorpresa(mazo, "Sorpresa 1");
        tablero.añadeCasilla(sorpresa1);
        
        TituloPropiedad tres = new TituloPropiedad("Calle Jardines", 500f, 0.5f, 1000f, 1000f, 1200f);
        CasillaCalle jardines = new CasillaCalle(tres);
        tablero.añadeCasilla(jardines);
        
        TituloPropiedad cuatro = new TituloPropiedad("Calle Blas Infante", 700f, 0.5f, 1200f, 1200f, 1400f);
        CasillaCalle blas_infante = new CasillaCalle(cuatro);
        tablero.añadeCasilla(blas_infante);
                
        CasillaSorpresa sorpresa2 = new CasillaSorpresa(mazo, "Sorpresa 2");
        tablero.añadeCasilla(sorpresa2);
        
        TituloPropiedad cinco = new TituloPropiedad("Calle Ramon y Cajal", 1000f, 0.5f, 1500f, 1500f, 1700f);
        CasillaCalle ramon_y_cajal = new CasillaCalle(cinco);
        tablero.añadeCasilla(ramon_y_cajal);
        
        TituloPropiedad seis = new TituloPropiedad("Calle Monteneros", 1100f, 0.5f, 1600f, 1600f, 1800f);
        CasillaCalle monteneros = new CasillaCalle(seis);
        tablero.añadeCasilla(monteneros);
        
        Casilla parking = new Casilla("Parking de la ETSIIT");
        tablero.añadeCasilla(parking);
        
        TituloPropiedad siete = new TituloPropiedad("Calle Real", 1200f, 0.5f, 1700f, 1700f, 1900f);
        CasillaCalle real = new CasillaCalle(siete);
        tablero.añadeCasilla(real);
        
        TituloPropiedad ocho = new TituloPropiedad("Calle Nueva", 1300f, 0.5f, 1800f, 1800f, 2000f);
        CasillaCalle nueva = new CasillaCalle(ocho);
        tablero.añadeCasilla(nueva);
        
        CasillaSorpresa sorpresa3 = new CasillaSorpresa(mazo, "Sorpresa 3");
        tablero.añadeCasilla(sorpresa3);
        
        TituloPropiedad nueve = new TituloPropiedad("Calle Elvira", 1500f, 0.5f, 2000f, 2000f, 2200f);
        CasillaCalle elvira = new CasillaCalle(nueve);
        tablero.añadeCasilla(elvira);
                
        tablero.añadeJuez();
                
        TituloPropiedad diez = new TituloPropiedad("Paseo De Los Tristes", 1600f, 0.5f, 2100f, 2100f, 2300f);
        CasillaCalle paseo_tristes = new CasillaCalle(diez);
        tablero.añadeCasilla(paseo_tristes);
        
        TituloPropiedad once = new TituloPropiedad("Carretera De La Sierra", 1700f, 0.5f, 2200f, 2200f, 2400f);
        CasillaCalle carretera_sierra = new CasillaCalle(once);
        tablero.añadeCasilla(carretera_sierra);
        
        CasillaImpuesto impuesto = new CasillaImpuesto(1500, "Impuesto");
        tablero.añadeCasilla(impuesto);
        
        TituloPropiedad doce = new TituloPropiedad("Calle Recogidas", 2000f, 0.5f, 2500f, 2500f, 3000f);
        CasillaCalle recogidas = new CasillaCalle(doce);
        tablero.añadeCasilla(recogidas);
    }
    
    private void pasarTurno(){
        indiceJugadorActual++;
        if(indiceJugadorActual>=jugadores.size()){
            indiceJugadorActual=0;
        }
    }
    
    public ArrayList<Jugador> ranking(){
        ArrayList<Jugador> resultado = jugadores;
        Collections.sort(resultado);
        Collections.reverse(resultado);
        return resultado;
    }

    public Casilla getCasillaActual(){
        int numCasilla=jugadores.get(indiceJugadorActual).getNumCasillaActual();
        
        return tablero.getCasilla(numCasilla);
    }
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }
}

    

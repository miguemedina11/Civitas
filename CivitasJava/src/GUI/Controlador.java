package GUI;

import civitas.CivitasJuego;
import civitas.OperacionesJuego;
import civitas.OperacionInmobiliaria;

public class Controlador {
    private CivitasJuego juego;
    private CivitasView vista;
    
    public Controlador(CivitasJuego juego, CivitasView vista){
        this.juego=juego;
        this.vista=vista;
    }
    
    public void juega(){
        vista.setCivitasJuego(juego);

        boolean finalJuego=juego.finalDelJuego();
        boolean casillamost =false;

        OperacionesJuego op;

        while (!finalJuego){

            //vista.pausa();

            op=juego.siguientePaso();

            vista.mostrarSiguienteOperacion(op);

            if(op != OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            else{
                casillamost= false;
            }

            if(!casillamost){
                vista.actualizarVista();
                casillamost=true;
            }

            finalJuego=juego.finalDelJuego();

            
            if (!finalJuego){
                if(op==civitas.OperacionesJuego.COMPRAR){
                    if(juego.getJugadorActual().puedeComprarCasilla()){
                        GUI.Respuestas res = vista.comprar();
                        if(res == GUI.Respuestas.SI){
                            juego.comprar();
                            juego.siguientePasoCompletado(op);
                            vista.actualizarVista();
                        }
                        else if(res == GUI.Respuestas.NO){
                            juego.siguientePasoCompletado(op);
                        }
                    }
                    else{
                        juego.siguientePasoCompletado(op);
                    }
                }
                else if(op==civitas.OperacionesJuego.GESTIONAR){
                    vista.gestionar();
                    int gest = vista.getGestion();
                    int prop = vista.getPropiedad();
                    civitas.OperacionInmobiliaria op_inm = new OperacionInmobiliaria(civitas.GestionesInmobiliarias.values()[gest], prop);
                    if (op_inm.getGestion() == civitas.GestionesInmobiliarias.values()[0]){
                        juego.vender(prop);
                    }
                    else if (op_inm.getGestion() == civitas.GestionesInmobiliarias.values()[1]){
                        juego.hipotecar(prop);
                    }
                    else if (op_inm.getGestion() == civitas.GestionesInmobiliarias.values()[2]){
                        juego.cancelarHipoteca(prop);
                    }
                    else if (op_inm.getGestion() == civitas.GestionesInmobiliarias.values()[3]){
                        juego.construirCasa(prop);
                    }
                    else if (op_inm.getGestion() == civitas.GestionesInmobiliarias.values()[4]){
                        juego.construirHotel(prop);
                    }
                    else if (op_inm.getGestion() == civitas.GestionesInmobiliarias.values()[5]){
                        juego.siguientePasoCompletado(op);    
                    }
                }
                else if (op==civitas.OperacionesJuego.SALIR_CARCEL){
                    civitas.SalidasCarcel salidas = vista.salirCarcel();
                    if (salidas == civitas.SalidasCarcel.values()[0]){
                        juego.salirCarcelPagando();
                    }
                    else{
                        juego.salirCarcelTirando();
                    }
                    juego.siguientePasoCompletado(op);
                }
            }
            
            
        }
        
        vista.ranking();
    }
    
    
}

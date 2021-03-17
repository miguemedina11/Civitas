/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.CivitasJuego;
import java.util.ArrayList;

/**
 *
 * @author escribano
 */
public class TestP5 {
    public static void main(String[] args){
        CivitasView vista = new CivitasView();
        Dado.createInstance(vista);
        Dado.getInstance().setDebug(false);
        CapturaNombres captura = new CapturaNombres(vista, true);
        ArrayList<String> nombres = new ArrayList<>();
        nombres=captura.getNombres();
        CivitasJuego juego = new CivitasJuego(nombres);
        Controlador controlador = new Controlador(juego, vista);
        
        //vista.setCivitasJuego(juego);
        //vista.actualizarVista();
        
        controlador.juega();
    }
}

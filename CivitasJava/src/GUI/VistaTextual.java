package GUI;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VistaTextual {
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  public VistaTextual () {
    in = new Scanner (System.in);
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  SalidasCarcel salirCarcel() {
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
      new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

  Respuestas comprar() {
      int opcion = menu ("¿Quieres comprar esta calle?",
      new ArrayList<> (Arrays.asList("No","Si")));
    return (Respuestas.values()[opcion]);
  }

  void gestionar () {
  iGestion = menu ("Elige la gestion inmobiliaria deseada",
      new ArrayList<> (Arrays.asList("Vender","Hipotecar", "Cancelar hipoteca", 
              "Construir casa", "Construir hotel", "Terminar")));
  
  iPropiedad = menu ("Elige la propiedad sobre la que realizar la gestion", juegoModel.getJugadorActual().getPropiedadesString());
  }
  
  public int getGestion(){
      return iGestion;
  }
  
  public int getPropiedad(){
      return iPropiedad;
  }
    

  void mostrarSiguienteOperacion(OperacionesJuego operacion) {
      if(operacion == civitas.OperacionesJuego.AVANZAR){
          System.out.println("La siguiente operacion es AVANZAR");
      }
      
      else if(operacion == civitas.OperacionesJuego.COMPRAR){
          System.out.println("La siguiente operacion es COMPRAR");
      }
      
      else if(operacion == civitas.OperacionesJuego.GESTIONAR){
          System.out.println("La siguiente operacion es GESTIONAR");
      }
      
      else if(operacion == civitas.OperacionesJuego.PASAR_TURNO){
          System.out.println("La siguiente operacion es PASAR TURNO");
      }
      
      else{
          System.out.println("La siguiente operacion es SALIR CARCEL");
      }
  }

  void mostrarEventos() {
      while(Diario.getInstance().eventosPendientes()){
          System.out.println(Diario.getInstance().leerEvento());
      }
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
        this.actualizarVista();
  }
  
  void actualizarVista(){
      System.out.println(juegoModel.infoJugadorTexto());
      System.out.println(juegoModel.getCasillaActual().toString());         
  } 
}

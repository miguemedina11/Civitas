# encoding:utf-8 
require 'singleton'
require_relative 'dado'
require_relative 'diario'
require_relative 'estados_juego'
require_relative 'gestor_estados'
require_relative 'operaciones_juego'
require_relative 'titulo_propiedad'
require_relative 'jugador'
require_relative 'mazo_sorpresas'
require_relative 'casilla'
require_relative 'tipo_sorpresa'
require_relative 'tipo_casilla'


module Civitas
  class TestP1
    
    @contador = [0,0,0,0]
    
    
    x=0
    while x < 100
      
      empieza = Civitas::Dado.instance.quien_empieza(4)
      
      for i in 0..3
        if empieza == i
          @contador[i] = @contador[i] + 1;
        end
      end
      
      x= x+1
    end 

    
    for j in 0..3
      puts "Empieza el #{j}: " 
      puts "#{@contador[j]}\n"
    end
    
    Civitas::Dado.instance.debug=true
    puts "TIRADAS CON DEBUG"
    
    
    x=0
    while x < 10
 
      puts "#{Civitas::Dado.instance.tirar} "
      
      x=x+1
    end
    
    Civitas::Dado.instance.debug=false
    puts "TIRADAS SIN DEBUG"
    x=0
    
    while x < 10
 
      puts "#{Civitas::Dado.instance.tirar} "
      
      x=x+1
    end
    
    if Civitas::Dado.instance.salgo_de_la_carcel
      puts "Sales de la carcel"
    else
      puts "No sales de la carcel"
    end
    
    puts "Ultima tirada: #{Civitas::Dado.instance.ultimoResultado}"
    
    puts Civitas::Estados_juego::INICIO_TURNO
    puts Civitas::Operaciones_juego::SALIR_CARCEL
    
    Civitas::Diario.instance.ocurre_evento("PRUEBA")
    Civitas::Diario.instance.eventos_pendientes
    Civitas::Diario.instance.leer_evento
   
    juga = Jugador.new("Migue")
    titulo = Titulopropiedad.new("Sala Dos Hermanas", 1000, 1.1, 4000, 3000, 400) 
    
    titulo.actualizar_propietario_por_conversion(juga)
    
    puts titulo.to_string
    
    puts "*********************************"
    
    mazo = Civitas::MazoSorpresas.new()
            
    cas = Civitas::Casilla.new("1", titulo, 10, mazo, 6, Civitas::Tipo_casilla::SORPRESA)
    
    puts cas.to_string
    
  end
end

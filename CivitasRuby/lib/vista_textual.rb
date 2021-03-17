#encoding:utf-8
require_relative 'operaciones_juego'
require_relative 'jugador'
require_relative 'casilla'
require_relative 'civitas_juego'
require_relative 'gestiones_inmobiliarias'
require_relative 'salidas_carcel'
require_relative 'respuestas'
require 'io/console'

module Civitas

  class Vista_textual
    
    def initialize
      @juego_model = nil
      @i_propiedad = nil
      @i_gestion = nil
    end

    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end



    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts "  +#{index}-#{l}"
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end

    
    def comprar
      opcion = menu("¿Quieres comprar esta calle?", Civitas::Respuestas::Lista_respuestas)
      
      return Civitas::Respuestas::Lista_respuestas[opcion];
    end

    def gestionar
      @i_gestion= menu("Elige la gestion inmobiliaria deseada", Civitas::Gestiones_inmobiliarias::Lista_gestiones_inmobiliarias)
      
      if(@i_gestion != 5)
        @i_propiedad = menu("Elige la propiedad para realizar la gestion", @juego_model.get_jugador_actual.get_propiedades)
      end
    end

    def get_gestion
      return @i_gestion
    end

    def get_propiedad
      return @i_propiedad
    end

    def mostrar_siguiente_operacion(operacion)
      case operacion
      when Civitas::Operaciones_juego::AVANZAR
        puts "La siguiente operacion es AVANZAR"
        
      when Civitas::Operaciones_juego::COMPRAR
        puts "La siguiente operacion es COMPRAR"
        
      when Civitas::Operaciones_juego::GESTIONAR
        puts "La siguiente operacion es GESTIONAR"
        
      when Civitas::Operaciones_juego::PASAR_TURNO
        puts "La siguiente operacion es PASAR TURNO"
        
      when Civitas::Operaciones_juego::SALIR_CARCEL
        puts "Las siguiente operacion es SALIR CARCEL"
      end
    end

    def mostrar_eventos
      while(Diario.instance.eventos_pendientes)
        puts Diario.instance.leer_evento
      end
    end

    def set_civitas_juego(civitas)
         @juego_model=civitas
         self.actualizar_vista
    end

    def actualizar_vista
      @juego_model.info_jugador_texto
      puts @juego_model.get_casilla_actual.to_string
    end
    
    def salir_carcel
      opcion = menu("¿Elige la forma para intentar salir de la carcel", Civitas::Salidas_carcel::Lista_salidas_carcel)
      
      return Civitas::Salidas_carcel::Lista_salidas_carcel[opcion];
    end
  end
end

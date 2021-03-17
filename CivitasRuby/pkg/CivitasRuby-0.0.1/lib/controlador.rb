#encoding:utf-8

require_relative 'vista_textual'
require_relative 'civitas_juego'
require_relative 'diario'
require_relative 'respuestas'
require_relative 'controlador'
require_relative 'operacion_inmobiliaria'
require_relative 'gestiones_inmobiliarias'
require_relative 'casilla'

module Civitas
  class Controlador
    

    def initialize(j,v)
      @juego=j
      @vista=v
    end
    
    def juega
      @vista.set_civitas_juego(@juego)
      casillamost=false
      
      while !@juego.final_del_juego

        @vista.pausa
       
        op= @juego.siguiente_paso

        @vista.mostrar_siguiente_operacion(op)
        
        if op != Operaciones_juego::PASAR_TURNO
          puts "\n*********************EVENTOS**********************\n"
          @vista.mostrar_eventos
          puts "**************************************************\n"
        else
          casillamost=false
        end
        
        if !casillamost
          @vista.actualizar_vista
          casillamost=true
        end
        
        
        if !@juego.final_del_juego
          case op
          when Operaciones_juego::COMPRAR
            if(@juego.get_jugador_actual.puede_comprar_casilla)
              res = @vista.comprar
              if res == Civitas::Respuestas::Lista_respuestas[1]
                @juego.comprar
                @juego.siguiente_paso_completado(op)
              elsif res == Civitas::Respuestas::Lista_respuestas[0]
                @juego.siguiente_paso_completado(op)
              end
            else
              @juego.siguiente_paso_completado(op)
            end
          when Operaciones_juego::GESTIONAR
            @vista.gestionar
            gest = @vista.get_gestion
            prop = @vista.get_propiedad
            op_inm = Operacion_inmobiliaria.new(Civitas::Gestiones_inmobiliarias::Lista_gestiones_inmobiliarias[gest],prop)
            case op_inm.get_gestion
            when Civitas::Gestiones_inmobiliarias::Lista_gestiones_inmobiliarias[0]
              @juego.vender(prop)
            when Civitas::Gestiones_inmobiliarias::Lista_gestiones_inmobiliarias[1]
              @juego.hipotecar(prop)
            when Civitas::Gestiones_inmobiliarias::Lista_gestiones_inmobiliarias[2]
              @juego.cancelar_hipoteca(prop)
            when Civitas::Gestiones_inmobiliarias::Lista_gestiones_inmobiliarias[3]
              @juego.construir_casa(prop)
            when Civitas::Gestiones_inmobiliarias::Lista_gestiones_inmobiliarias[4]
              @juego.construir_hotel(prop)
            when Civitas::Gestiones_inmobiliarias::Lista_gestiones_inmobiliarias[5]
              @juego.siguiente_paso_completado(op)
            end
          when Operaciones_juego::SALIR_CARCEL
            salidas = @vista.salir_carcel
            if salidas == Salidas_carcel::Lista_salidas_carcel[0]
              @juego.salir_carcel_pagando
            else
              @juego.salir_carcel_tirando
            end
            @juego.siguiente_paso_completado(op)
          end
        end
        
        @juego.info_jugador_texto
      end
      
      
      
      jugadores = @juego.ranking
      
      for i in jugadores do
        puts i
      end
    end
  end
end
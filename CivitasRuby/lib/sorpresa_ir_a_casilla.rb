#encoding:utf-8

require_relative 'jugador'
require_relative 'tablero'
require_relative 'casilla'
require_relative 'mazo_sorpresas'
require_relative 'sorpresa'
require 'sorpresa.rb'

module Civitas
    class SorpresaIrACasilla < Sorpresa
        def initialize(tablero, valor, texto)
            super(texto)
            @tablero=tablero
            @valor=valor
        end

        def aplicar_a_jugador(actual,todos)
            if(jugador_correcto(actual, todos))
                informe(actual, todos)
                #tirada=@tablero.calcular_tirada(todos[actual].num_casilla_actual, @valor) 
                #posicion_nueva=@tablero.nueva_posicion(todos[actual].num_casilla_actual, tirada)
                todos[actual].mover_a_casilla(@valor)
                #@tablero.get_casilla(@valor).recibe_jugador(actual, todos)
            end
        end

        public_class_method :new
    end
end
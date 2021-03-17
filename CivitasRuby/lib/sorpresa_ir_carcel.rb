#encoding:utf-8

require_relative 'jugador'
require_relative 'tablero'
require_relative 'casilla'
require_relative 'mazo_sorpresas'
require_relative 'sorpresa'
require 'sorpresa.rb'

module Civitas
    class SorpresaIrCarcel < Sorpresa
        def initialize(tablero, texto)
            super(texto)
            @tablero=tablero
        end

        def aplicar_a_jugador(actual, todos)
            if(jugador_correcto(actual, todos))
                informe(actual,todos)
                todos[actual].encarcelar(@tablero.get_num_casilla_carcel)
            end
        end

        public_class_method :new
    end
end
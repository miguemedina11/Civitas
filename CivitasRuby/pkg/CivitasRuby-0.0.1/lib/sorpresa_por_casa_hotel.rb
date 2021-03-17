#encoding:utf-8

require_relative 'jugador'
require_relative 'tablero'
require_relative 'casilla'
require_relative 'mazo_sorpresas'
require_relative 'sorpresa'
require 'sorpresa.rb'

module Civitas
    class SorpresaPorCasaHotel < Sorpresa
        def initialize(valor, texto)
            super(texto)
            @valor=valor
        end

        def aplicar_a_jugador(actual, todos)
            if(jugador_correcto(actual,todos))
                informe(actual, todos)
                jugador=todos[actual]
                jugador.modifica_saldo(@valor * jugador.cantidad_casas_hoteles)
            end
        end

        public_class_method :new
    end
end
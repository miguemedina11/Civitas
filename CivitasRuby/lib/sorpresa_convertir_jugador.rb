#encoding:utf-8

require_relative 'sorpresa'
require_relative 'jugador'
require_relative 'jugador_especulador'
require_relative 'casilla'
require_relative 'mazo_sorpresas'
require 'sorpresa.rb'

module Civitas
    class SorpresaConvertirJugador < Sorpresa 
        def initialize(valor, texto)
            super(texto)
            @valor=valor
        end

        def aplicar_a_jugador(actual,todos)
            if(jugador_correcto(actual,todos))
                informe(actual,todos)
                especulador=JugadorEspeculador.nuevo_especulador(todos[actual], @valor)
                todos[actual]=especulador
            end
        end

        public_class_method :new
    end
end
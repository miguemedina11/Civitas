#encoding:utf-8

require_relative 'casilla'
require_relative 'sorpresa'
require_relative 'mazo_sorpresas'
require 'casilla.rb'

module Civitas
    class CasillaSorpresa < Casilla
        def initialize(mazo, nombre)
            super(nombre)
            @mazo=mazo
        end

        def recibe_jugador(actual, todos)
            if(jugador_correcto(actual,todos))
                todos[actual].puede_comprar=false
                informe(actual,todos)
                @sorpresa=@mazo.siguiente
                @sorpresa.aplicar_a_jugador(actual, todos)
            end
        end

        def to_string
                String::new(str="**************************************************")
                str+="\n " + @nombre + " ¯|(º o)/¯"
                str+="\n**************************************************\n"

                return str;
        end
    end
end
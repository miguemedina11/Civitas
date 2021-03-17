#encoding:utf-8

require_relative 'casilla'
require_relative 'jugador'
require 'casilla.rb'

module Civitas
    class CasillaImpuesto < Casilla
        def initialize(cantidad, nombre)
            super(nombre)
            @importe=cantidad
        end

        def recibe_jugador(actual, todos)
            if (jugador_correcto(actual,todos))
                todos[actual].puede_comprar=false
                informe(actual,todos)
                todos[actual].paga_impuesto(@importe)
            end
        end

        def to_string  
          String::new(str="**************************************************")
          str+="\n " + @nombre + "    |    " + @importe
          str+="\n**************************************************\n"
          
          return str
        end
    end
end
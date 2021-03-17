#encoding:utf-8

require_relative 'jugador'
require_relative 'casilla'
require 'casilla.rb'

module Civitas
    class CasillaJuez < Casilla 
        def initialize(num_casilla_carcel, nombre)
            super(nombre)
            @carcel=num_casilla_carcel
        end

        def recibe_jugador(actual, todos)
            if(jugador_correcto(actual,todos))
                todos[actual].puede_comprar=false
                informe(actual,todos)
                todos[actual].encarcelar(@carcel)
            end
        end

        def to_string
          String::new(str="**************************************************")
          str+="\n " + @nombre + "    |    Ve a la Carcel (◣_◢)"
          str+="\n**************************************************\n"
    
          return str;
        end
    end
end
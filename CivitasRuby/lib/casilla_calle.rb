#encoding:utf-8

require_relative 'titulo_propiedad'
require_relative 'casilla'
require 'casilla.rb'

module Civitas
    class CasillaCalle < Casilla
        def initialize(titulo)
            super(titulo.nombre)
            @importe = titulo.precio_compra
            @titulo_propiedad = titulo
        end

        def recibe_jugador(actual, todos)
            if(jugador_correcto(actual,todos))
                informe(actual,todos)
                jugador=todos[actual]
                
                if(!@titulo_propiedad.tiene_propietario)
                  jugador.puede_comprar_casilla
                  jugador.puede_comprar=true
                  
                else
                  @titulo_propiedad.tramitar_alquiler(jugador)
                  jugador.puede_comprar=false
                end
            end
        end

        def to_string
            return @titulo_propiedad.to_string 
        end
    end
end
#encoding:utf-8

require_relative 'sorpresa'
require_relative 'jugador'
require_relative 'sorpresa_pagar_cobrar'
require_relative 'tablero'
require_relative 'casilla'
require_relative 'mazo_sorpresas'
require 'sorpresa.rb'

module Civitas
    class SorpresaPorJugador < Sorpresa 
        def initialize(valor, texto)
            super(texto)
            @valor=valor
        end

        def aplicar_a_jugador(actual, todos)
            if(jugador_correcto(actual,todos))
                informe(actual, todos)
                
                valor_pagar=@valor*-1
                pagar=SorpresaPagarCobrar.new(valor_pagar, "Pierdes 400 euros")
                for i in 0..(todos.length-1)
                    if(i!=actual)
                        pagar.aplicar_a_jugador(i,todos)
                    end
                end
                
                valor_cobrar=@valor*(todos.length-1)
                cobrar = SorpresaPagarCobrar.new(valor_cobrar, "Recibes todo el dinero")
                cobrar.aplicar_a_jugador(actual,todos)
            end
        end

        public_class_method :new
    end
end
            
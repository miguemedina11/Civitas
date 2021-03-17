#encoding:utf-8

require_relative 'jugador'
require_relative 'tablero'
require_relative 'casilla'
require_relative 'mazo_sorpresas'
require_relative 'sorpresa'
require 'sorpresa.rb'

module Civitas
    class SorpresaSalirCarcel < Sorpresa
        def initialize(mazo, texto)
            super(texto)
            @mazo=mazo
        end

        def aplicar_a_jugador(actual, todos)
            if(jugador_correcto(actual,todos))
                informe(actual, todos)
                encontrado=false
                i=0
                while !encontrado && i < todos.length
                    if(todos[i].tiene_salvoconducto)
                        encontrado=true
                    end
                    i += 1
                end
                
                if !encontrado
                    todos[actual].obtener_salvoconducto(self)
                    salir_del_mazo
                end
            end
        end

        def salir_del_mazo
            @mazo.inhabilitar_carta_especial(self)
        end
            
        def usada
            @mazo.habilitar_carta_especial(self)
        end
        
        public_class_method :new
    end
end

require_relative 'jugador'
require_relative 'tablero'
require_relative 'casilla'
require_relative 'mazo_sorpresas'

module Civitas
  class Sorpresa
    def init
      @valor=-1
      @tablero=nil
      @mazo=nil
    end
    
    def initialize(texto)
      init
      @texto=texto
    end
    
    def jugador_correcto(actual, todos)
      es_jugador_correcto=false
      
      if(actual >= 0 && actual < todos.length)
        es_jugador_correcto=true
      end
      
      return es_jugador_correcto
    end
    
    def informe(actual, todos)
      if(jugador_correcto(actual, todos))
        Diario.instance.ocurre_evento(todos[actual].get_nombre + ": " + self.to_string)
      end
    end
    
    def to_string
      string = "#{@texto}"
      
      return string
    end
    
    private_class_method :new
  end
end
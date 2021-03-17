#encoding:utf-8

require_relative 'jugador'
require_relative 'sorpresa'
require_relative 'mazo_sorpresas'
require_relative 'titulo_propiedad'


module Civitas
  class Casilla
    attr_reader :nombre
    attr_reader :titulo_propiedad
    
    def init
      @nombre=nil
      @titulo_propiedad = nil
      @importe=nil
      @carcel=nil
      @sorpresa=nil
      @mazo=nil
    end
    
    def initialize(nombre)
      init
      @nombre=nombre
    end
   
    def informe(actual,todos)
      Diario.instance.ocurre_evento("El jugador " + todos[actual].get_nombre + " ha caido en la casilla: " + @nombre)
    end
    
    def recibe_jugador(actual, todos)
      informe(actual, todos)
    end

    def jugador_correcto(actual,todos)
      bool=false
      if(actual<todos.length && actual >= 0)
        bool=true
      end
      return bool
    end

    def to_string
      String::new(str="**************************************************")
      str+="\n " + @nombre + " ‹(ᵒᴥᵒ )›"
      str+="\n**************************************************\n"
      
      return str
    end
  end
end

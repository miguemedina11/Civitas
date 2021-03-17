# encoding:utf-8 
require 'singleton'

module Civitas
  class Dado
    include Singleton
    
    @@SalidaCarcel = 5;
    attr_reader :ultimoResultado
    attr_writer :debug
    
    def initialize ()
      @ultimoResultado = 0
      @debug = false
    end
    
    
    def tirar()
      if @debug == true
        @ultimoResultado = 1
      else
        @ultimoResultado = 1 + rand(6)
      end
      
      return @ultimoResultado
    end
    
    
    def salgo_de_la_carcel()
      numero = tirar()
      salir = false
      
      if (numero >= @@SalidaCarcel)
        salir = true
      end
      
      return salir
      
    end
    
    def quien_empieza(n)
      return (rand(n))
    end
    
  end
end

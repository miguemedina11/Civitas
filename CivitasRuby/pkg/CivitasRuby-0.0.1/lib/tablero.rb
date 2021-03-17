#encoding:utf-8

require_relative 'casilla'

module Civitas

  class Tablero
    def initialize (casilla_carcel)
      if(casilla_carcel >= 1)
        @num_casilla_carcel=casilla_carcel
      else
        @num_casilla_carcel=1
      end
      
      @por_salida=0
      @tiene_juez=false
      @casillas=[]

      salida=Casilla.new("Salida")
      @casillas << salida
    end

    def correcto
      result = (@casillas.length > @num_casilla_carcel) && (@tiene_juez)
      
      return result
    end
    
    def correcto_cas(num_casilla)
      
      result = (correcto) && (num_casilla >= 0 && num_casilla < @casillas.length)
      puts @casillas.length
      
      return result
    end
    
    attr_reader :casillas
    
    def aniade_casilla(casilla)
      if(@casillas.length == @num_casilla_carcel)
        carcel=Casilla.new("Carcel")
        @casillas << carcel
      end
      
      @casillas << casilla
      
      if(@casillas.length == @num_casilla_carcel)
        carcel=Casilla.new("Carcel")
        @casillas << carcel
      end
    end
    
    def aniade_juez
      if(@tiene_juez == false)
        juez=CasillaJuez.new(@num_casilla_carcel, "Juez")
        @casillas << juez
        @tiene_juez = true
      end
    end
    
    def get_casilla(num_casilla)
      casilla=nil
      
      if(correcto_cas(num_casilla))
        casilla=@casillas[num_casilla]
      end
      
      return casilla
    end
    
    def nueva_posicion(actual, tirada)
      posicion_nueva=-1
      
      if(correcto)
        posicion_nueva=actual+tirada
        if(posicion_nueva > @casillas.length-1)
          posicion_nueva=posicion_nueva%@casillas.length
          @por_salida += 1
        end
      end
      
      return posicion_nueva
    end
    
    def calcular_tirada(origen, destino)
      tirada=destino-origen
      
      if(tirada<0)
        tirada=tirada+@casillas.length
        @por_salida += 1
      end
      
      return tirada
    end
    
    def get_num_casilla_carcel
      return @num_casilla_carcel
    end
    
    def get_por_salida
        aux_por_salida=@por_salida

        if(@por_salida>0)
          @por_salida-=1
        end
        
        return aux_por_salida
    end
    
    
    private  :correcto ,:correcto_cas
    
    
  end 
end

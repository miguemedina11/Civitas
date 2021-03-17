require_relative 'sorpresa'

module Civitas

  class MazoSorpresas
    
    attr_reader :ultima_sorpresa
    
    def init
      @sorpresas=Array.new
      @cartas_especiales=[]
      @barajada=false
      @usadas=0
      @ultima_sorpresa=nil
    end
    
    def initialize(debug=false)
      @debug=debug
      init
      if(@debug)
        Diario.instance.ocurre_evento("El modo debug ha sido activado") 
      end
    end
    
    def al_mazo(s)
      if(!@barajada)
        @sorpresas << s
      end
    end
    
    def siguiente
      aux = nil
      if(@barajada==false)
          @usadas=0
          @sorpresas.shuffle!
          @barajada=true
      end
      @usadas+=1
      aux=@sorpresas[0]
      @sorpresas.shift
      @sorpresas << aux
      @ultima_sorpresa=aux

      return @ultima_sorpresa
    end
    
    def inhabilitar_carta_especial(sorpresa)
      encontrado=false
      posicion_encontrado=0
      i=0
      while (i<@sorpresas.length || encontrado==false)
        if(@sorpresas[i] == sorpresa)
          encontrado=true
          posicion_encontrado=i
        end 
        i += 1
      end
      
      if(encontrado==true)
        @sorpresas.delete_at(posicion_encontrado)
        @cartas_especiales << sorpresa
        Diario.instance.ocurre_evento("Se ha inhabilitado una carta especial")
      end
    end

    def habilitar_carta_especial(sorpresa)
      encontrado=false
      posicion_encontrado=0
      i=0
      while (i<@cartas_especiales.length || encontrado==false)
        if(@cartas_especiales[i] == sorpresa)
          encontrado=true
          posicion_encontrado=i
        end  
        i += 1
      end

      if(encontrado==true)
        @cartas_especiales.delete_at(posicion_encontrado)
        @sorpresas << sorpresa
        Diario.instance.ocurreEvento("Se ha habilitado una carta especial")
      end
    end

    private :init
    
  end
end

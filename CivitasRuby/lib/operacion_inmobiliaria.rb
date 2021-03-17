module Civitas
  class Operacion_inmobiliaria
    
    def initialize(g,i)
      @num_propiedad = i
      @gestion = g
    end
    
    def get_gestion
      return @gestion
    end
    
    def get_num_propiedad
      return @num_propiedad
    end
    
  end
end

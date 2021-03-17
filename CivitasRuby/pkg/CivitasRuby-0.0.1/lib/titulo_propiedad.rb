# encoding:utf-8 

require_relative 'jugador.rb'

module Civitas
  class Titulopropiedad
    
    attr_reader :nombre, :hipotecado, :num_casas, :num_hoteles, :precio_compra, :precio_edificar, :propietario
    
    @@factor_intereses_hipoteca = 1.1
    
    def initialize(n, ab, fr, hb, pc, pe)
      @nombre = n
      @alquiler_base = ab
      @factor_revalorizacion = fr
      @hipoteca_base = hb
      @precio_compra = pc
      @precio_edificar = pe      
      @hipotecado = false
      @num_casas = 0
      @num_hoteles = 0
      @propietario = 0
    end
    
    def actualizar_propietario_por_conversion (jug)
      @propietario = jug
    end
    
    def es_este_el_propietario(jug)
      return (@propietario == jug)
    end
    
    def cantidad_casas_hoteles()
      return @num_casas + 5 * @num_hoteles
    end
    
    def get_importe_hipoteca()
      return (@hipoteca_base * (1 + cantidad_casas_hoteles() * 0.5))
    end
    
    def get_importe_cancelar_hipoteca()
      return get_importe_hipoteca() * @factor_revalorizacion
    end
    
    def cancelar_hipoteca(jug) 
      result = false
      if (@hipotecado)
        if es_este_el_propietario(jug)
          @hipotecado = false
          jug.paga(get_importe_cancelar_hipoteca())
          result = true
        end
      end
      return result
    end

    def hipotecar(jug)
      salida = false
      if (!@hipotecado && es_este_el_propietario(jug))
        jug.recibe(get_importe_hipoteca)
        @hipotecado = true
        salida = true
      end
      return salida
    end
    
    def comprar(jug)
      result = false
      if (!tiene_propietario())
        @propietario = jug
        result = true
        jug.paga(precio_compra())
      end
      return result
    end
    
    def construir_casa(jug)
      result = false
      if es_este_el_propietario(jug)
        jug.paga(@precio_edificar)
        @num_casas = @num_casas + 1
        result = true
      end
      return result
    end
    
    def construir_hotel(jug)
      result = false
      if es_este_el_propietario(jug)
        jug.paga(@precio_edificar)
        @num_hoteles = @num_hoteles + 1
        result = true
      end
      return result
    end
    
    def derruir_casas(n,jug)
      done = false
      if (es_este_el_propietario(jug))
        if (n >= @num_casas)
          @num_casas -= n
          done = true
        end
      end
      return done
    end
    
    def get_precio_venta()
      return @precio_compra + cantidad_casas_hoteles() * @precio_edificar * @factor_revalorizacion
    end
    
    def vender(jug)
      done = false
      if (es_este_el_propietario(jug))
        if (!@hipotecado)
          jug.recibe(get_precio_venta())
          @propietario = 0
          @num_casas = 0
          @num_hoteles = 0
          done = true
        end
      end
      return done
    end
    
    def propietario_encarcelado()
      return @propietario.is_encarcelado()
    end
    
    def get_precio_alquiler()
      precio = 0
      if (!(@hipotecado || propietario_encarcelado))
        precio = (@alquiler_base * (1 + cantidad_casas_hoteles() * 0.5))
      end
      return precio
    end
    
    def tiene_propietario()
      return (@propietario != 0)
    end
    
    def tramitar_alquiler(jug)
      if (tiene_propietario() && !es_este_el_propietario(jug))
        alquiler = get_precio_alquiler()
        jug.paga_alquiler(alquiler)
        @propietario.recibe(alquiler)
      end     
    end
    
    def to_string()
      String::new(mensaje="\n**************************************************\n")
      mensaje += " " + @nombre + "\n Propietario = "
      if tiene_propietario()
        mensaje+= @propietario.get_nombre()
      else
        mensaje+= "Nadie"
      end
      #mensaje +=  "HIPOTECADO = " + @hipotecado.to_s 
      #mensaje += "\n Hipoteca Base: #{@hipoteca_base}"      
      #mensaje += "\nFactor Interes de Hipoteca: #{@@factor_intereses_hipoteca}"
      mensaje += "\n Precio de Compra: #{@precio_compra}"        
      #mensaje += "\nFactor Revalorizacion: #{@factor_revalorizacion}"
      #mensaje += "\n Precio Edificar: #{@precio_edificar}"
      mensaje += "\n#{@num_casas} casas y #{@num_hoteles} hoteles (#{@precio_edificar})"
      mensaje += "\n**************************************************\n\n"
      
      return mensaje
    end
    
    private :es_este_el_propietario, :get_importe_hipoteca, :get_precio_alquiler, 
      :get_precio_venta, :propietario_encarcelado

  end
end

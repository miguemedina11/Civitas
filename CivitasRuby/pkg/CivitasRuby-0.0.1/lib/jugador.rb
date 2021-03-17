# encoding:utf-8 
require_relative 'titulo_propiedad.rb'
require_relative 'sorpresa.rb'
require_relative 'diario.rb'
require_relative 'civitas_juego.rb'
require_relative 'sorpresa_salir_carcel'
require_relative 'casilla'

module Civitas
  class Jugador
    
    attr_reader :casas_max, :casas_por_hotel, :hoteles_max, :num_casilla_actual, :precio_libertad, :paso_por_salida, :puede_comprar, :propiedades, :saldo, :salvoconducto
    
    attr_writer :puede_comprar
    
    @@casas_max = 4
    @@casas_por_hotel = 4
    @@hoteles_max = 4
    @@paso_por_salida = 1000
    @@precio_libertad = 200
    @@saldo_inicial = 7500
    
    def initialize(n)
      @nombre = n
      @encarcelado = false
      @num_casilla_actual = 0
      @puede_comprar = true
      @saldo = @@saldo_inicial
      @salvoconducto = nil
      @propiedades = []
    end
    
    def copia(cop)
      @nombre=cop.get_nombre
      @saldo=cop.saldo
      @propiedades=cop.propiedades
      @encarcelado=cop.is_encarcelado
      @num_casilla_actual=cop.num_casilla_actual
      @puede_comprar=cop.puede_comprar
      @salvoconducto=cop.salvoconducto
    end
    
    def get_nombre()
      return @nombre
    end
    
    def cantidad_casas_hoteles()
      cant = 0
      for i in 0..(@propiedades.length()-1)
        cant += @propiedades[i].cantidad_casas_hoteles()
      end
      return cant
    end
    
    def tiene_salvoconducto()
      return (@salvoconducto != nil)
    end
    
    def is_encarcelado()
      return @encarcelado
    end
    
    def obtener_salvoconducto(sorpresa) #salvoconducto es tipo sorpresa
      done = false
      if (!@encarcelado)
        @salvoconducto = sorpresa
        done = true          
      end
      return done
    end
    
    def perder_salvoconducto()
      Diario.instance.ocurre_evento("Jugador pierde salvoconducto")
      @salvoconducto.usada()
      @salvoconducto = nil
    end
    
    def get_premio_paso_salida()
      return @@paso_por_salida
    end
    
    def debe_ser_encarcelado()
      carcel = false
      if (!@encarcelado)
        if (!tiene_salvoconducto())
          carcel = true
        else
          perder_salvoconducto()
        end
      end
      return carcel
    end
    
    def mover_a_casilla(num_casilla)
      done = false
      if (!@encarcelado)
        @num_casilla_actual = num_casilla
        @puede_comprar = false
        Diario.instance.ocurre_evento("Jugador se mueve a la casilla #{num_casilla}")
        done = true
      end
      return done
    end
    
    def encarcelar(num_casilla_carcel)
      if (debe_ser_encarcelado())
        mover_a_casilla(num_casilla_carcel)
        @encarcelado = true   
        Diario.instance.ocurre_evento("jugador encarcelado")
      end
      return @encarcelado
    end
    
    def puede_comprar_casilla()
      puede = false
      if (!@encarcelado)
        if(@puede_comprar)
          puede = true
        end
      end
      return puede
    end
    
    def modifica_saldo(cant)
      @saldo += cant
      
      return true
    end
    
    def paga(cant)
      return modifica_saldo(cant * (-1))
    end    
    
    def paga_impuesto(cant)
      done = false
      if (!@encarcelado)
        paga(cant)
        done = true
      end
      return done
    end
    
    def paga_alquiler(cant)
      done = false
      if (!@encarcelado)
        paga(cant)
        done = true
      end
      return done
    end
    
    def recibe (cant)
      done = false
      if (!@encarcelado)
        modifica_saldo(cant)
        done = true
      end
      
      return done
    end
    
    def puedo_gastar(precio)
      puedo = @saldo >= precio
      return puedo
    end
    
    def get_propiedades()
      return @propiedades
    end
    
    def tiene_algo_que_gestionar()
      return @propiedades.length > 0   
    end
    
    def existe_la_propiedad(ip)
      aux=ip
      done = false
        if (tiene_algo_que_gestionar())
          if(aux < (@propiedades.length))
            done = true
          end
        end
        return done
    end
    
    def get_propiedades
      aux = []
      
      if (@propiedades.length) == 1
        aux << @propiedades[0].to_string
      else
        for i in 0..(@propiedades.length-1)
          aux << @propiedades[i].to_string
        end
      end
      
      return aux
    end    
    
    def vender(ip)
      done = false
      if (!@encarcelado)
        if (existe_la_propiedad(ip))
          done2 = @propiedades[ip].vender(self)
          if done2
            @propiedades.delete_at(ip)
            Diario.instance.ocurre_evento("Se vende propiedad #{ip}")
            done = true
          end
        end
      end
      return done
    end
    
    def puede_salir_carcel_pagando()
      return puedo_gastar(@@precio_libertad)
    end

    def salir_carcel_pagando()
      done = false
      if(@encarcelado && puede_salir_carcel_pagando())
        paga(@@precio_libertad)
        @encarcelado = false
        done = true
        Diario.instance.ocurre_evento("Jugador sale de la carcel pagando")
      end
      return done
    end
    
    def salir_carcel_tirando()
      done = false
      if(@encarcelado && Dado.instance.salgo_de_la_carcel())
        @encarcelado = false
        done = true
        Diario.instance.ocurre_evento("Jugador sale de la carcel tirando")
      end
      return done
    end
    
    def pasa_por_salida()
      modifica_saldo(@@paso_por_salida)
      Diario.instance.ocurre_evento("Jugador pasa por la casilla de salida")
      return true
    end
    
    def compare_to(otro)
      return @saldo - otro.saldo
    end
    
    def get_saldo
      return @saldo
    end
    
    def en_bancarrota
      result = (@saldo < 0)
      return result
    end
    
def puedo_edificar_hotel(propiedad)
      done=false
      precio = propiedad.precio_edificar
      if puedo_gastar(precio)
        if propiedad.num_hoteles<@@hoteles_max
           if propiedad.num_casas>=@@casas_por_hotel
             done = true
           end
        end
      end
      return done
end
    
def puedo_edificar_casa(propiedad)
      done=false
      precio = propiedad.precio_edificar
      if puedo_gastar(precio)
        if propiedad.num_casas<@@casas_max
          done = true
        end
      end
   return done
end
    
    def cancelar_hipoteca(ip)
      result = false
      if (!is_encarcelado())
        if (existe_la_propiedad(ip))
          propiedad = @propiedades[ip]
          cantidad = propiedad.get_importe_cancelar_hipoteca()
          puedo_gastar = self.puedo_gastar(cantidad)
          if puedo_gastar
            result = propiedad.cancelar_hipoteca(self)
            if result
              Civitas::Diario.instance.ocurre_evento("El jugador #{@nombre} cancela la hipoteca de la propiedad #{ip}")
            end
          end
        end
      end
      return result
    end
    
    def comprar(titulo)
      result = false
      if (!is_encarcelado())
        if (puede_comprar_casilla())
          precio= titulo.precio_compra;
          if (puedo_gastar(precio))
            result = titulo.comprar(self)
            if result
              @propiedades << titulo
              Civitas::Diario.instance.ocurre_evento("El jugador #{get_nombre} compra la propiedad " + titulo.to_string())
            end
            @puede_comprar= false
          end
        end
      end
      return result
    end
    
    def construir_hotel(ip)
      result = false
      if !is_encarcelado()
        if existe_la_propiedad(ip)
          propiedad = @propiedades[ip]
          puedo_edificar_hotel = puedo_edificar_hotel(propiedad)
          if puedo_edificar_hotel
            result = propiedad.construir_hotel(self)
            casas_por_hotel = @@casas_por_hotel
            propiedad.derruir_casas(casas_por_hotel,self)
            Civitas::Diario.instance.ocurre_evento("El jugador #{get_nombre} construye un hotel en la propiedad " + propiedad.to_string())
          end
          
        end
      end
      return result
    end
    
    def construir_casa(ip)
      result = false
      if !is_encarcelado()
        if existe_la_propiedad(ip)
          propiedad = @propiedades[ip]
          puedo_edificar_casa = puedo_edificar_casa(propiedad)
          if puedo_edificar_casa
            result = propiedad.construir_casa(self)
            Civitas::Diario.instance.ocurre_evento("El jugador #{get_nombre} construye una casa en la propiedad " + propiedad.to_string())
          end
          
        end
      end
      return result
    end
    
    def hipotecar(ip)
    result = false
      if (!is_encarcelado())
          if existe_la_propiedad(ip)
            propiedad = @propiedades[ip]
            result = propiedad.hipotecar(self)
            if result
              Civitas::Diario.instance.ocurre_evento("El jugador #{get_nombre} hipoteca la propiedad " + propiedad.to_string())
            end
          end
      end
      return result
    end
    
    def to_string()
      String::new(mensaje="******************************\n")
      mensaje += @nombre + "\n******************************\n\n" + "ENCARCELADO = " + @encarcelado.to_s 
      mensaje += "\n Numero Casilla Actual: #{@num_casilla_actual}         Saldo: #{@saldo}€"
      mensaje += "\n Puede comprar= " + @puede_comprar.to_s
      mensaje += "\n CasasMax: #{@@casas_max}      Casas por hotel: #{@@casas_por_hotel}      Hoteles max: #{@@hoteles_max}"
      mensaje += "\n Paso por salida = #{@@paso_por_salida}€, Precio Libertad = #{@@precio_libertad}€, Saldo inicial = #{@@saldo_inicial}"
      
      return mensaje
    end
    
    
  end
end
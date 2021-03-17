#encoding:utf-8

require_relative 'titulo_propiedad.rb'
require_relative 'sorpresa.rb'
require_relative 'diario.rb'
require_relative 'civitas_juego.rb'
require_relative 'sorpresa_salir_carcel'
require_relative 'casilla'
require_relative 'jugador'
require 'jugador.rb'

module Civitas
    class JugadorEspeculador < Jugador
        @@FactorEspeculador = 2

        def self.nuevo_especulador(jugador,fianza)
            nuevo=JugadorEspeculador.new(fianza)
            
            nuevo.copia(jugador)
          
            for i in 0..(jugador.propiedades.length-1)
                jugador.propiedades[i].actualiza_propietario_por_conversion(nuevo)
            end

            return nuevo
        end

        def initialize(fianza)
            @fianza=fianza
        end
        
        def puede_pagar_fianza
          puede=false
          if(@saldo >= @fianza)
            modifica_saldo(-@fianza)
            puede=true
          end
          
          return puede
        end
        
        def debe_ser_encarcelado
          carcel=false
          if(super)
            if(!puede_pagar_fianza)
              carcel=true
            end
          end
          
          return carcel
        end

        def get_hoteles_max
            return(@@hoteles_max * @@FactorEspeculador)
        end

        def get_casas_max
            return(@@casas_max * @@FactorEspeculador)
        end
        
        def puedo_edificar_casa(propiedad)
          done=false
          precio=propiedad.precio_edificar
          if(puedo_gastar(precio))
            if(propiedad.num_casas < (get_casas_max))
              done=true
            end
          end
          
          return done
        end
        
        def puedo_edificar_hotel(propiedad)
          done=false
          precio=propiedad.precio_edificar
          if(puedo_gastar(precio))
            if(propiedad.num_hoteles < (get_hoteles_max))
              if(propiedad.num_casas >= @@casas_por_hotel)
                done=true
              end
            end
          end
          
          return done
        end

        def paga_impuesto(cantidad)
            return(super(cantidad/@@FactorEspeculador))
        end
    end
end
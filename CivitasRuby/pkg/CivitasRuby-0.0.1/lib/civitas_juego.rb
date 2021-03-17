#encoding:utf-8
require_relative 'estados_juego'
require_relative 'jugador'
require_relative 'jugador_especulador'
require_relative 'gestor_estados'
require_relative 'titulo_propiedad'
require_relative 'mazo_sorpresas'
require_relative 'tablero'
require_relative 'casilla'
require_relative 'casilla_calle'
require_relative 'casilla_impuesto'
require_relative 'casilla_juez'
require_relative 'casilla_sorpresa'
require_relative 'sorpresa'
require_relative 'sorpresa_convertir_jugador'
require_relative 'sorpresa_ir_a_casilla'
require_relative 'sorpresa_ir_carcel'
require_relative 'sorpresa_pagar_cobrar'
require_relative 'sorpresa_por_casa_hotel'
require_relative 'sorpresa_por_jugador'
require_relative 'sorpresa_salir_carcel'
require_relative 'operaciones_juego'

module Civitas
  class Civitas_juego

    def initialize(nombres)
      
      @jugadores= []
      i=0
      while(i<nombres.length)
        un_jugador=Jugador.new(nombres[i])
        @jugadores << un_jugador
        i = i +1
      end
      @gestor_estados = Gestor_estados.new()
      @estado = @gestor_estados.estado_inicial
      @indice_jugador_actual=Dado.instance.quien_empieza(nombres.length)
      @mazo= MazoSorpresas.new()
      @tablero= Tablero.new(5)
      inicializa_mazo_sorpresa(@tablero)
      inicializa_tablero(@mazo)
      
      
    end
    
    def avanza_jugador
      jugador_actual=@jugadores[@indice_jugador_actual]
      posicion_actual=jugador_actual.num_casilla_actual
      tirada=Dado.instance.tirar
      posicion_nueva=@tablero.nueva_posicion(posicion_actual, tirada)
      casilla=@tablero.get_casilla(posicion_nueva)
      contabilizar_pasos_por_salida(jugador_actual)
      jugador_actual.mover_a_casilla(posicion_nueva)
      casilla.recibe_jugador(@indice_jugador_actual, @jugadores)
      contabilizar_pasos_por_salida(jugador_actual)
    end
    
    def siguiente_paso
      jugador_actual=@jugadores[@indice_jugador_actual]
      operacion=@gestor_estados.operaciones_permitidas(jugador_actual, @estado)
      if(operacion==Civitas::Operaciones_juego::PASAR_TURNO)
        pasar_turno
        siguiente_paso_completado(operacion)
        
      elsif(operacion==Civitas::Operaciones_juego::AVANZAR)
        avanza_jugador
        siguiente_paso_completado(operacion)
      end
      
      return operacion
    end
    
    def comprar
      jugador_actual=@jugadores[@indice_jugador_actual]
      num_casilla_actual=jugador_actual.num_casilla_actual
      casilla=@tablero.get_casilla(num_casilla_actual)
      titulo=casilla.titulo_propiedad
      resultado=jugador_actual.comprar(titulo)
      
      return resultado
    end

    def info_jugador_texto
      puts "Nombre del jugador: " + @jugadores[@indice_jugador_actual].get_nombre
      puts "Saldo del jugador: #{@jugadores[@indice_jugador_actual].get_saldo}"
      puts "Numero de casas y hoteles: #{@jugadores[@indice_jugador_actual].cantidad_casas_hoteles}"
      puts "Casilla actual del jugador: #{@jugadores[@indice_jugador_actual].num_casilla_actual}"
      for j in (0..(@jugadores.length()-1))
        if(@jugadores[j].en_bancarrota)
          puts ranking
        end
      end
    end

    def inicializa_tablero(mazo)
      
      uno=Titulopropiedad.new("Calle Velazquez", 100, 0.5, 500, 500, 750)
      velazquez=CasillaCalle.new(uno)
      @tablero.aniade_casilla(velazquez)
      
      dos=Titulopropiedad.new("Calle San Felipe", 200, 0.5, 700, 700, 900)
      san_felipe=CasillaCalle.new(dos)
      @tablero.aniade_casilla(san_felipe)
      
      sorpresa1 = CasillaSorpresa.new(mazo, "Sorpresa 1")
      @tablero.aniade_casilla(sorpresa1)
      
      tres=Titulopropiedad.new("Calle Jardines", 500, 0.5, 1000, 1000, 1200)
      jardines=CasillaCalle.new(tres)
      @tablero.aniade_casilla(jardines)
      
      cuatro=Titulopropiedad.new("Calle Blas Infante", 700, 0.5, 1200, 1200, 1400)
      blas_infante=CasillaCalle.new(cuatro)
      @tablero.aniade_casilla(blas_infante)
      
      sorpresa2=CasillaSorpresa.new(mazo,"Sorpresa 2")
      @tablero.aniade_casilla(sorpresa2)
      
      cinco=Titulopropiedad.new("Calle Ramon y Cajal", 1000, 0.5, 1500, 1500, 1700)
      ramon_y_cajal=CasillaCalle.new(cinco)
      @tablero.aniade_casilla(ramon_y_cajal)
      
      seis=Titulopropiedad.new("Calle Monteneros", 1100, 0.5, 1600, 1600, 1800)
      monteneros=CasillaCalle.new(seis)
      @tablero.aniade_casilla(monteneros)
      
      parking=Casilla.new("Parking de la ETSIIT")
      @tablero.aniade_casilla(parking)
      
      siete=Titulopropiedad.new("Calle Real", 1200, 0.5, 1700, 1700, 1900)
      real=CasillaCalle.new(siete)
      @tablero.aniade_casilla(real)
      
      ocho=Titulopropiedad.new("Calle Nueva", 1300, 0.5, 1800, 1800, 2000)
      nueva=CasillaCalle.new(ocho)
      @tablero.aniade_casilla(nueva)
      
      sorpresa3=CasillaSorpresa.new(mazo, "Sorpresa 3")
      @tablero.aniade_casilla(sorpresa3)
      
      nueve=Titulopropiedad.new("Calle Elvira", 1500, 0.5, 2000, 2000, 2200)
      elvira=CasillaCalle.new(nueve)
      @tablero.aniade_casilla(elvira)
      
      @tablero.aniade_juez
      
      diez=Titulopropiedad.new("Paseo De Los Tristes", 1600, 0.5, 2100, 2100, 2300)
      paseo_tristes=CasillaCalle.new(diez)
      @tablero.aniade_casilla(paseo_tristes)
      
      once=Titulopropiedad.new("Carretera De La Sierra", 1700, 0.5, 2200, 2200, 2400)
      carretera_sierra=CasillaCalle.new(once)
      @tablero.aniade_casilla(carretera_sierra)
      
      impuesto=CasillaImpuesto.new(1500, "Impuesto")
      @tablero.aniade_casilla(impuesto)
      
      doce=Titulopropiedad.new("Calle Recogidas", 2000, 0.5, 2500, 2500, 3000)
      recogidas=CasillaCalle.new(doce)
      @tablero.aniade_casilla(recogidas)
    end

    def inicializa_mazo_sorpresa(tablero)
      
      pagar = SorpresaPagarCobrar.new(1000, "te pagan 1000 euros por un premio al informatico con menos futuro")
      @mazo.al_mazo(pagar)
      
      cobrar = SorpresaPagarCobrar.new(-1000, "Mensaje de un numero privado\n ******************\n HAS GANADO UN IPHONE 11\nIntroduce tus datos de la tarjeta de credito para reclamarlo\n******************\n Pierdes 1000 euros")
      @mazo.al_mazo(cobrar)
      
      calle_real = SorpresaIrACasilla.new(tablero, 11, "Te desplazas a la Calle Real")
      @mazo.al_mazo(calle_real)
       
      carretera_sierra = SorpresaIrACasilla.new(tablero, 17, "Te desplazas a la Carretera de la Sierra")
      @mazo.al_mazo(carretera_sierra)
      
      ver_carcel = SorpresaIrACasilla.new(tablero, 5, "Te desplazas a la Carcel de visita full chill")
      @mazo.al_mazo(ver_carcel)
      
      pagar_xcasa = SorpresaPorCasaHotel.new(200, "Te pagan 200 euros por cada casa y hotel que tienes")
      @mazo.al_mazo(pagar_xcasa)
      
      cobrar_xcasa = SorpresaPorCasaHotel.new(-200, "Debido a una invasion de nutrias tienes que hacer reformas en cada casa y hotel por valor de 200 euros")
      @mazo.al_mazo(cobrar_xcasa)
      
      pagar_jug = SorpresaPorJugador.new(400, "Los keyloggers que le pasaste a tus amigos empiezan a generar sus beneficios. Cada jugador te da 400 euros")
      @mazo.al_mazo(pagar_jug)
      
      cobrar_jug = SorpresaPorJugador.new(-400, "Los otros tres jugadores hacen team y te atracan de forma conjunta. Pierdes 1200 euros que se reparten entre los tres (400 por jugador)")
      @mazo.al_mazo(cobrar_jug)
      
      salir_carcel = SorpresaSalirCarcel.new(@mazo, "Sales de la carcel")
      @mazo.al_mazo(salir_carcel)
      
      ir_carcel = SorpresaIrCarcel.new(tablero, "Vas a la carcel")
      @mazo.al_mazo(ir_carcel)
      
      convertir_jugador=SorpresaConvertirJugador.new(200, "Te conviertes en jugador especulador")
      @mazo.al_mazo(convertir_jugador)
    end

    def contabilizar_pasos_por_salida(jugador_actual)
      while (@tablero.get_por_salida > 0)
        jugador_actual.pasa_por_salida
      end
    end

    def pasar_turno
      if(@indice_jugador_actual == @jugadores.length-1)
        @indice_jugador_actual=0
      else
        @indice_jugador_actual += 1
      end
    end

    def siguiente_paso_completado(operacion)
      @estado = @gestor_estados.siguiente_estado(@jugadores[@indice_jugador_actual],@estado,operacion)

    end

    def construir_casa(ip)
      return @jugadores[@indice_jugador_actual].construir_casa(ip)
    end

    def construir_hotel(ip)
      return @jugadores[@indice_jugador_actual].construir_hotel(ip)
    end

    def vender(ip)
      return @jugadores[@indice_jugador_actual].vender(ip)
    end

    def hipotecar(ip)
      return @jugadores[@indice_jugador_actual].hipotecar(ip)
    end

    def cancelar_hipoteca(ip)
      return @jugadores[@indice_jugador_actual].cancelar_hipoteca(ip)
    end

    def salir_carcel_pagando
      return @jugadores[@indice_jugador_actual].salir_carcel_pagando
    end

    def salir_carcel_tirando
      return @jugadores[@indice_jugador_actual].salir_carcel_tirando
    end

    def final_del_juego
      bool=false
        if(@jugadores[@indice_jugador_actual].en_bancarrota)
          bool=true
        end
      return bool
    end

    def ranking
      
      puts "RANKING"
      
     tam =@jugadores.length-1

      clasificacion= Array.new
      saldos= Array.new
      
      for k in (0..tam)
        saldos << @jugadores[k].get_saldo
      end
      
      saldos = saldos.sort {|a,b| b <=> a}
      
      
      for i in(0..tam)
        encontrado=false
        for j in(0..tam)
          if ((saldos[i] == @jugadores[j].get_saldo) && !encontrado)
            clasificacion << "#{@jugadores[j].get_nombre} : #{@jugadores[j].get_saldo}"
            encontrado = true
          end
        end
      end
            
      return clasificacion
    end
    
    def get_casilla_actual
      num_casilla=@jugadores[@indice_jugador_actual].num_casilla_actual
      
      casilla = @tablero.get_casilla(num_casilla)
      
      return casilla
    end
    
    def get_jugador_actual
      return @jugadores[@indice_jugador_actual]
    end
    
    
    private :inicializa_tablero, :inicializa_mazo_sorpresa, :contabilizar_pasos_por_salida, 
      :pasar_turno
    
    
  end
end
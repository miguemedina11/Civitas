require_relative 'vista_textual'
require_relative 'dado'
require_relative 'civitas_juego'
require_relative 'controlador'
require_relative 'jugador'
require_relative 'jugador_especulador'

module Civitas
  class Prueba
    
    nombres = []
      
    nombres << "Jugador 1"
    nombres << "Jugador 2"
    #nombres << "Jugador 3"
    #nombres << "Jugador 4"
    
    vista = Vista_textual.new()
    juego = Civitas_juego.new(nombres)
    Civitas::Dado.instance.debug=true
    control = Controlador.new(juego,vista)
    control.juega
  end
  
  
end

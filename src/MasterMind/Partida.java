/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Yaros
 */
public class Partida {
    private LocalDateTime fecha;
    private int numero_de_rondas;
    private Usuario usuario1;
    private Usuario usuario2;
    private Ronda[] rondas = new Ronda[numero_de_rondas];

    public Partida(Usuario usuario1, Usuario usuario2) {    //Partida normal
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;       
    }
    
    public Partida(Usuario usuario1){   //Partida entrenamiento
        this.usuario1= usuario1;
    }
    
    
    public void jugarPartida(Usuario usuario1, Usuario usuario2){
        this.numero_de_rondas = 6;
        
        this.fecha = LocalDateTime.now();
        for(int i = 0; i < this.numero_de_rondas; i++){
            Combinacion clave = rondas[i].añadirCombinacion();
            this.rondas[i].jugar(clave);
        }
        
        asignarEstadísticas();
    }
    
    public void jugarEntrenamiento(Usuario usuario1){
        this.numero_de_rondas = 1;
        this.rondas[0]= new Ronda(10);
        this.rondas[0].jugar(rondas[0].getClave());
    }
    
    public void asignarEstadísticas(){
        int puntosUsuario1 = 0;
        int puntosUsuario2 = 0;
        
        //Cálculo de los puntos totales de cada jugador
        for(int i = 0; i < numero_de_rondas; i++){
            if((i%2) == 0){
               puntosUsuario1 += this.rondas[i].getPuntos();
            }else if((i%2) != 0){
               puntosUsuario2 += this.rondas[i].getPuntos();
            }
        }
        
        //Cálculo del vencedor y del perdedor. En caso de empate se toma como si ambos hubiesen vencido
        if(puntosUsuario1 > puntosUsuario2){
            this.usuario1.agregarVictoria();
            this.usuario2.agregarDerrota();
        }else if(puntosUsuario1 < puntosUsuario2){
            this.usuario1.agregarDerrota();
            this.usuario2.agregarVictoria();
        }else if(puntosUsuario1 == puntosUsuario2){
            this.usuario1.agregarVictoria();
            this.usuario2.agregarVictoria();
        }
        
        //Suma de los puntos obtenidos a las estadísticas del jugador
        usuario1.agregarPuntos_anotados(puntosUsuario1);
        usuario1.agregarPuntos_encajados(puntosUsuario2);
        
        usuario2.agregarPuntos_anotados(puntosUsuario2);
        usuario2.agregarPuntos_encajados(puntosUsuario1);
        
        //Se agrega esta partida a la lista de partidas de los usuarios
        usuario1.agregarPartida(this);
        usuario2.agregarPartida(this);
    }
    
    


}

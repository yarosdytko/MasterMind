/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 *
 * @author Yaros
 */
public class Partida {
    private LocalDateTime fecha;
    private int numero_de_rondas;
    private int rondas_gastadas;
    private int puntos = 10;
    private Usuario usuario1;
    private Usuario usuario2;
    private ArrayList<Ronda> rondas = new ArrayList<>();
    private boolean finRonda = false;

    public Partida(Usuario usuario1, Usuario usuario2) {    //Partida normal
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.numero_de_rondas=3;
        this.rondas_gastadas=0;
        this.fecha= LocalDateTime.now();
    }
    
    public void addRonda(Ronda ronda){
        this.rondas.add(ronda);
    }
    
    public int getIntentos(){
        return this.getRonda().getIntentos();
    }
    
    public int getIntentosGastados(){
        return this.getRonda().getIntentosGastdos();
    }
    
    public int getRondasRestantes(){
        return (this.numero_de_rondas-this.rondas_gastadas);
    }
    
    public void jugarRonda(Combinacion combinacion){
        this.getRonda().jugar(combinacion);
        if(this.getRonda().getIntentosRestantes()==0 || this.getRonda().esGanadora()){
            if(this.getRondasRestantes()==0){
                this.usuario1.agregarPartida(this);
                this.usuario2.agregarPartida(this);
            }
            finRonda = true;
            rondas_gastadas++; //si se acaba una ronda paso a la siguente
        } else {
            finRonda=false;
        }
    }
    
    public boolean finRonda(){
        return finRonda;
    }
    
    
    
    //devuelve la ultima ronda del array de rondas, que es a su vez la ronda acual
    public Ronda getRonda(){
        return this.rondas.get(this.rondas.size()-1);
    }
    
    /*
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
    
    */

    public int getNumero_de_rondas() {
        return numero_de_rondas;
    }

    public int getRondas_gastadas() {
        return rondas_gastadas+1;
    }

    public int getPuntos() {
        return puntos;
    }


}

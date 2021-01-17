/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Yaros
 */
public class Partida implements Serializable {
    private LocalDateTime fecha;
    private int numero_de_rondas=6;
    private int rondas_gastadas=0;
    private int puntos = 10;
    private Usuario usuario1;
    private Usuario usuario2;
    private ArrayList<Ronda> rondas = new ArrayList<>();
    private boolean finRonda = false;

    public Partida(Usuario usuario1, Usuario usuario2) {    //Partida normal
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.fecha= LocalDateTime.now();
    }
    
    public void addRonda(Ronda ronda){
        this.rondas.add(ronda);
    }
    
    public int getIntentos(){
        return this.getRonda().getIntentos();
    }
    
    public int getIntentosGastados(){
        return this.getRonda().getIntentosGastados();
    }
    
    public int getRondasRestantes(){
        return (this.numero_de_rondas-this.rondas_gastadas);
    }
    
    public void jugarRonda(Combinacion combinacion){
        this.getRonda().jugar(combinacion);
        if(this.getRonda().getIntentosRestantes()==0 || this.getRonda().esGanadora()){
            finRonda = true;
            rondas_gastadas++; //si se acaba una ronda paso a la siguente
        } else {
            finRonda=false;
        }
    }
    
    //este metodo es casi igual que el de arriba pero permite ir alterando rondas de usuarios
    public void jugarRonda(int numero_ronda,Combinacion combinacion){
        Ronda r = this.getRonda(numero_ronda); //obtengo la ronda correspondiente del array de rondas
        //comprobacion de datos de ronda
        r.jugar(combinacion);
        //si ya no quedan intentos en ronda o la ronda es ganadora
        if(r.getIntentosRestantes()==0 || r.esGanadora()) {
            finRonda=true; //ronda finalizada
            rondas_gastadas++;
        } else {
            finRonda=false;
        }
    }
    
    public boolean getFinRonda(){
        return finRonda;
    }
    
    public void guardarPartida(){
        this.usuario1.agregarPartida();
        this.usuario2.agregarPartida();
        this.usuario1.agregarPartida(this);
        this.usuario2.agregarPartida(this);
    }
    
    //devuelve la ultima ronda del array de rondas, que es a su vez la ronda acual
    public Ronda getRonda(){
        return this.rondas.get(this.rondas.size()-1);
    }
    
    public Ronda getRonda(int i){
        return this.rondas.get(i);
    }
    
    //esto lo uso en int grafica
    /*public String getHistoricoIntentos(int numRonda){
        Ronda r = this.getRonda(numRonda);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < r.getIntentosGastados(); i++) {
            sb.append(i+1).append(".- ");
            sb.append(r.getIntento(i).toString());
            sb.append(" ").append(r.getAcierto(i)).append(" aciertos");
            sb.append(" con ").append(r.getColocado(i)).append(" colocados\n");
        }
        return sb.toString();
    }*/
    
    public String getPistas(int numRonda){
        Ronda r = this.getRonda(numRonda);
        StringBuilder sb = new StringBuilder();
        sb.append(r.getIntentosGastados()).append(".- ");
        sb.append(r.getIntento(r.getIntentosGastados()-1).toString());
        sb.append(" ").append(r.getAciertos()).append(" aciertos");
        sb.append(" con ").append(r.getColocados()).append(" colocados\n");
        return sb.toString();
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
    */
    
    public void asignarEstadísticas(){
        int puntosUsuario1 = 0;
        int puntosUsuario2 = 0;
        
        //Cálculo de los puntos totales de cada jugador
        for(int i = 0; i < this.rondas.size(); i++){
            if((i%2) == 0){
               puntosUsuario1 += this.rondas.get(i).getPuntos();
            }else if((i%2) != 0){
               puntosUsuario2 += this.rondas.get(i).getPuntos();
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
    

    public int getNumero_de_rondas() {
        return numero_de_rondas;
    }

    public int getRondas_gastadas() {
        return rondas_gastadas+1;
    }

    public int getPuntos() {
        return puntos;
    }
    
    public String getFecha(){
        return this.fecha.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
    public String getHora(){
        return this.fecha.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Partida { fecha=").append(this.getFecha());
        sb.append(", hora=").append(this.getHora());
        sb.append("\n numero_de_rondas=").append(numero_de_rondas);
        sb.append(", rondas_gastadas=").append(rondas_gastadas); 
        sb.append(", puntos=").append(puntos);
        sb.append("\n usuario1=").append(usuario1.getNombre());
        sb.append(", usuario2=").append(usuario2.getNombre()).append("\n");
        for(int i = 0; i < rondas.size(); i++){
            sb.append("Ronda ").append(i+1).append("=> Clave: ").append(rondas.get(i).getClave()).append(", Total de intentos: ").append(rondas.get(i).getIntentosGastados()).append("\n");
        }
        //sb.append(", rondas=").append(rondas);
        //sb.append(", finRonda=").append(finRonda);
        sb.append('}');
        return sb.toString();
    }
    
}

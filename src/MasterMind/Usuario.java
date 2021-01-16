/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Yaros
 */
public class Usuario implements Serializable, Comparable<Usuario>{
    private String nombre;
    private String clave;
    private Boolean administrador=false;
    private int partidas_jugadas=0;
    private int partidas_ganadas=0;
    private int partidas_perdidas=0;
    private int puntos_anotados=0;
    private int puntos_encajados=0;
    //private int porcentaje_victorias=(partidas_ganadas/partidas_perdidas)*100; no se puede poner esto tal cual, el programa peta
    private int porcentaje_victorias=0;
    //private Partida[] partidas = new Partida[partidas_jugadas]; mejor usar arrayList , mas facil de gestionar
    private ArrayList<Partida> partidasJugadas;

    public Usuario(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
        this.partidasJugadas = new ArrayList<>();
    }

    public Usuario(String nombre, String clave, Boolean administrador) {
        this.nombre = nombre;
        this.clave = clave;
        this.administrador = administrador;
        this.partidasJugadas = new ArrayList<>();
    }
    
    //constructor copia, por si hace falta

    public Usuario(Usuario usuario) {
        this.nombre = usuario.nombre;
        this.clave = usuario.clave;
        this.administrador = usuario.administrador;
        this.partidas_jugadas = usuario.partidas_jugadas;
        this.partidas_ganadas = usuario.partidas_ganadas;
        this.partidas_perdidas = usuario.partidas_perdidas;
        this.puntos_anotados = usuario.puntos_anotados;
        this.puntos_encajados = usuario.puntos_encajados;
        this.porcentaje_victorias = usuario.porcentaje_victorias;
        this.partidasJugadas = usuario.partidasJugadas;
    }
    

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public int getPartidas_jugadas() {
        return partidas_jugadas;
    }

    public ArrayList<Partida> getPartidasJugadas() {
        return partidasJugadas;
    }

    public int getPartidas_ganadas() {
        return partidas_ganadas;
    }

    public int getPartidas_perdidas() {
        return partidas_perdidas;
    }

    public int getPuntos_anotados() {
        return puntos_anotados;
    }

    public int getPuntos_encajados() {
        return puntos_encajados;
    }

    public int getPorcentaje_victorias() {
        return porcentaje_victorias;
    }
    
    public void agregarPartida(){
        this.partidas_jugadas++;
    }
    
    public void agregarVictoria(){
        this.partidas_ganadas++;
        this.agregarPartida();
        this.actualizarPorcentaje_Victorias();
    }
    
    public void agregarDerrota(){
        this.partidas_perdidas++;
        this.agregarPartida();
        this.actualizarPorcentaje_Victorias();
    }
    
    public void agregarPuntos_anotados(int puntos){
        this.puntos_anotados += puntos;
    }
    
    public void agregarPuntos_encajados(int puntos){
        this.puntos_encajados += puntos;
    }
    
    public void agregarPartida(Partida partida){
        //this.partidas_jugadas++;
        this.partidasJugadas.add(partida);
    }

    public void setPartidas_ganadas(int partidas_ganadas) {
        this.partidas_ganadas = partidas_ganadas;
    }
    
    public void actualizarPorcentaje_Victorias(){
        this.porcentaje_victorias = (this.partidas_ganadas/this.partidas_jugadas)*100;
    }
    
    public Partida mostrarPartidas(int i){
        return (this.partidasJugadas.get(i));
    }
    
    public Ronda mostrarRondas(int i, int j){
        return (this.partidasJugadas.get(i).getRonda(j));
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", partidas_jugadas=" + partidas_jugadas + ", partidas_ganadas=" + partidas_ganadas + ", partidas_perdidas=" + partidas_perdidas + ", puntos_anotados=" + puntos_anotados + ", puntos_encajados=" + puntos_encajados + ", porcentaje_victorias=" + porcentaje_victorias + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        return this.nombre.equals(other.nombre);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        return hash;
    }
    
    //metodo comparador para poder usarlo en la clasificacion
    //por defecto se compara por porcentaje de victoras
    @Override
    public int compareTo(Usuario o) {
        return this.getPorcentaje_victorias() < o.getPorcentaje_victorias()?1:-1;
    }
    
}

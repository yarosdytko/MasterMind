/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import java.io.Serializable;
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
    private int porcentaje_victorias=(partidas_ganadas/partidas_perdidas)*100;
    private Partida[] partidas = new Partida[partidas_jugadas];

    public Usuario(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
    }

    public Usuario(String nombre, String clave, Boolean administrador) {
        this.nombre = nombre;
        this.clave = clave;
        this.administrador = administrador;
    }
    
    //constructor copia, por si hace falta

    public Usuario(Usuario usuario) {
        this.nombre = usuario.nombre;
        this.clave = usuario.clave;
        this.partidas_jugadas = usuario.partidas_jugadas;
        this.partidas_ganadas = usuario.partidas_ganadas;
        this.partidas_perdidas = usuario.partidas_perdidas;
        this.puntos_anotados = usuario.puntos_anotados;
        this.puntos_encajados = usuario.puntos_encajados;
        this.porcentaje_victorias = usuario.porcentaje_victorias;
        this.partidas = usuario.partidas;
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
    
    public Partida[] verPartidas(){
        return partidas;
    }
    
    public void agregarPartida(){
        this.partidas_jugadas++;
    }
    
    public void agregarVictoria(){
        this.partidas_ganadas++;
        this.agregarPartida();
    }
    
    public void agregarDerrota(){
        this.partidas_perdidas++;
        this.agregarPartida();
    }
    
    public void agregarPuntos_anotados(int puntos){
        this.puntos_anotados += puntos;
    }
    
    public void agregarPuntos_encajados(int puntos){
        this.puntos_encajados += puntos;
    }
    
    public void agregarPartida(Partida partida){
        this.partidas[partidas_jugadas-1] = partida;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", clave=" + clave + ", administrador=" + administrador + ", partidas_jugadas=" + partidas_jugadas + ", partidas_ganadas=" + partidas_ganadas + ", partidas_perdidas=" + partidas_perdidas + ", puntos_anotados=" + puntos_anotados + ", puntos_encajados=" + puntos_encajados + ", porcentaje_victorias=" + porcentaje_victorias + '}';
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
    @Override
    public int compareTo(Usuario o) {
        return this.getPartidas_ganadas() < o.getPartidas_ganadas()?1:-1;
    }
    
}

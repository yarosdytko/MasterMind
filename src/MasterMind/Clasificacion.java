/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import Excepciones.UsuarioYaExisteException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Yaros
 */
public class Clasificacion implements Serializable{
    private ArrayList<Usuario> usuarios;
    
    
    public Clasificacion() {
        
        usuarios = new ArrayList<Usuario>();
        
    }
    
    /*este metodo es para controlar que no haya usuarios repetidos en la clasificacion*/
    private Usuario usuarioExiste(String nombre){
        Usuario usuario = new Usuario(nombre, "");
        if(this.usuarios.contains(usuario)){
            int i = this.usuarios.indexOf(usuario);
            usuario = this.usuarios.get(i);
        } else {
            usuario=null;
        }
        
        return usuario;
    }
    
    /*
    * añade al usuario al array de clasificacion
    * este metodo deberia llamarse solo una vez por usuario
    * en el momento de registrarse el usuario en el sistema
    */
    public void addUsuario(Usuario usuario) throws UsuarioYaExisteException{
        if(usuarioExiste(usuario.getNombre())!=null){
            throw new UsuarioYaExisteException("Usuario ya existe");
        } else {
            usuarios.add(usuario);
        }
    }
    
    public void actualizarUsuario(Usuario usuario){
        
        Usuario u = new Usuario(usuario.getNombre(), "");
        
        if(this.usuarios.contains(u)){
            int i = this.usuarios.indexOf(u);
            u=this.usuarios.get(i);
            u=usuario;
        }
    }
    
    public void ordenar(){
        Collections.sort(usuarios);
    }
    
    //muestra la clasificacion odenada por porcentaje de victorias
    public String mostrarClasificacion(){
       
        this.ordenarPorPorcentaje_de_victorias();
        
       StringBuilder stringBuilder = new StringBuilder();
       
        for (Usuario u : usuarios) {
            stringBuilder.append(u.toString());
            stringBuilder.append("\n");
        }
       return stringBuilder.toString();
    }
    
    //este metodo lo uso para la clasificacion de la int grafica
    public ArrayList<Usuario> getClasificacion(){
        this.ordenarPorPorcentaje_de_victorias();
        return this.usuarios;
    }
    
    public String mostrarClasificacionPorPartidasGanadas(){
        
        //ordeno la lista
        this.ordenarPorPartidas_ganadas();
       
       StringBuilder stringBuilder = new StringBuilder();
       //stringBuilder.append("Nombre, Porcentaje victorias, Partidas jugadas, Partidas ganadas, Partidas perdidas, Puntos anotados, Puntos encajados");
        for (Usuario u : usuarios) {
            stringBuilder.append(u.toString());
            stringBuilder.append("\n");
        }
       return stringBuilder.toString();
    }
    
 
    
    private void ordenarPorPorcentaje_de_victorias(){
        Collections.sort(usuarios, new Comparator<Usuario>(){
            @Override
            public int compare(Usuario o1, Usuario o2){
                return o1.getPorcentaje_victorias() < o2.getPorcentaje_victorias()?1:-1;
            }
        });
    }
    
    private void ordenarPorPartidas_ganadas(){
        Collections.sort(usuarios, new Comparator<Usuario>() {
        @Override   
            public int compare(Usuario o1, Usuario o2){
                return o1.getPartidas_ganadas() < o2.getPartidas_ganadas()?1:-1;
            }
        });
    }
    
    public void volcar_a_txt(File fichero) throws IOException{
        PrintWriter escribir = new PrintWriter(new BufferedWriter(new FileWriter(fichero)));
        
        for(int i = 0; i < usuarios.size(); i++){
            escribir.println(usuarios.get(i).toString());
        }
        escribir.close();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import Excepciones.UsuarioYaExisteException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author Yaros
 */
public class Clasificacion {
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
            System.out.println("ya existe");
            throw new UsuarioYaExisteException("Usuario ya existe");
        } else {
            System.out.println("usuario nuevo "+usuario.getNombre());
            usuarios.add(usuario);
        }
    }
    
    public void actualizarUsuario(Usuario usuario){
        //por hacer
    }
    
    public void ordenar(){
        Collections.sort(usuarios);
    }
    
    public String mostrarClasificacion(){
       
       StringBuilder stringBuilder = new StringBuilder();
       
        for (Usuario u : usuarios) {
            stringBuilder.append(u.toString());
            stringBuilder.append("\n");
        }
       return stringBuilder.toString();
    }
    
    public void volcar_a_txt(File fichero){
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import Excepciones.UsuarioNoExisteException;
import Excepciones.UsuarioYaExisteException;
import Excepciones.WrongPasswordException;
import java.util.ArrayList;

/**
 *
 * @author Yaros
 */
public class Almacen_Login {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    
    private Usuario usuarioExiste(String nombre){
        Usuario usuario = new Usuario(nombre, "");
        if(this.usuarios.contains(usuario)){
            System.out.println("hay usuario con ese nombre");
            int i = this.usuarios.indexOf(usuario);
            System.out.println("Indice: "+i);
            usuario = this.usuarios.get(i);
            System.out.println("Usuario despues de get: "+usuario.toString());
        } else {
            System.out.println("no hay usuario");
            usuario=null;
        }
        
        return usuario;
    }
    
    public Usuario registrar(Usuario usuario) throws UsuarioYaExisteException {
        if(usuarioExiste(usuario.getNombre())!=null){
            throw new UsuarioYaExisteException();
        } else {
            Usuario u = new Usuario(usuario);
            System.out.println("Registra, usuario no existe");
            usuarios.add(u);
            return u;
        }
    }
    
    public Usuario identificar(Usuario usuario) throws WrongPasswordException, UsuarioNoExisteException{
        Usuario u = usuarioExiste(usuario.getNombre());
        if(u!=null){
            if(u.getClave() == null ? usuario.getClave() == null : u.getClave().equals(usuario.getClave())){
                System.out.println("Usuario "+u.getNombre()+" identificado");
                return u;
            } else {
                throw new WrongPasswordException();
            }
        } else {
            throw new UsuarioNoExisteException();
        }
    }
    
}

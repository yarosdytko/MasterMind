/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import Excepciones.UsuarioNoExisteException;
import Excepciones.UsuarioYaExisteException;
import Excepciones.WrongPasswordException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Yaros
 */
public class Almacen_Login implements Serializable {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    
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
    
    public Usuario registrar(Usuario usuario) throws UsuarioYaExisteException {
        if(usuarioExiste(usuario.getNombre())!=null){
            throw new UsuarioYaExisteException("Usuario ya existe");
        } else {
            Usuario u = new Usuario(usuario);
            usuarios.add(u);
            return u;
        }
    }
    
    public Usuario identificar(Usuario usuario) throws WrongPasswordException, UsuarioNoExisteException{
        Usuario u = usuarioExiste(usuario.getNombre());
        if(u!=null){
            if(u.getClave() == null ? usuario.getClave() == null : u.getClave().equals(usuario.getClave())){
                return u;
            } else {
                throw new WrongPasswordException("Clave incorrecta");
            }
        } else {
            throw new UsuarioNoExisteException("Usuario no existe");
        }
    }
    
}

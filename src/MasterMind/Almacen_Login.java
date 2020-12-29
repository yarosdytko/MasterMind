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
    private final ArrayList<Usuario> usuarios = new ArrayList<>();
    
    private Usuario comprobarUsuario(Usuario usuario){
        if(usuarios.contains(usuario)){
            int i = usuarios.indexOf(usuario);
            //System.out.println("Usuario existe");
            return usuarios.get(i);
        } else {
            //System.out.println("usuario no existe");
            return null;
        }
    }
    
    
    public void registrar(Usuario usuario) throws UsuarioYaExisteException {
        if(comprobarUsuario(usuario)!=null){
            throw new UsuarioYaExisteException();
        } else {
            usuarios.add(usuario);
            //System.out.println("Usuario registrado");
        }
    }
    
    public Usuario identificar(Usuario usuario) throws WrongPasswordException, UsuarioNoExisteException {
        Usuario u = comprobarUsuario(usuario); //compruebo si existe un usuario con su nombre en array de usuarios
        if(u!=null){
            if(u.getClave()==usuario.getClave()){
                return u;
            } else {
                throw new WrongPasswordException();
            }
        } else {
            throw new UsuarioNoExisteException();
        }
    }
}

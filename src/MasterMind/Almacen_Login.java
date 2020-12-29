/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import java.util.ArrayList;

/**
 *
 * @author Yaros
 */
public class Almacen_Login {
    private final ArrayList<Usuario> usuarios = new ArrayList<>();
    
    
    public boolean estaRegistrado(String nombre){
        if(comprobarUsusario(nombre)!=null){
            System.out.println("Usuario ya existe");
            return true;
        } else {
            System.out.println("Usuario aun no existe");
            return false;
        }
    }
    
    private Usuario comprobarUsusario(String nombre){
        Usuario u = new Usuario(nombre, "");
        if(this.usuarios.contains(u)){
            int i = this.usuarios.indexOf(u);
            u = (Usuario) this.usuarios.get(i);
        } else {
            u = null;
            
        }
        return u;
    }
    
    public void registrar(Usuario usuario){
        System.out.println("Registrando");
        if(usuario!=null){
            usuarios.add(usuario);
            System.out.println("usuario registrado");
        }
    }
    
    public Usuario identificar(Usuario usuario){
        Usuario u = comprobarUsusario(usuario.getNombre());
        if(u!=null && u.equals(usuario)){
            return u;
        } else {
            return null;
        }
    }
}

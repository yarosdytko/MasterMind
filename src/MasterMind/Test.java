/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;


/**
 *
 * @author Yaros
 */
public class Test {
    public static void main(String[] args) {
        
        Almacen_Login almacen_Login = new Almacen_Login();
        
        Usuario usuario = new Usuario("Juan", "1234"); 
        Usuario usuario2 = new Usuario("Juan", "1234"); 
        System.out.println(almacen_Login.estaRegistrado(usuario.getNombre()));
        almacen_Login.registrar(usuario);
        System.out.println(almacen_Login.estaRegistrado(usuario2.getNombre()));
        
    }
}

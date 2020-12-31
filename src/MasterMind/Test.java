/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import Excepciones.UsuarioNoExisteException;
import Excepciones.UsuarioYaExisteException;
import Excepciones.WrongPasswordException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Yaros
 */

//este archivo lo uso para test y demas tonterias


public class Test {
    public static void main(String[] args) {
        
        Almacen_Login almacen_Login = new Almacen_Login();
        
        Usuario usuario = new Usuario("Juan", "1234"); 
        Usuario usuario2 = new Usuario("Juanito", "4321"); 
        
        
        
        
        
        /*
            try {
            almacen_Login.registrar(usuario2);
            } catch (UsuarioYaExisteException ex) {
            Logger.getLogger("").log(Level.INFO, "usuario ya existe");
            }
            
            
            
            try {
            if(almacen_Login.identificar(usuario)!=null){
            System.out.println("identificar ok");
            } else {
            System.out.println("identificar no ok");
            }
            } catch (WrongPasswordException ex) {
            Logger.getLogger("").log(Level.INFO, "contraseña incorrecta");
            } catch (UsuarioNoExisteException ex) {
            Logger.getLogger("").log(Level.INFO, "usuario no existe");
            }
            
            try {
            if(almacen_Login.identificar(usuario2)!=null){
            System.out.println("identificar ok");
            } else {
            System.out.println("identificar no ok");
            }
            } catch (WrongPasswordException ex) {
            Logger.getLogger("").log(Level.INFO, "contraseña incorrecta");
            } catch (UsuarioNoExisteException ex) {
            Logger.getLogger("").log(Level.INFO, "usuario no existe");
            }
            */
    }
}

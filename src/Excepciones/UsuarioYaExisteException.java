/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author Yaros
 */
public class UsuarioYaExisteException extends Exception{

    public UsuarioYaExisteException() {
    }

    public UsuarioYaExisteException(String string) {
        super(string);
    }
    
}

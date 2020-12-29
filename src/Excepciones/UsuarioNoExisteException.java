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
public class UsuarioNoExisteException extends Exception {

    public UsuarioNoExisteException() {
    }

    public UsuarioNoExisteException(String string) {
        super(string);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IODatos;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *  Esta clase deserializa cualquier objeto que se le pasa
 *  para recuperar datos del disco
 *  @author Yaros
 */
public class DatosIn {
    private FileInputStream file;
    private ObjectInputStream input;
    
    public void open(String fileName) throws IOException {
        file = new FileInputStream(fileName);
        input = new ObjectInputStream(file);
    }
    
    public void close() throws IOException {
        if(input!=null){
            input.close();
        }
    }
    
    public Object read() throws IOException, ClassNotFoundException {
        Object object = null;
        if(input!=null){
            try {
                object = input.readObject();
            } catch (EOFException eofe) {
            }
        }
        return object;
    }
    
}

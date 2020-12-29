/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IODatos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Yaros
 */
public class DatosOut {
    
    private FileOutputStream file;
    private ObjectOutputStream output;
    
    public void open(String fileName) throws IOException {
        file = new FileOutputStream(fileName);
        output = new ObjectOutputStream(file);
    }
    
    public void close() throws IOException {
        if(output!=null){
            output.close();
        }
    }
    
    public void write(Object object) throws  IOException{
        if(output!=null){
            output.writeObject(object);
        }
    }
    
}

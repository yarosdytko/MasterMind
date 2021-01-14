/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import Excepciones.CombinacionNoValidaException;
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
        Clasificacion clasificacion = new Clasificacion();
        
        Usuario usuario = new Usuario("Juan", "1234"); 
        Usuario usuario2 = new Usuario("Juanito", "4321"); 
       
//        System.out.println("Combnacion 2: "+c2.toString());
        Combinacion c3 = new Combinacion("VVRR");
//        System.out.println("Combnacion 3: "+c3.toString());
        Combinacion c4 = new Combinacion("ARAV");
//        System.out.println("Combnacion 4: "+c4.toString());
        Combinacion c5 = new Combinacion("ARRV");
//        System.out.println("Combnacion 5: "+c5.toString());
        Combinacion c6 = new Combinacion("RVAA");
        
        System.out.println("Combinacion 6: "+c6.toString());
        
        Ronda r = new Ronda(c6);
        r.jugar(c3);
        
        StringBuilder str = new StringBuilder();
        
        str.append(r.getIntentosGastdos()).append(".-").append(r.getIntento().toString());
        str.append(" ").append(r.getAciertos()).append(" aciertos con ").append(r.getColocados()).append(" colocados");
        System.out.println(str.toString());
        System.out.println(r.getClave().toString());
        if(r.esGanadora()){
            str.append("---- !!! GANADOR !!! ----");
        }
        //System.out.println(r.getRondaLog());
        
//        
//        
//        System.out.println("Aciertos: "+c.devolverAciertos(c2));
//        System.out.println("Colocados: "+c.devolverColocados(c2));
//        System.out.println("Aciertos: "+c.devolverAciertos(c3));
//        System.out.println("Colocados: "+c.devolverColocados(c3));
//        System.out.println("Aciertos: "+c.devolverAciertos(c4));
//        System.out.println("Colocados: "+c.devolverColocados(c4));
//        System.out.println("Aciertos: "+c.devolverAciertos(c5));
//        System.out.println("Colocados: "+c.devolverColocados(c5));
//        System.out.println("Aciertos: "+c.devolverAciertos(c6));
//        System.out.println("Colocados: "+c.devolverColocados(c6));
        
/*
            try {
            almacen_Login.registrar(usuario);
            clasificacion.addUsuario(usuario);
            
            } catch (UsuarioYaExisteException ex) {
            //Logger.getLogger("").log(Level.INFO, "usuario ya existe");
                System.out.println(ex.toString());
            }
            
            try {
            almacen_Login.registrar(usuario2);
            clasificacion.addUsuario(usuario2);
            
            } catch (UsuarioYaExisteException ex) {
            //Logger.getLogger("").log(Level.INFO, "usuario ya existe");
                System.out.println(ex.toString());
            }
            usuario2.setPartidas_ganadas(10);
            clasificacion.ordenar();
            System.out.println(clasificacion.mostrarClasificacion());
            
            usuario.setPartidas_ganadas(100);
            clasificacion.ordenar();
            System.out.println(clasificacion.mostrarClasificacion());
            /*
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
            }*/
    }
}

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
       
        Combinacion c1 = new Combinacion("RVAA");
        
        Combinacion c2 = new Combinacion("BBNN");
        
        Combinacion c3 = new Combinacion("AAVV");

        Combinacion c4 = new Combinacion("VVRR");

        Combinacion c5 = new Combinacion("VRAA");

        
        int aciertos2 = c1.devolverAciertos(c2);
        int colocados2 = c1.devolverColocados(c2);
        
        System.out.println(aciertos2+" | "+colocados2);
        
        int aciertos3 = c1.devolverAciertos(c3);
        int colocados3 = c1.devolverColocados(c3);
        
        System.out.println(aciertos3+" | "+colocados3);
        
        int aciertos4 = c1.devolverAciertos(c4);
        int colocados4 = c1.devolverColocados(c4);
        
        System.out.println(aciertos4+" | "+colocados4);
        
        int aciertos5 = c1.devolverAciertos(c5);
        int colocados5 = c1.devolverColocados(c5);
        
        System.out.println(aciertos5+" | "+colocados5);
        /*
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
        */
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

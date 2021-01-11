/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Yaros
 */
public class Combinacion {
    private final char [] COLORES = {'B','N','A','R','V','M'};
    private char [] clave = new char[4];
    
    //constructor para generar clave de modo automatico
    public Combinacion(){
        
        Random rd = new Random();
        
        for(int i=0;i<this.clave.length;i++){
            this.clave[i]=this.COLORES[rd.nextInt(5)];
        }
        
    }
    
    //constructor para generar clave de modo manual
    public Combinacion(String clave){
        
        for(int i=0;i<this.clave.length;i++){
            this.clave[i]=clave.toUpperCase().charAt(i);
        }
    }
    
    
    public int devolverAciertos(Combinacion c){
        int aciertos = 0;
        char[] cl = clave.clone();
        for (int i = 0; i < clave.length; i++) {
            for (int j = 0; j < clave.length; j++) {
                if(cl[i]==c.getClave()[j]){
                    aciertos++;
                    break;
                }
            }
        }
        
        return aciertos;
    }
    
    public int devolverColocados(Combinacion c){
        int colocados=0;
        
        for (int i = 0; i < clave.length; i++) {
            if(c.getClave()[i]==clave[i]){
                colocados++;
            }
        }
        
        return colocados;
    }
    
    private char[] getClave(){
        return this.clave;
    }
    
    @Override
    public String toString() {
        String s = "";
        for(char c: clave){
            s+=c;
        }
        
        return s;
    }
    
    
    
}

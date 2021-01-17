/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Yaros
 */
public class Combinacion implements Serializable{
    private final char [] COLORES = {'B','N','A','R','V','M'};
    private char [] clave = new char[4];
    
    //constructor para generar clave de modo automatico
    public Combinacion(){
        
        Random rd = new Random();
        
        for(int i=0;i<this.clave.length;i++){
            this.clave[i]=this.COLORES[rd.nextInt(5)];
        }
        
    }
    
    public Combinacion(Combinacion c){
        this.clave=c.getClave();
    }
    
    //constructor para generar clave de modo manual
    public Combinacion(String s){
        
        for (int i = 0; i < this.clave.length; i++) {
            this.clave[i]=s.toUpperCase().charAt(i);
        }

    }    
    
    public int devolverAciertos(Combinacion c){
        int aciertos = 0;
        int ultimo_acertado=-1;
        
        for (int i = 0; i < clave.length; i++) {
            for (int j = 0; j < clave.length; j++) {
                if(j!=ultimo_acertado && this.clave[i]==c.getClave()[j]){
                    ultimo_acertado=j;
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
        StringBuilder stringBuilder = new StringBuilder();
        for(char c: clave){
            stringBuilder.append(c);
        }
        
        return stringBuilder.toString();
    }
    
    //metodo  para comprobar que la combinacion se crea solo con colores definidos
    public boolean checkCombinacion() {
        boolean valida=false;
        int caracteres_validos=0; //contador de caracteres validos
        
        //bucle doble de comprobacion
        for (int i = 0; i < clave.length; i++) {
            char c = this.clave[i];
            for (int j = 0; j < COLORES.length; j++) {
                if(c==COLORES[j]){
                    caracteres_validos++;
                }
            }
        }
        
        if(caracteres_validos==clave.length){
            valida=true;
        }
       return valida;
    }
    
}

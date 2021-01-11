/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;
import java.util.Scanner;
/**
 *
 * @author Yaros
 */
public class Ronda {

    private int numero_de_intentos;
    private int puntos;
    private int[] aciertos;
    private int[] colocados;
    private Combinacion combinacion;
    private Scanner teclado = new Scanner(System.in);
    
    public Ronda(Combinacion c){    //modo partida
        
        numero_de_intentos = 10;
        puntos = 0;
    }
    
    public Ronda(int n){     //modo entrenamiento
        
        numero_de_intentos = n;
        //Si el ususario introduce 0 intentos, tendrá intentos infinitos
        if(numero_de_intentos == 0){
            numero_de_intentos = Integer.MAX_VALUE;
        }
        combinacion = new Combinacion();    //comb random
        
        
    }
    
    public Combinacion añadirCombinacion(){
        String clave = teclado.nextLine();
        
        Combinacion c = new Combinacion(clave);
        
        return c;
    }
    
    public int getPuntos(){
        return puntos;
    }
    
    public void calcularPistas(int contador_int, Combinacion clave, Combinacion intento){
        
        aciertos[contador_int] = clave.devolverAciertos(intento);
        colocados[contador_int] = clave.devolverColocados(intento);
        
    }
    
    public void jugar(Combinacion clave){
        Combinacion[] intento = new Combinacion[numero_de_intentos];
        
        //Bucle que se repite hasta que el jugador acierte o se terminen los intentos
        for(int i = 0; i < numero_de_intentos; i++){
            //Se pide la clave al usuario
            System.out.print("Introduce el " + (i+1) +" intento: ");
            intento[i] = añadirCombinacion(); 
            
            //Se calculan los aciertos y los colocados
            calcularPistas(i, combinacion, intento[i]);
            
            //Se informa de los aciertos y fallos en cada intento
            for(int j = 0; j <= i; j++){
                System.out.println((j+1)+".- "+ aciertos[j]+ " acierto(-s) con "+ colocados[j]+ " colocado(-s)");
            }
            //Se comprueba que ambas claves sean iguales.
            if(clave.toString() == intento.toString()){
                //Se calcula el total de puntos de la ronda.
                puntos = 10-i;
                //Se cierra el bucle for.
                i = numero_de_intentos;
                //Se felicita al usuario.
                System.out.println("¡¡¡GANADOR!!!");
            }
        }
    }


}

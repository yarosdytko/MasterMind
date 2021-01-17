/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Yaros
 */

public class Ronda implements Serializable {

    private int numero_de_intentos;
    private int intentos_gastados = 0;
    private int puntos;
    private ArrayList<Integer> aciertos = new ArrayList<>();
    private ArrayList<Integer> colocados = new ArrayList<>();
    private Combinacion combinacionSecreta;
    private ArrayList<Combinacion> intentos = new ArrayList<>();
    private boolean finRonda = false;
    //private StringBuilder rondaLog = new StringBuilder();
    
    //modo partida
    public Ronda(Combinacion combinacionSecreta){    
        this.numero_de_intentos = 10;
        this.puntos = 0;
        this.combinacionSecreta=combinacionSecreta;
    }
    
    //modo entrenamiento
    public Ronda(int intentos){     
        this.numero_de_intentos = intentos;
        this.combinacionSecreta = new Combinacion();    //comb random para entrenamiento
    }
    
    public int getPuntos(){
        
        return this.puntos;
    }
    
    public int getIntentosGastados(){
        return this.intentos_gastados;
    }
    
    //para obtener la clave secreta
    public Combinacion getClave(){
        return combinacionSecreta;
    }
    
    //para obtener la clave del ultimo intento
    public Combinacion getIntento(){
        return this.intentos.get(intentos_gastados-1);
    }
    
    //para obtener la clave de cualquier intento
    /*public Combinacion getIntento(int numero_de_intento){
        if(numero_de_intento<=intentos_gastados){
            return intentos.get(numero_de_intento-1);
        } else {
            return null;
        }
    }*/
    
    public int getNumeroDeIntento(){
        return this.intentos_gastados;
    }
    
    public int getIntentos(){
        return this.numero_de_intentos;
    }
    
    //devuelve los ultimos aciertos del array de los aciertos
    public int getAciertos(){
        return this.aciertos.get(intentos_gastados-1);
    }
    
    //devuelve los ultimos colocados del array de colocados
    public int getColocados(){
        return this.colocados.get(intentos_gastados-1);
    }
    
    //devuelve un acierto dado del array de aciertos
    public int getAcierto(int posicion){
        return this.aciertos.get(posicion);
    }
    
    //devuelve un colocado dado del array de colocados
    public int getColocado(int posicion){
        return this.colocados.get(posicion);
    }
    
    //devuelve un intento dado del array de intentos
    public Combinacion getIntento(int posicion){
        return this.intentos.get(posicion);
    }
    

    
    //solo accesible para admin
    public String verClave(boolean entrenamiento, boolean admin){
        if(admin && entrenamiento){
            return (this.getClave().toString());
        }else{
            return ("Necesitas ser administrador y estar jugando en entrenamiento para ver la clave");
        }
    }
    
    /*public void calcularPistas(int contador_int, Combinacion clave, Combinacion intento){
        
        this.aciertos[contador_int] = clave.devolverAciertos(intento);
        this.colocados[contador_int] = clave.devolverColocados(intento);
        
    }*/
    
    //solo accesible para admin
    public void setIntentos(int n, boolean administrador){
        if(administrador){
            this.numero_de_intentos = n;
        }else{
            System.out.println("Necesitas ser administrador para hacer eso");
        }
    }
    
    public int getIntentosRestantes(){
        return (this.numero_de_intentos-this.intentos_gastados);
    }
    
    public boolean esGanadora(){
        return this.getAciertos()==4 && this.getColocados()==4;
    }
    
    /*public String getRondaLog(){
        return this.rondaLog.toString();
    }*/
    
    //se puede prescindir de este metodo, al menos de momento
    /*public void añadirCombinacion(Combinacion c){
        this.intentos.add(new Combinacion(c)); //se añade una combinacion nueva al array de intentos
    }*/
    
    
    
    public void jugar(Combinacion c){
        //StringBuilder sb = new StringBuilder();
        intentos_gastados++;

        if(!finRonda){
            Combinacion intento = new Combinacion(c);
            intentos.add(intento);
            //sb.append(intentos_gastados+".- "+intento.toString()+" ");
            
            //calculo colocados y aciertos 
            int ac = combinacionSecreta.devolverAciertos(intento);
            int col = combinacionSecreta.devolverColocados(intento);
            this.aciertos.add(ac);
            this.colocados.add(col);
            
            //sb.append(ac+" aciertos con "+col+" colocados\n");
            
            if(esGanadora()){
                this.puntos = (this.numero_de_intentos - this.intentos_gastados);
                finRonda=true;
                //sb.append("\n------- !!Ganador!!-------\n");
            }
        }
        if(intentos_gastados==numero_de_intentos){
            finRonda=true;
            
           // sb.append("Ya se han agotado todos los intentos, fin de la ronda\n");
        }
        //rondaLog.append(sb.toString());
    }
    
    
    //hay que tunear el to string, aunqe no estoy seguro que en esta clase haga falta ese metodo
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Combinación de la ronda: "+ combinacionSecreta+ "\n-------------\n");
        for(int i = 0; i < intentos.size(); i++){
            s.append("Intento"+(i+1)+".- "+intentos.get(i).toString()+": "+ aciertos.get(i)+ " acierto(-s) con "+ colocados.get(i)+" colocado(-s)\n");
        }
        s.append("----------\nTotal de puntos: "+puntos);
        return (s.toString());
    }


}

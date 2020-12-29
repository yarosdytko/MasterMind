/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterMind;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Yaros
 */
public class Partida {
    private LocalDate fecha;
    private int numero_de_rondas;
    private Usuario usuario1;
    private Usuario usuario2;
    private ArrayList<Ronda> rondas[];

    public Partida(Usuario usuario1, Usuario usuario2) {
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
    }
    
    


}

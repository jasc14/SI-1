/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1819_p2si;

import java.util.ArrayList;

/**
 *
 * @author Francisco Javier
 */

public class Adaboost {
    public Adaboost(){}
    public ArrayList<ArrayList<ClasificadorDebil>> algoritmoAdaboost(int categoria, ArrayList<ArrayList> imagenes, ArrayList vecPesos){
        ArrayList<ArrayList<ClasificadorDebil>> cd = new ArrayList<ArrayList<ClasificadorDebil>>();
        
        
        
        return cd;
    }
    // metodo que devuelve el vector de pesos
    public ArrayList obtenerVectorDePesos(ArrayList<ArrayList> imagenes, boolean inicializado){
        ArrayList vectorPesos = new ArrayList();
        int n;
        float z;
        if(!inicializado){    
            for(int i = 0; i < 8; i++){
                n = imagenes.get(i).size();
                vectorPesos.add(1/n);
            }
        }
        else{
            // Introducir aquie comportamiento de la actualizacion del vector despues de la iniciazizacion
            //(FOTO DE PAULA EN WASAP)
        }
        return vectorPesos;
    }
    
}

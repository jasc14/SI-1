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
public class ClasificadorFuerte{
    private ArrayList<ClasificadorDebil> cf;
    private double confianza;
    private double error;
    
    public ClasificadorFuerte(){
        cf = new ArrayList<ClasificadorDebil>();
    }
    public double getConfianza(){
        return confianza;
    }
    public double getError(){
        return error;
    }
    public ArrayList<ClasificadorDebil> getClasificadores(){
        return cf;
    }
    public void anyadeClasificador(ClasificadorDebil cl){
        cf.add(cl);
    }
}

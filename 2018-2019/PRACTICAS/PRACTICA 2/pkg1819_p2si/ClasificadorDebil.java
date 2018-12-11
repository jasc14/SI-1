/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1819_p2si;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Francisco Javier
 */
public class ClasificadorDebil {
    private int pixel;
    private int umbral;
    private int direccion;
    private Random r;
    public ClasificadorDebil(){}
    public ClasificadorDebil generarClasificadorAzar(){
        ClasificadorDebil cd = new ClasificadorDebil();
        boolean d;
        // Generamos un clasificador debil apartir de los 
        // valores random de pixel, umbral y dirección
        cd.pixel = r.nextInt(785);
        cd.umbral = r.nextInt(256);
        d = r.nextBoolean();
        if(d){
            cd.direccion = 1;
        }
        else{
            cd.direccion = -1;
        }
        return cd;
    }
}

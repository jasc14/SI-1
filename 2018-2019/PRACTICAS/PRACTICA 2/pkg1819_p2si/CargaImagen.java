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
public class CargaImagen {
    private ArrayList<Imagen> totales;
    private ArrayList<Imagen> entrenamiento;
    private ArrayList<Imagen> test;
    private int porcentaje;
    
    public CargaImagen(){
        totales = new ArrayList<>();
        entrenamiento = new ArrayList<>();
        test = new ArrayList<>();
    }
    public ArrayList<Imagen> getTotalImagenes(){
        return totales;
    }
    public ArrayList<Imagen> getImagenesEntr(){
        return entrenamiento;
    }
    public ArrayList<Imagen> getImagenesTest(){
        return test;
    }
    
    public void anyadeImagenes(DBLoader ml){
        for(int i = 0; i < 8; i++){
            ArrayList d_imgs = ml.getImageDatabaseForDigit(i);
            for(int j = 0; j < d_imgs.size(); j++){
                Imagen img = (Imagen) d_imgs.get(j);
                img.setDigitoPertenece(i);
                totales.add(img);
            }
        }
    }
    public void anyadeEntrenamiento(int porcent){
        porcentaje = porcent;
        double size1 = (4001.0*(porcent/100.0)); // he cambiado 1000.0 -> 4001.0
        int size2 = (int) size1;
        int valor;
        for(int i = 0; i < size2; i++){
            valor =  (int) (Math.random() * 4001); // he cambiado 1000 -> 4001
            if(!entrenamiento.contains(totales.get(valor))){
                entrenamiento.add(totales.get(valor));
            }
            else{
                size2 = size2 + 1;
            }
        }
    }
    public void anyadeTest(int resto){
    double size1 = (4001.0*(resto/100.0)); // he cambiado 1000.0 -> 4001.0
    int size2 = (int) size1;
        int valor;
        for(int i = 0; i < size2; i++){
            valor = (int) (Math.random() * 4001); // he cambiado 1000 -> 4001
            if(!entrenamiento.contains(totales.get(valor))){
                test.add(totales.get(valor));
            }
            else{
                size2 = size2 + 1;
            }
        }
    }
}

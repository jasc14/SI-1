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
    private float error;
    private Random r;
    private ArrayList<ArrayList<Boolean>> resultados;
    public ClasificadorDebil(int pixel, int umbral, int direccion){
        this.pixel = pixel;
        this.umbral = umbral;
        this.direccion = direccion;
        error = 0f;
        resultados = new ArrayList<ArrayList<Boolean>>();
    }
    public ClasificadorDebil generarClasificadorAzar(){
       ClasificadorDebil cd = null;
       int p, u, d;
       Random r = new Random(System.currentTimeMillis());
       p = r.nextInt(785);
       u = r.nextInt(256);
       d = (r.nextInt((1 - 0) + 1) + 0);
       cd = new ClasificadorDebil(p,u,d);
       return cd;
    }
    /*
        COMO CONSEGUIR EL PIXEL DE UNA IMAGEN
        Imagen i = imagenes.get(i).get(j)
        byte pixeles[] = i.getImageData()
        int pixelImagen = Byte.toUnsignedInt(pixeles[getPixel()]);
    */
    // Recibe las imagenes y las busca por categoria, 
    //generando asi una ArrayList de booleanos por cada categoria
    public ArrayList<ArrayList<Boolean>> aplicarClasificadorDebil(ArrayList<ArrayList<Imagen>> imagenes){
        ArrayList<Boolean> resultadosPorCategoria = new ArrayList<Boolean>();
        int pixelImagen;
        for(int i = 0; i < imagenes.size(); i++){ // recorre todas las categorias
            for(int j = 0; j < imagenes.get(i).size(); j++){ // recorre todas las imagenes j de cada categoria i
                Imagen imagen = imagenes.get(i).get(j);
                byte pixeles[] = imagen.getImageData();
                pixelImagen = Byte.toUnsignedInt(pixeles[getPixel()]); // consigue el mismo pixel que el pasado como parametro getPixel()
                if(getDireccion() == 0){
                    if(pixelImagen <= getUmbral()){
                        resultadosPorCategoria.add(true);
                    }
                    else{
                        resultadosPorCategoria.add(false);
                    }
                }
                else{
                    if(pixelImagen >= getUmbral()){
                        resultadosPorCategoria.add(true);
                    }
                    else{
                        resultadosPorCategoria.add(false);
                    }
                }
            }
            resultados.add(resultadosPorCategoria);
        }
        return resultados;
    }
    // obtiene el error del clasificador
    public void obtenerErrorClasificador(ArrayList<ArrayList<Boolean>> resEsperados, ArrayList<ArrayList<Float>> pesos){
        for(int i = 0; i < resEsperados.size(); i++){
            for(int j = 0; j < resEsperados.get(i).size(); j++){
                if(resEsperados.get(i).get(j).equals(resultados.get(i).get(j)) == false){
                    error += pesos.get(i).get(j);
                }
            }
        }
    }
    public int getPixel(){ return pixel; }
    public int getUmbral(){ return umbral; }
    public int getDireccion(){ return direccion; }
    public float confianza(){ return (float) (0.5*(Math.log(1 - error/error))/Math.log(2)); }
    public ArrayList<ArrayList<Boolean>> getResultados(){ return resultados; }
}

/*
        COMO CONSEGUIR EL PIXEL DE UNA IMAGEN
        Imagen i = imagenes.get(i).get(j)
        byte pixeles[] = i.getImageData()
        int pixelImagen = Byte.toUnsignedInt(pixeles[getPixel()]);
    */
    // Recibe las imagenes y las busca por categoria, 
    //generando asi una ArrayList de booleanos por cada categoria

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
public class ClasificadorDebil{
    private int pixel;
    private int umbral;
    private int direccion;
    public double error;
    public double confianza;
    
    public ClasificadorDebil(){
        pixel = (int) (Math.random() * 784);
        umbral = (int) (Math.random() * 256) - 127; 
        int valor = (int) (Math.random() * 2);
        if(valor == 0){
            direccion = -1;
        }
        else{
            direccion = 1;
        }
        error = 0.0;
        confianza = 0.0;
    }
     public int getPixel(){
        return pixel;
    }
    public int getUmbral(){
        return umbral;
    }
    public int getDireccion(){
        return direccion;
    }
    public double getError(){
        return error;
    }
    public double getConfianza(){
        return confianza;
    }
    public void setPixel(int pixel){
        this.pixel = pixel;
    }
    public void setUmbral(int umb){
        umbral = umb;
    }
    public void setDireccion(int direc){
        direccion = direc;
    }
    public void setError(double err){
        error = err;
    }
    public void setConfianza(double conf){
        confianza = conf;
    }
    public double Peso(){
        //MNISTLoader ml = new MNISTLoader();
        DBLoader ml = new DBLoader();
        ml.loadDBFromPath("./db");
        int numimagenes = 0;
        double peso = 0.0;
        
        for(int i = 0; i < 8; i++){ // LO HE CAMBIADO DE <= 9
            ArrayList d_imgs = ml.getImageDatabaseForDigit(i);
            numimagenes = numimagenes + d_imgs.size(); //numero total de imagenes
        }
        peso = 1.0/numimagenes;
        return peso;
    }
    public void Entrenar(ArrayList<Imagen> entrenamiento, double[] D, int numero){
        int [] binario = new int[entrenamiento.size()];
        
        for(int j = 0; j < entrenamiento.size(); j++){ //train de las imgs
            Imagen img = (Imagen) entrenamiento.get(j);

            byte imageData[] = img.getImageData();
            if(direccion == 1){
                if(imageData[pixel] < umbral){
                  binario[j] = 1;
                }
                else{
                  binario[j] = -1;
                }
            }
            else{
                if(imageData[pixel] >= umbral){
                   binario[j] = 1;
                }
                else{
                   binario[j] = -1;
                }
            }
        }
        int es;
        for(int i = 0; i < entrenamiento.size(); i++){
            if(numero == entrenamiento.get(i).getDigitoPertenece()){ //Si pertenece con el digito de esa imagen
                es = 1;
            }
            else{
                es = -1;
            }
            //Tenemos que sumar el peso de donde ha fallado para calcular el error
            if(binario[i] == 1 && es == -1){
                this.error += D[i];
            }
            if(binario[i] == -1 && es == 1){
                this.error += D[i];
            }
        }
        double numerador = (double)(1 - error)/error;
        confianza = (double) 0.5 * Math.log(numerador);   
    }
    public void AplicaPixel(ArrayList<Imagen> vectorimg, int[] binario){
       
        for(int j = 0; j < vectorimg.size(); j++){ //train de las imgs
            Imagen img = (Imagen) vectorimg.get(j);

            byte imageData[] = img.getImageData();
            //for (int k = 0; k < imageData.length; k++){
             //     imageData[k] = (byte) (255 - imageData[k]);
            //}
            if(direccion == 1){
                if(imageData[pixel] < umbral){
                  binario[j] = 1;
                }
                else{
                  binario[j] = -1;
                }
            }
            else{
                if(imageData[pixel] >= umbral){
                   binario[j] = 1;
                }
                else{
                   binario[j] = -1;
                }
            }
        } 
    }
    public int AplicaImagen(Imagen img){
        byte[] v_bytes = img.getImageData();
        int r = 0;
        
        if(direccion == 1){
            if(v_bytes[pixel] < umbral){
                r = 1;
            }
            else if(v_bytes[pixel] >= umbral){
                r = -1;
            }
        }
        else{
           if(v_bytes[pixel] < umbral){
                r = -1;
            }
            else if(v_bytes[pixel] >= umbral){
                r = 1;
            } 
        }
        return r;
    }
}
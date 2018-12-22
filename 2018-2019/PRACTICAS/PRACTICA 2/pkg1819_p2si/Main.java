/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1819_p2si;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author fidel
 */
class AdaBoost {
    private ClasificadorFuerte cf;
    
    AdaBoost(){
        cf = new ClasificadorFuerte();
    }
    
    public ClasificadorFuerte getClasificadorFuerte(){
        return cf;
    }
    
    public ClasificadorDebil mejorClasificador(ArrayList<ClasificadorDebil> clasificadores){
        ClasificadorDebil mejor = null;
        
        for(ClasificadorDebil cd : clasificadores){
            if(mejor == null || cd.getError() < mejor.getError()){
                mejor = cd;
            }
        }
        return mejor;
    }
    
    public void inicializaPesos(double[] D){
        for(int i = 0; i < D.length; i++){
            D[i] = 1.0/D.length;
        }
    }
    
    /**
     * @param D 
     */
    public void ActualizarDt(ArrayList<Imagen> entrenamiento, ClasificadorDebil mejor, double[] D, int[] binario, int numero){
        int res;
        
        for(int i = 0; i < D.length; i++){
            if(numero == entrenamiento.get(i).getDigitoPertenece()){
                res = 1;
            }
            else{
                res = -1;
            }
            
            if(binario[i] == -1 && res == 1){
                D[i] = (double) (D[i] * Math.pow(Math.E, mejor.getConfianza()));
            }
            else{
                if(binario[i] == 1 && res == -1){
                    D[i] = (double) (D[i] * Math.pow(Math.E, mejor.getConfianza()));
                }
                else{
                    D[i] = (double) (D[i] * Math.pow(Math.E, -1 * mejor.getConfianza()));
                }
            }
        }
        double Z = 0;
        for(int i = 0; i < D.length; i++){
            Z += D[i];
        }
        
        if(Z != 1){ //De ser 1 el resultado seria el mismo
            for(int i = 0; i < D.length; i++){
                D[i] = D[i]/Z;
            }
        }
    }
    
    public void AlgoritmoAdaboost(ArrayList<Imagen> entrenamiento, int cuantosDebiles, int numero){
        int res;
        double sumatorio = 0;
        //System.out.println("¡ Adaboost en ejecución !");
        double[] D = new double[entrenamiento.size()];
        inicializaPesos(D);
        
        for(int i = 0; i < 200; i++){
          ArrayList<ClasificadorDebil> debiles = new ArrayList<ClasificadorDebil>();
          ClasificadorDebil mejor = null;
          
            for(int j = 0; j < cuantosDebiles; j++){
                ClasificadorDebil ht = new ClasificadorDebil();
                ht.Entrenar(entrenamiento,D, numero);
                debiles.add(ht);
            }
            mejor = mejorClasificador(debiles);
            int []binario = new int[entrenamiento.size()];
            mejor.AplicaPixel(entrenamiento,binario);
            //Actualizar Dt + 1
            ActualizarDt(entrenamiento, mejor,D, binario, numero);
            cf.anyadeClasificador(mejor);
        }
    }
    
    public int Clasifica(Imagen img){
        ClasificadorDebil d = null;
        int aplicado = 0;
        double confianza = 0.0;
        double suma = 0.0;
        
        for(int i = 0; i < cf.getClasificadores().size(); i++){
            d = cf.getClasificadores().get(i);
            aplicado = d.AplicaImagen(img);
            confianza = d.getConfianza();
            suma = suma + (aplicado * confianza);
        }
        if(suma > 0){
            return 1;
        }else if(suma < 0){
            return 0;
        }else{return 0;}
    }

}
public class Main {
    private ArrayList<AdaBoost> v_adaboost;
    
    public Main(){
        v_adaboost = new ArrayList<AdaBoost>();
    }
    
    public void anyade(AdaBoost ada){
        v_adaboost.add(ada);
    }
    
    public ArrayList<AdaBoost> getAda(){
        return v_adaboost;
    }
    
    public float AplicaFuertes(Imagen imagen, AdaBoost ada){
        float resultado = 0;
        
        for(int i = 0; i <  ada.getClasificadorFuerte().getClasificadores().size();i++){
            int aplicado = ada.getClasificadorFuerte().getClasificadores().get(i).AplicaImagen(imagen);
            double confianza = ada.getClasificadorFuerte().getClasificadores().get(i).getConfianza();
            resultado += (double)aplicado * confianza; 
        }
        return resultado;
    }
    
    public static String devolverElemento(int digito){
        String cad;
        switch(digito){
            case 0:
                cad = "abrigo";
                break;
            case 1:
                cad = "bolso";
                break;
            case 2:
                cad = "camiseta";
                break;
            case 3:
                cad = "pantalon";
                break;
            case 4:
                cad = "sueter";
                break;
            case 5:
                cad = "vestido";
                break;
            case 6:
                cad = "zapatillas";
                break;
            case 7:
                cad = "zapatos";
                break;
            default:
                cad = "";
        }
        return cad;
    }
    
    public static void main(String[] args) {
        
        //Cargador de la BD de SI
        DBLoader ml = new DBLoader();
        ml.loadDBFromPath("./db");
        //Accedo a las imagenes de bolsos
        ArrayList d0imgs = ml.getImageDatabaseForDigit(1);
        
        //Y cojo el decimo bolso de la bd
        Imagen img = (Imagen) d0imgs.get(9);
        
        //La invierto para ilustrar como acceder a los pixels y imprimo los pixeles
        //en hexadecimal
        System.out.print("Image pixels: ");
        byte imageData[] = img.getImageData();
        for (int i = 0; i < imageData.length; i++)
        {
            imageData[i] = (byte) (255 - imageData[i]);
            System.out.format("%02X ", imageData[i]);
        }
        
        //Muestro la imagen invertida
        MostrarImagen imgShow = new MostrarImagen();
        imgShow.setImage(img);
        imgShow.mostrar();
        // ----------------------------------------------------------
        // ---------------- COMIENZO DE LA PRACTICA -----------------
        // ----------------------------------------------------------
        Main m = new Main();
        CargaImagen carga = new CargaImagen();
        carga.anyadeImagenes(ml);
        carga.anyadeEntrenamiento(80); //Escogemos por ejemplo un 80% para entrenar
        carga.anyadeTest(20); //Un 20% para test
        double contador = 0;
      
        if(args.length >= 2){
            if(args[0].equals("-train")){ //   
                for(int i = 0; i < 8; i++){
                    AdaBoost adb = new AdaBoost();
                    adb.AlgoritmoAdaboost(carga.getImagenesEntr(), 100, i);
                    contador = 0;

                    for(int k = 0; k < carga.getImagenesEntr().size(); k++){    
                        int coincide;
                            int res = adb.Clasifica(carga.getImagenesEntr().get(k));
                            if(i == carga.getImagenesEntr().get(k).getDigitoPertenece()){
                                coincide = 1;
                            }
                            else{
                                coincide = 0;
                            }
                            if(res == coincide){
                                contador++;
                            }
                    }
                    float resultado = (float) (((contador) / (carga.getImagenesEntr().size())) * 100);
                    System.out.print("Recuento de aciertos de entrenamiento para " + i + ":      ");
                    System.out.println("Recuento de aciertos de test para: " + i + ":     ");
                    System.out.print("     " + resultado +"%");
                    contador = 0;
                    for(int k = 0; k < carga.getImagenesTest().size(); k++){    
                        int coincide;
                            int res = adb.Clasifica(carga.getImagenesTest().get(k));
                            if(i == carga.getImagenesTest().get(k).getDigitoPertenece()){
                                coincide = 1;
                            }
                            else{
                                coincide = 0;
                            }
                            if(res == coincide){
                                contador++;
                            }
                    }
                    System.out.println("                                            " + (float) (((contador)/(carga.getImagenesTest().size()))*100) +"%");
                    m.anyade(adb);
                }
                Fichero.creaFichero(m, args[1]);
                System.out.println("Fichero creado o sobreescrito!");
            }
            else{
                if(args[0].equals("-run")){
                    Fichero.leeFichero(m, args[1]); // 0 -> 1
                    //System.out.println("Los argumentos 1" +args[1]);
                    String directorio_imagen = "." + args[2]; // 1 -> 2
                    File file = new File(directorio_imagen);
                    Imagen img1 = new Imagen(file.getAbsoluteFile());

                    int digito_pertenece = 0;
                    float r = m.AplicaFuertes(img1, m.getAda().get(0));

                    for(int i = 1; i < m.getAda().size(); i++){
                        float res = m.AplicaFuertes(img1, m.getAda().get(i));
                        if(r < res){
                            digito_pertenece = i;
                            r = m.AplicaFuertes(img1, m.getAda().get(i));
                        }
                    }
                    String cd = devolverElemento(digito_pertenece);
                    System.out.println();
                    System.out.println("La imagen es: " + cd);
                }
            }
        }
        else{
            System.out.println("Error en los argumentos.");
            System.out.println("1) Adaboost –train <nombre_fichero.cf>");
            System.out.println("2) Adaboost -run <nombre_fichero.cf> <imagen_prueba>");
        }
    } 
}

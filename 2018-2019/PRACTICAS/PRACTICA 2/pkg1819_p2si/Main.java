/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1819_p2si;

import java.util.ArrayList;

/**
 *
 * @author fidel
 */
public class Main {

    
    /**
     * @param args the command line arguments
     */
    // metodo que devuele el conjunto de entrenamiento o el de test en funcion de un porcentaje
    public static ArrayList conjuntoDe(int categoria, int porcentaje, ArrayList<ArrayList> imagenesPorCat){
        ArrayList imagenes = new ArrayList();
        int cantidadImagenes;
        int j = 0;
        if(porcentaje == 80){
            cantidadImagenes = (int) ((int) imagenesPorCat.get(categoria).size() * 0.8);
        }
        else{
            cantidadImagenes = (int) ((int) imagenesPorCat.get(categoria).size() * 0.8);
            j = cantidadImagenes;
            cantidadImagenes = imagenesPorCat.get(categoria).size();
        }
        while(j < cantidadImagenes){
            imagenes.add(imagenesPorCat.get(categoria).get(j));
            j++;
        }
        return imagenes;
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
        // 1º Obtengo un ArrayList con todas categorias
        ArrayList<ArrayList> imagenesPorCategorias = new ArrayList<ArrayList>();
        for(int i = 0; i < 8; i++){
            imagenesPorCategorias.add(ml.getImageDatabaseForDigit(i)); // esto me crea un arrayList con todas las categorias que asu vez tienen
                                                                       // todas las imagens de cada una de las categorias
        }
        /*
        Prueba de ver cuantas imagenes alamceno por categorias
        for(int i = 0; i < imagenesPorCategorias.size(); i++){
            System.out.println("La categoria " + i + " tiene " + imagenesPorCategorias.get(i).size() + " imagenes"); 
        }
        */
        // ********
        // MIRAR SI HAY QUE CREAR AQUI LOS CONJUNTOS DE ENTRENAMIENTO (80%) Y DE TEST (20%)
        ArrayList<ArrayList> entrenamiento = new ArrayList<ArrayList>();
        ArrayList<ArrayList> test = new ArrayList<ArrayList>();
        for(int i = 0; i < 8; i++){
            entrenamiento.add(conjuntoDe(i, 80, imagenesPorCategorias));        
        }
        for(int i = 0; i < 8; i++){
            test.add(conjuntoDe(i, 20, imagenesPorCategorias));        
        }
        /*
        
        for(int i = 0; i < entrenamiento.size(); i++){
            System.out.println("ENTRENAMIENTO La categoria " + i + " tiene " + entrenamiento.get(i).size() + " imagenes"); 
        }
        for(int i = 0; i < test.size(); i++){
            System.out.println("TEST La categoria " + i + " tiene " + test.get(i).size() + " imagenes"); 
        
        */
        
        // ********
        
        // 3º ahora tenemos que lanzar adaboost tantas veces como categorias hay pasandole la categoria que queremos analzizar
        ArrayList<ArrayList<ClasificadorDebil>> clasificadorFuerte = new ArrayList<ArrayList<ClasificadorDebil>>(); // Un clasificador fuerte esta compuesto por
                                                                                                                    // un conjunto de clasificadores debiles esto crea un clasificador fuerte formado 
                                                                                                                    // por clasificadores debiles de cada una de las categorias
        //ArrayList<ClasificadorDebil> clasificadorFuerte = new ArrayList<ClasificadorDebil>();
        Adaboost a = new Adaboost();
        // TENGO QUE OBTENER EL VECTOR DE PESOS INICIAL, TODAS LAS POSICIONES ESTAN INICIALZADAS A 1 / numeroImagnesEntrenamiento
        ArrayList vectorPesos = a.obtenerVectorDePesos(entrenamiento, false);
        for(int categoria = 0; categoria < 8; categoria++){
            // llamamos a adaboost
            // le pasamos la categoria que queremos analizar
            // le pasamos el conjunto de entrenamiento
            clasificadorFuerte = a.algoritmoAdaboost(categoria, entrenamiento, vectorPesos);
            vectorPesos = a.obtenerVectorDePesos(entrenamiento, true);
        
        }
    }
}

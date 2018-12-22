/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1819_p2si;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author Francisco Javier
 */
public class Fichero {
    public static void creaFichero(Main m, String fich){
        try {
            FileWriter fichero = new FileWriter(fich);
            PrintWriter print = new PrintWriter(fich);
            int tamanyo = 0;
            for(int i = 0; i < 8; i++){
                tamanyo = m.getAda().get(i).getClasificadorFuerte().getClasificadores().size();
                for(int j = 0; j < tamanyo; j++){
                    ClasificadorDebil d = m.getAda().get(i).getClasificadorFuerte().getClasificadores().get(j);
                    print.println(d.getUmbral());
                    print.println(d.getPixel());
                    print.println(d.getDireccion());
                    print.println(d.getError());
                    print.println(d.getConfianza());
                    print.println();
                }
            }
        } catch (Exception ex) {
           System.out.println("Excepcion con el fichero" + ex );
        }
    }
    public static void leeFichero(Main m, String fich){
        try {
            String umbral, pixel, direccion, error, confianza;
            AdaBoost ada = null;
            FileReader reader = new FileReader(fich);
            BufferedReader buffer = new BufferedReader(reader);
            buffer.readLine();
            for(int i = 0; i < 8; i++){
                ada = new AdaBoost(); //10 adaboost
                ClasificadorDebil nuevo = null;
                for(int j = 0; j < 200; j++){
                    nuevo = new ClasificadorDebil();
                    umbral = buffer.readLine();
                    pixel = buffer.readLine();
                    direccion = buffer.readLine();
                    error = buffer.readLine();
                    confianza = buffer.readLine();
                    buffer.readLine();
                    nuevo.setUmbral(Integer.parseInt(umbral));
                    nuevo.setPixel(Integer.parseInt(pixel));
                    nuevo.setDireccion(Integer.parseInt(direccion));
                    nuevo.setError(Double.parseDouble(error));
                    nuevo.setConfianza(Double.parseDouble(confianza));
                    ada.getClasificadorFuerte().anyadeClasificador(nuevo);
                }
                m.anyade(ada);
            }
            
        } catch (Exception ex) {
            
        }
    }
}

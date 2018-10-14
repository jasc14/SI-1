// ME HE QUEDADO EN LA LINEA 63 DEL GITHUB
// https://github.com/hunzaGit/Algoritmo-A-estrella/blob/master/src/Negocio/aStar/Algoritmo_A_Estrella.java


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AEstrella;

import java.util.ArrayList;

/**
 *
 * @author mirse
 */

class Nodo{
    Nodo padre;
    Coordenada c;
    int f;
    int g;
    int h;
    ArrayList<Nodo> listaVecinos;
    Nodo vecino1;
    Nodo vecino2;
    Nodo vecino3;
    Nodo vecino4;
    Nodo vecino5;
    Nodo vecino6;
    public Nodo(Coordenada coord, int g, int h){
        c = coord;
        this.g = g;
        this.h = h;
        f = g + h;
        listaVecinos = new ArrayList<Nodo>();
    }
    public Nodo getPadre(){return padre; }
    public void setPadre(Nodo p) {padre = p;}
    public Coordenada getCoordenada(){ return c; }
    public void setCoordenada(Coordenada c){ this.c = c;}
    public int getG(){ return g; }
    public void setG(int g) {this.g = g;}
    public int getH(){ return h; }
    public void setH(int h) {this.h = h;}
    public int getF(){ return f; }
    public void setF(int f) {this.f = f;}
    public ArrayList<Nodo> getListaVecinos() { return listaVecinos; }
    public Nodo getVecino1(){ return vecino1; }
    public void setVecino1(Nodo n){
        if(listaVecinos.contains(vecino1)){
            listaVecinos.remove(vecino1);
        }
        listaVecinos.add(n);
        vecino1 = n;
    }
    public Nodo getVecino2(){ return vecino2; }
    public void setVecino2(Nodo n){
        if(listaVecinos.contains(vecino2)){
            listaVecinos.remove(vecino2);
        }
        listaVecinos.add(n);
        vecino2 = n;
    }
    public Nodo getVecino3(){ return vecino3; }
    public void setVecino3(Nodo n){
        if(listaVecinos.contains(vecino3)){
            listaVecinos.remove(vecino3);
        }
        listaVecinos.add(n);
        vecino3 = n;
    }
    public Nodo getVecino4(){ return vecino4; }
    public void setVecino4(Nodo n){
        if(listaVecinos.contains(vecino4)){
            listaVecinos.remove(vecino4);
        }
        listaVecinos.add(n);
        vecino4 = n;
    }
    public Nodo getVecino5(){ return vecino5; }
    public void setVecino5(Nodo n){
        if(listaVecinos.contains(vecino5)){
            listaVecinos.remove(vecino5);
        }
        listaVecinos.add(n);
        vecino5 = n;
    }
    public Nodo getVecino6(){ return vecino6; }
    public void setVecino6(Nodo n){
        if(listaVecinos.contains(vecino6)){
            listaVecinos.remove(vecino6);
        }
        listaVecinos.add(n);
        vecino6 = n;
    }
    // PREGUNTAR SI ES ASI O SI NO ES ASI
    double getDistanciaEntre(Nodo n){
        double distancia = 0;
        //Math.sqrt(Math.pow(c.row - row,2) + Math.pow(c.column - row,2));
        distancia = Math.sqrt(Math.pow(this.c.getX() - n.c.getX(), 2) + Math.pow(this.c.getY() - n.c.getY() ,2));
        return distancia;
    }
}
public class AEstrella {
 
    //Mundo sobre el que se debe calcular A*
    Mundo mundo;
    
    //Camino
    public char camino[][];
    
    //Casillas expandidas
    int camino_expandido[][];
    
    //Número de nodos expandidos
    int expandidos;
    
    //Coste del camino
    float coste_total;
    
    public AEstrella(){
        expandidos = 0;
        mundo = new Mundo();
    }
    
    public AEstrella(Mundo m){
        //Copia el mundo que le llega por parámetro
        mundo = new Mundo(m);
        camino = new char[m.tamanyo_y][m.tamanyo_x];
        camino_expandido = new int[m.tamanyo_y][m.tamanyo_x];
        expandidos = 0;
        
        //Inicializa las variables camino y camino_expandidos donde el A* debe incluir el resultado
            for(int i=0;i<m.tamanyo_x;i++)
                for(int j=0;j<m.tamanyo_y;j++){
                    camino[j][i] = '.';
                    camino_expandido[j][i] = -1;
                }
    }
    
    //Calcula el A*
    public int CalcularAEstrella(){

        boolean encontrado = false;
        int result = -1;
        //AQUÍ ES DONDE SE DEBE IMPLEMENTAR A*
        boolean esLaMeta;
        ArrayList<Nodo> listaInterior = new ArrayList<Nodo>();
        ArrayList<Nodo> listaFrontera = new ArrayList<Nodo>();
        Coordenada posInicio = mundo.getCaballero(); // CONSEGUIMOS EL VALOR DEL ORIGEN
        Coordenada posFinal = mundo.getDragon(); // Y EL VALOR DEL FINAL DEL MAPA
        listaInterior.clear();
        int g = posInicio.getX() + posInicio.getY();
        int h = posFinal.getX() + posFinal.getY();
        Nodo nodoInicio = new Nodo(posInicio, g, h); // CREAMOS EL NODO INICIAL
        listaFrontera.add(nodoInicio); // Y LO AÑADIMOS A LISTAFRONTERA
       
        while(listaFrontera.size() != 0){
            //Obtenemos de listaFrontera el nodo con menor F
            // CUANDO ESTE IMPLEMENTADO PONER TODO ESTO EN UN METODO AUXILIAR
            Nodo n = null;
            if(listaFrontera.size() == 1){
                n = listaFrontera.get(0);
            }
            else{
                int pos = 0;
                for(int i = 0; i < listaFrontera.size(); i++){
                    if(listaFrontera.get(pos).getF() >= listaFrontera.get(i).getF()){
                        pos = i;
                    }
                }
                n = listaFrontera.get(pos);
            }
            // AHORA TENGO QUE COMPROBAR SI EL NODO ACTUAL ES META
            esLaMeta = false;
            if(n.getCoordenada().getX() == posFinal.getX() 
                    && n.getCoordenada().getY() == posFinal.getY()){
                esLaMeta = true;
            }
            // 
            if(esLaMeta){
                // reconstruir el camino desde la meta al inicio siguiento los punteros
                
            }
            else{
                // borrar de listaFrontera actual
                listaFrontera.remove(n);
                listaInterior.add(n);
                for(Nodo m : n.getListaVecinos()){
                    //boolean esMejorVecino;
                    // hay que calcular una g' que no se mucho lo que es
                    double gPrima = n.getG() + n.getDistanciaEntre(m);
                    if(!listaFrontera.contains(m)){
                        m.setCoordenada(n.getCoordenada());
                        m.setF(n.getF());
                        m.setH(n.getH());
                        m.setG(n.getG());
                        m.setPadre(n);
                        listaFrontera.add(m);
                    }
                    else{
                        // Verificamos si el nuevo camino es mejor
                        if(gPrima < m.getG()){
                            m.setPadre(n);
                            // recalculamos f y g del nodo m
                            int gm = m.c.getX() + m.c.getY();
                            int fm = gm + h;
                            m.setG(gm);
                            m.setF(fm);
                        }
                    }
                }
            }
        }


        //Si ha encontrado la solución, es decir, el camino, muestra las matrices camino y camino_expandidos y el número de nodos expandidos
        if(encontrado){
            //Mostrar las soluciones
            System.out.println("Camino");
            mostrarCamino();

            System.out.println("Camino explorado");
            mostrarCaminoExpandido();
            
            System.out.println("Nodos expandidos: "+expandidos);
        }

        return result;
    }
    

    
    
   
    
    //Muestra la matriz que contendrá el camino después de calcular A*
    public void mostrarCamino(){
        for (int i=0; i<mundo.tamanyo_y; i++){
            if(i%2==0)
                System.out.print(" ");
            for(int j=0;j<mundo.tamanyo_x; j++){
                System.out.print(camino[i][j]+" ");
            }
            System.out.println();   
        }
    }
    
    //Muestra la matriz que contendrá el orden de los nodos expandidos después de calcular A*
    public void mostrarCaminoExpandido(){
        for (int i=0; i<mundo.tamanyo_y; i++){
            if(i%2==0)
                    System.out.print(" ");
            for(int j=0;j<mundo.tamanyo_x; j++){
                if(camino_expandido[i][j]>-1 && camino_expandido[i][j]<10)
                    System.out.print(" ");
                System.out.print(camino_expandido[i][j]+" ");
            }
            System.out.println();   
        }
    }
    
    public void reiniciarAEstrella(Mundo m){
        //Copia el mundo que le llega por parámetro
        mundo = new Mundo(m);
        camino = new char[m.tamanyo_y][m.tamanyo_x];
        camino_expandido = new int[m.tamanyo_y][m.tamanyo_x];
        expandidos = 0;
        
        //Inicializa las variables camino y camino_expandidos donde el A* debe incluir el resultado
            for(int i=0;i<m.tamanyo_x;i++)
                for(int j=0;j<m.tamanyo_y;j++){
                    camino[j][i] = '.';
                    camino_expandido[j][i] = -1;
                }
    }
    
    public float getCosteTotal(){
        return coste_total;
    }
}



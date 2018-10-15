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
    private Coordenada actual;
    private int g;
    private float h;
    private float f;
    private Nodo padre;
    // CONSTRUCTOR DE NODO PARA EL NODO INICIAL
    public Nodo(Coordenada actual, float heuristica){
        this.actual = actual;
        g = 0;
        h = heuristica;
        f = g + h;
        padre = null;
    }
    public Nodo(Coordenada actual, int g, float heuristica, Nodo padre){
        this.actual = actual;
        this.g = g;
        h = heuristica;
        this.padre = padre;
    }
    public Coordenada getCoordenadaActual(){ return actual; }
    public void setCoordenadaActual(Coordenada c){ actual = c; }
    public int getG(){ return g; }
    public void setG(int g){ this.g = g; }
    public float getH(){ return h; }
    public void setH(float h){ this.h = h; }
    public Nodo getCoordenadaPadre(){ return padre; }
    public void setPadre(Nodo p){ padre = p; }
    public float getF(){ return f; }
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
    
    public float heuristica0(){
        return 0;
    }
    public Nodo obtenerNodoMenorF(ArrayList<Nodo> ns){
        Nodo n = null;
        if(ns.size() == 1){
            n = ns.get(0);
        }
        else{
            int pos = 0;
            for(int i = 0; i < ns.size(); i++){
		if(ns.get(pos).getF() >= ns.get(i).getF()){
			pos = i;
		}
            }
            n = ns.get(pos);
        }
        return n;
    }
    
    public boolean esMeta(Nodo n, Coordenada meta){
        return n.getCoordenadaActual().getX() == meta.getX() 
                && n.getCoordenadaActual().getY() == meta.getY();
    }
    
    public boolean esFilaPar(Nodo n){
        boolean par = false;
        if(n.getCoordenadaActual().getY() % 2 == 0){
            par = true;
        }
        return par;
    }
    
    boolean esCoordenadaInalcanzable(Coordenada c, Coordenada ci){
        boolean inalcanzable = false;
        if(c.getX() == ci.getX() && c.getY() == ci.getY()){
            inalcanzable = true;
        }
        return inalcanzable;
    }
    public int getValorCeldaHijo(Coordenada c){
        char elemento = mundo.getCelda(c.getX(), c.getY());
        int valor = -1;
        if(elemento == 'c'){
            valor = 1;
        }
        else{
            if(elemento == 'h'){
                valor = 2; 
            }
            else{
                if(elemento == 'a'){
                    valor = 3;
                }
                else{
                    if(elemento == 'p' || elemento == 'b'){
                        valor = 0;
                    }
                }
            }
        }
        return valor;
    }
    // EN ESTE METODO SE SACAN LOS POSIBLES HIJOS DE UN NODO 
    // Y SE LE ACTUALIZAN EL VALOR DE G F Y EL PADRE
    public ArrayList<Nodo> getHijos(Nodo n){
        ArrayList<Nodo> hijos = new ArrayList<Nodo>();
        Coordenada actual = n.getCoordenadaActual();
        boolean par = esFilaPar(n);
        boolean inalcazable1, inalcazable2;
        int valorCeldaHijo;
        Coordenada [] cs = {new Coordenada(-1, -1), new Coordenada(-1, 0), 
            new Coordenada(-1, 1), new Coordenada(0, 1), new Coordenada(1, 1),
            new Coordenada(1, 0), new Coordenada(1, -1), new Coordenada(0, -1)};
        if(par){
            Coordenada inalcanzable_Par_1 = new Coordenada(actual.getX() - 1, actual.getY() - 1);
            Coordenada inalcanzable_Par_2 = new Coordenada(actual.getX() - 1, actual.getY() + 1);
            for(int i = 0; i < cs.length; i++){
                Coordenada coordenadaHijo = new Coordenada(cs[i].getX() + actual.getX(), cs[i].getY() + actual.getY());
                inalcazable1 = esCoordenadaInalcanzable(coordenadaHijo, inalcanzable_Par_1);
                inalcazable2 = esCoordenadaInalcanzable(coordenadaHijo, inalcanzable_Par_2);
                if(!inalcazable1 && !inalcazable1){
                    valorCeldaHijo = getValorCeldaHijo(coordenadaHijo);
                    if(valorCeldaHijo != 0){
                        Nodo hijo = new Nodo(coordenadaHijo, n.getG() + valorCeldaHijo, heuristica0(), n);
                        hijos.add(hijo);
                    }
                }
            }
        }
        else{
            Coordenada inalcanzable_Impar_1 = new Coordenada(actual.getX() + 1, actual.getY() - 1);
            Coordenada inalcanzable_Impar_2 = new Coordenada(actual.getX() + 1, actual.getY() + 1);
            for(int i = 0; i < cs.length; i++){
                Coordenada coordenadaHijo = new Coordenada(cs[i].getX() + actual.getX(), cs[i].getY() + actual.getY());
                inalcazable1 = esCoordenadaInalcanzable(coordenadaHijo, inalcanzable_Impar_1);
                inalcazable2 = esCoordenadaInalcanzable(coordenadaHijo, inalcanzable_Impar_2);
                if(!inalcazable1 && !inalcazable1){
                    valorCeldaHijo = getValorCeldaHijo(coordenadaHijo);
                    if(valorCeldaHijo != 0){
                        Nodo hijo = new Nodo(coordenadaHijo, n.getG() + valorCeldaHijo, heuristica0(), n);
                        hijos.add(hijo);
                    }
                }
            }
        }
        return hijos;
    }
    //Calcula el A*
    public int CalcularAEstrella(){

        boolean encontrado = false;
        int result = -1;
        //AQUÍ ES DONDE SE DEBE IMPLEMENTAR A*
        boolean esLaMeta;
        Coordenada inicio = mundo.getCaballero();
        Coordenada destino = mundo.getDragon();
        ArrayList<Nodo> listaInterior = new ArrayList<Nodo>();
        ArrayList<Nodo> listaFrontera = new ArrayList<Nodo>();
        float heuristica = heuristica0();
        Nodo inicial = new Nodo(inicio, heuristica);
        listaFrontera.add(inicial);
        while(listaFrontera.size() != 0){
            Nodo n = obtenerNodoMenorF(listaFrontera); // Obtenemos el nodo con menor F
            esLaMeta = esMeta(n, destino);
            if(esLaMeta){
                // reconstruimos el camino
            }
            else{
                listaFrontera.remove(n);
                listaInterior.add(n);
                for(Nodo m : getHijos(n)){
                    if(!listaFrontera.contains(m)){
                        listaFrontera.add(m);
                    }
                    else{
                       // if(){ 
                            // CONSULTAR ESTA PARTE
                       // }
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



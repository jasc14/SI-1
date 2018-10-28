/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AEstrella;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        f = g + h;
        this.padre = padre;
    }
    public Nodo (Nodo n){
        actual = n.actual;
        g = n.g;
        h = n.h;
        f = n.f;
        padre = n.padre;
    }
    public Coordenada getCoordenadaActual(){ return actual; }
    public void setCoordenadaActual(Coordenada c){ actual = c; }
    public int getG(){ return g; }
    public void setG(int g){ this.g = g; }
    public float getH(){ return h; }
    public void setH(float h){ this.h = h; }
    public Nodo getPadre(){ return padre; }
    public void setPadre(Nodo p){ padre = p; }
    public float getF(){ return f; }
    public void setF(float f){ this.f = f; }
}
class Cubo{
    private int x;
    private int y;
    private int z;
    public Cubo(int vx, int vy, int vz){
        x = vx;
        y = vy;
        z = vz;
    }
    public int getX(){ return x; }
    public void setX(int vx){ x = vx; }
    public int getY(){ return y; }
    public void setY(int vy){ y = vy; }
    public int getZ(){ return z; }
    public void setZ(int vz){ z = vz; }
    
}
public class AEstrella  {
 
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
    public boolean existeEnInterior(Coordenada c, ArrayList<Nodo> li){
        boolean existe = false;
        for(int i = 0; i < li.size() && existe == false; i++){
            if(coordenadasIguales(c, li.get(i).getCoordenadaActual())){
                existe = true;
            }
        }
        return existe;
    }
    public boolean existeEnFrontera(Nodo n, ArrayList<Nodo> lf){
        boolean existe = false;
        for(int i = 0; i < lf.size() && existe == false; i++){
            if(coordenadasIguales(n.getCoordenadaActual(), lf.get(i).getCoordenadaActual())){
                existe = true;
            }
        }
        return existe;
    }
    public Nodo existeEnFrontera(ArrayList<Nodo> lf, Coordenada nodoBuscado){
        Nodo n = null;
        int pos = -1;
        for(int i = 0; i < lf.size() && pos == -1; i++){
            if(coordenadasIguales(nodoBuscado, lf.get(i).getCoordenadaActual())){
                pos = i;
            }
        }
        if(pos != -1){
            n = lf.get(pos);
        }
        return n;
    }
    public boolean coordenadasIguales(Coordenada c1, Coordenada c2){
        boolean iguales = false;
        if(c1.getX() == c2.getX() && c1.getY() == c2.getY()){
            iguales = true;
        }
        return iguales;
    }
    
    public Nodo obtenerNodoMenorF(ArrayList<Nodo> ns){
        Nodo n = null;
        if(ns.size() == 1){
            n = ns.get(0);
        }
        else{
            int pos = 0;
            for(int i = 0; i < ns.size(); i++){
		if(ns.get(pos).getF() > ns.get(i).getF()){
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
    
    public boolean esCoordenadaInalcanzable(Coordenada c, Coordenada ci){
        boolean inalcanzable = false;
        if(c.getX() == ci.getX() && c.getY() == ci.getY()){
            inalcanzable = true;
        }
        return inalcanzable;
    }
    public int getValorCeldaHijo(Coordenada c){
        char elemento = mundo.getCelda(c.getX(), c.getY());
        int valor = -1;
        if(elemento == 'c' || elemento == 'd'){ // camino
            valor = 1;
        }
        else{
            if(elemento == 'h'){ // hierva
                valor = 2; 
            }
            else{
                if(elemento == 'a'){ // agua
                    valor = 3;
                }
                else{
                    if(elemento == 'p' || elemento == 'b'){ // piedra o pared
                        valor = 0;
                    }
                }
            }
        }
        return valor;
    }
    
    public ArrayList<Nodo> getHijos(Nodo n, ArrayList<Nodo> li, Coordenada finalcoord){ 
        ArrayList<Nodo> hijos = new ArrayList<Nodo>();
        Coordenada actual = n.getCoordenadaActual();
        Nodo hijo;
        Cubo c1 = null;
        Cubo c2 = null;
        int valorCeldaHijo;
        boolean par = esFilaPar(n);
        boolean inalcanzable1, inalcanzable2;
        Coordenada [] cs = {new Coordenada(-1, -1), new Coordenada(-1, 0), 
            new Coordenada(-1, 1), new Coordenada(0, 1), new Coordenada(1, 1),
            new Coordenada(1, 0), new Coordenada(1, -1), new Coordenada(0, -1)};
        if(par){
            Coordenada inalcanzable_Par_1 = new Coordenada(actual.getX() - 1, actual.getY() - 1);
            Coordenada inalcanzable_Par_2 = new Coordenada(actual.getX() - 1, actual.getY() + 1);

            
            for(int i = 0; i < cs.length; i++){
                Coordenada coordenadaHijo = new Coordenada(cs[i].getX() + actual.getX(), cs[i].getY() + actual.getY());
                inalcanzable1 = esCoordenadaInalcanzable(coordenadaHijo, inalcanzable_Par_1);
                inalcanzable2 = esCoordenadaInalcanzable(coordenadaHijo, inalcanzable_Par_2);
                if(!inalcanzable1 && !inalcanzable2){
                    valorCeldaHijo = getValorCeldaHijo(coordenadaHijo);
                    if(valorCeldaHijo != 0){    
                        if(!existeEnInterior(coordenadaHijo, li)){
                            //float heuristica = heuristicaManhattan(coordenadaHijo, finalcoord);
                            //float heuristica = heuristica0();
                            //float heuristica = heuristicaEuclidea(coordenadaHijo, finalcoord);
                            c1 = obtenerCubo(coordenadaHijo);
                            c2 = obtenerCubo(finalcoord);
                            float heuristica = heuristicaHexagonales(c1, c2);                          
                            hijo = new Nodo(coordenadaHijo, n.getG() + valorCeldaHijo, heuristica, n);
                            hijos.add(hijo);
                        }
                    }    
                }
            }
        }
        else{
            Coordenada inalcanzable_Impar_1 = new Coordenada(actual.getX() + 1, actual.getY() - 1);
            Coordenada inalcanzable_Impar_2 = new Coordenada(actual.getX() + 1, actual.getY() + 1);

            for(int i = 0; i < cs.length; i++){
                Coordenada coordenadaHijo = new Coordenada(cs[i].getX() + actual.getX(), cs[i].getY() + actual.getY());
                inalcanzable1 = esCoordenadaInalcanzable(coordenadaHijo, inalcanzable_Impar_1);
                inalcanzable2 = esCoordenadaInalcanzable(coordenadaHijo, inalcanzable_Impar_2);
                if(!inalcanzable1 && !inalcanzable2){
                    valorCeldaHijo = getValorCeldaHijo(coordenadaHijo);
                    if(valorCeldaHijo != 0){    
                        if(!existeEnInterior(coordenadaHijo, li)){
                            //float heuristica = heuristicaManhattan(coordenadaHijo, finalcoord);
                            //float heuristica = heuristica0();
                            //float heuristica = heuristicaEuclidea(coordenadaHijo, finalcoord);
                            c1 = obtenerCubo(coordenadaHijo);
                            c2 = obtenerCubo(finalcoord);
                            float heuristica = heuristicaHexagonales(c1, c2);
                            hijo = new Nodo(coordenadaHijo, n.getG() + valorCeldaHijo, heuristica, n);
                            hijos.add(hijo);
                        }
                    }    
                }
            }
        }
        return hijos;
    }
    public void reconstruirCamino(ArrayList<Nodo> li, char c[][]){
        Nodo actual = null;
        Nodo padre = null;
        actual = li.get(li.size() - 1);
        padre = actual.getPadre();
        while(padre != null){
            c[actual.getCoordenadaActual().getY()][actual.getCoordenadaActual().getX()] = 'X';
            actual = padre;
            padre = actual.getPadre();
        }
    }
    public float heuristica0(){
        return 0;
    }
    public float heuristicaManhattan(Coordenada inicial, Coordenada destino){
       return Math.abs(inicial.getX() - destino.getX()) + Math.abs(inicial.getY() - destino.getY());
    }
    public float heuristicaEuclidea(Coordenada inicial, Coordenada destino){
        float x = (float) Math.pow(destino.getX() - inicial.getX(), 2);
        float y = (float) Math.pow(destino.getY() - inicial.getY(), 2);
        return (float) Math.sqrt(x + y);
    }
    public float heuristicaHexagonales(Cubo a, Cubo b){
        return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()) + Math.abs(a.getZ() - b.getZ()))/2;
    }
    public Cubo obtenerCubo(Coordenada c){
        int x, y, z;
        Cubo cubo = null;
        x = c.getX() - (c.getY() + c.getY()%2)/2;
        z = c.getY();
        y = -x - z;
        cubo = new Cubo(x,y,z);
        return cubo;
    }
    
    //Calcula el A*
    public int CalcularAEstrella(){
        boolean encontrado = false;
        int result = -1;
        expandidos = 0;
        Coordenada inicio = mundo.getCaballero();
        Coordenada destino = mundo.getDragon();
        ArrayList<Nodo> hijos = new ArrayList<Nodo>();
        ArrayList<Nodo> listaInterior = new ArrayList<Nodo>();
        ArrayList<Nodo> listaFrontera = new ArrayList<Nodo>();
        //float heuristica = heuristica0();
        //float heuristica = heuristicaManhattan(inicio, destino);
        //float heuristica = heuristicaEuclidea(inicio, destino);
        Cubo c1 = obtenerCubo(inicio);
        Cubo c2 = obtenerCubo(destino);
        float heuristica = heuristicaHexagonales(c1, c2);
        Nodo inicial = new Nodo(inicio, heuristica);
        listaFrontera.add(inicial);
        camino_expandido[inicial.getCoordenadaActual().getY()][inicial.getCoordenadaActual().getX()] = expandidos;
        while(listaFrontera.size() != 0 && result == -1){
            
            Nodo n = obtenerNodoMenorF(listaFrontera);
            
            boolean esMeta = esMeta(n, destino);
            if(esMeta){
                listaInterior.add(n);
                coste_total = n.getF(); 
                reconstruirCamino(listaInterior, camino);
                encontrado = true;
                result = 0;
            }
            else{
                listaFrontera.remove(n);
                listaInterior.add(n);
                hijos = getHijos(n, listaInterior, destino);
                for(Nodo m : hijos){
                    if(existeEnFrontera(m, listaFrontera) == false){
                        listaFrontera.add(m);
                        expandidos++;
                        camino_expandido[m.getCoordenadaActual().getY()][m.getCoordenadaActual().getX()] = expandidos;                       
                    }
                    else{
                        Nodo enFrontera = existeEnFrontera(listaFrontera, m.getCoordenadaActual());
                        if(m.getG() < enFrontera.getG()){
                            listaFrontera.remove(enFrontera);
                            listaFrontera.add(m);
                        }
                    }
                }
                hijos.clear();
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




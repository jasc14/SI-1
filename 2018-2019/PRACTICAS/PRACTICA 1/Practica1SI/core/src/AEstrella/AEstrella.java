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
    boolean visitado;
    // CONSTRUCTOR DE NODO PARA EL NODO INICIAL
    public Nodo(Coordenada actual, float heuristica){
        this.actual = actual;
        g = 0;
        h = heuristica;
        f = g + h;
        visitado = false;
        padre = null;
    }
    public Nodo(Coordenada actual, int g, float heuristica, Nodo padre){
        this.actual = actual;
        this.g = g;
        h = heuristica;
        f = g + h;
        visitado = false;
        this.padre = padre;
    }
    public Nodo (Nodo n){
        actual = n.actual;
        g = n.g;
        h = n.h;
        f = n.f;
        padre = n.padre;
        visitado = false;
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
    public int getValorCeldaHijo(Coordenada c){ // tengo que ver si el valor de la celda destino se tien en cuenta
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
    public ArrayList<Nodo> getHijos(Nodo n, ArrayList<Nodo> li){ // ESTE METODO DEVUELVE LOS NODOS HIJOS QUE NO ESTAN EN LISTAINTERIOR
        ArrayList<Nodo> hijos = new ArrayList<Nodo>();
        Coordenada actual = n.getCoordenadaActual();
        Nodo hijo;
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
                            hijo = new Nodo(coordenadaHijo, n.getG() + valorCeldaHijo, heuristica0(), n);
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
                            hijo = new Nodo(coordenadaHijo, n.getG() + valorCeldaHijo, heuristica0(), n);
                            hijos.add(hijo);
                        }
                    }    
                }
            }
        }
        return hijos;
    }
    public void reconstruirCamino(ArrayList<Nodo> li, char c[][],int ce [][],int expandidos){ // falta que me haga lo del camino expandido y todo esa mierda
        // PARA RECONSTRUIR EL CAMINO TENGO QUE
        // DESDE EL NODO FINAL VOLVER AL PRIMERO (EL QUE TENGA COMO PADRE NULL)
        //int expandidosaux = expandidos;
        Nodo actual = null;
        Nodo padre = null;
        Nodo actualAux = null;
        actual = li.get(li.size() - 1);
        padre = actual.getPadre(); // con este tengo que buscar la coordenada actual del nodo padre
        while(padre != null){
            c[actual.getCoordenadaActual().getY()][actual.getCoordenadaActual().getX()] = 'X';
            //ce[actual.getCoordenadaActual().getY()][actual.getCoordenadaActual().getX()] = expandidosaux;
            actual = padre;
            padre = actual.getPadre();
        }
    }
    //Calcula el A*
    
    public int CalcularAEstrella(){

        boolean encontrado = false;
        int result = -1;
        Coordenada inicio = mundo.getCaballero();
        Coordenada destino = mundo.getDragon();
        ArrayList<Nodo> hijos = new ArrayList<Nodo>();
        ArrayList<Nodo> listaInterior = new ArrayList<Nodo>();
        ArrayList<Nodo> listaFrontera = new ArrayList<Nodo>();
        float heuristica = heuristica0();
        Nodo inicial = new Nodo(inicio, heuristica);
        listaFrontera.add(inicial);
        while(listaFrontera.size() != 0 && result == -1){
            Nodo n = obtenerNodoMenorF(listaFrontera);
            boolean esMeta = esMeta(n, destino);
            if(esMeta){
                listaInterior.add(n);
                coste_total = n.getF();
                reconstruirCamino(listaInterior, camino, camino_expandido, expandidos);
                encontrado = true;
                result = 0;
            }
            else{
                listaFrontera.remove(n);
                listaInterior.add(n);
                // Obtenemos los hijos m de n que no esten en lista interior
                hijos = getHijos(n, listaInterior);
                for(Nodo m : hijos){
                    if(existeEnFrontera(m, listaFrontera) == false){ // si el nodo no esta en listaFrontera lo añadimos
                        listaFrontera.add(m);
                    }
                    else{
                        Nodo enFrontera = existeEnFrontera(listaFrontera, m.getCoordenadaActual());
                        if(m.getG() < enFrontera.getG()){
                            // tengo que asignar al hijo todos los valores de el enFrontera
                            m.setCoordenadaActual(enFrontera.getCoordenadaActual());
                            m.setG(enFrontera.getG());
                            m.setH(enFrontera.getH());
                            m.setF(enFrontera.getF());
                            listaFrontera.remove(enFrontera);
                            listaFrontera.add(m);
                        }
                    }
                }
                hijos.clear();
                //expandidos++;
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



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import realworld.Device;

/**
 *
 * @author danie
 */
public class Graph {
    public final int MAX_VERTS = 22; //Máxima cantidad de vértices en el grafo
    public Vertex vertexList[]; // Lista de vértices
    public SortedList adjacency[];      // Lista enlazada de adyacencia
    private int nVerts;          // Actual cantidad de vértices en el grafo
    private PriorityQ theQueue;

    public Graph(){
      vertexList = new Vertex[MAX_VERTS];
      adjacency = new SortedList[MAX_VERTS];
      nVerts = 0;
      for(int x=0; x<MAX_VERTS; x++){      // se setea la adyacencia
        adjacency[x] = new SortedList();
      }
      theQueue = new PriorityQ(MAX_VERTS);
    }

    public void addVertex(char lab, Device device){
      vertexList[nVerts++] = new Vertex(lab, device);
    }

    public void addEdge(int start, int end, int distance){
      adjacency[start].insert(vertexList[end], end, distance);
      adjacency[end].insert(vertexList[start], start, distance);
    }

    public void displayVertex(int v){
     System.out.print(vertexList[v].label);
    }
   
   /*
   * Cálculo de la ruta de un envío
   */
   public int[] searchRoute(Device from, Device to){                   // breadth-first search
        //Primero anotamos las distancias desde el nodo de origen a todos los demás en una matriz
        int[] distances = new int[this.nVerts];
        int[] previos = new int[this.nVerts];
        //Relleno todos los huecos con un 999999 y los previos con -1
        for(int i = 0; i < this.nVerts; i++){
            distances[i] = 999999;
            previos[i] = -1;
        }
        // begin at vertex 0
        vertexList[from.indexOnArray].wasVisited = true; // mark it
        distances[from.indexOnArray] = 0;
        //displayVertex(from);                // display it
        //theQueue.insert(0);              // insert at tail
        theQueue.insert(new Link(vertexList[from.indexOnArray],from.indexOnArray,0));
        Link v2;

        while( !theQueue.isEmpty() ){// until queue empty,
            Link v1 = theQueue.remove();// remove vertex at head
            // until it has no unvisited neighbors

            SortedList adjacentNodes = adjacency[v1.indexOnArray];
            Link link = adjacentNodes.getFirst();
            do{
                //Si el nodo no ha sido visitado y si figura como conectado para el nodo que está consultando la ruta
                if(!link.vertex.wasVisited && from.checkStatus(link.vertex.device)){
                    this.relaxation(v1, link, link.dData, distances, theQueue, previos);
                }
                link = link.next;

                if(link == null){
                    break;
                }
            }while(true);
        }// end while(queue not empty)
        
        //this.imprimirRecorrido(to, previos); //Sólo debug
        // La cola está vacía, por lo que se setean todos los vértices como no visitados
        for(int j=0; j<nVerts; j++){             // reset flags
           vertexList[j].wasVisited = false;
        }
        
        //Se retorna el array con los índices de los nodos que se deben visitar para entregar el paquete
        return getRoute(to.indexOnArray, previos);
      }
   
   /*
   * Metodo utilizado por searchRoute para el cálculo de la ruta
   */
   private void relaxation(Link u, Link v, int w, int[] distances, PriorityQ theQueue, int[] previos){
       if((distances[u.indexOnArray] + w) < distances[v.indexOnArray]){
           distances[v.indexOnArray] = (distances[u.indexOnArray] + w);
           previos[v.indexOnArray] = u.indexOnArray;
           theQueue.insert(v);
       }
   }
   
   /*
   * Devuelve un array de enteros con los índices de los nodos por los que se
   * debe pasar para llegar de un nodo a otro
   */
   private int[] getRoute(int to, int[] previos){
       int[] route = new int[this.nVerts];
       for(int j = 0; j<this.nVerts; j++){
           route[j] = -1;
       }
       int i = 0;
       while(true){
           if(to == -1){
               break;
           }
           route[i] = to;
           to = previos[to];
           i++;
       }
       //Hay que invertir el array porque queda al revés
       int[] realRoute = new int[i];
       for(int k = i; k>0; k--){
           realRoute[i-k] = route[k-1];
       }
       return realRoute;
   }
   
   /*Usada sólo en debug*/
   void imprimirRecorrido( int destino, int[] previos){
        if( previos[ destino ] != -1 )    //si aun poseo un vertice previo
            this.imprimirRecorrido( previos[ destino ], previos );  //recursivamente sigo explorando
        System.out.println(destino );        //terminada la recursion imprimo los vertices recorridos
    }
   
    /*Usada sólo en debug*/
    public void print(){
        //Recorro todo el árbol mostrando las conexiones de cada vértice
        int index = 0;
        for (SortedList ad : adjacency) 
        { 
            this.displayVertex(index);
            ad.displayList();
            index++;
        }
    }
}

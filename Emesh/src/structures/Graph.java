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
   private final int MAX_VERTS = 21;
   public Vertex vertexList[]; // list of vertices
   private SortedList adjacency[];      // adjacency matrix
   private int nVerts;          // current number of vertices
   private StackX theStack;
   private PriorityQ theQueue;
// ------------------------------------------------------------
   public Graph()               // constructor
      {
      vertexList = new Vertex[MAX_VERTS];
                                          // adjacency matrix
      adjacency = new SortedList[MAX_VERTS];
      nVerts = 0;
      for(int x=0; x<MAX_VERTS; x++)      // set adjacency
        adjacency[x] = new SortedList();
      theStack = new StackX();
      theQueue = new PriorityQ(21);
      }  // end constructor
// ------------------------------------------------------------
   public void addVertex(char lab, Device device)
      {
      vertexList[nVerts++] = new Vertex(lab, device);
      }
// ------------------------------------------------------------
   public void addEdge(int start, int end, int distance)
      {
      adjacency[start].insert(vertexList[end], end, distance);
      adjacency[end].insert(vertexList[start], start, distance);
      }
// ------------------------------------------------------------
   public void displayVertex(int v)
      {
      System.out.print(vertexList[v].label);
      }
// ------------------------------------------------------------
   public void dfs()  // depth-first search
      {                                 // begin at vertex 0
      vertexList[0].wasVisited = true;  // mark it
      displayVertex(0);                 // display it
      theStack.push(0);                 // push it

      while( !theStack.isEmpty() )      // until stack empty,
         {
         // get an unvisited vertex adjacent to stack top
         int v = getAdjUnvisitedVertex( theStack.peek() );
         if(v == -1)                    // if no such vertex,
            theStack.pop();
         else                           // if it exists,
            {
            vertexList[v].wasVisited = true;  // mark it
            displayVertex(v);                 // display it
            theStack.push(v);                 // push it
            }
         }  // end while

      // stack is empty, so we're done
      for(int j=0; j<nVerts; j++)          // reset flags
         vertexList[j].wasVisited = false;
      }  // end dfs
// ------------------------------------------------------------
   // returns an unvisited vertex adj to v
   public int getAdjUnvisitedVertex(int v)
      {
         Link link = adjacency[v].find();
         if(link != null)
            return link.indexOnArray;
      return -1;
      }  // end getAdjUnvisitedVertex()
   public Link getAdjUnvisitedVertexLink(int v)
      {
         Link link = adjacency[v].find();
         if(link != null)
            return link;
      return null;
      }  // end getAdjUnvisitedVertex()
// ------------------------------------------------------------
   public void mst()  // minimum spanning tree (depth first)
      {                                  // start at 0
      vertexList[0].wasVisited = true;   // mark it
      theStack.push(0);                  // push it

      while( !theStack.isEmpty() )       // until stack empty
         {                               // get stack top
         int currentVertex = theStack.peek();
         // get next unvisited neighbor
         int v = getAdjUnvisitedVertex(currentVertex);
         if(v == -1)                     // if no more neighbors
            theStack.pop();              //    pop it away
         else                            // got a neighbor
            {
            vertexList[v].wasVisited = true;  // mark it
            theStack.push(v);                 // push it
                                         // display edge
            displayVertex(currentVertex);     // from currentV
            displayVertex(v);                 // to v
            System.out.print(" ");
            }
         }  // end while(stack not empty)

         // stack is empty, so we're done
         for(int j=0; j<nVerts; j++)          // reset flags
            vertexList[j].wasVisited = false;
      }  // end mst()
   
   
   
   public void bfs()                   // breadth-first search
      {                                // begin at vertex 0
      vertexList[0].wasVisited = true; // mark it
      displayVertex(0);                // display it
      //theQueue.insert(0);              // insert at tail
      theQueue.insert(new Link(vertexList[0],0,0));
      Link v2;

      while( !theQueue.isEmpty() )     // until queue empty,
         {
         Link v1 = theQueue.remove();   // remove vertex at head
         // until it has no unvisited neighbors
         while( (v2=getAdjUnvisitedVertexLink(v1.vertex.device.indexOnArray)) != null )
            { 
            v2.vertex.wasVisited = true;
            displayVertex(v2.indexOnArray);                 // display it
            theQueue.insert(v2);               // insert it
            }   // end while
         }  // end while(queue not empty)

      // queue is empty, so we're done
      for(int j=0; j<nVerts; j++)             // reset flags
         vertexList[j].wasVisited = false;
      }  // end bfs()
// -------------------------------------------------------------
   
   public int[] searchRoute(int from, int to){                   // breadth-first search
        //Primero anotamos las distancias desde el nodo de origen a todos los demás en una matriz
        int[] distances = new int[this.nVerts];
        int[] previos = new int[this.nVerts];
        //Relleno todos los huecos con un 999999 y los previos con -1
        for(int i = 0; i < this.nVerts; i++){
            distances[i] = 999999;
            previos[i] = -1;
        }
        // begin at vertex 0
        vertexList[from].wasVisited = true; // mark it
        distances[from] = 0;
        //displayVertex(from);                // display it
        //theQueue.insert(0);              // insert at tail
        theQueue.insert(new Link(vertexList[from],from,0));
        Link v2;

        while( !theQueue.isEmpty() ){// until queue empty,
            Link v1 = theQueue.remove();// remove vertex at head
            // until it has no unvisited neighbors

            SortedList adjacentNodes = adjacency[v1.indexOnArray];
            Link link = adjacentNodes.getFirst();
            do{
                if(!link.vertex.wasVisited){
                    this.relaxation(v1, link, link.dData, distances, theQueue, previos);
                }
                link = link.next;
                if(link == null){
                    break;
                }
            }while(true);
        }// end while(queue not empty)
        
        //this.imprimirRecorrido(to, previos);
        // queue is empty, so we're done
        for(int j=0; j<nVerts; j++)             // reset flags
           vertexList[j].wasVisited = false;
        
        return getRoute(to, previos);
      }  // end search()
// -------------------------------------------------------------
   
   private void relaxation(Link u, Link v, int w, int[] distances, PriorityQ theQueue, int[] previos){
       if((distances[u.indexOnArray] + w) < distances[v.indexOnArray]){
           distances[v.indexOnArray] = (distances[u.indexOnArray] + w);
           previos[v.indexOnArray] = u.indexOnArray;
           theQueue.insert(v);
       }
   }
   
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
   
   void imprimirRecorrido( int destino, int[] previos){
        if( previos[ destino ] != -1 )    //si aun poseo un vertice previo
            this.imprimirRecorrido( previos[ destino ], previos );  //recursivamente sigo explorando
        System.out.println(destino );        //terminada la recursion imprimo los vertices recorridos
    }
   
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

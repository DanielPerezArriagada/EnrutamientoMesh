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
   private Vertex vertexList[]; // list of vertices
   private SortedList adjacency[];      // adjacency matrix
   private int nVerts;          // current number of vertices
   private StackX theStack;
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

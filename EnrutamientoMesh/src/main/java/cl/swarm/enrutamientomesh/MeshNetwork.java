/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.swarm.enrutamientomesh;

import java.util.Random;
import cl.swarm.structures.Graph;

/**
 *
 * @author danie
 */
public class MeshNetwork {
    public static void main(String[] args) {
      /* Los grafos generados aleatoriamente pueden no contener sólo enlaces simples.
       * Se utilizó una lista enlazada ordenada por pesos para generar
       * el subgrafo de mínima expansión en lugar de la matriz de adjacencia. De esta
       * forma, el primer vértice visitado siempre será el de la arista con menor peso.
       */
      
      Graph theGraph = new Graph();
      theGraph.addVertex('A');    // 0  (start for dfs)
      theGraph.addVertex('B');    // 1
      theGraph.addVertex('C');    // 2
      theGraph.addVertex('D');    // 3
      theGraph.addVertex('E');    // 4
      theGraph.addVertex('F');    // 5
      theGraph.addVertex('G');    // 6
      theGraph.addVertex('H');    // 7
      theGraph.addVertex('I');    // 8
      theGraph.addVertex('J');    // 9
      theGraph.addVertex('K');    // 10
      theGraph.addVertex('L');    // 11
      theGraph.addVertex('M');    // 12

      float min = 0.000001f;
      int max = 1;
      Random r = new Random();
      //Creando aristas con sus respectivos pesos
      int cantidadDeVertices = 13;
      for(int i = 0; i < cantidadDeVertices; i++){
          //Aplicamos un par de aristas extra por cada vértice para tener redundancia
          for(int k = 0; k < 3; k++){
            int j = i;
            //Nos aseguramos de que la arista no referencie al mismo vértice
            while(j == i){
                j = r.nextInt(cantidadDeVertices);
            }
            float peso = min + r.nextFloat() * (max - min);
            theGraph.addEdge(i, j, peso);
          }
      }
      
      System.out.println("Grafo original");
      theGraph.print();
      
      System.out.println();
      System.out.println("SubGrafo de expansión mínima");
      theGraph.mst();
      System.out.println();
    }
}

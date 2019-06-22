/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emesh;

import java.util.Random;
import realworld.Device;
import structures.Graph;
/**
 *
 * @author danie
 */
public class Emesh {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Los grafos generados aleatoriamente pueden no contener sólo enlaces simples.
       * Se utilizó una lista enlazada ordenada por pesos para generar
       * el subgrafo de mínima expansión en lugar de la matriz de adjacencia. De esta
       * forma, el primer vértice visitado siempre será el de la arista con menor peso.
       */
      //Creo los nodos y sus grafos vacíos
      Device devices[] = new Device[21];
      int index = 0;
      for (char c = 'A'; c <= 'U'; c++) {
          devices[index++] = new Device(c, new Graph());
      }

      //Agrego los vértices a cada grafo
      for(int i = 0; i<21; i++){
        for(int j = 0; j<21; j++){
          devices[i].network.addVertex(devices[j].getIdentifier(), devices[j]);
        }
      }


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
            //theGraph.addEdge(i, j, peso);
          }
      }
      do{
          
      }while(true);
    }
    
}

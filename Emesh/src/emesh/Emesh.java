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
          devices[index] = new Device(c, new Graph(),index);
          index++;
      }

      //Agrego los vértices a cada grafo
      for(int i = 0; i<21; i++){
        for(int j = 0; j<21; j++){
          devices[i].network.addVertex(devices[j].getIdentifier(), devices[j]);
        }
      }
      
      //Se agregan las adyacencias y los pesos de las aristas
      for(int i = 0; i<21; i++){
          devices[i].network.addEdge(0,2,150);
          devices[i].network.addEdge(2,1,250);
          devices[i].network.addEdge(2,3,140);
          devices[i].network.addEdge(3,5,110);
          devices[i].network.addEdge(4,5,120);
          devices[i].network.addEdge(5,7,210);
          devices[i].network.addEdge(7,6,200);
          devices[i].network.addEdge(6,9,290);
          devices[i].network.addEdge(6,8,240);
          devices[i].network.addEdge(8,17,190);
          devices[i].network.addEdge(17,20,290);
          devices[i].network.addEdge(20,12,100);
          devices[i].network.addEdge(12,14,120);
          devices[i].network.addEdge(12,13,90);
          devices[i].network.addEdge(12,10,100);
          devices[i].network.addEdge(12,11,80);
          devices[i].network.addEdge(11,10,160);
          devices[i].network.addEdge(14,15,240);
          devices[i].network.addEdge(15,16,290);
          devices[i].network.addEdge(17,18,130);
          devices[i].network.addEdge(18,19,120);
          devices[i].network.addEdge(13,14,80);
      }
      devices[0].sendRoutedPackage(devices[20], "Hola");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emesh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static void main(String[] args) throws IOException {
      //Creo los nodos y sus grafos vacíos
      Device devices[] = new Device[22];
      int index = 0;
      for (char c = 'A'; c <= 'V'; c++) {
          devices[index] = new Device(c, new Graph(),index);
          index++;
      }

      //Agrego los vértices a cada grafo
      for(int i = 0; i<22; i++){
        for(int j = 0; j<22; j++){
          devices[i].network.addVertex(devices[j].getIdentifier(), devices[j]);
        }
      }
      
      //Se agregan las adyacencias y los pesos de las aristas
      for(int i = 0; i<22; i++){
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
          devices[i].network.addEdge(11,13,120);
          devices[i].network.addEdge(14,15,240);
          devices[i].network.addEdge(15,16,290);
          devices[i].network.addEdge(17,18,130);
          devices[i].network.addEdge(18,19,120);
          devices[i].network.addEdge(13,14,80);
          devices[i].network.addEdge(9,21,90);
          devices[i].network.addEdge(21,10,100);
      }
      
      //Encender los nodos
      for(int i = 0; i<22; i++){
          devices[i].turnOn();
      }
      //Insertar acá el menú
      devices[0].sendRoutedPackage(devices[15], "Hola");
      
      while(true)
         {
         System.out.println("Inserte la inicial de la acción a realizar: ");
         System.out.println("Apagar un nodo: a");
         System.out.println("Encender un nodo: e");
         System.out.println("Broadcast a un nodo: b");
         System.out.println("Paquete enrutado a un nodo: p");
         int choice = getChar();
         switch(choice)
            {
            case 'a':
               devices[21].turnOff();
               break;
            case 'e':
               //System.out.print("Enter value to insert: ");
               //value = getInt();
               //theTree.insert(value, value + 0.9);
               break;
            case 'b':
               //System.out.print("Enter value to find: ");
               //value = getInt();

               break;
            case 'p':
               //System.out.print("Enter value to delete: ");
               //value = getInt();
               //boolean didDelete = theTree.delete(value);
                devices[6].sendRoutedPackage(devices[13], "Hola");
               break;
            default:
               System.out.println("Acción inválida");
            }  // end switch
         }  // end while
    }
    
    public static String getString() throws IOException
      {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String s = br.readLine();
      return s;
      }
// -------------------------------------------------------------
   public static char getChar() throws IOException
      {
      String s = getString();
      return s.charAt(0);
      }
//-------------------------------------------------------------
   public static int getInt() throws IOException
      {
      String s = getString();
      return Integer.parseInt(s);
      }
    
}

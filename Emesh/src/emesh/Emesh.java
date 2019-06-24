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
      
      //Menú
      
      while(true)
         {
         System.out.println("///////////////////////////////////////////////////");
         System.out.println("// Inserte la inicial de la acción a realizar: ");
         System.out.println("// Apagar un nodo: a");
         System.out.println("// Encender un nodo: e");
         System.out.println("// Broadcast a un nodo: b");
         System.out.println("// Paquete enrutado a un nodo: p");
         System.out.println("///////////////////////////////////////////////////");
         int choice = getChar();
         int value;
         int value2;
         PackageSupervisor supervisor;
         switch(choice)
            {
            case 'a':
               System.out.println("Ingresa el index del nodo que deseas apagar");
               value = getInt();
               devices[value].turnOff();
               break;
            case 'e':
               System.out.println("Ingresa el index del nodo que deseas encender");
               value = getInt();
               devices[value].turnOn();
               break;
            case 'b':
               //System.out.print("Enter value to find: ");
               //value = getInt();
               System.out.print("Ingresa el index del emisor del paquete");
               value = getInt();
               System.out.print("Ingresa el index del receptor del paquete");
               value2 = getInt();
               supervisor = new PackageSupervisor();
               devices[value].sendBroadcastPackage(devices[value2], "BLABLABLA", supervisor);
               supervisor.getInformation();
               break;
            case 'p':
               System.out.print("Ingresa el index del emisor del paquete");
               value = getInt();
               System.out.print("Ingresa el index del receptor del paquete");
               value2 = getInt();
               supervisor = new PackageSupervisor();
               devices[value].sendRoutedPackage(devices[value2], "BLABLABLA", supervisor);
               supervisor.getInformation();
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
      String s;
      int i = 0;
      boolean valid = false;
      do{
        try{
            s = getString();
            i = Integer.parseInt(s);
            if(i<0 || i>21){
                System.out.println("Sólo existen 22 nodos. El número debe estar en el intervalo [0,21]");
            }else{
                valid = true;
            }
        }catch(Exception e){
            System.out.println("Entrada no válida, debe ser un número");
        }
      }while(!valid);
      return i;
      }
    
}

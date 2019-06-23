/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package realworld;

import structures.Graph;
import structures.Link;
import structures.SortedList;

/**
 *
 * @author danie
 */
public class Device {
    private boolean on;
    private char identifier;
    public int indexOnArray;
    public Graph network;
    private StatusTimer statusTimer;
    private int quantityOfJumps; //Cuántos saltos puede dar un paquete enviado por broadcast
    
    public Device(char identifier, Graph network, int indexOnArray){
        this.identifier = identifier;
        this.network = network;
        this.setStatusTimer();
        this.indexOnArray = indexOnArray;
    }
    
    private void setStatusTimer(){
        statusTimer = new StatusTimer(this);
    }
    
    public void turnOn(){
        this.on = true;
        statusTimer.startTimer();
    }
    
    public void turnOff(){
        this.on = false;
    }
    
    public boolean itsOn(){
        return this.on;
    }
    
    public char getIdentifier(){
        return this.identifier;
    }

    public void sendRoutedPackage(Device to, String message){
        //Calcular ruta
        int[] route = this.network.searchRoute(this.indexOnArray, to.indexOnArray);
        System.out.print("Ruta calculada por el nodo " + this.identifier + ": ");
        for(int i = 0; i<route.length; i++){
            System.out.print(this.network.vertexList[route[i]].label);
        }
        System.out.println();
        //Detectar primer intermediario
        int intermediary = route[1];
        //Enviar paquete
        //Nodos adyacentes a este
        SortedList adj = this.network.adjacency[this.indexOnArray];
        //recorro los nodos adyacentes enviando el paquete
        Link l = adj.getFirst();
        do{
            l.vertex.device.recvRoutedPackage(to, intermediary, message);
            l = l.next;
            if(l == null){
                break;
            }
        }while(true);
        //this.network.vertexList[intermediary].device.recvRoutedPackage(to, intermediary ,message);
    }

    public void sendBroadcastPackage(Device to, String message){
        //Crear paquete
        BroadcastPackage p = new BroadcastPackage(this, to, message, this.quantityOfJumps);
        //Enviar paquete(?)
    }
    
    public void recvRoutedPackage(Device to, int intermediary, String message){
        if(to.indexOnArray == this.indexOnArray){
            //Es este nodo el receptor
            System.out.println("El paquete ha sido recibido por el nodo " + this.identifier);
            return;
        }
        if(intermediary == this.indexOnArray){
            //Este nodo es intermediario
            this.sendRoutedPackage(to, message);
            return;
        }
        System.out.println("El nodo " + this.identifier + " escuchó el paquete pero no lo replica");
    }
}

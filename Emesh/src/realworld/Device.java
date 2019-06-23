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
    public int quantityOfJumps; //Cuántos saltos puede dar un paquete enviado por broadcast
    private long[] lastStatus;
    
    public Device(char identifier, Graph network, int indexOnArray){
        this.identifier = identifier;
        this.network = network;
        this.setStatusTimer();
        this.indexOnArray = indexOnArray;
        this.quantityOfJumps = network.MAX_VERTS;
        this.lastStatus = new long[network.MAX_VERTS];
        //Seteo como que hace 10 segundos que no sé nada de los nodos, para que aparezcan apagados para este nodo
        for(int i = 0; i<network.MAX_VERTS; i++){
            this.lastStatus[i] = System.currentTimeMillis() - 10000L;
        }
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
        statusTimer.stopTimer();
    }
    
    public boolean itsOn(){
        return this.on;
    }
    
    public char getIdentifier(){
        return this.identifier;
    }
    
    public void recvStatus(Device owner, int jump){
        //Si este dispositivo se encuentra apagado, no hacemos nada
        if(!this.itsOn()){
            return;
        }
        if(System.currentTimeMillis() - this.lastStatus[owner.indexOnArray] < 4000L){
            //Ya recibí este estatus recién, así que no lo replico más
            return;
        }
        //Escribo en mi registro que recibí el status de este dispositivo (owner)
        this.lastStatus[owner.indexOnArray] = System.currentTimeMillis();
        //Envío el status a los aledaños siempre y cuando jump sea mayor que cero y le resto uno
        if(jump > 0){
            //obtengo los nodos aledaños a este nodo
            SortedList adj = this.network.adjacency[this.indexOnArray];
            Link link = adj.getFirst();
            do{
                link.vertex.device.recvStatus(owner, jump-1);
                link = link.next;
                if(link == null){
                    break;
                }
            }while(true);
        }
    }

    public void sendRoutedPackage(Device to, String message){
        if(!this.checkStatus(to)){
            System.out.println("El nodo al que se le intenta enviar el paquete está desvinculado de la red");
            return;
        }
        //Calcular ruta
        int[] route = this.network.searchRoute(this, to);
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
            l.vertex.device.recvRoutedPackage(to, this, intermediary, message);
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
    
    public void recvRoutedPackage(Device to, Device from, int intermediary, String message){
        if(!this.itsOn()){
            //Si está apagado este nodo, no hacemos nada
            return;
        }
        if(to.indexOnArray == this.indexOnArray){
            //Es este nodo el receptor
            System.out.println("El paquete enviado por el nodo " + from.identifier +" ha sido recibido por el nodo " + this.identifier);
            return;
        }
        if(intermediary == this.indexOnArray){
            //Este nodo es intermediario
            this.sendRoutedPackage(to, message);
            return;
        }
        System.out.println("El nodo " + this.identifier + " escuchó el paquete pero no lo replica");
    }
    
    public boolean checkStatus(Device device){
        //Si han pasado más de 5 segundos desde el anterior status
        if(System.currentTimeMillis() - this.lastStatus[device.indexOnArray] > 5000L){
            return false;
        }
        //Si no, retornamos que el nodo si figura encendido
        return true;
    }
}

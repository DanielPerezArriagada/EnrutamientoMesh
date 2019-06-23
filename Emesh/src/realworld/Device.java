/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package realworld;

import structures.Graph;

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
        if(to.indexOnArray == this.indexOnArray){
            System.out.println("El paquete ha sido recibido por el nodo " + this.identifier);
            return;
        }
        int[] route = this.network.searchRoute(this.indexOnArray, to.indexOnArray);
        System.out.print("Ruta calculada por el nodo " + this.identifier + ": ");
        for(int i = 0; i<route.length; i++){
            System.out.print(this.network.vertexList[route[i]].label);
        }
        System.out.println();
        //Detectar primer intermediario
        int intermediary = route[1];
        //Enviar paquete
        this.network.vertexList[intermediary].device.sendRoutedPackage(to, message);
    }

    public void sendBroadcastPackage(Device to, String message){
        //Crear paquete
        BroadcastPackage p = new BroadcastPackage(this, to, message, this.quantityOfJumps);
        //Enviar paquete(?)
    }
}

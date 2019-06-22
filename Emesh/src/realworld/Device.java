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
    public Graph network;
    private StatusTimer statusTimer;
    private int quantityOfJumps; //Cuántos saltos puede dar un paquete enviado por broadcast
    
    public Device(char identifier, Graph network){
        this.identifier = identifier;
        this.network = network;
        this.setStatusTimer();
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

        //Detectar primer intermediario

        //Enviar paquete
    }

    public void sendBroadcastPackage(Device to, String message){
        //Crear paquete
        BroadcastPackage p = new BroadcastPackage(this, to, message, this.quantityOfJumps);
        //Enviar paquete(?)
    }
}

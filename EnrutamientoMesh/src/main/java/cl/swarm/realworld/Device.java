/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.swarm.realworld;

import cl.swarm.structures.Graph;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author danie
 */
public class Device {
    
    private int longitude;
    private int latitude;
    private boolean on;
    private String identifier;
    private Graph network;
    private StatusTimer statusTimer;
    
    public Device(String identifier, int longitude, int latitude, Graph network){
        this.longitude = longitude;
        this.latitude = latitude;
        this.identifier = identifier;
        this.network = network;
        this.setStatusTimer();
        this.turnOn();
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
    
    public String getIdentifier(){
        return this.identifier;
    }
}

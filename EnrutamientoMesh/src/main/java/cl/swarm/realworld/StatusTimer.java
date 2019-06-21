/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.swarm.realworld;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author danie
 */
public class StatusTimer implements ActionListener{
    
    Timer statusTimer;
    Device device;
    int millis;
    
    public StatusTimer(Device device){
        this.device = device;
        this.millis = 5000;
        
        this.setTimer();
    }
    
    private void setTimer(){
        statusTimer = new Timer(this.millis, this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //Enviar el status en esta parte
        System.out.println("El nodo " + this.device.getIdentifier() + " ha enviado un status");
    }
    
    public void startTimer(){
        this.statusTimer.start();
    }
    
    public void stopTimer(){
        this.statusTimer.stop();
    }
    
}

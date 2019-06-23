/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package realworld;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import structures.Link;
import structures.SortedList;

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
        //System.out.println("El nodo " + this.device.getIdentifier() + " ha enviado un status");
        //Envío el paquete de status a todos los vecinos de este nodo, ellos se encargarán de propagarlo
        SortedList adj = this.device.network.adjacency[this.device.indexOnArray];
        Link link = adj.getFirst();
        do{
            link.vertex.device.recvStatus(this.device, this.device.quantityOfJumps);
            link = link.next;
            if(link == null){
                break;
            }
        }while(true);
    }
    
    public void startTimer(){
        this.statusTimer.start();
    }
    
    public void stopTimer(){
        this.statusTimer.stop();
    }
    
}

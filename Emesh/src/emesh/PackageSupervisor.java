/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emesh;

/**
 *
 * @author danie
 */
public class PackageSupervisor {
    private long receptionCounter;
    private long transmitionCounter;
    private long validReceptionCounter;
    
    public PackageSupervisor(){
        receptionCounter = 0;
        transmitionCounter = 0;
        validReceptionCounter = 0;
    }
    
    public void received(){
        receptionCounter++;
    }
    
    public void transmited(){
        transmitionCounter++;
    }
    
    public void receivedByOwner(){
        validReceptionCounter++;
    }
    
    public void getInformation(){
        System.out.println("----------------------------------------------------------------");
        System.out.println("El paquete fue recepcionado " + receptionCounter + " veces.");
        System.out.println("El paquete fue recepcionado por el destinatario " + validReceptionCounter + " veces.");
        System.out.println("El paquete fue transmitido " + transmitionCounter + " veces");
        System.out.println("----------------------------------------------------------------");
    }
}

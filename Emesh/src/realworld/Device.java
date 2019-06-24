/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package realworld;

import emesh.PackageSupervisor;
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
        this.quantityOfJumps = 10;
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

    public void sendRoutedPackage(Device to, String message, PackageSupervisor supervisor){
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
        supervisor.transmited();
        Link l = adj.getFirst();
        do{
            l.vertex.device.recvRoutedPackage(to, this, intermediary, message, supervisor);
            l = l.next;
            if(l == null){
                break;
            }
        }while(true);
    }
    
    /*
    * Quería crear la forma de enviar paquetes broadcast también, para ver la comparativa,
    * pero quizás no alcance, así que lo dejo en stand by
    */
    public void sendBroadcastPackage(Device to, String message, PackageSupervisor supervisor){
        //Traer los nodos adyacentes
        SortedList adj = this.network.adjacency[this.indexOnArray];
        supervisor.transmited();
        //recorro los nodos adyacentes enviando el paquete
        Link l = adj.getFirst();
        do{
            l.vertex.device.recvBroadcastPackage(this, to, message, this.quantityOfJumps, supervisor);
            l = l.next;
            if(l == null){
                break;
            }
        }while(true);
    }
    
    public void recvBroadcastPackage(Device from, Device to, String message, int jump, PackageSupervisor supervisor){
        if(!this.itsOn()){
            //Si el nodo está apagado, no hace nada
            return;
        }
        //Lo cuento como recibido
        supervisor.received();
        if(this.indexOnArray == to.indexOnArray){
            //El paquete es de este nodo, no lo vuelvo a replicar
            //Lo cuento como recibido por el destinatario
            supervisor.receivedByOwner();
            System.out.println("El nodo " + this.identifier + " ha recibido el paquete enviado por el nodo " + from.identifier);
        }else{
            //No era para mi, así que lo replico, sólo si este salto es mayor a 0
            if(from.indexOnArray == this.indexOnArray){
                //El paquete era mío, así que no hago nada al respecto
                System.out.println("El nodo " + this.identifier + " ha recibido el paquete que él mismo envío, así que no lo replica");
                return;
            }
            if(jump > 0){
                System.out.println("El paquete no es para el nodo " + this.identifier + " por lo que lo envía por Broadcast");
                supervisor.transmited();
                //Traer los nodos adyacentes
                SortedList adj = this.network.adjacency[this.indexOnArray];
                //recorro los nodos adyacentes enviando el paquete
                Link l = adj.getFirst();
                do{
                    l.vertex.device.recvBroadcastPackage(from, to, message, jump-1, supervisor);
                    l = l.next;
                    if(l == null){
                        break;
                    }
                }while(true);
            }else{
                System.out.println("El paquete no es para el nodo " + this.identifier + " pero no lo replica porque se acabaron los saltos");
            }
        }
        
        
    }
    
    /*
    * Este método se encarga de recibir los paquetes y ver si debe reenviarlos o no (es intermediario, destino o ninguno)
    */
    public void recvRoutedPackage(Device to, Device from, int intermediary, String message, PackageSupervisor supervisor){
        if(!this.itsOn()){
            //Si está apagado este nodo, no hacemos nada
            return;
        }
        //Como recibimos el paquete, lo contamos
        supervisor.received();
        if(to.indexOnArray == this.indexOnArray){
            //Es este nodo el receptor
            //Como somos nosotros el receptor, lo anotamos en el supervisor
            supervisor.receivedByOwner();
            System.out.println("El paquete enviado por el nodo " + from.identifier +" ha sido recibido por el nodo " + this.identifier);
            return;
        }
        if(intermediary == this.indexOnArray){
            //Este nodo es intermediario
            //Anotamos que transmitiremos el paquete
            supervisor.transmited();
            this.sendRoutedPackage(to, message, supervisor);
            return;
        }
        System.out.println("El nodo " + this.identifier + " escuchó el paquete pero no lo replica");
    }
    
    /*
    * Este método verifica si este nodo considera a otro como activo fijándose en su tabla de status.
    * Si el tiempo que ha pasado desde el último status de un nodo es mayor a 5 segundos (en este caso), es porque está inactivo
    */
    public boolean checkStatus(Device device){
        //Si han pasado más de 5 segundos desde el anterior status
        if(System.currentTimeMillis() - this.lastStatus[device.indexOnArray] > 5500L){
            return false;
        }
        //Si no, retornamos que el nodo si figura encendido
        return true;
    }
}

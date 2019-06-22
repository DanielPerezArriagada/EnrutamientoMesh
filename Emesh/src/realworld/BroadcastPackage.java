/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package realworld;

/**
 *
 * @author danie
 */
public class BroadcastPackage {
    public Device from;
    public Device to;
    public String message;
    public int quantityOfJumps;

    public BroadcastPackage(Device from, Device to, String message, int quantityOfJumps){
    	this.from = from;
    	this.to = to;
    	this.message = message;
    	this.quantityOfJumps = quantityOfJumps;
    }
}

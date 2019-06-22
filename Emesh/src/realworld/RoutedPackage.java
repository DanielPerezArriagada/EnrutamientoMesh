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
public class RoutedPackage {
    public Device from;
    public Device to;
    public Device intermediary;
    public String message;
    public RoutedPackage(Device from, Device to, Device intermediary, String message){
        this.from = from;
        this.to = to;
        this.intermediary = intermediary;
        this.message = message;
    }
}

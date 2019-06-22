/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import realworld.Device;

/**
 *
 * @author danie
 */
public class Vertex {
    public char label;        // label (e.g. 'A')
    public Device device;
    public boolean wasVisited;
// ------------------------------------------------------------
   public Vertex(char lab, Device device)   // constructor
      {
      label = lab;
      this.device = device;
      wasVisited = false;
      }
// ------------------------------------------------------------
}

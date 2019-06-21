/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.swarm.structures;

/**
 *
 * @author danie
 */
public class Vertex {
    public char label;        // label (e.g. 'A')
   public boolean wasVisited;
// ------------------------------------------------------------
   public Vertex(char lab)   // constructor
      {
      label = lab;
      wasVisited = false;
      }
// ------------------------------------------------------------
}

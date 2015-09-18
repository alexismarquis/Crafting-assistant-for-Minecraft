/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcassist;

import java.util.ArrayList;

/**
 *
 * @author alexis
 */
public class Item {
    
    private String name;
    private ArrayList<Craft> crafts;
    
    public Item(String name) {
        this.name = name;
        this.crafts = new ArrayList();
    } 
    
    public void addCraft(Craft craft) {
        this.crafts.add(craft);
    } 
    
    public String getName() {
        return this.name;
    }
    
    public ArrayList<Craft> getCrafts() {
        return this.crafts;
    }
    
    public boolean isRessource() {
        return this.crafts.isEmpty();
    }
            
}

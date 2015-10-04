/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcassist;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexis
 */
public class Item {
    
    private String name;
    private String id;
    private Integer difficulty=null;
    
    private ArrayList<Craft> crafts;
    
    public Item(String id, String name) {
        this.name = name;
        this.id = id;
        this.crafts = new ArrayList();
    } 
    
    public void addCraft(Craft craft) {
        this.crafts.add(craft);
    } 
    
    public int getDifficulty(){
        return this.difficulty;
    }
    
    public void setDifficulty(int diff){
        this.difficulty = diff;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getId() {
        return this.id;
    }
    
    public ArrayList<Craft> getCrafts() {
        return this.crafts;
    }
    
    public boolean isRessource() {
        return this.crafts.isEmpty();
    }
    
    public String toString(){
        return name;
    }
}

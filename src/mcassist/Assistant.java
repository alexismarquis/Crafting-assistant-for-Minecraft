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
public class Assistant {
    
    private static Assistant instance;
    private ArrayList<Item> items;
    
    private Assistant() {
        this.items = loadItems();
    }
    
    public static Assistant getInstance() {
        if(instance == null) instance = new Assistant();
        
        return instance;
    }
    
    public ArrayList getCraftableItems() {
        ArrayList<Item> craftableItems = new ArrayList();

        for(Item item : items) {
            if(!item.isRessource()) craftableItems.add(item);
        }
        
        return craftableItems;
    }
    
    public ArrayList getFullCraft(Item item) {
        ArrayList<Craft> crafts = new ArrayList();
        
        
        return crafts;
    }
    
    private ArrayList loadItems() {
        ArrayList<Item> items = new ArrayList();
        
        items.add(new Item("Test"));
        
        return items;
    }
}

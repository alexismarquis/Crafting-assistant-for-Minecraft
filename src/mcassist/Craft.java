/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcassist;

import java.util.HashMap;

/**
 *
 * @author alexis
 */
public class Craft {
   private Item[][] items;
    
    public Craft(Item[][] items) {
        this.items = items;
    }
    public String toString(){
        String affiche = "";
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                if (items[i][j] != null) {
                     affiche += "\n"+items[i][j].getName();
                }
            }
        }
        return affiche;
    }
        
    
    
    public HashMap<Item,Integer> listRessource(HashMap<Item,Integer> liste){
        HashMap<Item,Integer> listeR;
        if (liste.isEmpty()) {
            listeR = new HashMap<Item,Integer>() ;
        }
        else{
            listeR = liste;
        }
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length ; j++) {
                if (items[i][j].isRessource()) {
                    if (listeR.containsKey(items[i][j])) {
                        listeR.put(items[i][j], listeR.get(items[i][j]) + 1);
                    }
                    else{
                        listeR.put(items[i][j], 1);
                    }
                }
                else{
                    Craft c = items[i][j].getCrafts().get(0);
                    listeR = c.listRessource(listeR);
                }
                }
            }
        return listeR;
   }
}

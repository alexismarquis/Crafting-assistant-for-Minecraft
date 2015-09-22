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
   private Item[] items;
   private int nbCraft;
    
    public Craft(Item[] items,int nbCraft) {
        this.items = items;
        this.nbCraft = nbCraft;
    }
    
    public Craft(Item[] items){
        this.items=items;
        this.nbCraft = 1;
    }
    
    public int getNbCraft(){
        return nbCraft;
    }
    
    public String toString(){
        String affiche = "";
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                 affiche += "\n"+items[i].getName();
            }    
        }
        return affiche;
    }
    
    
    public HashMap<Item,Integer> listRessource(HashMap<Item,Integer> liste,HashMap<Item,Integer> coffre){
        HashMap<Item,Integer> listeR;
        HashMap<Item,Integer>coffret;
        if (liste.isEmpty()) {
            listeR = new HashMap<Item,Integer>() ;
        }
        else{
            listeR = liste;
        }
        if(coffre.isEmpty()){
            coffret = new HashMap<Item,Integer>();
        }
        else{
            coffret=coffre;
        }
        
        for (int i = 0; i < items.length; i++) {
            Craft currentCraft=items[i].getCrafts().get(0);
                if (items[i].isRessource()) {
                    if (listeR.containsKey(items[i])) {       
                        listeR.put(items[i], listeR.get(items[i]) + 1);
                    }
                    else{
                        listeR.put(items[i], 1);
                    }
                }
                else{
                    if (coffret.containsKey(items[i])) {
                        coffret.put(items[i],coffret.get(items[i])-1);
                        //Voir apres debug
                        if (listeR.containsKey(items[i])) {       
                            listeR.put(items[i], listeR.get(items[i]) + 1);
                        }
                    }
                    else{
                        if (currentCraft.getNbCraft()!=1) {
                            coffret.put(items[i],coffret.get(items[i])+currentCraft.getNbCraft()-1);
                        }
                        listeR = currentCraft.listRessource(listeR,coffret);
                    }
                }   
            }
        return listeR;
   }
}

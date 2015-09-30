/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcassist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
    
    public Item[] getItems() {
        return items;
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
    
    public HashMap<Item,Integer> init(HashMap<Item,Integer> map){
        if (map.isEmpty()) {
            return new HashMap<Item,Integer>();
        }
            return map;      
    }
    
    /**
     *
     * @param map
     * @param i,HashMap<Item,Integer>
     * @return HashMap<Item,Integer>
     * @author JulienVannier
     */
    public HashMap<Item,Integer> ajouterListe(HashMap<Item,Integer> map,int i){
        HashMap<Item,Integer> test= map;
        if (map.containsKey(items[i])) { //Si la essource apparait déja dans la liste
            test.put(items[i], test.get(items[i]) + 1); //on incrémente le nombre de cette ressource
        }
        else{
            test.put(items[i], 1); //Sinon on l'ajoute a la liste et on fixe sa quantité à1
        }
        return test;
    }
    
    /**
     *
     * @param liste
     * @param coffre
     * @return HashMap<Item,Integer>
     * @author JulienVannier
     */
    public HashMap<Item,Integer> listRessource(){
         return listRessource(new HashMap<Item,Integer>(),new HashMap<Item,Integer>());
    }
    
    public HashMap<Item,Integer> listRessource(HashMap<Item,Integer> liste,HashMap<Item,Integer> coffre){
        HashMap<Item,Integer> listeR; //HashMap Contenant la liste des item necessaire et leurs nombres
        HashMap<Item,Integer>coffret; //HashMap contenant les items en trops
        listeR = init(liste); //Initialisation
        coffret = init(coffre);    
        for (int i = 0; i < items.length; i++) {//Parcours des 9 case composant un craft
            if (items[i] != null){
                if (items[i].isRessource()) { //Si l'item est une ressource
                    listeR = ajouterListe(listeR,i);
                }
                else{
                    Craft currentCraft=items[i].getCrafts().get(0);//Permet d'obtenir le craft de l'item current
                    if (coffret.containsKey(items[i]) && coffret.get(items[i]) != 0) { //On regarde si l'item est contenu dans le coffre et si la quantité n'est pas égale a 0
                        coffret.put(items[i],coffret.get(items[i])-1); // Si oui on retire une fois l'item dans le coffre
                        //Voir apres debug
                        listeR = ajouterListe(listeR,i);
                    }
                    else{ //Si non contenu dans le coffre on doit craft l'item
                        if (currentCraft.getNbCraft()>1) {//Si le craft de cette item nous donne plusieurs de cette item
                            if (coffret.containsKey(items[i])) {
                                coffret.put(items[i],coffret.get(items[i])+currentCraft.getNbCraft()-1);//Ajout au coffre
                            }
                            else{
                                coffret.put(items[i], currentCraft.getNbCraft()-1);
                            }
                            listeR = ajouterListe(listeR,i);
                        }
                        listeR = currentCraft.listRessource(listeR,coffret);//Appel fonction en récursif
                    }
                }
            }
        }
        return listeR; //Retourne la liste des items nécessaire au craft et leurs nombre
   }
    public HashMap<Item,Integer> StepByStepItem(){
        HashMap<Item,Integer> liste = listRessource();
        HashMap<Item,Integer> listeItem = new HashMap<Item,Integer>();
        Set<Item> listKeys=liste.keySet();  // Obtenir la liste des clés
    	Iterator iterateur=listKeys.iterator();
        while(iterateur.hasNext())
    		{
                    Item key= (Item)iterateur.next();
                    if (!key.isRessource()) {
                        listeItem.put(key, liste.get(key));
                    }
    		}
        return listeItem;
    }
    
    public HashMap<Item,Integer> StepByStepRessource(){
        HashMap<Item,Integer> liste = listRessource();
        HashMap<Item,Integer> listeRessource = new HashMap<Item,Integer>();
        Set<Item> listKeys=liste.keySet();  // Obtenir la liste des clés
    	Iterator iterateur=listKeys.iterator();
        while(iterateur.hasNext())
    		{
                    Item key= (Item)iterateur.next();
                    if (key.isRessource()) {
                        listeRessource.put(key, liste.get(key));
                    }
    		}
        return listeRessource;
    }
}

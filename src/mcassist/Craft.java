/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcassist;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 *
 * @author alexis
 */
public class Craft {
   private Item[] items;
   private int nbCraft;
   private String type;

    public Craft(Item[] items,int nbCraft, String type) {
        this.items = items;
        this.nbCraft = nbCraft;
        this.type = type;
    }

    public int craftDifficulty(){
        double difficulty=0;
        int nbDiff=0;
        int nb=0;
        HashMap<Item,Integer> listeRessource = StepByStepRessource();
        Set<Item> listKeys=listeRessource.keySet();  // Obtenir la liste des clés
        for (Item key : listKeys) {
            difficulty += Math.pow(key.getDifficulty(),2)*listeRessource.get(key);//Affecte un coeff selon la difficutlé
            nbDiff += key.getDifficulty()*listeRessource.get(key);//Somme des difficultés
            if (key.getDifficulty()>=3) { 
                nb += (listeRessource.get(key))/5; //Nombre d'item avec une difficulté superieur a 3
            } 
        }
        difficulty /= nbDiff;
        difficulty += nb;
        difficulty*=2;
        if (difficulty >10)difficulty = 10;
        return (int)Math.ceil(difficulty);
    }

    public String getType() {
        return type;
    }

    public int getNbCraft(){
        return nbCraft;
    }

    public Item[] getItems() {
        return items;
    }

   @Override
    public String toString(){
        String affiche = "";
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                 affiche += "\n"+items[i].getName();
            }
        }
        return affiche;
    }

    public LinkedHashMap<Item,Integer> init(LinkedHashMap<Item,Integer> map){
        if (map.isEmpty()) {
            return new LinkedHashMap<>();
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
    public LinkedHashMap<Item,Integer> ajouterListe(LinkedHashMap<Item,Integer> map,int i){
        LinkedHashMap<Item,Integer> test= map;
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
    public LinkedHashMap<Item,Integer> listRessource(){
         return listRessource(new LinkedHashMap<Item,Integer>(),new LinkedHashMap<Item,Integer>());
    }

    private LinkedHashMap<Item,Integer> listRessource(LinkedHashMap<Item,Integer> liste,LinkedHashMap<Item,Integer> coffre){
        LinkedHashMap<Item,Integer> listeR; //HashMap Contenant la liste des item necessaire et leurs nombres
        LinkedHashMap<Item,Integer>coffret; //HashMap contenant les items en trops
        listeR = init(liste); //Initialisation
        coffret = init(coffre);
        for (int i = 0; i < items.length; i++) {//Parcours des 9 case composant un craft
            if (items[i] != null){
                if (items[i].isRessource()) { //Si l'item est une ressource
                    listeR = ajouterListe(listeR,i);
                }
                else{
                    Craft currentCraft = items[i].getCrafts().get(0);//Permet d'obtenir le craft de l'item current
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
                        }
                        listeR = currentCraft.listRessource(listeR,coffret);//Appel fonction en récursif
                        listeR = ajouterListe(listeR,i);

                    }
                }
            }
        }
        return listeR; //Retourne la liste des items nécessaire au craft et leurs nombre
   }
    public LinkedHashMap<Item,Integer> StepByStepItem(){
        LinkedHashMap<Item,Integer> liste = listRessource();
        LinkedHashMap<Item,Integer> listeItem = new LinkedHashMap<>();
        Set<Item> listKeys=liste.keySet();  // Obtenir la liste des clés
        for (Item key : listKeys) {
            if (!key.isRessource()) {
                listeItem.put(key, liste.get(key));
            }
        }
        return listeItem;
    }

    public HashMap<Item,Integer> StepByStepRessource(){
        LinkedHashMap<Item,Integer> liste = listRessource();
        LinkedHashMap<Item,Integer> listeRessource = new LinkedHashMap<>();
        Set<Item> listKeys=liste.keySet();  // Obtenir la liste des clés
        for (Item key : listKeys) {
            if (key.isRessource()) {
                listeRessource.put(key, liste.get(key));
            }
        }
        return listeRessource;
    }
}

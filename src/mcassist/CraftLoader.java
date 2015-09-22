package mcassist;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author TheLastOperator
 */
public class CraftLoader {
    
    HashMap<String, Item> items = new HashMap<>();
    
    public static void main(String [] args) {
        CraftLoader cl = new CraftLoader();
        try {
            cl.loadCraftsFromFile("C:\\VPC\\crafts.json");
        } catch (JSONException | IOException ex) {
            Logger.getLogger(CraftLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @return a hashMap of items organized by id
     */
    public HashMap<String, Item> getItems() {
        return items;
    }
    
    /**
     * Load the craft list from the file located at path
     * @param path
     * @throws JSONException
     * @throws IOException 
     */
    public void loadCraftsFromFile(String path) throws JSONException, IOException {
        String str = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(str);

        items.clear();
        
        Iterator<?> keys = obj.getJSONObject("items").keys();
        
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            //System.out.println(key);
            if ( obj.getJSONObject("items").get(key) instanceof JSONObject ) {
                items.put(key, new Item(obj.getJSONObject("items").getJSONObject(key).getString("name")));
            }
        }
        
        keys = obj.getJSONObject("items").keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            JSONArray crafts = obj.getJSONObject("items").getJSONObject(key).getJSONArray("crafts");
            Item[] craftItems = new Item[crafts.length()];
            //System.out.println("Item id: " + key + " Name: " + items.get(key).getName());
            for(int i = 0; i < crafts.length(); i++) {
                //System.out.println("Crafted with: ");
                for(int j = 0; j < crafts.length(); j++) {
                    craftItems[j] = items.get(crafts.getJSONObject(i).getJSONArray("input").getString(j)); 
                    //if(craftItems[j] != null) System.out.println(craftItems[j].getName());
                }
                items.get(key).addCraft(new Craft(craftItems, crafts.getJSONObject(i).getInt("output")));
            }
        }
    }  
}

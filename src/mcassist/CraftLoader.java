package mcassist;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author TheLastOperator
 */
public class CraftLoader {
    
    public static void main(String [] args) {
        CraftLoader cl = new CraftLoader();
        try {
            cl.loadCraftsFromFile("C:\\VPC\\crafts.json");
        } catch (JSONException | IOException ex) {
            Logger.getLogger(CraftLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadCraftsFromFile(String path) throws JSONException, IOException {
        String str = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(str);

        HashMap<Integer, Item> items = new HashMap<Integer, Item>();
        
        Iterator<?> keys = obj.getJSONObject("items").keys();
        
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            System.out.println(key);
            if ( obj.getJSONObject("items").get(key) instanceof JSONObject ) {
                items.put(Integer.parseInt(key), new Item(obj.getJSONObject("items").getJSONObject(key).getString("name")));
            }
        }
        
        Iterator<Integer> keySetIterator = items.keySet().iterator();

        while(keySetIterator.hasNext()){
            Integer key = keySetIterator.next();
            items.get(key).addCraft(new Craft());
            System.out.println("key: " + key + " value: " + items.get(key).getName());
        }

    }
    
}

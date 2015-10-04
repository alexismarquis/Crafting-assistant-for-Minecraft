package mcassist;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 *
 * @author TheLastOperator
 */
public class CraftLoader {
    
    LinkedHashMap<String, Item> items = new LinkedHashMap<>();
    
    /**
     * 
     * @return a hashMap of items organized by id
     */
    public LinkedHashMap<String, Item> getItems() {
        return items;
    }
    
    
    public void loadFromFile(String path) throws FileNotFoundException, IOException {
        FileReader reader = new FileReader(path);
        JsonObject object = Json.parse(reader).asObject();
        
        JsonObject jsonItems = object.get("items").asObject();

        items.clear();
        
        for (Member member : jsonItems) {
            String id = member.getName();
            JsonObject jsonItem = member.getValue().asObject();
            String name = jsonItem.get("name").asString();
            JsonValue difficulty = jsonItem.get("difficulty");
            items.put(id, new Item(id, name));
            if (difficulty != null) {
               items.get(id).setDifficulty(difficulty.asInt()); 
            }
            
        }
        
        for (Member member : jsonItems) {
            String id = member.getName();
            JsonArray jsonCrafts = member.getValue().asObject().get("crafts").asArray();  
            
            for (JsonValue value : jsonCrafts) {
                JsonObject jsonCraft = value.asObject();
                String type = jsonCraft.get("type").asString();
                Integer output = jsonCraft.get("output").asInt();
                JsonArray jsonInput = jsonCraft.get("input").asArray();        
                
                Item[] craftItems = new Item[jsonInput.size()];
                
                int i = 0;
                for (JsonValue craftValue : jsonInput) {
                    craftItems[i] = items.get(craftValue.asString());
                    i++;
                }
                items.get(id).addCraft(new Craft(craftItems, output, type));
            }
        }

    }
    
}

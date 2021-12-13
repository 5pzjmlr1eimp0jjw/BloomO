package edu.brown.cs.cs32friends.handlers;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.brown.cs.cs32friends.plant.PlantImage;

public class PlantImageDeserializer extends StdDeserializer<PlantImage> {

    int flag = 0;

    public PlantImageDeserializer() {
        this(null);
    }

    protected PlantImageDeserializer(Class<?> vc) {
        super(vc);

    }

    public int getFlag() {
        return flag;
    }

    @Override
    public PlantImage deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        flag = 0;
        JsonNode node = p.getCodec().readTree(p);  // read the tree
        PlantImage pi = new PlantImage();
        JsonNode node2 = p.getCodec().readTree(p); // read the tree
        try {
        node2 = node.get("query").get("pages"); // get the pages node
        Iterator<Map.Entry<String, JsonNode>> fields = node2.fields();  // get the fields
        pi.setUrl(node2.get(fields.next().getKey()).get("thumbnail").get("source").asText()); // get the url
    } catch (NullPointerException e) {
            flag = 1;
        }
        return pi;
    }
    
}

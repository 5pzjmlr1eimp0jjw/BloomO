package edu.brown.cs.cs32friends.handlers;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.brown.cs.cs32friends.zones.Zone;

/**
 * The class that does the deserialization of the Json response from the API call
 * We use this to deserialize the nested Json 
 */
public class ZipcodeDeserializer extends StdDeserializer<Zone> {

    public ZipcodeDeserializer() {
        this(null);
    }

    public ZipcodeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Zone deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node;
        Zone zone = new Zone(); 
        try {
            node = jp.getCodec().readTree(jp);
            zone.setZone(node.get("additionalMessageToDisplay").textValue()); // get the values and directly set the fields for the particular zone class
            if(zone.zone.equals("")) {
                return null;
            }
            zone.setZoneSpecificInfo();
            
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (JacksonException e) {
            e.printStackTrace();
        }   
         return zone;
    }
}
    


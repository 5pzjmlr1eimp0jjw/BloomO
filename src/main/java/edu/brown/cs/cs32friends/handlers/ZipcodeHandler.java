package edu.brown.cs.cs32friends.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import edu.brown.cs.cs32friends.main.CommandHandler;
import edu.brown.cs.cs32friends.main.ParseCommands;
import edu.brown.cs.cs32friends.zones.Zone;


// The class that does the zone look-up given a zipcode.
public class ZipcodeHandler implements CommandHandler {

    
    public static Map<Integer, Zone> zones = new HashMap<Integer, Zone>();
    public Zone curZone;

    // handle method will make an http request to the zipcode API and return the zone of the zipcode.

public static Map<Integer, Zone> getZones() {
    return zones;
}

public Zone getCurZone() {
    return curZone;
}

@Override
public void handle() {
    
    System.out.println("Start the zone look-up:");
    String[] input = ParseCommands.getInputLine().split(" ");  // split the input line to get the zipcode input
    String zipcode = input[1];
    Integer zip = Integer.parseInt(zipcode);

    if (zones.containsKey(zip)) { // ideally, we do not want to make the same request over and over again.
        
        curZone = zones.get(zip);
        System.out.println("Zipcode found in our database!");
        System.out.println("Results:");
        System.out.println("zipcode " + zip + " is in " + curZone.getZone());

    } else {
        String urlToZone = "https://restrict-by-zipcode.herokuapp.com/api/check-shop-zipcode?zipcode=" + zipcode + "&shop=plantmegreen.myshopify.com";  // create the url
        HttpClient client = HttpClient.newHttpClient();  
        HttpRequest requestToZone = HttpRequest.newBuilder().uri(URI.create(urlToZone)).build();  // make a simple API request
        HttpResponse<String> apiResponse;
        try {
            ObjectMapper mapper = new ObjectMapper(); 
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Zone.class, new ZipcodeDeserializer());  // create the mapper & add the deserializer of the json
            mapper.registerModule(module);
            apiResponse = client.send(requestToZone, HttpResponse.BodyHandlers.ofString());   // send the request and get the response
            Zone zone = this.mapClass(mapper, apiResponse);
            if (zone == null) {
                curZone = null;
                return;
            }
            curZone = zone;  
            zones.put(zip, zone); // make a map from zipcode to the Zone class of that zipcode ("zipcode db")
            System.out.println("Results:");
            System.out.println("zipcode " + zip + " is in " + zone.getZone());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

public Zone mapClass(ObjectMapper mapper, HttpResponse<String> apiResponse) {
    Zone zone = null;
    try {
        zone = mapper.readValue(apiResponse.body(), Zone.class);
    } catch (Exception e) {
        return null;
    }
    return zone;
}


}
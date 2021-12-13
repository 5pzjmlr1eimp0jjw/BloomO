package edu.brown.cs.cs32friends.handlers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.module.SimpleModule;

import edu.brown.cs.cs32friends.main.CommandHandler;
import edu.brown.cs.cs32friends.main.ParseCommands;
import edu.brown.cs.cs32friends.plant.PlantImage;

public class PlantImageHandler implements CommandHandler {

    String link = "";

    public String getCurUrl() {
        return this.link;
    }
    @Override
    public void handle() {
        System.out.println("Getting the link to the plant image...");
        String[] input = ParseCommands.getInputLine().split(" ");
        String plantName = "";
        if (input.length < 2) {
            System.out.println("Invalid input. Please try again.");   
            return;
        } else if (input.length > 2){
            for (int i = 1; i < input.length; i++) { //handles the case where plant name consists of multiple words
                if (i == 1) {
                    plantName += input[i];
                } else {
                    plantName += "_" + input[i];
                }
            }
        } else {
            plantName = input[1];
        }
        System.out.println("Fetching images of " + plantName + "...");

        String url = "https://en.wikipedia.org/w/api.php?action=query&titles=" + plantName + "&prop=pageimages&format=json&pithumbsize=500";  // create the url
        HttpClient client = HttpClient.newHttpClient();  
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();  // make a simple API request
        HttpResponse<String> apiResponse;
        PlantImageDeserializer deserializer = new PlantImageDeserializer();
        try {
            ObjectMapper mapper = new ObjectMapper(); 
            SimpleModule module = new SimpleModule();
            module.addDeserializer(PlantImage.class, deserializer);  // create the mapper & add the deserializer of the json
            mapper.registerModule(module);
            apiResponse = client.send(request, HttpResponse.BodyHandlers.ofString()); // send the request and get the response
            PlantImage pi = mapper.readValue(apiResponse.body(), PlantImage.class);
            
            if (deserializer.getFlag()==1) {   //some plants might not have any image on wikipedia (or no page found for the plant)
                System.out.println("Error");
                System.out.println("No image of " + plantName + " found");
            }
            else {
                System.out.println("Results:");
                System.out.println(pi.getUrl());
                link = pi.getUrl();
            }
            

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
    }
    
    
    
}

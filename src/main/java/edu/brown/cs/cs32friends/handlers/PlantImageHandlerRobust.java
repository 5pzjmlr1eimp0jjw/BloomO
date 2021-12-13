package edu.brown.cs.cs32friends.handlers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import edu.brown.cs.cs32friends.main.CommandHandler;
import edu.brown.cs.cs32friends.main.ParseCommands;

public class PlantImageHandlerRobust implements CommandHandler {
    
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

        String url = "https://es.wikipedia.org/wiki/Archivo:"+ plantName+".jpg";  // create the url
        HttpClient client = HttpClient.newHttpClient();  
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();  // make a simple API request
        HttpResponse<String> apiResponse;
        System.out.println("Deserializing...");
        try {

            Pattern pattern = null;
            Matcher matcher = null;
            String w1 = "";
        

            apiResponse = client.send(request, HttpResponse.BodyHandlers.ofString()); // send the request and get the response
            pattern = Pattern.compile("<img[^>]*src=\"([^\"]*)",Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(apiResponse.body());
            if (matcher.find()) {
                w1 = matcher.group(1);
            }

            if (w1.equals("")||(w1.equals("//upload.wikimedia.org/wikipedia/commons/thumb/3/34/Icon_-_upload_photo_2.svg/40px-Icon_-_upload_photo_2.svg.png")))
            { //some plants might not have any image on wikipedia (or no page found for the plant)
                System.out.println("Error");
                System.out.println("No image of " + plantName + " found");
            }
            else {
                System.out.println("Results:");
                String urlToImg = w1.toString().replace("//", "");
                this.link = urlToImg;
                System.out.println("The image link is: " + urlToImg);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
    }
    
}

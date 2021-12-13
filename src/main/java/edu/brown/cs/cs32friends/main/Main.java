package edu.brown.cs.cs32friends.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.brown.cs.cs32friends.gui.GUI;
import edu.brown.cs.cs32friends.handlers.PlantLoaderHandler;
import edu.brown.cs.cs32friends.handlers.UserPlant;
import edu.brown.cs.cs32friends.kdtree.coordinates.Coordinate;
import edu.brown.cs.cs32friends.plant.Plant;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 *
 */

public final class Main {

  private static final int DEFAULT_PORT = 4567;
  private GUI gui;
  private static PlantLoaderHandler plantLoaderHandler = new PlantLoaderHandler();

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();

  }

  public static Map<String, Plant> getPlantMap() {
    return plantLoaderHandler.getPlantList();
  }

  public static Map<Integer, List<Coordinate<String>>> getZoneMap() {
    return plantLoaderHandler.getZoneList();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
    gui = new GUI();
  }

  private void run() {
    
    ParseCommands replit = new ParseCommands();

    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }
    plantLoaderHandler.handle();
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in))) {
      String input;
      while ((input = br.readLine()) != null) {
        input = input.trim();
        ParseCommands.setInputLine(input);
        String command = ParseCommands.getArguments().get(0);
        replit.handleArgs(command);
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      System.out.println("ERROR: Invalid input for REPL");
      ParseCommands.setOutputString("ERROR: Invalid input for REPL");
    }
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    // Setup Spark Routes
   
    Spark.options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");

      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
    });

    Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
    Spark.post("/zone", gui.zoneLookupGUI);
    Spark.post("/plant-lookup", gui.plantLookupGUI);
    Spark.post("/zoneAdd", gui.zoneAddGUI);
    Spark.post("/plant-LatLookup", gui.latNameLookupGUI);
    //Spark.post("/plant-image-add", gui.imageUploadGUI);
    Spark.post("/users-plant", gui.userPlantGUI);
    Spark.post("/add-users-plant", gui.addUserPlantGUI);
  }

  /**./
   * Display an error page when an exception occurs in the server.
   *
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}

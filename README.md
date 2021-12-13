# cs0320 Term Project 2021

## **How to Build and Run**
To host the website, do the following command:

```shell
cd plant-app

npm start
```

To Run the REPL, do the following command:

```shell
mvn package

./run      <- start the repl

plant_zone <zipcode>      <- get the zone information for a zipcode

input_plant_image <name of plant>      <- get the image link in url form of an input plant 

plant_load <path to csv file>
```

## **Team Members:**  
*(PR: primary role, SR: secondary role)
| Name|Email|
|----|----|
|Kenta Yoshii|kenta_yoshii@brown.edu|||
|Yuki Hayashita|yhayash2@cs.brown.edu|||
|Sylvie Bartusek|sylvie_bartusek@brown.edu|||
|Amanda Levy|alevy14@cs.brown.edu|||
|Emma Shapland|eshaplan@cs.brown.edu|||
|Truman Cunningham|truman_cunningham@brown.edu|||

## Roles
*(PR: primary role, SR: secondary role)

*number in the brackets indicate the week number

frontend, backend, data sourcing, stakeholder relations/requirements/user-facing documentation, test planning, security, etc.

| Name|PR (1)|SR (1)|
|----|----|----|
|Kenta Yoshii|__backend__|__frontend__|
|Yuki Hayashita|__frontend__|__backend__|
|Sylvie Bartusek|__backend__|__testing__|
|Amanda Levy|__frontend(security)__|__planning__|
|Emma Shapland|__data sourcing__|__stakeholders__|
|Truman Cunningham|__data sourcing__|__backend__|

**Mentor TA:** Colton Rusch (colton_rusch@brown.edu)

## **Meetings**

**Specs, Mockup, and Design Meeting:**

General Structure Meeting: 11/11 @ 8:00 pm
General Structure Meeting2: 11/16 @ 9:30 pm

Figma: 11/13 @ 10:00 pm


**ICM Checkpoint:**

**Management Checkpoint:**

## **References**
* [Figma Prototype of our website](https://www.figma.com/proto/lQ8veNlC6axWsobIxj7PLu/PLANTS!!!?node-id=3%3A2&scaling=min-zoom&page-id=0%3A1&starting-point-node-id=3%3A2)
* [Planning Document](https://docs.google.com/document/d/1pYqY9224oIA1x9fmDM748JTxmxvJ2ovvyK6aR-lqzCk/edit)
* ...

___

## **Project Description**
### An App for Gardeners

- `Overview`
  - a website or app that has the following functionalities
  
    1. __Plant Lookup__
       - If the plant is not in the database, user can add it by providing the appropriate information
    2. __Zone lookup__
       - The user can search for a zone by providing the longitude and latitude (or zip code). 
       - Zone information, city, state, and other characteristics are will be returned and displayed.
    3. __Plant Recommendation__
       - A user can get plants recommendations by providing the zone number.
       - A user can get plants recommendations by providing a plant name.
       - A user can get plants recommendations based on their previously planted plants.
    4. __User Login__
       - A user can login to the website by providing their userId and password.

- `Databases`
  - Since this website we are trying to make is particularly oriented for gardeners, it is hard to find a database for our use. So we will be doing either one of the following methods.
    1. We create our own SQL database for our own use including attributes we want by scraping data from the internet. For example, we could scrape data from [garden.org](https://garden.org/plants/view/76639/Closed-Gentian-Gentiana-andrewsii/) and create our own db.
    2. Another approach is using the open source database such as [the USDA](https://plants.sc.egov.usda.gov/home) or [OpenFarm](https://openfarm.cc/pages/about) by means of API calls. However, this approach is not as efficient as approach 1, as the attributes the databases include are something we will not be using.
    3. Use one of the databases on Kaggle. This pre-made database could be good when used as tests, but does not serve our purpose, as We could not find the suitable database.

- `Project Structure`
  - __User class__
      ```java
      class User {
        private String name;
        private String age;
        private String[] previouslyGrownPlants;
        private String[] address
        ...
      }
      ```
      where all the basic information about the user is stored.

  - __Plant class__ (using inheritance)
    ```java
    class Plant { //superclass
      private String name; 
      private String type;
      private int waterNeeds;
      ...
    }

    class Dandelion extends Plant {
      private String color;
      ...
    }
    ```
    where each plant extends a Plant class and has its own unique traits.
  - __Plant class__ (using interface)
    ```java
    interface PlantInterface { //interface
      private String name;
      private String type;
      private int waterNeeds;
      ... 

      String getName();
      String getZone();
    }

    class Plant implements PlantInterface {
      private String name = "Dandelion";
      private String type = "Flower";
      private int waterNeeds = 1;
    }
    ```
    where each plant implements a Plant interface and has its own unique traits.

  - __Zone Class__ 
    ```java
    interface Zone {
      private String name;
      private int zoneId;
      private int minTemp;
      ...

      String getName();
      ...
    }

    class Zone1 implements Zone {
      private String name = "Zone 1";
      private String zoneId = "1";
      private int minTemp = 60;
    }
    ```
    where each zone has its corresponding traits.
- `User Story`
  
  - Basic Functionality Requirements
    1. As a user, I can __look up__ plants.
    2. As a user, I can enter my previously owned plants and __get plant recommendations__. 
    3. As a user, I can __enter my outdoor location__ and get plant recommendations (based on hardiness levels, climate?). 
    4. As a user, I can enter my __indoor growing conditions__ and get plant recommendations. 
  - Nice-to-haves
  
    1. As a user, I want to see a __display of a map__ ideal growing conditions.
    2. As a user, I can __connect with new gardeners__.
    3. As a user, I want to be able to __log in to my own account__.
    4. As a user, I want to make my own account __look fancy__.

- `Implementation for Basic User Stories`
    1. To look up plants, we will use a *hashmap* to store all the plants where the map key is the plant name and the value is the plant object. This allows us to do a search for a plant by name. If the plant does not exist in the database, the user can have an option to add it to the database. The user will be required to fill in the appropriate information.
    ```java
    Map<String, <nameOfPlant>> plantMap = new HashMap<String, plant>();
    plantMap.put("Dandelion", new plant(...));
    ```
    2. Plant recommendations can be done in two ways.
   
       1. One way is to recommend variety of plants given a single plant. For this case, we can simply create a plant node and feed that into kd-tree to get plants that best match with the input plant.
       2. Second way is to recommend new plants for an user based on their previous plants. For this case, we can use the user's previous plants to get an "average plant" and then use kd-tree to get plants that best match with average plant.


    3. To get plant recommendations based on outdoor location/zone number, we can create a hashmap, where the key is the zone number and the value is the set that contains all the plants in that zone. 
    ```java
    Set<Plant> zone1 = new HashSet<Plant>();
    Set<Plant> zone2 = new HashSet<Plant>();
    ...

    Map<String, Set<Plant>> zoneMap = new HashMap<String, Set<Plant>>();
    //examplle: zoneMap.put(1,zone1);
    //          zoneMap.get(1).add(new plant());
    ```
    The difficulty lies in the fact that hardiness zones are not separated nicely and some overlaps. We need to come up with a way to handle this (hardcode way might be to use a bunch of if-else statements).

    4. For the login feature, we will use React framework. We will in particular use [Auth0](https://auth0.com/blog/complete-guide-to-react-user-authentication/) to do this.
- `User Experience/Prototype`
    - For more information on this, please refer to the [prototype](https://www.figma.com/proto/lQ8veNlC6axWsobIxj7PLu/PLANTS!!!?node-id=3%3A2&scaling=min-zoom&page-id=0%3A1&starting-point-node-id=3%3A2)
- `Testing`
    - Our testing plan is as follows:
      - We will use the [JUnit](https://junit.org/) framework to test our code part by part.
      - We will be testing the plant lookup, plant recommendations, and user account creation in the first stage.
      - After we have the basic functionality working, we will then start testing on the user-specific recommendations algorithm.
      - For testing data, we will create our own mini SQL database and use it to test our code.

- `Class Diagram`
  ![Class Diagram](./Store/Img/classDiagram.jpg)
  
- `Security Concerns`
  - One security concern our project has is that we are storing user information in a database. Additionally, we will be needing to store user login information. To secure our database, we will be using a password hashing algorithm.

- `Expected Hurdles`
  - One hurdle we have is the initial loading of the database and the creation of the plants (and the hashmaps). 
  - Another hurdle is the user account creation. To secure the user account, we need to be very wary of how we implement it.
  - The hardest of all would probability be the implementation of the recommender algorithms using kdtree.
  - Finally, we need to come up with an efficient way of adding new data to the database. (eg. new plants, new zones, new plants a user has grown, etc.)

- `Impact of This Project`
  - We are designing this app for all the gardeners out there who want to grow plants, but don't have enough knowledge about plants. We hope to help them learn about plants and grow them. Although we do not necessarily exclude a specific group of people, this project is not of an interest for non-gardeners, so in that sense, this project is not for them.

  - As mentioned above, the prime target of this project is the gardeners out there who want to grow plants. They can benefit from our project by learning about plants more and how they can grow them correctly. Then can also benefit from getting exposures to plants that they have never seen before (through our recommendation algorithm).

  - As our project is something that helps people by teaching them new things, we do not necessarily think there are any harmful side effects. 
  
  - As we have not yet implemented the project yet, we have not yet discovered potential harms.

- `Road Map`
  - The implementation of the ___PLANTS!!!___ project will be done in the following stages:
    1. Scraping Data and Creating Database
    2. Plant Lookup functionality
       - For this we need Plant class and few other supporting classes
    3. Zone Lookup functionality
       - Zone class and Zone interface
    4. Plant Recommendation Part 1(Single Plant)
       - First we have to make sure the plant recommendation works for one input plant
    5. Plant Recommendation Part 2(Multiple Plants)
       - Then we can test the plant recommendations given multiple plants
    6. User Account Creation/Adding Login Functionality
___
  
## **Back-up Ideas:** 

### Idea 1: App for Meeting New People

- `Overview`
  - It is so much better to study with others but it is hard to coordinate the same location, especially when you are rushing to settle down and start working. Sometimes, your friends can be in the same library as you, and you don’t even realize it! You will get a list of nearest neighbors of who is closest to you and their study windows. You can then create a study session and add nearest neighbors to form study pods. Your nearest neighbors can be individuals or study groups.
- `Problem to solve`
  - Studying as a group is so much better but it is so hard to coordinate one!
- `Functionality`
  - User can get a list of nearest neighbors of who is closest to where you are 
  - Notification Functionality
    - No need to worry where your friend is!
    - Need to get the best alert timing
      - one alert for ETA
      - second alert for actual arrival
  - Time Estimation Functionality
    - use Google Maps to estimate based on users route
- `Critical Features`
  - Incorporating Google API
  - Getting the GPS data of the users
  - Processing user feedbacks
    - these will be placed in all major study spaces


### Idea 2: An Online Photo Gallery for Artists
- `Overview`
  -  Artists online don’t have much control over how their online portfolios appear. 
Solution: This project would solve this problem by creating an app or website that allows artists to create their own 2D or 3D gallery space to display their art (whichever is more of a possibility) for others to view. Artists would first be able to design a custom gallery space by changing the colors of the walls, adding text, choosing the size… They would then be able to upload images, sound art, or video art, wherever they wish in the room they designed.
- `Functionality`
  - Sign-in/log-in functionality
    - This is being included so that only the artist/creator or the room can edit the parameters of the gallery
  - Creating and designing their own gallery (editing parameters of the room, and placing art into it)
  - Setting the room as public or private, so that viewers can browse through all public rooms, or enter a code to see a private room.
    - Some artists would like to have more of a say as to who gets to see their artwork, because art is pretty personal.

___

## **Team Strengths and Weaknesses:**

1. __Amanda__
   - Strength: Project Management, Algorithms 
   - Weakness: JavaScript, HTML, and writing tests
2. __Kenta__
   - Strength: OOP, Algorithms, Front-end
   - Weakness: Writing Tests, CSS
3. __Yuki__
   - Strength: Chrome Extension
   - Weakness: Writing Tests, Async JS
4. __Emma__
   - Strength: Algorithms, OOP, Leadership
   - Weakness: Writing Tests, Front-end
5. __Sylvie__
   - Strength: Writing Tests, OOP, HTML, documentation
   - Weakness: Algorithms
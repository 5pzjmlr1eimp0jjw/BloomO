package edu.brown.cs.cs32friends.zones;

import com.google.gson.JsonObject;

public class Zone {
    public String zone = "";
    public String avgLastFrost = "";
    public String avgFirstFrost = "";
    public String avgTempLow = "";
    public String avgTempHigh = "";
    public String avgGrowingDays = "";
    public String listOfStates = "";
    public boolean hasFreeze = true;


    // define the getters and setters for the field variables
    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setZoneSpecificInfo(){
        if (this.zone.equals("0a") || this.zone.equals("0b")){
            this.avgLastFrost = "All Year Round";
            this.avgFirstFrost = "All Year Round";
            if (this.zone.equals("0a")){
                this.avgTempLow = "<-65°F/-53.9°C";
            }
            else {
                this.avgTempLow = "-65°F/-53.9°C";
                this.avgTempHigh = "-60°F/-51.1°C";
            }
            this.avgGrowingDays = "No data. Zone is too cold to grow.";
            this.listOfStates = "No state in this zone.";
        }

        else if (this.zone.equals("1a") || this.zone.equals("1b")){
            this.avgLastFrost = "Jun 15";
            this.avgFirstFrost = "Jul 15";
            if (this.zone.equals("1a")){
                this.avgTempLow = "-60°F/-51.1°C";
                this.avgTempHigh = "-55°F/-48.3°C";
            }
            else {
                this.avgTempLow = "-55°F/-48.3°C";
                this.avgTempHigh = "-50°F/-45.6°C";
            }
            this.avgGrowingDays = "30";
            this.listOfStates = "Alaska";
        }

        else if (this.zone.equals("2a") || this.zone.equals("2b")){
            this.avgLastFrost = "May 15";
            this.avgFirstFrost = "Aug 15";
            if (this.zone.equals("2a")){
                this.avgTempLow = "-50°F/-45.6°C";
                this.avgTempHigh = "-45°F/-42.8°C";
            }
            else {
                this.avgTempLow = "-45°F/-42.8°C";
                this.avgTempHigh = "-40°F/-40°C";
            }
            this.avgGrowingDays = "90";
            this.listOfStates = "Alaska";
        }

        else if (this.zone.equals("3a") || this.zone.equals("3b")){
            this.avgLastFrost = "May 15";
            this.avgFirstFrost = "Sep 15";
            if (this.zone.equals("3a")){
                this.avgTempLow = "-40°F/-40°C";
                this.avgTempHigh = "-35°F/-37.2°C";
            }
            else {
                this.avgTempLow = "-35°F/-37.2°C";
                this.avgTempHigh = "-30°F/-34.4°C";
            }
            this.avgGrowingDays = "120";
            this.listOfStates = "Alaska, Colorado, Idaho, Maine, Minnesota, Montana, New Hampshire, New York, North Dakota, Vermont, Wisconsin, Wyoming";
        }

        else if (this.zone.equals("4a") || this.zone.equals("4b")){
            this.avgLastFrost = "May 10";
            this.avgFirstFrost = "Sep 15";
            if (this.zone.equals("4a")){
                this.avgTempLow = "-30°F/-34.4°C";
                this.avgTempHigh = "-25°F/-31.7°C";
                this.listOfStates = "Alaska, Arizona, Colorado, Idaho, Iowa, Maine,Michigan, Minnesota, Montana, Nebraska, Nevada, New Hampshire, New Mexico, New York, North Dakota, Oregon, Utah, Vermont, Washington, Wisconsin, Wyoming";
            }
            else {
                this.avgTempLow = "-25°F/-31.7°C";
                this.avgTempHigh = "-20°F/-28.9°C";
            }
            this.avgGrowingDays = "125";
        }

        else if (this.zone.equals("5a") || this.zone.equals("5b")){
            this.avgLastFrost = "Apr 30";
            this.avgFirstFrost = "Oct 15";
            if (this.zone.equals("5a")){
                this.avgTempLow = "-20°F/-28.9°C";
                this.avgTempHigh = "-15°F/-26.1°C";
            }
            else {
                this.avgTempLow = "-15°F/-26.1°C";
                this.avgTempHigh = "-10°F/-23.3°C";
            }
            this.avgGrowingDays = "165";
            this.listOfStates = "Alaska, Arizona, California, Colorado, Connecticut, Rhode Island, Idaho, Illinois, Indiana, Iowa, Kansas, Maine, Maryland, Massachusetts, Michigan, Minnesota, Missouri, Montana, Nebraska, Nevada, New Hampshire, New Mexico, New York, North Carolina, Ohio, Oregon, Pennsylvania, Tennessee, Utah, Vermont, Virginia, Washington, West Virginia, Wisconsin, Wyoming";
        }

        else if (this.zone.equals("6a") || this.zone.equals("6b")){
            this.avgLastFrost = "Apr 15";
            this.avgFirstFrost = "Oct 15";
            if (this.zone.equals("6a")){
                this.avgTempLow = "-10°F/-23.3°C";
                this.avgTempHigh = "-5°F/-20.6°C";
            }
            else {
                this.avgTempLow = "-5°F/-20.6°C";
                this.avgTempHigh = "0°F/-17.8°C";
            }
            this.avgGrowingDays = "180";
            this.listOfStates = "Alaska, Arizona, California, Colorado, Connecticut, Rhode Island, Georgia, Idaho, Illinois, Indiana, Iowa, Kansas, Kentucky, Maine, Maryland, Massachusetts, Michigan, Missouri, Montana, Nevada, New Hampshire, New Jersey, New Mexico, New York, North Carolina, Ohio, Oklahoma, Oregon, Pennsylvania, Tennessee, Texas, Utah, Virginia, Washington, West Virginia, Wyoming";
        }

        else if (this.zone.equals("7a") || this.zone.equals("7b")){
            this.avgLastFrost = "Apr 15";
            this.avgFirstFrost = "Oct 15";
            if (this.zone.equals("7a")){
                this.avgTempLow = "0°F/-17.8°C";
                this.avgTempHigh = "5°F/-15°C";
            }
            else {
                this.avgTempLow = "5°F/-15°C";
                this.avgTempHigh = "10°F/-12.2°C";
            }
            this.avgGrowingDays = "180";
            this.listOfStates = "Alaska, Alabama, Arizona, Arkansas, California, Colorado, Connecticut, Rhode Island, Delaware, Georgia, Idaho, Illinois, Kansas, Kentucky, Maryland, Massachusetts, Mississippi, Missouri, Nevada, New Jersey, New Mexico, New York, North Carolina, Oklahoma, Oregon, Pennsylvania, South Carolina, Tennessee, Texas, Utah, Virginia, Washington, West Virginia, Wisconsin, Wyoming";
        }

        else if (this.zone.equals("8a") || this.zone.equals("8b")) {
            this.avgLastFrost = "Mar 10";
            this.avgFirstFrost = "Nov 15";
            if (this.zone.equals("8a")){
                this.avgTempLow = "10°F/-12.2°C";
                this.avgTempHigh = "15°F/-9.4°C";
            }
            else {
                this.avgTempLow = "15°F/-9.4°C";
                this.avgTempHigh = "20°F/-6.7°C";
            }
            this.avgGrowingDays = "245";
            this.listOfStates = "Alaska, Alabama, Arizona, Arkansas, California, Florida, Georgia, Louisiana, Maryland, Mississippi, Nevada, New Mexico, North Carolina, Oklahoma, Oregon, South Carolina, Tennessee, Texas, Utah, Virginia, Washington";
        }
        
        else if (this.zone.equals("9a") || this.zone.equals("9b")) {
            this.avgLastFrost = "Feb 15";
            this.avgFirstFrost = "Dec 15";
            if (this.zone.equals("9a")){
                this.avgTempLow = "20°F/-6.7°C";
                this.avgTempHigh = "25°F/-3.9°C";
            }
            else {
                this.avgTempLow = "25°F/-3.9°C";
                this.avgTempHigh = "30°F/-1.1°C";
            }
            this.avgGrowingDays = "265";
            this.listOfStates = "Alabama, Arizona, California, Florida, Georgia, Hawaii, Louisiana, Mississippi, Nevada, New Mexico, Oregon, South Carolina, TexasUtah, Washington";
        }
        
        else if (this.zone.equals("10a") || this.zone.equals("10b")){
            this.avgLastFrost = "Jan 20";
            this.avgFirstFrost = "Dec 20";
            if (this.zone.equals("10a")){
                this.avgTempLow = "30°F/-1.1°C";
                this.avgTempHigh = "35°F/1.7°C";
            }
            else {
                this.avgTempLow = "35°F/1.7°C";
                this.avgTempHigh = "40°F/4.4°C";
            }
            this.avgGrowingDays = "335";
            this.listOfStates = "Arizona, California, Florida, Hawaii, Louisiana, Nevada, Texas ";
        }

        else{
            this.avgLastFrost = "No Freeze";
            this.avgFirstFrost = "No Freeze";
            this.avgGrowingDays = "365";
            this.hasFreeze = false;
            this.avgTempHigh = "40°F/4.4°C or higher";
            this.listOfStates = "California, Florida, Hawaii";
        }
    }

    public String getZone() {
        return this.zone;
    }
      
    // define the toJson method to be used in the serialization of the zone object
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("zone", this.zone);
        json.addProperty("avgLastFrost", this.avgLastFrost);
        json.addProperty("avgFirstFrost", this.avgFirstFrost);
        json.addProperty("avgGrowingDays", this.avgGrowingDays);
        json.addProperty("avgTempLow", this.avgTempLow);
        json.addProperty("avgTempHigh", this.avgTempHigh);
        json.addProperty("listOfStates", this.listOfStates);
        return json;
    }


//    /**
//     *
//     * @param z Zone
//     * @param hardier 1: more hardy, 2: less hardy
//     * @return if is hardier, less hardy
//     */
//    public boolean compareHardiness(Zone z, int hardier) {
//        if (z == null){
//            return true;
//        }
//        if (hardier == 1 && this.zoneNumber() >= z.zoneNumber()){ // is input is in higher hardiness zone?
//            return (this.zoneChar() >= z.zoneChar());
//        }
//        else if (hardier == 0 && this.zoneNumber() <= z.zoneNumber()){ // is input is in lower hardiness zone?
//            return (this.zoneChar() <= z.zoneChar());
//        }
//        else return false;
//    }

    // public void constructZoneInformationSentence(){
    //     if (hasFreeze){
    //         zoneSentence = "Your hardiness zone is very useful! Specifically, hardiness zone " + " means that your average " +
    //             "last frost will be on " + avgLastFrost + ", and that your " +
    //             "first average frost will be on " + avgFirstFrost + ". Therefore, you'll have " +
    //             avgGrowingDays + " average growing days.";
    //     }
    //     else {
    //         zoneSentence = "This hardiness zone means that on average, you will " +
    //             "not have any days below freezing. Therefore, you'll have " + avgGrowingDays +
    //             "average growing days. Wow!";
    //     }
    // }

}

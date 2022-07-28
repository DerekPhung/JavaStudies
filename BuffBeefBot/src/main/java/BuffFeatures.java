import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuffFeatures {


    ArrayList<Lifter> lifterList = new ArrayList<>();
    IOFileSetup io = new IOFileSetup();
    static Path dataLog = Paths.get("src/main/resources/data.txt");
    HashMap<String, String> fullDataMap = new HashMap<>();

    public BuffFeatures(){
        loadingLifters();
    }

    public void loadingLifters(){
        //adding all data from txt to hashmap
        for(String e : io.readFromFile(dataLog)){
            String name = e.split("=")[0];
            String allData = e.split("=")[1];
            fullDataMap.put(name,allData);
        }
    }

    // method to take care of certain commands
    public String weightUpdate(String category, String username, String message){
        int lift = -1;
        if(category.equals("squat")){lift=0;}
        if(category.equals("bench")){lift=1;}
        if(category.equals("deadlift")){lift=2;}

        int squat = 0;
        int bench = 0;
        int deadlift = 0;
        int weightInput = 0;


        //if the username does not exist then we make an entree for him
        if(!fullDataMap.containsKey(username)) {
            fullDataMap.put(username,squat + "," + bench + "," + deadlift);
        }

        //squat, bench , deadlift is set to current user's lift before the update
        squat = Integer.parseInt( fullDataMap.get(username).split(",")[0] );
        bench = Integer.parseInt( fullDataMap.get(username).split(",")[1] );
        deadlift = Integer.parseInt( fullDataMap.get(username).split(",")[2] );

        //uses the checkStats method
        if(category.equals("checkStats")){
            return checkStats(username,message,squat,bench,deadlift);
        }

        // looking at everyone
        if(category.equals("all")){
            String all = "";
            for (Map.Entry<String, String> entry : fullDataMap.entrySet()) {
                squat = Integer.parseInt( entry.getValue().split(",")[0] );
                bench = Integer.parseInt( entry.getValue().split(",")[1] );
                deadlift = Integer.parseInt( entry.getValue().split(",")[2] );
                all += String.format("__**%s**__\n  **Squat**: %slb\n  **Bench**: %slb\n  **Deadlift**: %slb\n" +
                                "   **Total**: %slb\n\n",
                        entry.getKey(),squat,bench,deadlift,(squat+bench+deadlift));
            }
            return all;
        }

        //convert user message into a number and then catching any mistakes also guard against absurdity
        try {
            weightInput = Integer.parseInt(message.split(" ")[1]);
            if(weightInput < 0 || weightInput > 5000) return "Bad Human! Bad!";
        } catch (Exception e){
            return "Bad Human! Bad!";
        }

        //update specific category
        switch (lift){
            case 0: squat = weightInput; break;
            case 1: bench = weightInput; break;
            case 2: deadlift = weightInput; break;
            default: return "something went wrong";
        }

        //adding data before the database to update the data
        String allWeights = squat + "," + bench + "," + deadlift;
        fullDataMap.put(username,allWeights);

        //create arraylist then through a loop add Hashmap into the arraylist
        ArrayList<String> neoListToWrite = new ArrayList<>();
        for (Map.Entry<String, String> entry : fullDataMap.entrySet()) {
            neoListToWrite.add(entry.getKey()+"="+entry.getValue());
        }

        //writing the arraylist into textfile
        io.writeToFile(dataLog, neoListToWrite);

        //once all process is done send a feedback back to the user
        loadingLifters();
        return "Database has been updated.";
    }

    //for checking stats
    public String checkStats(String username, String message, int squat, int bench, int deadlift){
        int total = (squat+bench+deadlift);
        switch(message.toLowerCase()){
            case"checkstats":
                return String.format("__**%s**__\n**Squat** is: %slb\n" +
                                "**Bench** is: %slb\n**Deadlift** is: %slb\n**Total** is: %slb",
                        username,squat,bench,deadlift,total);
            case "checkbench":
                return "Bench Max is " + bench + "lb";
            case "checksquat":
                return "Squat Max is " + squat + "lb";
            case "checkdeadlift":
                return "Deadlift Max is " + deadlift + "lb";
            case "checktotal":
                return "Total max is " + total + "lb";
        }
        return "Doesn't work yet!";
    }




}

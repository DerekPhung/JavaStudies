import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class DiscordBot extends ListenerAdapter {

    static Path tokenPath = Paths.get("src/main/resources/token.txt");
    static Path dataLog = Paths.get("src/main/resources/data.txt");
    static Path testerLog = Paths.get("src/main/resources/dummyData.txt");
    Charset utf8 = StandardCharsets.UTF_8;


    public void createAFile(Path newPath) throws IOException {
        if( Files.exists(newPath) ) return;
            Files.createFile(newPath);
    }

    // reads from file
    public static List<String> readFromFile( Path path ) {
        try {
            return Files.readAllLines( path );
        } catch (IOException e){
            System.out.println("Error: readFromFile failed");
            return new ArrayList<>();
        }

    }

    //write to file
    public void writeToFile(Path newPath, ArrayList<String> list) throws IOException {
        createAFile(newPath);
        try {
            Files.write(
                    newPath, list,
                    utf8, StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e){
            System.out.println("Error: writeToFile failed");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws LoginException {

        String token = readFromFile(tokenPath).get(0);

        JDA bot = JDABuilder.createDefault(token)
                .setActivity(Activity.watching("Buff Beef Boys"))
                .addEventListeners(new DiscordBot())
                .build();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();
        String username = event.getAuthor().getName();
        System.out.println(message);

        // guard clause to check prefix then removing prefix
        if( !message.startsWith(">") ) return;
        message = message.substring(1);

        //bot commands
        if (message.toLowerCase().startsWith("bench:")){
            event.getMessage().reply(weightUpdate("bench", username, message)).queue();
        }
        if (message.toLowerCase().startsWith("squat:")){
            event.getMessage().reply(weightUpdate("squat", username, message)).queue();
        }
        if (message.toLowerCase().startsWith("deadlift:")){
            event.getMessage().reply(weightUpdate("deadlift", username, message)).queue();
        }

        if(message.startsWith("check ")){
            try{
                String otherUser = message.split(" ")[1];
                event.getMessage().reply(weightUpdate("checkStats",otherUser,"checkStats")).queue();
                return;
            }catch (Exception e){
                event.getMessage().reply("Bad Human! Bad!").queue();
            }
        }

        if(message.startsWith("check")){
            event.getMessage().reply(weightUpdate("checkStats",username,message)).queue();
        }
        if(message.equalsIgnoreCase("postall")){
            event.getMessage().reply(weightUpdate("all",username,message)).queue();
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

        HashMap<String, Integer> dataMap = new HashMap<>();

        //experiment hashmap below
        HashMap<String, String> fullDataMap = new HashMap<>();

        for(String e : readFromFile(dataLog)){
            String name = e.split("=")[0];
            String allData = e.split("=")[1];
            fullDataMap.put(name,allData);
        }

        if(!fullDataMap.containsKey(username)) {
            fullDataMap.put(username,squat + "," + bench + "," + deadlift);
        }

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
                all += String.format("%s\n  Squat: %s\n  Bench: %s\n  Deadlift: %s\n   Total: %s\n\n",
                        entry.getKey(),squat,bench,deadlift,(squat+bench+deadlift));
            }
            return all;
        }

        //catching user mistakes
        try {
            weightInput = Integer.parseInt(message.split(" ")[1]);
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

        //adding data before the database
        String allWeights = squat + "," + bench + "," + deadlift;
        fullDataMap.put(username,allWeights);

        //create arraylist then add Hashmap into it to be written into the textfile
        ArrayList<String> neoListToWrite = new ArrayList<>();
        for (Map.Entry<String, String> entry : fullDataMap.entrySet()) {
            neoListToWrite.add(entry.getKey()+"="+entry.getValue());
        }

        try{
            writeToFile(dataLog, neoListToWrite);
        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println(username + ": " + weightInput);
        System.out.println(dataMap);

        return "Database has been updated.";
    }

    //for checking stats
    public String checkStats(String username, String message, int squat, int bench, int deadlift){
        int total = (squat+bench+deadlift);
        switch(message.toLowerCase()){
            case"checkstats":
                return String.format("%s\nSquat is: %s\nBench is: %s\nDeadlift is: %s\nTotal is: %s",
                        username,squat,bench,deadlift,total);
            case "checkbench":
                return "Bench Max is " + bench;
            case "checksquat":
                return "Squat Max is " + squat;
            case "checkdeadlift":
                return "Deadlift Max is " + deadlift;
            case "checktotal":
                return "Total max is " + total;
        }
        return "Doesn't work yet!";
    }


}

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class DiscordBot extends ListenerAdapter {


    HashMap<String, String> fullDataMap = new HashMap<>();

    ArrayList<Lifter> lifterList = new ArrayList<>();

    static Path tokenPath = Paths.get("src/main/resources/token.txt");
    static Path dataLog = Paths.get("src/main/resources/data.txt");
    Charset utf8 = StandardCharsets.UTF_8;


    // if a file doesnt exist it will create a file
    public void createAFile(Path newPath) {
        if( Files.exists(newPath) ) return;
        try {
            Files.createFile(newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void writeToFile(Path newPath, ArrayList<String> list) {
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

    //main
    public static void main(String[] args) throws LoginException {

        String token = readFromFile(tokenPath).get(0);

        JDA bot = JDABuilder.createDefault(token)
                .setActivity(Activity.watching("Buff Beef Boys"))
                .addEventListeners(new DiscordBot())
                .build();
    }

    //
    public void convertToHashMap(){

        //adding all data from txt to hashmap
        for(String e : readFromFile(dataLog)){
            String name = e.split("=")[0];
            String weightData = e.split("=")[1];
            lifterList.add( new Lifter( name,
                    Integer.parseInt(weightData.split(",")[0]),
                    Integer.parseInt(weightData.split(",")[1]),
                    Integer.parseInt(weightData.split(",")[2])
            ));
        }

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();
        String username = event.getAuthor().getName();
        System.out.println(message);

        // guard clause to check prefix then removing prefix
        if( !message.startsWith(">") ) return;
        message = message.substring(1);

        //adding all data from txt to hashmap
        for(String e : readFromFile(dataLog)){
            String name = e.split("=")[0];
            String allData = e.split("=")[1];
            fullDataMap.put(name,allData);
        }

        //bot commands
        if(message.equalsIgnoreCase("help")){
            event.getMessage().reply(help()).queue();
        }
        if(message.equalsIgnoreCase("aboutme")){
            event.getMessage().reply(aboutMe()).queue();
        }
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
        writeToFile(dataLog, neoListToWrite);

        //once all process is done send a feedback back to the user
        return "Database has been updated.";
    }

    //for checking stats
    public String checkStats(String username, String message, int squat, int bench, int deadlift){
        int total = (squat+bench+deadlift);
        switch(message.toLowerCase()){
            case"checkstats":
                return String.format("__**%s**__\nSquat is: **%s**lb\n" +
                                "Bench is: **%s**lb\nDeadlift is: **%s**lb\nTotal is: **%s**lb",
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

    // a message about each feature the bot has
    public String help(){
        return "```prefix is > \n" +
                "(aboutMe) to read random stuff\n" +
                "(bench: number) your bench to update your bench\n" +
                "(squat: number) your squat to update your squat\n" +
                "(deadlift: number) your deadlift to udpate your deadlift\n" +
                "(checkStats) to check your own stats\n" +
                "(checkBench) to check your bench\n" +
                "(checkSquat) to check your squats\n" +
                "(checkDeadlift) to check your deadlift\n" +
                "(checkTotal) to check your total\n" +
                "(check username) to check someone's stats\n" +
                "(postAll) to show everybody in the database```";
    }

    // a message from the bot creator
    public String aboutMe(){
        return "```Yo!\n" +
                "So this bot is made by Fluffy and Tubbie\n" +
                "currently the bot only have __**Buff Beef Boys**__ functionality in mind\n" +
                "which means it's main functionality is keeping track of power lifting max\n" +
                "you can contact either Fluffywin or TubbieTubTub for more functionality/features\n" +
                "if the bot is down then that means I finally turned off my computer\n" +
                "or someone crashed the bot while I am away from the computer\n" +
                "hopefully when I finally get my hands on raspberry pi but will be up consistently```";
    }


}

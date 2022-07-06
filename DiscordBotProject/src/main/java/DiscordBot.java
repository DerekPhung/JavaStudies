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
        ArrayList<String> data = new ArrayList<>( readFromFile(dataLog) );
        HashMap<String, Integer> dataMap = new HashMap<>();
        HashMap<String, HashMap<String,Integer> > fullDataMap = new HashMap<>();

        for (String e : data) {
            dataMap.put(e.split("=")[0], Integer.parseInt(e.split("=")[1]));
        }

        int weight = 0;

        String message = event.getMessage().getContentRaw();
        String username = event.getAuthor().getName();
        System.out.println(message);

        // guard clause
        if( !message.startsWith(">") ) return;

        message = message.substring(1);

        if (message.toLowerCase().startsWith("bench:")){

            ArrayList<String> listToWrite = new ArrayList<>();

            try {
                weight = Integer.parseInt(message.split(" ")[1]);

            } catch (NumberFormatException e){
                event.getMessage().reply("Bad Human! Bad!").queue();
            }

                dataMap.put(username,weight);

                for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
                    listToWrite.add(entry.getKey() + "=" + entry.getValue());
                }

                try {
                    writeToFile(dataLog, listToWrite);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(username + ": " + weight);
                System.out.println(dataMap);


        }

        if(message.startsWith("checkBench")){
            event.getMessage().reply("Bench: " + dataMap.get(username)).queue();
        }
    }



}

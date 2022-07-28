import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DiscordBot extends ListenerAdapter {

    static IOFileSetup io = new IOFileSetup();
    static Path tokenPath = Paths.get("src/main/resources/token.txt");
    static String token = io.readFromFile(tokenPath).get(0);
    BuffFeatures buffFeatures = new BuffFeatures();


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();
        String username = event.getAuthor().getName();
        System.out.println(message);

        // guard clause to check prefix then removing prefix
        if( !message.startsWith(">") ) return;
        message = message.substring(1);


        //bot commands
        if(message.equalsIgnoreCase("help")){
            event.getMessage().reply(help()).queue();
        }
        if(message.equalsIgnoreCase("aboutme")){
            event.getMessage().reply(aboutMe()).queue();
        }
        if (message.toLowerCase().startsWith("bench:")){
            event.getMessage().reply(buffFeatures.weightUpdate("bench", username, message)).queue();
        }
        if (message.toLowerCase().startsWith("squat:")){
            event.getMessage().reply(buffFeatures.weightUpdate("squat", username, message)).queue();
        }
        if (message.toLowerCase().startsWith("deadlift:")){
            event.getMessage().reply(buffFeatures.weightUpdate("deadlift", username, message)).queue();
        }


        // purging command (>delete: 50) will delete 50 messages
        if(message.toLowerCase().startsWith("delete: ")){
            event.getMessage().delete();
            try{
                int num = Integer.parseInt(message.split(" ")[1]) + 1;
                if(num < 1 || num > 100){
                    event.getMessage().reply("can only be between 1 - 98").queue();
                    return;
                }
                //this grabs the history of each messages on the channel
                List<Message> msgList = event
                        .getMessage()
                        .getChannel()
                        .getHistory()
                        .retrievePast(num)
                        .complete();

                System.out.println(msgList);

                event.getChannel().purgeMessages(msgList);

                //legacy code below
                //event.getTextChannel().deleteMessages(msgList).queue();

                event.getChannel()
                        .sendMessage (event.getAuthor().getName() + " " +(num-1) + " messages deleted")
                        .queue();
                return;

            }catch (Exception e){
                event.getMessage().reply("Bad! Human Bad!").queue();
                e.printStackTrace();
            }

        }

        // checking other people's stats
        if(message.startsWith("check ")){
            try{
                String otherUser = message.split(" ")[1];
                event.getMessage().reply(buffFeatures.weightUpdate("checkStats",otherUser,"checkStats")).queue();
                return;
            }catch (Exception e){
                event.getMessage().reply("Bad Human! Bad!").queue();
            }
        }

        // checking your own stats
        if(message.startsWith("check")){
            event.getMessage().reply(buffFeatures.weightUpdate("checkStats",username,message)).queue();
        }
        if(message.equalsIgnoreCase("postall")){
            event.getMessage().reply(buffFeatures.weightUpdate("all",username,message)).queue();
        }
    }


    // a message about each feature the bot has
    public String help(){
        return "```prefix is > \n" +
                "(>aboutMe) to read random stuff\n" +
                "(>bench: number) your bench to update your bench\n" +
                "(>squat: number) your squat to update your squat\n" +
                "(>deadlift: number) your deadlift to udpate your deadlift\n" +
                "(>checkStats) to check your own stats\n" +
                "(>checkBench) to check your bench\n" +
                "(>checkSquat) to check your squats\n" +
                "(>checkDeadlift) to check your deadlift\n" +
                "(>checkTotal) to check your total\n" +
                "(>check username) to check someone's stats\n" +
                "(>postAll) to show everybody in the database\n" +
                "(>delete: num) will purge the number of messages```";
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

    //main
    public static void main(String[] args) throws LoginException {

        JDA bot = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.watching("Buff Beef Boys"))
                .addEventListeners(new DiscordBot())
                .build();
    }


}

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IOFileSetup {


    Charset utf8 = StandardCharsets.UTF_8;


    // reads from file
    public static List<String> readFromFile(Path path ) {
        try {
            return Files.readAllLines( path );
        } catch (IOException e){
            System.out.println("Error: readFromFile failed");
            return new ArrayList<>();
        }
    }

    // if a file doesnt exist it will create a file
    public void createAFile(Path newPath) {
        if( Files.exists(newPath) ) return;
        try {
            Files.createFile(newPath);
        } catch (IOException e) {
            e.printStackTrace();
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




}

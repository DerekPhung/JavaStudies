package Intermediate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteStudies {

    Path path = Paths.get("src/resources/names.txt");
    Charset utf8 = StandardCharsets.UTF_8;

    Path path1 = Paths.get("src/resources/firstNames.txt");
    Path path2 = Paths.get("src/resources/lastNames.txt");


    public void createAFile(Path newPath) throws IOException {
        if( !Files.exists(newPath) )
            Files.createFile(newPath);
    }

    // reads from file
    public List<String> readFromFile() {
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
                    utf8, StandardOpenOption.APPEND
            );
        } catch (IOException e){
            System.out.println("Error: writeToFile failed");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {

        ReadWriteStudies mt = new ReadWriteStudies();
        ArrayList<String> nameList = (ArrayList<String>) mt.readFromFile();

        System.out.println(nameList);

        ArrayList<String> firstNames = new ArrayList<>();
        ArrayList<String> lastNames = new ArrayList<>();

        for(String name : nameList){
            firstNames.add(name.split(" ")[0]);
            lastNames.add(name.split(" ")[1]);
        }
        mt.writeToFile(mt.path1, firstNames);
        mt.writeToFile(mt.path2, lastNames);

    }
}

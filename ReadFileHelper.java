import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

//Reader class to read the commands from the file provided to the function readFile as input

public class ReadFileHelper {

    public List<String> readFile(File inputFile){
        List<String> listOfCommands=new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                listOfCommands.add(line.replaceAll("\\s",""));                  //read command by trimming all the spaces in the command
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listOfCommands;
    }
}

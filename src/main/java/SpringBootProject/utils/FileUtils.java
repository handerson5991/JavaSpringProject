package SpringBootProject.utils;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FileUtils {

    public String readFromFile(File file) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            StringBuffer sb = new StringBuffer();

            while ((str = in.readLine()) != null) {
                sb.append(str + "\n ");
            }
            in.close();
            return sb.toString();
        } catch (IOException ie) {
            System.out.println(ie.getMessage());
        }
        return "Could not read from file";
    }

    public void writeToFile(File file, String writeString) {
        try {
            FileWriter output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write(writeString);
            writer.flush();
            writer.close();
        } catch (IOException ie) {
            System.out.println(ie.getMessage());
        }
    }
}

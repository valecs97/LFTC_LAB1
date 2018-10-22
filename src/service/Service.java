package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Service {

    public String readFromFile(String fileLocation) throws FileNotFoundException {
        File file = new File(fileLocation);
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\\Z");
        String code = sc.next();
        sc.close();

        return code;
    }
}

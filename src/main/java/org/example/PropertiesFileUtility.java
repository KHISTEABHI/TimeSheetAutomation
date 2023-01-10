package org.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class PropertiesFileUtility {
	
	public static Properties prop;

    public static Properties readPropertiesFile() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter path for the properties file : ");
        String propertiesFilePath = scan.nextLine();
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(propertiesFilePath);
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.\nPlease check the path");
        }
        PropertiesFileUtility.prop=prop;
        return prop;
    }


}

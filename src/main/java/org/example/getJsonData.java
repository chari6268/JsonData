package org.example;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class getJsonData {

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            File file = new File("C:\\Users\\srini\\IdeaProjects\\JsonData\\src\\main\\java\\org\\example\\data.json");
            if (!file.exists()) {
                System.out.println("File not found: " + file.getAbsolutePath());
                return;
            }
            // Read JSON file (can be replaced with network response as string)
            Object obj = parser.parse(new FileReader(file));
            // Convert Object to JSONObject
            JSONObject jsonObject = (JSONObject) obj;

            // Parse JSON data
            JSONArray subjectsArray = (JSONArray) jsonObject.get("Subjects");
            JSONArray attendedArray = (JSONArray) jsonObject.get("SubjectHoursAttended");
            JSONArray totalArray = (JSONArray) jsonObject.get("SubjectHoursTotal");
            JSONArray countArray = (JSONArray) jsonObject.get("SubjectCount");

            for (int i = 0; i < subjectsArray.size(); i++) {
                JSONObject subjectObj = (JSONObject) subjectsArray.get(i);
                JSONObject attendedObj = (JSONObject) attendedArray.get(i);
                JSONObject totalObj = (JSONObject) totalArray.get(i);
                JSONObject countObj = (JSONObject) countArray.get(i);

                int subjectCount = ((Long) countObj.get("Count")).intValue();
                for (int j = 0; j < subjectCount; j++) {
                    String subjectName = (String) subjectObj.get("Sub " + j);
                    String present = (String) attendedObj.get("Sub " + j);
                    String total = (String) totalObj.get("Sub " + j);

                    // Calculate percentage
                    double presentValue = Double.parseDouble(present);
                    double totalValue = Double.parseDouble(total);
                    String percentage = (totalValue > 0) ?
                            String.format("%.2f", (presentValue * 100) / totalValue) :
                            "0.00";

                    // Print the results
                    System.out.println("Subject: " + subjectName);
                    System.out.println("Present: " + present);
                    System.out.println("Total: " + total);
                    System.out.println("Percentage: " + percentage);
                    System.out.println("--------------------------------");
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}

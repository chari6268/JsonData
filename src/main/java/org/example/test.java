package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class test {

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            File file = new File("C:\\Users\\srini\\IdeaProjects\\JsonData\\src\\main\\java\\org\\example\\data.json");
            if (!file.exists()) {
                System.out.println("File not found: " + file.getAbsolutePath());
                return;
            }

            // Read JSON file
            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj;

            // Parse JSON data
            JSONArray subjectsArray = (JSONArray) jsonObject.get("Subjects");
            JSONArray SubjectCode = (JSONArray) jsonObject.get("SubjectCode");
            JSONArray attendedArray = (JSONArray) jsonObject.get("SubjectHoursAttended");
            JSONArray totalArray = (JSONArray) jsonObject.get("SubjectHoursTotal");
            JSONArray countArray = (JSONArray) jsonObject.get("SubjectCount");

            // Maps to hold subject and lab details
            Map<String, JSONObject> subjectMap = new HashMap<>();
            Map<String, JSONObject> labMap = new HashMap<>();

            for (int i = 0; i < subjectsArray.size(); i++) {
                JSONObject subjectObj = (JSONObject) subjectsArray.get(i);
                JSONObject subjectcodeObj = (JSONObject) SubjectCode.get(i);
                JSONObject attendedObj = (JSONObject) attendedArray.get(i);
                JSONObject totalObj = (JSONObject) totalArray.get(i);
                JSONObject countObj = (JSONObject) countArray.get(i);

                int subjectCount = ((Long) countObj.get("Count")).intValue();
                for (int j = 0; j < subjectCount; j++) {
                    String subjectCode = "Sub " + j;
                    String subjectCode1 = "SubCode " + j;

                    String subjectName = (String) subjectObj.get(subjectCode);
                    String subjectCODE = (String) subjectcodeObj.get(subjectCode1);
                    String present = (String) attendedObj.get(subjectCode);
                    String total = (String) totalObj.get(subjectCode);

                    // Determine if it's a lab
                    if (subjectCODE != null && subjectCODE.endsWith("A")) {
                        labMap.put(subjectCODE, new JSONObject());
                        labMap.get(subjectCODE).put("subjectName",subjectName);
                        labMap.get(subjectCODE).put("present", present);
                        labMap.get(subjectCODE).put("total", total);
                    } else {
                        subjectMap.put(subjectCODE, new JSONObject());
                        subjectMap.get(subjectCODE).put("present", present);
                        subjectMap.get(subjectCODE).put("subjectName",subjectName);
                        subjectMap.get(subjectCODE).put("total", total);
                    }
                }
            }

            // Combine and print details
            for (String subject : subjectMap.keySet()) {
                JSONObject subjectDetails = subjectMap.get(subject);
                System.out.println("Subject Code: " + subject);
                // Check if there is a corresponding lab
                String labSubject =subject + "A";
                if (labMap.containsKey(labSubject)) {
                    String present = (String) labMap.get(labSubject).get("present");
                    String total = (String) labMap.get(labSubject).get("total");
                    String code = (String) labMap.get(labSubject).get("subjectName");

                    // Calculate percentage
                    double presentValue = Double.parseDouble(present);
                    double totalValue = Double.parseDouble(total);
                    String percentage = (totalValue > 0) ?
                            String.format("%.2f", (presentValue * 100) / totalValue) :
                            "0.00";
                    subjectMap.get(subject).put("present",String.valueOf(Double.parseDouble((String) subjectMap.get(subject).get("present"))+presentValue));
                    subjectMap.get(subject).put("total",String.valueOf(Double.parseDouble((String) subjectMap.get(subject).get("total"))+totalValue));

                    printDetails(subjectDetails);
                }else{
                    printDetails(subjectDetails);
                }
                System.out.println("--------------------------------");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void printDetails(JSONObject details) {
        String present = (String) details.get("present");
        String total = (String) details.get("total");
        String code = (String) details.get("subjectName");

        // Calculate percentage
        double presentValue = Double.parseDouble(present);
        double totalValue = Double.parseDouble(total);
        String percentage = (totalValue > 0) ?
                String.format("%.2f", (presentValue * 100) / totalValue) :
                "0.00";

        // Print the results
        System.out.println("Subject Name: " + code);
        System.out.println("Present: " + present);
        System.out.println("Total: " + total);
        System.out.println("Percentage: " + percentage);
    }
}

package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import utils.Constants;

public class GetQueryResults { // static class
    
    private static boolean fileAccessFlag = false;
//===================================Private Methods==================================

    private static String read(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    private static void parseQueryObject(JSONObject query, List<Pair<String, String>> l) {
//        JSONObject queryObject = (JSONObject) query.get("nextPage");
        Pair<String, String> tmp = new Pair<>();

        // Get title
        String title = (String) query.get("title");
        tmp.setP1(title);

        // TODO Get image
//        String image = (String) query.get("image");
//        System.out.println(image);

        // Get link
        String link = (String) query.get("link");
        tmp.setP2(link);

        l.add(tmp);
    }


    private static JSONArray pullDataFromFile(int page) throws JSONException {
        // Read html returned from API call
        Document doc = Jsoup.parse(read(Constants.ABS_FILEPATH + Constants.FILENAME + page + ".json"));
        Element main = doc.select("body").first();
        Scanner bScan = new Scanner(main.text());
//        bScan.nextLine();

        // Build JSON

        String tmp = bScan.nextLine();
        tmp = tmp.replaceAll("<.*?>|\u00a0", ""); // longer queries work; shorter queries fail
        JSONObject json = new JSONObject(tmp);
        bScan.close();
        JSONArray queryResults = (JSONArray) json.get("items");
        return queryResults;
    }

    private static void write(String data, File file) {
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void processQuery(PostQueryJSoup pq) {
        System.out.print("Sending search query now..." /* TODO loading percentage */);

        pq.initializeConnection();
        pq.writeToFile();

        System.out.println("Raw data written to: " + pq.gettmpFilePath());
    }

//===================================Public Methods===================================

    public static void setup(int start, int end, String query) {
        fileAccessFlag = false;
        try {
            ArrayList<PostQueryJSoup> pqBigBoi = new ArrayList<>();
            for (int i = start - 1; i < end - 1; i++) {
                pqBigBoi.add(new PostQueryJSoup(query, new URL("http://google.com"), (10 * i) + 1));
            }
            for (PostQueryJSoup pq : pqBigBoi) {
                processQuery(pq);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray pullShell(int errCt, int page) throws TimeoutException{
        if (errCt <= 0) {
            throw new TimeoutException();
        }
        try {
            JSONArray ja = GetQueryResults.pullDataFromFile(page);
            return ja;
        } catch (JSONException e) {
            System.err.println("File err");
            return pullShell(errCt - 1, page);
        }
    }
    

    public static void writeToFileFormatted(JSONArray queryResults, int iter, int maxIter) {
        // Iterate over json array
        List<Pair<String, String>> l = new ArrayList<>();

        for (int i = 0; i < queryResults.length(); ++i) {
            JSONObject singleQuery = queryResults.getJSONObject(i);
            parseQueryObject(singleQuery, l);
        }

        File out = new File(Constants.ABS_FILEPATH + Constants.FILENAME + ".json");
        String str = "";
        if (iter == 1) {
            str += "{\n\t\"array\":[";
            PrintWriter writer;
            try {
                writer = new PrintWriter(out);
                writer.print("");
                writer.close();
            } catch (FileNotFoundException ignore) {
                try {
                    out.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < l.size(); i++) {
            str += l.get(i).toString();
            str += ",";
        }
        write(str, out);
        fileAccessFlag = true;
    }
    
    public static boolean cleanup(int start, int end) {
        for (int i = start; i < end; i++) {
            File f = new File(Constants.ABS_FILEPATH + Constants.FILENAME + i + "1.json");
            if(f.exists()){
                f.delete();
            }
        }
        if (fileAccessFlag) {
            File output = new File(Constants.ABS_FILEPATH + Constants.FILENAME + ".json");
            try {
                FileWriter fw = new FileWriter(output, true);
                fw.write("\n\t\t\"\"\n\t]\n}");
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Query Request Failed. Using previous Query...");
        }
        return fileAccessFlag;
    }
}
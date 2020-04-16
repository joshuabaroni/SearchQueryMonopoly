package model;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import utils.Constants;

public class PostQueryJSoup {

    private String query = "";

    // \/Google host keys: DO NOT CHANGE\/
    private final String CSID = "000592719178194040958:csxkw5jvtll";
    private final String API_KEY = "AIzaSyCipGqN15HgF-3o6VJAp5tnp9_VgrBWHwc";
    // /\Google host keys: DO NOT CHANGE/\

    private URL targetUrl;
    private JSONObject payload;
    private Document doc;
    private boolean connected = false;
    private int start = 1;
    private String tmpFilePath = "TEMPFILE NOT INITIALIZED";

//====================================Constructors====================================

    @Deprecated
    public PostQueryJSoup() {
        query = "HelloWorld";
        constructRestCall();
    }

    public PostQueryJSoup(String query, int start) {
        this.query = query;
        this.start = start;
        constructRestCall();
    }

    public PostQueryJSoup(URL url, int start) {
        this.targetUrl = url;
        this.start = start;
        constructRestCall();
    }

    public PostQueryJSoup(String query, URL url, int start) {
        this.query = query;
        this.targetUrl = url;
        this.start = start;
        constructRestCall();
    }

//===================================Private Methods==================================

    private void constructRestCall() {
        try {

            // HTML space char encoding
            Scanner scan = new Scanner(query);
            scan.useDelimiter(" ");
            query = "";
            while (scan.hasNext()) {
                query += scan.next() + "%20";
            }
            scan.close();

            targetUrl = new URL("https://www.googleapis.com/customsearch/v1?" + "key=" + API_KEY + "&cx=" + CSID + "&q="
                    + query + "&start=" + start);
            start += 10;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

//===================================Public Methods===================================

    public boolean initializeConnection() {
        try {
            HttpURLConnection urlConn = (HttpURLConnection) targetUrl.openConnection();
            String line = null;
            StringBuilder tmp = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            while ((line = in.readLine()) != null) {
                tmp.append(line);
            }

            doc = Jsoup.parse(tmp.toString());

            System.out.println("\nConnection to \n" + targetUrl + "\ninitialized." + "\nHTTP STATUS: "
                    + urlConn.getResponseCode() + " " + urlConn.getResponseMessage());
            if (urlConn.getResponseCode() >= 300) {
                System.err.println("QUERY REQUEST FAILED TO CONTACT THE SERVER");
            }
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean writeToFile() {
        if (!connected) {
            System.err.println("No connection established.");
            return false;
        }
        try {
            File raw = new File(Constants.ABS_FILEPATH + Constants.FILENAME + start + ".json");
            raw.getParentFile().mkdirs();
            raw.createNewFile();
            doc = Jsoup.parse(doc.toString());
            Element link = doc.select("body").first();
            Scanner scan = new Scanner(link.text());
            PrintWriter out = new PrintWriter(new FileWriter(raw, false));
            while (scan.hasNext()) {
                out.write(scan.next() + "\n\t");
            }
            tmpFilePath = raw.getAbsolutePath();
            out.close();
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
//===========================Getters, Setters, toString==============================

    public String gettmpFilePath() {
        return tmpFilePath;
    }

    @Override
    public String toString() {
        return "JSONPayload: " + payload.toString() + "\nRequestURL: " + targetUrl.toString() + "\nquery: "
                + query.toString();

    }
}

package controller;

import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;

import model.GetQueryResults;

import utils.Constants;

// EventListener HTML Tutorial:
// https://www.w3schools.com/code/tryit.asp?filename=GDEO9PUUR6CW

public class QueryApplication {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        try {
            System.out.println("Enter a search Query:");
            GetQueryResults.setup(Constants.START, Constants.END, new Scanner(System.in).nextLine());
            for (int i = Constants.START; i < Constants.END; i++) {
                JSONArray ja = GetQueryResults.pullShell(10, i * 10 + 1); // errsAllowed, pg
                GetQueryResults.writeToFileFormatted(ja, i, Constants.END);
            }
        } catch (TimeoutException ignore) {

        }
        GetQueryResults.cleanup(Constants.START, Constants.END);
    }
}

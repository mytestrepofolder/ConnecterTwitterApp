package com.test.twitter.main;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import com.test.twitter.authentication.TwitterAuthenticationException;
/**
 * Entry point for the app.
 * @author Plabon Kakoti
 * 
 *
 */
public class TwitterAppMain {

    public static void main(String[] args) throws TwitterAuthenticationException, IOException, FileNotFoundException {
    	
        TwitterApp application = new TwitterApp();
        String stringToTrack = null;
        stringToTrack = System.getProperty("search.name", "bieber");
        int maxMessages = getIntProperty("max.limit", 100);
        int trackingTime = getIntProperty("tracking.timeout", 30);
        application.authenticate(stringToTrack);       
        application.startTracking(stringToTrack, maxMessages, trackingTime);
        application.logsMessagesToLogByUser();
    }
        
    private static int getIntProperty(String paramName, int defaultValue) {

        String paramValue = System.getProperty(paramName, String.valueOf(defaultValue));

        try {
            return Integer.parseInt(paramValue);
        } catch (Exception e) {
        }

        return defaultValue;
    }
    
    
}

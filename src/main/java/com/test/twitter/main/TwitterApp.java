package com.test.twitter.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.test.twitter.authentication.TwitterAuthenticationException;
import com.test.twitter.main.TwitterAppStreamAPI;
import com.test.twitter.messages.TwitterMessageHandler;
import com.test.twitter.messages.TwitterMessageProcessor;
import com.test.twitter.messages.TwitterMessageRetriever;
import com.test.twitter.model.Twitt;
import com.test.twitter.model.TwitterUser;

/**
 * 
 * @author Plabon Kakoti
 *
 */
public class TwitterApp {

    private static final Logger logger = LoggerFactory.getLogger(TwitterApp.class);

    private ExecutorService messageRetrieverExService = Executors.newSingleThreadExecutor();

    private ExecutorService messageReaderExService = Executors.newSingleThreadExecutor();

    private List<Twitt> messages = new ArrayList<>();
    
    private PrintWriter pw = null;
    
    static FileWriter fw = null;

	private TwitterAppStreamAPI twitterReq;
/* 
 *     static {
    	try {

    		fw = new FileWriter("./" + "stdout.log",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/

    public void authenticate(String stringToTrack) throws TwitterAuthenticationException {

		twitterReq = new TwitterAppStreamAPI(System.out, stringToTrack);
		twitterReq.authenticate();
    }

    public void startTracking(String stringToTrack, int totalTwitts, int trackingTimeout) throws IOException ,TwitterAuthenticationException {
    	
    	//pw = new PrintWriter(fw,true);
    	SimpleDateFormat dtFt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	logger.debug("***** Tracking twitter on name : "+"'"+stringToTrack+"'"+". Tracking Started at :"+dtFt.format(new Date())+".");
    	logger.debug("***** Tracking the twitter for "+totalTwitts+" twitts or "+trackingTimeout+" seconds which ever comes first ***** ");
        logger.info("Tracking twitter on : {}", stringToTrack);
        logger.info("Looking for {} twitts or {} for 30 seconds", totalTwitts, trackingTimeout);

        HttpResponse httpResponse = twitterReq.executeFilterRequest();

        if (httpResponse.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {

            startTracking(httpResponse.getContent(), totalTwitts, trackingTimeout);

        } else {
            logger.error("Http response code: {}", httpResponse.getStatusCode());
            logger.error("Error message: {}", httpResponse.getStatusMessage());
        }

        httpResponse.disconnect();
         logger.debug("Disconnected from twitter!!!!");
         logger.debug("\n");
    }

    private void startTracking(InputStream source, int totalTwitts, int trackingTimeout) {

        LinkedBlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(totalTwitts);

        TwitterMessageRetriever messageRetriever = new TwitterMessageRetriever(source, messageQueue, totalTwitts, trackingTimeout);
        Future<Boolean> messageReaderResult = messageRetrieverExService.submit(messageRetriever);

        TwitterMessageHandler twitterMessageHandler = new TwitterMessageHandler(messageQueue);
        Future<List<Twitt>> messageDeserializationResult = messageReaderExService.submit(twitterMessageHandler);

        try {
            boolean receivedAllMessages = messageReaderResult.get();

            if (receivedAllMessages) {

                twitterMessageHandler.concludeProcessing();
                List<Twitt> deSerializedMessages = messageDeserializationResult.get();

                messages.addAll(deSerializedMessages);
                
            }

        } catch (Exception e) {
            logger.error("Error while tracking. Aborting tracking!", e);
        } finally {

            logger.info("Shutting down MessageRetriever");
            messageRetrieverExService.shutdown();

            logger.info("Shutting down TwitterMessageHandler");
            
            
            messageReaderExService.shutdown();
        }
    }

    public void logsMessagesToLogByUser() {

        TwitterMessageProcessor messageProcessor = new TwitterMessageProcessor(messages);
        SortedMap<TwitterUser, SortedSet<Twitt>> sortedMessages = messageProcessor.process();

        sortedMessages.forEach(printAuthorAndMessages);
        sortedMessages.forEach(printAuthorAndMessagesToFile);
        //pw.close();
    }

    private Consumer<Twitt> printMessage = (message) -> {
        System.out.println(message);
    };

    private BiConsumer<TwitterUser, SortedSet<Twitt>> printAuthorAndMessages = (author, authorMessages) -> {
		
        System.out.println("***************************");
        System.out.println("Twitts ordered By Users");
        System.out.println("***************************");
        System.out.println(author);
        authorMessages.forEach(printMessage);
        System.out.println("***************************");
    };
    
    
    private Consumer<Twitt> printMessageToFile = (message) -> {
       
    	 logger.debug("Message Id: " + message.getId() + "\n");
         logger.debug("{");
         logger.debug("Text: "+ message.getText());
         logger.debug("Author Id: "+ message.getAuthor());  
         logger.debug("Created Time: "+message.getCreatedDateEpoch());
         logger.debug("}");
        
    };
    
    private BiConsumer<TwitterUser, SortedSet<Twitt>> printAuthorAndMessagesToFile = (author, authorMessages) -> {
		
        	//fw = new FileWriter("C:/Twitter/stdout.txt");
    		//pw = new PrintWriter(fw,true);
        	 logger.debug("***************************");
	         logger.debug("Twitts ordered By Users");
	         logger.debug("***************************");	
	         logger.debug("Author Id: "+ author.getId() );
	         logger.debug("{");
	         logger.debug("Name: : "+ author.getScreenName());
	         logger.debug("Twitter Handle: "+author.getName());  
	         logger.debug("Created Time:  "+author.getCreatedAtEpoch());
	         logger.debug("}");
	         logger.debug("\n");
	         authorMessages.forEach(printMessageToFile);
	         logger.debug("\n");
	         logger.debug("***************************");
	        
    };
    
    
}

package com.test.twitter.messages;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Implementation of Callable  to retrieve messages from twitter stream. It retrieves messages for
 *  time keepTracking is true or maxMessageCount is reached which ever occurs first. It adds the messagess into messageQueue.
 * 
 * @author Plabon Kakoti
 *
 */
public class TwitterMessageRetriever implements Callable<Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(TwitterMessageRetriever.class);

    private InputStream source;

    private BlockingQueue<String> messageQueue;

    private int maxMessageCount;

    private int trackingTimeout;

    private boolean keepTracking = true;
    
    //private PrintWriter pw;
    

    public TwitterMessageRetriever(InputStream source, BlockingQueue<String> messageQueue, int maxMessages,
            int trackingTimeout) {
        this.source = source;
        this.messageQueue = messageQueue;
        this.maxMessageCount = maxMessages;
        this.trackingTimeout = trackingTimeout;
        //this.pw = pw; 
    }

    @Override
    public Boolean call() throws Exception {

        logger.info("Started retrieving twitts");
        logger.debug("***** Started retrieving twitts *******");

        int messagesReceived = 0;
        ExecutorService scheduledExecutor = setStopTimer();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(source));

            while ((messagesReceived != maxMessageCount) && keepTracking) {

                String message = reader.readLine();
                messageQueue.offer(message);
                messagesReceived++;
            }

        } catch (Throwable t) {
            logger.error("Exception while retrieving twitts", t);
        } finally {

            logger.info("***** Shutting down *****");
            scheduledExecutor.shutdownNow();
        }

        logger.info("***** Twitt retriving stopped *****");
        logger.debug("\n");
       
        logger.debug("Total of number of twitts retrieved = " + messagesReceived );
        logger.debug("\n");
        return Boolean.TRUE;
    }

    private ExecutorService setStopTimer() {

        Runnable stopTimer = () -> {
            keepTracking = false;
        };
        
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.schedule(stopTimer, trackingTimeout, TimeUnit.SECONDS);

        return scheduledExecutor;
    }
}

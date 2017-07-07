package com.test.twitter.messages;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.twitter.model.Twitt;
import com.test.twitter.utils.TwitterCommonUtils;

/**
 * Implementation of Callable  to  de-serialize messages from the message queue retrieved from twitter into Java model.
 * 
 * @author  Plabon Kakoti
 *
 */
public class TwitterMessageHandler implements Callable<List<Twitt>> {

    private static final Logger logger = LoggerFactory.getLogger(TwitterMessageHandler.class);

    private boolean keepRunning = true;
    

    private BlockingQueue<String> messageQueue;


    TwitterCommonUtils  utils = new TwitterCommonUtils();
    
    public TwitterMessageHandler(BlockingQueue<String> messageQueue) {
        this.messageQueue = messageQueue;
       // this.pw  =  pw;
    }

    @Override
    public List<Twitt> call() throws Exception {

        ArrayList<Twitt> messages = new ArrayList<>();

        while (!messageQueue.isEmpty() || keepRunning) {

            try {

                String jsonString = messageQueue.poll(1, TimeUnit.SECONDS);

                if (jsonString != null) {

                    Twitt message = utils.convertToObject(jsonString, new Twitt());

                    if (message.getId() == null || message.getId().trim().isEmpty()) {
                        logger.warn("Non status message received. Skipping message - {}", jsonString);
                        continue;
                    }

                    messages.add(message);
                }

            } catch (Throwable t) {
                logger.error("Exception while de-serializing message", t);
            }
        }

        logger.info("Total {} message(s) de-serialized", messages.size());

        return messages;
    }

    public void concludeProcessing() {
        keepRunning = false;
    }
}

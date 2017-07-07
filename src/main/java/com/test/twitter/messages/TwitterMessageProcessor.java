package com.test.twitter.messages;

import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.twitter.model.Twitt;
import com.test.twitter.model.TwitterUser;

/**
 * This method arranges read messages and group them by users. It  sorts the
 * messages in  order of author creation time and message creation time. Finally prints out
 * the messages in a STDOUT file using Logback.
 * 
 * @author Plabon Kakoti
 *
 */
public class TwitterMessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TwitterMessageProcessor.class);

    private List<Twitt> messages;

    public TwitterMessageProcessor(List<Twitt> messages) {
        this.messages = messages;
    }
/*
    public SortedMap<TwitterUser, SortedSet<Twitt>> process() {

        SortedMap<TwitterUser, SortedSet<Twitt>> messagesGroupdByAuthors = new TreeMap<>( new Comparator<TwitterUser>() {
																				                            @Override
																				                            public int compare(TwitterUser o1, TwitterUser o2) {
																				                            	return (int) (o1.getCreatedAtEpoch() - o2.getCreatedAtEpoch());
																				             }
																				        });
       
        Consumer<Twitt> groupByAuthor = (message) -> {


            TwitterUser author = message.getAuthor();

            if (!messagesGroupdByAuthors.containsKey(author)) {
                messagesGroupdByAuthors.put(author, new TreeSet<>(new Comparator<Twitt>(){

					@Override
					public int compare(Twitt o1, Twitt o2) {
						 if (o2 == null) {
					            return -1;
					        }

					     return (int) (o1.getCreatedDateEpoch() - o2.getCreatedDateEpoch());
					}}));
            }

            messagesGroupdByAuthors.get(author).add(message);
        };

        messages.forEach(groupByAuthor);

        return messagesGroupdByAuthors;
    }
*/
    

    public SortedMap<TwitterUser, SortedSet<Twitt>> process() {

        SortedMap<TwitterUser, SortedSet<Twitt>> messagesGroupdByAuthors = new TreeMap<>(authorSorter);

        messages.forEach((message) -> {
        	
        	//logger.debug("Processing Message : {} "+message.getId());
        	TwitterUser author = message.getAuthor();
        	if(!messagesGroupdByAuthors.containsKey(author)){
        		messagesGroupdByAuthors.put(author, new TreeSet<Twitt>(messageSorter));
        		
        	}
        	 messagesGroupdByAuthors.get(author).add(message);
        });

        return messagesGroupdByAuthors;
    }
    
    private Comparator<Twitt> messageSorter = (messageOne, messageTwo) -> {

        if (messageTwo == null) {
            return -1;
        }

        return (int) (messageOne.getCreatedDateEpoch() - messageTwo.getCreatedDateEpoch());
    };
    
    
   private Comparator<TwitterUser> authorSorter = (author1, author2) -> {
	   if(author2 == null){
		   return -1;
	   }
	   
	   return (int) (author1.getCreatedAtEpoch() - author2.getCreatedAtEpoch());  
   };
    
}

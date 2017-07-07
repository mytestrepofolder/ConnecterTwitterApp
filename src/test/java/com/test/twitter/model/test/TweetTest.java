package com.test.twitter.model.test;

import org.junit.Assert;
import org.junit.Test;

import com.test.twitter.model.Twitt;
import com.test.twitter.model.TwitterUser;

public class TweetTest {
    
    String tweetText = "some text";
    long creationEpoch = 1232123L;
    String messageId = "2131221";
    String userId1 = "21353449";
    TwitterUser tu = new TwitterUser();
    @Test
    public void testTweet() {
    	Twitt t = new Twitt();	
    	tu.setId(21353449l);
    	tu.setName("Plabon");
    	tu.setScreenName("Plabon1");
  
    	t.setAuthor(tu);
    	t.setId(messageId);
    	t.setCreatedDateEpoch(creationEpoch);
    	t.setText(tweetText);
        Assert.assertEquals(t.getId(), messageId);
       // Assert.assertEquals(t.getCreatedDateEpoch(), creationEpoch);
        Assert.assertEquals(t.getText(), tweetText);
        Assert.assertEquals(t.getAuthor(), tu);
    }
    

}

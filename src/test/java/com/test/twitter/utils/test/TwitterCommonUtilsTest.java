package com.test.twitter.utils.test;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.test.twitter.model.Twitt;
import com.test.twitter.model.TwitterUser;
import com.test.twitter.utils.TwitterCommonUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;



public class TwitterCommonUtilsTest {
	
	@Test
    public void testEpochTime() {

		long actual = TwitterCommonUtils.toEpochTime("Mon Aug 10 10:53:31 +0000 2009", "EEE MMM dd HH:mm:ss Z yyyy");
		long expected = 1249901611;
		assertEquals(expected, actual);
    }
	
	@Test
    public void testConvertToObject() throws JsonParseException, JsonMappingException, IOException {
		
		
		TwitterCommonUtils commonUtils = new TwitterCommonUtils();
		String jSonString = "{\"created_at\":\"Mon Mar 13 10:23:28 +0000 2017\",\"id\":841233240354672640,\"id_str\":\"841233240354672640\",\"text\":\"RT @Iifeiswliving: justin bieber sente nojo de gente mal educada sim ta achando ruim processa ele\",\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\/download\\/android\\\" rel=\\\"nofollow\\\"\\u003eTwitter for Android\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null,\"in_reply_to_screen_name\":null,\"user\":{\"id\":2605714453,\"id_str\":\"2605714453\",\"name\":\"\\u2655larEEssa\",\"screen_name\":\"lylasflatline\",\"location\":\"sheldamormeu\",\"url\":null,\"description\":\"\\\"nobody breaks my heart\\\" ata\",\"protected\":false,\"verified\":false,\"followers_count\":5399,\"friends_count\":4997,\"listed_count\":1,\"favourites_count\":1880,\"statuses_count\":13838,\"created_at\":\"Sat Jul 05 15:47:36 +0000 2014\",\"utc_offset\":-10800,\"time_zone\":\"Brasilia\",\"geo_enabled\":true,\"lang\":\"pt\",\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"000000\",\"profile_background_image_url\":\"http:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_image_url_https\":\"https:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_tile\":false,\"profile_link_color\":\"000000\",\"profile_sidebar_border_color\":\"000000\",\"profile_sidebar_fill_color\":\"000000\",\"profile_text_color\":\"000000\",\"profile_use_background_image\":false,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/840007195987185664\\/fXrjnJS0_normal.jpg\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/840007195987185664\\/fXrjnJS0_normal.jpg\",\"profile_banner_url\":\"https:\\/\\/pbs.twimg.com\\/profile_banners\\/2605714453\\/1489108393\",\"default_profile\":false,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"retweeted_status\":{\"created_at\":\"Mon Mar 13 01:32:32 +0000 2017\",\"id\":841099627260739584,\"id_str\":\"841099627260739584\",\"text\":\"justin bieber sente nojo de gente mal educada sim ta achando ruim processa ele\",\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\/download\\/android\\\" rel=\\\"nofollow\\\"\\u003eTwitter for Android\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null,\"in_reply_to_screen_name\":null,\"user\":{\"id\":1667991679,\"id_str\":\"1667991679\",\"name\":\"\\ufe0f\",\"screen_name\":\"Iifeiswliving\",\"location\":\"SG2\",\"url\":\"http:\\/\\/justinbiebermusic.com\",\"description\":\"grammy-nominee, singer and songwriter, demi lovato.\",\"protected\":false,\"verified\":false,\"followers_count\":4727,\"friends_count\":781,\"listed_count\":217,\"favourites_count\":9086,\"statuses_count\":137280,\"created_at\":\"Tue Aug 13 14:33:12 +0000 2013\",\"utc_offset\":-10800,\"time_zone\":\"Brasilia\",\"geo_enabled\":true,\"lang\":\"pt\",\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"3B94D9\",\"profile_background_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_background_images\\/581543465307090944\\/mo2cga4e.jpg\",\"profile_background_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_background_images\\/581543465307090944\\/mo2cga4e.jpg\",\"profile_background_tile\":true,\"profile_link_color\":\"111111\",\"profile_sidebar_border_color\":\"FFFFFF\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/841089803261214723\\/jbon5zMw_normal.jpg\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/841089803261214723\\/jbon5zMw_normal.jpg\",\"profile_banner_url\":\"https:\\/\\/pbs.twimg.com\\/profile_banners\\/1667991679\\/1489366153\",\"default_profile\":false,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"is_quote_status\":false,\"retweet_count\":31,\"favorite_count\":8,\"entities\":{\"hashtags\":[],\"urls\":[],\"user_mentions\":[],\"symbols\":[]},\"favorited\":false,\"retweeted\":false,\"filter_level\":\"low\",\"lang\":\"pt\"},\"is_quote_status\":false,\"retweet_count\":0,\"favorite_count\":0,\"entities\":{\"hashtags\":[],\"urls\":[],\"user_mentions\":[{\"screen_name\":\"Iifeiswliving\",\"name\":\"\\ufe0f\",\"id\":1667991679,\"id_str\":\"1667991679\",\"indices\":[3,17]}],\"symbols\":[]},\"favorited\":false,\"retweeted\":false,\"filter_level\":\"low\",\"lang\":\"pt\",\"timestamp_ms\":\"1489400608515\"}";
		TwitterUser tUser =  new TwitterUser();
		tUser.setId(2605714453l);
		tUser.setName("♕larEEssa");
		tUser.setScreenName("lylasflatline");
		tUser.setCreatedAt("Sat Jul 05 15:47:36 +0000 2014");
		Twitt expected = new Twitt();
		expected.setAuthor(tUser);
		expected.setCreatedAt("Mon Mar 13 10:23:28 +0000 2017");
		expected.setId("841233240354672640");//841231253416349696
		expected.setText("RT @Iifeiswliving: justin bieber sente nojo de gente mal educada sim ta achando ruim processa ele");
		Twitt actual = commonUtils.convertToObject(jSonString, new Twitt());
		TwitterUser expectedUser = expected.getAuthor();
		TwitterUser actualUser = actual.getAuthor();		
		assertEquals(expectedUser, actualUser);
    }
	
}


package com.test.twitter.main;

/**
 * @author Plabon Kakoti
 *
 */
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.test.twitter.authentication.TwitterAuthenticationException;
import com.test.twitter.authentication.TwitterAuthenticator;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import java.io.IOException;
import java.io.PrintStream;

public class TwitterAppStreamAPI {
    
    private final static String PUBLIC_FILTER_ENDPOINT = "https://stream.twitter.com/1.1/statuses/filter.json?track=";   
 
    /**
     * The consumer secret and consumer key to access the twitter API for this app.
     */
    private final static String CONSUMER_KEY = "RLSrphihyR4G2UxvA0XBkLAdl";
    private final static String CONSUMER_SECRET ="FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4";
        
    
    private TwitterAuthenticator auth;
    private final String filterQuery;
    private HttpRequestFactory requestFactory;

    public TwitterAppStreamAPI(PrintStream userStream, String filterQuery) {
        assert filterQuery != null;
        this.filterQuery = filterQuery;
        auth = new TwitterAuthenticator(userStream, CONSUMER_KEY, CONSUMER_SECRET);
    }

    /**
     * For test
     */
    public void setAuthenticator(TwitterAuthenticator auth) {
        this.auth = auth;
    }

    public void authenticate() throws TwitterAuthenticationException {
        requestFactory = auth.getAuthorizedHttpRequestFactory();
    }
    
    /**
     * 
     * @see https://dev.twitter.com/streaming/overview/connecting
     */
    public HttpResponse executeFilterRequest() throws TwitterAuthenticationException, IOException {
        GenericUrl url = new GenericUrl(PUBLIC_FILTER_ENDPOINT + filterQuery);
        HttpRequest request = requestFactory.buildGetRequest(url);
        HttpResponse httpResponse = request.execute();
        return httpResponse;
    }
}
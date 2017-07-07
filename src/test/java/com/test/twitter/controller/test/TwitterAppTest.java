package com.test.twitter.controller.test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.test.twitter.authentication.TwitterAuthenticator;
import com.test.twitter.main.TwitterAppStreamAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class TwitterAppTest {
   
		HttpTransport transport;
		HttpRequest request;
		MockLowLevelHttpResponse response;
	    
		@Before
	    public void setup() throws Exception {
	        response = new MockLowLevelHttpResponse().setContentLength(-1);
	        transport = new MockHttpTransport.Builder().setLowLevelHttpResponse(response).build();
	        request = transport.createRequestFactory().buildPostRequest(HttpTesting.SIMPLE_GENERIC_URL, null);
	    }
	    
	    @Test
	    public void testTestResponse() throws Exception {
	        String content = "Hello World";
	        response.setContent(content);
	        HttpResponse response = request.execute();
	        Assert.assertEquals(content,(new BufferedReader(new InputStreamReader(response.getContent()))).readLine());
	    }
        @Test
        public void testExecuteFilterRequest() throws Exception {
            PrintStream mockOut = Mockito.mock(PrintStream.class);
            TwitterAuthenticator mockAuth = Mockito.mock(TwitterAuthenticator.class);
            HttpTransport transport = new MockHttpTransport();
            String filter = "bieber";
            TwitterAppStreamAPI apiRequest = new TwitterAppStreamAPI(mockOut, filter);
            apiRequest.setAuthenticator(mockAuth);
            Mockito.when(mockAuth.getAuthorizedHttpRequestFactory()).thenReturn(transport.createRequestFactory());
            apiRequest.authenticate();
            HttpResponse httpResponse = apiRequest.executeFilterRequest();
            verify(mockAuth).getAuthorizedHttpRequestFactory();
            Assert.assertEquals(filter, httpResponse.getRequest().getUrl().getFirst("track"));
           

    }
}

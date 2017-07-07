package com.test.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.twitter.utils.TwitterCommonUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterUser {

    private static final String dateTimePattern = "EEE MMM dd HH:mm:ss Z yyyy";
    
    public TwitterUser(){
    
    }
    
    private long id;

    @JsonProperty("created_at")
    private String createdAt;

    private String name;

    @JsonProperty("screen_name")
    private String screenName;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreatedAtEpoch() {
        return TwitterCommonUtils.toEpochTime(createdAt, dateTimePattern);
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return this.screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object other) {

        if ((other == null) || !(other instanceof TwitterUser)) {
            return false;
        }

        TwitterUser otherAuthor = (TwitterUser) other;

        return id == otherAuthor.getId();
    }

    @Override
    public String toString() {

        StringBuilder output = new StringBuilder("Author Id: " + id + "\n");

        output.append("{").append("\n");
        output.append("Name: ").append(screenName).append("\n");
        output.append("Twitter Handle: ").append(name).append("\n");
        output.append("Created at: ").append(getCreatedAtEpoch()).append("\n");
        output.append('}').append("\n");

        return output.toString();
    }
}

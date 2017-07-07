package com.test.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.twitter.utils.TwitterCommonUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Twitt {

	//For Test purpose
    public Twitt(String id, String text, String createdAt, TwitterUser author) {
		super();
		this.id = id;
		this.text = text;
		this.createdAt = createdAt;
		this.author = author;
	}
    
   	public Twitt() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final String dateTimePattern = "EEE MMM dd HH:mm:ss Z yyyy";

    @JsonProperty("id_str")
    private String id;

    private String text;
    
    private long createdEpoch;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("user")
    private TwitterUser author;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCreatedDateEpoch() {
        return TwitterCommonUtils.toEpochTime(createdAt, dateTimePattern);
    }

    public void setCreatedDateEpoch(long createdEpoch) {
        this.createdEpoch = createdEpoch;
    }

    
    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public TwitterUser getAuthor() {
        return this.author;
    }

    public void setAuthor(TwitterUser author) {
        this.author = author;
    }

   @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object other) {

        if ((other == null) || !(other instanceof Twitt)) {
            return false;
        }

        Twitt otherMessage = (Twitt) other;

        return id == otherMessage.getId();
    }

    @Override
    public String toString() {

        StringBuilder output = new StringBuilder("Message Id: " + id + "\n");

        output.append('{').append("\n");
        output.append("Text: ").append(text).append("\n");
        output.append("Twitter User Id: ").append(author.getId()).append("\n");
        output.append("Created at: ").append(getCreatedDateEpoch()).append("\n");
        output.append('}').append("\n");

        return output.toString();
    }
}

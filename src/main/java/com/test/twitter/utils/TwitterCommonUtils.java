package com.test.twitter.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.twitter.model.Twitt;

/**
 * Common Utility class 
 * 
 * @author Plabon Kakoti
 *
 */
public class TwitterCommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(TwitterCommonUtils.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static long toEpochTime(String timeString, String pattern) {

        long epochTime = -1;

        try {

            TemporalAccessor temporalAccessor = DateTimeFormatter.ofPattern(pattern).parse(timeString);
            LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);

            epochTime = localDateTime.toEpochSecond(ZoneOffset.UTC);

        } catch (Exception e) {
            logger.error("Error in converting to Epoch Time: '{}', Pattern: '{}'", timeString, pattern, e);
        }

        return epochTime;
    }
    
    public Twitt convertToObject(String jsonString, Twitt type)
            throws JsonParseException, JsonMappingException, IOException {

    	Twitt object = objectMapper.readValue(jsonString, type.getClass());
        return object;
    }
    
}

package org.jasonleaster.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Author: jasonleaster
 * Date  : 2017/5/10
 * Email : jasonleaster@gmail.com
 */
public final class JsonUtils {

    private final static Logger logger = Logger.getLogger(JsonUtils.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {}

    public static String toJson(Object object)
    {
        String value = "";
        try {
            value = mapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            logger.error("Failed to transform an object into json string");
        }
        return value;
    }

    public static <T> T toObject(String jsonStr, Class clazz)
    {
        try {
            return (T) mapper.readValue(jsonStr, clazz);
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.error("Failed to transform an json string into object");
        }
        return null;
    }
}

package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.PathNotFoundException;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

public class JsonUtils {
    private static final JSONParser parser = new JSONParser();

    public static Object getObjectFromJson(String jsonContent, String jsonPath) {
        try {
            Configuration config = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                    .mappingProvider(new JacksonMappingProvider()).build();
            return JsonPath.using(config).parse(jsonContent).read(jsonPath);
        } catch (PathNotFoundException ex) {
            return null;
        }
    }

    public static String getObjectFromJsonAsString(String jsonContent, String jsonPath) {
        String object = getObjectFromJson(jsonContent, jsonPath).toString();
        if (object.contains("\"")) {
            object = object.substring(1);
            object = object.substring(0, (object.length() - 1));
        }
        return object;
    }

    public static Object parse(String jsonContent) throws ParseException {
        return parser.parse(jsonContent);
    }

    public static String getPrettyJsonString(String uglyJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            Object jsonObject = mapper.readValue(uglyJson, Object.class);

            return mapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            return uglyJson;
        }
    }

    public static org.json.JSONObject convertStringToJson(String json) {
        try {
            return new org.json.JSONObject(json);
        } catch (JSONException e) {
            return null;
        }
    }
}
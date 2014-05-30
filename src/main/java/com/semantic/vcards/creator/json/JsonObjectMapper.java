package com.semantic.vcards.creator.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semantic.vcards.creator.json.exceptions.SematicRuntimeException;
import com.semantic.vcards.creator.model.VCard;

import java.io.IOException;

/**
 * @author Tomasz Lelek
 * @since 2014-03-27
 */
public class JsonObjectMapper {

    private JsonObjectMapper() {}

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * it make mapping from json to VCard
      * @param json
     * @return
     */
    public static VCard jsonToVcard(String json) {
        try {
            return objectMapper.readValue(json, VCard.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SematicRuntimeException("can not createRdf json : " + json, e);
        }
    }
}

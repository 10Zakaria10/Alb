package com.theodo.albeniz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodo.albeniz.model.Tune;

public class TestUtils {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Tune getObject(final String content){
        try{
            return new ObjectMapper().readValue(content,Tune.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

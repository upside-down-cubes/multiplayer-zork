package io.upsidedowncubes.multiplayerzork.webLogic.Controller.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class JsonConvertor {

    public static String convert(Object obj) {
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(writer, obj);
            return writer.toString();
        } catch (IOException e) {
            return "[Bad object or conventions]";
        }

    }
}

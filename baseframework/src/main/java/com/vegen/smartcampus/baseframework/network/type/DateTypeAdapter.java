package com.vegen.smartcampus.baseframework.network.type;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by vegen on 2018/2/23.
 */
public class DateTypeAdapter extends TypeAdapter<Double> {

    @Override
    public void write(JsonWriter jsonWriter, Double number) throws IOException {
        if (number == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(number);
    }

    @Override
    public Double read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }

        try {
            String value = jsonReader.nextString();
            if ("".equals(value)) {
                return 0d;
            }
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

}


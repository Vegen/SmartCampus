//package com.vegen.smartcampus.baseframework.network.type;
//
//import com.google.gson.JsonSyntaxException;
//import com.google.gson.TypeAdapter;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonToken;
//import com.google.gson.stream.JsonWriter;
//
//import java.io.IOException;
//
///**
// * Created by vegen on 2018/2/23.
// */
//public class FloatTypeAdapter extends TypeAdapter<Float> {
//
//    @Override
//    public void write(JsonWriter jsonWriter, Float number) throws IOException {
//        if (number == null) {
//            jsonWriter.nullValue();
//            return;
//        }
//        jsonWriter.value(number);
//    }
//
//    @Override
//    public Float read(JsonReader jsonReader) throws IOException {
//        if (jsonReader.peek() == JsonToken.NULL) {
//            jsonReader.nextNull();
//            return null;
//        }
//
//        try {
//            String value = jsonReader.nextString();
//            if ("".equals(value)) {
//                return 0f;
//            }
//            return Float.parseFloat(value);
//        } catch (NumberFormatException e) {
//            throw new JsonSyntaxException(e);
//        }
//    }
//
//}
//

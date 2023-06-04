package com.example.apptinder.Type;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;


public class Converter {
    @TypeConverter
    public static List<String> fromString(String value) {
        return new Gson().fromJson(value, new TypeToken<List<String>>() {}.getType());
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        return new Gson().toJson(list);
    }
}

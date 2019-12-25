package com.zerowater.environment.util;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-08-10.
 * yap company
 * byzerowater@gmail.com
 * EmptyCheckTypeAdapterFactory
 */
public class EmptyCheckTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        // We filter out the EmptyCheckTypeAdapter as we need to check this for emptiness!

        Timber.i("TypeAdapter " + type.getRawType().getSimpleName());
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
        return new EmptyCheckTypeAdapter<>(delegate, elementAdapter).nullSafe();
    }

    /**
     * EmptyCheckTypeAdapter
     * body 빈값 여부 체크 후 data 항목만 가져오기
     * "" 일 경우 null로 변경
     *
     * @param <T>
     */
    public static class EmptyCheckTypeAdapter<T> extends TypeAdapter<T> {

        private final TypeAdapter<T> delegate;
        private final TypeAdapter<JsonElement> elementAdapter;

        public EmptyCheckTypeAdapter(final TypeAdapter<T> delegate,
                                     final TypeAdapter<JsonElement> elementAdapter) {
            this.delegate = delegate;
            this.elementAdapter = elementAdapter;
        }

        @Override
        public void write(final JsonWriter out, final T value) throws IOException {
            this.delegate.write(out, value);
        }

        @Override
        public T read(final JsonReader in) throws IOException {
            JsonElement jsonElement = elementAdapter.read(in);

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has("data")) {
                    String data = jsonObject.get("data").toString();
                    if (!StringUtil.isEmpty(data.replace("\"\"", "null"))) {
                        jsonElement = jsonObject.get("data");
                    }
                }
            }
            return this.delegate.fromJson(jsonElement.toString().replace("\"\"", "null"));
        }
    }

}

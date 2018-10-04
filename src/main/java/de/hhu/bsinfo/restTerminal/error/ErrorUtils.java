package de.hhu.bsinfo.restTerminal.error;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
//TODO auf Retrofit anders zugreifen
public class ErrorUtils {
    public static APIError parseError(Response<?> response) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8009/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Converter<ResponseBody, APIError> converter =
                retrofit.responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}


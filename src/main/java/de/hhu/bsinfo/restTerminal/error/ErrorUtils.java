package de.hhu.bsinfo.restTerminal.error;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
public class ErrorUtils {
    public static APIError parseError(Response<?> response, Retrofit p_retrofit) {
        APIError error;
        Converter <ResponseBody, APIError> converter = p_retrofit.responseBodyConverter
                (APIError.class, new Annotation[0]);
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError("Server sent an unexpected error response. Please check your request.");
        }

        return error;
    }
}


package de.hhu.bsinfo.restTerminal.error;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Class for handling errors
 */
public class ErrorUtils {
    /**
     * This class parses an error and converts it into an APIError Object
     * @see de.hhu.bsinfo.restTerminal.error.APIError
     * @param p_response response object which represents the error response
     * @param p_retrofit retrofit object
     * @return APIError object cotaining the error message
     */
    public static APIError parseError(Response<?> p_response, Retrofit p_retrofit) {
        APIError error;
        Converter <ResponseBody, APIError> converter = p_retrofit.responseBodyConverter
                (APIError.class, new Annotation[0]);
        try {
            error = converter.convert(p_response.errorBody());
        } catch (IOException e) {
            return new APIError("Server sent an unexpected error response. Please check your request.");
        }

        return error;
    }
}


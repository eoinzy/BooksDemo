package com.eoinzy.booksdemo.ui.main.network;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static RestInterface REST_CLIENT;
    private static final String API_BASE_URL = "https://www.googleapis.com/books/v1/";

    static {
        setupRestClient();
    }

    private RestClient() {
    }

    public static RestInterface get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);

        //Attach API key, if needed
//        httpClient.addInterceptor(getApiKeyInterceptor());

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        REST_CLIENT = builder.create(RestInterface.class);
    }

    /**
     * Add a query string parameter to all requests.
     *
     * @return An OkHttp Interceptor which adds a query string parameter to all requests.
     */
    private static Interceptor getApiKeyInterceptor() {
        return chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", "<YOUR-API-KEY-HERE>")
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }
}

package com.eoinzy.booksdemo.ui.main.network;

import com.eoinzy.booksdemo.models.BookSearch;

import io.reactivex.Observable;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("volumes")
    Observable<BookSearch> getVolumes(@Query("q") String searchQuery);
}


//    https://www.googleapis.com/apiName/apiVersion/resourcePath?parameters
//    https://www.googleapis.com/books/v1/{collectionName}/resourceID?parameters

//    https://www.googleapis.com/books/v1/volumes
//    https://www.googleapis.com/books/v1/volumes/volumeId
//    https://www.googleapis.com/books/v1/mylibrary/bookshelves
//    https://www.googleapis.com/books/v1/mylibrary/bookshelves/shelf
//    https://www.googleapis.com/books/v1/mylibrary/bookshelves/shelf/volumes

//    https://www.googleapis.com/books/v1/volumes?q=quilting

//    https://www.googleapis.com/books/v1/volumes/s1gVAAAAYAAJ

//    https://www.googleapis.com/books/v1/volumes?q=harry+potter&callback=handleResponse

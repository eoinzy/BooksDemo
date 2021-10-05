package com.eoinzy.booksdemo.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eoinzy.booksdemo.models.BookSearch;
import com.eoinzy.booksdemo.models.Item;
import com.eoinzy.booksdemo.ui.main.network.RestClient;

import java.util.Collections;
import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainViewModel extends ViewModel {

    private final CompositeDisposable myCompositeDisposable = new CompositeDisposable();

    private final MutableLiveData<BookSearch> bookSearchResults = new MutableLiveData<BookSearch>();

    public void performBookSearch(String searchQuery) {
        Observable<BookSearch> call = RestClient.get().getVolumes(searchQuery);
        myCompositeDisposable.add(call
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(BookSearch bookSearch) {
        bookSearchResults.postValue(bookSearch);
    }

    private void handleError(Throwable throwable) {
        //TODO: Display error to user
        Timber.d("Error::%s", throwable.getLocalizedMessage());
    }

    public MutableLiveData<BookSearch> getBookSearchResults() {
        return bookSearchResults;
    }
}
package com.foreseer.definethis.HistoryScreen.Model;

import com.foreseer.definethis.Storage.StorageHandler;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class HistoryInteractorImpl implements HistoryInteractor {

    private HistoryInteractorListener listener;

    public HistoryInteractorImpl(HistoryInteractorListener listener) {
        this.listener = listener;
    }

    @Override
    public void requestDefinitions() {
        Observable.just(StorageHandler.getAllWords())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(words -> {
                    listener.onDefinitionsReceived(words);
                });
    }
}

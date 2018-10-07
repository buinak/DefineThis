package com.foreseer.definethis.UI.WordInformationScreen;

import com.foreseer.definethis.Data.Repository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WordInformationModelImpl implements WordInformationContract.WordInformationModel {

    private WordInformationContract.WordInformationModel.Listener listener;

    private Disposable request;

    public WordInformationModelImpl(WordInformationContract.WordInformationModel.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void requestWord(String word) {
        request = Observable.just(Repository.getInstance().getWord(word))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                   listener.onWordReceived(result);
                   request.dispose();
                });
    }

    @Override
    public void onDestroy() {
        request.dispose();
        listener = null;
    }
}

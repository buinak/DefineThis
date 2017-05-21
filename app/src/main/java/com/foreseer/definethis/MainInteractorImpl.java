package com.foreseer.definethis;

import com.foreseer.definethis.API.WordAPIClient;
import com.foreseer.definethis.API.WordsAPIService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public class MainInteractorImpl implements MainInteractor {
    private MainInteractorListener listener;

    public MainInteractorImpl(MainInteractorListener listener) {
        this.listener = listener;
    }

    @Override
    public void onWordDefinitionRequested(String word) {
        WordAPIClient.getApiClient().getWordDefinition(WordsAPIService.key, WordsAPIService.accept, word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    if (result.getResults().size() == 1) {
                        listener.onWordDefinitionReceived(result.getResults().get(0).getDefinition());
                    } else {
                        listener.onWordDefinitionsReceived(result.getResults());
                    }
                }, e -> {
                    listener.onError(e.getMessage());
                });
    }
}

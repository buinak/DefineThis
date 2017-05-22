package com.foreseer.definethis.Model;

import com.foreseer.definethis.Model.API.WordAPIClient;
import com.foreseer.definethis.Model.API.WordsAPIService;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 * For any questions, feel free to reach me using any of my contacts.
 * Contacts:
 *  e-mail (preferred): fforeseer@gmail.com
 */

public class MainInteractorImpl implements MainInteractor {
    private MainInteractorListener listener;

    private PublishSubject<String> subject;
    private String lastRequested;

    public MainInteractorImpl(MainInteractorListener listener) {
        this.listener = listener;
        lastRequested = "";
        initializePublishSubject();
    }

    private void initializePublishSubject(){
        subject = PublishSubject.create();
        subject.debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(s -> {
                    listener.onRequestStarted();
                    onWordDefinitionRequested(s);
                });
    }

    @Override
    public void onWordDefinitionRequested(String word) {
        lastRequested = word;
        listener.onRequestStarted();
        WordAPIClient.getApiClient().getWordDefinition(word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    if (result.getWord().equals(lastRequested)) {
                        if (result.getDefinitions().size() == 0){
                            listener.onWordNotFound(result.getWord());
                        }
                        else if (result.getDefinitions().size() == 1) {
                            listener.onWordDefinitionReceived(result.getDefinitions().get(0));
                        } else {
                            listener.onWordDefinitionsReceived(result.getDefinitions());
                        }
                    }
                }, e -> {
                    listener.onError(e.getMessage(), false);
                });
    }

    @Override
    public void onTextChanged(String text) {
        subject.onNext(text);
    }
}

package com.foreseer.definethis.UI.MainScreen;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.UI.MainScreen.API.WordAPIClient;
import com.foreseer.definethis.Data.Repository;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 * For any questions, feel free to reach me using any of my contacts.
 * Contacts:
 * e-mail (preferred): fforeseer@gmail.com
 */


public class MainInteractorImpl implements MainScreenContract.MainInteractor {
    private MainInteractorListener listener;

    private PublishSubject<String> subject;
    private String lastRequested;

    private Disposable request;

    public MainInteractorImpl(MainInteractorListener listener) {
        this.listener = listener;
        lastRequested = "";
        initializePublishSubject();
    }

    private void initializePublishSubject() {
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
        if (request != null) {
            request.dispose();
            request = null;
        }
        word = word.toLowerCase();

        if (!validateWord(word)) {
            return;
        }

        if (isCached(word)) {
            lastRequested = "";
            listener.onWordDefinitionsReceived(Repository.getWord(word));
            return;
        }

        lastRequested = word;

        request = WordAPIClient.getGoogleApiClient().getWordDefinition(word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    processResult(result);
                }, e -> {
                    listener.onError((Exception) e, false);
                });
    }

    private boolean isCached(String word) {
        if (Repository.wasWordPreviouslyRequested(word)) {
            return true;
        }
        return false;
    }

    private boolean validateWord(String word) {
        if (word.contains(" ")) {
            if (word.split(" ").length != 2) {
                listener.onIncorrectWord();
                return false;
            }
            if (!word.startsWith("to ")) {
                listener.onIncorrectWord();
                return false;
            }
        }

        if (!word.equals("")) {
            listener.onRequestStarted();
            return true;
        } else {
            listener.onEmptyRequestReceived();
            return false;
        }
    }

    private void processResult(Word word) {
        request.dispose();
        request = null;

        Repository.save(word);
        listener.onWordDefinitionsReceived(word);
    }


    @Override
    public void onTextChanged(String text) {
        if (!text.equals("")) {
            subject.onNext(text);
        } else {
            subject.onNext(text);
        }
    }

    @Override
    public void onDestroy() {
        if (request != null) {
            request.dispose();
            request = null;
        }
    }
}

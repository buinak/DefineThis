package com.foreseer.definethis.UI.MainScreen;

import com.foreseer.definethis.Data.Entities.DefineThis.Word;
import com.foreseer.definethis.UI.MainScreen.API.WordAPIClient;
import com.foreseer.definethis.Data.Repository;

import org.apache.commons.lang3.StringUtils;

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


public class MainModelImpl implements MainScreenContract.MainModel {
    private MainModelListener listener;

    private PublishSubject<String> subject;

    private Disposable request;
    private Disposable subjectDisposable;

    private String lastRequested;

    public MainModelImpl(MainModelListener listener) {
        this.listener = listener;
        lastRequested = "";

        initializePublishSubject();
    }

    private void initializePublishSubject() {
        subject = PublishSubject.create();
        subjectDisposable = subject.debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(s -> {
                    onWordDefinitionRequested(s);
                });
    }

    @Override
    public void onWordDefinitionRequested(String word) {
        word = word.toLowerCase();

        if (!validateWord(word)) {
            listener.onIncorrectWord();
            return;
        }

        if (word.equals(lastRequested)){
            return;
        }
        lastRequested = word;
        disposeRequest();

        listener.onCorrectWord();

        if (isCached(word)) {
            Word result = Repository.getInstance().getWord(word);
            Repository.getInstance().saveWord(result);
            listener.onWordDefinitionsReceived(result);
            return;
        }

        listener.onRequestStarted();
        request = WordAPIClient.getGoogleApiClient().getWordDefinition(word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    processResult(result);
                }, e -> {
                    listener.onError((Exception) e, false);
                });
    }

    private void disposeRequest() {
        if (request != null) {
            request.dispose();
            request = null;
        }
    }

    private boolean isCached(String word) {
        if (Repository.getInstance().wasWordPreviouslyRequested(word)) {
            return true;
        }
        return false;
    }

    private boolean validateWord(String word) {
        if (StringUtils.isNumeric(word)){
            return false;
        }

        if (word.contains(" ")) {
            if (word.split(" ").length != 2) {
                return false;
            }
            if (!word.startsWith("to ")) {
                return false;
            }
        }

        if (!word.equals("")) {
            return true;
        } else {
            listener.onEmptyRequestReceived();
            return false;
        }
    }

    private void processResult(Word word) {
        fixPhoneticsString(word);

        Repository.getInstance().saveWord(word);
        listener.onWordDefinitionsReceived(word);
    }

    private void fixPhoneticsString(Word word) {
        if (!word.getPhonetics().get(0).startsWith("/")){
            String newPhonetics = "/" + word.getPhonetics().get(0) + "/";
            word.getPhonetics().set(0, newPhonetics);
        }
    }


    @Override
    public void onTextChanged(String text) {
        subject.onNext(text);
    }

    @Override
    public void finish() {
        disposeRequest();
        disposeSubject();
    }

    private void disposeSubject() {
        if (subjectDisposable != null){
            subjectDisposable.dispose();
            subjectDisposable = null;
        }
    }
}

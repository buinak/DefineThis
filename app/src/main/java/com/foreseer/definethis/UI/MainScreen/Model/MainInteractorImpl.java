package com.foreseer.definethis.UI.MainScreen.Model;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.UI.MainScreen.Model.API.WordAPIClient;
import com.foreseer.definethis.Data.StorageHandler;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
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
        word = word.toLowerCase();

        if (!validateWord(word)) {
            return;
        }

        if (isCached(word)) {
            lastRequested = "";
            listener.onWordDefinitionsReceived(StorageHandler.getWord(word));
            return;
        }

        String partOfSpeech = "";
        if (word.contains("to")){
            partOfSpeech = "verb";
            word = word.substring(word.indexOf(" ") + 1);
        }
        lastRequested = word;

        WordAPIClient.getGoogleApiClient().getWordDefinition(word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    processResult(result);
                }, e -> {
                    listener.onError((Exception) e, false);
                });
    }

    private boolean isCached(String word) {
        if (StorageHandler.wasWordPreviouslyRequested(word)){
            return true;
        }
        return false;
    }

    private boolean validateWord(String word) {
        if (word.contains(" ")){
            if (word.split(" ").length != 2){
                listener.onIncorrectWord();
                return false;
            }
            if (!word.startsWith("to ")){
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

    private void processResult(Word word){
        StorageHandler.save(word);
        listener.onWordDefinitionsReceived(word);
    }

    private Observable<Word> requestDefinition(String word, String partOfSpeech, int limit){
            return WordAPIClient.getGoogleApiClient().getWordDefinition(word);
    }


    @Override
    public void onTextChanged(String text) {
        if (!text.equals("")) {
            subject.onNext(text);
        } else {
            subject.onNext(text);
        }
    }
}

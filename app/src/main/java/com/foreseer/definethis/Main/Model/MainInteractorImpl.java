package com.foreseer.definethis.Main.Model;

import com.foreseer.definethis.Main.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.Main.Model.API.JSONSchema.Word;
import com.foreseer.definethis.Main.Model.API.WordAPIClient;

import java.util.Objects;
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

        if (!word.equals("")) {
            listener.onRequestStarted();
        } else {
            listener.onEmptyRequestReceived();
            return;
        }

        String partOfSpeech = "";
        if (word.contains("to")){
            partOfSpeech = "verb";
            word = word.substring(word.indexOf(" ") + 1);
        }
        lastRequested = word;

        requestDefinition(word, partOfSpeech, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    processResult(result);
                }, e -> listener.onError(e.getMessage(), false));
    }

    private void processResult(Word word) {
        String headword = Utils.parseHeadwordOutOfURL(word.getUrl());
        if (headword.equals(lastRequested) && !headword.equals("") && !lastRequested.equals("")) {
            lastRequested = "";
            if (word.getResults().size() == 0) {
                listener.onWordNotFound(lastRequested);
            } else {
                String definition;
                String partOfSpeech;
                if (word.getResults().get(0).getSenses().get(0).getDefinition() != null) {
                    definition = word.getResults().get(0).getSenses().get(0).getDefinition();
                    partOfSpeech = word.getResults().get(0).getPartOfSpeech();
                } else {
                    definition = word.getResults().get(0).getSenses().get(0).getSubsenses().get(0).getDefinition();
                    partOfSpeech = word.getResults().get(0).getPartOfSpeech();
                }

                listener.onWordDefinitionReceived(new Definition(definition, partOfSpeech));
            }
        }
    }

    private Observable<Word> requestDefinition(String word, String partOfSpeech, int limit){
        if (!partOfSpeech.equals("")) {
            return WordAPIClient.getApiClient().getWordDefinition(word, 1, partOfSpeech);
        } else {
            return WordAPIClient.getApiClient().getWordDefinition(word, 1);
        }
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

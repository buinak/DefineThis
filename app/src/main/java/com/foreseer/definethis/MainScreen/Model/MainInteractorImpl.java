package com.foreseer.definethis.MainScreen.Model;

import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Result;
import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Word;
import com.foreseer.definethis.MainScreen.Model.API.WordAPIClient;
import com.foreseer.definethis.Storage.StorageHandler;

import java.util.ArrayList;
import java.util.List;
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
            listener.onWordDefinitionsReceived(StorageHandler.getDefinitions(word));
            return;
        }

        String partOfSpeech = "";
        if (word.contains("to")){
            partOfSpeech = "verb";
            word = word.substring(word.indexOf(" ") + 1);
        }
        lastRequested = word;

        requestDefinition(word, partOfSpeech, 1000)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    processResult(result);
                }, e -> {
                    listener.onError(e.getMessage(), false);
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

    private void processResult(Word word) {
        String headword = Utils.parseHeadwordOutOfURL(word.getUrl());
        if (headword.equals(lastRequested) && !headword.equals("") && !lastRequested.equals("")) {
            if (word.getResults().size() == 0) {
                listener.onWordNotFound(lastRequested);
            } else {
                lastRequested = "";
                String definition;
                String partOfSpeech;
                List<Definition> definitionList = new ArrayList<>();
                for (Result result : word.getResults()){
                    if (result.getSenses().get(0).getDefinition() != null){
                        definitionList.add(new Definition(result.getSenses().get(0).getDefinition(), result.getPartOfSpeech()));
                    } else {
                        definitionList.add(new Definition(result.getSenses().get(0).getSubsenses().get(0).getDefinition(), result.getPartOfSpeech()));
                    }
                }
                processPartOfSpeech(definitionList);
                StorageHandler.save(headword, definitionList);
                listener.onWordDefinitionsReceived(definitionList);
            }
        }
    }

    private Observable<Word> requestDefinition(String word, String partOfSpeech, int limit){
        if (!partOfSpeech.equals("")) {
            return WordAPIClient.getApiClient().getWordDefinition(word, limit, partOfSpeech, WordAPIClient.API_KEY);
        } else {
            return WordAPIClient.getApiClient().getWordDefinition(word, limit, WordAPIClient.API_KEY);
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

    /**
     * This method processes list of definitions and fixes part of speech parts in definitions.
     * Since the API returns null for those definitions where part of speech is not certain (abbreviations,
     * phrases), we can guess part of speech based on contents of the definition.
     *
     * For example if definition contains "abbreviation" we can guess it's an abbreviation et cetera
     *
     * @param definitions List of definitions
     */
    private void processPartOfSpeech(List<Definition> definitions){
        for (Definition definition : definitions){
            if (definition.getPartOfSpeech() == null){
                if (definition.getDefinition().contains("abbreviation")){
                    definition.setPartOfSpeech("abbreviation");
                } else if (definition.getDefinition().contains("phrase")){
                    definition.setPartOfSpeech("phrase");
                } else {
                    definition.setPartOfSpeech("unknown");
                }
            }
        }
    }
}

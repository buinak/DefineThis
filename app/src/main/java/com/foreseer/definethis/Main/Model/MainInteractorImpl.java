package com.foreseer.definethis.Main.Model;

import com.foreseer.definethis.Main.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.Main.Model.API.WordAPIClient;

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
        if (!word.equals("")) {
            listener.onRequestStarted();
        } else {
            listener.onEmptyRequestReceived();
            return;
        }

        WordAPIClient.getApiClient().getWordDefinition(word, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    String headword = Utils.parseHeadwordOutOfURL(result.getUrl());
                    if (headword.equals(lastRequested) && !headword.equals("") && !lastRequested.equals("")) {
                        if (result.getResults().size() == 0){
                            listener.onWordNotFound(lastRequested);
                        } else {
                            String definition;
                            String partOfSpeech;
                            if (result.getResults().get(0).getSenses().get(0).getDefinition() != null){
                                definition = result.getResults().get(0).getSenses().get(0).getDefinition();
                                partOfSpeech = result.getResults().get(0).getPartOfSpeech();
                            } else {
                                definition = result.getResults().get(0).getSenses().get(0).getSubsenses().get(0).getDefinition();
                                partOfSpeech = result.getResults().get(0).getPartOfSpeech();
                            }

                            listener.onWordDefinitionReceived(new Definition(definition, partOfSpeech));
                        }
                    }
                }, e -> listener.onError(e.getMessage(), false));
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

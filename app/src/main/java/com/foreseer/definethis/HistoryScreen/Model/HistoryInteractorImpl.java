package com.foreseer.definethis.HistoryScreen.Model;

import com.foreseer.definethis.HistoryScreen.View.RecyclerView.ExpandableWord;
import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.Storage.Models.Word;
import com.foreseer.definethis.Storage.StorageHandler;

import java.util.ArrayList;
import java.util.List;

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
                    processWords(words);
                });
    }

    @Override
    public void resetHistory() {
        StorageHandler.resetAllHistory();
    }

    private void processWords(List<Word> words){
        List<ExpandableWord> list = new ArrayList<>();
        for (Word word : words){
            List<Definition> definitions = StorageHandler.convertJSONToDefinitions(word.getJsonDefinitions());
            ExpandableWord expandableWord = new ExpandableWord(word.getWord(), definitions);
            list.add(expandableWord);
        }
        listener.onDefinitionsReceived(list);
    }
}

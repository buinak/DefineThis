package com.foreseer.definethis.HistoryScreen.Model;

import com.foreseer.definethis.HistoryScreen.SortType;
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
    public void requestDefinitions(SortType sortType) {
        Observable.just(StorageHandler.getAllWords())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(words -> {
                    processWords(words, sortType);
                });
    }

    @Override
    public void resetHistory() {
        StorageHandler.resetAllHistory();
    }

    private void processWords(List<Word> words, SortType sortType) {
        List<ExpandableWord> list = new ArrayList<>();
        for (Word word : words) {
            List<Definition> definitions = StorageHandler.convertJSONToDefinitions(word.getJsonDefinitions());
            ExpandableWord expandableWord = new ExpandableWord(word, definitions);
            list.add(expandableWord);
        }
        if (sortType != null) {
            //very primitive sorting please replace later
            sortList(sortType, list);
            // sorting ends here
            System.out.println();
        }

        listener.onDefinitionsReceived(list);
    }

    private void sortList(SortType sortType, List<ExpandableWord> list) {
        for (int i = 0; i < (list.size() - 1); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (sortType == SortType.NEWEST) {
                    if (list.get(i).getWord().getDate().before(list.get(j).getWord().getDate())) {
                        ExpandableWord temporary = list.get(i);
                        list.set(i, list.get(j));
                        list.set(j, temporary);
                    }
                } else if (sortType == SortType.OLDEST) {
                    if (list.get(i).getWord().getDate().after(list.get(j).getWord().getDate())) {
                        ExpandableWord temporary = list.get(i);
                        list.set(i, list.get(j));
                        list.set(j, temporary);
                    }
                } else if (sortType == SortType.A_TO_Z) {
                    if (list.get(i).getWord().getWord().compareTo(list.get(j).getWord().getWord()) > 0) {
                        ExpandableWord temporary = list.get(i);
                        list.set(i, list.get(j));
                        list.set(j, temporary);
                    }
                } else if (sortType == SortType.Z_TO_A) {
                    if (list.get(i).getWord().getWord().compareTo(list.get(j).getWord().getWord()) < 0) {
                        ExpandableWord temporary = list.get(i);
                        list.set(i, list.get(j));
                        list.set(j, temporary);
                    }
                }
            }
        }
    }

}

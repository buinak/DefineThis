package com.foreseer.definethis.HistoryScreen.View;

import com.foreseer.definethis.Storage.Models.Word;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public interface HistoryView {
    void displayWords(List<Word> wordList);
}

package com.foreseer.definethis.HistoryScreen.View.RecyclerView;

import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.Storage.Models.Word;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 23.06.2017.
 */

public class ExpandableWord extends ExpandableGroup<Definition> {

    private Word word;
    private List<Definition> definitions;

    public ExpandableWord(Word word, List<Definition> definitions) {
        super(word.getWord(), definitions);

        this.word = word;
        this.definitions = definitions;
    }

    public Word getWord() {
        return word;
    }
}

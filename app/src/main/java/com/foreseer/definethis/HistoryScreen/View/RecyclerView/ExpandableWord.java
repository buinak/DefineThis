package com.foreseer.definethis.HistoryScreen.View.RecyclerView;

import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.Storage.Models.Word;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 23.06.2017.
 */

public class ExpandableWord extends ExpandableGroup<Definition> {

    public ExpandableWord(String title, List<Definition> items) {
        super(title, items);
    }
}

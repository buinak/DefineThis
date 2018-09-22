package com.foreseer.definethis.HistoryScreen.View.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 23.06.2017.
 */

public class HistoryRecyclerViewAdapter extends ExpandableRecyclerViewAdapter<WordGroupHolder, DefinitionGroupHolder> {

    public HistoryRecyclerViewAdapter(List<ExpandableWord> groups) {
        super(groups);
    }

    @Override
    public WordGroupHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_history_item, parent, false);
        return new WordGroupHolder(view);
    }

    @Override
    public DefinitionGroupHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_definition_history_item, parent, false);
        return new DefinitionGroupHolder(view);
    }

    @Override
    public void onBindChildViewHolder(DefinitionGroupHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Definition definition = (Definition) group.getItems().get(childIndex);
        holder.onBind(definition);
    }

    @Override
    public void onBindGroupViewHolder(WordGroupHolder holder, int flatPosition, ExpandableGroup group) {
        if (flatPosition % 2 == 0) {
            holder.layout.setBackgroundColor(0xF5E0EEEE);
        }
        ExpandableWord word = (ExpandableWord) group;
        holder.setWord(word);
    }
}

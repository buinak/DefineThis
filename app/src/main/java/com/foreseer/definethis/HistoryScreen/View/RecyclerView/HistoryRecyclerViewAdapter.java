package com.foreseer.definethis.HistoryScreen.View.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foreseer.definethis.MainScreen.Model.API.Google.JSONSchemaGoogle.Definition;
import com.foreseer.definethis.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 23.06.2017.
 */

public class HistoryRecyclerViewAdapter extends ExpandableRecyclerViewAdapter<WordGroupHolder, DefinitionGroupHolder> {

    private List<String> words;

    boolean isBackgroundDone = false;
    int i = 0;

    public HistoryRecyclerViewAdapter(List<ExpandableWord> groups) {
        super(groups);
        words = new ArrayList<>(groups.size());
        for (int i = 0; i < groups.size(); i++) {
            words.add(groups.get(i).getTitle());
        }
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
        ExpandableWord word = (ExpandableWord) group;
        holder.setWord(word);
        //making sure there's no magic and we're checking for indeed the word we want
        //that's because sometimes, for god knows what reason, the word in the group and the word
        //in the holder would be different. Why?
        String holderWord = holder.getWord().split(" ")[1];
        if (words.indexOf(holderWord) % 2 == 0) {
            holder.layout.setBackgroundColor(0xF5E0EEEE);
        } else {
            holder.layout.setBackgroundColor(holder.layout.getResources().getColor(R.color.main_card_colour));
        }


    }
}
